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
 
 
 package rene.zirkel.objects;

// file: MidpointObject.java

import java.util.Enumeration;

import rene.util.xml.XmlWriter;
import rene.zirkel.Zirkel;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.Count;

public class MidpointObject extends PointObject
{	PointObject P1,P2;
	static Count N=new Count();
	
	public MidpointObject (Construction c, PointObject p1, PointObject p2)
	{	super(c,0,0);
		P1=p1; P2=p2;
		Moveable=false;
		validate();
		updateText();
	}
        
//        public void setName ()
//	{	
//            
//             if (Cn.LongNames)
//			Name=Zirkel.name("name."+getTag())+" "+getN();
//		else
//			Name=Zirkel.name("name.short."+getTag())+getN();
////             System.out.println("nom="+Name);
//             
//	}

	public String getTag () { return "Midpoint"; }
	public int getN () { return N.next(); }
	
	public void updateText ()
	{	try
		{	setText(text2(Zirkel.name("text.midpoint"),P1.getName(),P2.getName()));
		}
		catch (Exception e) {}
	}
	public void validate ()
	{	if (!P1.valid() || !P2.valid()) Valid=false;
		else
		{	Valid=true;
                        setXY((P1.getX()+P2.getX())/2,(P1.getY()+P2.getY())/2);
		}
	}
	public void printArgs (XmlWriter xml)
	{	xml.printArg("first",P1.getName());
		xml.printArg("second",P2.getName());
		printType(xml);
	}

	public Enumeration depending ()
	{	super.depending();
		DL.add(P1); DL.add(P2);
		return DL.elements();
	}

	public void translate ()
	{	P1=(PointObject)P1.getTranslation();
		P2=(PointObject)P2.getTranslation();
	}

}
