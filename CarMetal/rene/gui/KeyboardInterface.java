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

public interface KeyboardInterface
{	/**
	Got a command string.
	@return Could handle command or not.
	*/
	public boolean keyboardCommand (KeyEvent e, String o);
	/**
	Got an escaped character. Escape works as macro key or call of
	external programs in JE.
	*/
	public boolean keyboardEscape (KeyEvent e, char c);
	/**
	Got a character input.
	*/
	public boolean keyboardChar (KeyEvent e, char c);
}
