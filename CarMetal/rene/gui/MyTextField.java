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

import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
A TextField with a modifyable background and font.
*/

public class MyTextField extends TextField
	implements FocusListener
{	public MyTextField (String s, int n)
	{	super(s,n);
		if (Global.NormalFont!=null) setFont(Global.NormalFont);
		addFocusListener(this);
	}
	public MyTextField (String s)
	{	super(s);
		if (Global.NormalFont!=null) setFont(Global.NormalFont);
		addFocusListener(this);
	}
	public MyTextField ()
	{	if (Global.NormalFont!=null) setFont(Global.NormalFont);
		addFocusListener(this);
	}
	public void focusGained (FocusEvent e)
	{	setSelectionStart(0);
	}
	public void focusLost (FocusEvent e)
	{	setSelectionStart(0);
		setSelectionEnd(0);
	}
}
