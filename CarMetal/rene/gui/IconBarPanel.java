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

import java.awt.*;

/**
 * @author Rene
 * A panel for two components. The left one uses its width. 
 *
 */
public class IconBarPanel
	extends MyPanel
{	Component C1,C2;
	int IX=0,IY=0;

	public IconBarPanel (Component c1, Component c2)
	{	C1=c1; C2=c2;
		add(C1);
		add(C2);
	}

	public void doLayout ()
	{	int w=C1.getPreferredSize().width;
		C1.setSize(w,getSize().height-2*IY);
		C1.setLocation(IX,IY);
		C2.setSize(getSize().width-3*IX-w,getSize().height-2*IX);
		C2.setLocation(w+2*IX,IY);
		C1.doLayout();
		C2.doLayout();
	}

	public Dimension getPreferredSize ()
	{	Dimension d1=C1.getPreferredSize(),d2=C2.getPreferredSize();
		return new Dimension(d1.width+d2.width,
			Math.max(d1.height,d2.height));
	}

	public void setInsets (int x, int y)
	{	IX=x; IY=y;
	}

	public static void main(String[] args)
	{}
}
