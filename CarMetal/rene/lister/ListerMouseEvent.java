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
 
 
 package rene.lister;

import java.awt.event.*;

public class ListerMouseEvent
	extends ActionEvent
{	static int ID=0;
	MouseEvent E;
	
	public ListerMouseEvent (Object o, String name, MouseEvent e)
	{	super(o,ID++,name);
		E=e;
	}
	
	public MouseEvent getEvent ()
	{	return E;
	}
	
	public String getName ()
	{	return E.paramString();
	}

	public boolean rightMouse ()
	{	return E.isMetaDown();
	}
	
	public int clickCount ()
	{	return E.getClickCount();
	}
}
