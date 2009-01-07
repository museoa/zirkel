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

import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.CheckboxAction;
import rene.gui.CloseDialog;
import rene.gui.Global;
import rene.gui.MyLabel;
import rene.gui.MyPanel;

public class AgainQuestion extends CloseDialog 
    implements ActionListener
{	public int Result;
	Frame F;
	public static int NO=0,YES=1;
	Checkbox Again;
	public AgainQuestion (Frame f, String c, String title)
	{	super(f,title,true);
		F=f;
		JPanel main=new MyPanel();
		main.setLayout(new GridLayout(0,1));
		JPanel pc=new MyPanel();
		FlowLayout fl=new FlowLayout();
		pc.setLayout(fl);
		fl.setAlignment(FlowLayout.CENTER);
		pc.add(new MyLabel(" "+c+" "));
		main.add(pc);
		JPanel pd=new MyPanel();
		pd.add(Again=new CheckboxAction(this,Global.name("question.again")));
		Again.setState(true);
		main.add(pd);
		add("Center",main);
		JPanel p=new MyPanel();
		p.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p.add(new ButtonAction(this,Global.name("yes"),"Yes"));
		p.add(new ButtonAction(this,Global.name("no"),"No"));
		add("South",p);
		pack();
	}
	public void doAction (String o)
	{	if (o.equals("Yes"))
  		{	tell(YES);
  		}
  		else if (o.equals("No"))
  		{	tell(NO);
  		}
  	}
  	/**
  	Needs to be overriden for modal usage. Should dispose the dialog.
  	*/
	public void tell (int f)
	{	Result=f;
		doclose();
	}
	/**
	@return if the user pressed yes.
	*/
	public boolean yes ()
	{	return Result==YES;
	}
	public int getResult ()
	{	return Result;
	}
	public boolean again ()
	{	return Again.getState();
	}
}

