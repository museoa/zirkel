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

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import rene.gui.Global;

/**
 *
 * @author erichake
 */
public class JToolTip extends JDialog {
    
    private JPalette JP;
    private JLabel ToolTipLabel;
    private JLabel ShortcutLabel;
    
    private JToolTipContainer Content;
    
    /** Creates a new instance of JToolTip */
    public JToolTip(JDialog parent) {
        super(parent);
        JP=(JPalette) parent;
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ToolTip");
        
//        setBackground(new Color(120,120,120));
        setFocusable(false);
        setFocusableWindowState(false);
        this.setUndecorated(true);
        Content=new JToolTipContainer();
        this.setContentPane(Content);
        Content.setBackground(new Color(90,90,90));
//        this.setSize(100,100);
        
        ShortcutLabel=new JLabel("");
//        ShortcutLabel.setBackground(new Color(120,120,120));
        ShortcutLabel.setForeground(new Color(230,230,230));
        ShortcutLabel.setFont(new Font("Verdana",1,10));
        ShortcutLabel.setOpaque(false);
        ShortcutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ShortcutLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        ToolTipLabel=new JLabel("");
        ToolTipLabel.setBackground(new Color(194,217,231));
        ToolTipLabel.setForeground(new Color(71,79,84));
        ToolTipLabel.setFont(new Font("Verdana",0,10));
        ToolTipLabel.setOpaque(true);
//        ToolTipLabel.setBorder(j avax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));
        Content.add(ShortcutLabel);
        Content.add(ToolTipLabel);
        
        
    }
    
    public void ShowTip(String txt,String shorcut,int x,int y){
        if (!Global.getParameter("smartboard",false)){
            if (shorcut.equals("")){
                ShortcutLabel.setText("");
            }else{
                ShortcutLabel.setText(" "+shorcut+" ");
            };
            String mytxt=txt.replaceAll("\\+","&nbsp;<br>&nbsp;");
            ToolTipLabel.setText("<html>&nbsp;"+mytxt+"&nbsp;</html>");
            
            this.pack();
            
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            x=((x+this.getSize().width)>dim.width)?(dim.width-this.getSize().width):x;
            this.setLocation(x,y);
            this.setVisible(true);
        }
    }
    
    public void HideTip(){
        this.setVisible(false);
    }
    
    
    private class JToolTipContainer extends JPanel{
        public JToolTipContainer() {
            this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        }
    }
}
