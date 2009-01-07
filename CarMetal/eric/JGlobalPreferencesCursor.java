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

import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class JGlobalPreferencesCursor extends JPanel{
    JLabel mylabel,myval;
    public JSlider mycursor;
    String myP,mytxt;
    int PW=300;//standard palette width
    boolean palmember=false;
    /** Creates a new instance of JCursor */
    public void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
    }
    

    
    
    // JCursor constructor for palette member :
    public JGlobalPreferencesCursor(String myparam,String mytext,int min,int max,int sel) {
//        this(mytext,40,min,max,rene.gui.Global.getParameter(myparam,sel));
        sel=Global.getParameter(myparam,sel);
        myP=myparam;
        palmember=true;
        int lblwidth=150;
        int valwidth=20;
        int valheight=27;
        mytxt=mytext;
        mycursor = new JSlider();
        mylabel=new JLabel();
        myval=new JLabel();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        this.setAlignmentX(0);
        mylabel.setText(" "+mytxt);
        mylabel.setForeground(Color.DARK_GRAY);
        mylabel.setFont(new Font(JGlobals.GlobalFont, Font.PLAIN, 13));
        mylabel.setSize(lblwidth,27);
        mylabel.setMaximumSize(new java.awt.Dimension(lblwidth, 27));
        mylabel.setMinimumSize(new java.awt.Dimension(lblwidth, 27));
        mylabel.setPreferredSize(new java.awt.Dimension(lblwidth, 27));
        myval.setText(String.valueOf(sel));
        myval.setForeground(Color.GRAY);
        myval.setFont(new java.awt.Font(JGlobals.GlobalFont, Font.BOLD, 13));
        myval.setSize(valwidth,valheight);
        myval.setMaximumSize(new java.awt.Dimension(valwidth,valheight));
        myval.setMinimumSize(new java.awt.Dimension(valwidth,valheight));
        myval.setPreferredSize(new java.awt.Dimension(valwidth,valheight));
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
        mycursor.setMaximumSize(new java.awt.Dimension(PW-lblwidth-valwidth, valheight));
        mycursor.setMinimumSize(new java.awt.Dimension(PW-lblwidth-valwidth, valheight));
        mycursor.setPreferredSize(new java.awt.Dimension(PW-lblwidth-valwidth, valheight));
        mycursor.setSize(new java.awt.Dimension(PW-lblwidth-valwidth, valwidth));
        
        
        
        
        mycursor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                JSlider myc=(JSlider)evt.getSource();
                int mysel=myc.getValue();
                rene.gui.Global.setParameter(myP,mysel);

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
