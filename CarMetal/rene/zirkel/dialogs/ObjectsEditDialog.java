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
 
 
 package rene.zirkel.dialogs;

// file: PointObject.java

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.IconBar;
import rene.gui.IconBarListener;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.zirkel.Zirkel;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;

/**
 * @author Rene
 *
 * Dialog to edit multiple objects at once.
 * Has icons with unset state.
 *
 */
public class ObjectsEditDialog extends HelpCloseDialog
	implements IconBarListener
{	protected TextField Name,Text;
	protected IconBar ColorIB,ThicknessIB,TypeIB,IB;
	protected Frame F;
	Button OK;
	Vector V;
	
	public ObjectsEditDialog (Frame f, Vector v)
	{	super(f,Zirkel.name("objectsedit.title"),true);
		F=f;
		V=v;
		setLayout(new BorderLayout());
		
		// edit options:
		JPanel center=new MyPanel();
		center.setLayout(new GridLayout(0,1));

		ColorIB=new IconBar(F);
		ColorIB.addToggleGroupLeft("color",6);
		Enumeration e=V.elements();
		int col=((ConstructionObject)e.nextElement()).getColorIndex();
		boolean unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).getColorIndex()
				!=col)
			{	unique=false; break;
			}
		}
		if (unique) ColorIB.setState("color"+col,true);
		center.add(ColorIB);

		ThicknessIB=new IconBar(F);
		ThicknessIB.addToggleGroupLeft("thickness",4);
		e=V.elements();
		int th=((ConstructionObject)e.nextElement()).getColorType();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).getColorType()
				!=th)
			{	unique=false; break;
			}
		}
		if (unique) ThicknessIB.setState("thickness"+th,true);
		center.add(ThicknessIB);

		TypeIB=new IconBar(F);
		TypeIB.addToggleGroupLeft("type",6);
		e=V.elements();
		unique=true;
		try
		{	int ty=((PointObject)e.nextElement()).getType();
			while (e.hasMoreElements())
			{	if (((PointObject)e.nextElement()).getType()
					!=ty)
				{	unique=false; break;
				}
			}
			if (unique) TypeIB.setState("thickness"+ty,true);
			center.add(TypeIB);
		}
		catch (ClassCastException ex) { TypeIB=null; }

		IB=new IconBar(F);
		IB.setIconBarListener(this);
		IB.addToggleLeft("hide");
		e=V.elements();
		boolean flag=((ConstructionObject)e.nextElement()).isHidden();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).isHidden()
				!=flag)
			{	unique=false; break;
			}
		}
		if (unique) IB.setState("hide",flag);
		else IB.unset("hide");
		IB.addSeparatorLeft();
		IB.addToggleLeft("showname");
		e=V.elements();
		flag=((ConstructionObject)e.nextElement()).showName();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).showName()
				!=flag)
			{	unique=false; break;
			}
		}
		if (unique) IB.setState("showname",flag);
		else IB.unset("showname");
		IB.addToggleLeft("showvalue");
		e=V.elements();
		flag=((ConstructionObject)e.nextElement()).showValue();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).showValue()
				!=flag)
			{	unique=false; break;
			}
		}
		if (unique) IB.setState("showvalue",flag);
		else IB.unset("showvalue");
		IB.addSeparatorLeft();
		IB.addToggleLeft("large");
		e=V.elements();
		flag=((ConstructionObject)e.nextElement()).isLarge();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).isLarge()
				!=flag)
			{	unique=false; break;
			}
		}
		if (unique) IB.setState("large",flag);
		else IB.unset("large");
		IB.addToggleLeft("bold");
		e=V.elements();
		flag=((ConstructionObject)e.nextElement()).isBold();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).isBold()
				!=flag)
			{	unique=false; break;
			}
		}
		if (unique) IB.setState("bold",flag);
		else IB.unset("bold");
		IB.addSeparatorLeft();
		IB.addToggleLeft("isback");
		e=V.elements();
		flag=((ConstructionObject)e.nextElement()).isBack();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).isBack()
				!=flag)
			{	unique=false; break;
			}
		}
		if (unique) IB.setState("isback",flag);
		else IB.unset("isback");
		center.add(IB);
		
		add("Center",new Panel3D(center));
		
		// button panel:
		JPanel p=new MyPanel();
		OK=new ButtonAction(this,Zirkel.name("edit.ok"),"OK");
		p.add(OK);
		p.add(new ButtonAction(this,Zirkel.name("edit.cancel"),"Close"));
		addHelp(p,"edit");
		
		add("South",new Panel3D(p));
		pack();
		center(f);
        ColorIB.forceRepaint();
	}

	public void focusGained (FocusEvent e)
	{	OK.requestFocus();
	}
	
	public void doAction (String o)
	{	if (o.equals("OK"))
		{	Enumeration e=V.elements();
			while (e.hasMoreElements())
			{	ConstructionObject O=(ConstructionObject)e.nextElement();
				if (IB.isSet("hide")) O.setHidden(IB.getState("hide"));
				O.setColor(ColorIB.getToggleState("color"));
				O.setColorType(ThicknessIB.getToggleState("thickness"));
				if (IB.isSet("showname")) O.setShowName(IB.getState("showname"));
				if (IB.isSet("isback")) O.setBack(IB.getState("isback"));
				if (IB.isSet("showvalue")) O.setShowValue(IB.getState("showvalue"));
				if (IB.isSet("large")) O.setLarge(IB.getState("large"));
				if (IB.isSet("bold")) O.setBold(IB.getState("bold"));
				if (TypeIB!=null && O instanceof PointObject &&
						TypeIB.getToggleState("type")>=0)
					((PointObject)O).setType(TypeIB.getToggleState("type"));
			}
			doclose();
		}
		else super.doAction(o);
	}

	public void iconPressed (String o)
	{
	}
}
