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
 
 
 package rene.zirkel.macro;

// file: ZirkelCanvas.java

import java.awt.MenuItem;

import rene.util.sort.SortObject;

public class MacroItem
	implements SortObject
{	public MenuItem I;
	public Macro M;
	public String Name;
	public MacroItem (Macro m, MenuItem i)
	{	M=m; I=i;
	}
	public int compare (SortObject o) 
	{	MacroItem mio=(MacroItem)o;
		return -mio.M.Name.compareTo(M.Name);
	}
}
