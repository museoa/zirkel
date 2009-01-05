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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;
import rene.gui.Global;
import rene.util.ImageSelection;
import rene.util.PngEncoder;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.graphics.MyGraphicsEPS;
import rene.zirkel.graphics.MyGraphicsSvg;
import eric.JHelpPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowFocusListener;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroItem;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.TextObject;

public class JZirkelFrame extends javax.swing.JFrame implements WindowFocusListener, MouseListener, MouseMotionListener {

    public boolean construct = true;
    public ZirkelFrame ZF = new ZirkelFrame(false);
    public boolean is3D = false;
    JZirkelFrame MW;
    JZirkelTheme JZT = new JZirkelTheme();
    JLocalPreferences JPR;
    JPanel Content;
    JTitleBar TitleBar;
    JStatusBar StatusBar;
    JGeneralMenuBar GeneralMenuBar;
    JZirkelFrameContent ZContent;
    public JPaletteManager JPM;
    boolean restricted = false;
    public boolean restrictedSession = false;
    boolean EditRestricted = false;
    ResourceBundle Strs;
    Runnable doactualisemacrostree;
    JZirkelChanges ZChanges;
    int Wwidth = 800;
    int Pwidth = 194;
    int Wheight = 600;
    public boolean busy = false;
    private int changeX;
    private int changeY;
    private MouseEvent pressed;
    private Point location;
    public JPointName PointLabel;
    public JColorPicker ColorPicker;

    public JZirkelFrame(boolean with3D, int xloc, int yloc, int w, int h) {
        MW = this;
        is3D = with3D;
        Pwidth = IconSize() * 6;
        PointLabel = new JPointName(this);
        PointObject.setPointLabel(PointLabel);

//        this.addWindowStateListener(this);
        this.addWindowFocusListener(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        this.addWindowListener(new WindowAdapter() {

            public void windowIconified(WindowEvent windowEvent) {
                JMacrosTools.activateFrontMostWindow();
            }

            public void windowDeiconified(WindowEvent windowEvent) {
                toFront();
                requestFocus();
            }
        });


        ZF.ZC.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (Global.getParameter("smartboard", false)) {
                    return;
                }
                String s = PointLabel.getBetterName(null, true);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (!Global.getParameter("smartboard", false)) {
                    return;
                }
                String s = PointLabel.getBetterName(null, true);
            }
        });
        ZF.ZC.getConstruction();
        JPR = new JLocalPreferences(this);


        // "busy" is set to true
        // and set to false in JGlobals.CheckRestrictedIcons :
        this.doactualisemacrostree = new Runnable() {

            public void run() {
                if ((!ZF.isEnabled()) || (busy)) {
                    SwingUtilities.invokeLater(doactualisemacrostree);
                } else {
//                    SetTitle(ZF.getTitle());
                    ZContent.macros.myJML.initMacrosTree();
                    JPM.setSelected("grid", ZF.ZC.showGrid());
                    JPM.setSelected("hidden", false);
                    Global.setParameter("grid.colorindex", ZF.ZC.GridColor);
                    Global.setParameter("grid.thickness", ZF.ZC.GridThickness);
                    Global.setParameter("grid.labels", ZF.ZC.GridLabels);
                    Global.setParameter("grid.axesonly", ZF.ZC.AxesOnly);
                    JPM.setSelected("acolor" + Global.getParameter("grid.colorindex", 0), true);
                    JPM.setSelected("athickness" + Global.getParameter("grid.thickness", 0), true);
                    JPM.setSelected("numgrid", Global.getParameter("grid.labels", false));
                    JPM.setSelected("dottedgrid", Global.getParameter("grid.axesonly", false));

                    JPM.setSelected("partial", false);
                    JPM.setSelected("plines", false);
                    JPM.setSelected("showvalue", false);
                    if (ZF.ZC.getConstruction().BackgroundFile == null) {
                        JPM.setSelected("background", false);
                    } else {
                        JPM.setSelectedWithoutClic("background", true);

                    }
                    ZChanges.CLength = 0;
                    JPM.MainPalette.FollowWindow();

                }
                ;
            }
        };

        String lang = Global.getParameter("language", "");
        String country = Global.getParameter("country", "");

        if (!lang.equals("")) {
            try {
                JGlobals.changeGlobalFont(lang);
                Locale.setDefault(new Locale(lang, country));
            } catch (Exception ex) {
                Locale.setDefault(new Locale("en", ""));
                Global.setParameter("language", "en");
                Global.setParameter("country", "");
            }
        }

        Strs = ResourceBundle.getBundle("eric/docs/JZirkelProperties");


        JZT.setTheme(Global.getParameter("LookAndFeel", "gray"));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setTitle("CaR");
        setBackground(new java.awt.Color(240, 240, 240));
        this.setUndecorated(true);


        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle dim = ge.getMaximumWindowBounds();


        Wwidth = w;
        Wheight = h;
//        int w=dim.width-Pwidth-10;
//        int h=dim.height-4;
//
//        Wwidth=(Wwidth>w)?w:Wwidth;
//        Wheight=(Wheight>h)?h:Wheight;

        if ((xloc == 0) && (yloc == 0)) {
            this.setLocation((dim.width - Wwidth - Pwidth) / 2, (dim.height - Wheight) / 2);
            ZF.setLocation((dim.width - Wwidth - Pwidth) / 2, (dim.height - Wheight) / 2);
        } else {
            this.setLocation(xloc, yloc);
            ZF.setLocation(xloc, yloc);
        }



        Content = new JPaletteContainer();
        this.setContentPane(Content);


        TitleBar = new JTitleBar();
        Content.add(TitleBar);
        StatusBar = new JStatusBar(ZF, this);
        GeneralMenuBar = new JGeneralMenuBar(ZF, this);
        Content.add(GeneralMenuBar);
        ZContent = new JZirkelFrameContent(ZF, this);
        Content.add(ZContent);

        Content.add(StatusBar);
        JPM = new JPaletteManager(ZF, this, IconSize());
        GeneralMenuBar.InitObjectsMenu();
        ZF.ZC.CDP.setVisible(false);
        ZChanges = new JZirkelChanges(ZF, this);
        JGlobals.setRestrictedIcons(Global.getParameter("restrictedicons", JGlobals.DefaultIcons));

//                this.pack();
//        this.setVisible(true);








        // These are very general keylisteners. They work great
        // even when the user is on the most deeper component !
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "action ENTER");
        getRootPane().getActionMap().put("action ENTER", new AbstractAction() {

            public void actionPerformed(ActionEvent arg0) {
                if (ZContent.isMacroPanel()) {
                    ZContent.macros.myJML.MacrosTree.nodepopup.renamenode();
                }
            }
        });
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "action DOWN");
        getRootPane().getActionMap().put("action DOWN", new AbstractAction() {

            public void actionPerformed(ActionEvent arg0) {
                if (ZContent.isMacroPanel()) {
                    ZContent.macros.myJML.MacrosTree.nodepopup.goDownOrUp(true);
                }
            }
        });
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "action UP");
        getRootPane().getActionMap().put("action UP", new AbstractAction() {

            public void actionPerformed(ActionEvent arg0) {
                if (ZContent.isMacroPanel()) {
                    ZContent.macros.myJML.MacrosTree.nodepopup.goDownOrUp(false);
                }
            }
        });
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "action ESC");
        getRootPane().getActionMap().put("action ESC", new AbstractAction() {

            public void actionPerformed(ActionEvent arg0) {
                JPM.setSelected("move", true);
                ZContent.ShowLeftPanel(0);
            }
        });


        this.setVisible(true);
        this.ResizeAll();
        construct = false;
    }

//    public void requestFocus(){
//        toFront();
//        super.requestFocus();
//    }
    public void repaint() {
    }

    public void pack() {
        super.pack();
        paintImmediately();
    }

    public void paintImmediately() {
//        getRootPane().paintImmediately(new Rectangle(0,0,getSize().width,getSize().height));
        TitleBar.paintImmediately();
        GeneralMenuBar.paintImmediately();
        ZContent.paintImmediately();
        StatusBar.paintImmediately();
    }

    public String ToolTip(String s) {
        String ToolTipText = "";
        String purename = (s.startsWith("bi_")) ? s.substring(3) : s;
        try {
            ToolTipText = Strs.getString("palette.info." + purename);
        } catch (Exception e1) {
            try {
                ToolTipText = Strs.getString("palette.info." + s);
            } catch (Exception e2) {
                ToolTipText = rene.zirkel.Zirkel.name("iconhelp." + purename);
            }
        }
        ;
        return ToolTipText;
    }

    public String FilteredStatus(String status) {
        String newstatus = status;
        int index;
        if ((index = status.indexOf("@builtin@/syma")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/syma\\E", Loc("palette.info.bi_syma"));
//            newstatus=newstatus.replace("@builtin@/syma",Loc("palette.info.bi_syma"));
        } else if ((index = status.indexOf("@builtin@/symc")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/symc\\E", Loc("palette.info.bi_symc"));
        } else if ((index = status.indexOf("@builtin@/trans")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/trans\\E", Loc("palette.info.bi_trans"));
        } else if ((index = status.indexOf("@builtin@/med")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/med\\E", Loc("palette.info.bi_med"));
        } else if ((index = status.indexOf("@builtin@/biss")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/biss\\E", Loc("palette.info.bi_biss"));
        } else if ((index = status.indexOf("@builtin@/circ")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/circ\\E", Loc("palette.info.bi_circ"));
        } else if ((index = status.indexOf("@builtin@/arc")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/arc\\E", Loc("palette.info.bi_arc"));
        } else if ((index = status.indexOf("@builtin@/function_u")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/function_u\\E", ToolTip("bi_function_u"));
        } else if ((index = status.indexOf("@builtin@/t_align")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/t_align\\E", Loc("palette.info.bi_t_align"));
        } else if ((index = status.indexOf("@builtin@/t_para")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/t_para\\E", Loc("palette.info.bi_t_para"));
        } else if ((index = status.indexOf("@builtin@/t_perp")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/t_perp\\E", Loc("palette.info.bi_t_perp"));
        } else if ((index = status.indexOf("@builtin@/t_equi")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/t_equi\\E", Loc("palette.info.bi_t_equi"));
        } else if ((index = status.indexOf("@builtin@/t_app")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/t_app\\E", Loc("palette.info.bi_t_app"));
        } else if ((index = status.indexOf("@builtin@/t_conf")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/t_conf\\E", Loc("palette.info.bi_t_conf"));
        } else if ((index = status.indexOf("@builtin@/3Dcoords")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/3Dcoords\\E", Loc("palette.info.bi_3Dcoords"));
        } else if ((index = status.indexOf("@builtin@/3Dcube")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/3Dcube\\E", Loc("palette.info.bi_3Dcube"));
        } else if ((index = status.indexOf("@builtin@/3Darete")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/3Darete\\E", Loc("palette.info.bi_3Darete"));
        } else if ((index = status.indexOf("@builtin@/3Dtetra")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/3Dtetra\\E", Loc("palette.info.bi_3Dtetra"));
        } else if ((index = status.indexOf("@builtin@/3Ddode")) > 0) {
            newstatus = newstatus.substring(index);
            newstatus = newstatus.replaceAll("\\Q@builtin@/3Ddode\\E", Loc("palette.info.bi_3Ddode"));
        }
        ;
        return newstatus;
    }

    public int IconSize() {
        int icsize = 32;
        switch (Global.getParameter("options.iconsize", 1)) {
            case 0:
                icsize = 32;
                break;
            case 1:
                icsize = 28;
                break;
            case 2:
                icsize = 24;
                break;
        }
        ;
        return icsize;
    }

    private void fixsize(JComponent cp, Dimension d) {
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    public void ResizeAll() {
        ResizeAll(Wwidth, Wheight);
    }

    public void ResizeAll(int w, int h) {
        if ((h > 180) && (w > 100)) {
            Wwidth = w;
            Wheight = h;
            int LeftPanelWidth = (ZContent.leftpanel == null) ? 0 : ZContent.leftpanelwidth;
            int ZContentHeight = h - JZT.TitleBarHeight - JZT.MenuBarHeight - JZT.StatusHeight;
            int ZContentWidth = w - 2 * JZT.VertBorderWidth;
            int ZContentCenterWidth = ZContentWidth - LeftPanelWidth;
            double ZCCH = ((double) ZContentCenterWidth / (double) ZContentWidth) * (double) ZContentHeight;
            int ZContentCenterHeight = (int) Math.round(ZCCH);
            int ZContentCenterBorderHeight = (ZContentHeight - ZContentCenterHeight) / 2;

            int MacroPanelHeight = (ZContent.macros.myJML.createmacropanel.visible) ? 66 : 0;

            ZContentCenterHeight = ZContentHeight - 2 * ZContentCenterBorderHeight;

            fixsize(TitleBar, new Dimension(w, JZT.TitleBarHeight));
            fixsize(TitleBar.buttons, new Dimension(60, JZT.TitleBarTextHeight));

            int t = TitleBar.titlepixelwidth();
            int spacerw = 0;
            if ((System.getProperty("mrj.version") != null) && JZT.AllowMacLook()) {
                spacerw = Math.max(0, (w - t - 132) / 2);
                fixsize(TitleBar.macosspacer, new Dimension(6, JZT.TitleBarHeight));
                fixsize(TitleBar.titlespacer, new Dimension(spacerw, JZT.TitleBarHeight));

            } else {
                spacerw = (t + 66 > w) ? 0 : Math.min(w - t - 66, (w - t) / 2);
                fixsize(TitleBar.titlespacer, new Dimension(spacerw, JZT.TitleBarHeight));
            }
            fixsize(TitleBar.windowtitle, new Dimension(w - spacerw - 66, JZT.TitleBarTextHeight));


            fixsize(StatusBar, new Dimension(w, JZT.StatusHeight));
            fixsize(StatusBar.status, new Dimension(w - 50, JZT.StatusHeight - 4));
            fixsize(GeneralMenuBar, new Dimension(w, JZT.MenuBarHeight));

            fixsize(ZContent, new Dimension(w, ZContentHeight));
            fixsize(ZContent.left, new Dimension(JZT.VertBorderWidth, ZContentHeight));

            ZContent.history.setVisible(false);
            ZContent.macros.setVisible(false);
            ZContent.help.setVisible(false);

            if (ZContent.leftpanel != null) {
                if (!(ZContent.leftpanel.isVisible())) {
                    ZContent.leftpanel.setVisible(true);
                }
                fixsize(ZContent.leftpanel, new Dimension(LeftPanelWidth, ZContentHeight));
                fixsize(ZContent.leftpanel.vertseparator, new Dimension(JZT.VertSeparatorWidth, ZContentHeight));
                fixsize(ZContent.leftpanel.title, new Dimension(LeftPanelWidth - JZT.VertSeparatorWidth, JZT.LeftPanelTitleHeight));
                fixsize(ZContent.leftpanel.wholecontent, new Dimension(LeftPanelWidth - JZT.VertSeparatorWidth, ZContentHeight));
                fixsize(ZContent.leftpanel.content, new Dimension(LeftPanelWidth - JZT.VertSeparatorWidth, ZContentHeight - JZT.LeftPanelTitleHeight));

                if (ZContent.history.isVisible()) {
                    ZF.ZC.CDP.setSize(new Dimension(LeftPanelWidth - JZT.VertSeparatorWidth, ZContentHeight));
                    ZF.ZC.CDP.validate();
                }
                ;

                if (ZContent.help.isVisible()) {
                    ((JHelpPanel) ZContent.help.content).fixPanelSize(LeftPanelWidth - JZT.VertSeparatorWidth, ZContentHeight);
//                    fixsize((JComponent) ZContent.help.content.getComponent(0), new Dimension(LeftPanelWidth-JZT.VertSeparatorWidth, 22));
//                    fixsize((JComponent) ZContent.help.content.getComponent(2), new Dimension(LeftPanelWidth-JZT.VertSeparatorWidth, 25));
                }
                ;

                if (ZContent.macros.isVisible()) {
                    fixsize(ZContent.macros.myJML.controls, new Dimension(LeftPanelWidth - JZT.VertSeparatorWidth, 22));
                    fixsize(ZContent.macros.myJML.createmacropanel, new Dimension(LeftPanelWidth - JZT.VertSeparatorWidth, MacroPanelHeight));
                }
                ;





            }
            ;

            fixsize(ZContent.right, new Dimension(JZT.VertBorderWidth, ZContentHeight));
            fixsize(ZContent.center, new Dimension(ZContentCenterWidth, ZContentHeight));
            Dimension dzcenterfigure = new Dimension(ZContentCenterWidth, ZContentCenterHeight);
            fixsize(ZContent.center.figure, dzcenterfigure);
            fixsize(ZF.ZC, dzcenterfigure);
            ZF.ZC.UseSize = dzcenterfigure;
            Dimension dzcenterborder = new Dimension(ZContentCenterWidth, ZContentCenterBorderHeight);
            fixsize(ZContent.center.b1, dzcenterborder);
            fixsize(ZContent.center.b2, dzcenterborder);

            this.setSize(w, h);

            paintImmediately();
        }
    }

    public class JTitleBar extends JPanel {

        JLabel windowtitle;
        JPanel titlespacer, macosspacer, buttons;
        Point origin = new Point();
//        private int x=-1;
//        private int y=-1;
        private int xw = -1;
        private int yw = -1;
        private JButton reducebtn, growbtn, closebtn;

//        private boolean MouseOn=false;
        public void paintComponent(java.awt.Graphics g) {
//            super.paintComponent(g);
            java.awt.Dimension d = this.getSize();
            g.drawImage(JZT.getImage("titlebar.gif"), 0, 0, d.width, d.height, this);

            if (construct) {
                return;
            }
            if (!MW.equals(JMacrosTools.CurrentJZF)) {
                JZT.setDisable(g, d);
            }
            ;
        }

        public void paintImmediately() {
            this.paintImmediately(0, 0, getWidth(), getHeight());
        }

        public int titlepixelwidth() {
//            FontMetrics fm = getFontMetrics(getFont());
            FontMetrics fm = getFontMetrics(new Font(JGlobals.GlobalFont, 0, JGlobals.MenuTextSize));

            return fm.stringWidth(windowtitle.getText());
        }

        private void growboxtouched() {
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//
//            Rectangle screenRect = ge.getMaximumWindowBounds();

//            if (System.getProperty("os.name").equals("Linux")) {
//                screenRect.grow(0, -25);
//            }

            int w = Zirkel.SCREEN.width - Pwidth - 5;
            int h = Zirkel.SCREEN.height - 4;
            if ((MW.getSize().width == w) && (MW.getSize().height == h)) {
                MW.setLocation(xw, yw);
                MW.ResizeAll(800, 600);
            } else {
                xw = MW.getLocation().x;
                yw = MW.getLocation().y;
                MW.setLocation(Zirkel.SCREEN.x + 2, Zirkel.SCREEN.y + 2);
                MW.ResizeAll(w, h);
            }
            JGlobals.JPB.fixWindowsPosition();

            // Very important for java 1.5 versions :
            MW.pack();

            MW.ZF.ZC.recompute();
            MW.ZF.ZC.validate();
            MW.ZF.ZC.repaint();
            JPM.MainPalette.FollowWindow();
        }

        public void init() {
            this.removeAll();
            titlespacer = new JPanel();
            titlespacer.setOpaque(false);

            macosspacer = new JPanel();
            macosspacer.setOpaque(false);

            buttons = new JPanel();
            buttons.setLayout(new javax.swing.BoxLayout(buttons, javax.swing.BoxLayout.X_AXIS));
            buttons.setOpaque(false);

            windowtitle = new JLabel("");
            windowtitle.setFont(new Font(JGlobals.GlobalFont, 0, 12));
            windowtitle.setForeground(new Color(80, 80, 80));
            windowtitle.setHorizontalAlignment(SwingConstants.LEFT);



            reducebtn = new JButton();
            reducebtn.setBorder(BorderFactory.createEmptyBorder());
            reducebtn.setOpaque(false);
            reducebtn.setContentAreaFilled(false);
            reducebtn.setFocusable(false);
            reducebtn.addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent e) {
                    MW.setState(JFrame.ICONIFIED);
                }
            });

            growbtn = new JButton();
            growbtn.setBorder(BorderFactory.createEmptyBorder());
            growbtn.setOpaque(false);
            growbtn.setContentAreaFilled(false);
            growbtn.setFocusable(false);
            growbtn.addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent e) {
                    growboxtouched();
                }
            });


            closebtn = new JButton();
            closebtn.setBorder(BorderFactory.createEmptyBorder());
            closebtn.setOpaque(false);
            closebtn.setContentAreaFilled(false);
            closebtn.setFocusable(false);
            closebtn.addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent e) {
                    JMacrosTools.disposeCurrentJZF();
                }
            });
            reducebtn.setIcon(JZT.getIcon("zreducebutton.png"));
            reducebtn.setRolloverIcon(JZT.getIcon("zreducebuttonover.png"));
            growbtn.setIcon(JZT.getIcon("zgrowbutton.png"));
            growbtn.setRolloverIcon(JZT.getIcon("zgrowbuttonover.png"));
            closebtn.setIcon(JZT.getIcon("zclosebutton.png"));
            closebtn.setRolloverIcon(JZT.getIcon("zclosebuttonover.png"));

            if ((System.getProperty("mrj.version") != null) && JZT.AllowMacLook()) {
                this.add(macosspacer);
                buttons.add(closebtn);
                buttons.add(reducebtn);
                buttons.add(growbtn);
                this.add(buttons);
                this.add(titlespacer);
                this.add(windowtitle);
            } else {
                this.add(titlespacer);
                this.add(windowtitle);
                buttons.add(reducebtn);
                buttons.add(growbtn);
                buttons.add(closebtn);
                this.add(buttons);

            }

            titlespacer.setAlignmentY(0.0f);
            macosspacer.setAlignmentY(0.0f);
            windowtitle.setAlignmentY(0.0f);
            buttons.setAlignmentY(0.0f);

            reducebtn.setAlignmentY(0.5F);
            growbtn.setAlignmentY(0.5F);
            closebtn.setAlignmentY(0.5F);

            this.revalidate();
        }

        public JTitleBar() {
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
            init();

        }
    }

    private class JPaletteContainer extends JPanel {

        public void paintComponents(Graphics g) {
        }
//        public void paint(Graphics g){
//            
//        }

        public JPaletteContainer() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        }
    }

    public void SetTitle(String newtitle) {
//        if (true) return;
        FontMetrics fm = getFontMetrics(new Font(JGlobals.GlobalFont, 0, JGlobals.MenuTextSize));
        int t = fm.stringWidth(newtitle);
        int w = this.getSize().width;
        int spacerw = 0;
        if ((System.getProperty("mrj.version") != null)) {
            spacerw = Math.max(0, (w - t - 132) / 2);
            fixsize(TitleBar.macosspacer, new Dimension(6, JZT.TitleBarHeight));
            fixsize(TitleBar.titlespacer, new Dimension(spacerw, JZT.TitleBarHeight));

        } else {
            spacerw = (t + 66 > w) ? 0 : Math.min(w - t - 66, (w - t) / 2);
            fixsize(TitleBar.titlespacer, new Dimension(spacerw, JZT.TitleBarHeight));
        }
        fixsize(TitleBar.windowtitle, new Dimension(w - spacerw - 66, JZT.TitleBarTextHeight));

        TitleBar.windowtitle.setText(newtitle);
        TitleBar.validate();
        TitleBar.repaint();

        this.setTitle(newtitle);
    }

    private String Loc(String s) {
        return Strs.getString(s);
    }

    public void runmacro(String macroname) {
        Vector mc;
        Macro m;
        TextObject t;
        mc = ZF.ZC.getMacros();

        for (int i = 0; i < mc.size(); i++) {
            m = ((MacroItem) mc.elementAt(i)).M;

            if (m.getName().equals(macroname)) {
                if (m.getName().equals("@builtin@/syma")) {
                    m.Prompts[0] = Loc("macro.bi_syma.0");
                    m.Prompts[1] = Loc("macro.bi_syma.1");
                } else if (m.getName().equals("@builtin@/symc")) {
                    m.Prompts[0] = Loc("macro.bi_symc.0");
                    m.Prompts[1] = Loc("macro.bi_symc.1");
                } else if (m.getName().equals("@builtin@/trans")) {
                    m.Prompts[0] = Loc("macro.bi_trans.0");
                    m.Prompts[1] = Loc("macro.bi_trans.1");
                    m.Prompts[2] = Loc("macro.bi_trans.2");
                } else if (m.getName().equals("@builtin@/med")) {
                    m.Prompts[0] = Loc("macro.bi_med.0");
                    m.Prompts[1] = Loc("macro.bi_med.1");
                } else if (m.getName().equals("@builtin@/biss")) {
                    m.Prompts[0] = Loc("macro.bi_biss.0");
                    m.Prompts[1] = Loc("macro.bi_biss.1");
                    m.Prompts[2] = Loc("macro.bi_biss.2");
                } else if (m.getName().equals("@builtin@/circ")) {
                    m.Prompts[0] = Loc("macro.bi_circ.0");
                    m.Prompts[1] = Loc("macro.bi_circ.1");
                    m.Prompts[2] = Loc("macro.bi_circ.2");
                } else if (m.getName().equals("@builtin@/arc")) {
                    m.Prompts[0] = Loc("macro.bi_circ.0");
                    m.Prompts[1] = Loc("macro.bi_circ.1");
                    m.Prompts[2] = Loc("macro.bi_circ.2");
                } else if (m.getName().equals("@builtin@/function_u")) {
                    m.Prompts[0] = Loc("macro.bi_expression.0");
                } else if (m.getName().equals("@builtin@/t_align")) {
                    m.Prompts[0] = Loc("macro.bi_circ.0");
                    m.Prompts[1] = Loc("macro.bi_circ.1");
                    m.Prompts[2] = Loc("macro.bi_circ.2");
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 1);
                    t.setLines(Loc("macro.bi_t_align.text1"));
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 2);
                    t.setLines(Loc("macro.bi_t_align.text0"));
                } else if (m.getName().equals("@builtin@/t_para")) {
                    m.Prompts[0] = Loc("macro.bi_t_para.0");
                    m.Prompts[1] = Loc("macro.bi_t_para.1");
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 1);
                    t.setLines(Loc("macro.bi_t_para.text0"));
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 2);
                    t.setLines(Loc("macro.bi_t_para.text1"));
                } else if (m.getName().equals("@builtin@/t_perp")) {
                    m.Prompts[0] = Loc("macro.bi_t_para.0");
                    m.Prompts[1] = Loc("macro.bi_t_para.1");
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 2);
                    t.setLines(Loc("macro.bi_t_perp.text1"));
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 1);
                    t.setLines(Loc("macro.bi_t_perp.text0"));
                } else if (m.getName().equals("@builtin@/t_equi")) {
                    m.Prompts[0] = Loc("macro.bi_t_equi.0");
                    m.Prompts[1] = Loc("macro.bi_t_equi.1");
                    m.Prompts[2] = Loc("macro.bi_t_equi.2");
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 1);
                    t.setLines(Loc("macro.bi_t_equi.text0"));
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 2);
                    t.setLines(Loc("macro.bi_t_equi.text1"));
                } else if (m.getName().equals("@builtin@/t_app")) {
                    m.Prompts[0] = Loc("macro.bi_t_app.0");
                    m.Prompts[1] = Loc("macro.bi_t_app.1");
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 1);
                    t.setLines(Loc("macro.bi_t_app.text1"));
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 2);
                    t.setLines(Loc("macro.bi_t_app.text0"));
                } else if (m.getName().equals("@builtin@/t_conf")) {
                    m.Prompts[0] = Loc("macro.bi_t_conf.0");
                    m.Prompts[1] = Loc("macro.bi_t_conf.1");
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 1);
                    t.setLines(Loc("macro.bi_t_conf.text1"));
                    t = (TextObject) m.getTargets().get(m.getTargets().size() - 2);
                    t.setLines(Loc("macro.bi_t_conf.text0"));
                } else if (m.getName().equals("@builtin@/3Dcoords")) {
                    m.Prompts[4] = Loc("macro.bi_3Dcoords.0");
                } else if (m.getName().equals("@builtin@/3Dcube")) {
                    m.Prompts[4] = Loc("macro.bi_3Dcube.0");
                } else if (m.getName().equals("@builtin@/3Darete")) {
                    m.Prompts[0] = Loc("macro.bi_3Darete.0");
                    m.Prompts[1] = Loc("macro.bi_3Darete.1");
                    m.Prompts[2] = Loc("macro.bi_3Darete.2");
                    m.Prompts[3] = Loc("macro.bi_3Darete.3");
                } else if (m.getName().equals("@builtin@/3Dtetra")) {
                    m.Prompts[4] = Loc("macro.bi_3Dtetra.0");
                } else if (m.getName().equals("@builtin@/3Ddode")) {
                    m.Prompts[4] = Loc("macro.bi_3Ddode.0");
                }
                ZF.runMacro(m);
            }
        }
        ;
    }

//    public void loadmacros(String name){
//        ZF.setinfo("macro");
//        if (name.equals("")){
//            ZF.loadMacros();
//        }else{
//            InputStream o;
//            try {
//                o = new FileInputStream(name);
//                if (ZF.isCompressed(name)) o=new GZIPInputStream(o);
//                ZF.ZC.load(o,false,true);
//                o.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//
//        SwingUtilities.invokeLater(doactualisemacrostree);
//    }
    public void SaveJarAndLaunchBrowser(String targetpath, String targetfile) {
        String sep = System.getProperty("file.separator");
        String mypath = JGlobals.AppPath();
        if (new File(mypath + "CaRMetal.jar").exists()) {
            try {
                InputStream in = new FileInputStream(mypath + "CaRMetal.jar");
                OutputStream out = new FileOutputStream(targetpath + sep + "CaRMetal.jar");
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                    out.flush();
                }
                out.close();
                in.close();

                JBrowserLauncher.openURL(targetpath + sep + targetfile);

            } catch (Exception ex) {
            }
        }
    }

    public void SaveSlideShow() {
        ZContent.macros.myJML.MacrosTree.nodepopup.MakeSlideShow();
    }

    public void savefile() {
        ZContent.macros.myJML.MacrosTree.nodepopup.savefile();
    }

    public void savefileas() {
        if ((!restrictedSession) && (restricted)) {
            setRestrictedView(false);
        }
        ZContent.macros.myJML.MacrosTree.nodepopup.savefileas();
    }

    public void setRestrictedView(boolean on) {
        restricted = on;
        if (!on) {
            GeneralMenuBar.editpaletteitem.setSelected(false);
            EditRestricted = false;
        }
        GeneralMenuBar.restrictpaletteitem.setSelected(on);
        GeneralMenuBar.editpaletteitem.setEnabled(on);
        JGlobals.setRestrictedIcons(Global.getParameter("restrictedicons", JGlobals.DefaultIcons));
        JPM.dispose();
        JPM = null;
        JPM = new JPaletteManager(ZF, this, IconSize());
        JPM.MainPalette.setVisible(true);
    }

    public void saveSVG() {
        int Scale = 1;
        int w = ZF.ZC.getSize().width * Scale;
        int h = ZF.ZC.getSize().height * Scale;
        ZF.ZC.PointSize = Global.getParameter("minpointsize", 3) * Scale;
        ZF.ZC.FontSize = Global.getParameter("minfontsize", 12) * Scale;

        ZF.ZC.IW = w;
        ZF.ZC.IH = h;
        ZF.ZC.recompute();

        FileSystemView vueSysteme = FileSystemView.getFileSystemView();
        File def = vueSysteme.getHomeDirectory();
        File desk = vueSysteme.getChild(def, "Desktop");
        File choice = (desk == null) ? def : desk;
        JFileChooser jfc = new javax.swing.JFileChooser(choice);
        jfc.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jfc.setApproveButtonText("Export to SVG");
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(new ImageFilter());

        int rep = jfc.showSaveDialog(null);

        if (rep == JFileChooser.APPROVE_OPTION) {
            File outputfile = jfc.getSelectedFile();
            String filename = outputfile.getAbsolutePath();
            String ext = (outputfile.getAbsolutePath().endsWith(".svg")) ? "" : ".svg";
            try {
                OutputStream o = new FileOutputStream(filename + ext);
                if (ZirkelFrame.isCompressed(filename)) {
                    o = new GZIPOutputStream(o);
                }
                PrintWriter out = new PrintWriter(new OutputStreamWriter(o, "UTF8"));
                MyGraphicsSvg svg = new MyGraphicsSvg(out, w, h);
                svg.setSize(w, h);
                svg.setDefaultFont((int) ZF.ZC.FontSize, Global.getParameter("font.large", false), Global.getParameter("font.bold", false));
                ZF.ZC.dopaint(svg);
                svg.close();
                out.close();
            } catch (Exception e) {
            }
        }
    }

    public void saveeps() {
        int Scale = 1;
        int w = ZF.ZC.getSize().width * Scale;
        int h = ZF.ZC.getSize().height * Scale;
        ZF.ZC.PointSize = Global.getParameter("minpointsize", 3) * Scale;
        ZF.ZC.FontSize = Global.getParameter("minfontsize", 12) * Scale;

        ZF.ZC.IW = w;
        ZF.ZC.IH = h;
        ZF.ZC.recompute();
        FileSystemView vueSysteme = FileSystemView.getFileSystemView();
        File def = vueSysteme.getHomeDirectory();
        File desk = vueSysteme.getChild(def, "Desktop");
        File choice = (desk == null) ? def : desk;
        JFileChooser jfc = new javax.swing.JFileChooser(choice);
        jfc.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jfc.setApproveButtonText("Export to EPS");
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(new ImageFilter());

        int rep = jfc.showSaveDialog(null);
        if (rep == JFileChooser.APPROVE_OPTION) {
            File outputfile = jfc.getSelectedFile();
            String filename = outputfile.getAbsolutePath();
            String ext = (outputfile.getAbsolutePath().endsWith(".eps")) ? "" : ".eps";
            OutputStream o = null;
            try {
                o = new FileOutputStream(filename + ext);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

            try {
                MyGraphicsEPS eps = new MyGraphicsEPS(o, w, h);
                eps.setSize(w, h);
                eps.setLineWidth(Global.getParameter("minlinesize", 1));
                eps.setDefaultFont((int) ZF.ZC.FontSize, Global.getParameter("font.large", false), Global.getParameter("font.bold", false));


                ZF.ZC.dopaint(eps);
                eps.close();
                o.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void savepng(boolean issave) {
        if (issave) {
            rene.dialogs.ExportPictureDlg dlg = new rene.dialogs.ExportPictureDlg(this);
            dlg.setPictureWidth(ZF.ZC.getSize().width);
            dlg.setPictureHeight(ZF.ZC.getSize().height);
            dlg.setPercentScale(100);
            if (dlg.select()) {
                ZF.ZC.startWaiting();
                double Scale = ((double) dlg.getPercentScale()) / 100.0;
                int w = (int) (((double) ZF.ZC.getSize().width) * Scale);
                int h = (int) (((double) ZF.ZC.getSize().height) * Scale);
                ZF.ZC.PointSize = (int) (((double) Global.getParameter("minpointsize", 3)) * Scale);
                ZF.ZC.FontSize = (int) (((double) Global.getParameter("minfontsize", 12)) * Scale);
                ZF.ZC.IW = w;
                ZF.ZC.IH = h;
                Image I = createImage(w, h);
                rene.zirkel.graphics.MyGraphics13 IG = new rene.zirkel.graphics.MyGraphics13(I.getGraphics(), Scale, ZF.ZC, null);
                IG.setSize(w, h);
                IG.setDefaultFont((int) ZF.ZC.FontSize,
                        Global.getParameter("font.large", false),
                        Global.getParameter("font.bold", false));
                ZF.ZC.recompute();
                IG.clearRect(0, 0, w, h, Color.white);
                ZF.ZC.dopaint(IG);
                ZF.ZC.endWaiting();

                int dpi = 300;
                try {

                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dlg.getFileName()));
                    PngEncoder png = new PngEncoder(I, PngEncoder.NO_ALPHA, 0, 9);
                    png.setDPI(dpi);
                    out.write(png.pngEncode());
                    out.close();
                } catch (Exception e) {
                    //            warning(e.toString());
                    }
            //}
            }
        } else {
            ZF.ZC.startWaiting();
            int Scale = 3;
            int w = ZF.ZC.getSize().width * Scale;
            int h = ZF.ZC.getSize().height * Scale;
            ZF.ZC.PointSize = Global.getParameter("minpointsize", 3) * Scale;
            ZF.ZC.FontSize = Global.getParameter("minfontsize", 12) * Scale;
            ZF.ZC.IW = w;
            ZF.ZC.IH = h;
            Image I = createImage(w, h);
            rene.zirkel.graphics.MyGraphics13 IG = new rene.zirkel.graphics.MyGraphics13(I.getGraphics(), Scale, ZF.ZC, null);
            IG.setSize(w, h);
            IG.setDefaultFont((int) ZF.ZC.FontSize,
                    Global.getParameter("font.large", false),
                    Global.getParameter("font.bold", false));
            ZF.ZC.recompute();
            IG.clearRect(0, 0, w, h, Color.white);
            ZF.ZC.dopaint(IG);
            ZF.ZC.endWaiting();

            try {
                Clipboard clipboard = getToolkit().getSystemClipboard();
                ImageSelection is = new ImageSelection(I);
                clipboard.setContents(is, null);
            } catch (Exception e) {
                Scale = 1;
                I = null;
                repaint();
                JOptionPane.showMessageDialog(null, "Sorry : error occured while copying...");
            }


        }

    }

    public void setLanguage(String lang, String country) {
//        // Determine which fonts support Chinese here ...
//        Vector chinesefonts = new Vector();
//	Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//	int fontcount = 0;
//	String chinesesample = "\u4e00";
//	for (int j = 0; j < allfonts.length; j++) {
//	    if (allfonts[j].canDisplayUpTo(chinesesample) == -1) {
//	        chinesefonts.add(allfonts[j].getFontName());
//	    }
//  	    fontcount++;
//	};
//        if (chinesefonts.size()>0){
//            javax.swing.JOptionPane.showMessageDialog(null, String.valueOf(chinesefonts.size()));
//            javax.swing.JOptionPane.showMessageDialog(null, chinesefonts.get(0));
//        }
        try {
            JGlobals.changeGlobalFont(lang);
            Global.setParameter("language", lang);
            Global.setParameter("country", country);
            Locale myloc = new Locale(lang, country);
            Locale.setDefault(myloc);
            Strs = ResourceBundle.getBundle("eric/docs/JZirkelProperties");
            Global.initBundle("rene/zirkel/docs/ZirkelProperties", true);
            JGlobals.CreatePopertiesBar();
            this.Content.remove(GeneralMenuBar);
            GeneralMenuBar = new JGeneralMenuBar(ZF, this);
            this.Content.add(GeneralMenuBar, 1);

            this.ZContent.refreshlanguage();

            JPM.dispose();
            JPM = null;
            JPM = new JPaletteManager(ZF, this, IconSize());
            GeneralMenuBar.InitObjectsMenu();
            this.Content.revalidate();
            this.Content.repaint();
            this.ResizeAll();
//            this.pack();
            ZContent.macros.myJML.initMacrosTree();
            JPM.MainPalette.setVisible(true);
        } catch (Exception ex) {
            // There were no unicode font for this language :
            int rep = JOptionPane.showConfirmDialog(null, "Sorry, but the requested font is not installed" +
                    " for this language.\n" +
                    "This language will not be selected.\n\n" +
                    "Do you want to download the necessary font ?", "Font not installed", JOptionPane.YES_NO_OPTION);
            if (rep == JOptionPane.OK_OPTION) {
                JBrowserLauncher.openURL(JGlobals.FontURL);
            }
        }
    }

    public void mouseDragged(MouseEvent me) {
        location = getLocation(location);
        int x = location.x - pressed.getX() + me.getX();
        int y = location.y - pressed.getY() + me.getY();

        setLocation(x, y);
        Toolkit.getDefaultToolkit().sync();
        JPM.MainPalette.FollowWindow();
//        if (!System.getProperty("os.name").equals("Linux")) {
//            JPM.MainPalette.FollowWindow();
//        }
    }

    public void mousePressed(MouseEvent me) {
        pressed = me;
//        toFront();
//        JMacrosTools.setCurrentJZF(this);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void windowGainedFocus(WindowEvent arg0) {
        JMacrosTools.activateFrontMostWindow();
    }

    public void windowLostFocus(WindowEvent arg0) {
        JMacrosTools.activateFrontMostWindow();
    }

    public void update(Graphics g) {
        paint(g);
    }
}
