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
 
 
 package rene.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JPanel;

import rene.gui.ButtonAction;
import rene.gui.CloseDialog;
import rene.gui.Global;
import rene.gui.MyLabel;
import rene.gui.MyList;
import rene.gui.MyPanel;
import rene.gui.Panel3D;

/**
This is a general class to one in a list of items (like makros,
plugins or tools in the JE editor).
*/

public class ItemEditor extends CloseDialog
	implements ItemListener
{	Frame F;
	/** An AWT list at the left */
	MyList L;
	/** Aborted? */
	boolean Aborted=true;
	/** A vector of ItemEditorElement objects */
	Vector V;
	/** A panel to display the element settings */
	ItemPanel P;
	/** The name of this editor */
	String Name;
	/** The displayed item */
	int Displayed=-1;
	/** possible actions */
	public final static int NONE=0,SAVE=1,LOAD=2;
	/** save or load action */
	int Action=NONE;

	/**
	@param p The item editor panel
	@param v The vector of item editor elements
	@param prompt The prompt, displayed in the north
	*/
	public ItemEditor (Frame f, 
		ItemPanel p, Vector v, String name, String prompt)
	{	this(f,p,v,name,prompt,true,true,false,"");
	}
	public ItemEditor (Frame f, 
		ItemPanel p, Vector v, String name, String prompt,
		boolean allowChanges, boolean allowReorder, boolean allowSave,
		String extraButton)
	{	super(f,Global.name(name+".title"),true);
		Name=name; F=f; P=p;
		setLayout(new BorderLayout());
		
		// Title String:
		JPanel title=new MyPanel();
		title.add(new MyLabel(prompt));
		add("North",title);
		
		// Center panel:
		JPanel center=new MyPanel();
		center.setLayout(new BorderLayout(5,5));
		
		// Element List:
		center.add("West",L=new MyList(10));
		L.addItemListener(this);
		
		// Editor JPanel:
		JPanel cp=new MyPanel();
		cp.setLayout(new BorderLayout());
		cp.add("North",P);
		cp.add("Center",new MyPanel());
		center.add("Center",cp);

		add("Center",new Panel3D(center));

		// Buttons:
		JPanel buttons=new MyPanel();
		buttons.setLayout(new GridLayout(0,1));

		if (allowChanges)
		{	JPanel buttons1=new MyPanel();
			buttons1.add(new ButtonAction(this,
				Global.name("itemeditor.insert"),"Insert"));
			buttons1.add(new ButtonAction(this,
				Global.name("itemeditor.new"),"New"));
			buttons1.add(new ButtonAction(this,
				Global.name("itemeditor.delete"),"Delete"));
			buttons.add(buttons1);
		}
		
		if (allowReorder)
		{	JPanel buttons2=new MyPanel();
			buttons2.add(new ButtonAction(this,
				Global.name("itemeditor.down"),"Down"));
			buttons2.add(new ButtonAction(this,
				Global.name("itemeditor.up"),"Up"));
			buttons.add(buttons2);
		}
		
		JPanel buttons3=new MyPanel();
		buttons3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttons3.add(new ButtonAction(this,
			Global.name("OK"),"OK"));
		buttons3.add(new ButtonAction(this,
			Global.name("abort"),"Close"));
		buttons.add(buttons3);

		if (allowSave)
		{	JPanel buttons4=new MyPanel();
			buttons4.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttons4.add(new ButtonAction(this,
				Global.name("save"),"Save"));
			buttons4.add(new ButtonAction(this,
				Global.name("load"),"Load"));
			if (!extraButton.equals(""))
			{	buttons4.add(new ButtonAction(this,
					extraButton,"Extra"));
			}
			buttons.add(buttons4);
		}
		
		add("South",new Panel3D(buttons));

		V=new Vector();
		for (int i=0; i<v.size(); i++) V.addElement(v.elementAt(i));
		init();
		
		pack();
	}
	
	/**
	@param v A vector of item editor elements.
	*/
	public void init ()
	{	for (int i=0; i<V.size(); i++)
		{	ItemEditorElement e=(ItemEditorElement)V.elementAt(i);
			L.add(e.getName());
		}
		if (V.size()>0)
		{	L.select(0);
			select();
		}
	}

	/**
	React on Item changes.
	*/	
	public void itemStateChanged (ItemEvent e)
	{	if (e.getSource()==L)
		{	if (Displayed>=0) define(Displayed);
			select();
		}
	}

	/**
	Show the currently selected item on the item panel.
	*/
	public void select ()	
	{	int i=L.getSelectedIndex();
		if (i<0) return;
		P.display((ItemEditorElement)V.elementAt(i));
		Displayed=i;
	}

	public void doAction (String o)
	{	if (o.equals("Delete"))
		{	delete();
		}
		else if (o.equals("Insert"))
		{	insert();
		}
		else if (o.equals("New"))
		{	P.newElement();
		}
		else if (o.equals("Up"))
		{	up();
		}
		else if (o.equals("Down"))
		{	down();
		}
		else if (o.equals("OK"))
		{	noteSize(Name);
			define();
			Aborted=false;
			doclose();
		}
		else if (o.equals("Help"))
		{	P.help();
		}
		else if (o.equals("Save"))
		{	define();
			Action=SAVE;
			Aborted=false;
			doclose();
		}
		else if (o.equals("Load"))
		{	define();
			Action=LOAD;
			Aborted=false;
			doclose();
		}
		else if (o.equals("Extra"))
		{	if (P.extra(V))
			{	Aborted=false;
				doclose();
			}
		}
		else super.doAction(o);
	}
	
	/**
	Insert the current element, renaming if necessary.
	*/
	void insert ()
	{	String name=P.getName();
		int Selected=L.getSelectedIndex();
		if (Selected<0) Selected=0;
		while (find(name)) name=name+"*";
		P.setName(name);
		ItemEditorElement e=P.getElement();
		L.add(e.getName(),Selected);
		L.select(Selected);
		V.insertElementAt(e,Selected);
	}

	/**
	Changes an item.
	*/
	void define (int Selected)
	{	String name=P.getName();
		if (name.equals("")) return;
		if (!L.getItem(Selected).equals(name))
			L.replaceItem(name,Selected);
		V.setElementAt(P.getElement(),Selected);
		P.notifyChange(V,Selected);
	}
	
	/**
	Changes the currently selected item.
	*/
	void define ()
	{	int Selected=L.getSelectedIndex();
		if (Selected<0) return;
		define(Selected);
		L.select(Selected);
	}

	/**
	Find a plugin by name.
	@return true, if it exists.
	*/
	boolean find (String name)
	{	int i;
		for (i=0; i<V.size(); i++)
		{	ItemEditorElement t=((ItemEditorElement)V.elementAt(i));
			if (t.getName().equals(name)) return true;
		}
		return false;
	}

	/**
	Delete the current selected item.
	*/
	void delete ()
	{	int Selected=L.getSelectedIndex();
		if (Selected<0) return;
		V.removeElementAt(Selected);
		L.remove(Selected);
		if (L.getItemCount()==0) return;
		if (Selected>=L.getItemCount()) Selected--;
		L.select(Selected);
		select();
	}

	/**
	Push the selected item one down.
	*/
	void down ()
	{	define();
		int Selected=L.getSelectedIndex();
		if (Selected<0 || Selected+1>=V.size()) return;
		ItemEditorElement 
			now=(ItemEditorElement)V.elementAt(Selected),
			next=(ItemEditorElement)V.elementAt(Selected+1);
		V.setElementAt(next,Selected); 
		V.setElementAt(now,Selected+1);
		L.replaceItem(next.getName(),Selected); 
		L.replaceItem(now.getName(),Selected+1);
		Selected=Selected+1;
		L.select(Selected);
		select();
	}

	/**
	Push the selected item one up.
	*/
	void up ()
	{	define();
		int Selected=L.getSelectedIndex();
		if (Selected<=0) return;
		ItemEditorElement 
			now=(ItemEditorElement)V.elementAt(Selected),
			prev=(ItemEditorElement)V.elementAt(Selected-1);
		V.setElementAt(prev,Selected); 
		V.setElementAt(now,Selected-1);
		L.replaceItem(prev.getName(),Selected); 
		L.replaceItem(now.getName(),Selected-1);
		Selected=Selected-1;
		L.select(Selected);
		select();
	}

	/**
	@return If aborted.
	*/
	public boolean isAborted ()
	{	return Aborted;
	}

	/**
	@return The list of item elements.
	*/
	public Vector getElements ()
	{	return V;
	}
	
	/**
	Return the action, if there is one
	@return NONE,LOAD,SAVE
	*/
	public int getAction ()
	{	return Action;
	}
}
