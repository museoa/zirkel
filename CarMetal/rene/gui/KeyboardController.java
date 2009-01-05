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
 
 
 package rene.gui;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardController
	implements KeyListener
{	/** Macro and programs key pressed */
	boolean Escape=false;
	/** Note, if the next entry should be ignored or not */
	boolean IgnoreTyped=false;
	/** The type of the recent command key (1..5) */
	int CommandType=0;
	/** The component, which we are listening to. */
	Component C=null;
	/** The primary and secondary KeyboardInterface */
	KeyboardInterface Primary=null,Secondary=null;
	
	public synchronized void keyPressed (KeyEvent e)
	{	if (e.getKeyCode()==KeyEvent.VK_SHIFT) return;
		if (e.getKeyCode()==KeyEvent.VK_CONTROL) return;
		if (e.getKeyCode()==KeyEvent.VK_ALT) return;
		if (old(e)) return;
		String s=Keyboard.findKey(e,CommandType);
		// System.out.println(Escape+" "+CommandType+" "+s);
		IgnoreTyped=false;
		if (s.startsWith("command."))
		{	if (s.equals("command.escape"))
			{	Escape=!Escape;
			}
			else try
			{	CommandType=Integer.parseInt(s.substring(8));
				Escape=false;
			}
			catch (Exception ex) { CommandType=0; }
			IgnoreTyped=true;
		}
		else if (s.startsWith("charkey."))
		{	keyboardCommand(e,s);
			IgnoreTyped=true;
			Escape=false;
			CommandType=0;
		}
		else if (Escape)
		{	char c=e.getKeyChar();
			IgnoreTyped=true;
			keyboardEscape(e,c);
			Escape=false;
		}
		else if (!s.equals(""))
		{	keyboardCommand(e,s);
			IgnoreTyped=false;
			Escape=false;
			CommandType=0;
		}
		else if (!e.isActionKey())
		{	if (!Global.getParameter("keyboard.compose",true))
			{	keyboardChar(e,e.getKeyChar());
				Escape=false;
				CommandType=0;
			}
			else
			{	Escape=false;
				CommandType=0;
			}
		}
	}
	
	public void keyTyped (KeyEvent e)
	{	if (!Global.getParameter("keyboard.compose",true)) return;
		// System.out.println("key typed "+IgnoreTyped+" "+e);
		if (IgnoreTyped) return;
		IgnoreTyped=false;
		keyboardChar(e,e.getKeyChar());
		Escape=false;
		CommandType=0;
	}	

	public void keyReleased (KeyEvent e) {}
	
	public void keyboardCommand (KeyEvent e, String command)
	{	// System.out.println(command);
		if (Primary==null || !Primary.keyboardCommand(e,command))
			if (Secondary!=null) Secondary.keyboardCommand(e,command);
	}
	
	public void keyboardEscape (KeyEvent e, char c)
	{	//System.out.println("escape "+c);
		if (Primary==null || !Primary.keyboardEscape(e,c))
			if (Secondary!=null) Secondary.keyboardEscape(e,c);
	}
	
	public void keyboardChar (KeyEvent e, char c)
	{	//System.out.println(""+c);
		if (Primary==null || !Primary.keyboardChar(e,c))
			if (Secondary!=null) Secondary.keyboardChar(e,c);
	}
	
	boolean scaled=false; // scaled already
	long scale; // the scaling in milliseconds
	
	/**
	Test for old keys. This algorithm uses the difference between
	event time and system time. However, one needs to scale this
	first, since in Linux both timers do not agree.
	*/
	boolean old (KeyEvent e)
	{	if (!scaled)
		{	scaled=true; scale=System.currentTimeMillis()-e.getWhen();
			return false;
		}
		long delay=System.currentTimeMillis()-e.getWhen()-scale;
		if (delay>10000) return false; // function does not work!
		return (delay>200);
	}
	
	public void listenTo (Component c)
	{	if (C!=null) C.removeKeyListener(this);
		C=c;
		if (C!=null) C.addKeyListener(this);
	}
	
	public void setPrimary (KeyboardInterface i)
	{	Primary=i;
	}
	
	public void setSecondary (KeyboardInterface i)
	{	Secondary=i;
	}
}
