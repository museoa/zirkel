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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.net.URL;
import java.util.Vector;
import javax.swing.*;
import java.awt.event.*;
import rene.gui.Global;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.constructors.AreaConstructor;
import rene.zirkel.constructors.QuadricConstructor;
import rene.zirkel.tools.ObjectTracker;

public class JIcon extends JButton implements MouseListener {

    String name;
    private rene.zirkel.ZirkelFrame ZF;
    private JPaletteManager JPM;
    private JPaletteZone JPZ;
    Vector group;// button group : null->simple button , contain 1 elt-> togglebutton , contain more elts-> group member
    boolean isSelected; // icon state
    boolean isDisabled=false; // icon disabled ?
    boolean isEntered=false; // Mouseover ?
    private ImageIcon myimage;
    int iconsize;
    static String moveonreselect=",delete,hide,rename,edit,zoom,animate,";
//    Runnable doactualisemacrostree;
    String ToolTipText;
//    String Shortcut;
    public void paintComponent(java.awt.Graphics g) {
        /* I learned things from this pages :
        http://java.sun.com/developer/technicalArticles/GUI/java2d/java2dpart1.html
        http://www.apl.jhu.edu/~hall/java/Java2D-Tutorial.html
         */
//        super.paintComponent(g);
        java.awt.Dimension d=this.getSize();
        int w=d.width;
        int h=d.height;

//        g.drawImage(myimage.getImage(),0,0,w, h,this);

        Graphics2D g2=(Graphics2D) g;




        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);





        if (isDisabled) {

            ImageFilter filter=new GrayFilter(true, 60);


            Image disImage=createImage(new FilteredImageSource(myimage.getImage().getSource(), filter));
            ImageIcon myicn=new ImageIcon(disImage);
            g2.drawImage(myicn.getImage(), 0, 0, w, h, this);



//            ImageIcon forb=new ImageIcon(getClass().getResource("/eric/icons/palette/forbidden.png"));
//            g2.drawImage(forb.getImage(),w-8,h-8,8, 8,this);
            return;
        }

        g2.drawImage(myimage.getImage(), 0, 0, w, h, this);


        if (JPM.MW.EditRestricted) {
            if (JPM.isRestrictedIcon(name)) {
                AlphaComposite ac=
                        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
                g2.setComposite(ac);
                if (JPM.isFirstIconOnLine(name)) {
                    g2.setColor(new Color(0, 100, 0));
                } else {
                    g2.setColor(new Color(100, 0, 0));
                }

                g2.fillRect(1, 1, w-1, h-1);
            }
            ;

        } else {
            if (isSelected) {
                AlphaComposite ac=
                        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
                g2.setComposite(ac);
                g2.setColor(new Color(0, 0, 100));
//                g2.fillRoundRect(1,1,w-1,h-1,14,14);
                g2.fillRect(1, 1, w-1, h-1);
            }
            if (isEntered) {
                AlphaComposite ac=
                        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
                g2.setComposite(ac);
                g2.setColor(new Color(0, 0, 80));
                Stroke stroke=new BasicStroke(3f);
                g2.setStroke(stroke);
                g2.drawRect(2, 2, w-4, h-4);

            }
        }


    }
    
    @Override
    public void setSelected(boolean b){
        isSelected=b;
    }
    
    @Override
    public boolean isSelected(){
        return isSelected;
    }

    public void ShowToolTip() {
        if (JPM.MainPalette.isVisible()) {

            Point pt=this.getLocationOnScreen();
            JPM.MainPalette.ToolTip.ShowTip(ToolTipText, "", pt.x+this.getSize().width/2, pt.y+this.getSize().height+3);
//            JPM.MainPalette.ToolTip.ShowTip(ToolTipText,Shortcut,pt.x+this.getSize().width/2,pt.y+this.getSize().height+3);
        }
    }

    public void HideToolTip() {
        JPM.MainPalette.ToolTip.HideTip();
    }

    private void fixsize(int sze) {
        Dimension d=new Dimension(sze, sze);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setSize(d);
    }

    // Create an Icon wich belongs to group (if not null) :
    public JIcon(rene.zirkel.ZirkelFrame zf, JPaletteManager jpm, JPaletteZone jpz, String nm, Vector mygroup) {
        ZF=zf;
        JPM=jpm;
        JPZ=jpz;
        name=nm;
        this.isSelected=false;

        this.group=mygroup;

        URL myurl=getClass().getResource("/eric/icons/palette/"+name+".png");
        if (myurl==null) {
            myurl=getClass().getResource("/eric/icons/palette/"+name+".gif");
        }
        myimage=new ImageIcon(myurl);
//        this.setIcon(myimage);
        this.setBorder(BorderFactory.createEmptyBorder());
        fixsize(JPM.paletteiconsize);

//        Shortcut=Zirkel.name("shortcuts."+name);
        ToolTipText=JPM.MW.ToolTip(name);

//        if (Shortcut.equals(name)) Shortcut="";
        this.setContentAreaFilled(false);
        this.addMouseListener(this);
        if (group!=null) {
            group.add(this);
        }
    }
    
    @Override
    public String getName(){
        return name;
    }

    // Graphical appearence of button(s) and changing of state :
    private void toggleselect() {
        // remember : isSelected of a simple button never goes to true, so
        // here, group is not a null object...
        if (isSelected) {
            if (group.size()==1) { // I am a togglebutton
                isSelected=false;
//                isEntered=true;
                repaint();
            }
        } else {

            if (group!=null) {

                for (int i=0; i<group.size(); i++) {
                    JButton myicn=(JButton) group.get(i);
                    if (myicn.isSelected()) {
                        myicn.setSelected(false);
                        myicn.repaint();
                    }
                }
                isSelected=true;
                isEntered=false;
                repaint();
            }
        }
        ;
    }

    public void TasksBeforeClick() {
        ZF.CurrentTool=0;
        if (!JPZ.equals(JPM.JPAspect)) {
            AreaConstructor.deletePreview(ZF.ZC);
            QuadricConstructor.deletePreview(ZF.ZC);
        }
        JPM.MW.ZF.ZC.JCM.hideHandles(null);
        if (JPM.MW.ZContent.macros.myJML.controls.createbtn.isSelected()) {
            JPM.MW.ZContent.macros.myJML.createmacropanel.disappeargently();
            JPM.MW.ZContent.macros.myJML.controls.createbtn.setSelected(false);
        }
        ;
        JPM.MW.GeneralMenuBar.definejobitem.setSelected(false);
        toggleselect();
        
    }

    public void ClicOnMe() {
        // if icon was selected and must be deselected with the move tool :
        if ((JIcon.moveonreselect.indexOf(","+name+",")!=-1)&&(isSelected)) {
            JPM.setSelected("move", true);
            return;
        }
        TasksBeforeClick();

        if (name.equals("oneforward")) {
            JPM.Dreplay.iconPressed(name);
        } else if (name.equals("oneback")) {
            JPM.Dreplay.iconPressed(name);
        } else if (name.equals("fastforward")) {
            JPM.Dreplay.iconPressed(name);
        } else if (name.equals("fastback")) {
            JPM.Dreplay.iconPressed(name);
        } else if (name.equals("allforward")) {
            JPM.Dreplay.iconPressed(name);
        } else if (name.equals("setbreak")) {
            JPM.Dreplay.iconPressed(name);
        } else if (name.equals("nextbreak")) {
            JPM.Dreplay.iconPressed(name);
        } else {
            if (name!="null") {
                JPM.Dreplay.doclose();

                if (name.startsWith("type")) {
                    ZF.settype(Integer.parseInt(name.substring(4)));
                    ZF.setinfo("zone_aspect");
                } else if (name.startsWith("thickness")) {
                    JPM.setObjectColorType(Integer.parseInt(name.substring(9)));
                    ZF.setinfo("zone_aspect");
                } else if (name.startsWith("filled")) {
                    JPM.setObjectFilled(isSelected);
                } else if (name.startsWith("color")) {
                    ZF.setinfo("zone_aspect");
                    JPM.setObjectColor(Integer.parseInt(name.substring(5)));
                } else if (name.startsWith("acolor")) {
                    int chx=Integer.parseInt(name.substring(6));
                    Global.setParameter("grid.colorindex", chx);
                    ZF.ZC.setGrid();
                    ZF.ZC.repaint();
                } else if (name.startsWith("athickness")) {
                    int chx=Integer.parseInt(name.substring(10));
                    Global.setParameter("grid.thickness", chx);
                    ZF.ZC.setGrid();
                    ZF.ZC.repaint();
                } else if (name.equals("numgrid")) {
                    Global.setParameter("grid.labels", isSelected);
                    ZF.ZC.setGrid();
                    ZF.ZC.repaint();
                } else if (name.equals("dottedgrid")) {
                    ZF.ZC.AxesOnly=isSelected;
                    Global.setParameter("axesonly", ZF.ZC.AxesOnly);
                    ZF.ZC.setGrid();
                    ZF.ZC.repaint();
                } else if (name.equals("vector")) {
                    JPM.setGoodProperties(name);
                    ZF.setVectors(true);
                    ZF.iconPressed("segment");
                } else if (name.equals("segment")) {
                    JPM.setGoodProperties(name);
                    ZF.setVectors(false);
                    ZF.iconPressed("segment");
                } else if (name.equals("fixedsegment")) {
                    JPM.setGoodProperties(name);
                    ZF.setVectors(false);
                    ZF.iconPressed("fixedsegment");
                } else if (name.equals("image3")) {
                    if (!ZF.haveFile()) {
                        JOptionPane.showMessageDialog(null, JPM.MW.Strs.getString("palette.image.fileerror"));
                        JPM.MW.savefile();
                    }
                    ;
                    if (ZF.haveFile()) {
                        ZF.iconPressed("image");
                    } else {
                        JPM.setSelected("point", true);
                    }
                } else if (name.equals("background")) {
                    ZF.setinfo("background");
                    if (isSelected) {
                        if (!ZF.haveFile()) {
                            JOptionPane.showMessageDialog(null, JPM.MW.Strs.getString("palette.image.fileerror"));
                            JPM.MW.savefile();
                        }
                        ;
                        if (ZF.haveFile()) {
                            rene.gui.Global.setParameter("background.usesize", false);
                            rene.gui.Global.setParameter("background.tile", false);
                            rene.gui.Global.setParameter("background.center", true);
                            ZF.loadBackground();
                            JPM.setSelected("imcenter", true);
                        } else {

                            isSelected=false;
                            repaint();
                        }
                    } else {
                        ZF.dograb(false);
                    }
                } else if (name.equals("imcenter")) {
                    rene.gui.Global.setParameter("background.usesize", false);
                    rene.gui.Global.setParameter("background.tile", false);
                    rene.gui.Global.setParameter("background.center", true);
                    ZF.setinfo("background");
                    ZF.ZC.repaint();

                } else if (name.equals("imtile")) {
                    rene.gui.Global.setParameter("background.usesize", false);
                    rene.gui.Global.setParameter("background.tile", true);
                    rene.gui.Global.setParameter("background.center", false);
                    ZF.setinfo("background");
                    ZF.ZC.repaint();
                } else if (name.equals("imstretch")) {
                    rene.gui.Global.setParameter("background.usesize", true);
                    rene.gui.Global.setParameter("background.tile", false);
                    rene.gui.Global.setParameter("background.center", false);
                    ZF.setinfo("background");
                    ZF.ZC.repaint();
                } else if (name.equals("loadmacros")) {
                    JMacrosTools.OpenMacro("");
                } else if (name.equals("new")) {
                    JMacrosTools.NewWindow();
//                    JPM.MW.newfile();
//                    JPM.MW.ZContent.props.myJOP.clear();
                } else if (name.equals("load")) {
                    JMacrosTools.OpenFile();
//                    JGlobals.JPB.clearme();
//                    JPM.MW.ZContent.props.myJOP.clear();
                } else if (name.equals("save")) {
                    JPM.MW.savefile();
                } else if (name.equals("allback")) {
//                    ZF.settool(ZF.NParameters);
                    JPM.Dreplay.dispose();
                    JPM.Dreplay=new rene.zirkel.dialogs.Replay(ZF, ZF.ZC);
                    JPM.Dreplay.iconPressed(name);
                } else if (name.equals("partial")) {
                    ZF.ZC.setPartial(isSelected);
                } else if (name.equals("plines")) {
                    ZF.ZC.setPartialLines(isSelected);
                } else if (name.equals("showvalue")) {
                    JPM.setObjectShowValue(isSelected);
                } else if (name.equals("hidden")) {
                    JPM.MW.GeneralMenuBar.hiddenitem.setSelected(isSelected);
                    ZF.itemAction("menu.options.hidden", isSelected);
                    ZF.setinfo("hidden");
                } else if (name.equals("showname")) {
                    JPM.setObjectShowName(isSelected);
                } else if (name.equals("bold")) {
                    JPM.setObjectBold(isSelected);
                } else if (name.equals("large")) {
                    JPM.setObjectLarge(isSelected);
                } else if (name.equals("longnames")) {
                    ZF.setLongNames(isSelected);
                    ZF.setinfo("defaults");
                } else if (name.equals("obtuse")) {
                    JPM.setObjectObtuse(isSelected);
                } else if (name.equals("solid")) {
                    JPM.setObjectSolid(isSelected);
//                    ZF.setSolid(isSelected);
//                    ZF.setinfo("defaults");
                } else if (name.equals("grid")) {
                    ZF.ZC.ShowGrid=isSelected;
                    JPM.MW.GeneralMenuBar.griditem.setSelected(isSelected);
                    Global.setParameter("showgrid", ZF.ZC.ShowGrid);
                    Global.setParameter("axesonly", ZF.ZC.AxesOnly);
                    if (isSelected){
                         ZF.ZC.createAxisObjects();
                    }else{
                        ZF.ZC.deleteAxisObjects();
                    }
                    ZF.ZC.repaint();
                    ZF.setinfo("grid");
                } else if (name.equals("objecttracker")) {
                    JPM.setGoodProperties(name);
                    ZirkelFrame.ObjectConstructors[ZirkelFrame.NObjectTracker]=new ObjectTracker();
                    ZF.iconPressed("objecttracker");
                } else if (name.equals("locus")) {
                    JPM.setGoodProperties(name);
                    ZirkelFrame.ObjectConstructors[ZirkelFrame.NObjectTracker]=new JLocusObjectTracker();
                    ZF.iconPressed("objecttracker");
                } else if (name.equals("equationxy")) {
                    JPM.MW.ZF.ZC.createEquationXY();
                } else if (name.equals("manage_macros")) {
                    JPM.MW.ZContent.ShowLeftPanel(2);
                } else if (name.equals("help_panel")) {
                    JPM.MW.ZContent.ShowLeftPanel(3);
                } else if (name.equals("newmacro")) {
                    JPM.MW.ZContent.ShowMacroPanel();
                    JPM.MW.ZContent.macros.myJML.MacrosTree.JML.controls.createbtn.setSelected(true);
                    JPM.MW.ZContent.macros.myJML.MacrosTree.JML.createmacropanel.appeargently();
                    JPM.deselectgeomgroup();
                } else if (name.equals("history_panel")) {
                    JPM.MW.ZContent.ShowLeftPanel(1);
//                } else if (name.equals("properties_panel")){
//                    JGlobals.JPB.showme(isSelected);
                } else if (name.equals("copy")) {
                    JPM.MW.savepng(false);
                } else if (name.equals("exportpng")) {
                    JPM.MW.savepng(true);
                } else if (name.equals("exporteps")) {
                    JPM.MW.saveeps();
//                } else if (name.equals("function_u")){
//                    JPM.setGoodProperties(name);
//                    ZF.ZC.createFunction();
                //BuiltIn macros :
                } else if (name.startsWith("bi_")) {
                    if (name.equals("bi_function_u")) {
                        Global.setParameter("options.point.shownames", false);

                        JPM.MW.PointLabel.getBetterName(null, true);
                    }
                    JPM.setGoodProperties(name);
                    JPM.MW.runmacro("@builtin@/"+name.substring(3));
                } else if (name.equals("back")) {
                    ZF.iconPressed(name);
                } else if (name.equals("undo")) {
                    ZF.iconPressed(name);
                } else if (name.equals("boundedpoint")) {
                    JPM.setGoodProperties(name);
                    ZF.iconPressed("boundedpoint");
                } else if (name.equals("ctrl_slider")) {
//                    JPM.MW.JCM.addSlider();
//                    JPM.setSelected("ctrl_edit", true);
                } else if (name.equals("ctrl_popup")) {
//                    JPM.MW.JCM.addPopup();
//                    JPM.setSelected("ctrl_edit", true);
                }else if (name.equals("ctrl_chkbox")) {
//                    JPM.MW.JCM.addChkBox();
//                    JPM.setSelected("ctrl_edit", true);
                } else {


                    JPM.setGoodProperties(name);
                    ZF.iconPressed(name);
                }
                ;

            }
        }

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (isDisabled) {
            return;
        }
        if (JPM.MW.EditRestricted) {
//            JPM.setRestrictedIcon(name,!JPM.isRestrictedIcon(name),e.isPopupTrigger());
            JPM.setRestrictedIcon(name, !JPM.isRestrictedIcon(name), (e.getModifiers()&InputEvent.BUTTON3_MASK)==InputEvent.BUTTON3_MASK);

            repaint();
        } else {
            ClicOnMe();
        }
    }

    public void mouseReleased(MouseEvent e) {
        JGlobals.RefreshBar();
        if (isDisabled) {
            return;
        }
        if (!(isSelected)) {
            repaint();
        }

    }

    public void mouseEntered(MouseEvent e) {
        if (isDisabled) {
            return;
        }
        if (!JPM.MW.EditRestricted) {
            if (!name.equals("blank")) {
                if (!(isSelected)) {
                    isEntered=true;
                    ShowToolTip();
                    repaint();
                }
            }
        }
        ;

    }

    public void mouseExited(MouseEvent e) {
        if (isDisabled) {
            return;
        }
        if (!JPM.MW.EditRestricted) {
            if (!name.equals("blank")) {
                HideToolTip();
                if (!(isSelected)) {
                    isEntered=false;
                    repaint();
                }
            }
        }
    }
}





