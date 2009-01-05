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
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JSlideshowSaveDialog extends JDialog implements WindowListener,PropertyChangeListener{
    JZirkelFrame JZF;
    JFileChooser JFC;
    JCheckBox JCBK;
    JLabel TARGET;
    JPanel Content;
    JSlideshowSizePanel SIZE;
    File DIR=null;
    boolean OK=false;
    
    public JSlideshowSaveDialog(JFrame parent) {
        super(parent);
        JZF=(JZirkelFrame) parent;
        this.setModal(true);
        this.setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(JZF.Strs.getString("filedialog.openfolder.title"));
        this.addWindowListener(this);
        Content=new JPanel();
        Content.setLayout(new BoxLayout(Content, BoxLayout.Y_AXIS));
        this.setContentPane(Content);
        
        JFC = new JFileChooser(JGlobals.getLastFilePath());
        JFC.addPropertyChangeListener(this);
        JFC.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
        JFC.setControlButtonsAreShown(false);
        
        JFC.setApproveButtonText(JZF.Strs.getString("filedialog.openfolder.approve"));
//        JFC.setAcceptAllFileFilterUsed(false);
        
        JFC.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        
        JFC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OK=evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION);
                setVisible(false);
            }
        });
        JFC.setAlignmentX(0.5F);
        JFC.setAlignmentY(0.0F);
        fixsize(JFC,480,315);
        
        JCBK=new JCheckBox();
        JCBK.setText(JZF.Strs.getString("filedialog.openfolder.saveicons"));
        
        SIZE=new JSlideshowSizePanel(JZF,JZF.ZF.ZC.getSize().width,JZF.ZF.ZC.getSize().height);
        
        
        JPanel accessoryPanel = new JPanel();
        accessoryPanel.setLayout(new BoxLayout(accessoryPanel, BoxLayout.Y_AXIS));
        fixsize(accessoryPanel,480,100);
        accessoryPanel.setAlignmentX(0.5F);
        accessoryPanel.setAlignmentY(0.0F);
        accessoryPanel.setBorder(BorderFactory.createTitledBorder(JZF.Strs.getString("filedialog.options")));
        
        JCBK.setAlignmentX(0.0F);JCBK.setAlignmentY(0.0F);
        SIZE.setAlignmentX(0.0F);SIZE.setAlignmentY(0.0F);
        accessoryPanel.add(JCBK);
        accessoryPanel.add(SIZE);
        
        
        TARGET=new JLabel(JZF.Strs.getString("filedialog.openfolder.selected")+lastFolders());
        TARGET.setFont(new Font("Dialog",0,12));
        TARGET.setForeground(new Color(100,100,100));
        TARGET.setVerticalTextPosition(SwingConstants.CENTER);
        TARGET.setHorizontalAlignment(SwingConstants.CENTER);
        fixsize(TARGET,250,30);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        fixsize(buttonsPanel,480,30);
        buttonsPanel.setAlignmentX(0.5F);
        buttonsPanel.setAlignmentY(0.0F);
        JButton cancelBtn=new JButton(JZF.Strs.getString("filedialog.openfolder.cancel"));
        JButton okBtn=new JButton("Ok");
        cancelBtn.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    setVisible(false);
                }
            });
        okBtn.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    OK=true;
                    setVisible(false);
                }
            });
        buttonsPanel.add(new JPanel());
        buttonsPanel.add(TARGET);
        buttonsPanel.add(new JPanel());
        buttonsPanel.add(cancelBtn);
        buttonsPanel.add(okBtn);
        
        Content.add(JFC);
        Content.add(accessoryPanel);
        Content.add(buttonsPanel);
        
        fixsize(Content,500,450);
        
        Point loc=JZF.getLocation();
        setLocation(loc.x+JZF.getSize().width/2-250,loc.y+JZF.getSize().height/2-250);
        
        
        
        this.pack();
        
        this.setVisible(true);
    }
    
     private void fixsize(JComponent cp,int w,int h){
        Dimension d=new Dimension(w,h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
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
    
    public String lastFolders(){
        if (JFC.getSelectedFile()==null) DIR=JFC.getCurrentDirectory();
        else DIR=JFC.getSelectedFile();
        String dirpath=DIR.getAbsolutePath();
        String mystr=(dirpath.length()<21)?dirpath:"..."+dirpath.substring(dirpath.length()-20);
        return mystr;
    }
            
    
    public void windowOpened(WindowEvent e) {
    }
    public void windowActivated(WindowEvent e) {
    }
    public void windowClosing(WindowEvent e) {setVisible(false);}
    
    public void windowClosed(WindowEvent e) {}
    
    public void windowIconified(WindowEvent e) {}
    
    public void windowDeiconified(WindowEvent e) {}
    
    public void windowDeactivated(WindowEvent e) {}

    public void propertyChange(PropertyChangeEvent evt) {
        if ((evt.getPropertyName().equals("directoryChanged"))||(evt.getPropertyName().equals("SelectedFileChangedProperty"))) {
            TARGET.setText(JZF.Strs.getString("filedialog.openfolder.selected")+lastFolders());
        }
    }
    
}
