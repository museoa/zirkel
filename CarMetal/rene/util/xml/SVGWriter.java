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

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class SVGWriter extends XmlWriter
{	int W,H;

	public SVGWriter(PrintWriter o, String enc, int w, int h)
	{	super(o);
		printEncoding(enc);
		W=w; H=h;
		startTagStart("svg");
		printArg("width",""+w);
		printArg("height",""+h);
		startTagEndNewLine();
	}

	public SVGWriter(PrintWriter o)
	{	super(o);
	}
	
	public void startSVG (int w, int h)
	{	printEncoding("utf-8");
		Out.println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"");
		Out.println("\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");
		startTagStart("svg");
		printArg("xmlns","http://www.w3.org/2000/svg");
		printArg("width",""+w);
		printArg("height",""+h);
		startTagEndNewLine();
	}
	
	public void close ()
	{	endTag("svg");
		super.close();
	}
	
	public void coord (int x, int y)
	{	printArg("x",""+x);
		printArg("y",""+y);
	}

	public void text (String text, int x, int y)
	{	startTagStart("text");
		coord(x,y);
		startTagEnd();
		print(text);
		endTagNewLine("text");
	}
	
	public static void main (String args[])
		throws Exception
	{	SVGWriter out=new SVGWriter(
			new PrintWriter(new FileOutputStream("test.svg")),
			"",300,300);
		out.text("Hallo Welt",10,95);
		out.startTagStart("path");
		out.printArg("d","M 150 150 A 50 50 0 1 0 100 200");
		out.printArg("style","fill:none;stroke-width:1;stroke:black");
		out.finishTagNewLine();
		out.close();
	}
}
