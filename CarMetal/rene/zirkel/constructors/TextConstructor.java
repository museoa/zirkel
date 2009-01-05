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
 
 
 package rene.zirkel.constructors;

// file: PointConstructor.java

import java.awt.event.*;

import rene.util.xml.*;
import rene.zirkel.*;
import rene.zirkel.construction.*;
import rene.zirkel.expression.*;
import rene.zirkel.objects.*;

public class TextConstructor extends ObjectConstructor
{	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	double x=zc.x(e.getX()),y=zc.y(e.getY());
		TextObject p=new TextObject(zc.getConstruction(),x,y);
		zc.addObject(p);
		p.edit(zc);
		p.setDefaults();
		zc.repaint();
	}
	
	public boolean waitForPoint ()
	{	return false;
	}
	
	public void showStatus (ZirkelCanvas zc)
	{	zc.showStatus(Zirkel.name("message.text"));
	}
	
	public boolean construct (XmlTree tree, Construction c)
		throws ConstructionException
	{	if (!testTree(tree,"Text")) return false;
		XmlTag tag=tree.getTag();
		if (!tag.hasParam("x") || !tag.hasParam("y"))
			throw new ConstructionException("Point coordinates missing!");
		TextObject p=new TextObject(c,0,0);
		double x,y;
		try
		{	x=new Expression(tag.getValue("x"),c,p).getValue();
			y=new Expression(tag.getValue("y"),c,p).getValue();
			p.move(x,y);
		}
		catch (Exception e) {}
		setName(tag,p);
		set(tree,p);
		c.add(p);
		setConditionals(tree,c,p);
		p.setLines(p.getText());
		if (tag.hasParam("fixed"))
		{	p.setFixed(tag.getValue("x"),tag.getValue("y"));
		}
		return true;
	}


	public void reset (ZirkelCanvas zc)
	{	super.reset(zc);
		showStatus(zc);
	}
}
