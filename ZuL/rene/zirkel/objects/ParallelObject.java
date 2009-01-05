package rene.zirkel.objects;

// file: ParallelObject.java

import java.util.Enumeration;

import rene.util.xml.XmlWriter;
import rene.zirkel.*;
import rene.zirkel.construction.*;

public class ParallelObject extends PrimitiveLineObject
{	protected PrimitiveLineObject L;
	static Count N=new Count();
	public ParallelObject (Construction c, PrimitiveLineObject l, PointObject p)
	{	super(c);
		P1=p; L=l;
		validate();
		updateText();
	}
	public String getTag () { return "Parallel"; }
	public int getN () { return N.next(); }
	
	public void updateText ()
	{	setText(text2(Zirkel.name("text.parallel"),P1.getName(),L.getName()));
	}
	public void validate ()
	{	if (!P1.valid() || !L.valid()) Valid=false;
		else if (L instanceof SegmentObject &&
				((SegmentObject)L).getLength()==0)
		{	Valid=false;
		}
		else
		{	Valid=true;
			X1=P1.getX(); Y1=P1.getY();
			DX=L.getDX(); DY=L.getDY();
		}
	}
	public void printArgs (XmlWriter xml)
	{	xml.printArg("point",P1.getName());
		xml.printArg("line",L.getName());
		super.printArgs(xml);
	}	
	
	public Enumeration depending ()
	{	super.depending();
		return depset(P1,L);
	}

	public void translate ()
	{	P1=(PointObject)P1.getTranslation();
		L=(PrimitiveLineObject)L.getTranslation();
	}

	public boolean contains (PointObject p)
	{	return p==P1;
	}

	public boolean hasUnit ()
	{	return false;
	}

}
