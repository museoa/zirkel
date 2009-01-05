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
 
 
 package rene.zirkel.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;

import rene.gui.Global;
import rene.gui.MyLabel;
import rene.gui.Panel3D;

public class MessageWindow extends Window
{	public MessageWindow (Frame f, String s)
	{	super(f);
		setBackground(Global.ControlBackground);
		MyLabel label=new MyLabel(s);
		label.setFont(new Font("Courier",Font.BOLD,16));
		setLayout(new BorderLayout());
		add("Center",new Panel3D(label));
		pack();
		Dimension d=f.getSize();
		Point p=f.getLocation();
		setLocation(p.x+d.width/2-getSize().width/2,
			p.y+d.height/2-getSize().height/2);
	}
}

