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

import java.awt.event.MouseEvent;

import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.IntersectionObject;

/**
 * A tool to set the the point an intersection point should stay away from
 * or should come close to.  
 */

public class SetAwayTool extends ObjectConstructor
{	ObjectConstructor OC;
	IntersectionObject P;
	boolean Away;
	
	public SetAwayTool (ZirkelCanvas zc, IntersectionObject p, boolean away,
		ObjectConstructor oc)
	{	P=p; OC=oc;
		P.setSelected(true);
		Away=away;
		zc.repaint();
	}
	
	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	ConstructionObject o=zc.selectPoint(e.getX(),e.getY());
		if (o==null) return;
		if (zc.getConstruction().dependsOn(o,P))
		{	zc.warning(ConstructionObject.text1(Zirkel.name("error.depends"),P.getText()));
			return;
		}
		P.setAway(o.getName(),Away);
		P.setUseAlpha(e.isShiftDown());
		zc.validate();
		reset(zc);
	}
	
	public void showStatus (ZirkelCanvas zc)
	{	if (Away) zc.showStatus(
			Zirkel.name("message.setaway.away"));
		else zc.showStatus(
			Zirkel.name("message.setaway.close"));
	}
	
	public void reset (ZirkelCanvas zc)
	{	super.reset(zc);
		zc.setTool(OC);
		zc.validate();
		zc.repaint();
	}
	
	public boolean useSmartBoard ()
	{	return false;
	}
}
