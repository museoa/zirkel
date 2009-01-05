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


class c
{

    public int _flddo;
    public int _fldif;
    public int a;

    public c(int i, int j, int k)
    {
        _flddo = i;
        _fldif = j;
        a = k;
    }

    public c()
    {
        _flddo = 0;
        _fldif = 0;
        a = 0;
    }
}