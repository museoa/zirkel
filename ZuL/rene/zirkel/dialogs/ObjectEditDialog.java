package rene.zirkel.dialogs;

// file: PointObject.java

import java.awt.*;
import java.awt.event.*;

import rene.dialogs.ColorEditor;
import rene.gui.*;
import rene.zirkel.*;
import rene.zirkel.objects.*;

/**
 * @author Rene
 * 
 * Dialog to edit a single object.
 * Children can add own icons and buttons 
 *
 *@see PointEditDialog
 */
public class ObjectEditDialog extends HelpCloseDialog
	implements IconBarListener
{	protected MyTextField Text,Unit,Equation;
	protected TextFieldAction Name,Alias;
	protected ConstructionObject O;
	protected IconBar ColorIB,ThicknessIB,IB;
	protected Frame F;
	protected boolean More;
	protected ButtonAction OK;
	protected boolean SuperHide=false,HideChanged=false,
		HidingBreak=false,BreakChanged=false,OKControlDown=false;
	Checkbox Decorative;
	
	public ObjectEditDialog (Frame f, String title, ConstructionObject o)
	{	this(f,title,o,"properties");
	}

	public ObjectEditDialog (Frame f, String title, ConstructionObject o,
		String subject)
	{	super(f,title,true);
		F=f;
		O=o;
		o.getConstruction().changed(true);
		setLayout(new BorderLayout());
		
		// edit options:
		Panel center=new MyPanel();
		center.setLayout(new BorderLayout());
		Panel P=new MyPanel();
		Name=new TextFieldAction(this,"Name",o.getName(),32);
		if (O.canDisplayName())
			Alias=new TextFieldAction(this,"Alias",o.getAlias(),32);
		Text=new MyTextField(o.getText(),32);
		Unit=new MyTextField(o.getUnit(),32);
		P.setLayout(new GridLayout(0,2));
		P.add(new MyLabel(Zirkel.name("edit.name"))); P.add(Name);
		if (Alias!=null)
		{	P.add(new MyLabel(Zirkel.name("edit.alias"))); P.add(Alias);
		}
		P.add(new MyLabel(Zirkel.name("edit.text"))); P.add(Text);
		if (o.hasUnit())
		{	P.add(new MyLabel(Zirkel.name("edit.unit"))); P.add(Unit);
		}
		String eq=o.getEquation();
		if (!eq.equals(""))
		{	P.add(new MyLabel(Zirkel.name("edit.equation"))); 
			Equation=new MyTextField(o.getName(),32);
			Equation.setEditable(false);
			Equation.setText(eq);
			P.add(Equation);
		}
		if (o instanceof PointonObject)
		{	P.add(new MyLabel(Zirkel.name("edit.decorative"))); 
			Decorative=new Checkbox("");
			Decorative.setState(o.isDecorative());
			P.add(Decorative);
		}

		addFirst(P);

		center.add("Center",P);
		Panel cs=new MyPanel();
		cs.setLayout(new GridLayout(0,2));

		ColorIB=new IconBar(this);
		ColorIB.setIconBarListener(this);
		ColorIB.addToggleGroupLeft("color",6);
		ColorIB.toggle("color",O.getColorIndex(true));
		ColorIB.addSeparatorLeft();
		ColorIB.addColoredIconLeft("colors",o.getNormalColor());
		if (o.hasUserColor())
		{	ColorIB.unselect("color0");
			ColorIB.set("colors",true);
		}
		cs.add(new MyLabel("")); cs.add(ColorIB);

		ThicknessIB=new IconBar(this);
		ThicknessIB.addToggleGroupLeft("thickness",4);
		int ct=O.getColorType(true);
		ThicknessIB.toggle("thickness",ct);
		if (O.canFillBackground())
		{	ThicknessIB.addSeparatorLeft();
			ThicknessIB.addToggleLeft("fillbackground");
			ThicknessIB.setState("fillbackground",O.isFillBackground());
		}
		if (o.maybeTransparent())
		{	ThicknessIB.addSeparatorLeft();
			ThicknessIB.addOnOffLeft("solid");
			ThicknessIB.setState("solid",o.isSolid(true));
			if (!o.isFilled())
				ThicknessIB.setEnabled("solid",false);
		}
		cs.add(new MyLabel("")); cs.add(ThicknessIB);

		IB=new IconBar(f);
		IB.setIconBarListener(this);
		IB.addOnOffLeft("hide");
		IB.setState("hide",O.isHidden(true));
		IB.addSeparatorLeft();
		if (O.canDisplayName())
		{	IB.addOnOffLeft("showname");
			IB.setState("showname",O.showName(true));
			if (showsValue())
			{	IB.addOnOffLeft("showvalue");
				IB.setState("showvalue",O.showValue(true));
			}
			IB.addSeparatorLeft();
			IB.addOnOffLeft("bold");
			IB.setState("bold",O.isBold());
			IB.addOnOffLeft("large");
			IB.setState("large",O.isLarge());
			IB.addSeparatorLeft();
		}
		IB.addOnOffLeft("isback");
		IB.setState("isback",O.isBack(true));
		IB.addOnOffLeft("setbreak");
		IB.setState("setbreak",O.isBreak());
		cs.add(new MyLabel("")); cs.add(IB);
		
		addSecond(cs);

		center.add("South",cs);
		add("Center",new Panel3D(center));
		
		// button panel:
		Panel p=new MyPanel();
		addButton(p);
		if (Global.getParameter("options.more",true))
		{	p.add(new ButtonAction(this,Zirkel.name("edit.more"),"More"));
		}
		p.add(OK=new ButtonAction(this,Zirkel.name("edit.ok"),"OK"));
		OK.addMouseListener(new MouseAdapter ()
			{	public void mousePressed (MouseEvent e)
				{	OKControlDown=e.isControlDown();
				}
			}
		);
		OKControlDown=false;
		Button b=new ButtonAction(this,Zirkel.name("edit.cancel"),"Close");
		p.add(b);
		addHelp(p,subject);
		add("South",new Panel3D(p));
		
		More=false;
		pack();
		center(f);
	}
	
	public void doAction (String o)
	{	if (o.equals("OK") || o.equals("Name") || o.equals("Alias") || o.equals("More"))
		{	More=(o.equals("More") || OKControlDown);
			O.setName(Name.getText());
			if (Alias!=null)
			{	if (!Alias.getText().equals(""))
				{	if (O.getAlias()==null || !O.getAlias().equals(Alias.getText()))
						O.setShowName(true);
					O.setAlias(Alias.getText());
				}
				else O.setAlias(null);
			}
			if (o.equals("Name")) IB.setState("showname",true);
			String text=Text.getText();
			if (text.equals(""))
			{	O.setText("",true);
				O.updateText();
			}
			else if (!O.getText().equals(text))
			{	if (!text.endsWith(" ")) text=text+" ";
				O.setText(text,true);
			}
			O.setUnit(Unit.getText());
			if (HideChanged)
			{	O.setHidden(IB.getState("hide"));
				if (SuperHide && IB.getState("hide")) O.setSuperHidden(true);
				else O.setSuperHidden(false);
			}
			int cs=ColorIB.getToggleState("color");
			if (cs>=0) O.setColor(ColorIB.getToggleState("color"));
			else O.setColor(ColorIB.getColoredIcon("colors"));
			O.setColorType(ThicknessIB.getToggleState("thickness"));
			if (O.canFillBackground()) O.setFillBackground(ThicknessIB.getState("fillbackground"));
			O.setShowName(o.equals("Name") || o.equals("Alias") || IB.getState("showname"));
			O.setBold(IB.getState("bold"));
			O.setLarge(IB.getState("large"));
			O.setBack(IB.getState("isback"));
			if (showsValue()) O.setShowValue(IB.getState("showvalue"));
			if (O.maybeTransparent())
			{	O.setSolid(ThicknessIB.getState("solid"));
			}
			if (BreakChanged)
			{	if (IB.getState("setbreak"))
				{	if (HidingBreak) O.setHideBreak(true);
					else O.setBreak(true);
				}
				else O.setBreak(false);
			}
			if (O instanceof PointonObject && Decorative!=null)
			{	O.setDecorative(Decorative.getState());
			}
			doclose();
			setAction();
		}
		else super.doAction(o);
	}
	public void addFirst (Panel P) {}
	public void addSecond (Panel P) {}
	public void addButton (Panel p) {}

	public void setAction () {}
	
	public static Color UserC[]=null;
	
	public static void rememberUserC ()
	{	for (int i=0; i<UserC.length; i++)
		{	Global.setParameter("usercolor"+i,UserC[i]);
		}
	}
	
	public static void getUserC ()
	{	UserC=ColorEditor.getSomeColors();
		for (int i=0; i<UserC.length; i++)
		{	Color c=Global.getParameter("usercolor"+i,UserC[i]);
			UserC[i]=c;
		}
	}
	
	static
	{	getUserC();
	}
	
	public void iconPressed (String o)
	{	if (o.equals("hide"))
		{	HideChanged=true;
			if (IB.isShiftPressed()) SuperHide=IB.getState("hide");
			else SuperHide=false;
		}
		else if (o.equals("setbreak"))
		{	BreakChanged=true;
			if (IB.isShiftPressed()) HidingBreak=IB.getState("setbreak");
			else HidingBreak=false;
		}
		else if (o.equals("colors"))
		{	ColorEditor ce=new ColorEditor(F,"colors.recent",
				O.getColor(),ZirkelFrame.Colors,UserC);
			ce.center(F);
			ce.setVisible(true);
			if (!ce.isAborted())
			{	ColorIB.setColoredIcon("colors",ce.getColor());
				Global.setParameter("colors.recent",ce.getColor());
				ColorIB.unselect("color0");
				ColorIB.set("colors",true);
				rememberUserC();
			}
		}
		else if (o.startsWith("color"))
		{	ColorIB.set("colors",false);
		}
	}
	public void showValue ()
	{	if (showsValue()) IB.setState("showvalue",true);
	}
	public boolean showsValue ()
	{	return true;
	}

	public boolean wantsMore ()
	{	return More;
	}

}
