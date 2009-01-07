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

// file: CircleIntersectionObject.java

import rene.util.xml.XmlWriter;
import rene.zirkel.construction.Construction;
import rene.zirkel.structures.Coordinates;

public class CircleIntersectionObject extends IntersectionObject
{	public CircleIntersectionObject (
		Construction c, PrimitiveCircleObject P1, PrimitiveCircleObject P2,
		boolean first)
	{	super(c,P1,P2);
		First=first;
		validate();
	}

	public void updateCircleDep ()
	{	((PrimitiveCircleObject)P1).addDep(this);
		((PrimitiveCircleObject)P2).addDep(this);
	}

	public void validate ()
	{	boolean oldvalid=Valid;
		if (!P1.valid() || !P2.valid()) Valid=false;
		else Valid=true; 
		if (!Valid) return;
		Coordinates c=PrimitiveCircleObject.intersect(
			(PrimitiveCircleObject)P1,(PrimitiveCircleObject)P2);
		if (c==null)
		{	if (oldvalid && getConstruction().shouldSwitch())
			{	doSwitch();
				if (!getConstruction().noteSwitch()) Switched=false;
			}
			else if (oldvalid && Alternate && Away==null && getConstruction().canAlternate())
			{	First=!First;
			}
			Valid=false; return; 
		}
		PointObject oa=getAway();
		if (oa!=null)
		{	double x=oa.getX(),y=oa.getY();
			double r=(x-c.X)*(x-c.X)+(y-c.Y)*(y-c.Y);
			double r1=(x-c.X1)*(x-c.X1)+(y-c.Y1)*(y-c.Y1);
			boolean flag=(r>r1);
			if (!StayAway) flag=!flag;
			if (flag)
			{	
                            
                            setXY(c.X,c.Y);
			}
			else
			{   
                           
                            setXY(c.X1,c.Y1);
			}
		}
		else
		{	if (First) setXY(c.X,c.Y);
			else setXY(c.X1,c.Y1);
		}
		if (Restricted)
		{	if (!(((PrimitiveCircleObject)P1).getStart()==this ||
				((PrimitiveCircleObject)P1).getEnd()==this) &&
				!((PrimitiveCircleObject)P1).contains(X,Y)) Valid=false;
			if (!(((PrimitiveCircleObject)P2).getStart()==this ||
					((PrimitiveCircleObject)P2).getEnd()==this) &&
				!((PrimitiveCircleObject)P2).contains(X,Y)) Valid=false;
		}
	}

	public void printArgs (XmlWriter xml)
	{	super.printArgs(xml);
		if (First) xml.printArg("which","first");
		else xml.printArg("which","second");
	}

	public boolean isSwitchable ()
	{	return true;
	}

	public boolean canAlternate ()
	{	return true;
	}
}
