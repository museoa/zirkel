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
 
 
 package rene.zirkel.graphics;

import rene.zirkel.objects.ConstructionObject;

/**
 * @author Rene
 *
 */
public class PolygonDrawer
{	MyGraphics G;
	ConstructionObject O;
	double C,R;
	double C1,R1;
	boolean HaveToFinish;
	public static int step=9;
	public boolean Marker=false;
	public boolean Started;
	boolean Solid;
	
	public PolygonDrawer (MyGraphics g, ConstructionObject o)
	{	G=g; O=o;
		Started=false;
	}
	
	public void startPolygon (double c, double r)
	{	C=c; R=r;
		HaveToFinish=false;
		Started=true;
	}
	
	public void drawTo (double c, double r, boolean dodraw)
	{	if (!Started)
		{	startPolygon(c,r);
			return;
		}
		if (dodraw || (c-C)*(c-C)+(r-R)*(r-R)>step)
		{	if (Marker) ((MyGraphics13)G).drawMarkerLine(C,R,c,r);
			else G.drawLine(C,R,c,r,O);
			C=c; R=r;
			HaveToFinish=false;
		}
		else
		{	C1=c; R1=r;
			HaveToFinish=true;
		}
	}
	
	public void drawTo (double c, double r)
	{	drawTo(c,r,false);
	}
	
	public void finishPolygon ()
	{	if (HaveToFinish)
		{	if (Marker) ((MyGraphics13)G).drawMarkerLine(C,R,C1,R1);
			else G.drawLine(C,R,C1,R1,O);
			HaveToFinish=false;
		}	
		Started=false;
	}

	public boolean hasStarted ()
	{	return Started;
	}
	
	public double c()
	{	return C;
	}
	
	public double r()
	{	return R;
	}

	public void useAsMarker ()
	{	Marker=true;
		step=18;
	}
}
