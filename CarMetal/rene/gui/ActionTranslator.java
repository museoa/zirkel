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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
A translator for Actions.
*/

public class ActionTranslator implements ActionListener
{   String Name;
    DoActionListener C;
    ActionEvent E;
    public ActionTranslator (DoActionListener c, String name)
    {   Name=name; C=c;
    }
    public void actionPerformed (ActionEvent e)
    {   E=e;
    	C.doAction(Name);
    }
    public void trigger ()
    {	C.doAction(Name);
    }
}
