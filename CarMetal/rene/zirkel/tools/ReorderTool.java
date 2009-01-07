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
 
 
 package rene.zirkel.tools;

// file: Hider.java

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.CloseDialog;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.gui.TextFieldAction;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;

class ReorderDialog extends CloseDialog
{	String Name="";
	boolean Abort=true;
	TextField Input;
	
	public ReorderDialog (ZirkelCanvas zc, ConstructionObject o)
	{	super(zc.getFrame(),Zirkel.name("reorder.title"),true);
		setLayout(new BorderLayout());
		JPanel north=new MyPanel();
		north.setLayout(new GridLayout(1,0));
		north.add(new MyLabel(o.getName()+" : "+Zirkel.name("reorder.message")));
		ConstructionObject ol=zc.getConstruction().lastDep(o);
		String s="";
		if (ol!=null) s=ol.getName();
		north.add(Input=new TextFieldAction(this,"Reorder",s));
		add("North",new Panel3D(north));
		JPanel south=new MyPanel();
		south.add(new ButtonAction(this,Zirkel.name("ok"),"OK"));
		south.add(new ButtonAction(this,Zirkel.name("abort"),"Close"));
		add("South",south);
		pack();
		center(zc.getFrame());
		setVisible(true);
	}
	public void doAction (String o)
	{	if (o.equals("OK"))
		{	Abort=false;
			Name=Input.getText();
			doclose();
		}
		else super.doAction(o);
	}
	public String getResult ()
	{	return Name;
	}
	public boolean isAborted ()
	{	return Abort;
	}
}

public class ReorderTool extends ObjectConstructor
{	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	double x=zc.x(e.getX()),y=zc.y(e.getY());
		ConstructionObject o=zc.selectObject(e.getX(),e.getY());
		if (o==null) return;
		ReorderDialog d=new ReorderDialog(zc,o);
		if (!d.isAborted())
		{	String name=d.getResult();
			if (!name.equals(""))
			{	ConstructionObject u=zc.getConstruction().find(name);
				if (u==null)
				{	zc.warning(Zirkel.name("reorder.notfound"));
					return;
				}
				if (!zc.getConstruction().reorder(o,u))
					zc.warning(Zirkel.name("reorder.warning"));
			}
			else
				if (!zc.getConstruction().reorder(o,null))
					zc.warning(Zirkel.name("reorder.warning"));
		}
		zc.repaint();
	}
	public void mouseMoved (MouseEvent e, ZirkelCanvas zc, boolean simple)
	{	zc.indicateObjects(e.getX(),e.getY());
	}

	public void showStatus (ZirkelCanvas zc)
	{	zc.showStatus(
			Zirkel.name("message.reorder","Reorder: Select an object!"));
	}
	public boolean useSmartBoard ()
	{	return false;
	}
}
