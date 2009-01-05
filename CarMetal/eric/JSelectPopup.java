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
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import rene.util.MyVector;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ConstructionObject;

/**
 *
 * @author erichake
 */
// This class is only instanciated by JSelectPopup
// It is a "modal" popup for complex tools. Popup menu appear
// in a 1 pixel w/h modal JDialog : it seems it's in ZirkelCanvas, but it's not...

public class JSelectPopup extends JPopupMenu implements MouseListener,PopupMenuListener{
    ZirkelCanvas ZC;
    JSelectPopupDialog D;
    Vector V=new Vector();
    String CallerObject="RightClick";
    int sx=0;int sy=0;int sw=0;int sh=0;
    

    
    public JSelectPopup(ZirkelCanvas zc,JSelectPopupDialog d,MyVector v) {
        init(zc,v);
        D=d;
        this.addPopupMenuListener(this);
        show(D.Content,0,0);
        sx=this.getLocationOnScreen().x;
        sy=this.getLocationOnScreen().y;
        sw=this.getSize().width;
        sh=this.getSize().height;
        
        
    }
    
    
    
    public void init(ZirkelCanvas zc,MyVector v){
        ZC=zc;
        // A bit of a hack : need to know where was the calling method...
        // getStackTrace stores the whole history of caller methods
        StackTraceElement[]  trace = new Throwable().getStackTrace();
        for (int i=0;i<trace.length;i++){
            if (trace[i].getClassName().startsWith("rene.zirkel.tools")){
                String s=trace[i].getClassName();
                CallerObject=s.split("\\.")[3];
                break;
            }
        };
        String aa="";
        
        try {
            aa=JGlobals.Loc("selectpopup."+CallerObject);
        } catch(Exception e) {};
        JMenuItem m=new JMenuItem(aa+JGlobals.Loc("selectpopup.whatobject"));

        
        
        m.setBackground(Color.WHITE);
        m.setForeground(Color.DARK_GRAY);
        
        
        m.setFont(new Font("Dialog",3,12));
        m.setActionCommand("-1,false");
        
        m.setEnabled(false);
        m.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!(D==null)) D.setVisible(false);
            }
        });
        m.addMouseListener(this);
        this.add(m);
        
       
        
        for (int i=0;i<v.size();i++){
            ConstructionObject o=(ConstructionObject)v.elementAt(i);
            V.add(o);
            String tp=o.getName()+" : "+o.getText().split(" ")[0];
            m=new JMenuItem(tp);
            m.setForeground(o.getColor());
            m.setBackground(new Color(240,240,240));
            m.setFont(new Font("Dialog",1,12));
            
            m.setActionCommand(String.valueOf(i)+","+o.selected());
            m.setRolloverEnabled(true);
            
            m.addMouseListener(this);
            m.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    JMenuItem jm=(JMenuItem)event.getSource();
                    doaction(jm.getActionCommand(),event.getModifiers());
                }
            });
            this.add(m);
        };
        
    }
    
    
    
    
    
    public int row(String str){
        return Integer.parseInt(str.split(",")[0]);
    }
    
    public boolean sel(String str){
        String bl=str.split(",")[1].toLowerCase();
        return (bl.equals("true"));
    }
    
    public void doaction(String str,int modifier){
        ConstructionObject o=(ConstructionObject)V.elementAt(row(str));
        o.setSelected(sel(str));
        D.setConstructionObject(o);
        D.setVisible(false);
    }
    
    public void mouseClicked(MouseEvent e) {}
    
    public void mousePressed(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {}
    
    public void mouseEntered(MouseEvent e) {
        JMenuItem jm=(JMenuItem)e.getSource();
        int i= row(jm.getActionCommand());
        if ((i>-1)&&(!sel(jm.getActionCommand()))) {
            ConstructionObject o=(ConstructionObject)V.elementAt(i);
            o.setSelected(true);
            ZC.repaint();
        }
    }
    
    public void mouseExited(MouseEvent e) {
        JMenuItem jm=(JMenuItem)e.getSource();
        int i= row(jm.getActionCommand());
        if ((i>-1)&&(!sel(jm.getActionCommand()))) {
            ConstructionObject o=(ConstructionObject)V.elementAt(i);
            o.setSelected(false);
            ZC.repaint();
        }
        int x=MouseInfo.getPointerInfo().getLocation().x;
            int y=MouseInfo.getPointerInfo().getLocation().y;
            
            if ((x<sx)||(x>sx+sw)||(y<sy)||(y>sy+sh)) {
                D.setVisible(false);
            }
    }
//    
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
    
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
    
    public void popupMenuCanceled(PopupMenuEvent e) {
        D.setVisible(false);
    }
    
    
    
}
