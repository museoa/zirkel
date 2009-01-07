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
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.JPanel;

import rene.dialogs.Warning;
import rene.gui.ButtonAction;
import rene.gui.Global;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelFrame;

public class EditRunDialog extends HelpCloseDialog
{	String Filename;
	ZirkelFrame ZF;
	TextArea Text;

	public EditRunDialog (ZirkelFrame zf, String filename)
	{	super(zf,Zirkel.name("editrun.title"),true);
		Filename=filename;
		ZF=zf;
		setLayout(new BorderLayout());
		
		JPanel north=new MyPanel();
		north.add(new MyLabel(filename));
		add("North",new Panel3D(north));
		
		add("Center",new Panel3D(Text=new TextArea(30,60)));
		if (Global.FixedFont!=null) Text.setFont(Global.FixedFont);
		
		load(filename);
		
		JPanel south=new MyPanel();
		south.add(new ButtonAction(this,Zirkel.name("editrun.run"),"Run"));
		south.add(new ButtonAction(this,Zirkel.name("editrun.load"),"Load"));
		south.add(new ButtonAction(this,Zirkel.name("abort"),"Close"));
		addHelp(south,"visual");
		add("South",new Panel3D(south));
		
		setSize("editrun");
		center(zf);
	}
	
	public void doAction (String s)
	{	noteSize("editrun");
		if (s.equals("Run"))
		{	doclose();
			try
			{	save(Filename);
			}
			catch (Exception e)
			{	Warning w=new Warning(ZF,Zirkel.name("editrun.error"),
					Zirkel.name("warning"),true);
				w.center(ZF);
				w.setVisible(true);
				return; 
			}
			ZF.loadRun(Filename);
		}
		else if (s.equals("Load"))
		{	doclose();
			ZF.editRun("");
		}
		else super.doAction(s);
	}
	
	public void load (String filename)
	{	Text.setText("");
		Text.setEnabled(false);
		try
		{	BufferedReader in=new BufferedReader(
				new InputStreamReader(new FileInputStream(filename)));
			while (true)
			{	String line=in.readLine();
				if (line==null) break;
				Text.append(line+"\n");
			}
			in.close();
		}
		catch (Exception e) {}
		Text.setEnabled(true);
		Text.repaint();
	}
	
	public void save (String filename)
		throws IOException
	{	String text=Text.getText();
		PrintWriter out=new PrintWriter(
			new OutputStreamWriter(new FileOutputStream(filename)));
		out.print(text);
		out.close();
	}
	
}
