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

// file: RayConstructor.java

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.*;
import rene.zirkel.construction.*;
import rene.zirkel.objects.*;

public class RayConstructor extends LineConstructor
{	public ConstructionObject create (Construction c, PointObject p1, PointObject p2)
	{	return new RayObject(c,p1,p2);
	}
	public void showStatus (ZirkelCanvas zc)
	{	if (P1==null) zc.showStatus(
			Zirkel.name("message.ray.first","Ray: Set the root point!"));
		else zc.showStatus(
			Zirkel.name("message.ray.second","Ray: Set the second point!"));
	}
	public boolean construct (XmlTree tree, Construction c)
		throws ConstructionException
	{	if (!testTree(tree,"Ray")) return false;
		XmlTag tag=tree.getTag();
		if (!tag.hasParam("from") || !tag.hasParam("to"))
			throw new ConstructionException("Ray points missing!");
		try
		{	PointObject p1=(PointObject)c.find(tag.getValue("from")); 
			PointObject p2=(PointObject)c.find(tag.getValue("to"));
			RayObject o=new RayObject(c,p1,p2);
			if (tag.hasParam("partial")) o.setPartial(true);
			setName(tag,o);
			set(tree,o);
			c.add(o);
			setConditionals(tree,c,o);
		}
		catch (ConstructionException e)
		{	throw e;
		}
		catch (Exception e)
		{	e.printStackTrace();
			throw new ConstructionException("Ray points illegal!");
		}
		return true;
	}
	
	public String getPrompt ()
	{	return Zirkel.name("prompt.ray");
	}
	
	public String getTag () { return "Ray"; }
	public void construct (Construction c, 
		String name, String params[], int nparams)
		throws ConstructionException
	{	if (nparams!=2)
			throw new ConstructionException(Zirkel.name("exception.nparams"));
		ConstructionObject
			P1=c.find(params[0]);
		if (P1==null)
			throw new ConstructionException(Zirkel.name("exception.notfound")+" "+
				params[0]);
		ConstructionObject
			P2=c.find(params[1]);
		if (P2==null)
			throw new ConstructionException(Zirkel.name("exception.notfound")+" "+
				params[1]);
		if (!(P1 instanceof PointObject))
			throw new ConstructionException(Zirkel.name("exception.type")+" "+
				params[0]);
		if (!(P2 instanceof PointObject))
			throw new ConstructionException(Zirkel.name("exception.type")+" "+
				params[1]);
		RayObject s=new RayObject(c,(PointObject)P1,(PointObject)P2);
		c.add(s);
		s.setDefaults();
		if (!name.equals("")) s.setNameCheck(name);
	}

}
