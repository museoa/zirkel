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

import java.awt.event.KeyEvent;
import java.util.Hashtable;

/**
This servers as a dictionary to make sure that the key translation
will work on localized systems too. The key recognition depends on the
text translation in KeyEvent. For user defined keyboards this will not
matter, but this class makes sure that it does not matter for the
default keyboard.
*/

class KeyDictionary
{	static Hashtable H;
	static
	{	H=new Hashtable(100);
		put(KeyEvent.VK_F1,"f1");
		put(KeyEvent.VK_F2,"f2");
		put(KeyEvent.VK_F3,"f3");
		put(KeyEvent.VK_F4,"f4");
		put(KeyEvent.VK_F5,"f5");
		put(KeyEvent.VK_F6,"f6");
		put(KeyEvent.VK_F7,"f7");
		put(KeyEvent.VK_F8,"f8");
		put(KeyEvent.VK_F9,"f9");
		put(KeyEvent.VK_F10,"f10");
		put(KeyEvent.VK_F11,"f11");
		put(KeyEvent.VK_F12,"f12");
		put(KeyEvent.VK_LEFT,"left");
		put(KeyEvent.VK_RIGHT,"right");
		put(KeyEvent.VK_DOWN,"down");
		put(KeyEvent.VK_UP,"up");
		put(KeyEvent.VK_PAGE_DOWN,"page down");
		put(KeyEvent.VK_PAGE_UP,"page up");
		put(KeyEvent.VK_DELETE,"delete");
		put(KeyEvent.VK_BACK_SPACE,"backspace");
		put(KeyEvent.VK_INSERT,"insert");
		put(KeyEvent.VK_HOME,"home");
		put(KeyEvent.VK_END,"end");
		put(KeyEvent.VK_ESCAPE,"escape");
		put(KeyEvent.VK_TAB,"tab");
		put(KeyEvent.VK_ENTER,"enter");		
	}
	static void put (int code, String name)
	{	H.put(new Integer(code),name);
	}
	static String translate (int code)
	{	Object o=H.get(new Integer(code));
		if (o!=null) return (String)o;
		return KeyEvent.getKeyText(code);
	}
}

