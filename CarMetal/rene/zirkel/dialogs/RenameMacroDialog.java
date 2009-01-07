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
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.CloseDialog;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.gui.TextFieldAction;
import rene.zirkel.Zirkel;
import rene.zirkel.macro.Macro;

public class RenameMacroDialog extends CloseDialog
{	boolean Aborted=true; 
	TextField Name; 
	TextArea Comment;
	
	public RenameMacroDialog (Frame frame, Macro m)
	{	super(frame,Zirkel.name("renamemacro.title","Rename Macro"),true); 
		setLayout(new BorderLayout()); 
		
		JPanel north=new MyPanel(); 
		north.setLayout(new GridLayout(0,1)); 
		north.add(new MyLabel(Zirkel.name("renamemacro.name"))); 
		north.add(Name=new TextFieldAction(this,"OK",m.getName(),64)); 
		add("North",new Panel3D(north)); 
		
		add("Center",new Panel3D(Comment=new TextArea("",5,40,TextArea.SCROLLBARS_VERTICAL_ONLY)));
		Comment.setText(m.getComment());
		
		JPanel south=new MyPanel(); 
		south.add(new ButtonAction(this,Zirkel.name("ok"),"OK")); 
		south.add(new ButtonAction(this,Zirkel.name("cancel"),"Close")); 
		add("South",new Panel3D(south)); 
		
		pack(); 
	}
	
	public void doAction (String o)
	{	Aborted=true; 
		if (o.equals("OK"))
		{	Aborted=false; 
			doclose(); 
		}
		else super.doAction(o); 
	}
	
	public String getName ()
	{	return Name.getText(); 
	}
	
	public String getComment ()
	{	return Comment.getText();
	}
	
	public boolean isAborted ()
	{	return Aborted; 
	}
}

