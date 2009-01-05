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
A TextField with a modifyable background and font. This is used in
DoActionListener interfaces.
*/

public class TextFieldAction extends TextField
	implements FocusListener
{	protected ActionTranslator T;
	String S;
	public TextFieldAction (DoActionListener t, String name, String s)
	{	super(s);
		S=s;
		if (Global.NormalFont!=null) setFont(Global.NormalFont);
		if (Global.Background!=null) setBackground(Global.Background);
		T=new ActionTranslator(t,name);
		addActionListener(T);
		addFocusListener(this);
	}
	public TextFieldAction (DoActionListener t, String name)
	{	if (Global.NormalFont!=null) setFont(Global.NormalFont);
		if (Global.Background!=null) setBackground(Global.Background);
		T=new ActionTranslator(t,name);
		addActionListener(T);
		addFocusListener(this);
	}
	public TextFieldAction (DoActionListener t, String name, int n)
	{	super(n);
		if (Global.NormalFont!=null) setFont(Global.NormalFont);
		if (Global.Background!=null) setBackground(Global.Background);
		T=new ActionTranslator(t,name);
		addActionListener(T);
		addFocusListener(this);
	}
	public TextFieldAction (DoActionListener t, String name, String s, int n)
	{	super(s,n);
		S=s;
		if (Global.NormalFont!=null) setFont(Global.NormalFont);
		if (Global.Background!=null) setBackground(Global.Background);
		T=new ActionTranslator(t,name);
		addActionListener(T);
		addFocusListener(this);
	}
	public void triggerAction ()
	{	T.trigger();
	}
	public void focusGained (FocusEvent e)
	{	setSelectionStart(0);
	}
	public void focusLost (FocusEvent e)
	{	setSelectionStart(0);
		setSelectionEnd(0);
	}
	public void setText (String s)
	{	super.setText(s);
		S=s;
	}
	public String getOldText ()
	{	return S;
	}
	public boolean isChanged ()
	{	return !S.equals(getText());
	}
}
