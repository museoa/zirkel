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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import rene.gui.ChoiceAction;
import rene.zirkel.Zirkel;
import rene.zirkel.construction.ConstructionDisplayPanel;
import rene.zirkel.macro.Macro;

public class JZirkelFrameContent extends JPanel{
    rene.zirkel.ZirkelFrame ZF;
    JZirkelFrame JZF;
    JZirkelFrameContent JZFC;
    JZleft left;
    JZhistory history;
    JZmacros macros;
    JZhelp help;
    JZcenter center;
    JZright right;
    int leftpanelwidth=180;
    JZleftpanel leftpanel=null; //represent null xor JZmacros xor JZhistory xor JZhelp
    
    public JZirkelFrameContent(rene.zirkel.ZirkelFrame zf,JZirkelFrame jzf) {
        ZF=zf;
        JZF=jzf;
        JZFC=this;
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
        left=new JZleft();
        history=new JZhistory();
        macros=new JZmacros();
        help=new JZhelp();
        center=new JZcenter();
        right=new JZright();
        this.add(left);
        this.add(history);
        this.add(macros);
        this.add(help);
        this.add(center);
        this.add(right);
    }
    

    
    public void paintComponent(Graphics g){
        
    }
    
    public void paintImmediately(){
        paintImmediately(0, 0, getWidth(), getHeight());
    }
    
    public void refreshlanguage(){
        
        leftpanel=null;
        
        this.remove(macros);
        macros=new JZmacros();
        this.add(macros,2);

        
    }
    
    private void fixsize(JComponent cp,Dimension d){
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }
    
    private void fixsize(JComponent cp,int w,int h){
        Dimension d=new Dimension(w,h);
        fixsize(cp,d);
    }
    
    private int getLeftPanelCode(){
        int i=0;
        if (leftpanel!=null){
            if (leftpanel.equals(history)) i=1;
            else if (leftpanel.equals(macros)) i=2;
            else if (leftpanel.equals(help)) i=3;
        }
        
        return i;
    }
    
    public void ShowMacroPanel(){
        if (getLeftPanelCode()!=2) {
            ShowLeftPanel(2);
            JZF.ZContent.paintImmediately(0,0,1000,1000);
        }
    }
    
    public void ShowPropertiesPanel(){
        if (getLeftPanelCode()!=4) {
            ShowLeftPanel(4);
        }
    }
    
    public void HidePropertiesPanel(){
        if (getLeftPanelCode()==4) {
            ShowLeftPanel(4);
        }
    }
    
    public boolean isPropertiesPanel(){
        return (getLeftPanelCode()==4);
    }
    
    public boolean isMacroPanel(){
        return (getLeftPanelCode()==2);
    }
    
    
    public void ShowLeftPanel(int i){
        /*  i=0 means no panel
            i=1 for history panel
            i=2 for macro panel
            i=3 for help panel
            i=4 for object properties panel
         */
        
        
        history.seticonselected(false);
        macros.seticonselected(false);
        help.seticonselected(false);
        JZF.GeneralMenuBar.historyitem.setSelected(false);
        JZF.GeneralMenuBar.macrositem.setSelected(false);
        JZF.GeneralMenuBar.helpitem.setSelected(false);
        JZF.GeneralMenuBar.propertiesitem.setSelected(false);
//        JZF.ZF.removeInfo();
        help.hideme();
        ZF.ZC.CDP.setVisible(false);
        if (i==getLeftPanelCode()){
            leftpanel=null;
        }else{
            switch (i){
                case 0:
                    
                    leftpanel=null;
                    break;
                case 1:
                    leftpanel=history;
                    history.paletteicon.isSelected=true;
                    JZF.GeneralMenuBar.historyitem.setSelected(true);
//                    leftpanelwidth=(leftpanelwidth<220)?220:leftpanelwidth;
                    leftpanelwidth=220;
                    ZF.ZC.CDP.setVisible(true);
                    break;
                case 2:
                    leftpanel=macros;
                    macros.paletteicon.isSelected=true;
                    JZF.GeneralMenuBar.macrositem.setSelected(true);
//                    leftpanelwidth=(leftpanelwidth<220)?220:leftpanelwidth;
                    leftpanelwidth=220;
                    break;
                case 3:
                    help.showme();
                    leftpanel=help;
//                    leftpanelwidth=(leftpanelwidth<270)?270:leftpanelwidth;
                    leftpanelwidth=250;
                    help.paletteicon.isSelected=true;
                    JZF.GeneralMenuBar.helpitem.setSelected(true);
//                    JZF.ZF.info();
                    break;
            }
        }
        
        
        JZF.ResizeAll();
        JZF.ZContent.validate();
        JZF.ZContent.repaint();
        history.repainticon();
        macros.repainticon();
        help.repainticon();
        ZF.ZC.recompute();
        ZF.ZC.validate();
        ZF.ZC.repaint();
        ZF.ZC.requestFocus();
    }
    
    public class JZleftpanel extends JPanel{
        JZverticalseparator vertseparator;
        JPanel content,wholecontent;
        JZleftpanelTitle title;
        JIcon paletteicon=null;
        
        public JZleftpanel(){
            wholecontent=new JPanel();
            title=new JZleftpanelTitle();
            vertseparator=new JZverticalseparator();
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
            wholecontent.setLayout(new javax.swing.BoxLayout(wholecontent, javax.swing.BoxLayout.Y_AXIS));
            wholecontent.add(title);
            this.add(wholecontent);
            this.add(vertseparator);
        }
        
        public void paintComponents(Graphics g){
            
        }
        
        public String Loc(String s){
            return JZF.Strs.getString("leftpanel."+s);
        }
        
        public void SetJIcon(JIcon myicon){
            paletteicon=myicon;
        }
        
        public void seticonselected(boolean bool){
            if (paletteicon!=null) paletteicon.isSelected=bool;
        }
        
        public void repainticon(){
            if (paletteicon!=null) paletteicon.repaint();
        }
        
        
    }
    
    public class JZleftpanelTitle extends JPanel{
        
        public JLabel txt=new JLabel();
        
        
        
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
            java.awt.Dimension d = this.getSize();
            g.drawImage(JZF.JZT.getImage("backcontrols.gif"),0,0,d.width, d.height,this);
        }
        
        
        
        public JZleftpanelTitle(){
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
            this.setAlignmentX(0.0F);
            this.setAlignmentY(0.0F);
            txt.setForeground(new Color(50,50,50));
            txt.setFont(new Font(JGlobals.GlobalFont,0,11));
            txt.setVerticalAlignment(SwingConstants.TOP);
            JPanel marginleft=new JPanel();
            fixsize(marginleft,5,1);marginleft.setOpaque(false);
            this.add(marginleft);
            this.add(txt);
            JPanel spacer=new JPanel();spacer.setOpaque(false);
            this.add(spacer);
            JButton mybtn=new JButton();
            mybtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/icons/palette/Pclose.png")));
            mybtn.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/icons/palette/Pcloseover.png")));
            
            mybtn.addMouseListener(new MouseAdapter(){
                public void mousePressed(MouseEvent e){
                    ShowLeftPanel(0);
                }
            });
            mybtn.setOpaque(false);
            mybtn.setBorder(BorderFactory.createEmptyBorder());
            mybtn.setContentAreaFilled(false);
            this.add(mybtn);
            
            JPanel marginright=new JPanel();
            fixsize(marginright,5,1);marginright.setOpaque(false);
            this.add(marginright);
        }
    }
    
    public class JZmacros extends JZleftpanel{
        JMacrosList myJML;
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
        }
        
        public JZmacros(){
            myJML=new JMacrosList(ZF,JZF);
            content=myJML;
            content.setAlignmentX(0.0f);
            content.setAlignmentY(0.0f);
            wholecontent.add(content);
            title.txt.setText(Loc("macros"));
            
        }
        
        
    }
    
    
    
    public class JZhelp extends JZleftpanel{
//        ExtendedViewer V;
        
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
        }
        
        public JZhelp(){
            JZF.ZF.info();
            content=JZF.ZF.InfoPanel;
            content.setAlignmentX(0.0F);
            content.setAlignmentY(0.0F);
            wholecontent.add(content);
            wholecontent.setBackground(Color.white);
            content.setBackground(Color.white);
            title.txt.setText(Loc("help"));
        }
        
        public void showme(){
            JZF.ZF.setinfo("start");
        }
        
        public void hideme(){
//            JZF.ZF.removeInfo();
//            content.removeAll();
//            if (wholecontent.getComponentCount()>2) wholecontent.remove(2);
        }
        
        
        
        
        public void setMacroHelp(Macro m){

//            if (JZFC.getLeftPanelCode()==3) {
//                String lne="";
//
//                V.setText("");
//                if (m.Comment!=""){
//                    V.appendLine("***********************");
//                    V.appendLine(m.Comment);
//                    V.appendLine("***********************");
//                    V.newLine();
//                    V.newLine();
//                };
//                V.appendLine("*** Initial objects :");
//                V.newLine();
//                int num=1;
//                for (int i=0;i<m.Params.length;i++){
//                    boolean fix=m.Prompts[i].equals("="+m.Params[i].getName());
//                    lne=(fix)?"fixed object : ":"";
//
//                    V.appendLine(String.valueOf(num)+") Name: "+m.Params[i].getName()+" ("+lne+JZF.Strs.getString(m.Params[i].getClass().getName())+")");
//                    if (!(fix)) V.appendLine("Prompt: "+m.Prompts[i]);
//                    V.newLine();
//                    num++;
//                };
//                for (int i=0;i<m.PromptFor.length;i++){
//                    V.appendLine(String.valueOf(num)+") Name: "+m.PromptFor[i]+" (Numerical input)");
//                    V.appendLine("Prompt: "+m.PromptName[i]);
//                    V.newLine();
//                    num++;
//                }
//
//                V.update();
//            }
        }
        
        
    }
    
    
    
    public class JZhistory extends JZleftpanel{
        JZCDP myCDP;
        JComboBox mynewmenu;
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
        }
        
        public JZhistory(){
//            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
//            vertseparator=new JZverticalseparator();
            myCDP=new JZCDP();
//            content.add(myCDP);
            content=myCDP;
            content.setAlignmentX(0.0f);
            content.setAlignmentY(0.0f);
            ZF.ZC.CDP.setVisible(false);
            ZF.ZC.CDP.V.L.setFocusable(false);
            ZF.ZC.CDP.V.Vertical.setFocusable(false);
            ZF.ZC.CDP.V.setBackground(Color.white);
            ZF.ZC.CDP.V.L.setBackground(Color.white);
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);
            JPanel mypanel=(JPanel)myCDP.getComponent(myCDP.getComponentCount()-1);
            
            JPanel mypopupback=(JPanel)mypanel.getComponent(0);
            mypopupback.setBackground(Color.white);
            JPanel mypopup=(JPanel)mypopupback.getComponent(0);
            mypopup.setBackground(Color.white);
            
            ChoiceAction mymenu=(ChoiceAction)mypopup.getComponent(0);
            mynewmenu=new JComboBox();
            for (int i=0;i<ConstructionDisplayPanel.Choices.length;i++){
                mynewmenu.addItem(Zirkel.name("constructiondisplay."+ConstructionDisplayPanel.Choices[i]));
            };
            
            
            mynewmenu.addItemListener(new ItemAdapter());
            mynewmenu.setSelectedIndex(ZF.ZC.CDP.State);
            mypopup.add(mynewmenu);
            mypopup.remove(mymenu);
            
            mynewmenu.setFocusable(false);
            
            wholecontent.add(content);
//            this.add(vertseparator);
            title.txt.setText(Loc("history"));
        }
        
        class ItemAdapter implements ItemListener {
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange()==ItemEvent.SELECTED){
                    ZF.ZC.CDP.State=mynewmenu.getSelectedIndex();
                    ZF.ZC.CDP.reload();
                }
            }
        }
        
        
    }
    
    
    public class JZCDP extends JPanel{
        
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
        }
        
        public JZCDP(){
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
            
            if (ZF.ZC.CDP==null){
                ZF.ZC.CDP=new ConstructionDisplayPanel(ZF.ZC);
                ZF.ZC.reloadCD();
            };
            
            
            ZF.ZC.CDP.V.remove(ZF.ZC.CDP.V.Horizontal);
            this.add(ZF.ZC.CDP);
        }
    }
    
    
    public class JZverticalseparator extends JPanel{
        
//        private ImageIcon MoveIcon=eric.JZF.JZT.getIcon("separatormover.gif"));
        int x=-1;int y=-1;boolean MouseOn=false;
        
        
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
            java.awt.Dimension d = this.getSize();
            g.drawImage(JZF.JZT.getImage("verticalseparator.png"),0,0,d.width,d.height,this);
        }
        
        public JZverticalseparator(){
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
//            JButton btn=new JButton();
//            btn.setIcon(MoveIcon);
//            btn.setBorder(BorderFactory.createEmptyBorder());
//            btn.setAlignmentX(0.5F);
//            JPanel jp1=new JPanel();jp1.setOpaque(false);jp1.setMinimumSize(new Dimension(7,0));
//            this.add(jp1);
//            this.add(btn);
//            JPanel jp2=new JPanel();jp2.setOpaque(false);jp2.setMinimumSize(new Dimension(7,0));
//            this.add(jp2);
//
//            btn.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
//            btn.setFocusable(false);
            
            this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
            this.addMouseListener(new MouseAdapter(){
                public void mouseReleased(MouseEvent e){
                    MouseOn=false;
                    x = e.getX();
                    JZF.ZF.ZC.recompute();
                    JZF.ZF.ZC.validate();
                    JZF.ZF.ZC.repaint();
                    
                }
                public void mousePressed(MouseEvent e){
                    MouseOn=true;
                    x = e.getX();
                }
                public void mouseClicked(MouseEvent e){
                    
                }
            });
            
            this.addMouseMotionListener(new MouseMotionAdapter(){
                public void mouseDragged(MouseEvent e){
                    int xx=e.getX()-x;
                    if (((JZFC.leftpanelwidth+xx)>150)&&((JZFC.leftpanelwidth+xx)<JZF.getSize().width-100)){
                        JZFC.leftpanelwidth+=xx;
                        JZF.ResizeAll();
                        JZF.ZContent.validate();
                        JZF.ZContent.repaint();
                    }
                    
                    
                    
                    
                    
                }
            });
        }
        
    }
    
    
    
    public class JZcenter extends JPanel{
        JZcanvas figure;
        public JZcanvasBorder b1,b2;
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
        }
        
        public JZcenter(){
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
            b1=new JZcanvasBorder();
            figure=new JZcanvas();
            b2=new JZcanvasBorder();
            this.add(b1);
            this.add(figure);
            this.add(b2);
        }
    }
    
    public class JZcanvasBorder extends JPanel{
        
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
            java.awt.Dimension d = this.getSize();
            g.drawImage(JZF.JZT.getImage("zcborder.gif"),0,0,d.width, d.height,this);
        }
        
        
        public JZcanvasBorder(){
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
        }
    }
    
    
    
    public class JZcanvas extends JPanel{
        
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
        }
        
        public JZcanvas(){
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
            this.add(ZF.ZC);
        }
    }
    
    public class JZleft extends JPanel{
        
        
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
            java.awt.Dimension d = this.getSize();
            g.drawImage(JZF.JZT.getImage("leftcanvas.png"),0,0,d.width, d.height,this);
            if (JZF.construct) return;
            if (!JZF.equals(JMacrosTools.CurrentJZF)){JZF.JZT.setDisable(g,d);};
        }
        
//        public void paint(java.awt.Graphics g){
//            if (g==null) return;
//            java.awt.Dimension d = this.getSize();
//            g.drawImage(JZF.JZT.getImage("leftcanvas.png"),0,0,d.width, d.height,this);
//            super.paint(g);
//        }
        
        
        public JZleft(){
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
//            this.addMouseListener(JZF);
//        this.addMouseMotionListener(JZF);
        }
        
    }
    
    public class JZright extends JPanel{
        
        
        public void paintComponent(java.awt.Graphics g){
//            super.paintComponent(g);
            java.awt.Dimension d = this.getSize();
            g.drawImage(JZF.JZT.getImage("rightcanvas.png"),0,0,d.width, d.height,this);
            if (JZF.construct) return;
            if (!JZF.equals(JMacrosTools.CurrentJZF)){JZF.JZT.setDisable(g,d);};
        }
        
//        public void paint(java.awt.Graphics g){
//            if (g==null) return;
//            java.awt.Dimension d = this.getSize();
//            g.drawImage(JZF.JZT.getImage("rightcanvas.png"),0,0,d.width, d.height,this);
//            super.paint(g);
//        }
        
        public JZright(){
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
//            this.addMouseListener(JZF);
//        this.addMouseMotionListener(JZF);
        }
        
    }
    
}
