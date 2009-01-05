/* 
 
Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

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
 
 
 package rene.zirkel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.InputStream;

import rene.gui.Global;

public class LogoWindow extends Window
        implements Runnable {
    ZirkelFrame ZF;
    
    public LogoWindow(ZirkelFrame zf) {
        super(zf);
        setSize(300,150);
        Dimension d=getSize();
        String name="zirkelframe";
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width-300)/2,(dim.height-150)/2);
        loadLogo();
        setVisible(true);
    }
    
    Image I;
    
    public void loadLogo() {
        try {
            InputStream in=getClass().getResourceAsStream("/rene/zirkel/logowindow.jpg");
            int pos=0;
            int n=in.available();
            byte b[]=new byte[200000];
            while (n>0) {
                int k=in.read(b,pos,n);
                if (k<0) break;
                pos+=k;
                n=in.available();
            }
            in.close();
            I=Toolkit.getDefaultToolkit().createImage(b,0,pos);
            MediaTracker T=new MediaTracker(this);
            T.addImage(I,0);
            T.waitForAll();
        } catch (Exception e) {
            setVisible(false);
            I=null;
        }
    }
    
    public void paint(Graphics g) {
        if (I==null) return;
        g.drawImage(I,0,0,this);
        if (Global.getJavaVersion()>=1.2) {
            Graphics2D G=(Graphics2D)g;
            G.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        g.setFont(new Font("Dialog",Font.PLAIN,11));
        String s=Zirkel.name("version")+" "+Zirkel.name("program.version");
        g.setColor(Color.darkGray);
        g.drawString(s,135,80);
        g.setFont(new Font("Dialog",Font.PLAIN,11));
        g.setColor(Color.GRAY);
        s="java : "+System.getProperty("java.version");
        g.drawString(s,135,95);
    }
    
    public void run() {
    }
    
    
    
}


