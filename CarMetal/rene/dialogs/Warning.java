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
 
 
 package rene.dialogs;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import rene.gui.*;

/**
This is a simple warning dialog. May be used as modal or non-modal
dialog.
*/

public class Warning extends CloseDialog 
    implements ActionListener
{	public boolean Result;
	Frame F;
	
	public Warning (Frame f, String c, String title, boolean flag, String help)
	{	super(f,title,flag);
		F=f;
		JPanel pc=new MyPanel();
		FlowLayout fl=new FlowLayout();
		pc.setLayout(fl);
		fl.setAlignment(FlowLayout.CENTER);
		pc.add(new MyLabel(" "+c+" "));
		add("Center",pc);
		JPanel p=new MyPanel();
		p.add(new ButtonAction(this,Global.name("close"),"Close"));
		if (help!=null && !help.equals("")) addHelp(p,help);
		add("South",p);
		pack();
	}
	
	public Warning (Frame f, String c, String title, boolean flag)
	{	this(f,c,title,flag,"");
	}

	public Warning (Frame f, String c, String title)
	{	this(f,c,title,true,"");
	}
	
	public Warning (Frame f, String c1, String c2, String title, boolean flag,
			String help)
	{	super(f,title,flag);
		F=f;
		JPanel pc=new MyPanel();
		pc.setLayout(new GridLayout(0,1));
		pc.add(new MyLabel(" "+c1+" "));
		pc.add(new MyLabel(" "+c2+" "));
		add("Center",pc);
		JPanel p=new MyPanel();
		p.add(new ButtonAction(this,Global.name("close"),"Close"));
		if (help!=null && !help.equals("")) addHelp(p,help);
		add("South",p);
		pack();
	}
	
	public Warning (Frame f, String c1, String c2, String title, boolean flag)
	{	this(f,c1,c2,title,flag,"");
	}
	
	public Warning (Frame f, String c1, String c2, String title)
	{	this(f,c1,c2,title,true,"");
	}
}
