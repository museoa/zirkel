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
 
 
 package rene.util.list;

/**
The nodes of a list.
@see rene.list.ListClass
*/

public class ListElement
// A list node with pointers to previous and next element
// and with a content of type Object.
{	ListElement Next,Previous; // the chain pointers
	Object Content; // the content of the node
	ListClass L; // Belongs to this list
	
	public ListElement (Object content)
	// get a new Element with the content and null pointers
	{	Content=content;
		Next=Previous=null;
		L=null;
	}

	// access methods:
	public Object content ()
	{	return Content;
	}
	public ListElement next () { return Next; }
	public ListElement previous () { return Previous; }
	public void list (ListClass l) { L=l; }

	// modifying methods:
	public void content (Object o) { Content=o; }
	public void next (ListElement o) { Next=o; }
	public void previous (ListElement o) { Previous=o; }
	public ListClass list () { return L; }
}


