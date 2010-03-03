package rene.zirkel.dialogs;

/*
package rene.dialogs;

import java.awt.*;
import rene.gui.*;

/**
A canvas to display a sample of the chosen font.
The samples is drawn from the GetFontSize dialog.
*/

import java.awt.*;
import rene.gui.*;

class ExampleCanvas extends Canvas
{   ZulFontEditor GFS;
    public ExampleCanvas (ZulFontEditor gfs)
    {   GFS=gfs;
    }
    public void paint (Graphics g)
    {   GFS.example(g,getSize().width,getSize().height);
    }
    public Dimension getPreferredSize ()
    {	return new Dimension(200,100);
    }
}

/**
A dialog to get the font size of the fixed font and its name.
Both items are stored as a Global Parameter.
*/

public class ZulFontEditor extends CloseDialog
{	String FontTag;
	TextField FontName;
	IntField FontSize;
	Choice Fonts,Mode,LargeSmall;
	Canvas Example;
	String E=Global.name("fonteditor.sample");
	String FontDef;
	int SizeDef;
	/**
	@param fonttag,fontdef the font name resource tag and its default value
	@param sizetag,sizedef the font size resource tag and its default value
	*/
	public ZulFontEditor (Frame f, String fonttag,
		String fontdef, int sizedef,
		boolean bold, boolean italic, 
		boolean largesmallonly)
	{	super(f,Global.name("fonteditor.title"),true);
		FontDef=fontdef;
		SizeDef=sizedef;
		FontTag=fonttag;
		setLayout(new BorderLayout());
		Panel p=new MyPanel();
		p.setLayout(new GridLayout(0,2));
		p.add(new MyLabel(Global.name("fonteditor.name")));
		p.add(FontName=new TextFieldAction(this,"FontName"));
		FontName.setText(Global.getParameter(fonttag+".name",fontdef));
		p.add(new MyLabel(Global.name("fonteditor.available")));
		p.add(Fonts=new ChoiceAction(this,"Fonts"));
		Fonts.add("Dialog");
		Fonts.add("SansSerif");
		Fonts.add("Serif");
		Fonts.add("Monospaced");
		Fonts.add("DialogInput");
		Fonts.add("Courier");
		Fonts.add("TimesRoman");
		Fonts.add("Helvetica");
		Fonts.select(FontName.getText());
		p.add(new MyLabel(Global.name("fonteditor.mode")));
		p.add(Mode=new ChoiceAction(this,"Mode"));
		Mode.add(Global.name("fonteditor.plain"));
		if (bold) Mode.add(Global.name("fonteditor.bold"));
		if (italic) Mode.add(Global.name("fonteditor.italic"));
		String name=Global.getParameter(fonttag+".mode","plain");
	    if (name.startsWith("bold")) Mode.select(1);
	    else if (name.startsWith("italic")) Mode.select(2);
	    else Mode.select(0);
		p.add(new MyLabel(Global.name("fonteditor.size")));
		if (largesmallonly)
		{	p.add(LargeSmall=new ChoiceAction(this,"LargeSmall"));
			LargeSmall.add(Global.name("fonteditor.normal"));
			LargeSmall.add(Global.name("fonteditor.large"));
			LargeSmall.select(Global.getParameter(fonttag+".large",false)?1:0);
		}
		else
		{	p.add(FontSize=new IntField(this,"FontSize",
					Global.getParameter(fonttag+".size",sizedef)));
		}
		add("North",new Panel3D(p));
		Example=new ExampleCanvas(this);
		add("Center",new Panel3D(Example));
		Panel bp=new MyPanel();
		bp.add(new ButtonAction(this,Global.name("OK"),"OK"));
		bp.add(new ButtonAction(this,Global.name("fonteditor.default"),"Default"));
		bp.add(new ButtonAction(this,Global.name("close"),"Close"));
		add("South",new Panel3D(bp));
		pack();
	}
	public void doAction (String o)
	{	if ("OK".equals(o))
	    {	Global.setParameter(FontTag+".name",FontName.getText());
			String s="plain";
    		if (mode()==Font.BOLD) s="bold";
    		else if (mode()==Font.ITALIC) s="Italic";
    		Global.setParameter(FontTag+".mode",s);
		    if (FontSize!=null) Global.setParameter(FontTag+".size",FontSize.value(3,50));
		    else Global.setParameter(FontTag+".large",LargeSmall.getSelectedIndex()==1);
		    doclose();
	    }
		else if ("Default".equals(o))
		{	if (FontSize!=null) FontSize.set(SizeDef);
			else LargeSmall.select(0); 
			FontName.setText(FontDef);
			Mode.select(0);
		}
		else super.doAction(o);
        Example.repaint();
	}
	public void itemAction (String s, boolean flag)
	{   FontName.setText(Fonts.getSelectedItem());
	    Example.repaint();
	}
	int mode ()
	{   if (Mode.getSelectedItem().equals(Global.name("fonteditor.bold")))
	        return Font.BOLD;
	    else if (Mode.getSelectedItem().equals(Global.name("fonteditor.italic")))
	        return Font.ITALIC;
	    else return Font.PLAIN;
	}
	public void example (Graphics g, int w, int h)
	{   int size=12;
		if (FontSize!=null) size=FontSize.value(3,50);
		Font f=new Font(FontName.getText(),mode(),size);
	    g.setFont(f);
	    FontMetrics fm=g.getFontMetrics();
	    for (int i=1; i<=4; i++)
	        g.drawString(E,5,
	        	5+fm.getLeading()+fm.getAscent()+i*fm.getHeight());
	}
}
