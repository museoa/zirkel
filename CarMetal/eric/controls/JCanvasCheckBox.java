/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.controls;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;

import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;

/**
 *
 * @author erichake
 */
public class JCanvasCheckBox extends JCanvasPanel implements ItemListener{

    MyJCheckBox JCB;

    public JCanvasCheckBox(ZirkelCanvas zc,ExpressionObject o) {
        super(zc,o);
        JSL=new MyJCheckBox();
        JCB=(MyJCheckBox)JSL;
//        JCanvasPanel.fixsize(JCB, 50, 22);
        JCB.addMouseListener(this);
        JCB.addMouseMotionListener(this);
        JCB.addItemListener(this);
        JCB.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    if (isEditMode()) {
                        JCB.setEnabled(false);
                    };
                }
                
                public void mouseReleased(MouseEvent e){
                    JCB.setEnabled(true);
                }
            });
        this.add(JCB);
                showval=false;
        showunit=false;
        showcom=true;
        String s=eric.JGlobals.Loc("props.expl");
        s=s.trim();
        s=s.replace(":", "");
        s=s.trim();
        setComment(s);
        setVal(0);
        this.add(JCPlabel);
        this.add(JCPresize);
        zc.add(this);
    }
    

    public double getVal() {
        double s=JCB.isSelected()?1:0;
        return s;
    }


    class MyJCheckBox extends JCheckBox {

        MyJCheckBox() {
            super();
            setFocusable(false);
            setOpaque(false);
        }
        

    }

    public void itemStateChanged(ItemEvent arg0) {
        try {
            
                int s=JCB.isSelected()?1:0;
                setVal(s);
            } catch (Exception ex) {
            }
    }
    
    public void PrintXmlTags(XmlWriter xml) {
        xml.startTagStart("CTRLcheckbox");
        super.PrintXmlTags(xml);
        xml.finishTagNewLine();
    
    }
}
