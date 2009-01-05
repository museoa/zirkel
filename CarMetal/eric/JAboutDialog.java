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
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import rene.zirkel.Zirkel;

/**
 *
 * @author  erichake
 */
public class JAboutDialog extends javax.swing.JDialog implements MouseListener{
    private ImageIcon backimage=new ImageIcon(getClass().getResource("/rene/zirkel/logowindow.jpg"));
    
    public void paint(java.awt.Graphics g){
        super.paint(g);
        java.awt.Dimension d = this.getSize();
        g.drawImage(backimage.getImage(),0,0,d.width, d.height,this);
        g.setFont(new Font("Dialog",Font.PLAIN,11));
        String s=Zirkel.name("version")+" "+Zirkel.name("program.version");
        g.setColor(Color.darkGray);
        g.drawString(s,115,75);
        g.setFont(new Font("Dialog",Font.PLAIN,11));
        g.setColor(Color.GRAY);
	s="java : "+System.getProperty("java.version");
        g.drawString(s,125,95);
    }

    public void mouseClicked(MouseEvent e) {
        this.dispose();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
    }
    
    /** Creates new form JLogoWindow */
    public JAboutDialog(JFrame parent) {
        super(parent, true);
        setSize(300,150);
        Point loc=parent.getLocation();
        setLocation(loc.x+parent.getSize().width/2-150,loc.y+parent.getSize().height/2-75);
        this.setUndecorated(true);
        this.addMouseListener(this);
        setVisible(true);
    }
    
   

    
}
