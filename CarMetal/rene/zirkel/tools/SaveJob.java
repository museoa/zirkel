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

public class SaveJob extends ObjectConstructor
{	public ConstructionObject Last;
	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	double x=zc.x(e.getX()),y=zc.y(e.getY());
		ConstructionObject o=zc.selectObject(e.getX(),e.getY());
		if (o==null) return;
		o.setSelected(true);
		if (Last==null) 
		{	Last=o;
			zc.setJob(Last);
			showStatus(zc);
		}
		else
		{	if (e.isShiftDown()) zc.addJobTarget(o,true);
			else zc.addJobTarget(o,false);
		}
		zc.repaint();
	}
	public void mouseMoved (MouseEvent e, ZirkelCanvas zc, boolean simple)
	{	zc.indicateObjects(e.getX(),e.getY());
	}

	public void reset (ZirkelCanvas zc)
	{	super.reset(zc);
		zc.clearSelected();
		zc.getConstruction().clearTargets();
		zc.clearTargets();
		Last=null;
		showStatus(zc);
		zc.clearJob();
	}
	public void showStatus (ZirkelCanvas zc)
	{	if (Last==null)
			zc.showStatus(
				Zirkel.name("message.savejob.first"));
		else
			zc.showStatus(
				Zirkel.name("message.savejob.second"));
	}
	public boolean useSmartBoard ()
	{	return false;
	}
}
