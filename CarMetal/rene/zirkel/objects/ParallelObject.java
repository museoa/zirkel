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
 
 
 package rene.zirkel.objects;

// file: ParallelObject.java

import java.util.Enumeration;

import rene.util.xml.XmlWriter;
import rene.zirkel.*;
import rene.zirkel.construction.*;

public class ParallelObject extends PrimitiveLineObject
	implements MoveableObject
{	protected PrimitiveLineObject L;
	static Count N=new Count();
	public ParallelObject (Construction c, PrimitiveLineObject l, PointObject p)
	{	super(c);
		P1=p; L=l;
		validate();
		updateText();
	}
	public String getTag () { return "Parallel"; }
	public int getN () { return N.next(); }
	
	public void updateText ()
	{	setText(text2(Zirkel.name("text.parallel"),P1.getName(),L.getName()));
	}
	public void validate ()
	{	if (!P1.valid() || !L.valid()) Valid=false;
		else if (L instanceof SegmentObject &&
				((SegmentObject)L).getLength()==0)
		{	Valid=false;
		}
		else
		{	Valid=true;
			X1=P1.getX(); Y1=P1.getY();
			DX=L.getDX(); DY=L.getDY();
		}
	}
	public void printArgs (XmlWriter xml)
	{	xml.printArg("point",P1.getName());
		xml.printArg("line",L.getName());
		super.printArgs(xml);
	}	
	
	public Enumeration depending ()
	{	super.depending();
		return depset(P1,L);
	}

	public void translate ()
	{	P1=(PointObject)P1.getTranslation();
		L=(PrimitiveLineObject)L.getTranslation();
	}

	public boolean contains (PointObject p)
	{	return p==P1;
	}

	public boolean hasUnit ()
	{	return false;
	}

	public void dragTo (double x, double y) 
	{	P1.move(x1+(x-x3),y1+(y-y3));
	}

	public void move (double x, double y) 
	{
	}

	public boolean moveable () 
	{	if (P1.moveable()) return true;
		return false;
	}

	double x1,y1,x2,y2,x3,y3;
	
	public void startDrag (double x, double y) 
	{	x1=P1.getX(); y1=P1.getY();
		x3=x; y3=y;
	}

        public double getOldX() {
        return 0;
    }

    public double getOldY() {
        return 0;
    }

	public void snap (ZirkelCanvas zc)
	{	if (moveable())
		{	P1.snap(zc);
		}
	}
}
