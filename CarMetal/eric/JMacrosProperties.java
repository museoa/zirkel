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
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class JMacrosProperties extends JPanel {
    private boolean DEBUG = false;
    private JTable table;
    private DefaultTableModel model;
    private JMacrosInspector JMI;
    
    public JMacrosProperties(JMacrosInspector jmi) {
        super(new GridLayout(1,0));
        JMI=jmi;
        this.setFocusable(false);
        model = new DefaultTableModel() {
            public Class getColumnClass(int columnIndex) {
                if((columnIndex == 3)||(columnIndex == 4)) {
                    return Boolean.class;
                } else {
                    return super.getColumnClass(columnIndex);
                }
            }
        };
        
        
        
        table = new JTable(model){
            public boolean isCellEditable(int row, int col) {
                if (col==0) return false;
                if ((col==2)&&(String.valueOf(getValueAt(row,3)).equals("true"))) return false;
                return (getValueAt(row,col)!=null);
            }
            
            public boolean isCellSelected(int row,int col){
                return false;
            }
            
            public void setValueAt(Object o,int row,int col){
                getModel().setValueAt(o,row,col);
                
                if (col==3) JMI.content.fixObject(row,String.valueOf(getModel().getValueAt(row,col)).equals("true"));
                if (col==4) JMI.content.askObject(row,String.valueOf(getModel().getValueAt(row,col)).equals("true"));
                
            }
            
            public TableCellRenderer getCellRenderer(int row, int column) {
                Object value = getValueAt(row,column);
                if (value !=null) {
                    return getDefaultRenderer(value.getClass());
                }
                return getDefaultRenderer(String.class);
            }
        };
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setGridColor(Color.lightGray);
        table.setRowHeight(20);
        
        
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0, false), "none");
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "none");
        
        model.addColumn(JMI.getLocal("mi.tab.type"));
        model.addColumn(JMI.getLocal("mi.tab.name"));
        model.addColumn(JMI.getLocal("mi.tab.prompt"));
        model.addColumn(JMI.getLocal("mi.tab.fix"));
        model.addColumn(JMI.getLocal("mi.tab.ask"));
        
//        model.addRow(new Object[]{new String(""),new String(""),new String(""),new Boolean(false), new Boolean(false)});
        
        
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(45);
        table.getColumnModel().getColumn(2).setPreferredWidth(125);
        table.getColumnModel().getColumn(3).setPreferredWidth(33);
        table.getColumnModel().getColumn(4).setPreferredWidth(33);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }
    
    public int getRowCount(){
        
        return model.getRowCount();
    }
    
    public Object getValueAt(int i,int j){
        return model.getValueAt(i,j);
    }
    
    public void stopCellEditing(){
        TableCellEditor editor = table.getCellEditor();
        if (editor != null) {
            editor.stopCellEditing();
        }
    }
    
    public String getOType(int row){
        return (String) model.getValueAt(row,0);
    }
    
    public String getOName(int row){
        return (String) model.getValueAt(row,1);
    }
    
    public String getOPrompt(int row){
        return (String) model.getValueAt(row,2);
    }
    
    public boolean getOFix(int row){
        return String.valueOf(model.getValueAt(row,3)).equals("true");
    }
    
    public boolean getOAsk(int row){
        if (model.getValueAt(row,4)==null) return false;
        return String.valueOf(table.getModel().getValueAt(row,4)).equals("true");
    }
    
    public void setOType(String what,int row){
        model.setValueAt(new String(what),row,0);
    }
    
    public void setOName(String what,int row){
        model.setValueAt(new String(what),row,1);
    }
    
    public void setOPrompt(String what,int row){
        model.setValueAt(new String(what),row,2);
    }
    
    public void setOAsk(boolean what,int row){
        if (model.getValueAt(row,4)!=null) model.setValueAt(new Boolean(what),row,4);
    }
    
    public void setOFix(boolean what,int row){
        model.setValueAt(new Boolean(what),row,3);
    }
    
    
    public void addRow(String type,String name,String prompt,boolean fix){
        String newprompt=(fix)?"":prompt;
        model.addRow(new Object[]{new String(type),new String(name),new String(newprompt),new Boolean(fix), null});
        
    }
    
    public void addRow(String type,String name,String prompt,boolean fix,boolean ask){
        String newprompt=(fix)?"":prompt;
        model.addRow(new Object[]{new String(type),new String(name),new String(newprompt),new Boolean(fix), new Boolean(ask)});
    }
    
    public void removeAllRows(){
        while (model.getRowCount()>0) model.removeRow(0);
    }
    
    
}
