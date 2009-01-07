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
import java.util.Enumeration;
import java.util.Vector;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.AreaObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;

public class AreaConstructor extends ObjectConstructor {

    Vector Points=new Vector();
    static PointObject previewPoint=null;
    static AreaObject previewArea=null;

    public static void deletePreview(ZirkelCanvas zc) {
        if (previewArea!=null) {
            zc.delete(previewArea);
            previewPoint=null;
            previewArea=null;
            zc.reset();
        }
    }

    public void mousePressed(MouseEvent e, ZirkelCanvas zc) {
        if (!zc.Visual) {
            return;
        }
        if (previewPoint==null) {
            previewPoint=new PointObject(zc.getConstruction(), "PrevPoint");
        }

        if (previewArea==null) {
            previewArea=new AreaObject(zc.getConstruction(), Points);
            zc.addObject(previewArea);
            previewArea.setDefaults();
            if (!previewArea.isSolid()) {
                previewArea.setBack(true);
            }
        }
        double x=zc.x(e.getX()), y=zc.y(e.getY());
        Points.remove(previewPoint);
        PointObject P=zc.selectCreatePoint(e.getX(), e.getY());
        previewPoint.move(x, y);
        if (P!=null) {
            P.setSelected(true);
            Enumeration en=Points.elements();
            while (en.hasMoreElements()) {
                if (en.nextElement()==P) {
//                    if (Points.size()<3) zc.delete(previewArea);
//                    previewPoint=null;
//                    previewArea=null;
//                    reset(zc);
                    
                    if (Points.size()>=3) {
                        AreaObject o=new AreaObject(zc.getConstruction(), Points);
                        zc.addObject(o);
                        o.setDefaults();
                        if (!o.isSolid()) {
                            o.setBack(true);
                        }
                    }
                    deletePreview(zc);
//                    reset(zc);
                    return;
                }
            }

            Points.addElement(P);
            Points.addElement(previewPoint);
            previewArea.validate();
            previewArea.setDefaults();
            zc.repaint();
        }
    }

    public void mouseMoved(MouseEvent e, ZirkelCanvas zc, boolean simple) {
        if (Points.size()>=1) {
            previewPoint.move(zc.x(e.getX()), zc.y(e.getY()));
            previewArea.setDefaults();
            zc.repaint();
        }
        ;

        super.mouseMoved(e, zc, simple);
    }

    public String getTag() {
        return "Polygon";
    }

    public void construct(Construction c,
            String name, String params[], int nparams)
            throws ConstructionException {
        if (nparams<3) {
            throw new ConstructionException(Zirkel.name("exception.nparams"));
        }
        Vector v=new Vector();
        for (int i=0; i<nparams; i++) {
            ConstructionObject o=c.find(params[i]);
            if (o==null) {
                throw new ConstructionException(Zirkel.name("exception.notfound")+" "+
                        params[i]);
            }
            if (!(o instanceof PointObject)) {
                throw new ConstructionException(Zirkel.name("exception.type")+" "+
                        params[i]);
            }
            v.addElement(o);
        }
        AreaObject o=new AreaObject(c, v);
        if (!name.equals("")) {
            o.setNameCheck(name);
        }
        c.add(o);
        o.setDefaults();
        o.setBack(true);
    }

    public boolean construct(XmlTree tree, Construction c)
            throws ConstructionException {
        if (!testTree(tree, "Polygon")) {
            return false;
        }
        XmlTag tag=tree.getTag();
        try {
            int i=1;
            Vector v=new Vector();
            while (true) {
                PointObject p=(PointObject) c.find(tag.getValue("point"+i));
                if (p==null) {
                    break;
                }
                v.addElement(p);
                i++;
            }
            AreaObject o=new AreaObject(c, v);
            o.setBack(true);
            setName(tag, o);
            set(tree, o);
            c.add(o);
            setConditionals(tree, c, o);
            if (tag.hasParam("filled")) o.setFilled(false);
        } catch (ConstructionException e) {
            throw e;
        } catch (Exception e) {
            throw new ConstructionException("Polygon parameters illegal!");
        }
        return true;
    }

    public void showStatus(ZirkelCanvas zc) {
        zc.showStatus(Zirkel.name("message.area"));
        zc.setPrompt("="+Zirkel.name("prompt.area"));
    }

    public void reset(ZirkelCanvas zc) {
        super.reset(zc);
        Points=new Vector();
        zc.showStatus(Zirkel.name("message.area"));
    }
}
