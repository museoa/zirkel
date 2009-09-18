package rene.zirkel.tools;

// file: Hider.java

import java.awt.event.MouseEvent;

import rene.zirkel.*;
import rene.zirkel.constructors.*;
import rene.zirkel.construction.*;
import rene.zirkel.objects.*;

public class TicksTool extends ObjectConstructor 
	implements Selector
{	boolean Enforce=false,Started=false;
	
	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	ConstructionObject o=zc.selectWithSelector(e.getX(),e.getY(),this);
		if (o==null) return;
		if (o.isKeep()) return;
		if (!o.canHaveTicks()) return;
		int k=o.getTicks()+1;
		if (k>3) k=0;
		o.setTicks(k);
	}
	
	public void mouseMoved (MouseEvent e, ZirkelCanvas zc, boolean simple)
	{	zc.indicateWithSelector(e.getX(),e.getY(),this);
	}

	public void showStatus (ZirkelCanvas zc)
	{	zc.showStatus(Zirkel.name("message.ticks"));
	}
	
	public boolean useSmartBoard ()
	{	return false;
	}
	
	public void reset (ZirkelCanvas zc)
	{	Started=false;
	}

	public void close()
	{
	}

	public long getTimeout()
	{
		return 0;
	}

	public boolean isAdmissible(ZirkelCanvas zc, ConstructionObject o)
	{	return o.canHaveTicks();
	}
}
