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

import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import rene.util.MyVector;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ConstructionObject;



// JDialog for MODAL popup
// It is a "modal" popup for ZirkelCanvas "select" methods. Popup menu appear
// in a 1 pixel w/h modal JDialog : it seems it's in ZirkelCanvas, but it's not...
// It replaces the Ren� Dialog box which appears when you click on more than one object
// It must be modal because it have to interrupt the code, waiting a user choice
public class JSelectPopupDialog extends JDialog implements WindowListener{
    MyVector V;
    ZirkelCanvas ZC;
    JPanel Content;
    ConstructionObject O=null;
    int WindowEventCount=0;
    
    public JSelectPopupDialog(Frame parent,ZirkelCanvas zc,MyVector v) {
        super(parent);
        V=v;
        ZC=zc;
        this.setModal(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("");
        this.setUndecorated(true);
        this.addWindowListener(this);
        int x=MouseInfo.getPointerInfo().getLocation().x;
        int y=MouseInfo.getPointerInfo().getLocation().y;
        
        // another Linux patch (I get tired) !
        if (System.getProperty("os.name").equals("Linux")) {
            this.setLocation(x-2,y-2);
            this.setSize(4,4);
        }else{
            this.setLocation(x+2,y+2);
            this.setSize(1,1);
        }
        
        
        
        Content=new JPanel();
        this.setContentPane(Content);
        this.setVisible(true);
        this.toFront();
    }
    
    public void setConstructionObject(ConstructionObject o){
        O=o;
    }
    
    public ConstructionObject getConstructionObject(){
        return O;
    }
    
    public void doclose() {
        setVisible(false);
        Thread t=new Thread() {
            public void run() {
                dispose();
            }
        };
        t.start();
    }
    
    // In Linux (tested on ubuntu), windowActivated Event seems
    // to occurs after windowOpened.
    // In windows and mac OS it's the opposite.
    // So I display the popup after the last event :
    public void showPopup(){
        WindowEventCount++;
        if (WindowEventCount==2) {
            JSelectPopup jp=new JSelectPopup(ZC,this,V);
        };
    }
    
    public void windowOpened(WindowEvent e) {
        showPopup();
    }
    public void windowActivated(WindowEvent e) {
        showPopup();
    }
    public void windowClosing(WindowEvent e) {}
    
    public void windowClosed(WindowEvent e) {}
    
    public void windowIconified(WindowEvent e) {}
    
    public void windowDeiconified(WindowEvent e) {}
    
    public void windowDeactivated(WindowEvent e) {
    }
    
}
