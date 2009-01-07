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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import rene.gui.Global;

class JPaletteZone extends JPanel {

    private rene.zirkel.ZirkelFrame ZF;
    JPaletteZoneTitleBar ZoneTitle;
    JPaletteZoneContent ZoneContent;
    JLabel label=null;
    JPalette JP;
    JPaletteManager JPM;
    String title;
    MouseAdapter TitleMouseAdapter;
    String name;
    private int x=-1;
    private int y=-1;
    private Icon rightTriangle=new ImageIcon(getClass().getResource("/eric/icons/palette/PaletteTriangleDroite.png"));
    private Icon bottomTriangle=new ImageIcon(getClass().getResource("/eric/icons/palette/PaletteTriangleBas.png"));
    boolean ContentVisible=true;
    int PW=193;//Palette width
    public boolean mainmember=true;

    public void paintComponent(java.awt.Graphics g) {
//        super.paintComponent(g);
    }


    public JPaletteZone(rene.zirkel.ZirkelFrame zf, JPaletteManager jpm, JPalette jp, String PartTitle, String myname) {
        init(zf, jpm, jp, PartTitle, myname);
    }

    //Constructor for dialogs other than MainPalette tools :
    public JPaletteZone(rene.zirkel.ZirkelFrame zf, JPaletteManager jpm, JPalette jp, String PartTitle, String myname, int w) {
        setWidth(w);
        init(zf, jpm, jp, PartTitle, myname);
        mainmember=false;
    }

    public void init(rene.zirkel.ZirkelFrame zf, JPaletteManager jpm, JPalette jp, String PartTitle, String myname) {
        ZF=zf;
        JPM=jpm;
        JP=jp;
        title=PartTitle;
        name=myname;
        if (JPM!=null) {
            PW=JPM.paletteiconsize*6+1;
        }
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
        ZoneTitle=new JPaletteZoneTitleBar(JP, this, title);
        ZoneContent=new JPaletteZoneContent(zf, JP, this);
        this.setBackground(new java.awt.Color(204, 204, 204));
        this.add(ZoneTitle);
        this.add(ZoneContent);
    }

    private void fixsize(JComponent cp, int w, int h) {
        Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    public void setWidth(int w) {
        PW=w;
    }

    // must only be called by the jpalettemanager constructor :
    public void CollapseOrExpand() {
        if (Global.getParameter("hidepalette."+name, true)) {
            collapse();
        }
    }

    public void collapse() {
        Global.setParameter("hidepalette."+name, true);
        ContentVisible=false;
        ZoneTitle.title.setIcon(rightTriangle);
        JP.validate();
        this.remove(1);
        JP.validate();
        JP.pack();
        JP.Content.paintImmediately(0, 0, PW, JP.Content.getPreferredSize().height);
    }

    public void expand() {
        Global.setParameter("hidepalette."+name, false);
        ContentVisible=true;
        ZoneTitle.title.setIcon(bottomTriangle);
        this.add(ZoneContent);
        if (JPM!=null) {
            JPM.CollapseToFitScreenHeight(this);
        }
        JP.validate();
        JP.pack();
        JP.Content.paintImmediately(0, 0, PW, JP.Content.getPreferredSize().height);
    }

    public void addCursor(JCursor mycursor) {
        ZoneContent.add(mycursor);
    }

    public void addColorPicker(JColorPanel mycolorpanel) {
        ZoneContent.add(mycolorpanel);
    }

    public JLabel addLabel(String mytxt) {
        JLabel myLabel=new JLabel(mytxt);
        myLabel.setOpaque(false);
        myLabel.setFont(new java.awt.Font(JGlobals.GlobalFont, 1, 11));
        myLabel.setForeground(new Color(100, 100, 100));
        myLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel myline=ZoneContent.newLabelLine();
        fixsize(myLabel, myline.getSize().width, myline.getSize().height);
        myline.add(myLabel);
        addNewLine();
        return myLabel;
    }


    
    private JPanel add7iconsString (final String myname[], final Vector Group) {
        JPanel myLastLine=null;
        for (int i=0; i<myname.length; i++) {
            if (JPM.acceptedIcon(myname[i])) {
                JIcon myicon=new JIcon(ZF, JPM, this, myname[i], Group);
                if (JPM.MustBeFirstIconOnLine(myname[i])) {
                    addNewLine();
                }

                fixsize(myicon, JPM.paletteiconsize-5, JPM.paletteiconsize-5);
                myLastLine=getLastLine();
                myLastLine.add(myicon);
                JPM.AllIcons.add(myicon);
            }
        }
        return myLastLine;
    }
    
    public void addPointIcons(String myname[], Vector Group) {
        JPM.MW.PointLabel.addPaletteJLabel(add7iconsString(myname, Group));
    }
    public void addColorIcons(String myname[], Vector Group) {
        JPanel myLine=add7iconsString(myname, Group);
        myLine.add(JColorPicker.margin(5));

        JPM.MW.ColorPicker=new JColorPicker(JPM.paletteiconsize-5, 6,3,Group){
            public void doChange(){
                JPM.setObjectColor(getCurrentColor());
            }
            public void afterSelect(){
                JPM.setObjectColor(getCurrentColor());
                JPM.MW.ZF.setinfo("prop_scolor", false);
            }
            public void setPalettes(){
                setUsedColors(JPM.MW.ZF.ZC.getConstruction().getSpecialColors());
            }
        };
        myLine.add(JPM.MW.ColorPicker);
        addNewLine();
    }

    public JIcon addIcon(String name, Vector Group) {
        boolean invisible=false;
        if (name.startsWith("@@")) {
            invisible=true;
            name=name.substring(2);
        }
        if (JPM.acceptedIcon(name)) {
            JIcon myicon=new JIcon(ZF, JPM, this, name, Group);
            if (invisible) {
                JPM.AllIcons.add(myicon);
                return myicon;
            }
            if (JPM.MustBeFirstIconOnLine(name)) {
                addNewLine();
            }
            getLastLine().add(myicon);
            JPM.AllIcons.add(myicon);
            return myicon;
        }
        return null;
    }

    public void addIcons(String myname[], Vector Group) {
        for (int i=0; i<myname.length; i++) {
            addIcon(myname[i], Group);
//            if (JPM.acceptedIcon(myname[i])) {
//                JIcon myicon=new JIcon(ZF,JPM,this,myname[i],Group);
//                if (JPM.MustBeFirstIconOnLine(myname[i])) addNewLine();
//                getLastLine().add(myicon);
//                JPM.AllIcons.add(myicon);
//            }
        }
        ;
    }

    public void addVirtualIcons(String myname[], Vector Group) {
        for (int i=0; i<myname.length; i++) {
            JIcon myicon=new JIcon(ZF, JPM, this, myname[i], Group);
            JPM.AllIcons.add(myicon);
        }
        ;
    }

    public JIcon addToggleIcon(String myname) {
        if (JPM.acceptedIcon(myname)) {
            JIcon myicon=new JIcon(ZF, JPM, this, myname, new Vector());
            if (JPM.MustBeFirstIconOnLine(myname)) {
                addNewLine();
            }
            getLastLine().add(myicon);
            JPM.AllIcons.add(myicon);
            return myicon;
        } else {
            return null;
        }
    }

    public void addSimpleIcon(String myname) {
        if (JPM.acceptedIcon(myname)) {
            JIcon myicon=new JIcon(ZF, JPM, this, myname, null);
            if (JPM.MustBeFirstIconOnLine(myname)) {
                addNewLine();
            }
            getLastLine().add(myicon);
            JPM.AllIcons.add(myicon);
        }
        ;
    }

    public void addNewLine() {
        ZoneContent.newline();
    }

    public JPanel getLastLine() {
        JPanel lastline;
        if (ZoneContent.getComponentCount()==0) {
            lastline=ZoneContent.newline();
        } else {
            lastline=(JPanel) ZoneContent.getComponent(ZoneContent.getComponentCount()-1);
            if (lastline.getComponentCount()==6) {
                ZoneContent.add(lastline);
                lastline=ZoneContent.newline();
            }
        }
        return lastline;
    }

    public void setTitle(String tt) {
        ZoneTitle.title.setText(tt);
    }

    
    

         

          ;
        private   
          
          
          class JPaletteZoneTitleBar extends JPanel{
        JLabel title=new JLabel();
        private JPalette JP;
        private JPaletteZone myZone;
        private String TitleText;
        
        private boolean MouseOn=false;
        JPalette FP;

        public void paintComponent(java.awt.Graphics g) {
            Image OnImage=(JPM==null)?JMacrosTools.CurrentJZF.JZT.getImage("PaletteTitleBarH.png"):JPM.MW.JZT.getImage("PaletteTitleBarH.png");
            Image OffImage=(JPM==null)?JMacrosTools.CurrentJZF.JZT.getImage("PaletteTitleBarN.png"):JPM.MW.JZT.getImage("PaletteTitleBarN.png");
            java.awt.Dimension d=this.getSize();
            if (MouseOn) {
                g.drawImage(OnImage, 0, 0, PW, d.height, this);
            } else {
                g.drawImage(OffImage, 0, 0, PW, d.height, this);
            }
            ;
        }

        public JPaletteZoneTitleBar(JPalette jp, JPaletteZone myz, String TitleBar) {
            JP=jp;
            myZone=myz;
            TitleText=TitleBar;
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
            this.setFocusable(false);
            this.setAlignmentX(0);
            this.setMaximumSize(new java.awt.Dimension(PW, 17));
            this.setMinimumSize(new java.awt.Dimension(PW, 17));
            this.setPreferredSize(new java.awt.Dimension(PW, 17));
            this.setSize(PW, 17);
            title.setText(TitleBar);
            title.setIcon(bottomTriangle);
            title.setIconTextGap(7);
            title.setFont(new java.awt.Font(JGlobals.GlobalFont, 0, 11));
            title.setHorizontalAlignment(SwingConstants.LEFT);
            title.setMaximumSize(new java.awt.Dimension(PW-17, 17));
            title.setMinimumSize(new java.awt.Dimension(PW-17, 17));
            title.setPreferredSize(new java.awt.Dimension(PW-17, 17));
            this.add(title, null);

            TitleMouseAdapter=new MouseAdapter() {

                public void mouseReleased(MouseEvent e) {
                    MouseOn=false;
                    ZoneTitle.repaint();
                    x=e.getX();
                    y=e.getY();
                }

                public void mousePressed(MouseEvent e) {
                    MouseOn=true;
                    ZoneTitle.repaint();
                    x=e.getX();
                    y=e.getY();
                }

                public void mouseClicked(MouseEvent e) {
                    if (ContentVisible) {

                        myZone.collapse();
                    } else {
                        myZone.expand();
                    }
                }
            };

            JButton mybtn=new JButton();
            mybtn.setOpaque(false);
            mybtn.setContentAreaFilled(false);
            if (JP.MainPalette) {
                mybtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/icons/palette/Pdetach.gif")));

                mybtn.addMouseListener(new MouseAdapter() {

                    public void mousePressed(MouseEvent e) {
                        MouseOn=true;
                        ZoneTitle.repaint();
                        FP=JPM.detachpalette1(myZone);
                    }

                    public void mouseReleased(MouseEvent e) {
                        JPM.detachpalette2(FP, myZone);
                    }
                });
            } else {
                mybtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/icons/palette/Pclose.png")));
                mybtn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/icons/palette/Pcloseover.png")));

                mybtn.addMouseListener(new MouseAdapter() {

                    public void mousePressed(MouseEvent e) {
                        MouseOn=true;
                        ZoneTitle.repaint();
                        if (myZone.mainmember) {
                            JPM.dockpalette(JP, myZone);
                        } else {
                            MouseOn=false;
                            myZone.JP.setVisible(false);

                        }



                    }
                });
            }
            ;

            mybtn.setOpaque(false);
            mybtn.setBorder(BorderFactory.createEmptyBorder());
//            this.add(mybtn, null);
            if ((JPM==null)||(JGlobalPreferences.undockpalette)) {
                this.add(mybtn, null);
            }
            this.addMouseListener(TitleMouseAdapter);
            this.addMouseMotionListener(new MouseMotionAdapter() {

                public void mouseDragged(MouseEvent e) {
                    int xx=JP.getLocation().x;
                    int yy=JP.getLocation().y;
                    int Ybottom=yy+(e.getY()-y)+JP.getPreferredSize().height;
                    int ScreenBottom=Toolkit.getDefaultToolkit().getScreenSize().height;
                    if (Ybottom<ScreenBottom) {
                        JP.setLocation(xx+(e.getX()-x), yy+(e.getY()-y));
                    } else {
                        JP.setLocation(xx+(e.getX()-x), ScreenBottom-JP.getPreferredSize().height);
                    }

                }
            });
        }
    }

    public class JPaletteZoneContent extends javax.swing.JPanel {

        private rene.zirkel.ZirkelFrame ZF;
        private JPalette JP;
        private JPaletteZone myZone;
        private String[] StringObjects;

        public void paintComponent(java.awt.Graphics g) {
//            super.paintComponent(g);
        }

        public JPaletteZoneContent(rene.zirkel.ZirkelFrame zf, JPalette jp, JPaletteZone myz) {
            ZF=zf;
            JP=jp;
            myZone=myz;
            initComponents();
        }

        public void initComponents() {
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
        }

        public JPanel newline() {
            JPanel myline=new ContentLine();
            this.add(myline);
            return myline;
        }

        public JPanel newLabelLine() {
            JPanel myline=new LabelLine();
            this.add(myline);
            return myline;
        }

        private void JIconBarDragged(java.awt.event.MouseEvent evt) {
            int x=this.getX()+evt.getX();
            int xright=x+this.getBounds().width;
            if (x>0) {
                if (xright<JP.getBounds().width) {
                    this.setLocation(x, this.getY());
                } else {
                    setLocation(JP.getBounds().width-this.getBounds().width, this.getY());
                }
            } else {
                this.setLocation(0, this.getY());
            }
        }

        class ContentLine extends javax.swing.JPanel {

            public void paintComponent(java.awt.Graphics g) {
                java.awt.Dimension d=this.getSize();
                g.drawImage(JPM.MW.JZT.getImage("palbackground.gif"), 0, 0, d.width, d.height, this);
            }

            public ContentLine() {
                int lineheight=(JPM==null)?32:JPM.paletteiconsize;
                this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
                this.setAlignmentX(0);
                fixsize(this, PW, lineheight);
//                this.setMaximumSize(new java.awt.Dimension(PW, lineheight));
//                this.setMinimumSize(new java.awt.Dimension(PW, lineheight));
//                this.setPreferredSize(new java.awt.Dimension(PW, lineheight));
//                this.setSize(PW,lineheight);
            }
        }

        class LabelLine extends javax.swing.JPanel {

            public void paintComponent(java.awt.Graphics g) {
                java.awt.Dimension d=this.getSize();
                g.drawImage(JPM.MW.JZT.getImage("palbackground.gif"), 0, 0, d.width, d.height, this);
                Graphics2D g2=(Graphics2D) g;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
                g2.setColor(new Color(0, 0, 255));
                g2.fillRect(3, 3, d.width-6, d.height-6);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
                g2.drawRect(3, 3, d.width-7, d.height-7);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            public LabelLine() {
                int lineheight=20;
                this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
                this.setAlignmentX(0);
                fixsize(this, PW, lineheight);
            }
        }
    }
}
