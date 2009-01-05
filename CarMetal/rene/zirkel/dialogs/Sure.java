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

import java.awt.*;
import rene.gui.*;
import rene.dialogs.*;

public class Sure extends Question
{	public Sure (Frame f, String question)
	{	super(f,question,Global.name("sure.title"),false);
	}
	public static boolean ask (Frame f, String prompt)
	{	Sure s=new Sure(f,prompt);
		s.center(f);
		s.setVisible(true);
		return s.yes();
	}
	public static void main (String args[])
	{	Frame f=new CloseFrame()
			{	public boolean close ()
				{	return Sure.ask(this,"Close?");
				}
			};
		f.setSize(400,400);
		f.setLocation(100,100);
		f.setVisible(true);
	}
}
