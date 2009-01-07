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
 
 
 package rene.zirkel.graphics;

import java.io.PrintWriter;

import rene.gui.Global;

public class LatexOutput 
{	PrintWriter Out;
	double W,H;
	boolean DoubleDollar,Dollar,NoDollar;

	public LatexOutput (PrintWriter out)
	{	Out=out;
		DoubleDollar=Global.getParameter("options.doubledollar",true);
		Dollar=Global.getParameter("options.dollar",true);
		NoDollar=Global.getParameter("options.nodollar",false);
	}
	
	public void open (double w, double h, double dpi, String picfilename)
	{	W=w; H=h;
		Out.println("\\setlength{\\unitlength}{"+(2.54/dpi)+"cm}");
		Out.println("\\begin{picture}("+round(w)+","+round(h)+")");
		String s=picfilename.replace('\\','/');
		Out.println("\\put(0,0){\\includegraphics[width="+
				round(w/dpi*2.54)+"cm]{"+s+"}}");
		Out.println("%\\put(0,0){\\includegraphics[width="+
				round(w/dpi*2.54)+"cm,bb=0 0 "+((int)w)+" "+((int)h)+"]{"+s+"}}");
	}
	
	public void close ()
	{	Out.println("\\end{picture}");
		Out.close();
	}

	public boolean println (String s, double x, double y, boolean force)
	{	if (s.startsWith("$$"))
		{	if (!DoubleDollar) return false;
			s=s.substring(1);
			if (s.endsWith("$$")) s=s.substring(0,s.length()-1);
			if (!s.endsWith("$")) s=s+"$";
		}
		else if (!force && s.startsWith("$"))
		{	if (!Dollar) return false;
			if (!s.endsWith("$")) s=s+"$";
		}
		else if (!force)
		{	if (!NoDollar) return false;
		}
		// Out.println("\\put("+round(x)+","+round(H-y)+"){\\makebox(0,0)[lt]{"+s+"}}");
		Out.println("\\put("+round(x)+","+round(H-y)+"){"+s+"}");
		return true;
	}
	public boolean println (String s, double x, double y)
	{	return println(s,x,y,false);
	}
	
	public double round (double x)
	{	return Math.floor(x*1000+0.5)/1000;
	}
	
	public boolean printDollar ()
	{	return Dollar;
	}
}
