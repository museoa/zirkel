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

// file: LineCircleIntersectionObject.java

import rene.util.xml.*;
import rene.zirkel.construction.Construction;
import rene.zirkel.structures.Coordinates;


public class LineQuadricIntersectionObject extends IntersectionObject
{	public LineQuadricIntersectionObject (Construction c,
		PrimitiveLineObject P1, QuadricObject P2,
		boolean first)
	{	super(c,P1,P2);
		First=first;
		validate();
	}

//	public void updateCircleDep ()
//	{	((QuadricObject)P2).addDep(this);
//		((PrimitiveLineObject)P1).addDep(this);
//	}

	public void validate ()
	{	boolean oldvalid=Valid;
		if (!P1.valid() || !P2.valid()) Valid=false;
		else Valid=true; 
		if (!Valid) return;
		Coordinates c=PrimitiveLineObject.intersect(
			(PrimitiveLineObject)P1,(QuadricObject)P2);
                
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
		double X1,Y1;
		if (getAway()!=null)
		{	double x=getAway().getX(),y=getAway().getY();
			double r=(x-c.X)*(x-c.X)+(y-c.Y)*(y-c.Y);
			double r1=(x-c.X1)*(x-c.X1)+(y-c.Y1)*(y-c.Y1);
			boolean flag=(r>r1);
			if (!StayAway) flag=!flag;
			if (flag)
			{	
                                setXY(c.X,c.Y);
				X1=c.X1; Y1=c.Y1;
			}
			else
			{	
                                setXY(c.X1,c.Y1);
				X1=c.X; Y1=c.Y;
			}
		}
		else
		{	if (First) { setXY(c.X,c.Y); X1=c.X1; Y1=c.Y1; }
			else { setXY(c.X1,c.Y1); X1=c.X; Y1=c.Y; }
		}
		if (Restricted)
		{	
                    
                    if (!((PrimitiveLineObject)P1).contains(X,Y)) {
                        if (!((PrimitiveLineObject)P1).contains(X1,Y1)){
                            Valid=false;
                        }else{
                            setXY(X1,Y1);
                        }
                        
                    }
                    
                    
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
