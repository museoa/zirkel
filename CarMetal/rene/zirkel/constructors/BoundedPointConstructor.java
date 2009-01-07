/* 
Copyright 2006 Rene Grothmann, modified by Eric Hakenholz
This file is part of C.a.R. software.
C.a.R. is a free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.
C.a.R. is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package rene.zirkel.constructors;

// file: PointConstructor.java
import java.awt.event.MouseEvent;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Selector;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.InsideObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PointonObject;

public class BoundedPointConstructor extends ObjectConstructor
        implements Selector {

    boolean Control;

    public void mousePressed(MouseEvent e, ZirkelCanvas zc) {
        if (!zc.Visual) {
            return;
        }
        Control=e.isControlDown();



        ConstructionObject o=zc.selectWithSelector(e.getX(), e.getY(), this);
        if (o==null) {
            return;
        }
        PointObject p=
                new PointObject(zc.getConstruction(), zc.x(e.getX()), zc.y(e.getY()), o);
        if (!e.isShiftDown()) {
            p.setUseAlpha(true);
        }
//		if (Control && o instanceof InsideObject) p.setInside(true);
        if (o instanceof InsideObject) {
            p.setInside(true);
        }
        zc.addObject(p);
        p.validate();
        p.setDefaults();
    }

    public void mouseMoved(MouseEvent e, ZirkelCanvas zc, boolean simple) {
        Control=e.isControlDown();
        zc.indicateWithSelector(e.getX(), e.getY(), this);
    }

    public void showStatus(ZirkelCanvas zc) {
        zc.showStatus(
                Zirkel.name("message.boundedpoint",
                "Bounded Point: Choose a circle or line!"));
    }

    public boolean isAdmissible(ZirkelCanvas zc, ConstructionObject o) {
//                if (Control && o instanceof InsideObject) return true;
//		else if (!Control && o instanceof PointonObject) return true;

        if (o instanceof InsideObject) {
            return true;
        }
        return false;
    }

    public boolean construct(XmlTree tree, Construction c)
            throws ConstructionException {

        if (!testTree(tree, "PointOn")) {
            return false;
        }
        XmlTag tag=tree.getTag();
        if (!tag.hasParam("on")) {
            throw new ConstructionException("Point bound missing!");
        }
        try {
            ConstructionObject o=
                    (ConstructionObject) c.find(tag.getValue("on"));
            if (o!=null&&!(o instanceof PointonObject)&&!(o instanceof InsideObject)) {
                throw new ConstructionException("");
            }
            double x=0, y=0;
            try {
                x=new Double(tag.getValue("x")).doubleValue();
                y=new Double(tag.getValue("y")).doubleValue();
            } catch (Exception e) {
            }
            PointObject p;
            if (o!=null) {
                p=new PointObject(c, x, y, o);
            } else {
                p=new PointObject(c, x, y);
                p.setLaterBind(tag.getValue("on"));
            }
            p.setInside(tag.hasTrueParam("inside"));
            try {
                double alpha=new Double(tag.getValue("alpha")).doubleValue();
                p.setAlpha(alpha);
                p.setUseAlpha(true);
                if (tag.hasParam("on")) {
                    ConstructionObject on=c.find(tag.getValue("on"));
                    if (on!=null) {
                        p.project(on, alpha);
                    }
                }
            } catch (Exception e) {
            }
            if (tag.hasParam("shape")) {
                String s=tag.getValue("shape");
                if (s.equals("square")) {
                    p.setType(0);
                }
                if (s.equals("diamond")) {
                    p.setType(1);
                }
                if (s.equals("circle")) {
                    p.setType(2);
                }
                if (s.equals("dot")) {
                    p.setType(3);
                }
                if (s.equals("cross")) {
                    p.setType(4);
                }
                if (s.equals("dcross")) {
                    p.setType(5);
                }
            }
            if (tag.hasParam("boundorder")) {
                p.setBoundOrder(Double.valueOf(tag.getValue("boundorder")).doubleValue());
            }
            setName(tag, p);
            set(tree, p);
            c.add(p);
            setConditionals(tree, c, p);
            if (tag.hasParam("fixed")) {
                p.setFixed(tag.getValue("x"), tag.getValue("y"));
            }
            if (tag.hasParam("increment")) {
                try {
                    p.setIncrement(new Double(tag.getValue("increment")).doubleValue());
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConstructionException("Illegal point bound!");
        }
        return true;
    }

    public void reset(ZirkelCanvas zc) {
        super.reset(zc);
        zc.setPrompt(Zirkel.name("prompt.pointon"));
    }
}
