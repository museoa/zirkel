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

// file: Circle3Object.java

import java.util.*;

import rene.util.xml.*;
import rene.zirkel.Zirkel;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.InvalidException;

public class Circle3Object extends PrimitiveCircleObject
{	protected PointObject P1,P2;
	public Circle3Object (Construction c, 
		PointObject p1, PointObject p2, PointObject p3)
	{	super(c,p3);
		P1=p1; P2=p2;
		validate();
		updateText();
	}

	public String getTag () { return "Circle3"; }
	
	public void updateText ()
	{	setText(text3(Zirkel.name("text.circle3"),
			M.getName(),P1.getName(),P2.getName()));
	}
	public void validate ()
	{	super.validate();
		if (!M.valid() || !P1.valid() || !P2.valid()) { Valid=false; return; }
		else
		{	Valid=true;
			X=M.getX(); Y=M.getY();
			// compute normalized vector in the direction of the line:
			double DX=P2.getX()-P1.getX(),DY=P2.getY()-P1.getY(); 
			R=Math.sqrt(DX*DX+DY*DY);
			if (R<0) { R=0; }
		}
	}
	public void printArgs (XmlWriter xml)
	{	xml.printArg("from",P1.getName());
		xml.printArg("to",P2.getName());
		super.printArgs(xml);
	}	

	public double getValue ()
		throws ConstructionException
	{	if (!Valid) throw new InvalidException("exception.invalid");
		else return R;
	}

	public Enumeration depending ()
	{	super.depending();
		return depset(P1,P2);
	}
	
	public void translate ()
	{	P1=(PointObject)P1.getTranslation();
		P2=(PointObject)P2.getTranslation();
		super.translate();
	}

}
