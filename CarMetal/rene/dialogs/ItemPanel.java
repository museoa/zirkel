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
 
 
 package rene.dialogs;

import javax.swing.JPanel;
import java.util.Vector;

import rene.gui.DoActionListener;

public class ItemPanel extends JPanel
	implements DoActionListener
{	public void display (ItemEditorElement e)
	{
	}
	public String getName ()
	{	return "";
	}
	public void setName (String name)
	{
	}
	public ItemEditorElement getElement ()
	{	return null;
	}
	public void newElement ()
	{
	}
	public void help ()
	{
	}
	public void doAction (String o)
	{
	}
	public void itemAction (String o, boolean flag)
	{
	}
	/**
	Called, whenever an item is redefined.
	@param v The vector of KeyboardItem.
	@param item The currently changed item number.
	*/
	public void notifyChange (Vector v, int item)
	{
	}
	
	/**
	Called, when the extra Button was pressed.
	@v The vector of KeyboardItem.
	@return If the panel should be closed immediately.
	*/
	public boolean extra (Vector v)
	{	return false;
	}
}
