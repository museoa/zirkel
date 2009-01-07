/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.controls;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;
import eric.bar.JProperties;

/**
 *
 * @author erichake
 */
public class JCanvasTxtfield extends JCanvasPanel {

    MyJTextField JCB;
    Color errColor=new Color(201, 68, 27);
    Color goodColor=new Color(50, 50, 50);

    public JCanvasTxtfield(ZirkelCanvas zc, ExpressionObject o) {
        super(zc, o);
        JSL=new MyJTextField();
        JCB=(MyJTextField) JSL;
        JCB.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                setVal(JProperties.Comma_To_Point(JCB.getText(), O.getConstruction(), true));
                if (O.getExp().isValid()) {
                    JCB.setForeground(goodColor);
                } else {
                    JCB.setForeground(errColor);
                }
            }
        });
        JCB.addFocusListener(new FocusAdapter() {

            public void focusGained(FocusEvent e) {
                JCB.selectAll();
            }

            public void focusLost(FocusEvent e) {
            }
        });
        JCB.addMouseListener(this);
        JCB.addMouseMotionListener(this);
        JCB.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (isEditMode()) {
                    JCB.setFocusable(false);
                    JCB.setEnabled(false);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (!JCB.isEnabled()) {
                    JCB.setEnabled(true);
                    JCB.setFocusable(true);
                }
            }
        });
        showval=false;
        showunit=false;
        showcom=true;
        setComment(O.getName()+" = ");
        setVal(1);
        JCB.setText("1");
        JCB.setForeground(goodColor);
        this.add(JCPlabel);
        this.add(JCB);
        this.add(JCPresize);
        zc.add(this);
    }

    public double getVal() {
        return (Double.valueOf(JCB.getText()));
    }

    class MyJTextField extends JTextField {

        JTextField jtf;

        MyJTextField() {
            super();
        }
    }

    public void PrintXmlTags(XmlWriter xml) {
        xml.startTagStart("CTRLtxtfield");
        super.PrintXmlTags(xml);
        xml.printArg("txt", ""+JCB.getText());
        xml.finishTagNewLine();
    }
}
