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
 
 
 package rene.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.io.PrintWriter;

public class SystemViewer extends Viewer
{   TextArea T;
    public SystemViewer ()
    {   super("dummy");
        setLayout(new BorderLayout());
        add("Center",T=new TextArea());
    }
    public void appendLine (String s)
    {   T.append(s+"\n");
    }
	public void appendLine (String s, Color c)
	{	appendLine(s);
	}
    public void append (String s)
    {   T.append(s);
    }
    public void append (String s, Color c)
    {   append(s);
    }
    public void setText (String s)
    {   T.setText(s);
    }
	public void doUpdate (boolean showlast)
	{	T.repaint();
	}
    public void setFont (Font s)
    {	T.setFont(s);
    }
    public void save (PrintWriter fo)
    {   fo.print(T.getText());
    	fo.flush();
    }
}
