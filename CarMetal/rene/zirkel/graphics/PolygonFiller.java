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
public class PolygonFiller
{	MyGraphics G;
	ConstructionObject O;
	double C,R;
	double C1,R1;
	boolean HaveToFinish;
	public static int step=5;
	public boolean Started;
	boolean Solid;
	
	double x[],y[];
	int nx;
	
	public PolygonFiller (MyGraphics g, ConstructionObject o)
	{	G=g; O=o;
		Started=false;
	}
	
	public void startPolygon (double c, double r)
	{	C=c; R=r;
		HaveToFinish=false;
		nx=0;
		Started=true;
		drawTo(c,r,false);
	}
	
	public void start ()
	{	nx=0; Started=false;
	}
	
	public void drawTo (double c, double r)
	{	drawTo(c,r,true);
	}
	
	public void drawTo (double c, double r, boolean optimized)
	{	if (!Started)
		{	startPolygon(c,r);
			return;
		}
		if (!optimized || (c-C)*(c-C)+(r-R)*(r-R)>step)
		{	if (x==null)
			{	x=new double[1000];
				y=new double[1000];
				nx=0;
			}
			if (nx>=x.length)
			{	double xn[]=new double[2*x.length]; 
				double yn[]=new double[2*x.length];
				for (int i=0; i<nx; i++)
				{	xn[i]=x[i]; yn[i]=y[i]; 
				}
				x=xn; y=yn;
			}
			x[nx]=c; y[nx++]=r;
			C=c; R=r;
			HaveToFinish=false;
		}
		else
		{	
            C1=c; R1=r;
			HaveToFinish=true;
		}
	}
	
	public void finishPolygon ()
	{	if (HaveToFinish)
		{	drawTo(C1,R1,false);
			HaveToFinish=false;
		}
		G.fillPolygon(x,y,nx,false,true,O);
		Started=false;
	}

   



	
	public int length ()
	{	return nx;
	}
	
	public double x (int i)
	{	return x[i];
	}
	public double y (int i)
	{	return y[i];
	}

	public void setGraphics (MyGraphics g)
	{	G=g;
	}
}

