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

import rene.util.*;
import rene.zirkel.structures.CoordinatesXY;

import java.util.*;

// file: Drawing.java

public class Drawing
{	MyVector P;
	int Col;

	public Drawing ()
	{	P=new MyVector();
                
	}

	public void addXY (double x, double y)
	{	P.addElement(new CoordinatesXY(x,y));
	}
	
	public Enumeration elements ()
	{	return P.elements();
	}
	
	public void setColor (int col)
	{	Col=col;
	}
	
	public int getColor ()
	{	return Col;
	}
}
