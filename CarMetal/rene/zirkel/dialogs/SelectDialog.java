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

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.lister.Lister;
import rene.util.MyVector;
import rene.zirkel.Zirkel;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;

/**
 * @author Rene
 * Dialog to select objects, when the selection with the mouse
 * was not unique.
 */
public class SelectDialog extends HelpCloseDialog implements ActionListener
{	MyVector V;
	ConstructionObject O=null;
	Lister L;
	
	/**
	@param v A vector of ConstructionObjects to select from.
	*/
	public SelectDialog (Frame f, MyVector v)
	{	super(f,Zirkel.name("select.title","Select Object"),true);
		V=v;
		add("Center",L=new Lister());
		L.setMode(false,false,false,false);
		for (int i=0; i<v.size(); i++)
		{	ConstructionObject o=(ConstructionObject)v.elementAt(i);
			if (o instanceof PointObject)
				L.addElement(o.getName(),Color.black);
			else if (o instanceof PrimitiveLineObject)
				L.addElement(o.getName(),Color.red.darker());
			else if (o instanceof PrimitiveCircleObject)
				L.addElement(o.getName(),Color.blue.darker());			
			else L.addElement(o.getName(),Color.green.darker());
		}
		L.select(0);
		L.addActionListener(this);
		L.updateDisplay();
		JPanel p=new MyPanel();
		p.add(new ButtonAction(this,Zirkel.name("select.ok"),"OK"));
		p.add(new ButtonAction(this,Zirkel.name("select.cancel"),"Close"));
		addHelp(p,"mouse");
		add("South",new Panel3D(p));
		pack();
		center(f);
		setVisible(true);
	}
	
	public void actionPerformed (ActionEvent e)
	{	if (e.getSource()==L) // List double clicked!
		{	doAction("OK");
		}
		else super.actionPerformed(e);
	}
	
	public void doAction (String o)
	{	Aborted=true;
		if (o.equals("OK"))
		{	int i=L.getSelectedIndex();
			if (i>=0) O=(ConstructionObject)V.elementAt(i);
			Aborted=false;
			doclose();
		}
		else super.doAction(o);
	}
	
	public ConstructionObject getObject ()
	{	return O;
	}
}


