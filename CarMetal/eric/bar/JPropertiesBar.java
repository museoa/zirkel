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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import rene.gui.Global;
import rene.zirkel.Zirkel;
import rene.zirkel.objects.ConstructionObject;
import eric.JGlobals;
import eric.JMacrosTools;
import eric.JZirkelFrame;
import eric.controls.JCanvasPanel;

/**
 *
 * @author erichake
 */
public class JPropertiesBar extends JFrame implements MouseListener, MouseMotionListener {

    static int HAUT=0,  BAS=1,  TOP=2,  BOTTOM=3; // where the palette is docked
    static int Bx=0,  By=0,  Docky=HAUT,  Bwidth=1000,  Bheight=60;
    CPane CP;
    TitleBar titlebar;
    JControlProperties Content;
    private MouseEvent pressed;
    private Point location;
//    Rectangle screenBounds;
    public JPropertiesBar() {
        Bx=Global.getParameter("props.paletteX", 0);
        Docky=Global.getParameter("props.dockY", HAUT);
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        screenBounds = ge.getMaximumWindowBounds();
//        JGlobals.SCREEN.grow(0, -2);
//        screenBounds.translate(0, -1);
//        if (System.getProperty("os.name").equals("Linux")) {
//            screenBounds.grow(0, -25);
//        }

        if (Zirkel.SCREEN.x>Bx) {
            Bx=Zirkel.SCREEN.x;
        }
        if (Docky==HAUT) {
            By=Zirkel.SCREEN.y;
        } else {
            By=Zirkel.SCREEN.y+Zirkel.SCREEN.height-Bheight;
        }
        CP=new CPane();
        setContentPane(CP);
        titlebar=new TitleBar(this, 18);
        Content=new JControlProperties(Bwidth-titlebar.getSize().width, Bheight);
        Content.addPanel(JGlobals.Loc("props.aspecttab"));
        Content.addPanel(JGlobals.Loc("props.numerictab"));
        Content.addPanel(JGlobals.Loc("props.conditionaltab"));
        Content.selectTab(Global.getParameter("props.selectedtab", 0));

        CP.add(titlebar);
        CP.add(Content);
        setSize(Bwidth+2, Bheight+2);
        setLocation(Bx, By);
        setUndecorated(true);
//        setAlwaysOnTop(true);
        setFocusable(true);
//        setFocusableWindowState(false);
//        setVisible(true);

    } 
    
    public void clearme(){
        Content.clearme();
    }

    public void refresh() {
        Content.refresh();
    }
 
    public void showme(boolean vis) {

        if (vis) {
            setVisible(true);
            fixWindowsPosition();
        } else {
            setVisible(false);
        }
    }

    public void fixWindowsPosition() {
        if (!isVisible()) {
            return;
        }
        ArrayList wins=JMacrosTools.AllJZFs;

        
        for (int i=0; i<wins.size(); i++) {
            JZirkelFrame jzf=(JZirkelFrame) wins.get(i);
            int offsety=(System.getProperty("os.name").equals("Linux"))?3:2;
            if (jzf.getSize().height>Zirkel.SCREEN.height-Bheight-offsety) {
                jzf.ResizeAll(jzf.getSize().width, Zirkel.SCREEN.height-Bheight-offsety);
            }
            
            if (Docky==HAUT) {
                if (jzf.getLocation().y<(By+Bheight+offsety)) {
                    jzf.setLocation(jzf.getLocation().x, By+Bheight+offsety);
                }
            } else {
                
                if ((jzf.getLocation().y+jzf.getSize().height)>By-offsety) {
                    jzf.setLocation(jzf.getLocation().x, By-jzf.getSize().height-offsety);
                }
            }
        }
        toFront();
        JMacrosTools.CurrentJZF.toFront();
        JMacrosTools.CurrentJZF.JPM.MainPalette.FollowWindow();
    }

    public void setObject(ConstructionObject O, boolean forcevisible, boolean forcefocus) {
        
        if (forcevisible) {
            showme(true);
        }
        if ((!this.isVisible())&&(!forcefocus)) {
            return;
        }
        Content.setObject(O, forcevisible, forcefocus);
        
    }
    
    public void selectTab(int i){
        Content.selectTab(i);
    }
    


    public void setObject(JCanvasPanel jcp) {
        showme(true);
        Content.setObject(jcp);
    }

    private static void fixsize(JComponent cp, int w, int h) {
        Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    private class TitleBar extends JPanel {

        ImageIcon closeimg=new ImageIcon(getClass().getResource("gui/Pclose.png"));
        ImageIcon closeoverimg=new ImageIcon(getClass().getResource("gui/Pcloseover.png"));
        ImageIcon myicon=new ImageIcon(getClass().getResource("gui/titlebar.png"));
        JButton closebtn;
        JPropertiesBar Mother;

        public void paintComponent(java.awt.Graphics g) {


            java.awt.Dimension d=this.getSize();
            g.drawImage(myicon.getImage(), 0, 0, d.width, d.height, this);
            super.paintComponent(g);
        }

        public TitleBar(JPropertiesBar parent, int width) {
            Mother=parent;
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//            this.setAlignmentX(0F);
            this.setOpaque(false);
            fixsize(this, width, Bheight);
            this.addMouseListener(parent);
            this.addMouseMotionListener(parent);
            closebtn=new JButton(closeimg);
            closebtn.setRolloverIcon(closeoverimg);
            closebtn.setBorder(BorderFactory.createEmptyBorder());
            closebtn.setOpaque(false);
            closebtn.setContentAreaFilled(false);
            closebtn.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    Mother.setVisible(false);
                }
            });
            this.add(closebtn);

        }
    }

    private class CPane extends JPanel {

        public CPane() {
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setOpaque(false);
            this.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        }
    }

    public void mouseDragged(MouseEvent me) {
        location=getLocation(location);
        int x=location.x-pressed.getX()+me.getX();
        int y=location.y-pressed.getY()+me.getY();


        if (y<Zirkel.SCREEN.height/2) {
            Docky=HAUT;
            y=Zirkel.SCREEN.y;
        } else {
            Docky=BAS;
            y=Zirkel.SCREEN.y+Zirkel.SCREEN.height-Bheight;
        }


//        if (y < screenBounds.y + 20) {
//            y = screenBounds.y;
//        }
//        if (y + Bheight > screenBounds.y + screenBounds.height - 20) {
//            y = screenBounds.y + screenBounds.height - Bheight;
//        }

        if (x<Zirkel.SCREEN.x+20) {
            x=Zirkel.SCREEN.x;
        }
        if (x+Bwidth>Zirkel.SCREEN.x+Zirkel.SCREEN.width-20) {
            x=Zirkel.SCREEN.x+Zirkel.SCREEN.width-Bwidth;
        }
        setLocation(x, y);
        Toolkit.getDefaultToolkit().sync();
    }

    public void mousePressed(MouseEvent me) {
        pressed=me;
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        location=getLocation();
        int oldDocky=Docky;
        if (location.y<Zirkel.SCREEN.height/2) {
            Docky=HAUT;
            setLocation(location.x, Zirkel.SCREEN.y);
        } else {
            Docky=BAS;
            setLocation(location.x, Zirkel.SCREEN.y+Zirkel.SCREEN.height-Bheight);
        }
        Global.setParameter("props.paletteX", getLocation().x);
        Global.setParameter("props.dockY", Docky);
        Bx=getLocation().x;
        By=getLocation().y;
        fixWindowsPosition();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}


