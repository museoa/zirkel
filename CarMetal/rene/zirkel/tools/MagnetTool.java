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

// file: Binder.java
import eric.JGlobals;
import java.awt.event.*;

import java.util.Enumeration;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.*;
import rene.zirkel.constructors.*;
import rene.zirkel.objects.*;
import rene.zirkel.structures.MagnetObj;

public class MagnetTool extends ObjectConstructor
        implements Selector {

    ObjectConstructor OC;
    PointObject P;

    public MagnetTool(ZirkelCanvas zc, PointObject p, ObjectConstructor oc) {
        P = p;
        OC = oc;
//		P.setSelected(true);
        P.setStrongSelected(true);
        Enumeration e = P.getMagnetObjects().elements();
        while (e.hasMoreElements()) {
            MagnetObj mo = (MagnetObj) e.nextElement();
            mo.setSelected(true);
        }
        zc.repaint();
    }

    public void mousePressed(MouseEvent e, ZirkelCanvas zc) {
        ConstructionObject o = zc.selectWithSelector(e.getX(), e.getY(), this);

        if (o == null) {
            reset(zc);
            return;
        }
        if (o==P) return;
        if (o.selected()) {
            P.removeMagnetObject(o.getName());
            o.setSelected(false);
            JGlobals.RefreshBar();
        } else {
            P.addMagnetObject(o.getName());
//            o.setSelected(true);
            P.selectMagnetObjects(true);
            JGlobals.RefreshBar();
        }
        zc.repaint();
//        if (!e.isShiftDown()) {
//            reset(zc);
//
//        }
//		reset(zc);
    }

    public void mouseMoved(MouseEvent e, ZirkelCanvas zc, boolean simple) {
        zc.indicateWithSelector(e.getX(), e.getY(), this);
    }

    public boolean isAdmissible(ZirkelCanvas zc, ConstructionObject o) {
        return true;
//        if ((o instanceof InsideObject || o instanceof PointonObject) &&
//			!zc.getConstruction().dependsOn(o,P)) return true;
//		return false;
    }

    public void showStatus(ZirkelCanvas zc) {
        zc.showStatus(JGlobals.Loc("props.magnetmessage"));
    }

    public void reset(ZirkelCanvas zc) {
        super.reset(zc);
        zc.setTool(OC);
        zc.validate();
        JGlobals.RefreshBar();
        zc.repaint();
    }

    public boolean useSmartBoard() {
        return false;
    }
}
