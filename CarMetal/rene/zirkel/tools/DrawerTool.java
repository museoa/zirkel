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
 
 
 package rene.zirkel.tools;

// file: Hider.java

import java.awt.event.*;

import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.*;
import rene.zirkel.graphics.Drawing;

public class DrawerTool extends ObjectConstructor
{	Drawing D;
	boolean Dragging=false;

	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	double x=zc.x(e.getX()),y=zc.y(e.getY());
		D=new Drawing();
		D.addXY(x,y);
		Dragging=true;
		zc.addDrawing(D);
		D.setColor(zc.getDefaultColor());
	}
	
	public void mouseDragged (MouseEvent e, ZirkelCanvas zc)
	{	if (!Dragging) return;
		double x=zc.x(e.getX()),y=zc.y(e.getY());
		D.addXY(x,y);
		zc.repaint();		
	}
	
	public void mouseReleased (MouseEvent e, ZirkelCanvas zc)
	{	Dragging=false;
		zc.repaint();
	}

	public void showStatus (ZirkelCanvas zc)
	{	zc.showStatus(Zirkel.name("message.draw"));
	}
	
	public void reset (ZirkelCanvas zc)
	{	zc.clearSelected();
	}

	public boolean useSmartBoard ()
	{	return false;
	}
}
