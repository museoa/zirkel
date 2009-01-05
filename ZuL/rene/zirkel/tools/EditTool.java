package rene.zirkel.tools;

// file: Hider.java

import java.awt.event.*;
import java.util.*;

import rene.zirkel.*;
import rene.zirkel.constructors.*;
import rene.zirkel.dialogs.*;
import rene.zirkel.objects.ConstructionObject;

public class EditTool extends ObjectConstructor
{	Vector V;
	
	public void mousePressed (MouseEvent e, ConstructionObject o, 
		ZirkelCanvas zc)
	{	double x=zc.x(e.getX()),y=zc.y(e.getY());
		if (o==null)
		{	o=zc.selectObject(e.getX(),e.getY());
			if (o==null) return;
		}
		else 
		{	V=new Vector(); V.addElement(o);
		}
		if (o.isKeep()) return;
		if (V==null) V=new Vector();
		if (!V.contains(o))
		{	V.addElement(o);
			o.setSelected(true);
			zc.repaint();
			return;
		}
		if (V.size()>1)
		{	if (e.isControlDown())
			{	EditConditionals d=new EditConditionals(zc.getFrame(),V);
			}
			else
			{	ObjectsEditDialog d=new ObjectsEditDialog(zc.getFrame(),V);
				d.setVisible(true);
			}
		}
		else if(e.isControlDown())
		{	o=(ConstructionObject)V.firstElement();
			new EditConditionals(zc.getFrame(),o);
			zc.validate();
			zc.repaint();
		}
		else
		{	o=(ConstructionObject)V.firstElement();
			String oldname=o.getName();
			o.edit(zc);
			if (!oldname.equals(o.getName())) zc.updateTexts(o,oldname);
		}
		V=null;
		zc.clearSelected();
		zc.validate();
		zc.repaint();
	}
	
	public void mouseMoved (MouseEvent e, ZirkelCanvas zc, boolean simple)
	{	zc.indicateObjects(e.getX(),e.getY(),true);
	}

	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	mousePressed(e,null,zc);
	}
	
	public void showStatus (ZirkelCanvas zc)
	{	zc.showStatus(Zirkel.name("message.edit"));
	}
	
	public void reset (ZirkelCanvas zc)
	{	zc.clearSelected();
		V=null;
	}
}
