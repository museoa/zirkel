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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuBar;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import rene.dialogs.Question;
import rene.gui.Global;
import rene.util.FileName;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelFrame;

public class JGeneralMenuBar extends JPanel {

    rene.zirkel.ZirkelFrame ZF;
    JZirkelFrame JZF;
    myJMenuBar menubar=new myJMenuBar();
    public myJMenu MacrosMenu,  ObjectsMenu,  WindowsMenu;
    JButton historybutton;
    myJMenuItem hiddenitem, griditem, restrictpaletteitem, editpaletteitem, macrositem, historyitem, helpitem, propertiesitem, smallitem, mediumitem, largeitem, definejobitem;

    public void paintComponent(java.awt.Graphics g) {
//        super.paintComponent(g);
        java.awt.Dimension d=this.getSize();
        g.drawImage(JZF.JZT.getImage("menubar.gif"), 0, 0, d.width, d.height, this);
        if (JZF.construct) return;
        if (!JZF.equals(JMacrosTools.CurrentJZF)) {
            JZF.JZT.setDisable(g, d);
        }
    }

    public JGeneralMenuBar(rene.zirkel.ZirkelFrame zf, JZirkelFrame jzf) {
        ZF=zf;
        JZF=jzf;
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
        MacrosMenu=new myJMenu(Loc("macros"));
        ObjectsMenu=new myJMenu(JZF.Strs.getString("palette.construction"));
        WindowsMenu=new myJMenu(Loc("windows"));
        init();
    }
    
    public void paintImmediately(){
        paintImmediately(0, 0, getWidth(), getHeight());
    }

    class myJMenuBar extends JMenuBar {
        
        public void paintComponents(Graphics g){
            
        }

        myJMenuBar() {
            super();
            setUI(null);
        }

        void addMenu(JMenu mymen) {
            if (mymen.getItemCount()>0) {
                this.add(mymen);
            }
        }
    }

    class myJMenu extends JMenu {
        
                public void paintComponents(Graphics g){
            
        }

        myJMenu(String menuname) {
            this.setText(menuname);
            this.setFont(new java.awt.Font(JGlobals.GlobalFont, 0, 12));
            this.setForeground(new Color(40, 40, 40));
            this.setOpaque(false);
        }

        myJMenu(String menuname, boolean isSubmenu) {
            this(menuname);
            if (isSubmenu) {
                setOpaque(true);
                setIcon(new myImageIcon(getClass().getResource("/eric/icons/palette/null.png"), null));
            }
        }
        // Constructor for the Objects submenus :
        myJMenu(String menuname, int icnw) {
            this(menuname);
            setOpaque(true);
            myImageIcon myicn=new myImageIcon(getClass().getResource("/eric/icons/palette/null.png"), null);
            myicn.setIcnMargin(0);
            myicn.setIconWidth(icnw);
            setIcon(myicn);
        }

        boolean ok(String mnu, String icn) {
            if (!JZF.restrictedSession) {
                return true;
            }
            if ((!icn.equals(""))&&(JZF.JPM.isRestrictedIcon(icn))) {
                return true;
            }
            if (mnu.equals("file.saveas")) {
                return true;
            }
            if (mnu.equals("file.quit")) {
                return true;
            }
            if (mnu.equals("display.large")) {
                return true;
            }
            if (mnu.equals("display.medium")) {
                return true;
            }
            if (mnu.equals("display.small")) {
                return true;
            }
            if (mnu.equals("help.about")) {
                return true;
            }
            if (mnu.equals("help.info")) {
                return true;
            }
            if (mnu.equals("help.url1")) {
                return true;
            }
            if (mnu.equals("help.url2")) {
                return true;
            }
            return false;
        }

        void addSep() {
//            if (this.getItemCount()>0) this.addSeparator();

            this.add(new mySeparator());
        }

        void addI(String mnu, String icn, int a1, int a2, myJMenuItem item) {
            if (ok(mnu, icn)) {
                String mnuName=Loc(mnu);
                item.setNames(mnuName, icn);
                if (a1!=0) {
                    item.setAccelerator(KeyStroke.getKeyStroke(a1, a2));
                }
                this.add(item);
            }
        }

        void addI(String mnu, String icn, int a1, int a2, boolean sel, myJMenuItem item) {
            if (ok(mnu, icn)) {
                item.setNames(Loc(mnu), icn);
                item.setSelected(sel);
                if (a1!=0) {
                    item.setAccelerator(KeyStroke.getKeyStroke(a1, a2));
                }
                this.add(item);
            }
        }
        // only for objects submenus :
        void addI(String icn, myJMenuItem item) {
            String mnuName=JZF.ToolTip(icn);
            item.setNames(mnuName, icn);
            item.setText("<html>"+item.getText().replaceAll("\\+", "<br>")+"</html>");
            if (!(item.myimage==null)) {
                item.myimage.setIconWidth(28);
                item.myimage.setIconHeight(28);
            }
            this.add(item);
        }
        // only for language submenu :
        void addI(String lang, String country, myJMenuItem item) {
            String suffix=(country.equals(""))?lang:lang+"_"+country;
            String icn="lg_"+suffix;
            boolean good=JGlobals.isLanguage(lang, country);
            if ((good)&&(!(icn.equals("")))) {
                this.setIcon(new ImageIcon(getClass().getResource("/eric/icons/palette/"+icn+".png")));
            }
            addI("language."+suffix, icn, 0, 0, item);
            item.setEnabled(!good);
        }

        class mySeparator extends JPanel {

            public void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);

                java.awt.Dimension d=this.getSize();
                g.drawImage(JZF.JZT.getImage("sep.png"), 2, 0, d.width-4, 12, this);
            }

            mySeparator() {
                this.setOpaque(false);
            }
        }
    }
    
    class mySimpleJMenuItem extends JMenuItem {
        int ID=0;
        mySimpleJMenuItem(String name,int i) {
            super(name);
            ID=i;
            this.setOpaque(true);
            this.setFont(new java.awt.Font(JGlobals.GlobalFont, 0, JGlobals.MenuTextSize));
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    action();
                }
            });
        }

        void action() {
        }
    }

    class myJMenuItem extends JMenuItem {

        String ICname;
        boolean selected=false;
        myImageIcon myimage=null;

        myJMenuItem() {
            this.setOpaque(true);
            this.setFont(new java.awt.Font(JGlobals.GlobalFont, 0, JGlobals.MenuTextSize));
            this.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    action();
                }
            });
            this.setIcon(new myImageIcon(getClass().getResource("/eric/icons/palette/null.png"), null));
//            this.setIconTextGap(0);
        }

        myJMenuItem(String itemname, String iconname) {
            this();
            setNames(itemname, iconname);
        }

        public void setSelected(boolean sel) {
            selected=sel;
            int fontstyle=(selected)?1:0;
            this.setFont(new java.awt.Font(JGlobals.GlobalFont, fontstyle, JGlobals.MenuTextSize));
        }

        public boolean isSelected() {
            return selected;
        }

        void setNames(String itemname, String iconname) {
            ICname=iconname;
            setText(itemname);
            setIcn(iconname);
//            setPreferredSize(new Dimension(getPreferredSize().width+50,22));
        }

        void setIcn(String iconname) {
            if (iconname.equals("")) {
                iconname="null";
            }
            URL myurl=getClass().getResource("/eric/icons/palette/"+iconname+".png");
            if (myurl==null) {
                myurl=getClass().getResource("/eric/icons/palette/"+iconname+".gif");
            }
            myimage=new myImageIcon(myurl, this);
            setIcon(myimage);
        }

        void action() {
            JZF.JPM.ClicOn(ICname);
        }
    }

    class myImageIcon extends ImageIcon {

        int IcnHeight=22;
        int IcnWidth=24;
        int IcnMargin=12;
        myJMenuItem JM;

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2=(Graphics2D) g;




            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_PURE);

            if ((!(JM==null))&&(JM.isSelected())) {
                ImageIcon mysel=new ImageIcon(getClass().getResource("/eric/icons/palette/selmark.png"));
                g2.drawImage(mysel.getImage(), 3, 0, 8, IcnWidth, null);
            }
            g2.drawImage(getImage(), IcnMargin, 0, IcnWidth, IcnWidth, null);
        }
        ;

        public void setIcnMargin(int i) {
            IcnMargin=i;
        }
        ;

        public void setIconHeight(int i) {
            IcnHeight=i;
        }
        ;

        public void setIconWidth(int i) {
            IcnWidth=i;
        }
        ;

        public int getIconHeight() {
            return IcnHeight;
        }
        ;

        public int getIconWidth() {
            return IcnWidth;
        }
        ;

        myImageIcon(URL myurl, myJMenuItem jm) {
            super(myurl);
            JM=jm;
        }
    }

    private String Loc(String s) {
        try {
            String loc=JZF.Strs.getString("menu."+s);
            return loc;
        }catch (Exception e){
            return s;
        }
    }

    public void InitMacrosMenu() {
        MacrosMenu.removeAll();
        MacrosMenu.addI("special.newmacro", "newmacro", 0, 0, new myJMenuItem());
        MacrosMenu.addI("special.loadmacros", "loadmacros", 0, 0, new myJMenuItem());
        MacrosMenu.addSep();
    }

    public void InitWindowsMenu() {
        WindowsMenu.removeAll();
//        if (JMacrosTools.AllJZFs.size()<2) return;
        for (int i=0; i<JMacrosTools.AllJZFs.size(); i++) {
            JZirkelFrame jzf=(JZirkelFrame) JMacrosTools.AllJZFs.get(i);
            
            
            String s1="["+(i+1)+"] ";
            String s2=(jzf.ZF.Filename.equals(""))?Zirkel.name("program.name"):JMacrosTools.shortFileName(jzf.ZF.Filename);

                WindowsMenu.add(new mySimpleJMenuItem(s1+s2,i) {
                    void action() {
                        JZirkelFrame jzf=(JZirkelFrame) JMacrosTools.AllJZFs.get(ID);
                        jzf.requestFocus();
                    }
                });  
        }
    }

    public void InitObjectsMenu() {
        ObjectsMenu.removeAll();
        myJMenu m1=new myJMenu(Loc("objects.points"), 0);
        m1.addI("point", new myJMenuItem());
        m1.addI("intersection", new myJMenuItem());
        m1.addI("midpoint", new myJMenuItem());
        m1.addI("bi_syma", new myJMenuItem());
        m1.addI("bi_symc", new myJMenuItem());
        m1.addI("bi_trans", new myJMenuItem());
        ObjectsMenu.add(m1);
        myJMenu m2=new myJMenu(Loc("objects.lines"), 0);
        m2.addI("line", new myJMenuItem());
        m2.addI("ray", new myJMenuItem());
        m2.addI("parallel", new myJMenuItem());
        m2.addI("plumb", new myJMenuItem());
        m2.addI("bi_med", new myJMenuItem());
        m2.addI("bi_biss", new myJMenuItem());
        ObjectsMenu.add(m2);
        myJMenu m3=new myJMenu(Loc("objects.segments"), 0);
        m3.addI("segment", new myJMenuItem());
        m3.addI("fixedsegment", new myJMenuItem());
        m3.addI("vector", new myJMenuItem());
        m3.addI("area", new myJMenuItem());
        ObjectsMenu.add(m3);
        myJMenu m4=new myJMenu(Loc("objects.angles"), 0);
        m4.addI("angle", new myJMenuItem());
        m4.addI("fixedangle", new myJMenuItem());
        ObjectsMenu.add(m4);
        myJMenu m5=new myJMenu(Loc("objects.circles"), 0);
        m5.addI("circle", new myJMenuItem());
        m5.addI("fixedcircle", new myJMenuItem());
        m5.addI("circle3", new myJMenuItem());
        m5.addI("bi_circ", new myJMenuItem());
        m5.addI("bi_arc", new myJMenuItem());
        m5.addI("quadric", new myJMenuItem());
        ObjectsMenu.add(m5);
        myJMenu m7=new myJMenu(Loc("objects.functions"), 0);
        m7.addI("text", new myJMenuItem());
        m7.addI("expression", new myJMenuItem());
        m7.addI("bi_function_u", new myJMenuItem());
        m7.addI("function", new myJMenuItem());
        m7.addI("equationxy", new myJMenuItem());
        ObjectsMenu.add(m7);
        myJMenu m6=new myJMenu(Loc("objects.tracks"), 0);
        m6.addI("objecttracker", new myJMenuItem());
        m6.addI("tracker", new myJMenuItem());
        m6.addI("locus", new myJMenuItem());
        ObjectsMenu.add(m6);

    }

    private void fixsize(JComponent cp, int w, int h) {
        Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    private JPanel margin(int w) {
        JPanel mypan=new JPanel();
        fixsize(mypan, w, 1);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }

    private JPanel margintop(int h) {
        JPanel mypan=new JPanel();
        fixsize(mypan, 1, h);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }

    private void showrestrictedmessage() {
        if (Global.getParameter("showrestrictmessage", true)) {
            JPanel mypan=new JPanel();
            mypan.setLayout(new BoxLayout(mypan, BoxLayout.Y_AXIS));
            JLabel mylabel=new JLabel(JZF.Strs.getString("menu.display.restrictmessage"));
            mylabel.setFont(new Font("System", 0, 12));
            JCheckBox myjcb=new JCheckBox(JZF.Strs.getString("menu.display.restrictmessage.dontdisplay"));
            mypan.add(mylabel);
            mypan.add(margintop(10));
            mypan.add(myjcb);
            JOptionPane.showMessageDialog(null, mypan, "", JOptionPane.PLAIN_MESSAGE, null);
            Global.setParameter("showrestrictmessage", !myjcb.isSelected());
        }
    }

    public void init() {
        myJMenu menu;
        JMenuItem item;
        menubar.removeAll();
        this.removeAll();
        int ctrlkey=(System.getProperty("mrj.version")!=null)?InputEvent.META_DOWN_MASK:InputEvent.CTRL_DOWN_MASK;

        menubar.setOpaque(false);
        menubar.setBorder(BorderFactory.createEmptyBorder());
        menubar.setAlignmentY(0.5F);


        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        MenuBar ZFMenuBar=ZF.getMenuBar();
        /****************************
         *FILE MENU
         ****************************/
        menu=new myJMenu(Loc("file"));

        menu.addI("file.new", "new", KeyEvent.VK_N, ctrlkey, new myJMenuItem());
        menu.addI("file.new3D", "", 0, 0, new  

             myJMenuItem() {
                
            
        
        
        void action(){
                JMacrosTools.New3DWindow();
            }
        });
        
        menu.addSep();
        menu.addI("file.load", "load", KeyEvent.VK_O, ctrlkey, new myJMenuItem());
        menu.addI("file.save", "save", KeyEvent.VK_S, ctrlkey, new myJMenuItem());
        menu.addI("file.saveas", "", 0, 0, new  

             myJMenuItem() {
                
            
        
        void action(){
                JZF.savefileas();
            }
        });
        menu.addI("file.close", "", KeyEvent.VK_W, ctrlkey, new  

             myJMenuItem() {
                
            
        
        
        void action(){
                JMacrosTools.disposeCurrentJZF();
            }
        });
        menu.addSep();
        menu.addI("file.exportpng", "exportpng", 0, 0, new myJMenuItem());
        menu.addI("file.exporteps", "exporteps", 0, 0, new myJMenuItem());
        menu.addI("file.exportsvg", "", 0, 0, new  

             myJMenuItem() {
                
            
        
        
        void action(){
                JZF.saveSVG();
            }
        });
        menu.addSep();
        menu.addI("file.quit", "", KeyEvent.VK_Q, ctrlkey, new  

             myJMenuItem() {
                
            
        
        
         void action(){
//                if (ZF.close()) ZF.doclose();
                JMacrosTools.disposeAllJZFs();
            }
        });
        
        menubar.addMenu(menu);
        /****************************
         *EDIT MENU
         ****************************/
        menu=new myJMenu(Loc("edit"));
        menu.addI("edit.copy", "copy", KeyEvent.VK_C, ctrlkey, new myJMenuItem());
        menu.addSep();
        menu.addI("edit.move", "move", 0, 0, new myJMenuItem());
        menu.addI("edit.rename", "rename", 0, 0, new myJMenuItem());
        menu.addI("edit.edit", "edit", 0, 0, new myJMenuItem());
        menu.addI("edit.zoom", "zoom", 0, 0, new myJMenuItem());
        menu.addI("edit.hide", "hide", 0, 0, new myJMenuItem());
        menu.addI("edit.delete", "delete", 0, 0, new myJMenuItem());
        menu.addSep();
        menu.addI("edit.deactivatealltracks", "", 0, 0, new  

             myJMenuItem() {
                
                
                
            
        
        void action(){
                ZF.ZC.UniversalTrack.clearTrackImage();
                ZF.ZC.UniversalTrack.clearTrackObjects();
                ZF.ZC.repaint();
            }
        });
        menu.addI("edit.deletealltracks", "", KeyEvent.VK_T, ctrlkey, new  

             myJMenuItem() {
                
                
            
        
        
        void action(){
                ZF.ZC.UniversalTrack.clearTrackImage();
                ZF.ZC.repaint();
            }
        });
        menu.addSep();
        menu.addI("edit.deleteall", "", KeyEvent.VK_DELETE, 0, new  

             myJMenuItem() {
                  
                       void action(){
                if (ZF.ZC.changed()) {
                    Question q=new Question(ZF,Zirkel.name("savequestion.qsave"),
                            Zirkel.name("savequestion.title"), true);
                    q.center(ZF);
                    q.setVisible(true);
                    if (q.isAborted()) {
                        return;
                    }
                    if (q.yes()) {
                        JZF.savefile();
                    }
                }
                ZF.clear(false);
                ZF.Filename="";
                ZF.setTitle(Zirkel.name("program.name"));
                JMacrosTools.setWindowTitle(JZF);
                
            }
        });
        menubar.addMenu(menu);


        /****************************
         *OBJECTS MENU
         ****************************/
        if (!JZF.restrictedSession) {
            menubar.add(ObjectsMenu);
        /****************************
         *DISPLAY MENU
         ****************************/
        }
        menu=new myJMenu(Loc("display"));
        hiddenitem=new myJMenuItem();
        menu.addI("display.hidden", "hidden", 0, 0, false, hiddenitem);
        griditem=new myJMenuItem();
        menu.addI("display.grid", "grid", 0, 0, false, griditem);
        menu.addSep();
        menu.addI("display.smartboard", "", 0, 0, Global.getParameter("smartboard", false), new  

             myJMenuItem() {
                void
                action(){
                setSelected(!isSelected());
                Global.setParameter("smartboard", isSelected());
                if (isSelected()) {
                    JZF.JPM.MainPalette.ToolTip.HideTip();
                }
            }
        });
        menu.addSep();
        macrositem=new myJMenuItem();
        menu.addI("display.manage_macros", "manage_macros", 0, 0, false, macrositem);
        historyitem=new myJMenuItem();
        menu.addI("display.history_panel", "history_panel", 0, 0, false, historyitem);
        helpitem=new myJMenuItem();
        menu.addI("display.help_panel", "help_panel", 0, 0, false, helpitem);
        menu.addSep();
        propertiesitem=new  

             myJMenuItem() {
                void
            
        
        action(){
                JGlobals.JPB.showme(!JGlobals.JPB.isVisible());
                
            }
        };
        menu.addI("display.properties_panel", "properties_panel", KeyEvent.VK_P, ctrlkey, false, propertiesitem);
        menu.addSep();
        largeitem=new  

             myJMenuItem() {
                void
                 action() {
                    setSelected(
                    !isSelected());
                if (isSelected()){
                    JZF.JPM.dispose();
                    JZF.JPM=null;
                    JZF.JPM=new JPaletteManager(ZF, JZF, 32);
                    JZF.JPM.MainPalette.setVisible(true);
                    Global.setParameter("options.iconsize", 0);
                    smallitem.setSelected(false);
                    mediumitem.setSelected(false);
                } else {
                    largeitem.setSelected(true);
                }
            }
        };
        menu.addI("display.large", "", 0, 0, false, largeitem);

        mediumitem=new  

             myJMenuItem() {
                void
                 action() {
                    setSelected(
                    !isSelected());
                if (isSelected()){
                    JZF.JPM.dispose();
                    JZF.JPM=null;
                    JZF.JPM=new JPaletteManager(ZF, JZF, 28);
                    JZF.JPM.MainPalette.setVisible(true);
                    Global.setParameter("options.iconsize", 1);
                    smallitem.setSelected(false);
                    largeitem.setSelected(false);
                } else {
                    mediumitem.setSelected(true);
                }
            }
        };
        menu.addI("display.medium", "", 0, 0, false, mediumitem);

        smallitem=new  

             myJMenuItem() {
                void
                 action() {
                    setSelected(
                    !isSelected());
                if (isSelected()){
                    JZF.JPM.dispose();
                    JZF.JPM=null;
                    JZF.JPM=new JPaletteManager(ZF, JZF, 24);
                    JZF.JPM.MainPalette.setVisible(true);
                    Global.setParameter("options.iconsize", 2);
                    largeitem.setSelected(false);
                    mediumitem.setSelected(false);
                } else {
                    smallitem.setSelected(true);
                }
            }
        };
        menu.addI("display.small", "", 0, 0, false, smallitem);

        switch (Global.getParameter("options.iconsize", 1)) {
            case 0:
                largeitem.setSelected(true);
                break;
            case 1:
                mediumitem.setSelected(true);
                break;
            case 2:
                smallitem.setSelected(true);
                break;
        }
        menubar.addMenu(menu);



        /****************************
         *MACROS MENU : Initialised by
         ****************************/
        menubar.add(MacrosMenu);
        /****************************
         *SPECIAL MENU
         ****************************/
        menu=new myJMenu(Loc("special"));
        restrictpaletteitem=new  

             myJMenuItem() {
                void
                action()
                  {
                    setSelected(
                
            
        
        !isSelected());
                JZF.setRestrictedView(isSelected());
                if (isSelected()){
                    showrestrictedmessage();
                }
            }
        };
        menu.addI("display.restrictedpalette", "", 0, 0, false, restrictpaletteitem);


        editpaletteitem=new  

             myJMenuItem() {
                void
                action()
                
                {
                setSelected(!isSelected());
                JZF.EditRestricted=isSelected();
                JZF.JPM.dispose();
                JZF.JPM=null;
                JZF.JPM=new JPaletteManager(ZF, JZF, JZF.IconSize());
                JZF.JPM.MainPalette.setVisible(true);
            }
        };
        menu.addI("display.restrictedpaletteedit", "", KeyEvent.VK_G, ctrlkey, false, editpaletteitem);
        editpaletteitem.setEnabled(false);

        menu.addSep();

        myJMenu submenu=new myJMenu(Loc("language"), true);

        submenu.addI("zh", "TW", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("zh", "TW");
            }
        });
        submenu.addI("pt", "BR", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("pt", "BR");
            }
        });
        submenu.addI("de", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("de", "");
            }
        });
        submenu.addI("en", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("en", "");
            }
        });
        submenu.addI("es", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("es", "");
            }
        });
        submenu.addI("fr", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("fr", "");
            }
        });
        submenu.addI("gl", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("gl", "");
            }
        });
        submenu.addI("it", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("it", "");
            }
        });
        submenu.addI("nl", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("nl", "");
            }
        });
        submenu.addI("no", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("no", "");
            }
        });
        submenu.addI("pl", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("pl", "");
            }
        });
        submenu.addI("pt", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("pt", "");
            }
        });
        submenu.addI("sl", "", new  

             myJMenuItem() {
                void action(){JZF.setLanguage("sl", "");
            }
        });


        menu.add(submenu);

        myJMenu submenu2=new myJMenu(Loc("special.theme"), true);

        submenu2.addI("special.theme.gray", "", 0, 0, new  

             myJMenuItem() {
                void action(){JZF.JZT.ChangeTheme("gray");
            }
        });
        submenu2.addI("special.theme.brushed", "", 0, 0, new  

             myJMenuItem() {
                void action(){JZF.JZT.ChangeTheme("brushed");
            }
        });

        menu.add(submenu2);

        menu.addSep();

        definejobitem=new  

             myJMenuItem() {
                void
                 action() {
                    setSelected(
                    
                    
                    
                    !isSelected());
                if (isSelected()){
                    JZF.JPM.deselectgeomgroup();
                    ZF.CurrentTool=ZirkelFrame.NDefineJob;
                    ZF.ZC.setTool(ZirkelFrame.ObjectConstructors[ZirkelFrame.NDefineJob]);
                    ZirkelFrame.ObjectConstructors[ZirkelFrame.NDefineJob].resetFirstTime(ZF.ZC);
                    ZF.testjob(false);
                } else {
                    ZF.CurrentTool=0;
                    JZF.JPM.ClicOn("point");
                }
                ;

            }
        };
        menu.addI("special.definejob", "", 0, 0, false, definejobitem);

        menu.addI("special.loadjob", "", KeyEvent.VK_J, ctrlkey, new  

             myJMenuItem() {
                void action(){
                ZF.setinfo("assignment");
                ZF.loadJob();
            }
        });
        menu.addI("special.jobcomment", "", KeyEvent.VK_F8, 0, new  

             myJMenuItem() {
                void action(){
                ZF.setinfo("comment");
                ZF.showjobcomment();
            }
        });
        menu.addI("special.testjob", "", KeyEvent.VK_F1, ctrlkey, false, new  

             myJMenuItem() {
                void
                action()
                 {
                setSelected(!isSelected());
                ZF.testjob(isSelected());
                if (isSelected() && !ZF.ZC.getConstruction().getComment().equals("")) {
                    ZF.showcomment();
                }
                ZF.setinfo("assignments");
            }
        });
        menu.addI("special.savejob", "", KeyEvent.VK_K, ctrlkey, new  

             myJMenuItem() {
                void action(){
                ZF.setinfo("assignment");
                ZF.saveJob();
            }
        });
        menu.addSep();

        menu.addI("special.savezirset", "", 0, 0, new  

             myJMenuItem() {
                
            
        
        
        void action(){
                JZF.SaveSlideShow();
            }
        });
        
        menu.addSep();
        
        menu.addI("special.export", "", KeyEvent.VK_E, ctrlkey, new  

             myJMenuItem() {
                void action(){
                ZF.setinfo("htmlexport");
                if (ZF.exportHTML()) {
                    JZF.SaveJarAndLaunchBrowser(FileName.path(ZF.Filename), FileName.purefilename(ZF.Filename)+".html");
                }
            }
        });

        menu.addI("special.exporttemplate", "", 0, 0, new  

             myJMenuItem() {
                void action(){
                ZF.setinfo("htmlexporttemplate");
                ZF.exportTemplateHTML();
            }
        });


        menu.addSep();
        menu.addI("special.options", "", 0, 0, new  

             myJMenuItem() {
                
            
        
        
         void action(){
                JGlobalPreferences.ShowPreferencesDialog();
            }
        });
        
        
        
        menubar.addMenu(menu);
         /****************************
         *WINDOWS MENU
         ****************************/
        menubar.add(WindowsMenu);
        /****************************
         *HELP MENU
         ****************************/
        
        menu=new myJMenu(Loc("help"));
        menu.addI("help.about", "", 0, 0, new  

             myJMenuItem() {
                  
            
        
        void action(){
                JAboutDialog JLW=new JAboutDialog(JZF);
            }
        });
        menu.addI("help.licence", "", 0, 0, new  

             myJMenuItem() {
                  
            
        
        void action(){
                JLicence JL=new JLicence(JZF);
            }
        });
        menu.addI("help.info", "help_panel", 0, 0, new  

             myJMenuItem() {
                void action(){
                JZF.JPM.setSelected("help_panel", true);
            }
        });
        menu.addSep();
        menu.addI("help.url0", "", 0, 0, new  

             myJMenuItem() {
                void action(){
                JBrowserLauncher.openURL("http://db-maths.nuxit.net/CaRMetal/");
            }
        });
        menu.addI("help.url1", "", 0, 0, new  

             myJMenuItem() {
                void action(){
                JBrowserLauncher.openURL("http://db-maths.nuxit.net/CARzine/");
            }
        });
        menu.addI("help.url2", "", 0, 0, new  

             myJMenuItem() {
                void action(){
                JBrowserLauncher.openURL("http://mathsrv.ku-eichstaett.de/MGF/homes/grothmann/java/zirkel/doc_en/");
            }
        });
        menu.addSep();
        menu.addI("help.url3", "", 0, 0, new  

             myJMenuItem() {
                void action(){
                JBrowserLauncher.openURL("http://db-maths.nuxit.net/CaRMetal/index_translate.html");
            }
        });


        menubar.addMenu(menu);
        this.add(menubar);
        this.validate();
        this.repaint();
    }
}
