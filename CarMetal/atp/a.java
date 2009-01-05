/*****************************************************************************
*                                                                            *
*                   HotEqn Equation Viewer Basic Applet                      *
*                                                                            *
******************************************************************************
* Java Applet to view mathematical Equations provided in the LaTeX language  *
******************************************************************************

Copyright 2006 Stefan MŸller and Christian Schmid, modified by Rene Grothmann

This file is part of the HotEqn package.

    HotEqn is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; 
    HotEqn is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package atp;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

class a extends RGBImageFilter
{

    Color a;
    boolean _fldif;

    a(Color color)
    {
        _fldif = false;
        a = color;
        _fldif = false;
        super.canFilterIndexColorModel = true;
    }

    a(Color color, boolean flag)
    {
        _fldif = false;
        a = color;
        _fldif = flag;
        super.canFilterIndexColorModel = true;
    }

    public int filterRGB(int i, int j, int k)
    {
        if(_fldif)
        {
            return 0x1fff0000;
        }
        int l = k & 0xffffff;
        if(l == 0xffffff)
        {
            return l;
        } else
        {
            return 0xff000000 | a.getRGB();
        }
    }
}
