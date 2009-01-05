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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import rene.gui.MyMenu;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.macro.Macro;
import rene.zirkel.macro.MacroItem;
import rene.zirkel.objects.ConstructionObject;


// Toute ce qui touche ˆ l'arbre de macros et ˆ sa gestion :
public class JMacrosList extends JPanel {

    private ImageIcon JTreefoldclosed;
    private ImageIcon JTreefoldopened;
    private ImageIcon[] JTreeleaf;
    CTree MacrosTree;
    JDefaultMutableTreeNode MacroTreeTopNode = new JDefaultMutableTreeNode("Macros");
    ;
    rene.zirkel.ZirkelFrame ZF;
    public JZirkelFrame JZF;
    JScrollPane jscrolls;
    JControls controls;
    public Jcreatemacro createmacropanel;

    public JMacrosList(rene.zirkel.ZirkelFrame zf, JZirkelFrame jzf) {

        ZF = zf;
        JZF = jzf;
        JTreefoldclosed = JZF.JZT.getIcon("JTreefoldclosed.gif");
        JTreefoldopened = JZF.JZT.getIcon("JTreefoldopened.gif");
        JTreeleaf = new ImageIcon[4];
        JTreeleaf[0] = JZF.JZT.getIcon("JTreeleaf_0.gif");
        JTreeleaf[1] = JZF.JZT.getIcon("JTreeleaf_1.gif");
        JTreeleaf[2] = JZF.JZT.getIcon("JTreeleaf_2.gif");
        JTreeleaf[3] = JZF.JZT.getIcon("JTreeleaf_3.gif");
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

//        uncomment this line to obtain gray conection lines between leaves :
//        UIManager.put("Tree.hash",new ColorUIResource(Color.lightGray));


        MacroTreeTopNode = new JDefaultMutableTreeNode("Macros");
        MacrosTree = new CTree(this) {

            public void paint(Graphics g) {
                ImageIcon backimage = JZF.JZT.getIcon("macrospanelback.gif");
                g.drawImage(backimage.getImage(), 0, 0, this.getSize().width, backimage.getIconHeight(), this);
                super.paint(g);
            }
        };
        MacrosTree.setFocusable(false);
        MacrosTree.setModel(new MyTreeModel(MacroTreeTopNode));
        MacrosTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        MyCellRenderer renderer = new MyCellRenderer();
        MacrosTree.setCellRenderer(renderer);
        MacrosTree.setCellEditor(new MyDefaultCellEditor());
        MacrosTree.setOpaque(false);
        MacrosTree.setFont(new Font(JGlobals.GlobalFont, 0, 12));
        MacrosTree.setForeground(new Color(70, 70, 70));
        MacrosTree.setDragEnabled(false);
        MacrosTree.setEditable(false);
        jscrolls = new JScrollPane(MacrosTree);
        jscrolls.setAlignmentX(0F);
        jscrolls.setBorder(BorderFactory.createEmptyBorder());

        this.add(jscrolls);
        controls = new JControls();
        this.add(controls);
        createmacropanel = new Jcreatemacro();
        this.add(createmacropanel);
        
        
        

    }

    // Utilise les Vectors de macros (library et builtin de JMacrosTools) pour initialiser ZF.ZC puis l'arbre :
    public void initMacrosTree() {
        Vector mc;
        MacroItem mi;

        JMacrosTools.setDefaultMacros();
        MacroTreeTopNode.removeAllChildren();
        mc = ZF.ZC.getMacros();
        for (int i = 0; i < mc.size(); i++) {
            AddMacroToTree(((MacroItem) mc.elementAt(i)).M);
        }
        ;

        MacrosTree.setModel(new MyTreeModel(MacroTreeTopNode));
        MacrosTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        MyCellRenderer renderer = new MyCellRenderer();
        MacrosTree.setCellRenderer(renderer);
        MacrosTree.setCellEditor(new MyDefaultCellEditor());
        
        
        
        
        
        ActualiseMacroPopupMenu();

    }

    class MyDefaultCellEditor extends DefaultCellEditor {

        JTextField jtf;

        public MyDefaultCellEditor() {
            super(new JTextField());
            jtf = (JTextField) this.getComponent();
            jtf.setFocusTraversalKeysEnabled(false);
            jtf.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jtf.addKeyListener(new KeyAdapter() {

                @Override
                public void keyTyped(KeyEvent e) {
                    adjust(e.getKeyChar());
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if ((e.getKeyCode() == KeyEvent.VK_ESCAPE) || (e.getKeyCode() == KeyEvent.VK_TAB)) {
                        fireEditingStopped();
                    }
                }
            });
        }

        private void adjust(char ad) {
            FontMetrics fm = getFontMetrics(jtf.getFont());
            jtf.setSize(fm.stringWidth(jtf.getText() + ad) + 5, jtf.getHeight());
        }

        @Override
        protected void fireEditingCanceled() {
            super.fireEditingStopped();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        public Object getCellEditorValue() {
            return super.getCellEditorValue();
        }

        public Component getTableCellEditorComponent(JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column) {
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);

        }
    }

    public void AddMacroToTree(Macro m) {
        if (m.getName().startsWith("@builtin@")) {
            return;
        }
        String[] mypath;
        mypath = m.getName().split("/");
        JDefaultMutableTreeNode mother = MacroTreeTopNode;
        for (int i = 0; i < mypath.length - 1; i++) {
            mother = getFolder(mother, mypath[i]);
        }
        JDefaultMutableTreeNode node = new JDefaultMutableTreeNode(ZF, JZF, m);
        mother.add(node);
    }

    private JDefaultMutableTreeNode getFolder(JDefaultMutableTreeNode father, String name) {
        for (int i = 0; i < father.getChildCount(); i++) {
            if (name.equals((String) ((JDefaultMutableTreeNode) father.getChildAt(i)).getUserObject())) {
                return ((JDefaultMutableTreeNode) father.getChildAt(i));
            }
        }
        JDefaultMutableTreeNode node = new JDefaultMutableTreeNode(name);
        father.add(node);
        return node;
    }


    // Actualisation du PopupMenu de macro ET du menu principal "Macros"
    // AppelŽe ˆ chaque modification de l'arbre (Drag and Drop, Rename, ...)
    // appelŽe aussi ˆ la fin de initMacrosTreeFromPopup :
    public void ActualiseMacroPopupMenu() {

        MyMenu pm = new MyMenu("root");
        JMenu jm = new JMenu("root");

        if (MacroTreeTopNode.getChildCount() > 0) {
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);


            ParseMacroTree(pm, jm, MacroTreeTopNode, "root");

            ZF.ZC.PM.removeAll();
            JZF.GeneralMenuBar.InitMacrosMenu();

            JMenu jmroot = (JMenu) jm.getItem(0);
            MyMenu pmroot = (MyMenu) pm.getItem(0);
            while (pmroot.getItemCount() > 0) {
                ZF.ZC.PM.add(pmroot.getItem(0));
                JZF.GeneralMenuBar.MacrosMenu.add(jmroot.getItem(0));
            }
            ;
        } else {
            ZF.ZC.PM.removeAll();
            JZF.GeneralMenuBar.InitMacrosMenu();
        }


//        MacrosTree.nodepopup.updatelibrary();
    }

    // ProcŽdure recursive appelŽe uniquement par ActualiseMacroPopupMenu.
    // Parcours de l'arbre de macros :
    private void ParseMacroTree(MyMenu PMmenu, JMenu JMmenu, JDefaultMutableTreeNode node, String path) {
        String mypath = path;
        if (!(node.isLeaf())) {
            MyMenu mymenu = new MyMenu((String) node.getUserObject());
            JMenu myjmenu = new JMenu((String) node.getUserObject());
            myjmenu.setFont(new java.awt.Font("System", 0, 13));
            for (int i = 0; i < node.getChildCount(); i++) {
                ParseMacroTree(mymenu, myjmenu, (JDefaultMutableTreeNode) node.getChildAt(i), mypath + "/" + mymenu.getLabel());
            }
            PMmenu.add(mymenu);
            JMmenu.add(myjmenu);
        } else {
            String myname = (String) node.getUserObject();
            if (!(myname.startsWith("-- "))) {
                node.ActualisePath();
                PMmenu.add(node.PMmenuitem);
                JMmenu.add(node.MainMenuItem);

            } else {
                if (node.getParent().getChildCount() > 1) {
                    ((DefaultTreeModel) MacrosTree.getModel()).removeNodeFromParent(node);
                }
            }

        }
        ;
    }

    // Les noeuds de l'arbre sont considŽrŽs comme des JLabels
    // Cette classe se charge de leurs look :
    class MyCellRenderer extends JLabel implements TreeCellRenderer {

        public MyCellRenderer() {
            setOpaque(false);
            setBackground(null);
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            String stringValue = tree.convertValueToText(value, sel, expanded, leaf, row, hasFocus);

            setText(stringValue);
            setEnabled(tree.isEnabled());
            setFont(tree.getFont());
            setForeground(Color.black);
            setOpaque(sel);



            //Couleur de sŽlection :
            setBackground(Color.lightGray);
            JDefaultMutableTreeNode mynode = (JDefaultMutableTreeNode) value;
            if (leaf) {
                setIcon((stringValue.startsWith(("-- "))) ? null : JTreeleaf[mynode.macrotype]);
                if (mynode.macrotype == 0) {
                    setForeground(new Color(68, 84, 131));
                }
            } else {
                setIcon((expanded) ? JTreefoldopened : JTreefoldclosed);
            }
            ;

            return this;
        }
    }


    // Le mod�le sur lequel est basŽ l'arbre
    // Se charge de l'Ždition des noeuds et contient les TreeModelListeners :
    class MyTreeModel extends DefaultTreeModel implements TreeModelListener {

        public MyTreeModel(TreeNode node) {
            super(node);
            this.addTreeModelListener(this);
                    
        }

        public void valueForPathChanged(TreePath path, Object newValue) {
            JDefaultMutableTreeNode tn = (JDefaultMutableTreeNode) path.getLastPathComponent();
            super.valueForPathChanged(path, newValue);
            tn.ActualisePath();
        }

        public void treeNodesChanged(TreeModelEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ActualiseMacroPopupMenu();
                }
            });
        }

        public void treeNodesInserted(TreeModelEvent e) {
//            System.out.println("treeNodesInserted");
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ActualiseMacroPopupMenu();
                }
            });
        }

        public void treeNodesRemoved(TreeModelEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ActualiseMacroPopupMenu();
                }
            });
        }

        public void treeStructureChanged(TreeModelEvent e) {
//            System.out.println("treeStructureChanged");
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ActualiseMacroPopupMenu();
                }
            });
        }
    }

    /**************************************************************************************
     *** N'oublions pas que JMacrosList est un JPanel qui contient d'autres JPanels
     *** La class Jcontrols contient les boutons d'Ždition de l'arbre et leurs listeners
     ***************************************************************************************/
    class JControls extends JPanel {

        JButton addbtn;
        JButton delbtn;
        JButton renbtn;
        public JButton createbtn;
        String message = "";

        public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            java.awt.Dimension d = this.getSize();
            g.drawImage(JZF.JZT.getImage("backcontrols.gif"), 0, 0, d.width, d.height, this);

        }

        public void setButtonsIcons() {
            addbtn.setIcon(JZF.JZT.getIcon("addmacrofolder.png"));
            addbtn.setRolloverIcon(JZF.JZT.getIcon("addmacrofoldersel.png"));
            delbtn.setIcon(JZF.JZT.getIcon("delmacro.png"));
            delbtn.setToolTipText(JZF.Strs.getString("macros.deleteselected"));
            renbtn.setIcon(JZF.JZT.getIcon("renamemacro.png"));
            renbtn.setRolloverIcon(JZF.JZT.getIcon("renamemacrosel.png"));
            createbtn.setIcon(JZF.JZT.getIcon("createmacro.png"));
            createbtn.setRolloverIcon(JZF.JZT.getIcon("createmacroover.png"));

        }

        public JControls() {
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
            this.setAlignmentX(0F);
            addbtn = new JButton();
            addbtn.setToolTipText(JZF.Strs.getString("macros.addfolder"));
            addbtn.setOpaque(false);
            addbtn.setContentAreaFilled(false);
            addbtn.setBorder(BorderFactory.createEmptyBorder());
            addbtn.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    MacrosTree.nodepopup.addfolder();
                }
            });


            delbtn = new JButton();
            delbtn.setOpaque(false);
            delbtn.setContentAreaFilled(false);
            delbtn.setBorder(BorderFactory.createEmptyBorder());
            delbtn.setRolloverIcon(JZF.JZT.getIcon("delmacrosel.png"));
            delbtn.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    MacrosTree.nodepopup.deletenodes();
                }
            });

            renbtn = new JButton();
            renbtn.setToolTipText(JZF.Strs.getString("macros.renamemacro"));
            renbtn.setOpaque(false);
            renbtn.setContentAreaFilled(false);
            renbtn.setBorder(BorderFactory.createEmptyBorder());
            renbtn.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    MacrosTree.nodepopup.renamenode();
                }
            });

            createbtn = new JButton();
            createbtn.setToolTipText(JZF.Strs.getString("macros.recordmacro"));
            createbtn.setSelectedIcon(JZF.JZT.getIcon("createmacrosel.png"));
            createbtn.setBorder(BorderFactory.createEmptyBorder());
            createbtn.setOpaque(false);
            createbtn.setContentAreaFilled(false);
            createbtn.setSelected(false);

            setButtonsIcons();

            createbtn.addMouseListener(new MouseAdapter() {

                public void mouseEntered(MouseEvent e) {
                    if (createbtn.isSelected()) {
                        message = createmacropanel.steps.mycomment.getText();
                        createmacropanel.steps.mycomment.setText(JZF.Strs.getString("macros.cancel"));
                    }


                }

                public void mouseExited(MouseEvent e) {
                    if (createbtn.isSelected()) {
                        if (message != "") {
                            createmacropanel.steps.mycomment.setText(message);
                        }
                    }
                }

                public void mousePressed(MouseEvent e) {
                    if (createbtn.isSelected()) {
                        createbtn.setSelected(false);
                        createmacropanel.disappeargently();
                        JZF.JPM.setSelected("point", true);
                    } else {
                        createbtn.setSelected(true);
                        createmacropanel.appeargently();
                        JZF.JPM.deselectgeomgroup();
                    }

                }
            });

            JPanel spacer = new JPanel();
            spacer.setOpaque(false);

            this.add(addbtn);
            this.add(delbtn);
            this.add(renbtn);
            this.add(spacer);
            this.add(createbtn);
        }
    }

    /**************************************************************************************
     *** N'oublions pas que JMacrosList est un JPanel qui contient d'autres JPanels
     *** La class Jcreatemacro contient les ments UI qui grent l'enregistrement des macros
     ***************************************************************************************/
    class Jcreatemacro extends JPanel {

        JButton nextbtn;
        stepcomments steps;
        int stepnum;
        boolean visible = false;

        public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            java.awt.Dimension d = this.getSize();
            g.drawImage(JZF.JZT.getImage("backcontrols.gif"), 0, 0, d.width, d.height, this);
        }

        public Jcreatemacro() {
            this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
            this.setAlignmentX(0F);
        }

        // Disparition "lente" du panneau iTunes :
        public void disappeargently() {
            stepnum = 1;
            ZF.settool(ZirkelFrame.NParameters);
            ZF.ZC.getOC().reset(ZF.ZC);
            this.visible = false;
            this.removeAll();
            Dimension d = this.getSize();
            Dimension dc = JZF.ZContent.macros.content.getSize();
            for (int i = 1; i < 23; i++) {
                d.height = 66 - 3 * i;
                this.setMaximumSize(d);
                this.setMinimumSize(d);
                this.setPreferredSize(d);
                this.setSize(d);

                JZF.ZContent.macros.content.validate();
                JZF.ZContent.macros.content.paintImmediately(0, dc.height - 110, dc.width, 110);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
            JZF.ZContent.macros.myJML.controls.message = "";
        }

        // Apparition "lente" du panneau iTunes :
        public void appeargently() {
            stepnum = 1;
            ZF.settool(ZirkelFrame.NParameters);
            this.visible = true;
            Dimension d = this.getSize();
            Dimension dc = JZF.ZContent.macros.content.getSize();
            for (int i = 1; i < 23; i++) {
                d.height = 3 * i;
                this.setMaximumSize(d);
                this.setMinimumSize(d);
                this.setPreferredSize(d);
                this.setSize(d);
                JZF.ZContent.macros.content.validate();
                JZF.ZContent.macros.content.paintImmediately(0, dc.height - 110, dc.width, 110);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }

            steps = new stepcomments();
            nextbtn = new JButton(JZF.JZT.getIcon("Mnext.png"));
            nextbtn.setOpaque(false);
            nextbtn.setContentAreaFilled(false);
            nextbtn.setBorder(BorderFactory.createEmptyBorder());
            nextbtn.setAlignmentY(0.5F);
            nextbtn.addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent e) {
                }

                public void mousePressed(MouseEvent e) {
                    // if parameters are selected :
                    if (ZF.ZC.getConstruction().Parameters.size() > 0) {
                        switch (stepnum) {
                            case 1:
                                steps.mycomment.setText("2/2 - " + JZF.Strs.getString("macros.finals"));
                                ZF.settool(ZirkelFrame.NTargets);
                                break;
                            case 2:
                                TreePath tp = createmacro();
                                disappeargently();
                                JZF.ZContent.macros.myJML.controls.createbtn.setSelected(false);
                                JZF.JPM.setSelected("point", true);
                                MacrosTree.setEditable(true);
                                MacrosTree.startEditingAtPath(tp);
                                break;

                        }
                        stepnum++;
                    } else {
                        ZF.settool(ZirkelFrame.NParameters);
                        steps.mycomment.setText("<html><center>1/2 - " + JZF.Strs.getString("macros.initials") + "<br><b>" + JZF.Strs.getString("macros.pleaseselect") + "</b></center></html>");
                    }

                }

                public void mouseClicked(MouseEvent e) {
                }
            });

            this.add(steps);
            this.add(nextbtn);
            JZF.ZContent.macros.content.validate();
            JZF.ZContent.macros.content.repaint();
        }

        public TreePath createmacro() {
            JDefaultMutableTreeNode root;
            Vector V = ZF.ZC.getConstruction().Parameters;
            String s[] = new String[V.size()];
            for (int i = 0; i < V.size(); i++) {
                ConstructionObject o = (ConstructionObject) V.elementAt(i);
                s[i] = o.getName();
            }
            ;

            Macro m = new Macro(ZF.ZC, JZF.Strs.getString("macros.untitledmacro"), "", s);

            try {

                ZF.ZC.defineMacro(ZF.ZC.getConstruction(), m, (ZF.ZC.getConstruction().countTargets() > 0), true, s, false);
            } catch (ConstructionException e) {
            }
            m.hideDuplicates(false);
            ZF.ZC.storeMacro(m, false);

            JDefaultMutableTreeNode node = new JDefaultMutableTreeNode(ZF, JZF, m);

//            JDefaultMutableTreeNode node=new JDefaultMutableTreeNode(JZF.Strs.getString("macros.untitledmacro"));
//            node.setMacroName(ZF,"",JZF.Strs.getString("macros.untitledmacro"));

            TreePath[] paths = MacrosTree.getSelectionPaths();
            if ((paths) != null) {
                root = (JDefaultMutableTreeNode) paths[0].getLastPathComponent();

                if (root.isLeaf()) {
                    // if the first selected node is a leaf :
                    DefaultMutableTreeNode father = (DefaultMutableTreeNode) root.getParent();
                    int i = father.getIndex(root) + 1;
                    ((DefaultTreeModel) MacrosTree.getModel()).insertNodeInto(node, father, i);

                } else {
                    // if the first selected node is a folder :
                    ((DefaultTreeModel) MacrosTree.getModel()).insertNodeInto(node, root, root.getChildCount());
                }
            } else {
                // There is no selected node :
                ((DefaultTreeModel) MacrosTree.getModel()).insertNodeInto(node, MacroTreeTopNode, MacroTreeTopNode.getChildCount());
            }

            TreePath tp = new TreePath(node.getPath());
            node.ActualisePath();
            return tp;
        }

        private class stepcomments extends JPanel {

            JLabel mycomment = new JLabel();

            public void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Dimension d = this.getSize();
                g.drawImage(JZF.JZT.getImage("Mcomments.png"), 0, 0, d.width, d.height, this);

            }

            private stepcomments() {
                this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
                this.setAlignmentY(0.5F);
                ImageIcon backIcon = JZF.JZT.getIcon("Mcomments.png");
                Dimension d = new Dimension(backIcon.getIconWidth(), backIcon.getIconHeight());
                this.setMaximumSize(d);
                this.setMinimumSize(d);
                this.setPreferredSize(d);
                this.setSize(d);
                this.setOpaque(false);

                mycomment.setText("1/2 - " + JZF.Strs.getString("macros.initials"));
                mycomment.setFont(new Font("Verdana", 0, 10));
                mycomment.setMaximumSize(d);
                mycomment.setMinimumSize(d);
                mycomment.setPreferredSize(d);
                mycomment.setSize(d);
                mycomment.setHorizontalAlignment(SwingConstants.CENTER);
                this.add(mycomment);
            }
        }
    }
}
