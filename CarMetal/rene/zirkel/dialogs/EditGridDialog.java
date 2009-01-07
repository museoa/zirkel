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
import rene.gui.Global;
import rene.gui.IconBar;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.zirkel.Zirkel;
import rene.zirkel.objects.ConstructionObject;

/**
 * @author Rene
 * Dialog to select objects, when the selection with the mouse
 * was not unique.
 */
public class EditGridDialog extends HelpCloseDialog
{	IconBar ColorIB,ThicknessIB,StyleIB;	
	
	/**
	@param v A vector of ConstructionObjects to select from.
	*/
	public EditGridDialog (Frame f)
	{	super(f,Zirkel.name("ccordinates.title"),true);
	
		JPanel center=new MyPanel();
		center.setLayout(new BorderLayout());
		
		add("Center",new Panel3D(center));
		
		JPanel P=new JPanel();
		P.setLayout(new GridLayout(0,2));
		
		ColorIB=new IconBar(f);
		ColorIB.addToggleGroupLeft("color",6);
		ColorIB.toggle("color",Global.getParameter("grid.colorindex",0));
		P.add(new MyLabel("")); P.add(ColorIB);

		ThicknessIB=new IconBar(f);
		ThicknessIB.addToggleGroupLeft("thickness",4);
		ThicknessIB.toggle("thickness",Global.getParameter("grid.thickness",ConstructionObject.THIN));
		P.add(new MyLabel("")); P.add(ThicknessIB);
		
		StyleIB=new IconBar(f);
		StyleIB.addOnOffLeft("showname");
		StyleIB.setState("showname",Global.getParameter("grid.labels",true));
		StyleIB.addOnOffLeft("bold");
		StyleIB.setState("bold",Global.getParameter("grid.bold",false));
		StyleIB.addOnOffLeft("large");
		StyleIB.setState("large",Global.getParameter("grid.large",false));
		P.add(new MyLabel("")); P.add(StyleIB);
		
		center.add("South",P);
	
		JPanel p=new MyPanel();
		p.add(new ButtonAction(this,Zirkel.name("ok"),"OK"));
		p.add(new ButtonAction(this,Zirkel.name("cancel"),"Close"));
		addHelp(p,"grid");
		add("South",new Panel3D(p));
		
		pack();
		center(f);
		setVisible(true);
	}
	
	public void doAction (String o)
	{	Aborted=true;
		if (o.equals("OK"))
		{	Aborted=false;
			Global.setParameter("grid.colorindex",ColorIB.getToggleState("color"));
			Global.setParameter("grid.thickness",ThicknessIB.getToggleState("thickness"));
			Global.setParameter("grid.labels",StyleIB.getState("showname"));
			Global.setParameter("grid.bold",StyleIB.getState("bold"));
			Global.setParameter("grid.large",StyleIB.getState("large"));
			doclose();
		}
		else super.doAction(o);
	}
	
}


