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
 
 
 package rene.util.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Vector;

/**
Quick sort implementation. Sorts an array or a vector of SortObject.
*/

public class Sorter
{	static public void sort (SortObject v[])
	{	QuickSort(v,0,v.length-1);
	}
	
	static public void sort (SortObject v[], int n)
	{	QuickSort(v,0,n-1);
	}
	
	static public void sort (Vector v)
	{	SortObject o[]=new SortObject[v.size()];
		v.copyInto(o);
		sort(o);
		for (int i=0; i<o.length; i++)
			v.setElementAt(o[i],i);
	}
	
	static public void QuickSort (SortObject a[], int lo0, int hi0)
	{	int lo = lo0;
		int hi = hi0;
		SortObject mid;

		if ( hi0 > lo0)
		{	mid = a[ ( lo0 + hi0 ) / 2 ];
			while( lo <= hi )
			{	while( ( lo < hi0 ) && ( a[lo].compare(mid)<0 ) )
				   ++lo;
				while( ( hi > lo0 ) && ( a[hi].compare(mid)>0 ) )
				   --hi;
				if( lo <= hi ) 
				{	swap(a, lo, hi);
					++lo;
					--hi;
				}
			}
			if( lo0 < hi ) QuickSort( a, lo0, hi );
			if( lo < hi0 ) QuickSort( a, lo, hi0 );
		}	
	}

	static private void swap (SortObject a[], int i, int j)
	{	SortObject T;
		T = a[i]; 
		a[i] = a[j];
		a[j] = T;
	}

	static public void QuickSort (Object a[], int lo0, int hi0)
	{	int lo = lo0;
		int hi = hi0;
		SortObject mid;

		if ( hi0 > lo0)
		{	mid = (SortObject) a[ ( lo0 + hi0 ) / 2 ];
			while( lo <= hi )
			{	while( ( lo < hi0 ) && ( ((SortObject)a[lo]).compare(mid)<0 ) )
				   ++lo;
				while( ( hi > lo0 ) && ( ((SortObject)a[hi]).compare(mid)>0 ) )
				   --hi;
				if( lo <= hi ) 
				{	swap(a, lo, hi);
					++lo;
					--hi;
				}
			}
			if( lo0 < hi ) QuickSort( a, lo0, hi );
			if( lo < hi0 ) QuickSort( a, lo, hi0 );
		}
	}

	static private void swap (Object a[], int i, int j)
	{	Object T;
		T = a[i]; 
		a[i] = a[j];
		a[j] = T;
	}

	public static void main (String args[])
		throws IOException
	// Sort the incoming lines and remove doublicates
	{	BufferedReader in=new BufferedReader(
			new InputStreamReader(System.in));
		Vector v=new Vector();
		while (true)
		{	String line=in.readLine();
			if (line==null) break;
			v.addElement(new SortString(line));
		}
		in.close();
		sort(v);
		Enumeration e=v.elements();
		String last=null;
		while (e.hasMoreElements())
		{	String s=((SortString)e.nextElement()).toString();
			if (last==null || !s.equals(last))
			{	System.out.println(s);
				last=s;
			}
		}
	}
}

