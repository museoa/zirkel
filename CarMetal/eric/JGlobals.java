/* 
Copyright 2006 Eric Hakenholz
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
package eric;

import eric.bar.JPropertiesBar;
import eric.controls.JCanvasPanel;
import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import rene.gui.Global;
import rene.util.parser.StringParser;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelApplet;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroItem;
import rene.zirkel.macro.MacroRunner;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.TextObject;

/**
 *
 * @author erichake
 */
public class JGlobals {

    static public String DefaultIcons=" new load ln manage_macros properties_panel history_panel help_panel move rename edit zoom back undo hide delete color0 color1 color2 color3 color4 color5 ln thickness0 thickness1 thickness2 ln type0 type1 type2 type3 type4 type5 ";
    static public String RestrictedIcons="";
    static public int appletwidth=-1;
    static public int appletheight=-1;
    static public String GlobalFont="Dialog";
    static public int MenuTextSize=12;
    static public String FontURL="";
    static public String ChineseFontURL="http://db-maths.nuxit.net/CaRMetal/fonts/fireflysung.zip";
    static public JLogoWindow JLW;
    static public JPropertiesBar JPB=null;
//    static public GraphicsEnvironment GE = GraphicsEnvironment.getLocalGraphicsEnvironment();
//    static public Rectangle SCREEN= GE.getMaximumWindowBounds();
//    static public GraphicsEnvironment GE=GraphicsEnvironment.getLocalGraphicsEnvironment();
//    static public Rectangle SCREEN=GE.getMaximumWindowBounds();
            

     
    // get the path from the top of the jar archive
     // e.g. getPath("eric/ImageFilter.class")
    static public URL getPath(String s) {
        URL myPath=null;
        try {
            myPath=JGlobals.class.getResource("/"+s);
        }catch(Exception e){
        }
        return myPath;
    }


    static public void ShowLogoWindow() {
        JLW=new JLogoWindow();
    }

    static public void DisposeLogoWindow() {
        JLW.dispose();
    }

    static public void CreatePopertiesBar() {

        
        if (JPB==null) {
            JPB=new JPropertiesBar();
            return;
        }
        ;
        boolean vis=JPB.isVisible();
        JPB.dispose();
        JPB=new JPropertiesBar();
        JPB.showme(vis);
    }

    static public String Loc(String s) {
        ResourceBundle Strs=ResourceBundle.getBundle("eric/docs/JZirkelProperties");
        return Strs.getString(s);

//        return JMacrosTools.CurrentJZF.Strs.getString(s);
    }
    
    static public String CurrentLanguage(){
        ResourceBundle Strs=ResourceBundle.getBundle("eric/docs/JZirkelProperties");
        return Strs.getLocale().getLanguage();
    }

    static public String AppPath() {

        String mypath=System.getProperty("java.class.path");

        mypath=mypath.split(System.getProperty("path.separator"))[0];
        String sep=System.getProperty("file.separator");

        while (!(mypath.endsWith(sep))) {
            mypath=mypath.substring(0, mypath.length()-1);
        }
        return mypath;
    }

    static public String RestrictFileName(String filename) {
        if ((filename.endsWith(".r.zir"))||(filename.endsWith(".r.zirz"))) {
            return filename;
        }
        if (filename.endsWith(".zir")) {
            int i=filename.length()-4;
            return filename.substring(0, i)+".r.zir";
        }
        if (filename.endsWith(".zirz")) {
            int i=filename.length()-5;
            return filename.substring(0, i)+".r.zirz";
        }
        return filename+".r.zir";
    }

    static public void setRestrictedIcons(String icons) {
        RestrictedIcons=icons;
    }

    // Called only when JZirkelFrame.loadfile is called :
    static public void CheckRestrictedIcons(String icons) {
        boolean restrictstatefound=!(icons.equals(""));
        if ((!restrictstatefound)&&(!JMacrosTools.CurrentJZF.restrictedSession)) {
            JMacrosTools.CurrentJZF.busy=false;
            JMacrosTools.CurrentJZF.JPM.setSelected("move", true);
            return;
        }
        ;
        JMacrosTools.CurrentJZF.ZContent.ShowLeftPanel(0);
        setRestrictedIcons(icons);
        JMacrosTools.CurrentJZF.restricted=(restrictstatefound);
        JMacrosTools.CurrentJZF.restrictedSession=(restrictstatefound);
        JMacrosTools.CurrentJZF.ZF.RestrictIcons.setState(restrictstatefound);
        JMacrosTools.CurrentJZF.ZF.showDefaultIcons(!restrictstatefound);
        if (restrictstatefound) {
            JMacrosTools.CurrentJZF.ZF.loadBuiltInMacros();
        }
//        ZF.remakeIconBar();
        JMacrosTools.CurrentJZF.JPM.dispose();
        JMacrosTools.CurrentJZF.JPM=null;
        JMacrosTools.CurrentJZF.JPM=new JPaletteManager(JMacrosTools.CurrentJZF.ZF, JMacrosTools.CurrentJZF, JMacrosTools.CurrentJZF.IconSize());
        JMacrosTools.CurrentJZF.JPM.setSelected("move", true);
        JMacrosTools.CurrentJZF.GeneralMenuBar.init();
        JMacrosTools.CurrentJZF.busy=false;

    }
    static public ConstructionObject O=null;
    
    static public void SelectPropertiesTab(int i){
        JPB.selectTab(i);
    }

    static public void RefreshBar() {
        if (JPB!=null) JPB.refresh();
    }

    static public void EditObject(ConstructionObject o) {
        
        EditObject(o, true, true);
    }

    static public void EditObject(ConstructionObject o, boolean forcevisible, boolean forcefocus) {
        O=o;
        
        if ((JPB!=null)&&(O!=null)) {
            JPB.setObject(O, forcevisible, forcefocus);
        }
    }

    static public void EditObject(JCanvasPanel jcp) {
        O=jcp.O;
        if ((JPB!=null)&&(O!=null)) {
            JPB.setObject(jcp);
        }
    }

    static public void setLastFilePath(String fname) {
        String sep=System.getProperty("file.separator");
        if (sep.equals("\\")) {
            sep="\\\\";
        }
        String[] pathblocks=fname.split(sep);
        String newpath=pathblocks[0];
        for (int i=1; i<pathblocks.length-1; i++) {
            newpath+=sep+pathblocks[i];
        }
        Global.setParameter("lastfilepath", newpath);
    }

    static public String getLastFilePath() {
        return Global.getParameter("lastfilepath", System.getProperty("user.home"));
    }

    static public boolean isLanguage(String lang, String country) {
        String lng=Global.getParameter("language", Locale.getDefault().toString().substring(0, 2));
        String cnt=Global.getParameter("country", Locale.getDefault().getCountry().toString());
        boolean rep=(lang.equals(lng));
        rep=((rep)&&(country.equals(cnt)));
        return rep;
    }

    static public boolean isFontInstalled(String myfont) {
        Font UF=new Font("this is a weird unknown font", 0, 12);
        Font CF=new Font(myfont, 0, 12);
        boolean notInstalled=(UF.getFontName().equals(CF.getFontName()));
//        if (notInstalled) {
//            int rep=JOptionPane.showConfirmDialog(null, "Sorry, but the requested font is not installed\n" +
//                    "for this language.\n" +
//                    "This language will not be selected." +
//                    "Do you want to download the necessary font ?", "Font not installed", JOptionPane.YES_NO_OPTION);
//            if (rep==JOptionPane.OK_OPTION){
//                JBrowserLauncher.openURL(myurl);
//            }
//        }

        return (!notInstalled);
    }

    static public void changeGlobalFont(String lang) throws Exception {
        GlobalFont="Dialog";
        Font myfont=new Font(GlobalFont, 0, 12);
        if (lang.equals("zh")) {
            String chinesesample="\u4e00";
            // if the standard font can't display chinese caracters :
            if (!(myfont.canDisplayUpTo(chinesesample)==-1)) {
                // if the chinese unicode font is installed :
                if (isFontInstalled("AR PL New Sung")) {
                    GlobalFont="AR PL New Sung";
                } else {
                    FontURL=ChineseFontURL;
                    throw new Exception();
                }
            }
        }
    }

    // get a Color object from a string like "231,145,122"
    public static Color getColor(String s) {
//        String s=tag.getValue("colorbackground");
        StringParser p=new StringParser(s);
        p.replace(',', ' ');
        int red, green, blue;
        red=p.parseint();
        green=p.parseint();
        blue=p.parseint();
        return new Color(red, green, blue);
    }

    public static void XmlTagReader(XmlTag tag) {
        if ((!(rene.zirkel.Zirkel.IsApplet))&&(tag.name().equals("Windowdim"))&&(tag.hasParam("w"))&&(tag.hasParam("h"))) {
//            JMacrosTools.CurrentJZF.Wwidth=Integer.parseInt(tag.getValue("w"));
//            JMacrosTools.CurrentJZF.Wheight=Integer.parseInt(tag.getValue("h"));

        } else if (tag.name().equals("Preferences")) {
            if ((tag.hasParam("minfontsize"))) {
                Global.setParameter("minfontsize", tag.getValue("minfontsize"));
            }
            if ((tag.hasParam("minpointsize"))) {
                Global.setParameter("minpointsize", tag.getValue("minpointsize"));
            }
            if ((tag.hasParam("minlinesize"))) {
                Global.setParameter("minlinesize", tag.getValue("minlinesize"));
            }
            if ((tag.hasParam("arrowsize"))) {
                Global.setParameter("arrowsize", tag.getValue("arrowsize"));
            }
            if ((tag.hasParam("digits.lengths"))) {
                Global.setParameter("digits.lengths", tag.getValue("digits.lengths"));
            }
            if ((tag.hasParam("digits.edit"))) {
                Global.setParameter("digits.edit", tag.getValue("digits.edit"));
            }
            if ((tag.hasParam("digits.angles"))) {
                Global.setParameter("digits.angles", tag.getValue("digits.angles"));
            }
            if ((tag.hasParam("colorbackground"))) {
                Global.setParameter("colorbackground", getColor(tag.getValue("colorbackground")));
            }
            if ((!(rene.zirkel.Zirkel.IsApplet))&&(tag.hasParam("fig3D"))) {
                JMacrosTools.CurrentJZF.is3D=Boolean.valueOf(tag.getValue("fig3D")).booleanValue();
            }
        }
    }

    public static void XmlTagWriter(XmlWriter xml) {
        xml.startTagStart("Windowdim");
        xml.printArg("w", ""+JMacrosTools.CurrentJZF.getSize().width);
        xml.printArg("h", ""+JMacrosTools.CurrentJZF.getSize().height);
        xml.finishTagNewLine();
        xml.startTagStart("Preferences");
        xml.printArg("minfontsize", ""+rene.gui.Global.getParameter("minfontsize", 12));
        xml.printArg("minpointsize", ""+rene.gui.Global.getParameter("minpointsize", 3));
        xml.printArg("minlinesize", ""+rene.gui.Global.getParameter("minlinesize", 1));
        xml.printArg("arrowsize", ""+rene.gui.Global.getParameter("arrowsize", 15));
        xml.printArg("digits.lengths", ""+rene.gui.Global.getParameter("digits.lengths", 4));
        xml.printArg("digits.edit", ""+rene.gui.Global.getParameter("digits.edit", 4));
        xml.printArg("digits.angles", ""+rene.gui.Global.getParameter("digits.angles", 4));
        xml.printArg("colorbackground", ""+rene.gui.Global.getParameter("colorbackground", "230,230,230"));
        xml.printArg("fig3D", String.valueOf(JMacrosTools.CurrentJZF.is3D));
        xml.finishTagNewLine();
    }

    public static int FixFontSize(int fsize) {
        int visualsize=fsize;
        if ((!(rene.zirkel.Zirkel.IsApplet))&&(!(JMacrosTools.CurrentJZF.ZContent.leftpanel==null))) {
            visualsize=Math.round(fsize*JMacrosTools.CurrentJZF.ZF.ZC.getSize().width/JMacrosTools.CurrentJZF.getSize().width);
        }
        return visualsize;
    }
    
    
    /**
     * Check if the current Country uses the comma as decimal separator
     * This static method is used for inputs in the properties bar
     * @return
     */
    public static boolean isDecimalWithComma(){
        return String.format("%1.1f", 2.2).contains(",");
    }
    
    /**
     * Returns a string which represents the double x, with the good
     * decimal separator (dot or comma).
     * @param x
     * @param type
     * @return
     */
    public static String getLocaleNumber(double x,String type){
        String s=String.format("%1."+Global.getParameter("digits."+type, 5)+"f", x);
//        System.out.println("***"+s);
        // skip the last 0s of the number 
        s=s.replaceAll("(,+|\\.+)0+$", "");
        s=s.replaceAll("(,+|\\.+)([0-9]*[1-9]+)0+$", "$1$2");
//        System.out.println(s);
        return s;
    }
    
    
    // Running a builtin macro from the applet toolbar
    public static void runmacro(ZirkelCanvas zc, ZirkelApplet za, String macroname) {
        Vector mc;
        Macro m;
        TextObject t;
        mc=zc.getMacros();
        for (int i=0; i<mc.size(); i++) {
            m=((MacroItem) mc.elementAt(i)).M;
            if (m.getName().equals(macroname)) {
                ((MacroRunner) za.ObjectConstructors[ZirkelFrame.NMacro]).setMacro(m, zc);
                zc.setTool(za.ObjectConstructors[ZirkelFrame.NMacro]);
                za.ObjectConstructors[ZirkelFrame.NMacro].resetFirstTime(zc);
            }
        }
        ;
    }
}
