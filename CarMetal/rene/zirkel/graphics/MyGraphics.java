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

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import rene.zirkel.objects.ConstructionObject;

public abstract class MyGraphics
{	double W=1000,H=1000;
	public void setSize (double w, double h)
	{	W=w; H=h;
	} 
	public abstract void clearRect (int x, int y, int w, int h, Color c);
	public abstract void setColor (Color c);
	public abstract void setColor (ConstructionObject o);
	public abstract void setFillColor (ConstructionObject o);
	public abstract void setLabelColor (ConstructionObject o);
	public abstract void drawRect (double x, double y, double w, double h);
        public abstract void drawAxisLine(double x, double y, double x1, double y1);
	public abstract void drawLine (double x, double y, double x1, double y1, ConstructionObject o);
	public abstract void drawLine (double x, double y, double x1, double y1);
	public abstract void drawThickLine (double x, double y, double x1, double y1);
	public abstract void drawArc (double x, double y, double w, double h, double a, double b);
	public abstract void drawArc (double x, double y, double w, double h, double a, double b,
		ConstructionObject o);
	public void drawCircleArc (double x, double y, double r, double a, double b,
			ConstructionObject o)
	{	drawArc(x-r,y-r,2*r,2*r,a,b,o);
	}
	public abstract void drawString (String s, double x, double y);
	public abstract void drawOval (double x, double y, double w, double h);
	public abstract void drawOval (double x, double y, double w, double h,
		ConstructionObject o);
	public void drawCircle (double x, double y, double r,
		ConstructionObject o)
	{	drawOval(x-r,y-r,2*r,2*r,o);
	}
	public abstract void fillRect (double x, double y, double w, double h, 
		boolean outline, boolean transparent, ConstructionObject o);
        public abstract void fillRect (double x, double y, double w, double h,Color WithColor);
	public abstract void fillOval (double x, double y, double w, double h, 
		boolean outline, boolean transparent, ConstructionObject o);
        public abstract void fillOval (double x, double y, double w, double h,Color WithColor);
	public abstract void fillPolygon (double x[], double y[], int n,
		boolean outline, boolean tranparent, ConstructionObject o);
        public abstract void fillPolygon (double x[], double y[], int n, ConstructionObject o);
        public abstract void drawDiamond (double x, double y, double w,boolean isThick,ConstructionObject o);
        public abstract void drawDcross (double x, double y, double w,boolean isThick,ConstructionObject o);
	public abstract void fillArc (double x, double y, double w, double h, double a, double b,
		boolean outline, boolean transparent, boolean arc, ConstructionObject o);
	public abstract void drawImage (Image i, int x, int y, ImageObserver o);
	public abstract void drawImage (Image i, int x, int y, int w, int h,
		ImageObserver o);
	public abstract void drawImage (Image i, double x, double y, double x1, double y1, 
			double x2, double y2, ImageObserver o);
	public abstract void setDefaultFont (int size, boolean large, boolean bold);
	public abstract void setFont (boolean large, boolean bold);
	public abstract void setFont (int size, boolean bold);
	public abstract FontMetrics getFontMetrics ();
	public abstract Graphics getGraphics ();
	public abstract int stringAscent (String s);
	public abstract int stringWidth (String s);
	public abstract int stringHeight (String s);
	public abstract int drawStringExtended (String s, double x, double y);
        public abstract void setAntialiasing(boolean bool);
}
