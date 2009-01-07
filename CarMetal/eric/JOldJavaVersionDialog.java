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


import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;





public class JOldJavaVersionDialog extends Frame implements MouseListener,WindowListener{
    
    
    static public void main(String[] args) {
        new JOldJavaVersionDialog();
        
}
    
    public JOldJavaVersionDialog(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setResizable(false);
        this.addWindowListener(this);
        this.setBackground(new Color(255,255,255));
        
        ResourceBundle Strs =ResourceBundle.getBundle("eric/docs/JZirkelProperties");
        
        TextArea message=new TextArea(Strs.getString("java.old.message"),5,50,TextArea.SCROLLBARS_NONE);
        message.setEditable(false);
//        message.setSize(new Dimension(200,175));
        this.add(message);
        
        Panel pan=new Panel();
        pan.setSize(new Dimension(200,25));
        pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));
        
        Button quitbtn=new Button(Strs.getString("java.old.quit"));
        quitbtn.setName("quitbtn");
        Button gobtn=new Button(Strs.getString("java.old.go"));
        gobtn.setName("gobtn");
        quitbtn.addMouseListener(this);
        gobtn.addMouseListener(this);
        pan.add(quitbtn);
        pan.add(new Panel());
        pan.add(gobtn);
        
        
        this.add(pan);
        
        this.setSize(new Dimension(200,150));
        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        
        this.setVisible(true);
    }

    public void mouseClicked(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent e) {
        Button btn=(Button)e.getSource();
        if (btn.getName().equals("quitbtn")){
          System.exit(0);
        }else{
            JBrowserLauncher.openURL("http://www.java.com/");
        }
    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

   

    public void windowOpened(WindowEvent arg0) {
    }

    public void windowClosing(WindowEvent arg0) {
        System.exit(0);
    }

    public void windowClosed(WindowEvent arg0) {
    }

    public void windowIconified(WindowEvent arg0) {
    }

    public void windowDeiconified(WindowEvent arg0) {
    }

    public void windowActivated(WindowEvent arg0) {
    }

    public void windowDeactivated(WindowEvent arg0) {
        System.exit(0);
    }
    

}
