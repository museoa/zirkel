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

// file: SetParameter.java

import java.awt.event.MouseEvent;

import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;

public class SetTargetsTool extends ObjectConstructor
{	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	double x=zc.x(e.getX()),y=zc.y(e.getY());
		ConstructionObject o=zc.selectConstructableObject(e.getX(),e.getY());
		if (o==null || !o.isFlag()) return;
		o.setTarget(true);
		if (o.isTarget())
		{	o.setSelected(true);
			zc.getConstruction().addTarget(o);
			zc.repaint();
		}
	}
	public void mouseMoved (MouseEvent e, ZirkelCanvas zc, boolean simple)
	{	zc.indicateConstructableObjects(e.getX(),e.getY());
	}
	public void reset (ZirkelCanvas zc)
	{	super.reset(zc);
		zc.clearSelected();
		zc.getConstruction().clearTargets();
		zc.getConstruction().determineConstructables();
	}
	public void showStatus (ZirkelCanvas zc)
	{	zc.showStatus(
			Zirkel.name("message.targets",
				"Macro Targets: Select the Targets!"));
	}
	public boolean useSmartBoard ()
	{	return false;
	}
}
