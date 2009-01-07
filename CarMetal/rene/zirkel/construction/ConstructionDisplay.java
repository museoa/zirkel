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
 
 
 package rene.zirkel.construction;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.CheckboxAction;
import rene.gui.ChoiceAction;
import rene.gui.Global;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.lister.Lister;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.dialogs.HelpCloseDialog;
import rene.zirkel.objects.AngleObject;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.FixedAngleObject;
import rene.zirkel.objects.FunctionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;

/**
 * @author Rene
 * This displays the construction objects element by element in a
 * Lister. The user can toggle teh display of certain classes of
 * objects on and off. He can call the properties dialog for objects.
 * 
 * @see rene.lister.Lister
 */
public class ConstructionDisplay extends HelpCloseDialog
	implements ClipboardOwner
{	Lister V;
	Vector W;
	Construction C;
	ZirkelCanvas ZC;
	// display the help from subject.txt
	
	Choice Ch;
	Checkbox Sort;
	public static String Choices[]=
		{"all","visible","expressions",
			"points","lines","circles","angles","other","invalid"};
	int State=0;
	
	public ConstructionDisplay (Frame f, ZirkelCanvas zc)
	{	super(f,Zirkel.name("constructiondisplay.title","Construction"),true);
		C=zc.getConstruction();
		ZC=zc;
		V=new Lister()
			{	public Dimension getPreferredSize ()
				{	return new Dimension(500,400);
				}
			};
		V.setMode(true,false,false,false);
		if (Global.Background!=null) V.setBackground(Global.Background);
		V.setFont(Global.FixedFont);
		State=Global.getParameter("constructiondisplay.state",1);
		
		setLayout(new BorderLayout());
		
		JPanel north=new MyPanel();
		north.setLayout(new GridLayout(0,2));
		north.add(new MyLabel(Zirkel.name("constructiondisplay.select")));
		Ch=new ChoiceAction(this,"choices");
		for (int i=0; i<Choices.length; i++)
		{	Ch.add(Zirkel.name("constructiondisplay."+Choices[i]));
		}
		north.add(Ch);
		Ch.select(State);
		north.add(new MyLabel(Zirkel.name("constructiondisplay.sorted")));
		Sort=new CheckboxAction(this,"","Sort");
		Sort.setState(Global.getParameter("constructiondisplay.sort",true));
		north.add(Sort);
		add("North",new Panel3D(north));
		
		add("Center",V);
		
		JPanel p=new MyPanel();
		p.add(new ButtonAction(this,Zirkel.name("constructiondisplay.copy"),"Copy"));
		p.add(new ButtonAction(this,Zirkel.name("constructiondisplay.edit"),"Edit"));
		p.add(new ButtonAction(this,Zirkel.name("close","Close"),"Close"));
		addHelp(p,"construction");
		add("South",p);
		
		pack();
		setSize("constructiondialog");
		center(f);
		reload();
		setVisible(true);
	}
	
	public void reload ()
	{	V.clear();
		Enumeration e=null;
		if (Sort.getState()) e=C.getSortedElements();
		else e=C.elements();
		W=new Vector();
		outer: while (e.hasMoreElements())
		{	ConstructionObject o=(ConstructionObject)e.nextElement();
			switch (State)
			{	
				case 1 : 
					if (o.mustHide(ZC)) continue outer;
					break;
				case 2 :
					if (o.mustHide(ZC)) continue outer;
					if (!(o instanceof ExpressionObject ||
								o instanceof FunctionObject))
									continue outer;
					break;
				case 3 :
					if (o.mustHide(ZC)) continue outer;
					if (!(o instanceof PointObject))
									continue outer;
					break;
				case 4 :
					if (o.mustHide(ZC)) continue outer;
					if (!(o instanceof PrimitiveLineObject) ||
							(o instanceof FixedAngleObject))
									continue outer;
					break;
				case 5 :
					if (o.mustHide(ZC)) continue outer;
					if (!(o instanceof PrimitiveCircleObject))
									continue outer;
					break;
				case 6 :
					if (o.mustHide(ZC)) continue outer;
					if (!(o instanceof AngleObject || 
							o instanceof FixedAngleObject))
									continue outer;
					break;
				case 7 :
					if (o.mustHide(ZC)) continue outer;
					if (o instanceof PointObject || 
							o instanceof PrimitiveLineObject ||
							o instanceof PrimitiveCircleObject ||
							o instanceof AngleObject ||
							o instanceof ExpressionObject ||
							o instanceof FunctionObject)
									continue outer;
					break;	
				case 8 :
					if (o.valid()) continue outer;
					break;
			}
			V.addElement(o);
			W.addElement(o);
		}
		V.showLast();
		V.updateDisplay();
	}
	
	public void doAction (String o)
	{	noteSize("constructiondialog");
		Global.setParameter("constructiondisplay.sort",Sort.getState());
		if (o.equals("Edit"))
		{	super.doAction("Close");
			int selected=V.getSelectedIndex();
			if (selected>=0)
			{	Enumeration e=W.elements();
				int i=0;
				while (e.hasMoreElements())
				{	ConstructionObject O=(ConstructionObject)e.nextElement();
					if (i==selected)
					{	O.edit(ZC);
						return;
					}
					i++;
				}
			}			
		}
		else if (o.equals("Copy"))
		{	try
			{	ByteArrayOutputStream ba=new ByteArrayOutputStream(50000);
				PrintWriter po=new PrintWriter(
					new OutputStreamWriter(ba),true);
				V.save(po);
				po.close();
				String S=ba.toString();
				Clipboard clip=getToolkit().getSystemClipboard();
				StringSelection sel=new StringSelection(S);
				clip.setContents(sel,this);
			}
			catch (Exception e) {	}
		}
		super.doAction(o);
	}
	
	public void itemAction (String o, boolean flag)
	{	if (o.equals("Sort"))
		{	reload();
		}
		else if (flag)
		{	State=Ch.getSelectedIndex();
			Global.setParameter("constructiondisplay.state",State);
			reload();
		}
	}
	
	public void lostOwnership (Clipboard b, Transferable s) {}
	
}
