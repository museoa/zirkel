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

import java.awt.Color;

public class StringElement
	implements Element
{	public String S;
	public Color C;
	
	public StringElement (String s, Color c)
	{	S=s; C=c;
	}
	
	public StringElement (String s)
	{	this(s,null);
	}
	
	public String getElementString ()
	{	return S;
	}
	
	public String getElementString (int state)
	{	return S;
	}
	
	public Color getElementColor ()
	{	return C;
	}
}
