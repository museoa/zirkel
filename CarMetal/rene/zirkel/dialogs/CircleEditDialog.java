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

// file: PrimitiveCircleObject.java

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.FocusEvent;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.Global;
import rene.gui.IconBar;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.MyTextField;
import rene.gui.TextFieldAction;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.objects.CircleObject;
import rene.zirkel.objects.FixedCircleObject;
import rene.zirkel.objects.PrimitiveCircleObject;

public class CircleEditDialog extends ObjectEditDialog
{	TextField Length,BoundA,BoundB;
	Checkbox Fixed,Arc;
	IconBar IC;
	ZirkelCanvas ZC;
	Button RangeButton,KillRangeButton,SetButton;
	
	static Color Colors[]=ZirkelFrame.Colors;
	static String ColorStrings[]=ZirkelFrame.ColorStrings;
	
	public CircleEditDialog (Frame f, PrimitiveCircleObject o,
		ZirkelCanvas zc)
	{	super(f,Zirkel.name("edit.circle.title"),o,"circle");
		ZC=zc;
	}
	
	public void addFirst (JPanel P)
	{	PrimitiveCircleObject C=(PrimitiveCircleObject)O;
		
		if (C instanceof CircleObject)
		{	if (((CircleObject)C).canFix())
			{	Fixed=new Checkbox("");	
				Fixed.setState(((CircleObject)C).fixed());
			}
			Length=new TextFieldAction(this,"Length",((CircleObject)O).getStringLength(),30);
		}
		else if (C instanceof FixedCircleObject)
		{	Fixed=new Checkbox("");
			Fixed.setState(!((FixedCircleObject)C).isDragable());
			Length=new TextFieldAction(this,"Length",
				((FixedCircleObject)O).getStringLength(),30);
		}
		else Length=new MyTextField(""+C.round(C.getR()));
		
		P.add(new MyLabel(Zirkel.name("edit.circle.length"))); P.add(Length);
		if (Fixed!=null)
		{	P.add(new MyLabel(Zirkel.name("edit.fixed"))); P.add(Fixed);
		}
		else Length.setEditable(false);
		
		P.add(new MyLabel(Zirkel.name("edit.circle.bounds")));
		JPanel bounds=new MyPanel();
		bounds.setLayout(new GridLayout(1,3));
		Arc=new Checkbox("");
		Arc.setState(C.hasRange());
		bounds.add(Arc);
		BoundA=new MyTextField(C.getStartString(),5);
		BoundB=new MyTextField(C.getEndString(),5);
		bounds.add(BoundA); bounds.add(BoundB);
		P.add(bounds);
	}
	
	public void addButton (JPanel P)
	{	RangeButton=new ButtonAction(this,
			Zirkel.name("edit.circle.range"),"Range");
		P.add(RangeButton);
		if (O instanceof FixedCircleObject)
		{	SetButton=new ButtonAction(this,
				Zirkel.name("edit.fixedcircle.set"),"Set");
			P.add(SetButton);
		}
	}
	
	public void addSecond (JPanel P)
	{	PrimitiveCircleObject C=(PrimitiveCircleObject)O;
		
		IC=new IconBar(F);
		IC.setIconBarListener(this);
		IC.addOnOffLeft("partial");
		IC.setState("partial",C.isPartial());
		IC.addOnOffLeft("filled");
		IC.setState("filled",C.isFilled());
		IC.addOnOffLeft("obtuse");
		IC.setState("obtuse",C.getObtuse());
		IC.addOnOffLeft("chord");
		IC.setState("chord",!C.getArc());
		IC.setIconBarListener(this);
		P.add(new MyLabel(""));
		P.add(IC);
	}
	
	public void iconPressed (String o)
	{	if (o.equals("filled"))
		{	if (IC.getState("filled"))
			{	IB.setState("isback",true);
				ThicknessIB.setEnabled("solid",true);
			}
			else
			{	IB.setState("isback",false);
				ThicknessIB.setState("solid",false);
				ThicknessIB.setEnabled("solid",false);
			}
		}
		super.iconPressed(o);
	}

	public void doAction (String o)
	{	if (o.equals("Length") && Fixed!=null)
		{	Fixed.setState(true);
			super.doAction("OK");
		}
		else if (o.equals("Range"))
		{	ZC.range((PrimitiveCircleObject)O);
			super.doAction("OK");
		}
		else if (o.equals("KillRange"))
		{	((PrimitiveCircleObject)O).clearRange();
			super.doAction("OK");
		}
		else if (o.equals("Set") && O instanceof FixedCircleObject)
		{	ZC.set((FixedCircleObject)O);
			super.doAction("OK");
		}
		else super.doAction(o);
	}
	
	public void setAction ()
	{	if (O instanceof CircleObject)
		{	try
			{	((CircleObject)O).setFixed(Fixed.getState(),Length.getText());
			}
			catch (Exception e) {}
		}
		else if (O instanceof FixedCircleObject)
		{	((FixedCircleObject)O).setDragable(!Fixed.getState());
			try
			{	((FixedCircleObject)O).setFixed(Length.getText());
			}
			catch (Exception e) {}
		}
		((PrimitiveCircleObject)O).setPartial(IC.getState("partial"));
		((PrimitiveCircleObject)O).setFilled(IC.getState("filled"));
		((PrimitiveCircleObject)O).setArc(!IC.getState("chord"));
		O.setObtuse(IC.getState("obtuse"));
		if (!Arc.getState())
		{	((PrimitiveCircleObject)O).clearRange();
		}
		else
		{	((PrimitiveCircleObject)O).setRange(BoundA.getText(),BoundB.getText());
		}
		Global.setParameter("unit.length",Unit.getText());
	}

	public void focusGained (FocusEvent e)
	{	if (Fixed!=null && Fixed.getState()) Length.requestFocus();
		else super.focusGained(e);
	}
}
