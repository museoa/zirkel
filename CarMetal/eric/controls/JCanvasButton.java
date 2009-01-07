/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.controls;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;

/**
 *
 * @author erichake
 */
public class JCanvasButton extends JCanvasPanel {

    MyJButton JCB;

    public JCanvasButton(ZirkelCanvas zc, ExpressionObject o) {
        super(zc, o);
        JSL=new MyJButton();
        JCB=(MyJButton) JSL;
        JCB.addMouseListener(this);
        JCB.addMouseMotionListener(this);
        JCB.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                
                if (isEditMode()) {
                    JCB.setEnabled(false);
                }
            }

            public void mouseReleased(MouseEvent e) {
                JCB.setEnabled(true);
                if (getVal()==0) setVal(1);
                else setVal(0);
            }
        });
        showval=false;
        showunit=false;
        showcom=false;
        setComment("ok");
        setVal(0);
        this.add(JCPlabel);
        this.add(JCB);
        this.add(JCPresize);
        zc.add(this);
    }

    public void setComment(String s) {
        lbl_com=s;
        JCPlabel.setText(goodLabel());
        if (showcom) {
            JCB.setText("");
        } else {
            JCB.setText(s);
        }
        setDims();
    }

    public void setShowComment(boolean b) {
        showcom=b;
        JCPlabel.setText(goodLabel());
        if (showcom) {
            JCB.setText("");
        } else {
            JCB.setText(lbl_com);
        }
        setDims();
    }

    public double getVal() {
        double s;
        try {
            s=O.getValue();
        } catch (Exception ex) {
            s=0;
        }
        return s;
    }

    class MyJButton extends JButton {

        MyJButton() {
            super();
            setFocusable(false);
            setOpaque(false);
        }
    }



    public void PrintXmlTags(XmlWriter xml) {
        xml.startTagStart("CTRLbutton");

        super.PrintXmlTags(xml);
        xml.finishTagNewLine();

    }
}
