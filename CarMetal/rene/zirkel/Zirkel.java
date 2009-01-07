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

// File: Zirkel.java
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JFrame;

import rene.gui.Global;

public class Zirkel extends Applet
        implements ActionListener {

    public static final long Version = 110;
    public static boolean IsApplet = false;
    
    static public GraphicsEnvironment GE;
    static public Rectangle SCREEN;
    
    public static void DetectDesktopSize(){
        GE=GraphicsEnvironment.getLocalGraphicsEnvironment();
        if (System.getProperty("os.name").equals("Linux")) {
// Very dirty trick to escape a very dirty bug on linux java :
            JFrame myframe=new JFrame();
            myframe.setUndecorated(true);
            int s=myframe.getExtendedState();
            myframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
            myframe.setVisible(true);
            myframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
            while (myframe.getExtendedState()==s) {}
            
            SCREEN=myframe.getBounds();  
            myframe.dispose();
            if (SCREEN.x<0) SCREEN.x=0;
        }else{
            // this is much better on mac and windows :
            SCREEN=GE.getMaximumWindowBounds();
        }
        
    };

    public static String name(String tag, String def) {
        return Global.name(tag, def);
    }

    public static String name(String tag) {
        return Global.name(tag);
    }

    public void init() // zirkel is called as applet
    {
        String s = getParameter("Language");
        if (s != null) {
            Locale.setDefault(new Locale(s, ""));
        }
        Global.initBundle("rene/zirkel/docs/ZirkelProperties");
        setLayout(new BorderLayout());
        Button StartButton = new Button("Start");
        add("Center", StartButton);
        StartButton.addActionListener(this);
        IsApplet = true;
        if (getParameter("oldicons") != null) {
            Global.setParameter("iconpath", "/rene/zirkel/icons/");
            Global.setParameter("icontype", "gif");
            Global.setParameter("iconsize", 20);
        } else {
            Global.setParameter("iconpath", "/rene/zirkel/newicons/");
            Global.setParameter("icontype", "png");
            if (getParameter("smallicons") != null) {
                Global.setParameter("iconsize", 24);
            } else {
                Global.setParameter("iconsize", 32);
            }
        }

    }

    public static void main(String args[]) // zirkel is calles as application
    {
        DetectDesktopSize();

        int i = 0;
        String filename = "";
        boolean simple = false, restricted = false;
        String Home = null;


        while (i < args.length) {
            if (args[i].startsWith("-l") && i < args.length - 1) {
                Locale.setDefault(new Locale(args[i + 1], ""));
                i += 2;
            } else if (args[i].startsWith("-h") && i < args.length - 1) {
                Home = args[i + 1];
                i += 2;
            } else if (args[i].startsWith("-s")) {
                simple = true;
                i++;
            } else if (args[i].startsWith("-r")) {
                restricted = true;
                i++;
            } else if (args[i].startsWith("-d")) {
                Properties p = System.getProperties();
                try {
                    PrintStream out = new PrintStream(
                            new FileOutputStream(p.getProperty("user.home") +
                            p.getProperty("file.separator") + "zirkel.log"));
                    System.setErr(out);
                    System.setOut(out);
                } catch (Exception e) {
                    System.out.println("Could not open log file!");
                }
                i++;
            } else {
                filename= args[i];
                eric.JMacrosTools.StartupFiles.add(filename);
                i++;
            }
        }
        ;

        

        Global.loadProperties(eric.JMacrosTools.getHomeDirectory() + "carmetal_config.txt");
        Global.initBundle("rene/zirkel/docs/ZirkelProperties", true);

        eric.JGlobals.ShowLogoWindow();

        


        eric.JMacrosTools.initProperties();
        
        eric.JMacrosTools.createLocalDirectory();
        eric.JGlobalPreferences.initPreferences();
        
        eric.JMacrosTools.FirstRun();
        eric.JGlobals.CreatePopertiesBar();
        eric.JGlobals.DisposeLogoWindow();
    }

    public void actionPerformed(ActionEvent e) // the user pressed the start button of the applet
    {
        ZirkelFrame F = new ZirkelFrame(true);
        F.setVisible(true);
    }
}


