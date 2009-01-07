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
package rene.zirkel.tools;

// file: Hider.java
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.MoveableObject;
import rene.zirkel.objects.TextObject;
import rene.zirkel.objects.UserFunctionObject;

/**
 * @author Rene
 * Werkzeug zum Zoomen des Fensters und zum Verschieben.
 * Verschieben funktioniert im Zentrum des Fensters. 
 */
public class ZoomerTool extends ObjectConstructor {

    boolean Dragging=false, Zoom=false;
    double X, Y, W, X0, Y0;
    ObjectConstructor OC;

    public static void initNonDraggableObjects(Construction c) {
        Enumeration en=c.elements();
        while (en.hasMoreElements()) {
            ConstructionObject o=(ConstructionObject) en.nextElement();
            if ((o instanceof TextObject)||(o instanceof ExpressionObject)||(o instanceof UserFunctionObject)) {
                MoveableObject mo=(MoveableObject) o;
                mo.startDrag(0, 0);
            }
        }
    }

    public static void shiftNonDraggableObjectsBy(Construction c, double dx, double dy) {
        Enumeration en=c.elements();
        while (en.hasMoreElements()) {
            ConstructionObject o=(ConstructionObject) en.nextElement();
            if ((o instanceof TextObject)||(o instanceof ExpressionObject)||(o instanceof UserFunctionObject)) {
                MoveableObject mo=(MoveableObject) o;
                mo.dragTo(dx, dy);
            };

//            else if (!o.isKeepClose()) {
//
//                    System.out.println("dx="+dx);
//                o.setcOffset(o.xcOffset()+2*dx, o.ycOffset()+2*dy);
//
////                    C.setXYW(C.getX()+dx*C.getW(), C.getY()+dy*C.getW(), C.getW());
//
//            };


        }
    }

    public static void zoomNonDraggableObjectsBy(Construction c, double f) {
        Enumeration en=c.elements();

        while (en.hasMoreElements()) {
            ConstructionObject o=(ConstructionObject) en.nextElement();
            if ((o instanceof TextObject)||(o instanceof ExpressionObject)||(o instanceof UserFunctionObject)) {
                MoveableObject mo=(MoveableObject) o;
                mo.move(c.getX()+(mo.getOldX()-c.getX())*f, c.getY()+(mo.getOldY()-c.getY())*f);
            }
        }
    }

    public ZoomerTool() {
        super();
    }

    public ZoomerTool(ObjectConstructor oc, MouseEvent e, ZirkelCanvas zc) {
        super();
        OC=oc;
        X0=zc.x(e.getX());
        Y0=zc.y(e.getY());
        Construction c=zc.getConstruction();
        X=c.getX();
        Y=c.getY();
        W=c.getW();
        Zoom=false;
        zc.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        Dragging=true;
        initNonDraggableObjects(c);
    }

    public void mousePressed(MouseEvent e, ZirkelCanvas zc) {
        X0=zc.x(e.getX());
        Y0=zc.y(e.getY());
        Construction c=zc.getConstruction();
        X=c.getX();
        Y=c.getY();
        W=c.getW();
        Zoom=(Math.abs(X-X0)>W/4||Math.abs(Y-Y0)>W/4);
        if (!Zoom) {
            zc.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
        Dragging=true;
        OC=null;
        initNonDraggableObjects(c);
    }

    public void mouseDragged(MouseEvent e, ZirkelCanvas zc) {
        if (!Dragging) {
            return;
        }
        Construction c=zc.getConstruction();
        c.setXYW(X, Y, W);
        zc.recompute();
        double x=zc.x(e.getX()), y=zc.y(e.getY());
        if (Zoom) {
            double f=Math.sqrt((X0-X)*(X0-X)+(Y0-Y)*(Y0-Y))/
                    Math.sqrt((x-X)*(x-X)+(y-Y)*(y-Y));
            c.setXYW(X, Y, f*W);
            zoomNonDraggableObjectsBy(c, f);
        } else {
            c.setXYW(X-(x-X0), Y-(y-Y0), W);
            shiftNonDraggableObjectsBy(c, X0-x, Y0-y);
        }
        zc.recompute();
        zc.validate();
        zc.repaint();
    }

    public void mouseReleased(MouseEvent e, ZirkelCanvas zc) {
        Dragging=Zoom=false;
        zc.setCursor(Cursor.getDefaultCursor());
        zc.recompute();
        zc.validate();
        zc.repaint();
        if (OC!=null) {
            zc.setTool(OC);
        }
    }

    public void showStatus(ZirkelCanvas zc) {
        zc.showStatus(Zirkel.name("message.zoom"));
    }

    public void reset(ZirkelCanvas zc) {
        zc.clearSelected();
        zc.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        Zoom=Dragging=false;
    }

    public void invalidate(ZirkelCanvas zc) {
        zc.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseMoved(MouseEvent e, ZirkelCanvas zc, boolean flag) {
        X0=zc.x(e.getX());
        Y0=zc.y(e.getY());
        Construction c=zc.getConstruction();
        X=c.getX();
        Y=c.getY();
        W=c.getW();
        Zoom=(Math.abs(X-X0)>W/4||Math.abs(Y-Y0)>W/4);
        if (!Zoom) {
            zc.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        } else {
            zc.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    public boolean useSmartBoard() {
        return false;
    }
}
