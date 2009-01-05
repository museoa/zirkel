/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric.controls;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.objects.ExpressionObject;

/**
 *
 * @author erichake
 */
public class JCanvasSlider extends JCanvasPanel {

    static int PREFEREDVMAX=10000;
    int VMAX=PREFEREDVMAX;
    int TICKS=1000;
    double xMIN, xMAX, xTICKS;
    MyJSlider JCS;

    public JCanvasSlider(ZirkelCanvas zc, ExpressionObject o,double min, double max, double val) {
        super(zc,o);
        xMIN=min;
        xMAX=max;
        xTICKS=getCurrentTicks();
        setVal(val);
        JSL=new MyJSlider(0, VMAX, TICKS, (int) Math.round((val-xMIN)*VMAX/(xMAX-xMIN)));
        JCS=(MyJSlider)JSL;
        JCS.addMouseListener(this);
        JCS.addMouseMotionListener(this);
        setComment(O.getName()+"=");
        this.add(JCS);
        this.add(JCPlabel);
        this.add(JCPresize);
        zc.add(this);
    }
    
    public void setGoodKnobPos(double x){
        int i=(int) Math.round((x-xMIN)*(VMAX/(xMAX-xMIN)));
        JCS.setValue(i);
    }

    public double getCurrentTicks() {
        return TICKS*(xMAX-xMIN)/(VMAX);
    }

    public void setTicks(String s) {
        setTicks(Double.parseDouble(s));
    }

    public void setTicks(double x) {
        xTICKS=x;
        adjustVirtualMax();
        TICKS=(int) Math.round(x*VMAX/(xMAX-xMIN));
        JCS.setMinorTickSpacing(TICKS);
        this.revalidate();
        this.repaint();
    }

    public String getTicks() {
        return String.valueOf(xTICKS);
    }

    public void adjustVirtualMax() {
        if ((xMAX-xMIN)<1) {
            return;
        }
        int mySQRT=(int) Math.round(Math.sqrt(PREFEREDVMAX));

        // VMAX/(xMAX-xMIN) must be an integer :
        VMAX=(int) Math.round(Math.ceil(mySQRT/(xMAX-xMIN))*(xMAX-xMIN));
        // VMAX/xTICKS must also be an integer :
        VMAX*=(int) Math.round(Math.ceil(mySQRT/xTICKS)*xTICKS);
        JCS.setMaximum(VMAX);
    }

    public void setMax(String s) {
        setMax(Double.parseDouble(s));
    }

    public void setMax(double x) {
        xMAX=x;
        if (xMIN>xMAX) {
            xMIN=xMAX-10;
        }
        adjustVirtualMax();
        double newval=(getVal()>xMAX)?xMAX:getVal();
        newval=(getVal()<xMIN)?xMIN:getVal();
        setVal(newval);
        JCS.setValue((int) Math.round((newval-xMIN)*VMAX/(xMAX-xMIN)));
        setTicks(xTICKS);
        this.revalidate();
        this.repaint();
    }

    public String getMax() {
        return String.valueOf(xMAX);
    }

    public void setMin(String s) {
        setMin(Double.parseDouble(s));
    }

    public void setMin(double x) {
        xMIN=x;
        if (xMIN>xMAX) {
            xMAX=xMIN+10;
        }
        adjustVirtualMax();
        double newval=(getVal()>xMAX)?xMAX:getVal();
        newval=(getVal()<xMIN)?xMIN:getVal();
        setVal(newval);
        JCS.setValue((int) Math.round((newval-xMIN)*VMAX/(xMAX-xMIN)));
        setTicks(xTICKS);
        this.revalidate();
        this.repaint();
    }

    public String getMin() {
        return String.valueOf(xMIN);
    }

    public void setSnap(boolean b) {
        JCS.setSnapToTicks(b);
        this.revalidate();
        this.repaint();
    }

    public boolean getSnap() {
        return JCS.getSnapToTicks();
    }

    public void setShowTicks(boolean b) {
        JCS.setPaintTicks(b);
        this.revalidate();
        this.repaint();
    }

    public boolean getShowTicks() {
        return JCS.getPaintTicks();
    }

    public double getVal() {
        return xMIN+JCS.getValue()*(xMAX-xMIN)/VMAX;
    }

    class MyJSlider extends JSlider implements ChangeListener {

        int oldvalue;

        MyJSlider(int min, int max, int ticks, int val) {
            super(min, max, val);
            oldvalue=val;
            this.setOpaque(false);
            this.setFocusable(false);

//            this.setMajorTickSpacing((max-min)/5);
            this.setMinorTickSpacing(ticks);
            this.setPaintTicks(true);
            this.setSnapToTicks(true);
            this.setOpaque(false);

            this.addChangeListener(this);
        }

        public int getValue() {

            if (isEditMode()) {
                return oldvalue;
            }
            oldvalue=super.getValue();
            return oldvalue;

        }

        public void stateChanged(ChangeEvent e) {
            try {
                double val=getValue();
                val=xMIN+((xMAX-xMIN)/VMAX)*val;
                if (getSnapToTicks()) {
                    // snap to good value :
                    int i=(int) Math.round((val-xMIN)/xTICKS);
                    val=xMIN+xTICKS*i;
                    // eliminate side effects :
                    double ex=Math.pow(10, 5-Math.round(Math.log10(val)));
                    val=Math.round(val*ex)/ex;
                    if (Double.isNaN(val)) {
                        val=0;
                    }
                }

                setVal(val);
            } catch (Exception ex) {
            }
        }
    }

    public void PrintXmlTags(XmlWriter xml) {
        xml.startTagStart("CTRLslider");
        super.PrintXmlTags(xml);
        xml.printArg("min", ""+xMIN);
        xml.printArg("max", ""+xMAX);
        xml.printArg("T", ""+xTICKS);
        xml.printArg("fixT", ""+JCS.getSnapToTicks());
        xml.printArg("showT", ""+JCS.getPaintTicks());
        xml.finishTagNewLine();
    }
}



