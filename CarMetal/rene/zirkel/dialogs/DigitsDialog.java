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
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.CloseDialog;
import rene.gui.Global;
import rene.gui.IntField;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.zirkel.Zirkel;

public class DigitsDialog extends CloseDialog
{	IntField Edit,Lengths,Angles;
	
	public DigitsDialog (Frame f)
	{	super(f,Zirkel.name("digits.title"),true); 
		setLayout(new BorderLayout()); 
		
		JPanel c=new MyPanel(); 
		c.setLayout(new GridLayout(0,2)); 
		
		c.add(new MyLabel(Zirkel.name("digits.edit"))); 
		c.add(Edit=new IntField(this,"Edit",
			Global.getParameter("digits.edit",5),20)); 
		
		c.add(new MyLabel(Zirkel.name("digits.lengths"))); 
		c.add(Lengths=new IntField(this,"Lengths",
			Global.getParameter("digits.lengths",5))); 
		
		c.add(new MyLabel(Zirkel.name("digits.angles"))); 
		c.add(Angles=new IntField(this,"Angles",
			Global.getParameter("digits.angles",0))); 
		
		add("Center",new Panel3D(c)); 
		
		JPanel s=new MyPanel(); 
		s.add(new ButtonAction(this,Zirkel.name("ok"),"OK")); 
		s.add(new ButtonAction(this,Zirkel.name("abort"),"Close")); 
		add("South",new Panel3D(s)); 
		
		pack(); 
		center(f); 
		setVisible(true); 
	}
	
	public void doAction (String o)
	{	if (o.equals("OK"))
		{	Global.setParameter("digits.edit",Edit.value(2,20)); 
			Global.setParameter("digits.lengths",Lengths.value(0,10)); 
			Global.setParameter("digits.angles",Angles.value(0,10)); 
			doclose(); 
		}
		else super.doAction(o); 
	}
}

