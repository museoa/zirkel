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

import java.util.Enumeration;
import java.util.Vector;

/**
This is used to generate unique numbers for construction objects.
Instances will be static in their classes. There is also an
alternative count, which can be used for macro generation.
*/

public class Count
{	static Vector Counts=new Vector();
	int N,BackupN;
	boolean Fixed=false;
	public Count ()
	{	reset();
		Counts.addElement(this);
	}
	public synchronized int next ()
	{	if (Fixed) return 0;
		N++; return N;
	}
	public synchronized void reset ()
	{	N=0;
	}
	public synchronized void setAlternate (boolean flag)
	{	if (flag) { BackupN=N; N=0; }
		else N=BackupN;
	}
	static synchronized public void resetAll ()
	{	Enumeration e=Counts.elements();
		while (e.hasMoreElements())
			((Count)e.nextElement()).reset();
	}
	static synchronized public void setAllAlternate (boolean flag)
	{	Enumeration e=Counts.elements();
		while (e.hasMoreElements())
			((Count)e.nextElement()).setAlternate(flag);
	}
	public synchronized void fix (boolean flag)
	{	Fixed=flag;
	}
	static synchronized public void fixAll (boolean flag)
	{	Enumeration e=Counts.elements();
		while (e.hasMoreElements())
			((Count)e.nextElement()).fix(flag);
	}
}
