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

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Label;

/**
A Label with a midifyable Font.
*/

public class MyLabel extends javax.swing.JLabel
{   public MyLabel (String s)
    {   super(s);
        if (Global.NormalFont!=null) setFont(Global.NormalFont);
    }
    public MyLabel (String s, int allign)
    {   super(s,allign);
        if (Global.NormalFont!=null) setFont(Global.NormalFont);
    }
    /**
    This is for Java 1.2 on Windows.
    */
    public void paint (Graphics g)
    {	Container c=getParent();
    	if (c!=null) setBackground(c.getBackground());
    	super.paint(g);
    }
    
    public void setAlignment(int align) {
        if (align==Label.CENTER){
            this.setAlignmentX(0.5F);
        }
    	}
}
