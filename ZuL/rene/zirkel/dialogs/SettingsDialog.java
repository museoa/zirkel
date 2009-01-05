/*
 * Created on 22.10.2005
 *
 */
package rene.zirkel.dialogs;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;

import rene.gui.ButtonAction;
import rene.gui.CheckboxAction;
import rene.gui.Global;
import rene.gui.MyPanel;
import rene.gui.Panel3D;
import rene.zirkel.Zirkel;

public class SettingsDialog extends HelpCloseDialog
{	Frame F;
	
	Checkbox MoveName,MoveFixName,QSure,QPointOn,QIntersection,
		QChoice,Indicate,Simple,IconBarTop,IconBarTips,UTF,HTMLUTF,FD,
		SmartBoard,Backups,DoubleClick,SimpleGraphics,SquarePoints,
		SmallIcons,More,GermanPoints; 
	
	public SettingsDialog (Frame frame)
	{	super(frame,Zirkel.name("menu.options.other"),true); 
		F=frame;
		setLayout(new BorderLayout()); 
		
		Panel north=new MyPanel(); 
		north.setLayout(new GridLayout(0,1)); 
		
		MoveName=addcheck(north,"menu.settings.movename"); 
		MoveName.setState(Global.getParameter("options.movename",false)); 
		
		MoveFixName=addcheck(north,"menu.settings.movefixname"); 
		MoveFixName.setState(Global.getParameter("options.movefixname",true)); 
		
		QSure=addcheck(north,"menu.settings.sure"); 
		QSure.setState(Global.getParameter("options.sure",true)); 
		
		QPointOn=addcheck(north,"menu.settings.pointon"); 
		QPointOn.setState(Global.getParameter("options.pointon",false)); 
		
		QIntersection=addcheck(north,"menu.settings.intersection"); 
		QIntersection.setState(Global.getParameter("options.intersection",false)); 
		
		QChoice=addcheck(north,"menu.settings.choice"); 
		QChoice.setState(Global.getParameter("options.choice",true)); 
		
		Indicate=addcheck(north,"menu.settings.indicate"); 
		Indicate.setState(Global.getParameter("options.indicate",true)); 
		
		Simple=addcheck(north,"menu.settings.indicate.simple"); 
		Simple.setState(Global.getParameter("options.indicate.simple",false)); 
		
		IconBarTop=addcheck(north,"menu.settings.iconbartop"); 
		IconBarTop.setState(Global.getParameter("options.iconbartop",true)); 
		
		IconBarTips=addcheck(north,"menu.settings.showtips"); 
		IconBarTips.setState(Global.getParameter("iconbar.showtips",true)); 
		
		FD=addcheck(north,"menu.settings.filedialog"); 
		FD.setState(Global.getParameter("options.filedialog",true)); 
		
		UTF=addcheck(north,"menu.settings.utf"); 
		UTF.setState(Global.getParameter("options.utf",true)); 
		
		HTMLUTF=addcheck(north,"menu.settings.htmlutf"); 
		HTMLUTF.setState(Global.getParameter("options.htmlutf",true)); 
		
		SmartBoard=addcheck(north,"menu.settings.smartboard"); 
		SmartBoard.setState(Global.getParameter("smartboard",false)); 
		
		Backups=addcheck(north,"menu.settings.backups"); 
		Backups.setState(Global.getParameter("options.backups",true)); 
		
		DoubleClick=addcheck(north,"menu.settings.doubleclick"); 
		DoubleClick.setState(Global.getParameter("options.doubleclick",false)); 
		
		SimpleGraphics=addcheck(north,"menu.settings.simplegraphics"); 
		SimpleGraphics.setState(Global.getParameter("simplegraphics",false)); 
		
		/*
		SquarePoints=addcheck(north,"menu.settings.squarepoints"); 
		SquarePoints.setState(Global.getParameter("options.squarepoints",false)); 
		*/
		
		SmallIcons=addcheck(north,"menu.settings.smallicons"); 
		SmallIcons.setState(Global.getParameter("options.smallicons",false)); 
		
		More=addcheck(north,"menu.settings.more"); 
		More.setState(Global.getParameter("options.more",true)); 
		
		GermanPoints=addcheck(north,"menu.settings.germanpoints"); 
		GermanPoints.setState(Global.getParameter("options.germanpoints",false)); 
		
		add("North",new Panel3D(north)); 
		
		Panel south=new MyPanel(); 
		
		south.add(new ButtonAction(this,Zirkel.name("ok"),"OK")); 
		south.add(new ButtonAction(this,Zirkel.name("abort"),"Close"));
		addHelp(south,"settings"); 
		
		add("South",new Panel3D(south)); 
		
		pack(); 
		center(frame); 
		setVisible(true); 
	}
	
	public void doAction (String s)
	{	if (s.equals("OK"))
		{	Global.setParameter("options.movename",MoveName.getState()); 
			Global.setParameter("options.movefixname",MoveFixName.getState()); 
			Global.setParameter("options.pointon",QPointOn.getState()); 
			Global.setParameter("options.sure",QSure.getState()); 
			Global.setParameter("options.intersection",QIntersection.getState()); 
			Global.setParameter("options.choice",QChoice.getState()); 
			Global.setParameter("options.indicate",Indicate.getState()); 
			Global.setParameter("options.indicate.simple",Simple.getState()); 
			Global.setParameter("options.iconbartop",IconBarTop.getState()); 
			Global.setParameter("iconbar.showtips",IconBarTips.getState()); 
			Global.setParameter("options.filedialog",FD.getState()); 
			Global.setParameter("options.utf",UTF.getState()); 
			Global.setParameter("options.htmlutf",HTMLUTF.getState()); 
			Global.setParameter("smartboard",SmartBoard.getState()); 
			Global.setParameter("options.backups",Backups.getState()); 
			Global.setParameter("options.doubleclick",DoubleClick.getState()); 
			if (SimpleGraphics!=null)
				Global.setParameter("simplegraphics",SimpleGraphics.getState()); 
			// Global.setParameter("options.squarepoints",SquarePoints.getState()); 
			Global.setParameter("options.smallicons",SmallIcons.getState()); 
			Global.setParameter("options.more",More.getState()); 
			Global.setParameter("options.germanpoints",GermanPoints.getState()); 
			doclose(); 
		}
		else super.doAction(s); 
	}
	
	public Checkbox addcheck (Panel p, String name)
	{	Checkbox c=new CheckboxAction(this,Zirkel.name(name),name); 
		p.add(c); 
		return c; 
	}
}


