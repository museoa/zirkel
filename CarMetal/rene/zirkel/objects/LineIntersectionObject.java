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

import rene.zirkel.construction.Construction;
import rene.zirkel.structures.Coordinates;

// file: IntersectionObject.java


public class LineIntersectionObject extends IntersectionObject
{	public LineIntersectionObject (Construction c, 
		PrimitiveLineObject P1, PrimitiveLineObject P2)
	{	super(c,P1,P2);
		validate();
	}
        

	
	public void updateCircleDep ()
	{	((PrimitiveLineObject)P1).addDep(this);
		((PrimitiveLineObject)P2).addDep(this);
	}

	public void validate ()
	{	if (!P1.valid() || !P2.valid()) Valid=false;
		else Valid=true; 
		if (!Valid) return;
		Coordinates c=
			PrimitiveLineObject.intersect((PrimitiveLineObject)P1,
					(PrimitiveLineObject)P2);
		if (c==null) { Valid=false; return; }
                setXY(c.X,c.Y);
		if (Restricted)
		{	if (!((PrimitiveLineObject)P1).contains(X,Y)) Valid=false;
			else if (!((PrimitiveLineObject)P2).contains(X,Y)) Valid=false;
		}
	}
}
