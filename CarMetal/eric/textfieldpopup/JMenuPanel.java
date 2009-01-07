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
 
 
 package eric.textfieldpopup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import eric.JGlobals;

/**
 *
 * @author erichake
 */
public class JMenuPanel extends JPanel implements MouseListener{
   JPopupMenu MEN=null;
   int iconwidth=20;
   int iconheight=20;
    JComponent JTF;
    public JMenuPanel(JPopupMenu men){
        MEN=men;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setAlignmentY(0f);
        setOpaque(true);
        setFocusable(false);
        setBackground(new Color(250,250,250));
    }
    static void fixsize(JComponent cp, int w, int h) {
        Dimension d = new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }
    
    public JButton getJButton(String s){
        JButton mybtn=new JButton(s);
        mybtn.setBorder(BorderFactory.createEmptyBorder());
        mybtn.setBorderPainted(false);
        mybtn.setFocusPainted(false);
        mybtn.setFocusable(false);
        mybtn.setBackground(new Color(228,222,255));
        mybtn.setOpaque(false);
        mybtn.setContentAreaFilled(false);
        mybtn.addMouseListener(this);
        mybtn.setFont(new java.awt.Font(JGlobals.GlobalFont, 1, 11));
        mybtn.setForeground(new Color(20,20,20));
        fixsize(mybtn,iconwidth,iconheight);
        return mybtn;
    }
    
    JPanel getnewline(){
        JPanel line=new JPanel();
        line.setLayout(new BoxLayout(line,BoxLayout.X_AXIS));
        line.setAlignmentX(0f);
        line.setOpaque(false);
        return line;
    }
    
    JPanel getnewcol(){
        JPanel col=new JPanel();
        col.setLayout(new BoxLayout(col,BoxLayout.Y_AXIS));
        col.setAlignmentY(0f);
        col.setOpaque(true);
        col.setBackground(new Color(250,250,250));
        return col;
    }
    
    public void doAction(JButton mybtn){
        String s=mybtn.getText();
        
        mybtn.setOpaque(false);
        mybtn.setContentAreaFilled(false);
        if (JTF!=null){
            JTextComponent jt=(JTextComponent)JTF;
            String mytxt=jt.getText().substring(0, jt.getSelectionStart());
            mytxt+=s;
            int car=mytxt.length();
            mytxt+=jt.getText().substring(jt.getSelectionEnd());
            jt.setText(mytxt);
            jt.setCaretPosition(car);
            MEN.setVisible(false);
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        JButton mybtn=(JButton)e.getComponent();
        doAction(mybtn);
    }
    
    public void mousePressed(MouseEvent e) {

    }
    
    public void mouseReleased(MouseEvent e) {

    }
    
    public void mouseEntered(MouseEvent e) {
        if (e.getSource()!=null) {
            JButton btn=(JButton)e.getSource();
            btn.setBackground(new Color(171,191,231));
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
        };
    }
    
    public void mouseExited(MouseEvent e) {
        if (e.getSource()!=null) {
            JButton btn=(JButton)e.getSource();
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
        };
    }

}
