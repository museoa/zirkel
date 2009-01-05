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
 
 
 package rene.gui;

import java.awt.CheckboxMenuItem;
import java.util.Vector;

class CheckboxMenuElement
{	public String Tag;
	public CheckboxMenuItem Item;
	public CheckboxMenuElement (CheckboxMenuItem i, String tag)
	{	Item=i; Tag=tag;
	}
}

public class CheckboxMenu
{	Vector V;
	public CheckboxMenu ()
	{	V=new Vector();
	}
	public void add (CheckboxMenuItem i, String tag)
	{	V.addElement(new CheckboxMenuElement(i,tag));
	}
	public void set (String tag)
	{	int i;
		for (i=0; i<V.size(); i++)
		{	CheckboxMenuElement e=
				(CheckboxMenuElement)V.elementAt(i);
			if (tag.equals(e.Tag)) e.Item.setState(true);
			else e.Item.setState(false);
		}
	}
}
