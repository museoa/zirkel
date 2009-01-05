/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalComboBoxUI;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;

/**
 *
 * @author erichake
 */
public class JCanvasPopup extends JCanvasPanel implements ActionListener {

    MyJComboBox JCB;

    public JCanvasPopup(ZirkelCanvas zc,ExpressionObject o) {
        super(zc,o);
        JSL=new MyJComboBox();
        JCB=(MyJComboBox)JSL;
        JCB.setUI((ComboBoxUI) MyComboBoxUI.createUI(JCB));
//        JCanvasPanel.fixsize(JCB, 100, 18);
//        addMouseEvents();
        JCB.addMouseListener(this);
        JCB.addMouseMotionListener(this);
        JCB.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (isEditMode()) {
                    JCB.hidePopup();
                }
                ;

            }
            });
        addMouseEvents();
        JCB.addActionListener(this);
        showval=false;
        showunit=false;
        showcom=true;
        setComment(eric.JGlobals.Loc("props.expl")+" ");
        setVal(1);
        this.add(JCPlabel);
        this.add(JCB);
        this.add(JCPresize);
        zc.add(this);
    }

    static class MyComboBoxUI extends MetalComboBoxUI {

        public static ComponentUI createUI(JComponent c) {
            return new MyComboBoxUI();
        }
    }

    public void addMouseEvents() {
        for (int i=0; i<JCB.getComponentCount(); i++) {
            JCB.getComponent(i).addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    if (isEditMode()) {
                        JCB.hidePopup();
                    }
                    ;

                }
            });
            JCB.getComponent(i).addMouseListener(this);
            JCB.getComponent(i).addMouseMotionListener(this);
        }
    }
    
    public String getItems(){
        String s="";
        for (int i=0;i<JCB.getItemCount()-1;i++){
            s+=JCB.getItemAt(i)+"\n";
        }
        s+=JCB.getItemAt(JCB.getItemCount()-1);
        return s;
    }
    
    public void setItems(String s){
        JCB.removeAllItems();
        String[] itms=s.split("\n");
        for (int i=0;i<itms.length;i++){
            JCB.addItem(itms[i]);
        }
        setDims();
    }
    
    public double getVal() {
        return (JCB.getSelectedIndex()+1);
    }

    class MyJComboBox extends JComboBox {

        MyJComboBox() {
            super();
            setFocusable(false);

            setModel(new DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3"}));
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==JCB) {
            int selectedIndex=JCB.getSelectedIndex()+1;


            try {
                setVal(selectedIndex);
            } catch (Exception ex) {
            }



        }
    }
    
    public void PrintXmlTags(XmlWriter xml) {
        xml.startTagStart("CTRLpopup");
        super.PrintXmlTags(xml);
        String s=getItems().replace("\n", "@@@");
        xml.printArg("Items", ""+s);
        xml.finishTagNewLine();
    
    }
}
