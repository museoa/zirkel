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
 
 
 package rene.zirkel.construction;

// file: ConstructionObject.java

import java.util.*;

import rene.zirkel.objects.*;

public class DepList implements Enumeration
{	ConstructionObject E[]=new ConstructionObject[8];
	int N=0,I;
	public void reset () { N=0; }
	public void add (ConstructionObject o)
	{	
        if (have(o)) return;
		if (N<E.length) E[N++]=o;
		else
		{	ConstructionObject e[]=new ConstructionObject[E.length+8];
			for (int i=0; i<E.length; i++) e[i]=E[i];
			E=e;
			E[N++]=o;
		}
	}
	public boolean have (ConstructionObject o)
	{	for (int i=0; i<N; i++)
			if (E[i]==o) return true;
		return false;
	}
	public Enumeration elements ()
	{	I=0; return this;
	}
	public boolean hasMoreElements () { return I<N; }
	public Object nextElement () { return E[I++]; }
	public ConstructionObject[] getArray ()
	{	
                ConstructionObject o[]=new ConstructionObject[N];
                
		for (int i=0; i<N; i++) o[i]=E[i];
		return o;
	}
	public void translate ()
	{	for (int i=0; i<N; i++)
			E[i]=E[i].getTranslation();
	}
	public int size() { return N; }
}
