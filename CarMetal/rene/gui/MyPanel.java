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

import java.awt.Graphics;
import javax.swing.JPanel;

public class MyPanel extends JPanel
{	public MyPanel ()
	{	if (Global.ControlBackground!=null)
			setBackground(Global.ControlBackground);
		repaint();
	}
	public void paint (Graphics g)
	{	super.paint(g);
		getToolkit().sync();
	}
}
