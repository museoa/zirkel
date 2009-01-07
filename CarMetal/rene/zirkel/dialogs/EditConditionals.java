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

// file: ZirkelFrame.java

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;

import rene.dialogs.Warning;
import rene.gui.ButtonAction;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.MyTextField;
import rene.gui.Panel3D;
import rene.zirkel.Zirkel;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.objects.ConstructionObject;

public class EditConditionals extends HelpCloseDialog 
    implements ActionListener
{	TextField Colors[]=new TextField[ColorStrings.length];
	TextField ColorTypes[]=new TextField[ColorTypeStrings.length];
	TextField Hidden,SuperHidden;
	TextField ShowName,ShowValue;
	TextField Background,Solid;
	TextField Z;
	ConstructionObject O; // Single Object
	Vector V; // Vector of Objects
	Frame F;
	
	public static String ColorTypeStrings[]=
	{	"normal","thick","thin","invisible"
	}; 
	public static String ColorStrings[]=
	{	"black","green","blue","brown","cyan","red"
	}; 
	
	public EditConditionals (Frame f, ConstructionObject o, Vector v)
	{	super(f,
			o==null?Zirkel.name("editconditionals.title"):
				Zirkel.name("editconditionals.title")+" : "+o.getName(),true);
		O=o; F=f;
		if (O==null) O=o=(ConstructionObject)v.elementAt(0);
		if (v==null)
		{	V=new Vector(); V.addElement(o);
		}
		else 
			V=v;
		
		setLayout(new BorderLayout());
		
		MyPanel north=new MyPanel();
		north.setLayout(new GridLayout(0,2));
		for (int i=0; i<ColorStrings.length; i++)
		{	north.add(new MyLabel(
				Zirkel.name("colors."+ColorStrings[i])));
			Colors[i]=new MyTextField("",32);
			Expression ex=o.getConditional(ColorStrings[i]);
			if (ex!=null) Colors[i].setText(ex.toString());
			north.add(Colors[i]);
		}
		for (int i=0; i<ColorTypeStrings.length; i++)
		{	north.add(new MyLabel(
				Zirkel.name("color.type."+ColorTypeStrings[i])));
			ColorTypes[i]=new MyTextField("",32);
			Expression ex=o.getConditional(ColorTypeStrings[i]);
			if (ex!=null) ColorTypes[i].setText(ex.toString());
			north.add(ColorTypes[i]);
		}

		Hidden=addConditional(north,"hidden");
		SuperHidden=addConditional(north,"superhidden");

		ShowName=addConditional(north,"showname");
		ShowValue=addConditional(north,"showvalue");
		Background=addConditional(north,"background");
		Solid=addConditional(north,"solid");
		Z=addConditional(north,"z");
		
		add("North",new Panel3D(north));
		
		MyPanel south=new MyPanel();
		
		south.add(new ButtonAction(this,Zirkel.name("cancel"),"Close"));
		south.add(new ButtonAction(this,Zirkel.name("ok"),"OK"));
		addHelp(south,"conditions");
		
		add("South",new Panel3D(south));
		
		pack();
		center(f);
		setVisible(true);
	}
	
	public EditConditionals (Frame f, Vector v)
	{	this(f,null,v);
	}
	
	public EditConditionals (Frame f, ConstructionObject o)
	{	this(f,o,null);
	}
	
	public TextField addConditional (JPanel north, String tag)
	{	north.add(new MyLabel(Zirkel.name("editconditionals."+tag)));
		TextField H=new MyTextField("",32);
		Expression ex=O.getConditional(tag);
		if (ex!=null) H.setText(ex.toString());
		north.add(H);
		return H;
	}
	
	public void doAction (String o)
	{	if (o.equals("OK"))
		{	Enumeration en=V.elements();
			while (en.hasMoreElements())
			{	O=(ConstructionObject)en.nextElement();
				O.clearConditionals();
				for (int i=0; i<ColorStrings.length; i++)
				{	setConditional(ColorStrings[i],Colors[i]);
				}
				for (int i=0; i<ColorTypeStrings.length; i++)
				{	setConditional(ColorTypeStrings[i],ColorTypes[i]);
				}
				setConditional("hidden",Hidden);
				setConditional("superhidden",SuperHidden);
				
				setConditional("showname",ShowName);
				setConditional("showvalue",ShowValue);
				setConditional("background",Background);
				setConditional("solid",Solid);
				setConditional("z",Z);
				try
				{	O.checkConditionals();
				}
				catch (ConstructionException e)
				{	Warning w=new Warning(F,e.getDescription(),
						Zirkel.name("warning"),true);
					w.center(F);
					w.setVisible(true);
					return;
				}
			}
			doclose();
		}
		else super.doAction(o);
  	}
  	
  	public void setConditional (String tag, TextField t)
  	{	if (!t.getText().trim().equals(""))
		{	O.addConditional(tag,
				new Expression(t.getText().trim(),O.getConstruction(),O,null,false));
		}
  	}
}

