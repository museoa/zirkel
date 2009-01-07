/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.controls;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;
import eric.JGlobals;
import eric.JMacrosTools;
import eric.JPointName;

/**
 *
 * @author erichake
 */
public class JCanvasPanel extends JPanel implements MouseListener, MouseMotionListener, ChangeListener {

    ImageIcon ctrlresizeicon=new ImageIcon(getClass().getResource("/eric/icons/palette/ctrl_resize.png"));
    static int COMFONTSIZE=14;
    static int COMSIZE=50;
    static Color COMCOLOR=new Color(80, 80, 80);
    JComponent JSL;
    String lbl_com="", lbl_val="", lbl_unit="�";
    boolean showcom=true, showval=true, showunit=false;
    JLabel JCPlabel=new JLabel();
    JButton JCPresize=new JButton();
//    int W, H;
    ZirkelCanvas ZC;
    public ExpressionObject O;
    boolean hidden=false;
    private boolean showborder1=false;
    private boolean showborder2=false;
    private boolean showhandle=false;
    private MouseEvent pressed;
    private Point location;
    private DecimalFormat decFormat;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when mouseentered :
        Dimension d=getSize();
        if (showhandle) {
            Graphics2D g2d=(Graphics2D) g;

            Rectangle2D rectangle=new Rectangle2D.Double(0, 0, d.width-8, d.height-1);
            g2d.setColor(new Color(119, 136, 153));
            g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL,
                    1f, new float[]{2f}, 0f));
            g2d.draw(rectangle);
            g.drawImage(ctrlresizeicon.getImage(), d.width-12, d.height/2-4, this);
        } else {
            if (showborder1) {
                g.setColor(JControlsManager.bordercolor1);
                g.drawRect(0, 0, d.width-8, d.height-1);
            } else if (showborder2) {
                g.setColor(JControlsManager.bordercolor2);
                g.drawRect(0, 0, d.width-8, d.height-1);
            }
        }

    }

    public void paintChildren(Graphics g) {
        super.paintChildren(g);
    }

    // withoutExpr unused, just to make another constructor :
    public JCanvasPanel(ZirkelCanvas zc, ExpressionObject o) {
        super();
        ZC=zc;
        O=(o==null)?createExpression():o;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setOpaque(false);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        JCPlabel.setHorizontalAlignment(SwingConstants.LEFT);
        JCPlabel.setFont(new Font(JGlobals.GlobalFont, 0, COMFONTSIZE));
        JCPlabel.setForeground(COMCOLOR);


//        JCPresize.setIcon(ctrlresizeicon);
        JCPresize.setOpaque(false);
        JCPresize.setContentAreaFilled(false);
        JCPresize.setBorder(BorderFactory.createEmptyBorder());
        JCPresize.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        
        JCPresize.addMouseListener(new MouseAdapter() {
        
            public void mousePressed(MouseEvent me){
                pressed=me;
            }
            public void mouseReleased(MouseEvent me){
                ZC.JCM.hideBorders((JCanvasPanel)((JComponent)me.getSource()).getParent());
            }
        
        });

        JCPresize.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent me) {
                int w=JSL.getSize().width+me.getX();
                setDims(w-pressed.getX(), getSize().height);
                ZC.JCM.analyseResize((JCanvasPanel)((JComponent)me.getSource()).getParent());
            }
        });

        decFormat=new DecimalFormat();
        DecimalFormatSymbols dfs=new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        decFormat.setDecimalFormatSymbols(dfs);
    }

    private ExpressionObject createExpression() {
        ExpressionObject o=new ExpressionObject(ZC.getConstruction(), 0, 0);
        o.setDefaults();
        o.setSuperHidden(true);
        ZC.getConstruction().add(o);

        if (JMacrosTools.CurrentJZF!=null) {
            int i=JMacrosTools.CurrentJZF.PointLabel.getCurrentLetterSetCode();
            boolean b=Global.getParameter("options.point.shownames", false);
            Global.setParameter("options.point.shownames", true);
            String s=JMacrosTools.CurrentJZF.PointLabel.setLetterSet(JPointName.minLettersSetCode);
            
            Global.setParameter("options.point.shownames", b);
            JMacrosTools.CurrentJZF.PointLabel.setLetterSet(i);
            o.setName(s);
        }
        return o;
    }

    public int StringwWidth(String s) {
//            FontMetrics fm = getFontMetrics(getFont());
        FontMetrics fm=getFontMetrics(new Font(JGlobals.GlobalFont, 0, COMFONTSIZE));

        return fm.stringWidth(s);
    }

    // MUST BE OVERRIDE :
    public double getVal() {
        return 0.0;
    }

    public void setOnlyValue(double x) {
        decFormat.setMaximumFractionDigits(Global.getParameter("digits.edit", 5));
        lbl_val=String.valueOf(decFormat.format(x));
    }

    public void setVal(double x) {
        setOnlyValue(x);
        setVal(String.valueOf(x));
    }

    public void setVal(String s) {
        JCPlabel.setText(goodLabel());
        try {
            O.setExpression(s, ZC.getConstruction());
            ZC.recompute();
            setDims();
//            ZC.validate();
//            ZC.repaint();
        } catch (Exception ex) {
        }
    }

    public void setDims(int x, int y, int w, int h) {
        int W=w+StringwWidth(JCPlabel.getText())+15;
        fixsize(JCPlabel, StringwWidth(JCPlabel.getText()), h);
        fixsize(JCPresize, 15, h);
        fixsize(JSL, w, h);
        revalidate();
        setBounds(x, y, W, h);
        ZC.validate();
        ZC.repaint();
    }

    public void setDims(int w, int h) {
        int x=getLocation().x;
        int y=getLocation().y;
        setDims(x, y, w, h);
    }

    public void setDims() {
        setDims(JSL.getSize().width, JSL.getSize().height);
    }
    
    public void grow(int w,int h){
        setDims(JSL.getSize().width+w, JSL.getSize().height+h);
    }

    public String getComment() {
        return lbl_com;
    }

    public void setComment(String s) {
        lbl_com=s;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public String getUnit() {
        return lbl_unit;
    }

    public void setUnit(String s) {
        lbl_unit=s;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public void setShowComment(boolean b) {
        showcom=b;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public void setShowVal(boolean b) {
        showval=b;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public void setShowUnit(boolean b) {
        showunit=b;
        JCPlabel.setText(goodLabel());
        setDims();
    }

    public boolean getShowComment() {
        return showcom;
    }

    public boolean getShowVal() {
        return showval;
    }

    public boolean getShowUnit() {
        return showunit;
    }

    public String goodLabel() {
        String lbl="";
        if (showcom) {
            lbl+=lbl_com;
        }
        if (showval) {
            lbl+=lbl_val;
        }
        if (showunit) {
            lbl+=lbl_unit;
        }
        return lbl;
    }

    static public void fixsize(JComponent cp, int w, int h) {
        Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    public boolean isEditMode() {
        if (JMacrosTools.CurrentJZF==null) {
            return false;
        }
        boolean bool=(JMacrosTools.CurrentJZF.JPM.isSelected("edit"))||(JMacrosTools.CurrentJZF.JPM.isSelected("ctrl_edit"));
        bool=(bool||(JMacrosTools.CurrentJZF.JPM.isSelected("ctrl_slider")));
        bool=(bool||(JMacrosTools.CurrentJZF.JPM.isSelected("ctrl_popup")));
        bool=(bool||(JMacrosTools.CurrentJZF.JPM.isSelected("ctrl_chkbox")));
        bool=(bool||(JMacrosTools.CurrentJZF.JPM.isSelected("ctrl_txtfield")));
        bool=(bool||(JMacrosTools.CurrentJZF.JPM.isSelected("ctrl_button")));
        bool=(bool||(JMacrosTools.CurrentJZF.JPM.isSelected("delete")));
        bool=(bool||(JMacrosTools.CurrentJZF.JPM.isSelected("hide")));
        return bool;
    }

    public boolean isDeleteMode() {
        if (JMacrosTools.CurrentJZF==null) {
            return false;
        }
        return (JMacrosTools.CurrentJZF.JPM.isSelected("delete"));
    }

    public boolean isHideToolSelected() {
        if (JMacrosTools.CurrentJZF==null) {
            return false;
        }
        return (JMacrosTools.CurrentJZF.JPM.isSelected("hide"));
    }

    public boolean isHidden() {
        return (hidden || O.testConditional("hidden"));
    }

    public void setHidden(boolean b) {
        hidden=b;
    }

    public void showBorder() {
        showborder2=true;
        repaint();
    }

    public void hideBorder() {
        showborder2=false;
        repaint();
    }

    public void showHandle() {
        showhandle=true;

        setDims();
    }

    public void hideHandle() {
        showhandle=false;
        setDims();
    }

    public void mouseClicked(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent e) {
        pressed=e;
        if (e.isPopupTrigger()) {
            return;
        }
        ZC.JCM.hideHandles(null);
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            return;
        }
        if ((!ZC.getShowHidden())&&(isHidden())) {

            return;
        }
        if (isHideToolSelected()) {
            setHidden(!isHidden());
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            showborder1=false;
            repaint();
        }
        if (isDeleteMode()) {
            ZC.JCM.deleteControls(this);
        }

        ZC.JCM.hideBorders(this);
        if ((isEditMode())&&(!isHidden())) {
            showHandle();
            JGlobals.EditObject(this);
        }

    }

    public void mouseEntered(MouseEvent arg0) {
        if (isHidden()) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            showborder1=false;
//            repaint();
            return;
        }
        if (isEditMode()) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            showborder1=true;
            repaint();
        }
    }

    public void mouseExited(MouseEvent arg0) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        showborder1=false;
        repaint();
    }

    public void mouseDragged(MouseEvent me) {
        if (!isEditMode()) {
            return;
        }

        location=getLocation(location);
        int x=location.x-pressed.getX()+me.getX();
        int y=location.y-pressed.getY()+me.getY();
        if (x<0) {
            x=0;
        } else if (x+getSize().width>ZC.getSize().width) {
            x=ZC.getSize().width-getSize().width;
        }
        if (y<0) {
            y=0;
        } else if (y+getSize().height>ZC.getSize().height) {
            y=ZC.getSize().height-getSize().height;
        }
        setLocation(x, y);
        Toolkit.getDefaultToolkit().sync();
        ZC.JCM.analyseXY(this);
    }

    public void mouseMoved(MouseEvent arg0) {
    }

    // Change event from JSlider :
    public void stateChanged(ChangeEvent arg0) {
    }

    public void PrintXmlTags(XmlWriter xml) {

        xml.printArg("Ename", ""+O.getName());
        xml.printArg("x", ""+getLocation().x);
        xml.printArg("y", ""+getLocation().y);
        xml.printArg("w", ""+JSL.getSize().width);
        xml.printArg("h", ""+JSL.getSize().height);
        xml.printArg("showC", ""+showcom);
        xml.printArg("showU", ""+showunit);
        xml.printArg("showV", ""+showval);
        xml.printArg("hidden", ""+hidden);
        xml.printArg("C", ""+lbl_com);
        xml.printArg("U", ""+lbl_unit);
        xml.printArg("V", ""+lbl_val);

    }
}
