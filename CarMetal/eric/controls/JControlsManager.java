/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.controls;

import eric.JGlobals;
import eric.JMacrosTools;
import eric.JPaletteManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.util.ArrayList;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;
import rene.zirkel.objects.TextObject;

/**
 *
 * @author erichake
 */
public class JControlsManager {

    static Color bordercolor1=new Color(80, 80, 80);
    static Color bordercolor2=new Color(180, 180, 250);
    static int MAGNET=10;
    ZirkelCanvas ZC;
    ArrayList CPs=new ArrayList();
    Rectangle r=new Rectangle();
    Rectangle r2=new Rectangle();
    ArrayList XmlTags=new ArrayList();
    

    public JControlsManager(ZirkelCanvas zc) {
        ZC=zc;

    }

    public void paintControls() {
        BufferedImage sprite;
        Graphics2D g2D=null;
        for (int i=0; i<CPs.size(); i++) {
            JCanvasPanel jp=(JCanvasPanel) CPs.get(i);
            sprite=null;
            
            
            if ((!jp.isHidden())||(ZC.getShowHidden())) {
                sprite=new BufferedImage(jp.getSize().width, jp.getSize().height, BufferedImage.TYPE_INT_ARGB);
                g2D=sprite.createGraphics();
                jp.paintChildren(g2D);
                jp.paintComponent(g2D);
            }
            if ((jp.isHidden())&&(ZC.getShowHidden())) {
                ImageFilter filter=new GrayFilter(true, 60);
                Image disImage=jp.createImage(new FilteredImageSource(sprite.getSource(), filter));
                ImageIcon myicn=new ImageIcon(disImage);
                g2D.drawImage(myicn.getImage(), 0, 0, jp.getSize().width, jp.getSize().height, jp);
            }
            if (sprite!=null) {
                ZC.I.getGraphics().drawImage(sprite, jp.getLocation().x, jp.getLocation().y, jp);
            }
        }
    }

    public void addControl(JCanvasPanel jcp, boolean editme, int x, int y, int w, int h) {
        jcp.setDims(x, y, w, h);
        CPs.add(jcp);
        showHandles(jcp);
        ZC.revalidate();
        if (editme) {
            JGlobals.EditObject(jcp);
        }
    }

    public JCanvasCheckBox addChkBox(ExpressionObject o, int x, int y, int w, int h) {
        JCanvasCheckBox jcb=new JCanvasCheckBox(ZC, o);
        addControl(jcb, o==null, x, y, w, h);
        return jcb;
    }

   public JCanvasButton addButton(ExpressionObject o, int x, int y, int w, int h) {
        JCanvasButton jcb=new JCanvasButton(ZC, o);
        addControl(jcb, o==null, x, y, w, h);
        return jcb;
    }

    public JCanvasTxtfield addTxtField(ExpressionObject o, int x, int y, int w, int h) {
        JCanvasTxtfield jcb=new JCanvasTxtfield(ZC, o);
        addControl(jcb, o==null, x, y, w, h);
        return jcb;
    }

    public JCanvasSlider addSlider(ExpressionObject o, int x, int y, int w, int h) {
        JCanvasSlider jcs=new JCanvasSlider(ZC, o, -5, 5, -2);
        addControl(jcs, o==null, x, y, w, h);
        return jcs;
    }

    public JCanvasPopup addPopup(ExpressionObject o, int x, int y, int w, int h) {
        JCanvasPopup jcp=new JCanvasPopup(ZC, o);
        addControl(jcp, o==null, x, y, w, h);
        return jcp;
    }
    
    public void analyseResize(JCanvasPanel jp){
        r=jp.getBounds(r);
        for (int i=0; i<CPs.size(); i++) {
            JCanvasPanel jp2=(JCanvasPanel) CPs.get(i);
            if (!jp2.equals(jp)) {
                r2=jp2.getBounds(r2);
                if (Math.abs(r2.x+r2.width-r.x-r.width)<MAGNET) {
                    jp.grow(r2.x+r2.width-r.x-r.width, 0);
                    showBordersRight(jp);
                    return;
                }
            }
            }
        showBordersRight(jp);
    }

    public void analyseXY(JCanvasPanel jp) {
        r=jp.getBounds(r);
        for (int i=0; i<CPs.size(); i++) {
            JCanvasPanel jp2=(JCanvasPanel) CPs.get(i);
            if (!jp2.equals(jp)) {
                r2=jp2.getBounds(r2);
                //LEFT
                if (Math.abs(r2.x-r.x)<MAGNET) {
                    jp.setLocation(r2.x, r.y);
                    r=jp.getBounds(r);
                }
                //RIGHT
                if (Math.abs(r2.x+r2.width-r.x-r.width)<MAGNET) {
                    jp.setLocation(r2.x+r2.width-r.width, r.y);
                    r=jp.getBounds(r);
                }
//                //HCENTER
//                if (Math.abs(r2.x+r2.width/2-r.x-r.width/2)<MAGNET) {
//                    jp.setLocation(r2.x+r2.width/2-r.width/2, r.y);
//                    r=jp.getBounds(r);
//                }
//                //TOP
//                if (Math.abs(r2.y-r.y)<MAGNET) {
//                    jp.setLocation(r.x, r2.y);
//                    r=jp.getBounds(r);
//                }
//                //BOTTOM
//                if (Math.abs(r2.y+r2.height-r.y-r.height)<MAGNET) {
//                    jp.setLocation(r.x, r2.y+r2.height-r.height);
//                    r=jp.getBounds(r);
//                }
                //VCENTER
                if (Math.abs(r2.y+r2.height/2-r.y-r.height/2)<MAGNET) {
                    jp.setLocation(r.x, r2.y+r2.height/2-r.height/2);
                    r=jp.getBounds(r);
                }

            }
        }
        showBorders(jp);
    }
    
    public void showBordersRight(JCanvasPanel jp) {
        r=jp.getBounds(r);
        for (int i=0; i<CPs.size(); i++) {
            JCanvasPanel jp2=(JCanvasPanel) CPs.get(i);
            if (!jp2.equals(jp)) {
                r2=jp2.getBounds(r2);
                jp2.hideBorder();
                //RIGHT
                if ((r2.x+r2.width-r.x-r.width)==0) {
                    jp2.showBorder();

                }
            }
        }
    }

    public void showBorders(JCanvasPanel jp) {
        r=jp.getBounds(r);
        for (int i=0; i<CPs.size(); i++) {
            JCanvasPanel jp2=(JCanvasPanel) CPs.get(i);
            if (!jp2.equals(jp)) {
                r2=jp2.getBounds(r2);
                jp2.hideBorder();
                //LEFT
                if (r2.x==r.x) {
                    jp2.showBorder();

                }
                //RIGHT
                if ((r2.x+r2.width-r.x-r.width)==0) {
                    jp2.showBorder();

                }
//                //CENTER
//                if ((r2.x+r2.width/2-r.x-r.width/2)==0) {
//                    jp2.showBorder();
//
//                }
//                //TOP
//                if (r2.y==r.y) {
//                    jp2.showBorder();
//                }
//                //BOTTOM
//                if ((r2.y+r2.height-r.y-r.height)==0) {
//                    jp2.showBorder();
//
//                }
                if ((r2.y+r2.height/2-r.y-r.height/2)==0) {
                    jp2.showBorder();
                }

            }
        }
    }

    public void updateDigits() {
        for (int i=0; i<CPs.size(); i++) {
            JCanvasPanel jp2=(JCanvasPanel) CPs.get(i);
            jp2.setVal(jp2.getVal());
        }
    }

    public void hideBorders(JCanvasPanel jp) {
        for (int i=0; i<CPs.size(); i++) {
            JCanvasPanel jp2=(JCanvasPanel) CPs.get(i);
            if (!jp2.equals(jp)) {
                jp2.hideBorder();
            }
        }
    }

    public void hideHandles(JCanvasPanel jp) {
        for (int i=0; i<CPs.size(); i++) {
            JCanvasPanel jp2=(JCanvasPanel) CPs.get(i);
            if (!jp2.equals(jp)) {
                jp2.hideHandle();
            }
        }
    }

    public void showHandles(JCanvasPanel jp) {
        hideHandles(jp);
        jp.showHandle();

    }

    public static boolean createControl(ZirkelCanvas zc, MouseEvent e) {
        if (rene.zirkel.Zirkel.IsApplet) {
            return false;
        }
        if (JMacrosTools.CurrentJZF==null) {
            return false;
        }
        if (JMacrosTools.CurrentJZF.restrictedSession) {
            return false;
        }
        if (e.isPopupTrigger()) {
            return false;
        }

        int x=e.getX();
        int y=e.getY();
        JPaletteManager myJPM=JMacrosTools.CurrentJZF.JPM;
        JControlsManager myJCM=zc.JCM;
        if (myJPM.ctrlJSlider.isSelected()) {
            myJCM.addSlider(null, x, y, 200, 29);
            return true;
        } else if (myJPM.ctrlJPopup.isSelected()) {
            myJCM.addPopup(null, x, y, 120, 22);
            return true;
        } else if (myJPM.ctrlJCheckBox.isSelected()) {
            myJCM.addChkBox(null, x, y, 30, 22);
            return true;
        } else if (myJPM.ctrlJTextField.isSelected()) {
            myJCM.addTxtField(null, x, y, 120, 22);
            return true;
        } else if (myJPM.ctrlJButton.isSelected()) {
            myJCM.addButton(null, x, y, 100, 22);
            return true;
        }

        return false;
    }

    public void deleteControls(JCanvasPanel jp) {
        CPs.remove(jp);
        ZC.delete(jp.O);
        ZC.remove(jp);
        ZC.revalidate();

    }

    public static void PrintXmlTags(ZirkelCanvas zc, XmlWriter xml) {
        if (JMacrosTools.CurrentJZF==null) {
            return;
        }
        for (int i=0; i<zc.JCM.CPs.size(); i++) {
            ((JCanvasPanel) zc.JCM.CPs.get(i)).PrintXmlTags(xml);
        }
    }

    public void addSlider(XmlTag tag) {

        ExpressionObject o=(ExpressionObject) ZC.getConstruction().find(tag.getValue("Ename"));
        JCanvasSlider jcs=addSlider(o,
                Integer.parseInt(tag.getValue("x")),
                Integer.parseInt(tag.getValue("y")),
                Integer.parseInt(tag.getValue("w")),
                Integer.parseInt(tag.getValue("h")));
        jcs.hidden=Boolean.valueOf(tag.getValue("hidden")).booleanValue();
        jcs.showcom=Boolean.valueOf(tag.getValue("showC")).booleanValue();
        jcs.showunit=Boolean.valueOf(tag.getValue("showU")).booleanValue();
        jcs.showval=Boolean.valueOf(tag.getValue("showV")).booleanValue();
        jcs.lbl_com=tag.getValue("C");
        jcs.lbl_unit=tag.getValue("U");
//        jcs.lbl_val=tag.getValue("V");
        jcs.xTICKS=Double.valueOf(tag.getValue("T")).doubleValue();
        jcs.xMIN=Double.valueOf(tag.getValue("min")).doubleValue();
        jcs.xMAX=Double.valueOf(tag.getValue("max")).doubleValue();

        jcs.setVal(Double.valueOf(tag.getValue("V")).doubleValue());
        jcs.setTicks(jcs.xTICKS);



        jcs.JCS.setSnapToTicks(Boolean.valueOf(tag.getValue("fixT")).booleanValue());
        jcs.JCS.setPaintTicks(Boolean.valueOf(tag.getValue("showT")).booleanValue());
        jcs.setGoodKnobPos(Double.valueOf(tag.getValue("V")).doubleValue());

    }

    public void addTxtField(XmlTag tag) {
        ExpressionObject o=(ExpressionObject) ZC.getConstruction().find(tag.getValue("Ename"));
        JCanvasTxtfield jcs=addTxtField(o,
                Integer.parseInt(tag.getValue("x")),
                Integer.parseInt(tag.getValue("y")),
                Integer.parseInt(tag.getValue("w")),
                Integer.parseInt(tag.getValue("h")));
        jcs.hidden=Boolean.valueOf(tag.getValue("hidden")).booleanValue();
        jcs.showcom=Boolean.valueOf(tag.getValue("showC")).booleanValue();
        jcs.showunit=Boolean.valueOf(tag.getValue("showU")).booleanValue();
        jcs.showval=Boolean.valueOf(tag.getValue("showV")).booleanValue();
        jcs.lbl_com=tag.getValue("C");
        jcs.lbl_unit=tag.getValue("U");
        jcs.setVal(tag.getValue("txt"));
        jcs.JCB.setText(tag.getValue("txt"));
    }

    public void addChkBox(XmlTag tag) {
        ExpressionObject o=(ExpressionObject) ZC.getConstruction().find(tag.getValue("Ename"));
        JCanvasCheckBox jcs=addChkBox(o,
                Integer.parseInt(tag.getValue("x")),
                Integer.parseInt(tag.getValue("y")),
                Integer.parseInt(tag.getValue("w")),
                Integer.parseInt(tag.getValue("h")));
        jcs.hidden=Boolean.valueOf(tag.getValue("hidden")).booleanValue();
        jcs.showcom=Boolean.valueOf(tag.getValue("showC")).booleanValue();
        jcs.showunit=Boolean.valueOf(tag.getValue("showU")).booleanValue();
        jcs.showval=Boolean.valueOf(tag.getValue("showV")).booleanValue();
        jcs.lbl_com=tag.getValue("C");
        jcs.lbl_unit=tag.getValue("U");
        double chked=Double.valueOf(tag.getValue("V")).doubleValue();
        jcs.setVal(chked);
        jcs.JCB.setSelected(chked==1);
    }

    public void addButton(XmlTag tag) {
        ExpressionObject o=(ExpressionObject) ZC.getConstruction().find(tag.getValue("Ename"));
        JCanvasButton jcs=addButton(o,
                Integer.parseInt(tag.getValue("x")),
                Integer.parseInt(tag.getValue("y")),
                Integer.parseInt(tag.getValue("w")),
                Integer.parseInt(tag.getValue("h")));
        jcs.hidden=Boolean.valueOf(tag.getValue("hidden")).booleanValue();
        jcs.showcom=Boolean.valueOf(tag.getValue("showC")).booleanValue();
        jcs.showunit=Boolean.valueOf(tag.getValue("showU")).booleanValue();
        jcs.showval=Boolean.valueOf(tag.getValue("showV")).booleanValue();
        jcs.lbl_com=tag.getValue("C");
        jcs.lbl_unit=tag.getValue("U");
        jcs.setVal(Double.valueOf(tag.getValue("V")).doubleValue());
        
//        try {
////        jcs.setVal(Double.valueOf(tag.getValue("V")).doubleValue());
//            jcs.setVal(o.getValue());
//        } catch (Exception ex) {
//
//        }
        jcs.setComment(jcs.lbl_com);
    }

    public void addPopup(XmlTag tag) {
        ExpressionObject o=(ExpressionObject) ZC.getConstruction().find(tag.getValue("Ename"));
        JCanvasPopup jcs=addPopup(o,
                Integer.parseInt(tag.getValue("x")),
                Integer.parseInt(tag.getValue("y")),
                Integer.parseInt(tag.getValue("w")),
                Integer.parseInt(tag.getValue("h")));
        jcs.hidden=Boolean.valueOf(tag.getValue("hidden")).booleanValue();
        jcs.showcom=Boolean.valueOf(tag.getValue("showC")).booleanValue();
        jcs.showunit=Boolean.valueOf(tag.getValue("showU")).booleanValue();
        jcs.showval=Boolean.valueOf(tag.getValue("showV")).booleanValue();
        jcs.lbl_com=tag.getValue("C");
        jcs.lbl_unit=tag.getValue("U");

        jcs.setItems(tag.getValue("Items").replace("@@@", "\n"));
        double v=Double.valueOf(tag.getValue("V")).doubleValue();
        jcs.setVal(Math.round(v));
        jcs.JCB.setSelectedIndex((int) Math.round(v-1));
//        jcs.JCB.setSelected(chked==1);
    }

/**
 * Called by new3Dwindow : localize all strings that you can
 * find in a new 3D window (floor,coordinate system...).
 */
    public void fix3Dcomments() {
        if (CPs.size()>1) {
            JCanvasPanel jp=(JCanvasPanel) CPs.get(0);
            jp.setComment(JGlobals.Loc("canvas.3D.floor"));
            jp=(JCanvasPanel) CPs.get(1);
            jp.setComment(JGlobals.Loc("canvas.3D.system"));
            TextObject t=(TextObject) ZC.getConstruction().find("Text2");
            if (t!=null) {
                t.setLines(JGlobals.Loc("canvas.3D.rightclic"));
            }
        }
    }

    public void collectXmlTag(XmlTag tag) {
        XmlTags.add(tag);
    }

    public void readXmlTags() {
        for (int i=0; i<XmlTags.size(); i++) {
            XmlTag tag=(XmlTag) XmlTags.get(i);
            if (tag.name().equals("CTRLslider")) {
                addSlider(tag);
            }
            else if (tag.name().equals("CTRLcheckbox")) {
                addChkBox(tag);
            }
            else if (tag.name().equals("CTRLbutton")) {
                addButton(tag);
            }
            else if (tag.name().equals("CTRLpopup")) {
                addPopup(tag);
            }
            else if (tag.name().equals("CTRLtxtfield")) {
                addTxtField(tag);
            }
        }
        if (XmlTags.size()>0) {
            XmlTags.clear();
            hideHandles(null);
        }
    }
}
