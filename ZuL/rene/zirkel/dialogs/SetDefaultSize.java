/*
 * Created on 12.07.2008
 * Dialog to set the default size for the canvas.
 *
 */
package rene.zirkel.dialogs;

import java.awt.*;

import rene.gui.*;
import rene.zirkel.Zirkel;

public class SetDefaultSize extends HelpCloseDialog
{	TextField W,H;

	public SetDefaultSize (Frame zf, int w, int h)
	{	super(zf,Global.name("defaultsize.title"),true);
		setLayout(new BorderLayout());
		
		Panel center=new MyPanel();
		center.setLayout(new GridLayout(0,2));
		center.add(new MyLabel(Global.name("defaultsize.width")));
		center.add(W=new MyTextField(""+w));
		center.add(new MyLabel(Global.name("defaultsize.height")));
		center.add(H=new MyTextField(""+h));
		add("Center",new Panel3D(center));
		
		Panel south=new MyPanel();
		south.add(new ButtonAction(this,Zirkel.name("edit.default"),"Default"));
		south.add(new ButtonAction(this,Zirkel.name("edit.ok"),"OK"));
		south.add(new ButtonAction(this,Zirkel.name("edit.cancel"),"Close"));
		add("South",south);
		
		pack();
		center(zf);
	}
	
	public void doAction (String s)
	{	if (s.equals("OK"))
		{	Aborted=false;
			doclose();
		}
		else if (s.equals("Default"))
		{	W.setText("");
			H.setText("");
			Aborted=false;
			doclose();
		}
		else super.doAction(s);
	}

	public int getW (int dw)
	{	try
		{	dw=Math.max(Math.min(Integer.parseInt(W.getText()),5000),100);
		}
		catch (Exception e) {}
		return dw;
	}

	public int getH (int dh)
	{	try
		{	dh=Math.max(Math.min(Integer.parseInt(H.getText()),5000),100);
		}
		catch (Exception e) {}
		return dh;
	}
	
	public boolean isDefault ()
	{	return W.getText().equals("");
	}
}
