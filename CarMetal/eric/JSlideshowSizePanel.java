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

/**
 *
 * @author  erichake
 */
public class JSlideshowSizePanel extends javax.swing.JPanel {
    public javax.swing.JTextField h;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox pm;
    public javax.swing.JTextField w;                
    private int ZCH,ZCW;
    private JZirkelFrame JZF;
    
    /** Creates new form NewJPanel */
    public JSlideshowSizePanel(JZirkelFrame jzf,int zcw,int zch) {
        ZCH=zch;ZCW=zcw;
        JZF=jzf;
        initComponents();
        w.setText(String.valueOf(ZCW));h.setText(String.valueOf(ZCH));
    }
                          
    private void initComponents() {
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pm = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        w = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        h = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        jPanel3.setMaximumSize(new java.awt.Dimension(25, 1));
        jPanel3.setMinimumSize(new java.awt.Dimension(25, 1));
        jPanel3.setPreferredSize(new java.awt.Dimension(25, 1));
        add(jPanel3);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 13));
        jLabel1.setText(JZF.Strs.getString("filedialog.openfolder.appletsize"));
        add(jLabel1);

        pm.setFont(new java.awt.Font("Dialog", 0, 12));
        pm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { JZF.Strs.getString("filedialog.openfolder.actualsize"), "640 x 480", "800 x 600", "1024 x 768", JZF.Strs.getString("filedialog.openfolder.other") }));
        pm.setMaximumSize(new java.awt.Dimension(120, 27));
        pm.setMinimumSize(new java.awt.Dimension(120, 27));
        pm.setPreferredSize(new java.awt.Dimension(120, 27));
        pm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pmItemStateChanged(evt);
            }
        });

        add(pm);

        jPanel2.setMaximumSize(new java.awt.Dimension(50, 1));
        jPanel2.setMinimumSize(new java.awt.Dimension(50, 1));
        jPanel2.setPreferredSize(new java.awt.Dimension(50, 1));
        add(jPanel2);

        w.setFont(new java.awt.Font("Dialog", 0, 12));
        w.setEnabled(false);
        w.setMaximumSize(new java.awt.Dimension(50, 20));
        w.setMinimumSize(new java.awt.Dimension(50, 20));
        w.setPreferredSize(new java.awt.Dimension(50, 20));
        add(w);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel3.setText(" x ");
        add(jLabel3);

        h.setFont(new java.awt.Font("Dialog", 0, 12));
        h.setEnabled(false);
        h.setMaximumSize(new java.awt.Dimension(50, 20));
        h.setMinimumSize(new java.awt.Dimension(50, 20));
        h.setPreferredSize(new java.awt.Dimension(50, 20));
        add(h);

        
        add(jPanel1);

    }
    
    public boolean isActualSize(){
        return (pm.getSelectedIndex()==0);
    }

    private void pmItemStateChanged(java.awt.event.ItemEvent evt) { 
         if (pm.getSelectedIndex()==0) {
             w.setText(String.valueOf(ZCW));h.setText(String.valueOf(ZCH));
         } else if (pm.getSelectedIndex()==1) {
             w.setText("640");h.setText("480");
         } else if (pm.getSelectedIndex()==2) {
             w.setText("800");h.setText("600");
         } else if (pm.getSelectedIndex()==3) {
             w.setText("1024");h.setText("780");
         }
         w.setEnabled(pm.getSelectedIndex()==4);
         h.setEnabled(pm.getSelectedIndex()==4);
    }                                   
                     
    
}
