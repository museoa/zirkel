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
A node with a list of children trees.
*/

public class Tree
{	ListClass Children; // list of children, each with Tree as content
	Object Content; // content
	ListElement Le; // the listelement containing the tree
	Tree Parent; // the parent tree

	/** initialize with an object and no children */
	public Tree (Object o)
	{	Content=o;
		Children=new ListClass();
		Le=null; Parent=null;
	}

	/** add a child tree */
	public void addchild (Tree t)
	{	ListElement p=new ListElement(t);
		Children.append(p);
		t.Le=p; t.Parent=this;
	}

	/** insert a child tree */
	public void insertchild (Tree t)
	{	if (!haschildren()) // simple case
		{	addchild(t); return;
		}
		// give t my children
		t.Children=Children;
		// make t my only child
		Children=new ListClass();
		ListElement p=new ListElement(t);
		Children.append(p);
		t.Le=p; t.Parent=this;
		// fix the parents of all grandchildren
		ListElement le=t.Children.first();
		while (le!=null)
		{	Tree h=(Tree)(le.content());
			h.Parent=t;
			le=le.next();
		}
	}

	/** remove the specific child tree (must be in the tree!!!) */
	public void remove (Tree t)
	{	if (t.parent()!=this) return;
		Children.remove(t.Le);
	}

	/** remove all children */
	public void removeall ()
	{	Children.removeall();
	}

	// Access Methods:
	public boolean haschildren () { return Children.first()!=null; }
	public Tree firstchild () { return (Tree)Children.first().content(); }
	public Tree lastchild () { return (Tree)Children.last().content(); }
	public Tree parent () { return Parent; }
	public ListClass children () { return Children; }
	public Object content () { return Content; }
	public void content (Object o) { Content=o; }
	public ListElement listelement () { return Le; }
}

