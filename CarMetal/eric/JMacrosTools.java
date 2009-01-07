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
import java.awt.Frame;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

import rene.gui.Global;
import rene.util.FileName;
import rene.util.xml.XmlReader;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTagPI;
import rene.util.xml.XmlTree;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Count;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroItem;
import rene.zirkel.objects.PointObject;
import eric.controls.SliderSnap;

/**
 *
 * @author erichake
 */
public class JMacrosTools {

    static public Vector librarymacros=new Vector();
    static public Vector builtinmacros=new Vector();
    static public String MacrosLibraryFileName="";
    static public String MacrosBackupLibraryFileName="";
    static public JZirkelFrame CurrentJZF=null;
    static public ArrayList AllJZFs=new ArrayList();
    static public ArrayList StartupFiles=new ArrayList();
    static public boolean isStartup=true;
    static public boolean isNewVersion=false;
    static public boolean busy=false;
    static int activNum=0;

    // Appel� par Zirkel ou par la commande "new"
    public static boolean isJZFnumTooBig() {
        if (AllJZFs.size()>10) {
            JOptionPane.showMessageDialog(null, JGlobals.Loc("alert.toomuchwins"));
            return true;
        }
        return false;
    }

    public static void NewJZirkelWindow(boolean with3D, int w, int h) {
        int xloc=0, yloc=0;
//        Runtime.getRuntime().gc();
//        System.out.println(" mem: " + Runtime.getRuntime().freeMemory()/1048576 + "M");
        JZirkelFrame oldframe=CurrentJZF;
        if (!(oldframe==null)) {
            updateLibraryFromTree();
            Point pt=oldframe.getLocation();
            xloc=pt.x+20;
            yloc=pt.y+20;
        }
        initProperties();
        JGlobalPreferences.setLocalPreferences();
        rene.zirkel.construction.Count.resetAll();
        CurrentJZF=new eric.JZirkelFrame(with3D, xloc, yloc, w, h);
        if (AllJZFs.size()==0) {
            LoadDefaultMacrosAtStartup();
        }
        AllJZFs.add(CurrentJZF);
        CurrentJZF.ZContent.macros.myJML.initMacrosTree();
    }

    public static String shortFileName(String s) {
        s=s.replace(System.getProperty("file.separator"), "@sep@");
        String fn[]=s.split("@sep@");
        return fn[fn.length-1];
    }

    public static void setWindowTitle(JZirkelFrame jzf) {
        String s1=(AllJZFs.size()<2)?"":"["+(AllJZFs.indexOf(jzf)+1)+"] ";
        String s2=(jzf.ZF.Filename.equals(""))?Zirkel.name("program.name"):shortFileName(jzf.ZF.Filename);
        jzf.SetTitle(s1+s2);
        jzf.GeneralMenuBar.InitWindowsMenu();
    }

    public static void RefreshDisplay() {
        for (int i=0; i<AllJZFs.size(); i++) {
            JZirkelFrame jzf=(JZirkelFrame) AllJZFs.get(i);
            setWindowTitle(jzf);
            if (!jzf.equals(CurrentJZF)) {
                jzf.ZF.ZC.setFrozen(false);
                jzf.JPM.MainPalette.setVisible(false);
                jzf.ResizeAll();
                jzf.ZF.ZC.removeMouseMotionListener(jzf.ZF.ZC);

                jzf.JPR.setLocalPreferences();
                jzf.ZF.ZC.setFrozen(true);
                // Only for java5 :
                jzf.pack();

            }
        }
        CurrentJZF.ZF.ZC.setFrozen(true);
        CurrentJZF.ResizeAll();
        CurrentJZF.JPM.MainPalette.FollowWindow();
        CurrentJZF.JPM.MainPalette.setVisible(true);

        CurrentJZF.JPM.MainPalette.paintImmediately();
        CurrentJZF.ZF.ZC.addMouseMotionListener(CurrentJZF.ZF.ZC);

        CurrentJZF.JPR.setLocalPreferences();
        CurrentJZF.JPM.setGoodProperties(CurrentJZF.JPM.geomSelectedIcon());
        CurrentJZF.ZF.ZC.setFrozen(false);
        // Only for java5 :
        CurrentJZF.pack();

        if (JGlobals.JPB!=null) {
            JGlobals.JPB.clearme();
        }
    }

    public static void FirstRun() {
        if (StartupFiles.size()>0) {
            OpenStartupFiles();
        } else {
            NewWindow();
        }
        ;
        isStartup=false;
    }

    public static boolean isStartup() {
        return isStartup;
    }

    public static void NewWindow() {
        if (isJZFnumTooBig()) {
            return;
        }
        NewJZirkelWindow(false, 800, 600);
        RefreshDisplay();
    }

    public static void OpenStartupFiles() {
        busy=true;
        for (int i=0; i<StartupFiles.size(); i++) {
            String filename=(String) StartupFiles.get(i);
            if ((filename.endsWith(".mcr"))) {
                OpenMacro(filename);
            } else {
                OpenFile(filename, null, false);
            }
        }
        ;

        StartupFiles.clear();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                RefreshDisplay();
                busy=false;
            }
        });

    }

    public static void New3DWindow() {
        if (isJZFnumTooBig()) {
            return;
        }
        InputStream o=JMacrosTools.class.getResourceAsStream("/base3D.zir");
        String Filename="base3D.zir";
        OpenFile(Filename, o, true);
        CurrentJZF.ZF.setTitle(Zirkel.name("program.name"));
        CurrentJZF.ZF.Filename="";
        RefreshDisplay();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                CurrentJZF.ZF.ZC.JCM.fix3Dcomments();
            }
        });
    }

    public static void OpenFile() {
        if (isJZFnumTooBig()) {
            return;
        }
        String filename=getOpenFile();
        if (!filename.equals("")) {
            OpenFile(filename, null, false);
            RefreshDisplay();
        }
    }

    public static Dimension FindWindowSize(final String filename) {
        Dimension d=new Dimension(800, 600);
        InputStream o=null;
        try {


            o=new FileInputStream(filename);
            if (FileName.extension(filename).endsWith("z")) {
                o=new GZIPInputStream(o);
            }
            XmlReader xml=new XmlReader();
            xml.init(o);
            XmlTree tree=xml.scan();
            o.close();
            Enumeration e=tree.getContent();
            while (e.hasMoreElements()) {
                tree=(XmlTree) e.nextElement();
                if (tree.getTag() instanceof XmlTagPI) {
                    continue;
                }
                if (!tree.getTag().name().equals("CaR")) {
                    throw new ConstructionException("CaR tag not found");
                } else {
                    break;
                }
            }
            e=tree.getContent();
            while (e.hasMoreElements()) {
                tree=(XmlTree) e.nextElement();
                if (tree.getTag().name().equals("Construction")) {
                    break;
                }
            }
            e=tree.getContent();
            while (e.hasMoreElements()) {
                tree=(XmlTree) e.nextElement();
                if (tree.getTag().name().equals("Windowdim")) {
                    break;
                }
            }

            XmlTag tag=tree.getTag();

            if ((tag.hasParam("w"))&&(tag.hasParam("h"))) {
                int w=Integer.parseInt(tag.getValue("w"));
                int h=Integer.parseInt(tag.getValue("h"));
                d.width=w;
                d.height=h;
            }
        } catch (Exception ex) {
        }
        return d;
    }

    public JMacrosTools() {
    }
    
//    static JZirkelFrame requestedJZF=null;

    public static boolean isAlreadyOpened(String filename) {
        for (int i=0; i<AllJZFs.size(); i++) {
            JZirkelFrame jzf=(JZirkelFrame) AllJZFs.get(i);
            if (jzf.ZF.Filename.equals(filename)) {
//                requestedJZF=jzf;
                return true;
            }
        }
        return false;
    }

    public static void OpenFile(final String filename, final InputStream in, final boolean with3D) {
//        String filename=name;
        if (isJZFnumTooBig()) {
            return;
        }
        if (filename.equals("")) {
            return;
        }

        if (isAlreadyOpened(filename)) {
            return;
        }

        activNum++;

        Dimension d=FindWindowSize(filename);
        if ((filename.endsWith(".zir"))||(filename.endsWith(".zirz"))) {

            // detect if the current window exists and is empty :
            int m=1;
            try {
                if (!CurrentJZF.busy) {
                    m=CurrentJZF.ZF.ZC.getConstruction().V.size();
                }
            } catch (Exception e) {
            }
            if (m>0) {
                NewJZirkelWindow(with3D, d.width, d.height);
            }
//            CurrentJZF.ZF.ZC.setFrozen(true);
            CurrentJZF.is3D=with3D;
            CurrentJZF.busy=true;
            CurrentJZF.ZF.setinfo("save");
            CurrentJZF.ZF.ZC.getConstruction().BackgroundFile=null;
            CurrentJZF.ZF.Background="";
            CurrentJZF.ZF.dograb(false);



            CurrentJZF.ZF.doload(filename, in);
            CurrentJZF.JPM.fix3Dpalette();
            CurrentJZF.JPR.getLocalPreferences();
            rene.zirkel.construction.Count.resetAll();

            SwingUtilities.invokeLater(CurrentJZF.doactualisemacrostree);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    CurrentJZF.ZF.ZC.JCM.readXmlTags();
                }
            });

            activNum--;
            if (m>0) {
                activateFrontMostWindow();
            }
        }
    }

    public static void OpenMacro(String name) {
        if (AllJZFs.size()==0) {
            NewJZirkelWindow(false, 800, 600);
        }
        updateLibraryFromTree();
        saveLibraryToDisk();
        CurrentJZF.ZF.setinfo("macro");
        if (name.equals("")) {
            CurrentJZF.ZF.loadMacros();
        } else {
            InputStream o;
            try {
                o=new FileInputStream(name);
                if (ZirkelFrame.isCompressed(name)) {
                    o=new GZIPInputStream(o);
                }
                CurrentJZF.ZF.ZC.load(o, false, true);
                o.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ;
        SwingUtilities.invokeLater(CurrentJZF.doactualisemacrostree);
    }

    // Appel� seulement � chaque windowactivate d'une JZF existante :
    public static void setCurrentJZF(JZirkelFrame jzf) {
        if (!(CurrentJZF.equals(jzf))) {

            updateLibraryFromTree();
            CurrentJZF=jzf;
            rene.zirkel.construction.Count.resetAll();
            PointObject.setPointLabel(CurrentJZF.PointLabel);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    CurrentJZF.ZContent.macros.myJML.initMacrosTree();
                }
            });
        }
    }

    public static void setDefaultMacros() {
        if (!(CurrentJZF==null)) {
            int i=0;
            Vector F=new Vector();
            Vector V=CurrentJZF.ZF.ZC.getMacros();
            for (i=0; i<V.size(); i++) {
                MacroItem mi=(MacroItem) V.get(i);
                if (!(mi.M.isProtected())) {
                    F.add(V.get(i));
                }
            }
            V.clear();
            for (i=0; i<builtinmacros.size(); i++) {
                V.add(builtinmacros.get(i));
            }
            ;
            for (i=0; i<librarymacros.size(); i++) {
                V.add(librarymacros.get(i));
            }
            ;
            for (i=0; i<F.size(); i++) {
                V.add(F.get(i));
            }
            ;
        }
    }

    public static void getDefaultMacros() {
        if ((!(CurrentJZF==null))) {
            librarymacros.clear();
            Vector V=CurrentJZF.ZF.ZC.getMacros();
            for (int i=0; i<V.size(); i++) {
                MacroItem mi=(MacroItem) V.get(i);
                if (mi.M.isProtected()) {
                    if (!(mi.M.Name.startsWith("@builtin@"))) {
                        librarymacros.add(V.get(i));
                    }
                }
            }
        }
    }

    /**
     * Find the front most window and activate it
     */
    static public void activateFrontMostWindow() {
        // 
        // this code is dirty, (thread, sleep) because
        // of linux java version...
        // It should be much more clear.

        if (AllJZFs.size()==0) {
            return;
        }

        activNum++;
        Thread t=new Thread() {

            public void run() {
                try {
                    sleep(200);
                    activNum--;
                    if (activNum>0) {
                        return;
                    }
                } catch (Exception ex) {
                }
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        Frame jfs[]=Frame.getFrames();
                        for (int i=0; i<jfs.length; i++) {
                            if ((jfs[i].isActive())&&(jfs[i] instanceof JZirkelFrame)) {
                                JZirkelFrame jzf=(JZirkelFrame) jfs[i];
                                if (!jzf.equals(CurrentJZF)) {
                                    setCurrentJZF(jzf);
                                    RefreshDisplay();
                                    jzf.toFront();
                                }
                            }
                        }
                    }
                });
            }
        };
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }

    static public boolean disposeCurrentJZF() {
        if (AllJZFs.size()==1) {
            if (!CurrentJZF.restricted) {
                updateLibraryFromTree();
                saveLibraryToDisk();
                JGlobalPreferences.savePreferences();
            }
            if (CurrentJZF.ZF.close()) {
                Global.saveProperties("CaR Properties");
                Global.exit(0);
//                CurrentJZF.ZF.dispose();



            } else {
                return false;
            }
        } else {
            if (CurrentJZF.ZF.close()) {
                AllJZFs.remove(CurrentJZF);

                JPalette jp=CurrentJZF.JPM.MainPalette;
                ZirkelFrame curZF=CurrentJZF.ZF;
                CurrentJZF.dispose();
                curZF.dispose();
                jp.dispose();
//                activateFrontMostWindow();

//                Runtime.getRuntime().gc();
            } else {
                return false;
            }
        }
        AllJZFs.remove(CurrentJZF);
        return true;
    }

    static public void disposeAllJZFs() {
        while (AllJZFs.size()>0) {
            CurrentJZF=(JZirkelFrame) AllJZFs.get(AllJZFs.size()-1);
            CurrentJZF.toFront();
            RefreshDisplay();
            if (!disposeCurrentJZF()) {
                break;
            }
        }
    }

    /**
     * This function will copy files or directories from one location to another.
     * note that the source and the destination must be mutually exclusive. This 
     * function can not be used to copy a directory to a sub directory of itself.
     * The function will also have problems if the destination files already exist.
     * @param src -- A File object that represents the source for the copy
     * @param dest -- A File object that represnts the destination for the copy.
     * @throws IOException if unable to copy.
     */
    public static void copyFiles(File src, File dest) throws IOException {
        //Check to ensure that the source is valid...
        if (!src.exists()) {
            throw new IOException("copyFiles: Can not find source: "+src.getAbsolutePath()+".");
        } else if (!src.canRead()) { //check to ensure we have rights to the source...
            throw new IOException("copyFiles: No right to source: "+src.getAbsolutePath()+".");
        }
        //is this a directory copy?
        if (src.isDirectory()) {
            if (!dest.exists()) { //does the destination already exist?
                //if not we need to make it exist if possible (note this is mkdirs not mkdir)
                if (!dest.mkdirs()) {
                    throw new IOException("copyFiles: Could not create direcotry: "+dest.getAbsolutePath()+".");
                }
            }
            //get a listing of files...
            String list[]=src.list();
            //copy all the files in the list.
            for (int i=0; i<list.length; i++) {
                File dest1=new File(dest, list[i]);
                File src1=new File(src, list[i]);
                copyFiles(src1, dest1);
            }
        } else {
            //This was not a directory, so lets just copy the file
            FileInputStream fin=null;

            FileOutputStream fout=null;
            byte[] buffer=new byte[4096]; //Buffer 4K at a time (you can change this).
            int bytesRead;
            try {
                //open the files for input and output
                fin=new FileInputStream(src);
                fout=new FileOutputStream(dest);
                //while bytesRead indicates a successful read, lets write...
                while ((bytesRead=fin.read(buffer))>=0) {
                    fout.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) { //Error copying file... 
                IOException wrapper=new IOException("copyFiles: Unable to copy file: "+
                        src.getAbsolutePath()+"to"+dest.getAbsolutePath()+".");
                wrapper.initCause(e);
                wrapper.setStackTrace(e.getStackTrace());
                throw wrapper;
            } finally { //Ensure that the files are closed (if they were open).
                if (fin!=null) {
                    fin.close();
                }
                if (fout!=null) {
                    fin.close();
                }
            }
        }
    }

    public static void copyFile(String inFile, String outFile) {
        FileChannel in=null;
        FileChannel out=null;
        try {
            in=new FileInputStream(inFile).getChannel();
            out=new FileOutputStream(outFile).getChannel();

            in.transferTo(0, in.size(), out);
        } catch (Exception e) {
        } finally {
            if (in!=null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
            if (out!=null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static String getHomeDirectory() {
        String name="carmetal_config";
        String SP=System.getProperty("file.separator");
        return FileSystemView.getFileSystemView().getDefaultDirectory()+SP+name+SP;

    }

    public static void createLocalDirectory() {
        // Setting (if necessary) home directory name and home library macros file name :

        String mypath=eric.JGlobals.AppPath();

        // Place the help files in the local directory :
        if ((isNewVersion)||(!(new File(getHomeDirectory()+"docs").exists()))) {
            try {
                copyFiles(new File(mypath+"docs"), new File(getHomeDirectory()+"docs"));
            } catch (IOException ex) {
                System.out.println("bug : createLocalDirectory()");
            }
        }

//        // If there is the locate help file in the jar, then copy it to the
//        // user directory :
//        try {
//            InputStream in=JMacrosTools.class.getResourceAsStream("/"+Zirkel.name("language", "")+"info.txt");
//            OutputStream out=new FileOutputStream(getHomeDirectory()+"docs/"+Zirkel.name("language", "")+"info.txt");
//            byte[] buf=new byte[1024];
//            int len;
//            while ((len=in.read(buf))>0) {
//                out.write(buf, 0, len);
//                out.flush();
//            }
//            out.close();
//            in.close();
//        } catch (Exception e) {
//        }


        String Filename="library.mcr";
        if (new File(mypath+Zirkel.name("language", "")+"library.mcr").exists()) {
            Filename=Zirkel.name("language", "")+"library.mcr";
        } else if (new File(getHomeDirectory()+Zirkel.name("language", "")+"library.mcr").exists()) {
            Filename=Zirkel.name("language", "")+"library.mcr";
        }

        MacrosLibraryFileName=getHomeDirectory()+Filename;


        // is there a library in home folder ?
        if (new File(MacrosLibraryFileName).exists()) {
            // Is it a new version at this startup ?
            if (isNewVersion) {
                MacrosBackupLibraryFileName=getHomeDirectory()+"library_backup.mcr";
                copyFile(MacrosLibraryFileName, MacrosBackupLibraryFileName);
                copyFile(mypath+Filename, MacrosLibraryFileName);
            }
        } else {
            new File(getHomeDirectory()).mkdirs();
            copyFile(mypath+Filename, MacrosLibraryFileName);
        }




    }

    /**
     * 
     */
    public static void LoadDefaultMacrosAtStartup() {
        // Loading builtin macros (for some icons in palette, like symetry)
        try {
            InputStream o=JMacrosTools.class.getResourceAsStream("/builtin.mcr");
            LoadMacros(o, builtinmacros);
            o.close();
        } catch (Exception e) {
        }


        if (new File(MacrosLibraryFileName).exists()) {
            try {
                InputStream o=new FileInputStream(MacrosLibraryFileName);
                LoadMacros(o, librarymacros);
                o.close();
                if (!MacrosBackupLibraryFileName.equals("")) {

                    InputStream o2=new FileInputStream(MacrosBackupLibraryFileName);
                    LoadMacros(o2, librarymacros);
                    o2.close();
                    File f=new File(MacrosBackupLibraryFileName);
                    f.delete();
                }
                return;
            } catch (Exception e) {
            }
        }
        try {
            InputStream o=JMacrosTools.class.getResourceAsStream("/default.mcr");
            LoadMacros(o, librarymacros);
            o.close();
            return;
        } catch (Exception e) {
        }

    }

    static private void saveLibraryToDisk() {
        Macro m;
        if (!CurrentJZF.restricted) {
//            String mypath=JGlobals.AppPath();
//            String Filename="library.mcr";
//            if (new File(mypath+Zirkel.name("language","")+"library.mcr").exists())
//                Filename=Zirkel.name("language","")+"library.mcr";
            CurrentJZF.ZF.dosave(MacrosLibraryFileName, false, true, true, librarymacros);
        }
        ;
    }

    public static void updateLibraryFromTree() {
        if (!CurrentJZF.restricted) {
            librarymacros.removeAllElements();
            parseupdate(CurrentJZF.ZContent.macros.myJML.MacroTreeTopNode, librarymacros);
        }
        ;
    }

    private static void parseupdate(JDefaultMutableTreeNode node, Vector V) {
        if (node.isLeaf()) {
            String myname=(String) node.getUserObject();
            if (!(myname.startsWith("-- "))) {
                if (node.m.isProtected()) {
                    MacroItem mi=new MacroItem(node.m, null);
                    V.add(mi);
                }
            }
        } else {
            for (int i=0; i<node.getChildCount(); i++) {
                parseupdate((JDefaultMutableTreeNode) node.getChildAt(i), V);
            }
        }
    }

    private static void LoadMacros(InputStream in, Vector Macros)
            throws Exception {
        Macro m;
        try {
            XmlReader xml=new XmlReader();
            xml.init(in);
            XmlTree tree=xml.scan();
            if (tree==null) {
                throw new ConstructionException("XML file not recognized");
            }
            Enumeration e=tree.getContent();
            while (e.hasMoreElements()) {
                tree=(XmlTree) e.nextElement();
                if (tree.getTag() instanceof XmlTagPI) {
                    continue;
                }
                if (!tree.getTag().name().equals("CaR")) {
                    throw new ConstructionException("CaR tag not found");
                } else {
                    break;
                }
            }
            e=tree.getContent();
            boolean all=false;
            while (e.hasMoreElements()) {
                tree=(XmlTree) e.nextElement();
                XmlTag tag=tree.getTag();
                if (tag.name().equals("Macro")) {
                    try {
                        Count.setAllAlternate(true);
                        m=new Macro(null, tree);
                        int i=0;
                        for (i=0; i<Macros.size(); i++) {

                            if (((MacroItem) Macros.elementAt(i)).M.getName().equals(m.getName())) {
                                break;
                            }
                        }
                        if (i>=Macros.size()) {
                            m.setProtected(true);
                            MacroItem mi=new MacroItem(m, null);
                            Macros.addElement(mi);
                        }
                    } catch (ConstructionException ex) {
                        Count.setAllAlternate(false);
                        throw ex;
                    }
                    Count.setAllAlternate(false);

                } else {
                    throw new ConstructionException(
                            "Construction not found");
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static void initObjectsProperties() {

        Global.setParameter("options.segment.color", Global.getParameter("options.segment.color", 1));
        Global.setParameter("options.segment.colortype", Global.getParameter("options.segment.colortype", 0));
        Global.setParameter("options.segment.shownames", Global.getParameter("options.segment.shownames", false));
        Global.setParameter("options.segment.showvalues", Global.getParameter("options.segment.showvalues", false));
        Global.setParameter("options.segment.large", Global.getParameter("options.segment.large", false));
        Global.setParameter("options.segment.bold", Global.getParameter("options.segment.bold", false));
        Global.setParameter("options.line.color", Global.getParameter("options.line.color", 3));
        Global.setParameter("options.line.colortype", Global.getParameter("options.line.colortype", 0));
        Global.setParameter("options.line.shownames", Global.getParameter("options.line.shownames", false));
        Global.setParameter("options.line.showvalues", false);
        Global.setParameter("options.line.large", Global.getParameter("options.line.large", false));
        Global.setParameter("options.line.bold", Global.getParameter("options.line.bold", false));
        Global.setParameter("options.point.color", Global.getParameter("options.point.color", 2));
        Global.setParameter("options.point.colortype", Global.getParameter("options.point.colortype", 0));
        Global.setParameter("options.point.shownames", Global.getParameter("options.point.shownames", false));
        Global.setParameter("options.point.large", Global.getParameter("options.point.large", false));
        Global.setParameter("options.point.bold", Global.getParameter("options.point.bold", false));
        Global.setParameter("options.point.showvalues", Global.getParameter("options.point.showvalues", false));
        Global.setParameter("options.circle.color", Global.getParameter("options.circle.color", 4));
        Global.setParameter("options.circle.colortype", Global.getParameter("options.circle.colortype", 0));
        Global.setParameter("options.circle.shownames", Global.getParameter("options.circle.shownames", false));
        Global.setParameter("options.circle.showvalues", Global.getParameter("options.circle.showvalues", false));
        Global.setParameter("options.circle.filled", Global.getParameter("options.circle.filled", false));
        Global.setParameter("options.circle.solid", Global.getParameter("options.circle.solid", false));
        Global.setParameter("options.circle.large", Global.getParameter("options.circle.large", false));
        Global.setParameter("options.circle.bold", Global.getParameter("options.circle.bold", false));
        Global.setParameter("options.angle.color", Global.getParameter("options.angle.color", 1));
        Global.setParameter("options.angle.colortype", Global.getParameter("options.angle.colortype", 0));
        Global.setParameter("options.angle.shownames", Global.getParameter("options.angle.shownames", false));
        Global.setParameter("options.angle.showvalues", Global.getParameter("options.angle.showvalues", true));
        Global.setParameter("options.angle.filled", Global.getParameter("options.angle.filled", true));
        Global.setParameter("options.angle.solid", Global.getParameter("options.angle.solid", false));
        Global.setParameter("options.angle.large", Global.getParameter("options.angle.large", false));
        Global.setParameter("options.angle.bold", Global.getParameter("options.angle.bold", false));
        Global.setParameter("options.angle.obtuse", Global.getParameter("options.angle.obtuse", false));
        Global.setParameter("options.area.color", Global.getParameter("options.area.color", 1));
        Global.setParameter("options.area.colortype", Global.getParameter("options.area.colortype", 2));
        Global.setParameter("options.area.shownames", Global.getParameter("options.area.shownames", false));
        Global.setParameter("options.area.showvalues", Global.getParameter("options.area.showvalues", false));
        Global.setParameter("options.area.filled", Global.getParameter("options.area.filled", true));
        Global.setParameter("options.area.solid", Global.getParameter("options.area.solid", false));
        Global.setParameter("options.text.color", Global.getParameter("options.text.color", 1));
        Global.setParameter("options.text.colortype", Global.getParameter("options.text.colortype", 1));
        Global.setParameter("options.text.shownames", Global.getParameter("options.text.shownames", true));
        Global.setParameter("options.text.showvalues", Global.getParameter("options.text.showvalues", true));
        Global.setParameter("options.locus.color", Global.getParameter("options.locus.color", 1));
        Global.setParameter("options.locus.colortype", Global.getParameter("options.locus.colortype", 0));
        Global.setParameter("options.locus.shownames", Global.getParameter("options.locus.shownames", false));
        Global.setParameter("options.locus.showvalues", Global.getParameter("options.locus.showvalues", false));
    }

    public static void initProperties() {

        if (!Global.getParameter("program.version", "").equals(
                Zirkel.name("program.version"))) {
            Global.setParameter("program.newversion", true);
            Global.setParameter("program.version", Zirkel.name("program.version"));
            Global.setParameter("icons", ZirkelFrame.DefaultIcons);
            isNewVersion=true;
        }
        Global.setParameter("iconpath", "/rene/zirkel/newicons/");
        Global.setParameter("icontype", "png");
        if (Global.getParameter("options.smallicons", false)) {
            Global.setParameter("iconsize", 24);
        } else {
            Global.setParameter("iconsize", 32);
        }

//        rene.zirkel.help.Help.CodePage=Global.name("codepage.help", "");
        Global.setParameter("save.includemacros", true);
        Global.setParameter("load.clearmacros", false);
        Global.setParameter("options.backups", false);
        Global.setParameter("options.visual", true);
        Global.setParameter("options.filedialog", false);
        Global.setParameter("options.restricted", true);
        Global.setParameter("options.smallicons", false);
        Global.setParameter("options.indicate", true);
        Global.setParameter("restricted", false);
        Global.setParameter("showgrid", false);
        Global.setParameter("simplegraphics", false);
        Global.setParameter("quality", true);
        Global.setParameter("export.jar", "CaRMetal.jar");
        Global.setParameter("iconpath", "/eric/icons/palette/");
        Global.Background=Global.getParameter("colorbackground", new Color(231, 238, 255));
        Global.setParameter("background.tile", Global.getParameter("background.tile", false));
        if (!Global.haveParameter("options.germanpoints")&&
                Locale.getDefault().getLanguage().equals("de")) {
            Global.setParameter("options.germanpoints", true);
        }
        SliderSnap.init();
        initObjectsProperties();

    }

    public static String getOpenFile() {
        String name="";
        JFileChooser jfc=new JFileChooser(JGlobals.getLastFilePath());
        jfc.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
        jfc.setApproveButtonText("Ouvrir la figure");
        jfc.setAcceptAllFileFilterUsed(false);
        JFileFilter ffilter=new JFileFilter(CurrentJZF.Strs.getString("filedialog.filefilter"), ".zir");
        jfc.addChoosableFileFilter(ffilter);
        JFileFilter fcfilter=new JFileFilter(CurrentJZF.Strs.getString("filedialog.compressedfilefilter"), ".zirz");
        jfc.addChoosableFileFilter(fcfilter);
        jfc.setFileFilter(ffilter);

        jfc.setAccessory(new ZirkelCanvasFileChooserPreview(jfc));
        int rep=jfc.showOpenDialog(null);
        if (rep==JFileChooser.APPROVE_OPTION) {
            name=jfc.getSelectedFile().getAbsolutePath();
            JGlobals.setLastFilePath(name);
        } else {
            name="";
        }
        return name;
    }
}
