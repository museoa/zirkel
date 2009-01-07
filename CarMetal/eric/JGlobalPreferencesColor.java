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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.java.dev.colorchooser.ColorChooser;
import net.java.dev.colorchooser.Palette;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class JGlobalPreferencesColor extends JPanel implements MouseListener,MouseMotionListener{
    private ColorChooser cchooser;
    private Palette Pal;
    int PW=193;
    int PaletteType;
    int xx,yy;
    colorline mycolorpickerline;
    JGlobalPreferencesColor me;
    JComboBox JCB;
    JLabel comment;
    JPanel colorsample=new JPanel();
    
    public void paintComponent(java.awt.Graphics g){
            super.paintComponent(g);
        }
    private void fixsize(JComponent cp,Dimension d){
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }
    /** Creates a new instance of JColorPanel */
    public JGlobalPreferencesColor() {
        me=this;
        xx=-1;yy=-1;
        PaletteType=Global.getParameter("prefs.colorbackgroundPal",1);

        cchooser=new ColorChooser();
//        Pal=cchooser.getPalettes()[4];
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(0.5f);
        this.setOpaque(false);
        this.add(margintop(5));
        
        JCB=new JComboBox();
        JCB.setMaximumRowCount(5);
        JCB.setOpaque(false);
        JCB.setFocusable(false);
        JCB.setEditable(false);
        JCB.setAlignmentX(0.5f);
        JCB.setFont(new Font("System",0,11));
        JCB.addItem(Loc("saturated1"));
        JCB.addItem(Loc("desaturated1"));
        JCB.addItem(Loc("saturated2"));
        JCB.addItem(Loc("desaturated2"));
        JCB.addItem(Loc("constants"));
        JCB.setSelectedIndex(PaletteType);
        JCB.addItemListener(new ItemAdapter());
        JPanel JCBpanel=new JPanel();
        JCBpanel.setLayout(new BoxLayout(JCBpanel, BoxLayout.X_AXIS));
        JCBpanel.setAlignmentX(0.5f);
        JCBpanel.setOpaque(false);
        fixsize(JCBpanel,PW,24);
        JCBpanel.add(margin(5));
        fixsize(JCB,PW-10,22);
        JCBpanel.add(JCB);
        this.add(JCBpanel);
        
        mycolorpickerline=new colorline(PaletteType);
        this.add(mycolorpickerline);
        
        comment=new JLabel("coucou");
        comment.setOpaque(false);
        comment.setAlignmentX(0.5f);
        comment.setFont(new Font("System",0,9));
        fixsize(comment,PW,14);
        comment.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(comment);
                fixsize(colorsample,new Dimension(80,80));
                colorsample.setAlignmentX(0.5f);
        this.add(colorsample);
    }
    
     public String Loc(String s){
        return JMacrosTools.CurrentJZF.Strs.getString("palette.colors."+s);
    }
    
    class ItemAdapter implements ItemListener {
        public void itemStateChanged(ItemEvent evt) {
            if (evt.getStateChange()==ItemEvent.SELECTED){
                me.remove(mycolorpickerline);
                mycolorpickerline=new colorline(JCB.getSelectedIndex());
                Global.setParameter("prefs.colorbackgroundPal",JCB.getSelectedIndex());
                me.add(mycolorpickerline,2);
//                JDialog father=GetDialog(me);
//                father.validate();
//                father.pack();
                int x=Global.getParameter("prefs.colorbackgroundx",154);
                int y=Global.getParameter("prefs.colorbackgroundy",5);
                x=x*Pal.getSize().width/mycolorpickerline.mycolors.getSize().width;
                y=y*Pal.getSize().height/mycolorpickerline.mycolors.getSize().height;
                Color mycolor=Pal.getColorAt(x,y);
                if (mycolor!=null) {
                    Global.setParameter("prefs.colorbackground",mycolor);
                    Global.setParameter("prefs.colorbackgroundx",x);
                    Global.setParameter("prefs.colorbackgroundy",y);
                }
                me.repaint();
            }
        }
    }
    
    public JDialog GetDialog(Component c) {
        if(c instanceof JDialog || null==c)
            return c==null ? null : (JDialog)c;
        return GetDialog(c.getParent());
    }
    
    class colorline extends JPanel{
        JPanel mymargin;
        onlycolors mycolors;
        colorline(int ptype){
            PaletteType=ptype;
            Pal=cchooser.getPalettes()[ptype];
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setAlignmentX(0.5f);
            mymargin=margin(0);
//            if (PW>Pal.getSize().width){
//                fixsize(mymargin,(PW-Pal.getSize().width)/2,1);
//            };
            this.add(mymargin);
            mycolors=new onlycolors(ptype);
            this.add(mycolors);
            this.setOpaque(false);
        }
        
    }
    
    private class onlycolors extends JPanel{
        Image bimage;
        public void paintComponent(java.awt.Graphics g){
            super.paintComponent(g);
            Dimension d = this.getSize();
            int w=d.width;
            int h=d.height;
            g.drawImage(bimage,0,0,w, h,this);
            Color mycolor=Global.getParameter("prefs.colorbackground",new Color(231,238,255));
            cchooser.setColor(mycolor);
            colorsample.setBackground(mycolor);
//            ZF.ZC.setBackground(mycolor);
//            ZF.ZC.repaint();

            int x=Global.getParameter("prefs.colorbackgroundx",154);
            int y=Global.getParameter("prefs.colorbackgroundy",5);
            
            if (PaletteType==4){
                x=((int)(x/12))*12+6;
                y=((int)(y/12))*12+6;
            }
            
            Graphics2D g2 = (Graphics2D) g;
//            AlphaComposite ac =
//                    AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f);
//            g2.setComposite(ac);
            g2.setColor(new Color(0,0,0));
            Stroke stroke = new BasicStroke(1f);
            g2.setStroke(stroke);
            g2.drawRect(x-4,y-4,8,8);
            stroke = new BasicStroke(1f);
            g2.setStroke(stroke);
            g2.setColor(new Color(255,255,255));
            g2.drawRect(x-3,y-3,6,6);
//            g.drawRect(x-5,y-5,10,10);
            comment.setText(Pal.getNameAt(x,y));
        }
        
        onlycolors(int ptype){
            
            int w=(PW<Pal.getSize().width)?PW:Pal.getSize().width;
            fixsize(this,w,Pal.getSize().height);
//            bimage=JPM.MW.createImage(Pal.getSize().width,Pal.getSize().height);
            bimage=JMacrosTools.CurrentJZF.createImage(Pal.getSize().width,Pal.getSize().height);
            Pal.paintTo(bimage.getGraphics());
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setAlignmentX(0.5f);
            this.setOpaque(false);
            this.addMouseMotionListener(me);
            this.addMouseListener(me);
        }
        
//        public void changePal(int i){
//
//            Pal=cchooser.getPalettes()[i];
//            int w=(PW<Pal.getSize().width)?PW:Pal.getSize().width;
//            fixsize(this,w,Pal.getSize().height);
//            bimage=JPM.MW.createImage(Pal.getSize().width,Pal.getSize().height);
//            Pal.paintTo(bimage.getGraphics());
//        }
    }
    
    private void fixsize(JComponent cp,int w,int h){
        Dimension d=new Dimension(w,h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }
    private JPanel margin(int w){
        JPanel mypan=new JPanel();
        fixsize(mypan,w,1);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }
    private JPanel margintop(int h){
        JPanel mypan=new JPanel();
        fixsize(mypan,1,h);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }
    
    public void mouseClicked(MouseEvent e) {
    }
    
    public void mousePressed(MouseEvent e) {
        int x=e.getX();int y=e.getY();
        if (e.isShiftDown()) {
            if (yy==-1) yy=y;
            y=yy;
        }
        if (e.isAltDown()) {
            if (xx==-1) xx=x;
            x=xx;
        }
        if (x>mycolorpickerline.mycolors.getSize().width) x=mycolorpickerline.mycolors.getSize().width;
        if (x<0) x=0;
        if (y>mycolorpickerline.mycolors.getSize().height) y=mycolorpickerline.mycolors.getSize().height;
        if (y<0) y=0;
        
        Global.setParameter("prefs.colorbackgroundx",x);
        Global.setParameter("prefs.colorbackgroundy",y);
        
        x=x*Pal.getSize().width/mycolorpickerline.mycolors.getSize().width;
        y=y*Pal.getSize().height/mycolorpickerline.mycolors.getSize().height;
        
        
        
        
        Color mycolor=Pal.getColorAt(x,y);
        if (mycolor!=null) {
            Global.setParameter("prefs.colorbackground",mycolor);
            mycolorpickerline.mycolors.repaint();
        }
        
        
    }
    
    public void mouseReleased(MouseEvent e) {
        xx=-1;yy=-1;
    }
    
    public void mouseEntered(MouseEvent e) {
        
    }
    
    public void mouseExited(MouseEvent e) {
    }
    
    public void mouseDragged(MouseEvent e) {
        mousePressed(e);
    }
    
    public void mouseMoved(MouseEvent e) {
        
    }
    
}
