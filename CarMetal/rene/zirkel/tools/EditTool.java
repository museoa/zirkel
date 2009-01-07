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
import java.awt.event.MouseEvent;
import java.util.Vector;

import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.dialogs.EditConditionals;
import rene.zirkel.dialogs.ObjectsEditDialog;
import rene.zirkel.objects.ConstructionObject;

public class EditTool extends ObjectConstructor {

    boolean Other;
    Vector V;

    public void mousePressed(MouseEvent e, ConstructionObject o,
            ZirkelCanvas zc) {
        double x = zc.x(e.getX()), y = zc.y(e.getY());
        Other = (e.isShiftDown() && o == null);
        if (o == null) {
            o = zc.selectObject(e.getX(), e.getY());
            if (o == null) {
                return;
            }
        }
        if (o.isKeep()) {
            return;
        }
        if (Other) {
            if (V == null) {
                V = new Vector();
            }
            V.addElement(o);
            o.setSelected(true);
            zc.repaint();
            return;
        }
        if (V != null) {
            V.addElement(o);
            o.setSelected(true);
            if (e.isControlDown()) {
                EditConditionals d = new EditConditionals(zc.getFrame(), V);
                zc.clearSelected();
                zc.repaint();
            } else {
                ObjectsEditDialog d = new ObjectsEditDialog(zc.getFrame(), V);
                zc.clearSelected();
                d.setVisible(true);
            }
        } else if (e.isControlDown()) {
            new EditConditionals(zc.getFrame(), o);
            zc.validate();
            zc.repaint();
        } else {
            String oldname = o.getName();
            if (rene.zirkel.Zirkel.IsApplet) {
                o.edit(zc);
            } else {
                eric.JGlobals.EditObject(o, true,false);
            }
            if (!oldname.equals(o.getName())) {
                zc.updateTexts(o, oldname);
            }
        }
        V = null;
        Other = false;
        zc.validate();
        zc.repaint();
    }

    public void mouseMoved(MouseEvent e, ZirkelCanvas zc, boolean simple) {
        zc.indicateObjects(e.getX(), e.getY(), true);
    }

    public void mousePressed(MouseEvent e, ZirkelCanvas zc) {
        mousePressed(e, null, zc);
    }

    public void showStatus(ZirkelCanvas zc) {
        zc.showStatus(Zirkel.name("message.edit"));
    }

    public void reset(ZirkelCanvas zc) {
        zc.clearSelected();
        V = null;
        Other = false;
    }
}
