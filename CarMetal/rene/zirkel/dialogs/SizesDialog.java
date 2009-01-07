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

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.Global;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.MyTextField;
import rene.gui.Panel3D;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelFrame;

public class SizesDialog extends HelpCloseDialog 
{	ZirkelFrame ZF;
	TextField Pointsize,Linewidth,Arrowsize,Selectionsize,Fontsize;
	
	public SizesDialog (ZirkelFrame zf)
	{	super(zf,Zirkel.name("sizesdialog.title"),true);
		ZF=zf;
		setLayout(new BorderLayout());
		
		JPanel center=new MyPanel();
		center.setLayout(new GridLayout(0,2));
		
		Pointsize=addfield(center,"minpointsize.prompt","minpointsize",3);
		Linewidth=addfield(center,"minlinesize.prompt","minlinesize",1);
		Fontsize=addfield(center,"minfontsize.prompt","minfontsize",12);
		Selectionsize=addfield(center,"selectionsize.prompt","selectionsize",1.5);
		Arrowsize=addfield(center,"arrowsize.prompt","arrowsize",15);
		
		add("Center",new Panel3D(center));
		
		JPanel p=new MyPanel();
		Button b=new ButtonAction(this,Zirkel.name("edit.ok"),"OK");
		p.add(b);
		b=new ButtonAction(this,Zirkel.name("edit.cancel"),"Close");
		p.add(b);
		b.addActionListener(this);
		addHelp(p,"sizes");
		add("South",new Panel3D(p));
		
		center();
		pack();
		setVisible(true);
	}
	
	public void doAction (String o)
	{	if (o.equals("OK"))
		{	set(Pointsize,"minpointsize",0.5,10);
			set(Linewidth,"minlinesize",0.5,3);
			set(Fontsize,"minfontsize",2,30);
			set(Arrowsize,"arrowsize",5,50);
			set(Selectionsize,"selectionsize",0.5,5);
			doclose();
		}
		else super.doAction(o);
	}

	public TextField addfield (JPanel p, String tag, String deftag, double def)
	{	p.add(new MyLabel(Global.name(tag)));
		TextField t=new MyTextField(""+Global.getParameter(deftag,def),20);
		p.add(t);
		return t;
	}
	
	public void set (TextField t, String tag, double min, double max)
	{	try
		{	double x=new Double(t.getText()).doubleValue();
			if (x<min) x=min;
			if (x>max) x=max;
			Global.setParameter(tag,x);
		}
		catch (Exception e)
		{}
	}
}
