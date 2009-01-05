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

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import rene.gui.Global;

/**
 *
 * @author  erichake
 */
public class JGlobalPreferencesDlog extends javax.swing.JFrame {
    
    /** Creates new form JGlobalPreferencesDlog */
    public JGlobalPreferencesDlog() {
        initComponents();
        tabs.setTitleAt(0,Loc("sizes"));
        tabs.setTitleAt(1,Loc("colors"));
        tabs.setTitleAt(2,Loc("others"));
        undock.setText(Loc("others.undock"));
        JGlobalPreferences.savePreferences();
        sizes.add(Vspacer(20));
        sizes.add(new JGlobalPreferencesCursor("prefs.minpointsize",Loc("sizes.minpointsize"),1,9,3));
        sizes.add(new JGlobalPreferencesCursor("prefs.minlinesize",Loc("sizes.minlinesize"),1,9,1));
        sizes.add(new JGlobalPreferencesCursor("prefs.arrowsize",Loc("sizes.arrowsize"),3,50,15));
        sizes.add(new JGlobalPreferencesCursor("prefs.minfontsize",Loc("sizes.minfontsize"),1,64,12));
        sizes.add(Vspacer(20));
        sizes.add(new JGlobalPreferencesCursor("prefs.digits.lengths",Loc("sizes.digits.lengths"),0,12,5));
        sizes.add(new JGlobalPreferencesCursor("prefs.digits.edit",Loc("sizes.digits.edit"),0,12,5));
        sizes.add(new JGlobalPreferencesCursor("prefs.digits.angles",Loc("sizes.digits.angles"),0,12,0));
        colors.add(Vspacer(20));
        colors.add(new JGlobalPreferencesColor());
        undock.setSelected(Global.getParameter("prefs.undockpalette",false));
        setVisible(true);
    }
    static private String Loc(String s){
        return JMacrosTools.CurrentJZF.Strs.getString("menu.special.options."+s);
    }
    static private JPanel Vspacer(int h){
        JPanel mysep=new JPanel();
        mysep.setOpaque(false);
        fixsize(mysep,new Dimension(1,h));
        return mysep;
    }
    
    static private void fixsize(JComponent cp,Dimension d){
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        tabs = new javax.swing.JTabbedPane();
        sizes = new javax.swing.JPanel();
        colors = new javax.swing.JPanel();
        other = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        undock = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");
        setAlwaysOnTop(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        tabs.setMaximumSize(new java.awt.Dimension(330, 360));
        tabs.setMinimumSize(new java.awt.Dimension(330, 360));
        tabs.setPreferredSize(new java.awt.Dimension(330, 360));
        sizes.setLayout(new javax.swing.BoxLayout(sizes, javax.swing.BoxLayout.Y_AXIS));

        sizes.setOpaque(false);
        tabs.addTab("Sizes", sizes);

        colors.setLayout(new javax.swing.BoxLayout(colors, javax.swing.BoxLayout.Y_AXIS));

        colors.setOpaque(false);
        tabs.addTab("Colors", colors);

        other.setLayout(new javax.swing.BoxLayout(other, javax.swing.BoxLayout.Y_AXIS));

        other.setOpaque(false);
        jPanel2.setEnabled(false);
        jPanel2.setFocusable(false);
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 20));
        jPanel2.setMinimumSize(new java.awt.Dimension(10, 20));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(10, 20));
        other.add(jPanel2);

        undock.setText("Accept undocked palette");
        undock.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        undock.setMargin(new java.awt.Insets(0, 0, 0, 0));
        undock.setOpaque(false);
        undock.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                undockItemStateChanged(evt);
            }
        });

        other.add(undock);

        tabs.addTab("Other", other);

        getContentPane().add(tabs);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(330, 40));
        jPanel1.setMinimumSize(new java.awt.Dimension(330, 40));
        jPanel1.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.X_AXIS));

        jPanel3.setMaximumSize(new java.awt.Dimension(247, 40));
        jPanel3.setMinimumSize(new java.awt.Dimension(247, 40));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(247, 40));
        jPanel1.add(jPanel3);

        jButton2.setText("close");
        jButton2.setAlignmentX(0.5F);
        jButton2.setFocusPainted(false);
        jButton2.setFocusable(false);
        jButton2.setMaximumSize(new java.awt.Dimension(75, 35));
        jButton2.setMinimumSize(new java.awt.Dimension(75, 35));
        jButton2.setPreferredSize(new java.awt.Dimension(75, 35));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jPanel1.add(jButton2);

        getContentPane().add(jPanel1);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-330)/2, (screenSize.height-422)/2, 330, 422);
    }// </editor-fold>//GEN-END:initComponents
    
    private void undockItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_undockItemStateChanged
        Global.setParameter("prefs.undockpalette",(evt.getStateChange()==ItemEvent.SELECTED));
    }//GEN-LAST:event_undockItemStateChanged
    
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
        JGlobalPreferences.initPreferences();// TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed
    
    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JGlobalPreferencesDlog().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel colors;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel other;
    private javax.swing.JPanel sizes;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JCheckBox undock;
    // End of variables declaration//GEN-END:variables
    
}
