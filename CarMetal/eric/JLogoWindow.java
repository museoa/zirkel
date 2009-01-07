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
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import rene.zirkel.Zirkel;


public class JLogoWindow extends JFrame {

private ImageIcon backimage=new ImageIcon(getClass().getResource("/rene/zirkel/logowindow.jpg"));
    
    public JLogoWindow() {
        setSize(300,150);
        Dimension d=getSize();
        String name="zirkelframe";
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width-300)/2,(dim.height-150)/2);
        this.setUndecorated(true);
        setVisible(true);
    }
    
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
 
}


