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
import java.awt.Frame;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import rene.zirkel.ZirkelFrame;


public class JFileSaveDialog extends JDialog implements PropertyChangeListener {
    private JCheckBox restrict;
    private JFileChooser jfc;
    JZirkelFrame JZF;
    ZirkelFrame ZF;
    JNodePopup JNP;
    public JFileSaveDialog(JZirkelFrame jzf,ZirkelFrame zf,JNodePopup jnp) {
        super((Frame)jzf, true);
        JZF=jzf;
        ZF=zf;
        JNP=jnp;
        Dimension d=new Dimension(500,500);
//        setMaximumSize(d);
//        setMinimumSize(d);
//        setPreferredSize(d);
        setSize(d);
        Point loc=JZF.getLocation();
        setLocation(loc.x+JZF.getSize().width/2-250,loc.y+JZF.getSize().height/2-250);
        initComponents();
    }
    
    private void initComponents() {
        jfc = new javax.swing.JFileChooser(JGlobals.getLastFilePath());
        restrict = new javax.swing.JCheckBox();
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jfc.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jfc.setAlignmentX(0.5F);
        jfc.setAlignmentY(0.0F);
        
        jfc.setAcceptAllFileFilterUsed(false);
        FileFilter ffilter = new JFileFilter(JZF.Strs.getString("filedialog.filefilter"),".zir");
        jfc.addChoosableFileFilter(ffilter);
        FileFilter fcfilter = new JFileFilter(JZF.Strs.getString("filedialog.compressedfilefilter"),".zirz");
        jfc.addChoosableFileFilter(fcfilter);
        jfc.setFileFilter(ffilter);

        
        jfc.setApproveButtonText(JZF.Strs.getString("filedialog.saveas"));
        jfc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (evt.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)){
                    dispose();
                }else if (evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)){
                    JFileFilter ff=(JFileFilter)jfc.getFileFilter();
                    JGlobals.setLastFilePath(jfc.getSelectedFile().getAbsolutePath());
                    JNP.dosave(jfc.getSelectedFile().getAbsolutePath(),restrict.isSelected(),ff.extension,true);
                    dispose();
                }
            }
        });
        jfc.addPropertyChangeListener(this);
        getContentPane().add(jfc);
        
        
        setrestrictText();
        restrict.setAlignmentY(0.0F);
        restrict.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        restrict.setMargin(new java.awt.Insets(0, 0, 0, 0));
        restrict.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            }
        });
        
        JLabel forcerestrict=new JLabel(JZF.Strs.getString("filedialog.restrictedmessage"));
        fixsize(forcerestrict,450,40);
        forcerestrict.setAlignmentY(0.0F);
        
        JPanel accessoryPanel = new JPanel();
        accessoryPanel.setLayout(new BoxLayout(accessoryPanel, BoxLayout.Y_AXIS));
        fixsize(accessoryPanel,480,80);
        accessoryPanel.setAlignmentX(0.5F);
        accessoryPanel.setAlignmentY(0.0F);
        accessoryPanel.setBorder(BorderFactory.createTitledBorder(JZF.Strs.getString("filedialog.options")));
        
        if (JZF.restrictedSession) {
            restrict.setSelected(true);
            accessoryPanel.add (forcerestrict);
        }else{
           accessoryPanel.add (restrict); 
        }
        
        getContentPane().add(accessoryPanel);
        
        pack();
    }
    
   private void setrestrictText(){
       JFileFilter ff=(JFileFilter)jfc.getFileFilter();
       restrict.setText(JZF.Strs.getString("filedialog.alsorestricted")+" (.r"+ff.extension+")");
       restrict.validate();
       restrict.repaint();
   }
    
   private void fixsize(JComponent cp,int w,int h){
        Dimension d=new Dimension(w,h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("fileFilterChanged")) setrestrictText();
    }
   
   
    
}
