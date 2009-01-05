package rene.zirkel.tools;

// file: ReplacerTool.java

import java.awt.event.MouseEvent;

import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.*;
import rene.zirkel.objects.ConstructionObject;

public class ReplacerTool extends ObjectConstructor
	implements Selector
{	ConstructionObject First;
	
	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	ConstructionObject o=zc.selectWithSelector(e.getX(),e.getY(),this);
		if (o==null) return;
		if (First==null)
		{	First=o; showStatus(zc); return;
		}
		zc.replace(First,o);
		reset(zc);
		zc.getConstruction().needsOrdering();
		zc.validate();
		zc.repaint();
	}
	
	public void mouseMoved (MouseEvent e, ZirkelCanvas zc, boolean simple)
	{	zc.indicateWithSelector(e.getX(),e.getY(),this);
	}

	public void showStatus (ZirkelCanvas zc)
	{	if (First==null) zc.showStatus(Zirkel.name("message.replace"));
		else zc.showStatus(Zirkel.name("message.replace.second"));
	}
	
	public boolean useSmartBoard ()
	{	return false;
	}
	
	public void reset (ZirkelCanvas zc)
	{	First=null;
	}

	public boolean isAdmissible (ZirkelCanvas zc, ConstructionObject o) 
	{	if (First==null) return true;
		return First.canBeReplacedBy(o) &&
			!zc.getConstruction().dependsOn(o,First);
	}
}
