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

import rene.gui.Global;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;

public class PointConstructor extends ObjectConstructor {

    boolean Fix;
    PointObject P;
    boolean ShowsValue, ShowsName;

    public void mousePressed(MouseEvent e, ZirkelCanvas zc) {

        

        double x = zc.x(e.getX()), y = zc.y(e.getY());



        PointObject o = zc.selectCreatePoint(e.getX(), e.getY(), false, true);

        Dragging = false;
        if (o == null) {
            return;
        } else if (o.isPointOn()) // cerate a point on an object
        {
            if (e.isShiftDown() && zc.isNewPoint()) {
                o.setUseAlpha(true);
            }
        } else if (o.moveable() && zc.isNewPoint() && !zc.showGrid()) // create a new point and let it be dragged
        {
            P = o;
//			P.setDefaults();
            Fix = e.isShiftDown();
            ShowsValue = P.showValue();
            ShowsName = P.showName();

            Dragging = true;
            zc.repaint();
        } else if (o.moveable() && zc.isNewPoint() && zc.showGrid() && e.isShiftDown()) {
            P = o;
//			P.setDefaults();
            try {
                P.setFixed("" + P.round(P.getX(), ZirkelCanvas.LengthsFactor),
                        "" + P.round(P.getY(), ZirkelCanvas.LengthsFactor));
                P.edit(zc);
                P.validate();
            } catch (Exception ex) {
            }
        } else if (e.isShiftDown() && !zc.isNewPoint()) // create a fixed point at the position of a selected point
        {
            PointObject p =
                    new PointObject(zc.getConstruction(), o.getX(), o.getY());
            zc.addObject(p);
            
//			p.setDefaults();
            p.setFixed(true);
        }

    }

    public void mouseDragged(MouseEvent e, ZirkelCanvas zc) {
        if (!Dragging) {
            return;
        }
        if (Global.getParameter("options.movename", false)) {
            P.setShowValue(true);
            P.setShowName(true);
        }
        P.move(zc.x(e.getX()), zc.y(e.getY()));
        zc.repaint();
    }

    public void mouseReleased(MouseEvent e, ZirkelCanvas zc) {
        if (!Dragging) {
            return;
        }
        Dragging = false;
        P.setShowValue(ShowsValue);
        P.setShowName(ShowsName);
        P.updateText();
        zc.repaint();
        if (Fix) {
            try {
                P.setFixed("" + P.round(P.getX(), ZirkelCanvas.LengthsFactor),
                        "" + P.round(P.getY(), ZirkelCanvas.LengthsFactor));
                if (rene.zirkel.Zirkel.IsApplet) {
                    P.edit(zc);
                } else {
                    eric.JGlobals.EditObject(P);
                }
                P.validate();
            } catch (Exception ex) {
            }
        }
    }

    public void showStatus(ZirkelCanvas zc) {
        zc.showStatus(
                Zirkel.name("message.point", "Point: Set a point!"));
    }

    public boolean construct(XmlTree tree, Construction c)
            throws ConstructionException {
        if (!testTree(tree, "Point")) {
            return false;
        }
        XmlTag tag = tree.getTag();
        if (!tag.hasParam("x") || !tag.hasParam("y")) {
            throw new ConstructionException("Point coordinates missing!");
        }
        double x = 0, y = 0;
        try {
            if (tag.hasParam("actx")) {
                x = new Double(tag.getValue("actx")).doubleValue();

//                                System.out.println(x);
            }
            if (tag.hasParam("acty")) {
                y = new Double(tag.getValue("acty")).doubleValue();

            }

        } catch (Exception e) {
        }
        PointObject p = new PointObject(c, x, y);
        try {
            x = new Expression(tag.getValue("x"), c, p).getValue();
            y = new Expression(tag.getValue("y"), c, p).getValue();
            p.move(x, y);
        } catch (Exception e) {
        }
        setType(tag, p);
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
        if (tag.hasParam("magnetobjs")) {
            // must manage the magnet list after the whole construction is loaded (see c.readConstruction)
            Construction.magnet.add(p);
            Construction.magnet.add(tag.getValue("magnetobjs"));
//            p.setMagnetRay(Integer.parseInt(tag.getValue("magnetd")));
            p.setMagnetRayExp(tag.getValue("magnetd"));
        }
        return true;
    }

    static public void setType(XmlTag tag, PointObject p) {
        if (tag.hasParam("shape")) {
            String s = tag.getValue("shape");
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
    }

    public String getTag() {
        return "Point";
    }

    public void construct(Construction c,
            String name, String params[], int nparams)
            throws ConstructionException {
        
        if (nparams == 0) {
            PointObject p = new PointObject(c,
                    c.getX() + (Math.random() - 0.5) * c.getW(),
                    c.getY() + (Math.random() - 0.5) * c.getW());
            if (!name.equals("")) {
                p.setNameCheck(name);
            }
            c.add(p);
            p.setDefaults();
            return;
        }
        if (nparams == 1) {
            ConstructionObject o = c.find(params[0]);
            if (o == null) {
                throw new ConstructionException(Zirkel.name("exception.notfound") + " " +
                        params[0]);
            }
            if (!(o instanceof PrimitiveLineObject) &&
                    !(o instanceof PrimitiveCircleObject)) {
                throw new ConstructionException(Zirkel.name("exception.type") + " " +
                        params[0]);
            }
            PointObject p = new PointObject(c,
                    c.getX() + (Math.random() - 0.5) * c.getW(),
                    c.getY() + (Math.random() - 0.5) * c.getW(),
                    o);
            if (!name.equals("")) {
                p.setNameCheck(name);
            }
            c.add(p);
            p.setDefaults();
            return;
        }
        if (nparams != 2) {
            throw new ConstructionException(Zirkel.name("exception.nparams"));
        }
        Expression e1 = new Expression(params[0], c, null);
        Expression e2 = new Expression(params[1], c, null);
        if (!e1.isValid() || !e2.isValid()) {
            throw new ConstructionException(Zirkel.name("exception.expression"));
        }
        PointObject p = new PointObject(c, 0, 0);
        try {
            double x = new Double(params[0]).doubleValue();
            double y = new Double(params[1]).doubleValue();
            p.move(x, y);
        } catch (Exception e) {
            p.setFixed(params[0], params[1]);
        }
        c.add(p);
        p.validate();
        p.setDefaults();
        if (!name.equals("")) {
            p.setNameCheck(name);
        }
    }

    public void reset(ZirkelCanvas zc) {
        super.reset(zc);
        zc.setPrompt(Zirkel.name("prompt.point"));
    }
}
