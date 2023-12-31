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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintWriter;

import javax.swing.JPanel;

import rene.gui.Global;
import rene.gui.Panel3D;

/**
This is a read-only TextArea, removing the memory restriction in some
OS's. Component usage is like a JPanel. Use appendLine to append a line
of text. You can give each line a different color. Moreover, you can
save the file to a PrintWriter. You can mark blocks with the right
mouse button. Dragging and scrolling is not supported in this version.
*/

public class Viewer extends JPanel 
	implements AdjustmentListener, MouseListener, MouseMotionListener,
		ActionListener, KeyListener, WheelListener
{	TextDisplay TD;
	Scrollbar Vertical,Horizontal;
	TextPosition Start,End;
	PopupMenu PM;
	int X,Y;
	JPanel P3D;
	
	public Viewer (boolean vs, boolean hs)
	{	TD=new TextDisplay(this);
		setLayout(new BorderLayout());
		add("Center",P3D=new Panel3D(TD));
		if (vs)
		{	add("East",Vertical=new Scrollbar(Scrollbar.VERTICAL,0,100,0,1100));
			Vertical.addAdjustmentListener(this);
		}
		if (hs)
		{	add("South",Horizontal=new Scrollbar(Scrollbar.HORIZONTAL,0,100,0,1100));
			Horizontal.addAdjustmentListener(this);
		}
		TD.addMouseListener(this);
		TD.addMouseMotionListener(this);
		Start=End=null;
		PM=new PopupMenu();
		MenuItem mi=new MenuItem(Global.name("block.copy","Copy"));
		mi.addActionListener(this);
		PM.add(mi);
		PM.addSeparator();
		mi=new MenuItem(Global.name("block.begin","Begin Block"));
		mi.addActionListener(this);
		PM.add(mi);
		mi=new MenuItem(Global.name("block.end","End Block"));
		mi.addActionListener(this);
		PM.add(mi);
		add(PM);
		Wheel W=new Wheel(this);
		addMouseWheelListener(W);
	}
	
	public Viewer ()
	{	this(true,true);
	}
	
	public Viewer (String dummy)
	{
	}
	
	public void setFont (Font f)
	{	TD.init(f);
	}
	
	public void appendLine (String s)
	{	TD.appendLine0(s);
	}
	
	public void appendLine (String s, Color c)
	{	TD.appendLine0(s,c);
	}
	
	public void append (String s)
	{	append(s,Color.black);
	}
	
	public void append (String s, Color c)
	{	TD.append(s,c);
	}
	
	public void doUpdate (boolean showlast)
	{	TD.doUpdate(showlast);
		setVerticalScrollbar();
	}
	
	public void adjustmentValueChanged (AdjustmentEvent e)
	{	if (e.getSource()==Vertical)
		{	switch (e.getAdjustmentType())
			{	case AdjustmentEvent.UNIT_INCREMENT :
					TD.verticalUp(); break;
				case AdjustmentEvent.UNIT_DECREMENT :
					TD.verticalDown(); break;
				case AdjustmentEvent.BLOCK_INCREMENT :
					TD.verticalPageUp(); break;
				case AdjustmentEvent.BLOCK_DECREMENT :
					TD.verticalPageDown(); break;
				default :
					int v=Vertical.getValue();
					Vertical.setValue(v);
					TD.setVertical(v);
					return;
			}
			setVerticalScrollbar();
		}
		else if (e.getSource()==Horizontal)
		{	Horizontal.setValue(TD.setHorizontal(
				Horizontal.getValue()));
		}
	}
	
	public void setVerticalScrollbar ()
	{	if (Vertical==null) return;
		int h=TD.computeVerticalSize();
		Vertical.setValues(TD.computeVertical(),h,0,1000+h);
	}
	
	public void setText (String S)
	{	TD.unmark(); Start=End=null;
		TD.setText(S);
		setVerticalScrollbar();
	}
	
	public void save (PrintWriter fo)
	{	TD.save(fo);
	}
	
	public void appendLine0 (String s)
	{	TD.appendLine0(s);
	}
	
	public void appendLine0 (String s, Color c)
	{	TD.appendLine0(s,c);
	}
	
	boolean Dragging=false;
	
	public void mouseClicked (MouseEvent e) {}
	
	public void mousePressed (MouseEvent e)
	{	if (e.isPopupTrigger() || e.isMetaDown())
		{	PM.show(e.getComponent(),e.getX(),e.getY());
			X=e.getX(); Y=e.getY();
		}
		else
		{	TD.unmark(Start,End);
			Start=TD.getposition(e.getX(),e.getY());
			Start.oneleft();
			End=null;
		}
	}
	
	public Dimension getPreferredSize ()
	{	return new Dimension(150,200);
	}
	
	public Dimension getMinimumSize ()
	{	return new Dimension(150,200);
	}

	public void mouseReleased (MouseEvent e)
	{	Dragging=false;
	}
	
	public void mouseEntered (MouseEvent e)
	{
	}
	
	public void mouseExited (MouseEvent e)
	{
	}
	
	public void mouseMoved (MouseEvent e) {}
	
	public void mouseDragged (MouseEvent e)
	{	TD.unmark(Start,End);
		TextPosition h=TD.getposition(e.getX(),e.getY());
		if (h!=null) End=h;
		TD.mark(Start,End);
	}
	
	public void actionPerformed (ActionEvent e)
	{	String o=e.getActionCommand();
		if (o.equals(Global.name("block.copy","Copy"))) TD.copy(Start,End);
		else if (o.equals(Global.name("block.begin","Begin Block")))
		{	TD.unmark(Start,End);
			Start=TD.getposition(X,Y);
			Start.oneleft();
			if (End==null && TD.L.last()!=null)
			{	End=TD.lastpos();
			}
			TD.mark(Start,End);
		}
		else if (o.equals(Global.name("block.end","End Block")))
		{	TD.unmark(Start,End);
			End=TD.getposition(X,Y);
			if (Start==null && TD.L.first()!=null)
			{	Start=new TextPosition(TD.L.first(),0,0);
			}
			TD.mark(Start,End);
		}
	}
	
	public void keyPressed (KeyEvent e) {}
	
	public void keyReleased (KeyEvent e)
	{	if (e.isControlDown() && e.getKeyCode()==KeyEvent.VK_C
			&& Start!=null && End!=null)
		{	TD.copy(Start,End);
		}
	}
	
	public void keyTyped (KeyEvent e) {}
	
	public void setTabWidth (int t)
	{	TD.setTabWidth(t);
	}
	
	public void showFirst ()
	{	TD.showFirst();
		setVerticalScrollbar();
		TD.repaint();
	}

	public void showLast ()
	{	TD.showlast();
		setVerticalScrollbar();
		TD.repaint();
	}

	public boolean hasFocus () { return false; }

	public void setBackground (Color c)
	{	TD.setBackground(c);
		P3D.setBackground(c);
		super.setBackground(c);
	}	

	public void up (int n)
	{	for (int i=0; i<n; i++) TD.verticalUp();
		setVerticalScrollbar();
	}

	public void down (int n)
	{	for (int i=0; i<n; i++) TD.verticalDown();
		setVerticalScrollbar();
	}

	public void pageUp ()
	{	TD.verticalPageUp();
		setVerticalScrollbar();
	}

	public void pageDown ()
	{	TD.verticalPageDown();
		setVerticalScrollbar();
	}
	
	public void resized ()
	{	
	}

	public static void main (String args[])
	{	Frame f=new Frame();
		f.setLayout(new BorderLayout());
		Viewer v=new Viewer(true,false);
		f.add("Center",v);
		f.setSize(300,300);
		f.setVisible(true);
		v.append("test test test test test test test");
		v.appendLine("test test test test test test test");
		v.appendLine("test test test test test test test");
		v.appendLine("test test test test test test test");
	}
	
}
