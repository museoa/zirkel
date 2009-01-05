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

import java.awt.Cursor;

/**
 *
 * @author  erichake
 */
public class JLicence extends javax.swing.JFrame {
    JZirkelFrame JZF;
    public void paint(java.awt.Graphics g){
        super.paint(g);
        java.awt.Dimension d = this.getSize();
        g.drawImage(JZF.JZT.getImage("backcontrols.gif"),0,0,d.width, d.height,this);
//        super.paint(g);
        mytxt.repaint();
        mylink.repaint();
    }
    
    /** Creates new form JLicence */
    public JLicence(JZirkelFrame jzf) {
        JZF=jzf;
        initComponents();
        mytxt.setText(jzf.Strs.getString("licence.txt"));
        mytxt.setOpaque(false);
        mylink.setText("<html><a href='http://www.gnu.org/licenses/gpl.txt'>http://www.gnu.org/licenses/gpl.txt</a></html>");
        mylink.setOpaque(false);
        mylink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        int x=jzf.getLocation().x+(jzf.getWidth()-this.getSize().width)/2;
        int y=jzf.getLocation().y+(jzf.getHeight()-this.getSize().height)/2;
        this.setLocation(x,y);
        this.setVisible(true);
    }
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">                          
    private void initComponents() {
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        mytxt = new javax.swing.JLabel();
        mylink = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Licence");
        setResizable(false);
        setUndecorated(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.X_AXIS));

        jPanel2.setMaximumSize(new java.awt.Dimension(320, 10));
        jPanel2.setMinimumSize(new java.awt.Dimension(320, 10));
        jPanel2.setPreferredSize(new java.awt.Dimension(320, 10));
        getContentPane().add(jPanel2);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setOpaque(false);
        mytxt.setBackground(new java.awt.Color(255, 255, 255));
        mytxt.setFont(new java.awt.Font("Dialog", 0, 12));
        mytxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mytxt.setText("txt");
        mytxt.setAlignmentX(0.5F);
        mytxt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mytxt.setMaximumSize(new java.awt.Dimension(300, 95));
        mytxt.setMinimumSize(new java.awt.Dimension(300, 95));
        mytxt.setPreferredSize(new java.awt.Dimension(300, 95));
        mytxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mytxtMousePressed(evt);
            }
        });

        jPanel1.add(mytxt);

        mylink.setBackground(new java.awt.Color(255, 255, 255));
        mylink.setFont(new java.awt.Font("Dialog", 0, 12));
        mylink.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mylink.setText("coucou");
        mylink.setAlignmentX(0.5F);
        mylink.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mylink.setMaximumSize(new java.awt.Dimension(300, 40));
        mylink.setMinimumSize(new java.awt.Dimension(300, 40));
        mylink.setOpaque(true);
        mylink.setPreferredSize(new java.awt.Dimension(300, 40));
        mylink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mylinkMousePressed(evt);
            }
        });

        jPanel1.add(mylink);

        getContentPane().add(jPanel1);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.X_AXIS));

        jPanel3.setMaximumSize(new java.awt.Dimension(320, 10));
        jPanel3.setMinimumSize(new java.awt.Dimension(320, 10));
        jPanel3.setPreferredSize(new java.awt.Dimension(320, 10));
        getContentPane().add(jPanel3);

        pack();
    }// </editor-fold>                        

    private void mylinkMousePressed(java.awt.event.MouseEvent evt) {                                    
JBrowserLauncher.openURL("http://www.gnu.org/licenses/gpl.txt");
this.dispose();// TODO add your handling code here:
    }                                   

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {                                       
this.dispose();// TODO add your handling code here:
    }                                      

    private void formMousePressed(java.awt.event.MouseEvent evt) {                                  
this.dispose();// TODO add your handling code here:
    }                                 

    private void mytxtMousePressed(java.awt.event.MouseEvent evt) {                                   
this.dispose();// TODO add your handling code here:
    }                                  
    
   
    
    // Variables declaration - do not modify                     
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel mylink;
    private javax.swing.JLabel mytxt;
    // End of variables declaration                   
    
}