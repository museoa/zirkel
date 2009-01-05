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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class JZirkelTheme {
    private String ThemesPath="/eric/icons/themes/";
    private String CurrentTheme="/eric/icons/themes/gray/";
    private String CommonTheme="/eric/icons/themes/common/";
    private boolean MAClook=true;
    int TitleBarHeight=35;
    int TitleBarTextHeight=25;
    int MenuBarHeight=20;
    int VertBorderWidth=7;
    int VertSeparatorWidth=5;
    int StatusHeight=22;
    int LeftPanelTitleHeight=18;
    float opacity=0.5f;
    
    public JZirkelTheme(){
        
    };
    
    public void setDisable(Graphics g,Dimension d){
        Graphics2D g2 = (Graphics2D) g;
                AlphaComposite ac =
                        AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacity);
                g2.setComposite(ac);
                g2.setColor(new Color(255,255,255));
                g2.fillRect(0,0,d.width,d.height);
    }
    
    public boolean AllowMacLook(){
        return MAClook;
    }
    
    public ImageIcon getIcon(String s){
        ImageIcon myicon;
        try {
            myicon=new ImageIcon(JZirkelTheme.class.getResource(CurrentTheme+s));
        } catch(Exception e) {
            myicon=new ImageIcon(JZirkelTheme.class.getResource(CommonTheme+s));
        }
        return myicon;
    }
    
    public Image getImage(String s){
        ImageIcon myicon;
        try {
            myicon=new ImageIcon(JZirkelTheme.class.getResource(CurrentTheme+s));
        } catch(Exception e) {
            myicon=new ImageIcon(JZirkelTheme.class.getResource(CommonTheme+s));
        }
        return myicon.getImage();
    }
    
    public void setTheme(String theme){
        CurrentTheme=ThemesPath+theme+"/";
        
        if (theme.equals("gray")) {
           VertBorderWidth=5;
           VertSeparatorWidth=3;
           TitleBarHeight=35;
           TitleBarTextHeight=25;
           MenuBarHeight=20;
           MAClook=true;

        }else if (theme.equals("brushed")) {
           VertBorderWidth=7;
           VertSeparatorWidth=5;
           TitleBarHeight=25;
           TitleBarTextHeight=25;
           MenuBarHeight=20;
           MAClook=false;
        };
        
        Global.setParameter("LookAndFeel",theme);
    }
    
    public void ChangeTheme(String theme){
        ImageIcon myicon;
        
        setTheme(theme);
        JMacrosTools.CurrentJZF.StatusBar.setButtonsIcons();
        JMacrosTools.CurrentJZF.TitleBar.init();
        JMacrosTools.CurrentJZF.ZContent.macros.myJML.controls.setButtonsIcons();
        JMacrosTools.CurrentJZF.ResizeAll();
        JMacrosTools.CurrentJZF.repaint();
        JMacrosTools.CurrentJZF.JPM.MainPalette.repaint();
    }
    
}
