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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.CloseDialog;
import rene.gui.Global;
import rene.gui.HistoryTextField;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.Panel3D;

/**
A simple dialog to scan for a parameter.
*/

public class GetParameter extends CloseDialog
{	HistoryTextField Input;
	static public int InputLength;
	String Result="";
	boolean Aborted=true;
	public GetParameter (Frame f, String title, String prompt, String action)
	{	this(f,title,prompt,action,false);
	}
	public GetParameter (Frame f, String title, String prompt, String action,
		String subject)
	{	super(f,title,true);
		Subject=subject;
		Input=new HistoryTextField(this,"Action",InputLength);
		Input.addKeyListener(this);
		init(f,title,prompt,action,true);
	}
	public GetParameter (Frame f, String title, String prompt, String action,
		boolean help)
	{	super(f,title,true);
		Input=new HistoryTextField(this,"Action",InputLength);
		Input.addKeyListener(this);
		init(f,title,prompt,action,help);
	}
	void init (Frame f, String title, String prompt, String action, boolean help)
	{	setLayout(new BorderLayout());
		JPanel center=new MyPanel();
		center.setLayout(new GridLayout(0,1));
		center.add(new MyLabel(prompt));
		center.add(Input);
		add("Center",new Panel3D(center));
		JPanel south=new MyPanel();
		south.setLayout(new FlowLayout(FlowLayout.RIGHT));
		south.add(new ButtonAction(this,action,"Action"));
		south.add(new ButtonAction(this,Global.name("abort"),"Abort"));
		if (help)
			south.add(new ButtonAction(this,Global.name("help","Help"),"Help"));
		add("South",new Panel3D(south));
		pack();
	}
	public void doAction (String o)
	{	if (o.equals("Abort"))
		{	doclose();
		}
		else if (o.equals("Action"))
		{	Result=Input.getText();
			doclose();
			Aborted=false;
		}
		else super.doAction(o);
	}
	public void set (String s)
	{	Input.setText(s);
	}
	public String getResult () { return Result; }
	public boolean aborted () { return Aborted; }

}
