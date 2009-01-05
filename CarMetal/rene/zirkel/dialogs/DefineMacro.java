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

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JPanel;
import rene.gui.*;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.*;

public class DefineMacro extends HelpCloseDialog
{	TextField Name,PromptFor;
	TextArea Comment;
	ZirkelCanvas ZC;
	Vector V;
	boolean Aborted=true;
	Checkbox TargetsOnly,Invisible,HideDuplicates;

	public DefineMacro (Frame f, ZirkelCanvas zc)
	{	super(f,Zirkel.name("definemacro.title"),true);
		ZC=zc;
		setLayout(new BorderLayout());
		
		JPanel north=new MyPanel();
		north.setLayout(new GridLayout(0,2));
		north.add(new MyLabel(Zirkel.name("definemacro.name")));
		north.add(Name=new TextFieldAction(this,"",20));
		add("North",new Panel3D(north));
		
		JPanel center=new MyPanel();
		center.setLayout(new BorderLayout());
		
		center.add("North",new MyLabel(Zirkel.name("definemacro.comment")));
		center.add("Center",Comment=new TextArea("",5,40,
			TextArea.SCROLLBARS_VERTICAL_ONLY));
		if (Global.Background!=null) Comment.setBackground(Global.Background);
		JPanel p=new MyPanel();
		p.setLayout(new GridLayout(0,1));
		p.add(new MyLabel(Zirkel.name("definemacro.parameters")));
		Enumeration e=ZC.getConstruction().getParameters().elements();
		V=new Vector();
		while (e.hasMoreElements())
		{	ConstructionObject o=(ConstructionObject)e.nextElement();
			TextField tf=new TextFieldAction(this,"",o.getName());
			V.addElement(tf);
			p.add(tf);
		}
		if (zc.getConstruction().countTargets()>0)
		{	JPanel ph=new MyPanel();
			ph.setLayout(new GridLayout(1,0));
			ph.add(new MyLabel(Zirkel.name("definemacro.targetsonly")));
			TargetsOnly=new CheckboxAction(this,"","TargetsOnly");
			TargetsOnly.setState(true);
			ph.add(TargetsOnly);
			p.add(ph);
			ph=new MyPanel();
			ph.setLayout(new GridLayout(1,0));
			ph.add(new MyLabel(Zirkel.name("definemacro.invisible")));
			Invisible=new CheckboxAction(this,"","Invisible");
			Invisible.setState(false);
			ph.add(Invisible);
			p.add(ph);
		}
		JPanel ph=new MyPanel();
		ph.setLayout(new GridLayout(1,0));
		ph.add(new MyLabel(Zirkel.name("menu.options.hideduplicates")));
		HideDuplicates=new CheckboxAction(this,"","HideDuplicates");
		HideDuplicates.setState(true);
		ph.add(HideDuplicates);
		p.add(ph);
		JPanel pp=new MyPanel();
		pp.setLayout(new GridLayout(1,0));
		pp.add(new MyLabel(Zirkel.name("definemacro.promptfor")));
		PromptFor=new TextFieldAction(this,"","");
		pp.add(PromptFor);
		p.add(pp);
		center.add("South",p);
		
		add("Center",new Panel3D(center));
		
		JPanel south=new MyPanel();
		south.add(new ButtonAction(this,Zirkel.name("ok"),"OK"));
		south.add(new ButtonAction(this,Zirkel.name("abort"),"Close"));
		addHelp(south,"macro");
		add("South",new Panel3D(south));
		
		center(f);
		pack();
	}
	public void doAction (String o)
	{	if (o.equals("OK"))
		{	Aborted=false;
			doclose();
		}
		else super.doAction(o);
	}
	public void windowOpened (WindowEvent e)
	{	Name.requestFocus();
	}
	public boolean isAborted ()
	{	return Aborted;
	}
	public String[] getParams ()
	{	String s[]=new String[V.size()];
		for (int i=0; i<V.size(); i++)
			s[i]=((TextField)V.elementAt(i)).getText();
		return s;
	}
	public String getName()
	{	return Name.getText();
	}
	public String getComment ()
	{	return Comment.getText();
	}
	public boolean targetsOnly ()
	{	if (TargetsOnly==null) return false;
		return TargetsOnly.getState();
	}
	public boolean invisible ()
	{	if (Invisible==null) return false;
		return Invisible.getState();
	}
	public boolean hideduplicates ()
	{	return HideDuplicates.getState();
	}
	public String[] getPromptFor ()
	{	StringTokenizer t=new StringTokenizer(PromptFor.getText(),",");
		String s[]=new String[t.countTokens()];
		for (int i=0; i<s.length; i++)
			s[i]=t.nextToken();
		return s;
	}
}
