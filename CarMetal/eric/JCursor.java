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

import java.awt.*;
import javax.swing.*;
import rene.gui.Global;
import rene.zirkel.ZirkelFrame;

/**
 *
 * @author erichake
 */
public class JCursor extends JPanel{
    JLabel mylabel,myval;
    public JSlider mycursor;
    String myP,mytxt;
    rene.zirkel.ZirkelFrame ZF;
    JPaletteManager JPM;
    int PW=193;//standard palette width
    boolean palmember=false;
    /** Creates a new instance of JCursor */
    public void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
        if (palmember) {
        java.awt.Dimension d = this.getSize();
        g.drawImage(JPM.MW.JZT.getImage("palbackground.gif"),0,0,d.width, d.height,this);
        }
    }
    

    
    
    // JCursor constructor for palette member :
    public JCursor(ZirkelFrame zf,JPaletteManager jpm,String myparam,String mytext,int min,int max,int sel) {
//        this(mytext,40,min,max,rene.gui.Global.getParameter(myparam,sel));
        ZF=zf;
        JPM=jpm;
        if (JPM!=null) PW=JPM.paletteiconsize*6;
        sel=Global.getParameter(myparam,sel);
        myP=myparam;
        palmember=true;
        int lblwidth=65;
        mytxt=mytext;
        mycursor = new JSlider();
        mylabel=new JLabel();
        myval=new JLabel();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setAlignmentX(0);
        mylabel.setText(" "+mytxt);
        mylabel.setForeground(Color.DARK_GRAY);
        mylabel.setFont(new Font(JGlobals.GlobalFont, Font.PLAIN, 11));
        mylabel.setSize(lblwidth,27);
        mylabel.setMaximumSize(new java.awt.Dimension(lblwidth, 27));
        mylabel.setMinimumSize(new java.awt.Dimension(lblwidth, 27));
        mylabel.setPreferredSize(new java.awt.Dimension(lblwidth, 27));
        myval.setText(String.valueOf(sel));
        myval.setForeground(Color.GRAY);
        myval.setFont(new java.awt.Font(JGlobals.GlobalFont, Font.BOLD, 11));
        myval.setSize(20,27);
        myval.setMaximumSize(new java.awt.Dimension(20, 27));
        myval.setMinimumSize(new java.awt.Dimension(20, 27));
        myval.setPreferredSize(new java.awt.Dimension(20, 27));
        mycursor.setFont(new java.awt.Font(JGlobals.GlobalFont, 0, 11));
        mycursor.setOpaque(false);
        mycursor.setMajorTickSpacing(1);
        mycursor.setMaximum(max);
        mycursor.setMinimum(min);
        mycursor.setMinorTickSpacing(1);
        mycursor.setPaintLabels(false);
        mycursor.setPaintTicks(false);
        mycursor.setSnapToTicks(true);
        mycursor.setValue(sel);
        mycursor.setAlignmentX(0.0F);
        mycursor.setMaximumSize(new java.awt.Dimension(PW-82, 27));
        mycursor.setMinimumSize(new java.awt.Dimension(PW-82, 27));
        mycursor.setPreferredSize(new java.awt.Dimension(PW-82, 27));
        mycursor.setSize(new java.awt.Dimension(PW-82, 27));
        
        
        
        
        mycursor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                JSlider myc=(JSlider)evt.getSource();
                int mysel=myc.getValue();
                rene.gui.Global.setParameter(myP,mysel);
                
                JPM.MW.JPR.getLocalPreferences();
                ZF.ZC.updateDigits();
                ZF.ZC.JCM.updateDigits();
                ZF.ZC.resetGraphics();
		ZF.ZC.repaint(); 
                myval.setText(String.valueOf(mysel));
            }
        }) ;
        
        this.add(mylabel);
        this.add(mycursor);
        this.add(myval);
        this.setOpaque(false);
        this.setMaximumSize(new java.awt.Dimension(PW, 27));
        this.setMinimumSize(new java.awt.Dimension(PW, 27));
        this.setPreferredSize(new java.awt.Dimension(PW, 27));
        this.setSize(PW,27);
    }
    
}
