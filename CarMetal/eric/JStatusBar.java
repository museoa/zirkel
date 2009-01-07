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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JStatusBar extends JPanel implements MouseListener,MouseMotionListener{
    rene.zirkel.ZirkelFrame ZF;
    JZirkelFrame JZF;
    JStatusBar MB;
    int x=-1;
    int y=-1;
    int h=-1;
    int w=-1;
    JButton zoombutton;
    JLabel status;
    
    
    
    
    public void paintComponent(java.awt.Graphics g){
        java.awt.Dimension d = this.getSize();
        g.drawImage(JZF.JZT.getImage("statusbar.gif"),0,0,d.width, d.height,this);
        if (JZF.construct) return;
        if (!JZF.equals(JMacrosTools.CurrentJZF)){JZF.JZT.setDisable(g,d);};
    }
    
    public void paintImmediately(){
        paintImmediately(0, 0, getWidth(), getHeight());
    }
  
    
    public void setButtonsIcons(){
        zoombutton.setIcon(JZF.JZT.getIcon("zoombox.png"));
    }
    
    public JStatusBar(rene.zirkel.ZirkelFrame zf,JZirkelFrame jzf) {
        ZF=zf;
        JZF=jzf;
        status=ZF.Status;
        MB=this;
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
        
        zoombutton=new JButton();
        setButtonsIcons();
        zoombutton.setOpaque(false);
        zoombutton.setContentAreaFilled(false);
        zoombutton.setBorder(BorderFactory.createEmptyBorder());
        zoombutton.addMouseListener(this);
        zoombutton.addMouseMotionListener(this);
        zoombutton.setFocusable(false);
        
        
        
        
        
        JPanel mypan=new JPanel();
        Dimension d=new Dimension(4,1);
        mypan.setMaximumSize(d);
        mypan.setMinimumSize(d);
        mypan.setPreferredSize(d);
        mypan.setSize(d);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        this.add(mypan);
        
        status.setFont(new Font(JGlobals.GlobalFont,0,11));
        status.setForeground(new Color(60,60,60));
        status.setHorizontalAlignment(JLabel.LEFT);
        this.add(status);
        
        JPanel spacer=new JPanel();
        spacer.setOpaque(false);
        this.add(spacer);
        
        this.add(zoombutton);
        
        
    }
    
    
    
    
    
    public void mouseDragged(MouseEvent e) {
        int xx=e.getX()+MB.getLocation().x+zoombutton.getLocation().x-x;
        int yy=e.getY()+MB.getLocation().y+zoombutton.getLocation().y-y;
        JZF.ResizeAll(w+xx,h+yy);
        JZF.pack();
        JZF.JPM.MainPalette.FollowWindow();
    }
    public void mousePressed(MouseEvent e) {
        x = e.getX()+MB.getLocation().x+zoombutton.getLocation().x;
        y = e.getY()+MB.getLocation().y+zoombutton.getLocation().y;
        w=JZF.getSize().width;
        h=JZF.getSize().height;
    }
    public void mouseReleased(MouseEvent e) {
        x = e.getX()+MB.getLocation().x+zoombutton.getLocation().x;
        y = e.getY()+MB.getLocation().y+zoombutton.getLocation().y;
        JZF.setSize(JZF.Content.getSize());
        w=JZF.getSize().width;
        h=JZF.getSize().height;
        JZF.pack();
        JZF.ZF.ZC.recompute();
        JZF.ZF.ZC.validate();
        JZF.ZF.ZC.repaint();
        
    }
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    
}
