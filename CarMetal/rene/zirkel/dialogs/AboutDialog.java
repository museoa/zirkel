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

// file: AboutDialog.java

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import rene.gui.*;
import rene.zirkel.Zirkel;

public class AboutDialog extends CloseDialog
{	public AboutDialog (Frame f)
	{	super(f,Zirkel.name("about.title","About this Program"),true);
		setLayout(new BorderLayout());
		JPanel text=new MyPanel();
		text.setLayout(new GridLayout(0,1));
		MyLabel l;
		text.add(l=new MyLabel(Zirkel.name("program.name")));
		l.setAlignment(Label.CENTER);
		text.add(l=new MyLabel(Zirkel.name("version")+" "+Zirkel.name("program.version")));
		l.setAlignment(Label.CENTER);
		text.add(l=new MyLabel(Zirkel.name("date")+" "+Zirkel.name("program.date")));
		l.setAlignment(Label.CENTER);
		String empty="                                         ";
		text.add(l=new MyLabel(empty+"***"+empty));
		l.setAlignment(Label.CENTER);
		text.add(l=new MyLabel(Zirkel.name("about.programmed","programmed by")));
		l.setAlignment(Label.CENTER);
		text.add(l=new MyLabel("R. Grothmann"));
		l.setAlignment(Label.CENTER);
		text.add(l=new MyLabel(empty+"***"+empty));
		l.setAlignment(Label.CENTER);
		text.add(l=new MyLabel(Zirkel.name("about.language")+": "
			+Global.name("language","")));
		l.setAlignment(Label.CENTER);
		add("Center",new Panel3D(text));
		JPanel p=new MyPanel();
		Button b=new Button(Zirkel.name("close","Close"));
		b.addActionListener(this);
		p.add(b);
		add("South",new Panel3D(p));
		addWindowListener( // to close properly
			new WindowAdapter ()
			{	public void windowClosing (WindowEvent e)
				{	doclose();
				}
			}
		);
		pack();
		center(f);
                setVisible(true);
//		show();
	}
	public void actionPerformed (ActionEvent e)
	{	dispose();
	}
}
