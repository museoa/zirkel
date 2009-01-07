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
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.plaf.SeparatorUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import rene.dialogs.Warning;
import rene.gui.Global;
import rene.util.FileName;
import rene.util.xml.XmlTag;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroItem;

/**
 *
 * @author erichake
 */
public class JNodePopup extends JPopupMenu {

    CTree macrostree;
    JZirkelFrame JZF;
    rene.zirkel.ZirkelFrame ZF;
    JDefaultMutableTreeNode SelectedNode;
    TreePath[] SelectedPath;
    JMenuItem runitem, renitem, delitem, tolibitem, delfromlibitem, tofileitem, totempitem, saveitem, updtitem, propitem, dupitem;
    JMacrosInspector MInspector;

    /** Creates a new instance of JMacrosTreeNodeContextualPopup */
    public JNodePopup(CTree mytree) {
        macrostree = mytree;
        JZF = macrostree.JML.JZF;
        ZF = JZF.ZF;
        MInspector = new JMacrosInspector(ZF, JZF);


        dupitem = new JMenuItem(JZF.Strs.getString("macros.popup.duplicate"));
        dupitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                duplicatenodes();
            }
        });


        propitem = new JMenuItem(JZF.Strs.getString("macros.popup.properties"));
        propitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                showproperties();
            }
        });

        runitem = new JMenuItem(JZF.Strs.getString("macros.popup.run"));
        runitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                runmacro();
            }
        });

        renitem = new JMenuItem(JZF.Strs.getString("macros.popup.rename"));
        renitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                renamenode();
            }
        });

        delitem = new JMenuItem(JZF.Strs.getString("macros.popup.delete"));
        delitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                deletenodes();
            }
        });



        tolibitem = new JMenuItem(JZF.Strs.getString("macros.popup.addtolibrary"));
        tolibitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                changemacroselectiontype(0);
            }
        });

        delfromlibitem = new JMenuItem(JZF.Strs.getString("macros.popup.removefromlibrary"));
        delfromlibitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                removefromlibrary();
            }
        });

        tofileitem = new JMenuItem(JZF.Strs.getString("macros.popup.publish"));
        tofileitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                changemacroselectiontype(2);
            }
        });

        totempitem = new JMenuItem(JZF.Strs.getString("macros.popup.notpublish"));
        totempitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                changemacroselectiontype(3);
            }
        });

        saveitem = new JMenuItem(JZF.Strs.getString("macros.popup.saveas"));
        saveitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                savemacros();
            }
        });







    }

    public void handleMouseClick(MouseEvent e) {
        TreePath path = macrostree.getPathForLocation(e.getX(), e.getY());
        if (path != null) {
            SelectedNode = (JDefaultMutableTreeNode) path.getLastPathComponent();
            SelectedPath = macrostree.getSelectionPaths();
            if ((SelectedPath.length == 1) && (SelectedNode.isLeaf())) {
                runmacro();
            }
        }
    }

    public void handlePopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            TreePath path = macrostree.getPathForLocation(e.getX(), e.getY());

            if (path != null) {
                macrostree.addSelectionPath(path);
                SelectedNode = (JDefaultMutableTreeNode) path.getLastPathComponent();
                SelectedPath = macrostree.getSelectionPaths();
//                initSelectedPath();

                this.removeAll();

                if (SelectedPath.length > 1) {

                    this.add(delitem);
                    this.add(createSeparator());
                    this.add(tofileitem);
                    this.add(totempitem);
                    if (!JZF.restrictedSession) {
                        this.add(createSeparator());
                    }
                    if (!JZF.restrictedSession) {
                        this.add(tolibitem);
                    }
                    if (!JZF.restrictedSession) {
                        this.add(delfromlibitem);
                    }
                    if (!JZF.restrictedSession) {
                        this.add(createSeparator());
                    }
                    if (!JZF.restrictedSession) {
                        this.add(saveitem);
                    }
                } else {
                    if (SelectedNode.isLeaf()) {
                        this.add(renitem);
                        if (!JZF.restrictedSession) {
                            this.add(delitem);
                        }
                        if (!JZF.restrictedSession) {
                            this.add(dupitem);
                        }
                        this.add(createSeparator());
                        this.add(tofileitem);
                        this.add(totempitem);
                        this.add(createSeparator());
                        if (!JZF.restrictedSession) {
                            this.add(tolibitem);
                        }
                        if (!JZF.restrictedSession) {
                            this.add(delfromlibitem);
                        }
                        if (!JZF.restrictedSession) {
                            this.add(createSeparator());
                        }
                        if (!JZF.restrictedSession) {
                            this.add(saveitem);
                        }
                        if (!JZF.restrictedSession) {
                            this.add(createSeparator());
                        }
                        this.add(runitem);
                        this.add(createSeparator());
                        this.add(propitem);
                    } else {
                        this.add(renitem);
                        this.add(delitem);
                        this.add(createSeparator());
                        this.add(tofileitem);
                        this.add(totempitem);
                        this.add(createSeparator());
                        if (!JZF.restrictedSession) {
                            this.add(tolibitem);
                        }
                        if (!JZF.restrictedSession) {
                            this.add(delfromlibitem);
                        }
                        if (!JZF.restrictedSession) {
                            this.add(createSeparator());
                        }
                        if (!JZF.restrictedSession) {
                            this.add(saveitem);
                        }

                    }
                }

                this.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private void removefromlibrary() {
        for (int i = 0; i < SelectedPath.length; i++) {
            SelectedNode = (JDefaultMutableTreeNode) SelectedPath[i].getLastPathComponent();
            parseremove(SelectedNode);
        }
        macrostree.repaint();
//        updatelibrary();
    }

    private void parseremove(JDefaultMutableTreeNode node) {
        if (node.isLeaf()) {
            node.removefromlib();
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                parseremove((JDefaultMutableTreeNode) node.getChildAt(i));
            }
        }
    }

    private void changemacroselectiontype(int newtype) {
        for (int i = 0; i < SelectedPath.length; i++) {
            SelectedNode = (JDefaultMutableTreeNode) SelectedPath[i].getLastPathComponent();
            parse(SelectedNode, newtype);
        }
        macrostree.repaint();
//        updatelibrary();
    }

    private void parse(JDefaultMutableTreeNode node, int newtype) {
        if (node.isLeaf()) {
            node.setType(newtype);
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                parse((JDefaultMutableTreeNode) node.getChildAt(i), newtype);
            }
        }
    }

    private boolean ICanSave(String Fname, boolean ask) {
        if (!ask) {
            return true;
        }
        if (!new File(Fname).exists()) {
            return true;
        }
        return (JOptionPane.showConfirmDialog(null, JZF.Strs.getString("filedialog.savemessage1") + Fname + JZF.Strs.getString("filedialog.savemessage2"), "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
    }

    public void dosave(String Filename, boolean restrict, String ext, boolean ask) {
        if ((!Filename.endsWith(".zirz")) && (!Filename.endsWith(".zir"))) {
            Filename += ext;
        }
        if (ICanSave(Filename, ask)) {
            SetMacrosProtectionForSaveProcess(macrostree.JML.MacroTreeTopNode);
            ZF.RestrictIcons.setState(restrict);
            ZF.setinfo("save");
            OutputStream o;
            try {
                ZF.ZC.getConstruction().BackgroundFile = ZF.Background;
                ZF.ZC.getConstruction().ResizeBackground =
                        Global.getParameter("background.usesize", false);
                if (ZF.RestrictIcons.getState()) {
                    String icons = Global.getParameter("restrictedicons", JGlobals.DefaultIcons);
                    String rFileName = JGlobals.RestrictFileName(Filename);

                    if (!JZF.restrictedSession) {
                        OutputStream o2 = new FileOutputStream(rFileName);
                        if (ZirkelFrame.isCompressed(rFileName)) {
                            o2 = new GZIPOutputStream(o2, 10000);
                        }
                        ZF.ZC.save(o2, true, true, false, ZF.ZC.getMacros(), icons);
                        o2.close();

                        o = new FileOutputStream(Filename);
                        if (ZirkelFrame.isCompressed(Filename)) {
                            o = new GZIPOutputStream(o, 10000);
                        }
                        ZF.ZC.save(o, true, true, false, ZF.ZC.getMacros(), "");
                        o.close();
                    } else {
                        Filename = rFileName;
                    }
                    ;

                } else {
                    o = new FileOutputStream(Filename);
                    if (ZirkelFrame.isCompressed(Filename)) {
                        o = new GZIPOutputStream(o, 10000);
                    }
                    ZF.ZC.save(o, true, true, false, ZF.ZC.getMacros(), "");
                    o.close();
                }
                ZF.Filename = Filename;
                ZF.setTitle(Zirkel.name("program.name") + " : " + FileName.chop(Filename));
//                JZF.SetTitle(ZF.getTitle());
                JMacrosTools.setWindowTitle(JZF);
            } catch (Exception e) {
                Warning w = new Warning(JZF, Zirkel.name("warning.save"),
                        FileName.chop(32, e.toString(), 64),
                        Zirkel.name("warning"), true);
                w.center(JZF);
                w.setVisible(true);
            }
            ResetMacrosProtection(macrostree.JML.MacroTreeTopNode);
        }
    }

    public void savefile() {
        ZF.testjob(false);
        if (!ZF.haveFile()) {
            savefileas();
        } else {
            SetMacrosProtectionForSaveProcess(macrostree.JML.MacroTreeTopNode);
            ZF.RestrictIcons.setState(JZF.restricted);
            ZF.setinfo("save");
            dosave(ZF.Filename, JZF.restricted, ".zir", false);
//            JZF.SetTitle(ZF.getTitle());
            JMacrosTools.setWindowTitle(JZF);
            ResetMacrosProtection(macrostree.JML.MacroTreeTopNode);
        }
    }

    public void savefileas() {
        JFileSaveDialog jfc = new JFileSaveDialog(JZF, ZF, this);
        jfc.setVisible(true);
    }
    
    public String filemode(String fname){
        String mode=fname.endsWith(".job")?"job":"file";
        return mode; 
    }

    public String MakeSlideShow() {
        String name = "";
        String Tools = "";
        String Options="";
        JSlideshowSaveDialog d = new JSlideshowSaveDialog(JZF);
        if (d.OK) {
//            name=d.JFC.getSelectedFile().getAbsolutePath();
            name = d.DIR.getAbsolutePath();

            String[] files = d.DIR.list(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    return ((name.endsWith(".zir")) || (name.endsWith(".zirz")) || (name.endsWith(".job")));
                }
            });

            // initialisation des valeurs par d�faut des dimensions d'applets :
            int w = Integer.parseInt(d.SIZE.w.getText());
            int h = Integer.parseInt(d.SIZE.h.getText());
            boolean t = d.JCBK.isSelected();
            boolean as = d.SIZE.isActualSize();

            // Initialisation de la chaine myfiles='fig1.zir','fig2.zir','fig3.zir'
            // Et de l'ArrayList mydims
            if (files.length == 0) {
                return "";
            }
            String myfiles = "'" + files[0] + "'";
            String mymodes="'" + filemode(files[0]) + "'";
            String AssignmentComment="'" +ReadAssignmentComment(name + System.getProperty("file.separator") +files[0])+ "'";
            String AppletSize = "'" + ReadAppletSize(name + System.getProperty("file.separator") + files[0], w, h, t, as) + "'";
            for (int i = 1; i < files.length; i++) {
                myfiles += ",'" + files[i] + "'";
                mymodes+= ",'" + filemode(files[i]) + "'";
                AppletSize += ",'" + ReadAppletSize(name + System.getProperty("file.separator") + files[i], w, h, t, as) + "'";
                AssignmentComment+=",'" +ReadAssignmentComment(name + System.getProperty("file.separator") +files[i])+ "'";
            }


            String myapplettag = "<APPLET ARCHIVE='CaRMetal.jar' CODE='rene.zirkel.ZirkelApplet.class' ";
            myapplettag += "WIDTH='\"+dims[0]+\"' ";
            myapplettag += "HEIGHT='\"+dims[1]+\"' ";
            myapplettag += "ALIGN='CENTER'>";




            myapplettag += "<PARAM NAME='\"+appletmodes[i]+\"' VALUE='\"+zirfiles[i]+\"'>";
            myapplettag += "<PARAM NAME='color' VALUE='255,255,255'>";

            if (d.JCBK.isSelected()) {
                String[] b = ReadToolsAndOptions();
                Tools = b[0];
                Options=b[1];
                myapplettag += "<PARAM NAME='style' VALUE='\"+styl+\"'>";
                myapplettag += "\"+tools+options+\"";
            } else {
                myapplettag += "<PARAM NAME='style' VALUE='plain'>";
            }


            myapplettag += "<PARAM NAME='movefixname' VALUE='true'>";
            myapplettag += "<PARAM NAME='nomousezoom' VALUE='false'>";
            myapplettag += "<PARAM NAME='editdigits' VALUE='" + Global.getParameter("digits.edit", 5) + "'>";
            myapplettag += "<PARAM NAME='displaydigits' VALUE='" + Global.getParameter("digits.lengths", 5) + "'>";
            myapplettag += "<PARAM NAME='angledigits' VALUE='" + Global.getParameter("digits.angles", 1) + "'>";
            myapplettag += "<PARAM NAME='minpointsize' VALUE='" + Global.getParameter("minpointsize", 3) + "'>";
            myapplettag += "<PARAM NAME='minlinesize' VALUE='" + Global.getParameter("minlinesize", 1.0) + "'>";
            myapplettag += "<PARAM NAME='minfontsize' VALUE='" + Global.getParameter("minfontsize", 12) + "'>";
            myapplettag += "<PARAM NAME='arrowsize' VALUE='16'>";
            myapplettag += "</APPLET>";

            Vector lines = ReadAllTemplateLines();

            PrintWriter out;
            try {
                out = new PrintWriter(name + System.getProperty("file.separator") + "index.html");
                String myline = "";

                String sdownload = LocHTML("slideshow.download");
                String snext = LocHTML("slideshow.next");
                String sprevious = LocHTML("slideshow.previous");
                String ssignature = LocHTML("slideshow.signature");

                for (int i = 0; i < lines.size(); i++) {
                    myline = (String) lines.get(i);
                    if (myline.startsWith("var zirfiles")) {
                        myline = "var zirfiles=[" + myfiles + "];";
                    }if (myline.startsWith("var appletmodes")) {
                        myline = "var appletmodes=[" + mymodes + "];";
                    } else if (myline.startsWith("var appletdims")) {
                        myline = "var appletdims=[" + AppletSize + "];";
                    }else if (myline.startsWith("var appletcomments")) {
                        myline = "var appletcomments=[" + AssignmentComment + "];";
                    } else if (myline.startsWith("myapplettag=")) {
                        myline = "myapplettag=\"" + myapplettag + "\";";
                    } else if (myline.startsWith("var names")) {
                        myline = "var names=[" + myfiles + "];";
                    } else if (myline.startsWith("var next")) {
                        myline = "var next='" + snext + "';";
                    } else if (myline.startsWith("var previous")) {
                        myline = "var previous='" + sprevious + "';";
                    } else if (myline.startsWith("var download")) {
                        myline = "var download='" + sdownload + "';";
                    } else if (myline.startsWith("var signature")) {
                        myline = "var signature='" + ssignature + "';";
                    } else if (myline.startsWith("var applettools")) {
                        out.println("var applettools=[];");
                        if (d.JCBK.isSelected()) {
                            for (int k = 0; k < files.length; k++) {
                                out.println("applettools[" + k + "]='" + Tools + "';");
                            }
                        }
                        myline="";

                    } else if (myline.startsWith("var appletoptions")) {
                        out.println("var appletoptions=[];");
                        if (d.JCBK.isSelected()) {
                            for (int k = 0; k < files.length; k++) {
                                out.println("appletoptions[" + k + "]='" + Options + "';");
                            }
                        }
                        myline="";

                    }
                    
                    out.println(myline);
                    out.flush();
                }
                ;
                out.close();
            } catch (Exception ex) {
            }

            JZF.SaveJarAndLaunchBrowser(name, "index.html");
        }
        d.doclose();
        return name;
    }

    private String LocHTML(String code) {
        byte[] c;
        String lochtml = null;
        try {
            c = JZF.Strs.getString(code).getBytes("UTF-8");
            String encoding = (System.getProperty("mrj.version") != null) ? "MacRoman" : "ISO-8859-1";
            lochtml = new String(c, encoding);
        } catch (Exception ex) {
            lochtml = JZF.Strs.getString(code);
        }
        ;
        return lochtml;
    }

    private String[] ReadToolsAndOptions() {
//        String tls = " move point boundedpoint intersection line ray segment locus fixedsegment circle circle3 " +
//                "fixedcircle parallel plumb midpoint angle fixedangle tracker objecttracker " +
//                "animate expression area quadric text hide bi_symc bi_syma bi_trans bi_med ";
                String tls = " move point boundedpoint intersection line ray segment fixedsegment circle circle3 " +
                "fixedcircle parallel plumb midpoint angle fixedangle tracker objecttracker locus " +
                "animate expression area quadric text hide bi_symc bi_syma bi_trans bi_med " +
                "bi_biss bi_circ bi_arc bi_t_align bi_t_para bi_t_perp bi_t_equi bi_t_app " +
                "bi_t_conf bi_function_u ";
        String tools[] = tls.split(" ");
        String opts = "back delete undo showname showvalue hidden obtuse solid grid partial plines rename function";
        String options[] = opts.split(" ");

        String[] b = {" ", " "};
        for (int i = 0; i < tools.length; i++) {
            if (JZF.JPM.isRestrictedIcon(tools[i])) {
                b[0] += tools[i] + " ";
            }
        }
        ;
        for (int i = 0; i < options.length; i++) {
            if (JZF.JPM.isRestrictedIcon(options[i])) {
                b[1] += options[i] + " ";
            }
        }
        ;
        if (JZF.JPM.isRestrictedIcon("color0")) {
            b[1] += "color ";
        }
        if (JZF.JPM.isRestrictedIcon("thickness0")) {
            b[1] += "thickness ";
        }
        if (JZF.JPM.isRestrictedIcon("type0")) {
            b[1] += "type ";
        }


        return b;
    }

    private String ReadAppletSize(String file, int w, int h, boolean t, boolean as) {
        String str = "";
        if (t) {
            h += 45;
        }
        String params = w + "," + h;
        if (!as) {
            return params;
        }
        BufferedReader in;
        try {
            InputStream input = new FileInputStream(file);
            in = new BufferedReader(new InputStreamReader(input));
            while ((str = in.readLine()) != null) {
                if (str.startsWith("<Windowdim")) {
                    str = str.replace("<", "");
                    str = str.replace("/>", "");
                    XmlTag tag = new XmlTag(str);
                    if ((tag.hasParam("w")) && (tag.hasParam("h"))) {
                        int zcw = Integer.parseInt(tag.getValue("w")) - (JZF.getSize().width - JZF.ZF.ZC.getSize().width);
                        int zch = Integer.parseInt(tag.getValue("h")) - (JZF.getSize().height - JZF.ZF.ZC.getSize().height);
                        int myH = (t) ? 45 + zch : zch;
                        params = zcw + "," + myH;
                    }
                    break;
                }
            }
            in.close();
        } catch (Exception e) {
        }
        return params;
    }
    
    private String ReadAssignmentComment(String file) {
        String str = "";
        BufferedReader in;
        try {
            InputStream input = new FileInputStream(file);
            in = new BufferedReader(new InputStreamReader(input));
            while ((str = in.readLine()) != null) {
                if (str.startsWith("<Assignment>")) {
                    String myCom="";
                    while (true) {
                        str=in.readLine();
                        str=str.replace("<P>", "");
                        str=str.replace("</P>", "<br>");
                        if (str.startsWith("</Assignment>")) break;
                        else myCom+=str+" "; 
                    }
                    
                    in.close();
                    return myCom;
                }
            }
            in.close();
        } catch (Exception e) {
        }
        return "";
    }

    private Vector ReadAllTemplateLines() {
        String str;
        Vector lines = new Vector();
        BufferedReader in;
        try {
            InputStream input = getClass().getResourceAsStream("/eric/docs/index.html");
            in = new BufferedReader(new InputStreamReader(input));
            while ((str = in.readLine()) != null) {
                lines.add(str);
            }
            in.close();
        } catch (Exception e) {
        }

        return lines;
    }

    void SetMacrosProtectionForSaveProcess(JDefaultMutableTreeNode mynode) {
        if (!(mynode.isLeaf())) {
            for (int i = 0; i < mynode.getChildCount(); i++) {
                SetMacrosProtectionForSaveProcess((JDefaultMutableTreeNode) mynode.getChildAt(i));
            }
        } else {
            String myname = (String) mynode.getUserObject();
            if (!(myname.startsWith("-- "))) {
                if ((mynode.macrotype == 1) || (mynode.macrotype == 2)) {
                    if (mynode.m != null) {
                        mynode.m.setProtected(false);
                    }

                } else {
                    if (mynode.m != null) {
                        mynode.m.setProtected(true);
                    }
                }
//                ZF.ZC.storeMacro(mynode.m,true);
            }
        }
        ;
    }

    void ResetMacrosProtection(JDefaultMutableTreeNode mynode) {
        if (!(mynode.isLeaf())) {
            for (int i = 0; i < mynode.getChildCount(); i++) {
                ResetMacrosProtection((JDefaultMutableTreeNode) mynode.getChildAt(i));
            }
        } else {
            String myname = (String) mynode.getUserObject();
            if (!(myname.startsWith("-- "))) {
                if (mynode.macrotype < 2) {
                    if (mynode.m != null) {
                        mynode.m.setProtected(true);
                    }
                } else {
                    if (mynode.m != null) {
                        mynode.m.setProtected(false);
                    }
                }
            }
        }
        ;
    }

    private void savemacros() {
        Vector ZFMacros;

        ZFMacros = new Vector();
        for (int i = 0; i < SelectedPath.length; i++) {
            SelectedNode = (JDefaultMutableTreeNode) SelectedPath[i].getLastPathComponent();
            parsesave(SelectedNode, ZFMacros);
        }

        JFileChooser jfc = new JFileChooser(JGlobals.getLastFilePath());
        jfc.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jfc.setApproveButtonText(JZF.Strs.getString("macros.savedlog.buttonok"));
        jfc.setAcceptAllFileFilterUsed(false);
        JFileFilter ffilter = new JFileFilter(JZF.Strs.getString("filedialog.macrofilefilter"), ".mcr");
        jfc.addChoosableFileFilter(ffilter);
        jfc.setFileFilter(ffilter);

        int rep = jfc.showSaveDialog(null);
        if (rep == JFileChooser.APPROVE_OPTION) {
            File outputfile = jfc.getSelectedFile();
            JGlobals.setLastFilePath(outputfile.getAbsolutePath());
            String ext = (outputfile.getAbsolutePath().endsWith(".mcr")) ? "" : ".mcr";
            ZF.dosave(outputfile.getAbsolutePath() + ext, false, true, true, ZFMacros);
        }
    }

    private void parsesave(JDefaultMutableTreeNode node, Vector ZFMacros) {
        if (node.isLeaf()) {
            MacroItem mi = new MacroItem(node.m, null);
            ZFMacros.add(mi);
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                parsesave((JDefaultMutableTreeNode) node.getChildAt(i), ZFMacros);
            }
        }
    }

    public void updatelibrary() {
        Vector ZFMacros;
        
        if (!JZF.restricted) {
            ZFMacros = new Vector();
            parseupdate(macrostree.JML.MacroTreeTopNode, ZFMacros);
            String mypath = JGlobals.AppPath();
            String Filename = "library.mcr";
            if (new File(mypath + Zirkel.name("language", "") + "library.mcr").exists()) {
                Filename = Zirkel.name("language", "") + "library.mcr";
            }
            ZF.dosave(mypath + Filename, false, true, true, ZFMacros);
        }
        ;
    }

    private void parseupdate(JDefaultMutableTreeNode node, Vector ZFMacros) {
        if (node.isLeaf()) {
            String myname = (String) node.getUserObject();
            if (!(myname.startsWith("-- "))) {
                if (node.m.isProtected()) {
                    MacroItem mi = new MacroItem(node.m, null);
                    ZFMacros.add(mi);
                }
            }

        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                parseupdate((JDefaultMutableTreeNode) node.getChildAt(i), ZFMacros);
            }
        }
    }

    private void runmacro() {
        JZF.JPM.deselectgeomgroup();
        SelectedNode.runZmacro();
    }

    public void actualiseproperties() {
        if (MInspector.freeJP.isVisible()) {
            boolean nomacrofound = true;
            SelectedPath = macrostree.getSelectionPaths();
            if (SelectedPath != null) {
                for (int i = 0; i < SelectedPath.length; i++) {
                    SelectedNode = (JDefaultMutableTreeNode) SelectedPath[i].getLastPathComponent();
                    if ((SelectedNode.isLeaf()) && (!((String) (SelectedNode.getUserObject())).startsWith("--"))) {
                        MInspector.setMacro(SelectedNode);
                        nomacrofound = false;
                        break;
                    }
                }
                ;
            }
            ;
            if (nomacrofound) {
                MInspector.clearPalette();
            }
        }
    }

    private void showproperties() {
        if (SelectedNode != null) {
            MInspector.setMacro(SelectedNode);
        }
        MInspector.setStandardLocation();
        MInspector.setVisible(true);
    }

    public void duplicatenodes() {
        // this is a very dirty way to clone a macro :
        Macro mymacro = (Macro) SelectedNode.m.clone();
        String[] mytab = mymacro.getName().split("/");
        mymacro.setName(mytab[mytab.length - 1]);
        Vector ZFMacros = new Vector();
        ZFMacros.add(new MacroItem(mymacro, null));
        String mypath = JGlobals.AppPath();
        ZF.dosave(mypath + "buffer.mcr", false, true, true, ZFMacros);
        JMacrosTools.OpenMacro(mypath + "buffer.mcr");
    }

    public void deletenodes() {
        TreePath[] paths = macrostree.getSelectionPaths();
        if ((paths) != null) {
            Object[] options = {"Ok", "Cancel"};
            int rep = JOptionPane.showOptionDialog(null, JZF.Strs.getString("macros.question.delete"), "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            if (rep == 0) {
                for (int i = 0; i < paths.length; i++) {
                    JDefaultMutableTreeNode node = (JDefaultMutableTreeNode) paths[i].getLastPathComponent();
                    JDefaultMutableTreeNode father = (JDefaultMutableTreeNode) node.getParent();
                    ((DefaultTreeModel) macrostree.getModel()).removeNodeFromParent(node);
                    while (father.getChildCount() == 0) {
                        JDefaultMutableTreeNode grandfather = (JDefaultMutableTreeNode) father.getParent();
                        ((DefaultTreeModel) macrostree.getModel()).removeNodeFromParent(father);
                        father = grandfather;
                    }
                }
            }
            SwingUtilities.invokeLater(new Runnable() {
//                public void run(){ActualiseZirkelMacros();}
                public void run() {
                    ActualiseLibraryMacros();
                }
            });
        }
    }

    public void ActualiseLibraryMacros() {
        Vector macs = ZF.ZC.getMacros();
        macs.removeAllElements();
        parsedeletemacro(macrostree.JML.MacroTreeTopNode, macs);
        JMacrosTools.getDefaultMacros();
    }

//    public void ActualiseZirkelMacros(){
//        Vector ZFMacros=new Vector();
//        parsedeletemacro(macrostree.JML.MacroTreeTopNode,ZFMacros);
//        Vector macs=ZF.ZC.getMacros();
//        macs.removeAllElements();
//        for(int i=0;i<ZFMacros.size();i++){
//            macs.add((MacroItem)ZFMacros.get(i));
//        }
//    }
    private void parsedeletemacro(JDefaultMutableTreeNode Ndfrom, Vector ZFMacros) {
        if (Ndfrom.isLeaf()) {
            String myname = (String) Ndfrom.getUserObject();
            if (!(myname.startsWith("-- "))) {
                MacroItem mi = new MacroItem(Ndfrom.m, null);
                ZFMacros.add(mi);
                if (Ndfrom.m.isProtected()) {
                    JMacrosTools.librarymacros.add(mi);
                }
            }
        } else {
            for (int i = 0; i < Ndfrom.getChildCount(); i++) {
                parsedeletemacro((JDefaultMutableTreeNode) Ndfrom.getChildAt(i), ZFMacros);
            }
        }
        ;
    }

    public void addfolder() {
        JDefaultMutableTreeNode root;

        JDefaultMutableTreeNode node = new JDefaultMutableTreeNode(JZF.Strs.getString("macros.untitledfolder"));
        node.add(new JDefaultMutableTreeNode(JZF.Strs.getString("macros.emptynode")));

        TreePath[] paths = macrostree.getSelectionPaths();
        if ((paths) != null) {
            root = (JDefaultMutableTreeNode) paths[0].getLastPathComponent();

            if (root.isLeaf()) {
                // if the first selected node is a leaf :
                DefaultMutableTreeNode father = (DefaultMutableTreeNode) root.getParent();
                int i = father.getIndex(root) + 1;

                ((DefaultTreeModel) macrostree.getModel()).insertNodeInto(node, father, i);

            } else {
                // if the first selected node is a folder :
                ((DefaultTreeModel) macrostree.getModel()).insertNodeInto(node, root, root.getChildCount());
            }
        } else {
            // There is no selected node :
            ((DefaultTreeModel) macrostree.getModel()).insertNodeInto(node, macrostree.JML.MacroTreeTopNode, macrostree.JML.MacroTreeTopNode.getChildCount());
        }

        // Transformation d'un noeud en TreePath :
        TreePath tp = new TreePath(node.getPath());
        macrostree.setEditable(true);
        macrostree.startEditingAtPath(tp);

    }

    public void renamenode() {
        TreePath[] paths = macrostree.getSelectionPaths();
        if ((paths) != null) {
            macrostree.setEditable(true);
            macrostree.startEditingAtPath(paths[0]);
        }
    }
    
     public void goDownOrUp(boolean down) {
        TreePath[] paths = macrostree.getSelectionPaths();
        int inc=(down)?1:-1;
        if ((paths) != null) {
            macrostree.setEditable(true);
            macrostree.setSelectionRow(macrostree.getSelectionRows()[0]+inc);
        }
    }

    private static final JSeparator createSeparator() {
        JSeparator jsep = new JSeparator(JSeparator.HORIZONTAL);

        Dimension d = new Dimension(200, 12);
        jsep.setMaximumSize(d);
        jsep.setMinimumSize(d);
        jsep.setPreferredSize(d);
        jsep.setSize(d);
        jsep.setUI(new MiddleSeparatorUI());
        return jsep;
    }

    private static final class MiddleSeparatorUI extends SeparatorUI {

        public void paint(Graphics g, JComponent c) {
            Dimension s = c.getSize();
            int middleHeight = (s.height - 1) / 2;


            g.setColor(Color.lightGray);
            g.drawLine(0, middleHeight, s.width, middleHeight);


            g.setColor(Color.white);
            g.drawLine(0, middleHeight + 1, s.width, middleHeight + 1);
        }
    }
}
