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
import java.awt.Checkbox;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.CheckboxAction;
import rene.gui.Global;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.zirkel.Zirkel;

public class ExportSettingsDialog extends HelpCloseDialog
{
	Checkbox BitmapBackground,MinPointSize,MinFontSize; 
	
	public ExportSettingsDialog (Frame frame)
	{	super(frame,Zirkel.name("menu.options.export"),true); 
		setLayout(new BorderLayout()); 
		
		JPanel north=new MyPanel(); 
		north.setLayout(new GridLayout(0,1)); 
		
		MinPointSize=addcheck(north,"menu.settings.minpointsize"); 
		MinPointSize.setState(Global.getParameter("options.minpointsize",false)); 
		
		MinFontSize=addcheck(north,"menu.settings.minfontsize"); 
		MinFontSize.setState(Global.getParameter("options.minfontsize",false)); 
		
		BitmapBackground=addcheck(north,"menu.settings.bitmapbackground"); 
		BitmapBackground.setState(Global.getParameter("options.bitmapbackground",false)); 
		
		add("North",new Panel3D(north)); 
		
		JPanel south=new MyPanel(); 
		
		south.add(new ButtonAction(this,Zirkel.name("ok"),"OK")); 
		south.add(new ButtonAction(this,Zirkel.name("abort"),"Close")); 
		addHelp(south,"exportsettings");
		
		add("South",new Panel3D(south)); 
		
		pack(); 
		center(frame); 
		setVisible(true); 
	}
	
	public void doAction (String s)
	{	if (s.equals("OK"))
		{	Global.setParameter("options.minpointsize",MinPointSize.getState()); 
			Global.setParameter("options.minfontsize",MinFontSize.getState()); 
			Global.setParameter("options.bitmapbackground",BitmapBackground.getState()); 
			doclose(); 
		}
		else super.doAction(s); 
	}
	
	public Checkbox addcheck (JPanel p, String name)
	{	Checkbox c=new CheckboxAction(this,Zirkel.name(name),name); 
		p.add(c); 
		return c; 
	}
}


