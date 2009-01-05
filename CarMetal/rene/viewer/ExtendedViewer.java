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

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.swing.JPanel;
import rene.gui.*;
import rene.util.*;

/**
An extended Version of the Viewer. It is able to reformat lines, when
the area is resized. It has no vertical scrollbar. Text is stored into
a separate string buffer, and will be formatted on repaint.
*/

public class ExtendedViewer extends Viewer
	implements AdjustmentListener, MouseListener, MouseMotionListener,
		ActionListener, KeyListener, WheelListener
{	TextDisplay TD;
	Scrollbar Vertical;
	TextPosition Start,End;
	PopupMenu PM;
	int X,Y;
	JPanel P3D;
	MyVector V; // Vector of lines
	StringBuffer B; // Buffer for last line
	boolean Changed=false;
	
	public ExtendedViewer ()
	{	TD=new TextDisplay(this);
		setLayout(new BorderLayout());
		add("Center",P3D=new Panel3D(TD));
		add("East",Vertical=new Scrollbar(Scrollbar.VERTICAL,0,100,0,1100));
		Vertical.addAdjustmentListener(this);
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
		V=new MyVector();
		B=new StringBuffer();
	}

	public void setFont (Font f)
	{	TD.init(f);
	}

	public void appendLine (String s)
	{	B.append(s);
		V.addElement(B.toString());
		B.setLength(0);
		Changed=true;
	}
	
	public void newLine ()
	{	V.addElement(B.toString());
		B.setLength(0);
		Changed=true;
	}	

	public void appendLine (String s, Color c) 
	{	appendLine(s);
	}

	public void append (String s)
	{	B.append(s);
	}

	public void append (String s, Color c) 
	{	append(s);
	}

	public void doUpdate (boolean showlast)
	{
	}
	
	public void update ()
	{	resized();
		showFirst();
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
	}

	public void setVerticalScrollbar ()
	{	if (Vertical==null) return;
		int h=TD.computeVerticalSize();
		Vertical.setValues(TD.computeVertical(),h,0,1000+h);
	}

	public void setText (String S)
	{	TD.unmark(); Start=End=null;
		TD.setText(S);
		V.removeAllElements();
		B.setLength(0);		
		setVerticalScrollbar();
	}

	public void save (PrintWriter fo)
	{	TD.save(fo);
	}

	public void appendLine0 (String s)
	{	appendLine(s);
	}

	public void appendLine0 (String s, Color c)
	{	appendLine(s);
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

	public void paint (Graphics G)
	{	super.paint(G);
	}
	
	public void doAppend (String s)
	{	char a[]=s.toCharArray();
		int w[]=TD.getwidth(a);
		int start=0,end=0;
		int W=TD.getSize().width;
		int goodbreak;
		while (start<a.length && a[start]==' ') start++;
		if (start>=a.length)
		{	TD.appendLine("");
			return;
		}
		int blanks=0;
		String sblanks="";
		int offset=0;
		if (start>0)
		{	blanks=start;
			sblanks=new String(a,0,blanks);
			offset=blanks+w[0];
		}
		while (start<a.length)
		{	int tw=TD.Offset+offset;
			end=start;
			goodbreak=start;
			while (end<a.length && tw<W)
			{	tw+=w[end]; 
				if (a[end]==' ')
					goodbreak=end;
				end++;
			}
			if (tw<W) goodbreak=end;
			if (goodbreak==start) goodbreak=end; 
			if (blanks>0)
				TD.appendLine(sblanks+new String(a,start,goodbreak-start)); 
			else
				TD.appendLine(new String(a,start,goodbreak-start)); 
			start=goodbreak;
			while (start<a.length && a[start]==' ') start++;
		}
	}
	
	public synchronized void resized ()
	{	if (TD.getSize().width<=0) return;
		TD.setText("");
		Enumeration e=V.elements();
		while (e.hasMoreElements())
		{	String s=(String)e.nextElement();
			doAppend(s);
		}
		TD.repaint();
	}

	

	public void mouseWheelMoved(MouseWheelEvent arg0) {
	}
	
}
