package rene.zirkel;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

import rene.dialogs.ColorEditor;
import rene.dialogs.Warning;
import rene.gui.*;
import rene.util.*;
import rene.zirkel.construction.*;
import rene.zirkel.constructors.*;
import rene.zirkel.dialogs.*;
import rene.zirkel.listener.*;
import rene.zirkel.macro.*;
import rene.zirkel.objects.*;
import rene.zirkel.tools.*;

public class ZirkelApplet extends Applet
	implements IconBarListener, StatusListener, KeyListener, DoneListener,
	DoActionListener, ZirkelCanvasInterface
{	ZirkelCanvas ZC;
	IconBar IA,IB,IC;
	MacroBar IM;
	String filename="test.zir";
	Label Status;
	Color C,CC;
	Frame F;
	String FirstConstructor="point",Tools="all",Options="";
	int CurrentTool=0;
	HistoryTextField Input;
	ConstructionObject Last=null;
	boolean edit;
	boolean jumptostart; // for applets with replay buttons and start()
	
	final static public String DefaultIcons=
		" point line segment ray circle fixedcircle"+
		" parallel plumb circle3 midpoint angle fixedangle"+
		" move tracker objecttracker hide expression area text quadric"+
		" runmacro animate ";
	final static public String DefaultOptions=
		" back undo delete color type thickness"+
		" hidden showcolor macro grid "+
		" draw twolines indicate "
		;
	
	public void init ()
	{	if (getParameter("language")!=null)
		try
		{	Locale.setDefault(new Locale(getParameter("language"),""));
		}
		catch (RuntimeException e) {}
		Global.initBundle("rene/zirkel/docs/ZirkelProperties");
		Count.resetAll();
		CC=C=Global.Background;
		initLightColors();
		initObjectKeys();
		Dimension dscreen=getToolkit().getScreenSize();
		F=new Frame();
		F.setSize(dscreen);
		
		Tools=getParameter("tools");
		if (Tools==null || Tools.equals("defaults")) Tools=DefaultIcons;
		
		Options=getParameter("options");
		if (Options==null || Options.equals("defaults")) Options=DefaultOptions;
		
		Global.setParameter("iconpath","/rene/zirkel/icons/");
		Global.setParameter("iconsize",getParameter("smallicons")==null?32:24);
		if (Options.indexOf("smallicons")>=0) Global.setParameter("iconsize",24);
		Global.setParameter("icontype","png");
		
		String color=getParameter("color");
		if (color!=null)
		{	Global.setParameter("appletcolor",getParameter("color"));
			C=Global.getParameter("appletcolor",SystemColor.window);
			Global.Background=C;
			Global.ControlBackground=C;
			CC=C;
		}
		else
		{	Global.Background=SystemColor.window;
			Global.ControlBackground=SystemColor.control;
			CC=C=Global.Background;
		}
		
		color=getParameter("colorbackground");
		if (color!=null) Global.setParameter("colorbackground",color);
		else Global.removeParameter("colorbackground");
		color=getParameter("colorselect");
		if (color!=null) Global.setParameter("colorselect",color);
		else Global.removeParameter("colorselect");
		ZirkelFrame.SelectColor=Global.getParameter("colorselect",Color.red);
		color=getParameter("colortarget");
		if (color!=null) Global.setParameter("colortarget",color);
		else Global.removeParameter("colortarget");
		color=getParameter("colornochecktarget");
		if (color!=null) Global.setParameter("colornochecktarget",color);
		else Global.removeParameter("colornochecktarget");
		ZirkelFrame.TargetColor=Global.getParameter("colortarget",Color.pink.darker());
		ZirkelFrame.NoCheckTargetColor=Global.getParameter("colornochecktarget",Color.pink);
		ZirkelFrame.initLightColors(Color.white);				

		String font=getParameter("font");
		if (font!=null)
		{	if (font.indexOf("bold")>=0)
				Global.setParameter("font.bold",true);
			if (font.indexOf("large")>=0)
				Global.setParameter("font.large",true);				
		}
		
		if (getParameter("demo")!=null)
		{	initDemo();
			return;
		}
		
		String style=getParameter("style");
		if (style==null) style="plain";
		
		boolean simple=(style.equals("plain") || style.equals("3D") ||
			style.equals("showdow") || style.equals("breaks"));
		edit=!simple;
		boolean status=(style.equals("full") || style.equals("status"));
		boolean icons=(style.equals("full") || style.equals("nonvisual")
			|| style.equals("icons") );
		boolean breaks=(style.equals("breaks"));
		
		
		boolean shadow=true;
		if (style.equals("plain")) shadow=false;
		if (getParameter("shadow")!=null) 
		{	shadow=getParameter("shadow").equals("true");
		}
		
		if (getParameter("edit")!=null) edit=true;
		
		String fontname=getParameter("fontname");
		if (fontname!=null) Global.setParameter("font.name",fontname);
		
		for (int i=0; i<ZirkelFrame.Colors.length; i++)
		{	color=getParameter("color"+i);
			if (color!=null) Global.setParameter("color"+i,color);
			else Global.removeParameter("color"+i);
		}
		
		ZirkelFrame.initLightColors(Color.white);				
		
		Container all=this;
		if (shadow)
		{	all=new Panel();
			all.setLayout(new BorderLayout());
		}

		setLayout(new BorderLayout());

		Global.setParameter("macrobar",Options.indexOf("macrobar")>=0);
		makeMacroBar();
		
		if (icons)
		{	IB=new IconBar(F);
			IB.setBackground(CC);
			IB.setIconBarListener(this);
			StringTokenizer t=new StringTokenizer(Tools);
			int count=0;
			while (t.hasMoreTokens())
			{	t.nextToken(); count++;
			}
			String a[]=new String[count];
			t=new StringTokenizer(Tools);
			count=0;
			while (t.hasMoreTokens())
			{	a[count++]=t.nextToken();
			}
			if (count>0) FirstConstructor=a[0];
			IB.addToggleGroupLeft(a);
			IB.addSeparatorLeft();
			if (Options.indexOf("twolines")>=0 || Options.indexOf("defaults")>=0)
			{	IA=new IconBar(F);
				IA.setBackground(CC);
				IA.setIconBarListener(this);
				setIA(IA,Options);
				Panel north=new Panel();
				north.setBackground(CC);
				north.setLayout(new GridLayout(0,1));
				north.add(IA);
				north.add(IB);
				if (IM!=null) north.add(IM);
				all.add("North",new Panel3D(north,CC));
			}
			else
			{	IA=IB;
				setIA(IB,Options);
				if (IM!=null)
				{	Panel north=new Panel();
					north.setBackground(CC);
					north.setLayout(new GridLayout(0,1));
					north.add(IA);
					if (IM!=null) north.add(IM);
					all.add("North",new Panel3D(north,CC));					
				}
				else
				{	all.add("North",new Panel3D(IB,CC));
				}
			}
		}
		else IA=IB=null;
		
		Global.setParameter("options.intersection",false);
		Global.setParameter("options.pointon",false);
		Global.setParameter("options.choice",false);
		
		if (Options.indexOf("qchoice")>=0)
		{	Global.setParameter("options.choice",true);
		}
		if (Options.indexOf("qintersection")>=0)
		{	Global.setParameter("options.intersection",true);
		}
		if (Options.indexOf("qpointon")>=0)
		{	Global.setParameter("options.pointon",true);
		}
		Global.setParameter("options.indicate",true);
		Global.setParameter("options.indicate.simple",Options.indexOf("noindicate")>=0);
		
		if (getParameter("selectionsize")!=null)
		{	try
			{	double x=new Double(getParameter("selectionsize")).doubleValue();
				Global.setParameter("selectionsize",x);
			}
			catch (Exception e) {}
		}

		ZC=new ZirkelCanvas(!edit,!breaks,!breaks);
		ZC.addMouseListener(ZC);
		ZC.addMouseMotionListener(ZC);
		ZC.setBackground(Global.getParameter("colorbackground",C));
		ZC.setFrame(F);
		ZC.setZirkelCanvasListener(this);
		if (getParameter("showhidden")!=null) ZC.setShowHidden(true);
		if (style.equals("plain")) all.add("Center",ZC);
		else all.add("Center",new Panel3D(ZC,ZC.getBackground()));
		ZC.addStatusListener(this);
		ZC.addKeyListener(this);
		
		setShowNames(false);
		
		if (status)
		{	Status=new Label("");
			Status.setBackground(CC);
			all.add("South",new Panel3D(Status,Status.getBackground()));
		}
		else if (style.equals("nonvisual"))
		{	Input=new HistoryTextField(this,"Input");
			ZC.setTextField(Input);
			ZC.Visual=false;
			setShowNames(true);
			all.add("South",new Panel3D(Input));
		}
		
		try
		{	Global.setParameter("digits.edit",
				Integer.parseInt(getParameter("editdigits")));
		}
		catch (Exception e) {}
		try
		{	Global.setParameter("digits.lengths",
				Integer.parseInt(getParameter("displaydigits")));
		}
		catch (Exception e) {}
		try
		{	Global.setParameter("digits.angles",
				Integer.parseInt(getParameter("angledigits")));
		}
		catch (Exception e) {}
		
		setOption("movename");
		setOption("movefixname");
		ZC.updateDigits();
		
		setOption("nopopupmenu");
		setOption("nomousezoom");
				
		try
		{	Global.setParameter("minpointsize",
				new Double(getParameter("minpointsize")).doubleValue());
		}
		catch (Exception e) {}
		try
		{	Global.setParameter("minlinesize",
				new Double(getParameter("minlinewidth")).doubleValue());
		}
		catch (Exception e) {}
		try
		{	Global.setParameter("minfontsize",
				new Double(getParameter("minfontsize")).doubleValue());
		}
		catch (Exception e) {}
		try
		{	Global.setParameter("arrowsize",
				new Double(getParameter("arrowsize")).doubleValue());
		}
		catch (Exception e) {}

		try
		{	String grid=getParameter("grid");
			ZC.ShowGrid=!grid.equals("none");
			Global.setParameter("grid.fine",grid.equals("coordinates"));
		}
		catch (Exception e) {}
		if (checkParameter("snap","left"))
			Global.setParameter("grid.leftsnap",true);

		if (getParameter("interactive")!=null && 
			getParameter("interactive").equals("false"))
				ZC.setInteractive(false);

		boolean job=false;

		ZC.IW=getSize().width;
		ZC.IH=getSize().height;
		
		if (getParameter("germanpoints")!=null
			&& getParameter("germanpoints").equals("true"))
				Global.setParameter("options.germanpoints",true);
		
		filename=getParameter("file");
		if (filename==null)
		{	filename=getParameter("job");
			job=true;
		}
		if (filename!=null)
		{	boolean firsttry=true;
			while (true)
			{	try
				{	URL url;
					if (filename.toUpperCase().startsWith("HTTP"))
						url=new URL(
							firsttry?FileName.toURL(filename):filename);
					else
						url=new URL(getCodeBase(),
							firsttry?FileName.toURL(filename):filename);
					ZC.clear();
					InputStream in=url.openStream();
					if (ZirkelFrame.isCompressed(filename))
						in=new GZIPInputStream(in);
					showStatus(Global.name("loading"));
					ZC.load(in);
					toggleGrid(ZC.ShowGrid);
					if (job)
					{	ZC.displayJob(true);
						ZC.setDoneListener(this);
					}
					if (icons) iconPressed(FirstConstructor);
					in.close();
					if (getParameter("background")!=null)
					{	Image i=getToolkit().getImage(new URL(getCodeBase(),
							getParameter("background")));
						MediaTracker mt=new MediaTracker(this);
						mt.addImage(i,0);
						mt.waitForID(0);
						if (mt.checkID(0) && !mt.isErrorAny())
						{	ZC.setBackground(i);
						}
					}
					ZC.repaint();
				}
				catch (Exception e)
				{	if (firsttry)
					{	firsttry=false; continue;
					}
					Warning w=new Warning(F,FileName.chop(32,""+e,64),
						Zirkel.name("message"),true);
					w.center(F);
					w.setVisible(true);
					showStatus(""+e);
					e.printStackTrace();
					System.out.println(e);
				}
				break;
			}
		}

		if (breaks)
		{	IC=new IconBar(F);
			IC.setBackground(CC);
			IC.setIconBarListener(this);
			IC.addLeft("allback");
			if (haveBreaks()) IC.addLeft("nextbreak");
			else IC.addLeft("oneforward");
			IC.addLeft("allforward");
			Panel pic=new Panel3D(IC);
			all.add("South",pic);
			IC.setEnabled("nextbreak",false);
			IC.setEnabled("oneforward",false);
			IC.setEnabled("allforward",false);
			ZC.getConstruction().setOriginalOrder(true);
			jumptostart=(getParameter("jumptostart")!=null);
		}

		if (getParameter("restrictedmove")!=null)
		{	Global.setParameter("restrictedmove",true);
		}
		
		if (getParameter("noconfirmation")!=null)
		{	Global.setParameter("confirmation",false);
		}
		
		if (shadow)
		{	Panel sp=new ShadowPanel(all,C);
			this.add("Center",sp);
			if (getParameter("colorshadow")!=null)
			{	Global.setParameter("appletshadowcolor",getParameter("colorshadow"));
				Color sc=Global.getParameter("appletshadowcolor",SystemColor.window);
				sp.setBackground(sc);
			}
		}
		
		if (IA!=null) settype(2);
		ZC.setMacroBar(IM);
		ZC.updateMacroBar();
		ZC.recompute();
		ZC.repaint();
	}
	
	public boolean checkParameter (String param, String is)
	{	return (getParameter(param)!=null && getParameter(param).equals(is));
	}
	public boolean checkParameter (String param)
	{	return checkParameter(param,"true");
	}
	
	public void setOption (String option)
	{	try
		{	Global.setParameter("options."+option,
				getParameter(option).equals("true"));
		}
		catch (Exception e) {}
	}

	DemoRunner DR=null;
	
	void initDemo ()
	{	setLayout(new BorderLayout());
	
		boolean shadow=true;
		if (getParameter("shadow")!=null) 
		{	shadow=getParameter("shadow").equals("true");
		}
		
		Container all=this;
		if (shadow)
		{	all=new Panel();
			all.setLayout(new BorderLayout());
		}

		ZC=new ZirkelCanvas(false,false,false);
		ZC.setBackground(Global.getParameter("colorbackground",C));
		ZC.setFrame(F);
		ZC.setZirkelCanvasListener(this);
		if (getParameter("showhidden")!=null) ZC.setShowHidden(true);
		all.add("Center",new Panel3D(ZC,ZC.getBackground()));

		Status=new Label("",Label.CENTER);
		Status.setBackground(C);
		all.add("South",new Panel3D(Status,Status.getBackground()));
		
		if (shadow)
		{	add("Center",new ShadowPanel(all,C));
		}
		
	}

	void setIA (IconBar IA, String set)
	{	if (set.indexOf("back")>=0)
			IA.addLeft("back");
		if (set.indexOf("delete")>=0)
			IA.addToggleLeft("delete");
		if (set.indexOf("undo")>=0)
			IA.addLeft("undo");
		if (set.indexOf("hidden")>=0)
			IA.addOnOffLeft("hidden");
		if (set.indexOf("showcolor")>=0)
			IA.addMultipleIconLeft("showcolor",ZirkelFrame.Colors.length);
		if (set.indexOf("color")>=0)
			IA.addMultipleIconLeft("color",6);
		if (set.indexOf("colors")>=0)
			IA.addColoredIconLeft("colors",Color.black);
		if (set.indexOf("type")>=0)
			IA.addMultipleIconLeft("type",4);
		if (set.indexOf("thickness")>=0)
			IA.addMultipleIconLeft("thickness",3);
		if (set.indexOf("fillbackground")>=0)
			IA.addOnOffLeft("fillbackground");
		if (set.indexOf("partial")>=0)
			IA.addOnOffLeft("partial");
		if (set.indexOf("plines")>=0)
			IA.addOnOffLeft("plines");
		if (set.indexOf("arrow")>=0)
			IA.addOnOffLeft("arrow");
		if (set.indexOf("showname")>=0)
			IA.addOnOffLeft("showname");
		if (set.indexOf("showvalue")>=0)
			IA.addOnOffLeft("showvalue");
		if (set.indexOf("edit")>=0)
			IA.addToggleLeft("edit");
		if (set.indexOf("obtuse")>=0)
			IA.addOnOffLeft("obtuse");
		if (set.indexOf("solid")>=0)
			IA.addOnOffLeft("solid");
		if (set.indexOf("grid")>=0)
			IA.addOnOffLeft("grid");
		if (set.indexOf("macro")>=0 && !(set.indexOf("macrobar")==set.indexOf("macro")))
			IA.addMultipleToggleIconLeft("macro",3);
		if (set.indexOf("replay")>=0)
			IA.addLeft("replay");
		if (set.indexOf("rename")>=0)
			IA.addToggleLeft("rename");
		if (set.indexOf("ticks")>=0)
			IA.addToggleLeft("ticks");
		if (set.indexOf("zoom")>=0)
			IA.addToggleLeft("zoom");
		if (set.indexOf("comment")>=0)
			IA.addLeft("comment");
		if (set.indexOf("function")>=0)
			IA.addLeft("function");
		if (set.indexOf("draw")>=0)
			IA.addToggleLeft("draw");
	}
	
	public void makeMacroBar ()
	{	if (Global.getParameter("macrobar",true))
		{	IM=new MacroBar(F);
			IM.addKeyListener(this); 
			IM.setIconBarListener(this);
			IM.setBackground(CC);
		}
	}
	
	public void updateMacroBar ()
	{	ZC.updateMacroBar();
	}
	
	Replay RD=null;

	public void iconPressed (String o)
	{	if (RD!=null) RD.doclose();
		ZC.requestFocus();
		if (IA!=null && IA.isControlPressed())
		{	if (o.equals("edit"))
			{	if (CurrentTool!=ZirkelFrame.NEdit) IA.setState("edit",false);
				ZC.editLast();
				ZC.repaint();
			}
			IA.clearShiftControl();
			return;
		}
		for (int i=0; i<ZirkelFrame.ObjectStrings.length; i++)
			if (o.equals(ZirkelFrame.ObjectStrings[i]))
			{	if (i==ZirkelFrame.NMacro) runmacro(IB.isShiftPressed());
				else settool(i);
				return;
			}
		if (IM!=null) // Search in the macro line
		{	Macro m=IM.find(o);
			if (m!=null) 
			{	runMacro(m);
				return;
			}
		}
		if (o.equals("hidden"))
		{	ZC.setShowHidden(IA.getState("hidden"));
		}
		else if (o.equals("partial"))
		{	ZC.setPartial(IA.getState("partial"));
		}
		else if (o.equals("plines"))
		{	ZC.setPartialLines(IA.getState("plines"));
		}
		else if (o.equals("arrow"))
		{	ZC.setVectors(IA.getState("arrow"));
		}
		else if (o.equals("color"))
		{	int n=IA.getMultipleState("color");
			if (n>=0) setcolor(n);
		}
		else if (o.equals("colors"))
		{	ColorEditor ce=new ColorEditor(F,"colors.recent",
				Color.black,ZirkelFrame.Colors,
				ObjectEditDialog.UserC);
			ce.center(F);
			ce.setVisible(true);
			if (!ce.isAborted())
			{	IA.setColoredIcon("colors",ce.getColor());
				setcolor(ce.getColor());
				Global.setParameter("colors.recent",ce.getColor());
				IA.set("colors",true);
			}
		}
		else if (o.equals("showcolor"))
		{	int n=IA.getMultipleState("showcolor");
			if (n>=0) showcolor(n);
		}
		else if (o.equals("showname"))
		{	setShowNames(IA.getState("showname"));
		}
		else if (o.equals("obtuse"))
		{	setObtuse(IA.getState("obtuse"));
		}
		else if (o.equals("solid"))
		{	setSolid(IA.getState("solid"));
		}
		else if (o.equals("showvalue"))
		{	setShowValues(IA.getState("showvalue"));
		}
		else if (o.equals("longnames"))
		{	setLongNames(IA.getState("longnames"));
		}
		else if (o.equals("grid"))
		{	toggleGrid(IA.getState("grid"));
		}
		else if (o.equals("comment"))
		{	showcomment();
		}
		else if (o.equals("function"))
		{	if (IA.isControlPressed()) ZC.createFunction();
			else ZC.createCurve();
		}
		else if (o.equals("type"))
		{	int n=IA.getMultipleState("type");
			if (n>=0) settype(n);
		}
		else if (o.equals("thickness"))
		{	int n=IA.getMultipleState("thickness");
			if (n>=0) setcolortype(n);
		}
		else if (o.equals("fillbackground"))
		{	setfillbackground(IA.getState("fillbackground"));
		}
		else if (o.equals("edit"))
		{	settool(ZirkelFrame.NEdit);
		}
		else if (o.equals("back"))
		{	ZC.back();
			ZC.repaint();
		}
		else if (o.equals("undo"))
		{	ZC.undo();
			ZC.repaint();
		}
		else if (o.equals("replay"))
		{	replay();
		}
		else if (o.equals("macro"))
		{	int n=IA.getMultipleState("macro");
			switch (n)
			{	case 1 :
					settool(ZirkelFrame.NParameters);
					break;
				case 2 :
					settool(ZirkelFrame.NTargets);
					break;
				case 0 :
					definemacro();
					break;
			}
			IA.setState("macro",true);
		}
		else if (o.equals("allback"))
		{	if (Last!=null && Last instanceof TextObject)
				((TextObject)Last).setDoShow(false);
			Enumeration e=ZC.getConstruction().elements();
			if (e.hasMoreElements())
			{	Last=(ConstructionObject)e.nextElement();
				ZC.paintUntil(Last);
				if ((Last instanceof TextObject) && Last.valid()
					&& !Last.isSuperHidden())
				{	((TextObject)Last).setDoShow(true);
				}
			}
			IC.setEnabled("allforward",true);
			IC.setEnabled("nextbreak",true);
			IC.setEnabled("oneforward",true);
			IC.setEnabled("allback",false);
			if (haveBreaks() && !Last.isBreak()) iconPressed("nextbreak");
		}
		else if (o.equals("allforward"))
		{	if (Last!=null && Last instanceof TextObject)
				((TextObject)Last).setDoShow(false);
			ZC.paintUntil(null);
			Last=null;
			IC.setEnabled("allforward",false);
			IC.setEnabled("nextbreak",false);
			IC.setEnabled("oneforward",false);
			IC.setEnabled("allback",true);
		}
		else if (o.equals("nextbreak"))
		{	if (Last!=null && Last instanceof TextObject)
				((TextObject)Last).setDoShow(false);
			Enumeration e=ZC.getConstruction().elements();
			outer: while (e.hasMoreElements())
			{	ConstructionObject next=(ConstructionObject)e.nextElement();
				if (next==Last)
					while (e.hasMoreElements())
					{	Last=(ConstructionObject)e.nextElement();
						if (Last==null || Last.isBreak()) break outer;
					}
			}
			IC.setEnabled("allback",true);
			if ((Last instanceof TextObject) && Last.valid()
				&& !Last.isSuperHidden())
			{	((TextObject)Last).setDoShow(true);
			}
			ZC.paintUntil(Last);
			IC.setEnabled("allback",true);
			IC.setEnabled("nextbreak",e.hasMoreElements());
			IC.setEnabled("allforward",e.hasMoreElements());
		}
		else if (o.equals("oneforward"))
		{	if (Last!=null && Last instanceof TextObject)
				((TextObject)Last).setDoShow(false);
			Enumeration e=ZC.getConstruction().elements();
			outer: while (e.hasMoreElements())
			{	ConstructionObject next=(ConstructionObject)e.nextElement();
				if (next==Last)
					while (e.hasMoreElements())
					{	Last=(ConstructionObject)e.nextElement();
						if (Last==null || !Last.isHidden()) break outer;
						if ((Last instanceof TextObject) && Last.valid()
							&& !Last.isSuperHidden())
						{	((TextObject)Last).setDoShow(true);
							break outer;
						}
					}
			}
			IC.setEnabled("allback",true);
			ZC.paintUntil(Last);
			if (!e.hasMoreElements())
			{	ZC.paintUntil(null);
				IC.setEnabled("allforward",false);
				IC.setEnabled("oneforward",false);
				IC.setEnabled("allback",true);
			}
		}
		else if (IB.isControlPressed() || IA.isControlPressed())
		{	int i=CurrentTool;
			if (o.equals("objecttracker"))
			{	track();
			}
			settool(i);
		}	
		if (IA!=null) IA.clearShiftControl();
		if (IB!=null) IB.clearShiftControl();
	}
	
	public ObjectConstructor ObjectConstructors[]= // constructors
		{	new PointConstructor(),
			new BoundedPointConstructor(),
			new IntersectionConstructor(),
			new LineConstructor(),
			new RayConstructor(),
			new SegmentConstructor(),
			new SegmentConstructor(true),
			new CircleConstructor(),
			new Circle3Constructor(),
			new CircleConstructor(true),
			new ParallelConstructor(),
			new PlumbConstructor(),
			new MidpointConstructor(),
			new AngleConstructor(),
			new AngleConstructor(true),
			new MoverTool(),
			new Tracker(),
			new ObjectTracker(),
			new AnimatorTool(),
			new ExpressionConstructor(),
			new AreaConstructor(),
			new QuadricConstructor(),
			new ImageConstructor(),
			new TextConstructor(),
			new HiderTool(),
			new MacroRunner(),
			new EditTool(),
			new SetParameterTool(),
			new SetTargetsTool(),
			new SaveJob(),
			new DeleteTool(),
			new ReorderTool(),
			new DrawerTool(),
			new RenamerTool(),
			new ZoomerTool(),
		};

	public void settool (int i)
	{	if (IM!=null) IM.deselectAll();
		ZC.setTool(ObjectConstructors[i]);
		CurrentTool=i;
		if (i<ZirkelFrame.IconNumber  && IB.have(ZirkelFrame.ObjectStrings[i]))
			IB.toggle(ZirkelFrame.ObjectStrings[i]);
		else
			IB.unselect(FirstConstructor);
		ObjectConstructors[i].resetFirstTime(ZC); 
		if (i==ZirkelFrame.NTargets) IA.setMultipleState("macro",2);
		else if (i==ZirkelFrame.NParameters) IA.setMultipleState("macro",1);
		else
		{	IA.setMultipleState("macro",0);
			IA.setState("macro",false);
		}
		IA.setState("delete",i==ZirkelFrame.NDelete);
		IA.setState("edit",i==ZirkelFrame.NEdit);
		IA.setState("draw",i==ZirkelFrame.NDraw);
		IA.setState("rename",i==ZirkelFrame.NRename);
		IA.setState("ticks",i==ZirkelFrame.NTicks);
		IA.setState("zoom",i==ZirkelFrame.NZoom);
	}
	
	
	public void setcolor (int c)
	{	IA.setMultipleState("color",c);
		IA.set("colors",false); 
		ZC.setDefaultColor(c);
	}
	
	public void setcolor (Color c)
	{	ZC.setDefaultUserColor(c);
	}
	
	public void settype (int c)
	{	IA.setMultipleState("type",c);
		ZC.setDefaultType(c);
	}
	
	public void setcolortype (int c)
	{	IA.setMultipleState("thickness",c);
		ZC.setDefaultColorType(c);
	}
	
	public void showStatus (String s)
	{	super.showStatus(s);
		if (Status!=null) Status.setText(s);
	}

	public void keyPressed (KeyEvent e) {}
	public void keyReleased (KeyEvent e)
	{	int code=e.getKeyCode();
		int i;
		boolean Shift=e.isShiftDown(),Control=e.isControlDown(),Alt=e.isAltDown();
		if (Control)
		{	if (Options.indexOf("type")>0)
				for (i=0; i<ZirkelFrame.PointKeys.length; i++)
				{	if (ZirkelFrame.PointKeys[i]==code)
					{	settype(i);
						return;
					}
				}
			if (Options.indexOf("color")>0)
				for (i=0; i<ZirkelFrame.ColorKeys.length; i++)
				{	if (ZirkelFrame.ColorKeys[i]==code)
					{	setcolor(i);
						return;
					}
				}
		}
		else if (Alt)
		{	if (Options.indexOf("showcolor")>0)
				for (i=0; i<ZirkelFrame.ColorKeys.length; i++)
				{	if (ZirkelFrame.ColorKeys[i]==code)
					{	showcolor(i);
						return;
					}
				}
			if (Options.indexOf("thickness")>0)
				for (i=0; i<ZirkelFrame.ColorTypeKeys.length; i++)
				{	if (ZirkelFrame.ColorTypeKeys[i]==code)
					{	setcolortype(i);
						return;
					}
				}
		}
		else
		{	switch (code)
			{	case KeyEvent.VK_ESCAPE :
					if (ZC.getCurrentTool() instanceof DrawerTool) ZC.clearDrawings();
					else ZC.reset(); 
					break;
				case KeyEvent.VK_ENTER :
					if (Shift) track(); 
					break;
			}
		}
		if (!e.isActionKey()) return;
		switch (code)
		{	case KeyEvent.VK_F1 :
				showVersion();
				break;
			case KeyEvent.VK_F7 :
				if (Shift || Control)
				{	setShowNames(!IA.getState("showname"));
				}
				break;
			case KeyEvent.VK_F8 :
				if (Shift || Control)
				{	setLongNames(!IA.getState("longnames"));
				}
				break;
			case KeyEvent.VK_F9 :
				if (Shift || Control)
				{	IA.setState("partial",!IA.getState("partial"));
					ZC.setPartial(IA.getState("partial"));
				}
				else
				{	if (Options.indexOf("hidden")<0) break;
					IA.setState("hidden",!IA.getState("hidden"));
					ZC.setShowHidden(IA.getState("hidden"));
				}
				break;
			case KeyEvent.VK_F10 :
				if (Shift || Control)
				{	IA.setState("plines",!IA.getState("plines"));
					ZC.setPartial(IA.getState("plines"));
				}
				else showcomment();
				break;
			case KeyEvent.VK_F11 :
				if (Shift || Control)
				{	IA.setState("arrow",!IA.getState("arrow"));
					ZC.setPartial(IA.getState("arrow"));
				}
				else showconstruction();
				break;
			case KeyEvent.VK_F12 :
				if (Shift || Control)
				{	IA.setState("obtuse",!IA.getState("obtuse"));
					ZC.setObtuse(IA.getState("obtuse"));
				}
				else toggleGrid();
				break;
			case KeyEvent.VK_LEFT :
				if (Shift && ZC.getCurrentTool() instanceof ObjectTracker)
					((ObjectTracker)ZC.getCurrentTool()).increaseOmit();
				else if (Shift && ZC.getCurrentTool() instanceof BreakpointAnimator)
					((BreakpointAnimator)ZC.getCurrentTool()).decreaseSpeed(); 
				else if (Shift && ZC.getCurrentTool() instanceof AnimatorTool)
					((AnimatorTool)ZC.getCurrentTool()).decreaseSpeed(); 
				else ZC.shift(-0.2,0); 
				break;
			case KeyEvent.VK_RIGHT :
				if (Shift && ZC.getCurrentTool() instanceof ObjectTracker)
					((ObjectTracker)ZC.getCurrentTool()).decreaseOmit();
				else if (Shift && ZC.getCurrentTool() instanceof BreakpointAnimator)
					((BreakpointAnimator)ZC.getCurrentTool()).increaseSpeed(); 
				else if (Shift && ZC.getCurrentTool() instanceof AnimatorTool)
					((AnimatorTool)ZC.getCurrentTool()).increaseSpeed(); 
				else ZC.shift(0.2,0); 
				break;
			case KeyEvent.VK_UP :
				ZC.shift(0,0.2); break;
			case KeyEvent.VK_DOWN :
				ZC.shift(0,-0.2); break;
		}
	}
	public void keyTyped (KeyEvent e)
	{	char c=e.getKeyChar();
		if (e.isControlDown() || e.isAltDown()) return;
		int i;
		for (i=0; i<ZirkelFrame.ObjectKeys.length; i++)
		{	if (c==ZirkelFrame.ObjectKeys[i])
			{	if (Tools.indexOf(ZirkelFrame.ObjectStrings[i])>=0)
					settool(i);
				return;
			}
		}
		switch (c)
		{	case KeyEvent.VK_BACK_SPACE :
				ZC.undo();
				ZC.repaint();
				return;
			case '+' : ZC.magnify(1/Math.sqrt(Math.sqrt(2))); break;
			case '-' : ZC.magnify(Math.sqrt(Math.sqrt(2))); break;
		}
	}

	public void initLightColors ()
	{	int n=ZirkelFrame.Colors.length;
		ZirkelFrame.LightColors=new Color[n];
		Color back=C;
		int red=back.getRed(),green=back.getGreen(),blue=back.getBlue();
		double lambda=0.4;
		for (int i=0; i<n; i++)
		{	int r=(int)(red*(1-lambda)+ZirkelFrame.Colors[i].getRed()*lambda);
			int g=(int)(green*(1-lambda)+ZirkelFrame.Colors[i].getGreen()*lambda);
			int b=(int)(blue*(1-lambda)+ZirkelFrame.Colors[i].getBlue()*lambda);
			ZirkelFrame.LightColors[i]=new Color(r,g,b);
		}
	}

	public void initObjectKeys ()
	{	ZirkelFrame.ObjectKeys=new char[ZirkelFrame.ObjectStrings.length];
		for (int i=0; i<ZirkelFrame.ObjectStrings.length; i++)
		{	String shortcut=Zirkel.name("shortcuts."+ZirkelFrame.ObjectStrings[i]);
			if (shortcut.length()>0)
				ZirkelFrame.ObjectKeys[i]=shortcut.charAt(0);
		}
	}


	public void notifyDone ()
	{	repaint();
		try
		{	Thread.sleep(500);
		}
		catch (Exception e) {}
		if (Global.getParameter("confirmation",true))
		{	Warning w=new Warning(F,Zirkel.name("done"),
				Zirkel.name("message"),true);
			w.center(F);
			w.setVisible(true);
		}
		String sol=getParameter("solution");
		if (sol!=null)
		{	try
			{	AppletContext ac=getAppletContext();
				ac.showDocument(new URL(getCodeBase(),FileName.toURL(sol)));
			}
			catch (Exception e)
			{	System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	public void showcolor (int c)
	{	ZC.setShowColor(c);
	}

	public void showcomment ()
	{	if (F==null) return;
		CommentDialog d=new CommentDialog(F,
			ZC.getComment(),Zirkel.name("comment.title"),ZC.displayJob());
		ZC.setComment(d.getText());
	}

	public void showconstruction ()
	{	if (F==null) return;
	}

	public void toggleGrid ()
	{	ZC.toggleShowGrid();
	}

	void definemacro ()
	{	if (!ZC.defineMacro()) return; 
		if (ZC.getOC() instanceof SetTargetsTool)
			settool(ZirkelFrame.NParameters);
		else ZC.getOC().reset(ZC);
	}


	String OldMacro=null;

	public void runmacro (boolean shift)
	{	Macro m=null;
		if (OldMacro==null && ZC.getMacros().size()==1)
			m=((MacroItem)ZC.getMacros().elementAt(0)).M;
		else
		{	m=ZC.chooseMacro(OldMacro);
			if (!shift || m==null) m=ZC.chooseMacro();
		}
		if (m==null)
		{	settool(CurrentTool); return;
		}
		runMacro(m);
	}
	
	public void runMacro (Macro m)
	{	((MacroRunner)ObjectConstructors[ZirkelFrame.NMacro]).
			setMacro(m,ZC);
		settool(ZirkelFrame.NMacro);
		if (IM!=null) IM.select(m);
		IB.setMultipleState("macro",0);
		OldMacro=m.getName();
	}

	public void setShowNames (boolean flag)
	{	if (IA!=null) IA.setState("showname",flag);
		Global.setParameter("options.shownames",flag);
		ZC.setShowNames(flag);
	}

	public void setObtuse (boolean flag)
	{	IA.setState("obtuse",flag);
		Global.setParameter("options.obtuse",flag);
		ZC.setObtuse(flag);
	}

	public void setSolid (boolean flag)
	{	IA.setState("solid",flag);
		Global.setParameter("options.solid",flag);
		ZC.setSolid(flag);
	}

	public void setfillbackground (boolean flag)
	{	IA.setState("fillbackground",flag);
		Global.setParameter("options.fillbackground",flag);
	}

	public void setShowValues (boolean flag)
	{	IA.setState("showvalue",flag);
		Global.setParameter("options.showvalue",flag);
		ZC.setShowValues(flag);
	}

	public void setLongNames (boolean flag)
	{	IA.setState("longnames",flag);
		Global.setParameter("options.longnames",flag);
		ZC.setLongNames(flag);
	}

	public void toggleGrid (boolean grid)
	{	ZC.setShowGrid(grid);
		if (IA!=null) IA.setState("grid",grid);
	}

	public void loadsettings ()
	{	setcolor(ZC.getDefaultColor());
		settype(ZC.getDefaultType());
		setcolortype(ZC.getDefaultColorType());
		IA.setState("partial",ZC.getPartial());
		IA.setState("plines",ZC.getPartialLines());
		IA.setState("arrow",ZC.getVectors());
		ZC.setHidden(false);
	}

	public void doAction (String o)
	{	if (o.equals("Input"))
		{	try
			{	ZC.getConstruction().interpret(ZC,Input.getText(),"");
				Input.remember();
				Input.setText("");
				loadsettings();
				ZC.validate();
				ZC.getConstruction().updateCircleDep();
			}
			catch (ConstructionException e)
			{	ZC.warning(e.getDescription());
			}
		}
	}
	public void itemAction (String o, boolean flag)
	{	
	}

	AnimatorTool A=null;
	
	public void start ()
	{	if (getParameter("demo")!=null)
		{	DR=new DemoRunner(ZC,this,getParameter("demo"),Status);
			return;
		}
		Construction C=ZC.getConstruction();
		ZC.paint(ZC.getGraphics());
		ZC.allowRightMouse(true);
		if (C.TrackP!=null)
		{	try
			{	ConstructionObject
					P=C.find(C.TrackP);
				if (!((P instanceof PointObject) 
					|| (P instanceof PrimitiveLineObject)))
						throw new ConstructionException("");				
				ConstructionObject po[]=
					new ConstructionObject[C.TrackPO.size()];
				for (int i=0; i<po.length; i++)
				{	ConstructionObject o=C.find(
						(String)C.TrackPO.elementAt(i));
					if (o==null || !((o instanceof PointObject) 
						|| (o instanceof PrimitiveLineObject)))
							throw new ConstructionException("");
					po[i]=o;
				}
				PointObject PM=null;
				if (C.TrackPM!=null)
					PM=(PointObject)C.find(C.TrackPM);
				if (C.TrackO!=null)
				{	ConstructionObject O=C.find(C.TrackO);
					if (P==null || (PM==null && (O instanceof ExpressionObject)) || O==null)
						throw new ConstructionException("");
					ObjectTracker ot=new ObjectTracker(P,PM,O,ZC,
						C.Animate,C.Paint,po);
					if (C.Animate) ot.Interactive=false;
					ot.setOmit(C.Omit);
					ZC.setTool(ot);
					if (!edit) ZC.allowRightMouse(false);
					ZC.validate();
					ZC.repaint();
				}
				else
				{	if (P==null)
						throw new ConstructionException("");
					ZC.setTool(new Tracker(P,po));
					if (PM!=null) PM.setSelected(true);
					ZC.validate();
					ZC.repaint();
				}
			}
			catch (Exception e)
			{	
			}			
		}
		else if (C.AnimateP!=null)
		{	try
			{	PointObject
					P=(PointObject)C.find(C.AnimateP);
				if (P==null || !P.moveable())
					throw new ConstructionException("");
				Enumeration e=C.AnimateV.elements();
				while (e.hasMoreElements())
				{	String s=(String)e.nextElement();
					ConstructionObject o=C.find(s);
					if (o==null || !(o instanceof SegmentObject
						|| o instanceof PrimitiveCircleObject
						|| o instanceof PointObject))
							throw new ConstructionException("");				
				}
				ZC.setTool(A=new AnimatorTool(P,C.AnimateV,ZC,C.AnimateNegative,
						C.AnimateOriginal,C.AnimateDelay));
				ZC.allowRightMouse(false);
				A.setInteractive(false);
			}
			catch (Exception e)
			{	// e.printStackTrace();
			}			
		}
		else if (C.AnimateBreakpoints)
		{	BreakpointAnimator bp=new BreakpointAnimator();
			bp.setLoop(C.AnimateLoop);
			bp.setSpeed(C.AnimateTime);
			ZC.allowRightMouse(false);
			ZC.setTool(bp);
			bp.reset(ZC);
		}
		else if (jumptostart)
		{	System.out.println("here");
			iconPressed("allback");
		}
	}
	
	public void showVersion ()
	{	showStatus(
			Zirkel.name("program.name")+" "+Zirkel.name("program.version")
			+" "+Zirkel.name("program.date"));
	}

	public void stop ()
	{	ZC.invalidate();
		if (DR!=null) DR.stop();
	}
	
	public void destroy ()
	{	ZC.invalidate();
		if (DR!=null) DR.stop();
	}
	
	public void replay ()
	{	if (RD!=null) return;
		ZC.getConstruction().setOriginalOrder(true);
		RD=new Replay(F,ZC)
			{	public void doclose ()
				{	RD=null;
					super.doclose();
				}
			};
		Dimension d=getToolkit().getScreenSize();
		RD.setLocation(d.width-100-RD.getSize().width,100);
		ZC.OC.invalidate(ZC);
		RD.setVisible(true);
		ZC.getConstruction().setOriginalOrder(false);
	}
	
	public void replayChosen ()
	{	replay();
	}

	public boolean enabled (String function)
	{	if (Tools.indexOf("all")>=0) return true;
		return Tools.indexOf(function)>=0;
	}	

	public boolean haveBreaks ()
	{	Enumeration e=ZC.getConstruction().elements();
		while (e.hasMoreElements())
		{	if (((ConstructionObject)e.nextElement()).isBreak())
				return true;
		}
		return false;
	}

	public String loadImage() {
		return null;
	}

	public Image doLoadImage(String filename) 
	{	Image i;
		try 
		{	i = getToolkit().getImage(new URL(getCodeBase(),filename));
			MediaTracker mt=new MediaTracker(this);
			mt.addImage(i,0);
			mt.waitForID(0);
			if (mt.checkID(0) && !mt.isErrorAny())
			{	return i;
			}
		}
		catch (Exception e)
		{	showStatus(e.toString());
		}
		return null;
	}

	/**
	 * For external scripting: Interprets a command s using the
	 * internal construction language. (Expl: "A=point()")
	 * @param s
	 * @return Command executed or not
	 */
	public boolean interpret (String s)
	{	try
		{	ZC.getConstruction().interpret(ZC,s);
			ZC.repaint();
			return true;
		}
		catch (ConstructionException e) 
		{	return false;
		}
	}

	/**
	 * For external scripting: Gets the construction as an XML stream
	 * contained in a string.
	 * @return Construction.
	 */
	public String getConstruction ()
	{	ByteArrayOutputStream out=new ByteArrayOutputStream();
		try
		{	ZC.save(out,true,true,false,ZC.getMacros(),"");
			return out.toString("utf-8");
		}
		catch (Exception e)
		{	return "Error";
		}
	}
	
	/**
	 * For external scripting: Receives the construction as an XML stream,
	 * contained in a string.
	 * @param s
	 * @return Success
	 */
	public boolean putConstruction (String s)
	{	try
		{	ByteArrayOutputStream bout=new ByteArrayOutputStream();
			PrintWriter out=new PrintWriter(new OutputStreamWriter(bout,"utf-8"));
			out.print(s);
			out.close();
			byte b[]=bout.toByteArray();
			InputStream in=new ByteArrayInputStream(b);
			ZC.load(in,true,true);
			return true;
		}
		catch (Exception e)
		{	e.printStackTrace();
			return false;
		}
	}
	
	public void track ()
	{	if (ZC.getCurrentTool() instanceof ObjectTracker &&
		((ObjectTracker)ZC.getCurrentTool()).isComplete())
		{	((ObjectTracker)ZC.getCurrentTool()).keep(ZC); 
		}
	}
	
}
