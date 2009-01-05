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
 
 
 package eric.bar;

import eric.JGlobals;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 *
 * @author  erichake
 */
public class JTabPanelTitleBar extends JPanel {
    ArrayList TabTitles=new ArrayList();
    JTabPanel Mother;
    
    
    public void paintComponent(Graphics g){
            super.paintComponent(g);
            ImageIcon myicon1=new ImageIcon(getClass().getResource("gui/titles_back.png"));
            ImageIcon myicon2=new ImageIcon(getClass().getResource("gui/title_back_end.png"));
            java.awt.Dimension d = this.getSize();
            g.drawImage(myicon1.getImage(),0,0,d.width, d.height,this);
            g.drawImage(myicon2.getImage(),d.width-d.height,0,this);
    }
    
    public JTabPanelTitleBar(JTabPanel parent) {
        Mother=parent;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentX(0.0f);
        setAlignmentY(0.0f);
        add(margin(Mother.Leftmargin));
        add(margin(Mother.Rightmargin));
    }
    
    public void addTabTitle(String name){
        int cnt=this.getComponentCount();
        JTabTitle jtt=new JTabTitle(Mother,name);
        
        TabTitles.add(jtt);
        this.add(jtt,cnt-1);
        fixsize(this,getLeftWidth(),Mother.TabHeight);
    }
    
    public void selectTabTitle(int n){
        for (int i=0;i<TabTitles.size();i++){
            ((JTabTitle)TabTitles.get(i)).setSelected(i==n);
        }
    }
    
    public int getLeftWidth(){
    int w=Mother.Leftmargin+Mother.Rightmargin;
    for (int i=0;i<TabTitles.size();i++){
           JTabTitle jtt=(JTabTitle)TabTitles.get(i);
           w+=jtt.getSize().width;
        }
    return w;
}
    
    
    static JPanel margin(int w){
        JPanel mypan=new JPanel();
        fixsize(mypan,w,1);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }
    
    static void fixsize(JComponent cp,int w,int h){
        Dimension d=new Dimension(w,h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }
    
}
class JTabTitle extends JPanel{
    boolean isActive=false;
    JTabPanel Mother;
    int Pos;
    JLabel JLB=new JLabel();
    ImageIcon IcON=new ImageIcon(getClass().getResource("gui/tab_on.png"));
    ImageIcon IcOFF=new ImageIcon(getClass().getResource("gui/tab_off.png"));
    
    
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            ImageIcon icn=(isActive)?IcON:IcOFF;
            java.awt.Dimension d = this.getSize();
            g.drawImage(icn.getImage(),0,0,d.width, d.height,this);
    }
    
    public JTabTitle(JTabPanel parent,String txt){
        Mother=parent;
        Pos=Mother.Panes.size();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
//        JTabPanelTitleBar.fixsize(this,Mother.TabWidth,Mother.TabHeight);
        JTabPanelTitleBar.fixsize(this,titleWidth(txt)+2*Mother.TabTitleMargin,Mother.TabHeight);
        JLB.setText(txt);
        JLB.setFont(new Font(eric.JGlobals.GlobalFont,0,11));
        JLB.setOpaque(false);
        JLB.setHorizontalAlignment(SwingConstants.CENTER);
        JLB.setVerticalAlignment(SwingConstants.BOTTOM);
        JLB.setForeground(new Color(40,40,40));
        JTabPanelTitleBar.fixsize(JLB,titleWidth(txt)+2*Mother.TabTitleMargin,Mother.TabHeight);
//        JTabPanelTitleBar.fixsize(JLB,Mother.TabWidth,Mother.TabHeight);
        JLB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Mother.selectTab(Pos);
                Mother.repaint();
            }
        });
        this.add(JLB);
    }
    
    private int titleWidth(String s){
//            FontMetrics fm = getFontMetrics(getFont());
            FontMetrics fm = getFontMetrics(new Font(JGlobals.GlobalFont,0,Mother.TabTitleSize));
            
            return fm.stringWidth(s);
        }
    
    public void setSelected(boolean b){
        isActive=b;
    }
}
