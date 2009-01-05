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

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

class Wheel
implements MouseWheelListener
{	WheelListener V;

	public Wheel (WheelListener v)
	{	V=v;
	}
	
	public void mouseWheelMoved (MouseWheelEvent e)
	{	if (e.getScrollType()==MouseWheelEvent.WHEEL_BLOCK_SCROLL)
		{	if (e.getWheelRotation()>0)
				V.pageUp();
			else
				V.pageDown();
		}
		else
		{	int n=e.getScrollAmount();
			if (e.getWheelRotation()>0) V.up(n);
			else V.down(n);
		}
	}
}