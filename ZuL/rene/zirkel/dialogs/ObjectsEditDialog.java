package rene.zirkel.dialogs;

// file: PointObject.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import rene.dialogs.ColorEditor;
import rene.gui.*;
import rene.zirkel.*;
import rene.zirkel.objects.*;

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
	
	boolean equalcolor (Color c1, Color c2)
	{	return c1.getRed()==c2.getRed() && c1.getBlue()==c2.getBlue()
			&& c1.getGreen()==c2.getGreen();
	}

	public ObjectsEditDialog (Frame f, Vector v)
	{	super(f,Zirkel.name("objectsedit.title"),true);
		F=f;
		V=v;
		setLayout(new BorderLayout());
		
		// edit options:
		Panel center=new MyPanel();
		center.setLayout(new GridLayout(0,1));

		ColorIB=new IconBar(F);
		ColorIB.setIconBarListener(this);
		ColorIB.addToggleGroupLeft("color",6);
		Enumeration e=V.elements();
		int col=((ConstructionObject)e.nextElement()).getColorIndex(true);
		boolean unique=true;
		while (e.hasMoreElements())
		{	ConstructionObject o=(ConstructionObject)e.nextElement();
			if (o.getColorIndex(true)!=col || o.hasUserColor()) 
			{	unique=false; break;
			}
		}
		if (unique)
		{	ColorIB.setState("color"+col,true);
		}
		else
		{	ColorIB.unset("color0");
		}
		ColorIB.addSeparatorLeft();
		ColorIB.addColoredIconLeft("colors",Color.black);
		unique=true;
		e=V.elements();
		Color cu=((ConstructionObject)e.nextElement()).getUserColor();
		if (cu==null) unique=false;
		else
			while (e.hasMoreElements())
			{	ConstructionObject o=(ConstructionObject)e.nextElement();
				if (!o.hasUserColor() || !equalcolor(o.getUserColor(),cu))
				{	unique=false; break;
				}
			}
		if (unique)
		{	ColorIB.setColoredIcon("colors",cu);
		}
		else
		{	ColorIB.unset("colors");
		}
		center.add(ColorIB);

		ThicknessIB=new IconBar(F);
		ThicknessIB.addToggleGroupLeft("thickness",4);
		e=V.elements();
		int th=((ConstructionObject)e.nextElement()).getColorType();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).getColorType()!=th)
			{	unique=false; break;
			}
		}
		if (unique) 
		{	ThicknessIB.setState("thickness"+th,true);
		}
		else
		{	ThicknessIB.unset("thickness0");
		}
		
		ThicknessIB.addSeparatorLeft();
		ThicknessIB.addToggleLeft("fillbackground");
		e=V.elements();
		boolean fillbackground=((ConstructionObject)e.nextElement()).isFillBackground();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).isFillBackground()
				!=fillbackground)
			{	unique=false; break;
			}
		}
		if (unique && fillbackground) ThicknessIB.setState("fillbackground",true);
		else ThicknessIB.unset("fillbackground");
		
		center.add(ThicknessIB);

		TypeIB=new IconBar(F);
		TypeIB.addToggleGroupLeft("type",6);
		TypeIB.addSeparatorLeft();
		TypeIB.addToggleLeft("largepoint");
		e=V.elements();
		unique=true;
		boolean uniquelarge=true;
		try
		{	ConstructionObject o=(ConstructionObject)e.nextElement();
			int ty=((PointObject)o).getType();
			boolean large=((PointObject)o).isLarge();
			while (e.hasMoreElements())
			{	o=(ConstructionObject)e.nextElement();
				if (((PointObject)o).getType()!=ty)
				{	unique=false;
				}
				if (((PointObject)o).isLarge()!=large)
				{	uniquelarge=false;
				}
			}
			if (unique) TypeIB.setState("type"+ty,true);
			if (uniquelarge) TypeIB.setState("largepoint",large);
			else TypeIB.unset("largepoint");
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
		IB.addToggleLeft("frac");
		e=V.elements();
		flag=((ConstructionObject)e.nextElement()).isFrac();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).isFrac()
				!=flag)
			{	unique=false; break;
			}
		}
		if (unique) IB.setState("frac",flag);
		else IB.unset("frac");
		IB.addToggleLeft("quad");
		e=V.elements();
		flag=((ConstructionObject)e.nextElement()).isQuad();
		unique=true;
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).isQuad()
				!=flag)
			{	unique=false; break;
			}
		}
		if (unique) IB.setState("quad",flag);
		else IB.unset("quad");
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
		Panel p=new MyPanel();
		OK=new ButtonAction(this,Zirkel.name("edit.ok"),"OK");
		p.add(OK);
		p.add(new ButtonAction(this,Zirkel.name("edit.cancel"),"Close"));
		addHelp(p,"edit");
		
		add("South",new Panel3D(p));
		
		pack();
		center(f);
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
				O.setColorType(ThicknessIB.getToggleState("thickness"));
				if (ColorIB.isSet("color0"))
				{	int cs=ColorIB.getToggleState("color");
					if (cs>=0) O.setColor(ColorIB.getToggleState("color"));
					else O.setColor(ColorIB.getColoredIcon("colors"));
				}
				if (ThicknessIB.isSet("thickness0")) O.setColorType(ThicknessIB.getToggleState("thickness"));
				if (ThicknessIB.isSet("fillbackground")) O.setFillBackground(ThicknessIB.getState("fillbackground"));
				if (IB.isSet("showname")) O.setShowName(IB.getState("showname"));
				if (IB.isSet("isback")) O.setBack(IB.getState("isback"));
				if (IB.isSet("showvalue")) O.setShowValue(IB.getState("showvalue"));
				if (IB.isSet("large")) O.setLarge(IB.getState("large"));
				if (IB.isSet("bold")) O.setBold(IB.getState("bold"));
				if (IB.isSet("frac") && O.canuseFrac()) O.setFrac(IB.getState("frac"));
				if (IB.isSet("quad") && O.canuseQuad()) O.setQuad(IB.getState("quad"));
				if (TypeIB!=null && O instanceof PointObject &&
						TypeIB.getToggleState("type")>=0)
				{	((PointObject)O).setType(TypeIB.getToggleState("type"));
				}
				if (TypeIB!=null && O instanceof PointObject &&
						TypeIB.isSet("largepoint"))
				{	((PointObject)O).setLarge(TypeIB.getState("largepoint"));
				}
			}
			doclose();
		}
		else super.doAction(o);
	}

	public void iconPressed (String o)
	{	if (o.equals("colors"))
		{	ColorEditor ce=new ColorEditor(F,"colors.recent",Color.black,
				ZirkelFrame.Colors,
				ObjectEditDialog.UserC);
			ce.center(F);
			ce.setVisible(true);
			if (!ce.isAborted())
			{	ColorIB.setColoredIcon("colors",ce.getColor());
				Global.setParameter("colors.recent",ce.getColor());
				ColorIB.unselect("color0");
				ColorIB.set("colors",true);
				ObjectEditDialog.rememberUserC();
			}
		}
		else if (o.startsWith("color"))
		{	ColorIB.set("colors",false);
		}

	}
}
