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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class CheckboxTranslator implements ItemListener
{   DoActionListener C;
    String S;
    public CheckboxMenuItem CB;
    public CheckboxTranslator 
        (CheckboxMenuItem cb, DoActionListener c, String s)
    {   C=c; S=s; CB=cb;
    }
    public void itemStateChanged (ItemEvent e)
    {   C.itemAction(S,CB.getState());
    }
}

/**
A CheckboxMenuItem with modifyable font.
<p>
This is to be used in DoActionListener interfaces.
*/

public class CheckboxMenuItemAction extends CheckboxMenuItem
{   public CheckboxMenuItemAction (DoActionListener c, String s, String st)
    {   super(s);
        addItemListener(new CheckboxTranslator(this,c,st));
        if (Global.NormalFont!=null) setFont(Global.NormalFont);
    }
	public CheckboxMenuItemAction (DoActionListener c, String s)
	{	this(c,s,s);
	}
}
