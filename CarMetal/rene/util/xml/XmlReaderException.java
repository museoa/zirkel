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
 
 
 package rene.util.xml;

public class XmlReaderException extends Exception
{	String Line;
	int Pos;
	String S;
	public XmlReaderException (String s, String line, int pos)
	{	super(s);
		S=s;
		Line=line;
		Pos=pos;
	}
	public XmlReaderException (String s)
	{	this(s,"",0);
	}
	public String getLine ()
	{	return Line;
	}
	public int getPos ()
	{	return Pos;
	}
	public String getText ()
	{	return S;
	}
}
