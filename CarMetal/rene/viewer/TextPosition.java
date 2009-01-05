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
 
 
 package rene.viewer;

import rene.util.list.ListElement;

class TextPosition
{	ListElement L;
	int LCount;
	int LPos;
	public TextPosition (ListElement l, int lcount, int lpos)
	{	L=l; LCount=lcount; LPos=lpos;
	}
	boolean equal (TextPosition p)
	{	return p.LCount==LCount && p.LPos==LPos;
	}
	boolean before (TextPosition p)
	{	return p.LCount>LCount ||
			(p.LCount==LCount && p.LPos>LPos);
	}
	void oneleft ()
	{	if (LPos>0) LPos--;
	}
}

