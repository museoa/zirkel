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

import java.util.Enumeration;
import java.util.Vector;

import rene.util.list.ListElement;
import rene.util.list.Tree;
import rene.util.parser.StringParser;

public class XmlTree extends Tree
	implements Enumeration
{	public XmlTree (XmlTag t)
	{	super(t);
	}
	public XmlTag getTag ()
	{	return (XmlTag)content();
	}
	public XmlTree xmlFirstContent ()
	{	if (firstchild()!=null) return (XmlTree)firstchild();
		else return null;
	}
	public boolean isText ()
	{	if (!haschildren()) return true;
		if (firstchild()!=lastchild()) return false;
		XmlTree t=(XmlTree)firstchild();
		XmlTag tag=t.getTag();
		if (!(tag instanceof XmlTagText)) return false;
		return true;
	}
	public String getText ()
	{	if (!haschildren()) return "";
		XmlTree t=(XmlTree)firstchild();
		XmlTag tag=t.getTag();
		return ((XmlTagText)tag).getContent();
	}
	ListElement Current;
	public Enumeration getContent ()
	{	Current=children().first();
		return this;
	}
	public boolean hasMoreElements ()
	{	return Current!=null;
	}
	public Object nextElement ()
	{	if (Current==null) return null;
		XmlTree c=(XmlTree)(Current.content());
		Current=Current.next();
		return c;
	}
	public String parseComment ()
		throws XmlReaderException
	{	StringBuffer s=new StringBuffer();
		Enumeration e=getContent();
		while (e.hasMoreElements())
		{	XmlTree tree=(XmlTree)e.nextElement();
			XmlTag tag=tree.getTag();
			if (tag.name().equals("P"))
			{	if (!tree.haschildren()) s.append("\n");
				else
				{	XmlTree h=tree.xmlFirstContent();
					String k=((XmlTagText)h.getTag()).getContent();
					k=k.replace('\n',' ');
					StringParser p=new StringParser(k);
					Vector v=p.wraplines(1000);
					for (int i=0; i<v.size(); i++)
					{	s.append((String)v.elementAt(i));
						s.append("\n");
					}
				}
			}
			else if (tag instanceof XmlTagText)
			{	String k=((XmlTagText)tag).getContent();
				StringParser p=new StringParser(k);
				Vector v=p.wraplines(1000);
				for (int i=0; i<v.size(); i++)
				{	s.append((String)v.elementAt(i));
					s.append("\n");
				}			
			}
			else
				throw new XmlReaderException("<"+tag.name()+"> not proper here.");
		}
		return s.toString();
	}	
}
