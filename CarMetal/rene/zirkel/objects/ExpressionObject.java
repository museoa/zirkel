/* 
 
Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

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
 
 
 package rene.zirkel.objects;

// file: PointObject.java
import eric.JGlobals;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JPanel;
import rene.gui.*;

import rene.util.xml.*;
import rene.zirkel.*;
import rene.zirkel.construction.*;
import rene.zirkel.dialogs.*;
import rene.zirkel.expression.*;
import rene.zirkel.graphics.*;
import rene.dialogs.*;

class ExpressionEditDialog extends ObjectEditDialog {

    TextFieldAction X, Y;
    TextField Expression, Prompt, Min, Max;
    Checkbox Fixed, Slider;
    ZirkelCanvas ZC;

    public ExpressionEditDialog(Frame f, ZirkelCanvas zc, ExpressionObject o) {
        super(f, Zirkel.name("edit.expression.title"), o, "expression");
        ZC = zc;
    }

    public void focusGained(FocusEvent e) {
        Expression.requestFocus();
    }

    public void addFirst(JPanel P) {
        ExpressionObject p = (ExpressionObject) O;

        X = new TextFieldAction(this, "X", "" + p.round(p.getX()));
        P.add(new MyLabel(Zirkel.name("edit.point.x")));
        P.add(X);
        Y = new TextFieldAction(this, "Y", "" + p.round(p.getY()));
        P.add(new MyLabel(Zirkel.name("edit.point.y")));
        P.add(Y);

        Fixed = new Checkbox("");
        Fixed.setState(p.fixed());
        P.add(new MyLabel(Zirkel.name("edit.fixed")));
        P.add(Fixed);
        if (p.fixed()) {
            X.setText(p.getEX());
            Y.setText(p.getEY());
        }

        JPanel px = new MyPanel();
        px.setLayout(new GridLayout(1, 0));
        Slider = new Checkbox("");
        Slider.setState(p.isSlider());
        P.add(new MyLabel(Zirkel.name("edit.expression.slider")));
        px.add(Slider);
        Min = new TextFieldAction(this, "Min", "" + p.getMin());
        Max = new TextFieldAction(this, "Max", "" + p.getMax());
        px.add(Min);
        px.add(Max);
        P.add(px);


        Prompt = new MyTextField(p.getPrompt());
        P.add(new MyLabel(Zirkel.name("edit.expression.prompt")));
        P.add(Prompt);
        Expression = new TextFieldAction(this, "OK", p.getExpression(), 30);
        P.add(new MyLabel(Zirkel.name("edit.expression")));
        P.add(Expression);

    }

    public void doAction(String o) {
        if (o.equals("OK")) {
            if (Fixed != null && X.isChanged() || Y.isChanged()) {
                Fixed.setState(true);
            }
        }
        super.doAction(o);
    }

    public void setAction() {
        ExpressionObject p = (ExpressionObject) O;
        if (Slider.getState()) {
            p.setSlider(Min.getText(), Max.getText());
            if (Expression.getText().equals("")) {
                Expression.setText(Min.getText());
            }
        } else {
            p.setSlider(false);
        }
        try {
            double x = new Double(X.getText()).doubleValue();
            double y = new Double(Y.getText()).doubleValue();
            p.move(x, y);
        } catch (Exception e) {
        }
        try {
            p.setExpression(Expression.getText(), ZC.getConstruction());
        } catch (Exception e) {
        }
        if (Fixed.getState() == true) {
            p.setFixed(X.getText(), Y.getText());
        } else {
            try {
                double x = new Expression(X.getText(),
                        p.getConstruction(), p).getValue();
                double y = new Expression(Y.getText(),
                        p.getConstruction(), p).getValue();
                p.move(x, y);
                p.setFixed(false);
            } catch (Exception e) {
            }
        }
        p.setPrompt(Prompt.getText());
    }
}

public class ExpressionObject extends ConstructionObject
        implements MoveableObject, SimulationObject,DriverObject {

    protected double X,  Y;
    private static Count N = new Count();
    public Expression E;
    protected Expression EX,  EY;
    protected boolean Fixed;
    String Prompt = Zirkel.name("expression.value");
    protected double CurrentValue = 0;
    protected boolean CurrentValueValid = true;
    protected boolean Slider = false;
    protected Expression SMin,  SMax;
    private String LASTE="";

    public ExpressionObject(Construction c, double x, double y) {
        super(c);
        X = x;
        Y = y;
        setColor(ColorIndex,SpecialColor);
        updateText();
        setFixed("0");
    }

    public void setDefaults() {

        setShowName(Global.getParameter("options.text.shownames", false));
        setShowValue(true);
//		setShowValue(Global.getParameter("options.text.showvalues",false));
        setColor(Global.getParameter("options.text.color", 0), Global.getParameter("options.text.pcolor", (Color)null));
        setColorType(Global.getParameter("options.text.colortype", 0));
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
        setSolid(Cn.Solid);
        setLarge(Cn.LargeFont);
        setBold(Cn.BoldFont);
        setPartial(Cn.Partial);
    }

    public void setTargetDefaults() {

        setShowName(Global.getParameter("options.text.shownames", false));
        setShowValue(Global.getParameter("options.text.showvalues", false));
        setColor(Global.getParameter("options.text.color", 0), Global.getParameter("options.text.pcolor", (Color)null));
        setColorType(Global.getParameter("options.text.colortype", 0));
    }

    public String getTag() {
        return "Expression";
    }

    public int getN() {
        return N.next();
    }

    public void updateText() {
        if (E != null) {
            setText(text3(Zirkel.name("text.expression"),
                    E.toString(), "" + roundDisplay(X), "" + roundDisplay(Y)));
        } else {
            setText(text3(Zirkel.name("text.expression"),
                    "", "" + roundDisplay(X), "" + roundDisplay(Y)));
        }
    }
    public double C,  R,  W,  H; // for the expression position
    public double SC,  SR,  SW,  SH; // for the slider position in screen coord.
    public double SX,  SY,  SD; // for the slider scale in x,y-coordinates

    /**
     * Paint the expression.
     * Use value, name (i.e. tag), or slider.
     * Remember slider position for nearto and drags.
     */
    public void paint(MyGraphics g, ZirkelCanvas zc) {
        DisplaysText = false;
        if (!Valid || mustHide(zc)) {
            return;
        }
        C = zc.col(X);
        R = zc.row(Y);
        setFont(g);
        FontMetrics fm = g.getFontMetrics();
        if (isStrongSelected() && g instanceof MyGraphics) {
            ((MyGraphics13) g).drawMarkerRect(C - 5, R - 5, 10, 10);
        }
        g.setColor(this);
        String s = "";
        if (showName()) // shows the tag with or without = ...
        {
            s = Prompt;
            if (showValue()) // =, if value shows and % is not surpressed
            {
                if (s.endsWith("_") && s.length() > 1) {
                    s = s.substring(0, s.length() - 1);
                } else {
                    if (!s.equals("")) s = s + " = ";
                }
            } else {
                s = Prompt;
            }
        }
        if (showValue()) // show the value
        {
            try {
                E.getValue();
                double x = round(CurrentValue);
                if (Slider) {
                    x = round(CurrentValue, 100);
                }
                if (Math.abs(x - Math.round(x)) < 1e-10) {
                    s=s+JGlobals.getLocaleNumber((int)x, "edit");
//                    s = s + (int) x;
                } else {
                    s=s+JGlobals.getLocaleNumber(x, "edit");
//                    s = s + x;
                }
            } catch (Exception e) {
                s = s + "???";
            }
        }
        s = s + Unit; // add optional unit
        s = AngleObject.translateToUnicode(s); // translate \a etc.
//                s=s.replace(".",",");

        
        if (s.equals("")) // nothing to show 
        {
            if (!Slider) {
                s = "-";
            }
        }
        W = fm.stringWidth(s);
        if (!s.equals("")) {
            setFont(g);
            R -= fm.getAscent();
            H = g.drawStringExtended(s, C, R);
            W=((MyGraphics13) g).getW();
        }
        if (Slider) // we draw an additional slider
        {
            int coffset = (int) (4 * zc.pointSize());
            R += H;
            g.drawLine(C, R + coffset / 2, C + 10 * coffset, R + coffset / 2);
            
            double d = getSliderPosition();
            int size = coffset / 4;
            double rslider = C + d * 10 * coffset;
            double cw = 2 * size + 2;

            if (getColorType() == THICK) {
                g.fillOval(rslider - size-1, R + coffset / 2 - size-1, cw, cw, true, false, this);
            } else {
                g.fillOval(rslider - size-1, R + coffset / 2 - size-1, cw, cw, new Color(250, 250, 250));
                g.setColor(this);
                g.drawOval(rslider - size-1, R + coffset / 2 - size-1, cw, cw);
            }
            // remember position
            SC = rslider - size;
            SR = R + coffset / 2 - size;
            SW = SH = 2 * size;
            SX = zc.x((int) C);
            SY = zc.y((int) R + coffset / 2 - size);
            SD = zc.x((int) C + 10 * coffset) - SX;
            
            
            
            R -= H;
            
        }
    }

    /**
     * Compute the relative position, the slider must be on according
     * to CurrentValue
     * @return 0 <= position <= 1
     */
    public double getSliderPosition() {
        try {
            double min = SMin.getValue();
            double max = SMax.getValue();
            double x = CurrentValue;
            if (min >= max) {
                Valid = false;
                return 0;
            }
            if (x < min) {
                x = min;
            }
            if (x > max) {
                x = max;
            }
            return (x - min) / (max - min);
        } catch (Exception e) {
            Valid = false;
            return 0;
        }
    }

    /**
     * Set the expression according to the relative value the slider is on.
     * 0 <= d <= 1.
     * @param d
     */
    public void setSliderPosition(double d) {
        try {
            double min = SMin.getValue();
            double max = SMax.getValue();
            if (min >= max) {
                Valid = false;
                return;
            }
            double value = min + d * (max - min);
            if (value < min) {
                value = min;
            }
            if (value > max) {
                value = max;
            }
            E.setValue(value); // kills expression and makes it a constant
        } catch (Exception e) {
            Valid = false;
        }
    }

    public boolean nearto(int x, int y, ZirkelCanvas zc) {
        DragSlider = false;
        
        
        
        if (Valid && !displays(zc)) {
            return false;
        }
        if (C <= x && x <= C + W && R <= y && y <= R + H) {
            return true;
        }
        if (SC <= x && SR <= y && SC + SW >= x && SR + SH >= y) {
            DragSlider = true;
            return true;
        }
        
        
        
        
        return false;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public Expression getExp(){
        return E;
    }

    public void move(double x, double y) {
        X = x;
        Y = y;
    }

    public void snap(ZirkelCanvas zc) {
        double d = zc.getGridSize() / 2;
        X = Math.round(X / d) * d;
        Y = Math.round(Y / d) * d;
    }

    public void round() {
        move(round(X, ZirkelCanvas.LengthsFactor),
                round(Y, ZirkelCanvas.LengthsFactor));
    }

    public void edit(ZirkelCanvas zc) {
        if (!rene.zirkel.Zirkel.IsApplet) {
            eric.JGlobals.EditObject(this);
            return;
        }
        ObjectEditDialog d = new ExpressionEditDialog(zc.getFrame(), zc, this);
        d.setVisible(true);
        zc.repaint();
        if (E != null && !E.isValid()) {
            Frame F = zc.getFrame();
            Warning w = new Warning(F, E.getErrorText(),
                    Zirkel.name("warning"), true);
            w.center(F);
            w.setVisible(true);
        }
        if ((EX != null && !EX.isValid())) {
            Frame F = zc.getFrame();
            Warning w = new Warning(F, EX.getErrorText(),
                    Zirkel.name("warning"), true);
            w.center(F);
            w.setVisible(true);
        } else if ((EY != null && !EY.isValid())) {
            Frame F = zc.getFrame();
            Warning w = new Warning(F, EY.getErrorText(),
                    Zirkel.name("warning"), true);
            w.center(F);
            w.setVisible(true);
        }
        validate();
        if (d.wantsMore()) {
            new EditConditionals(zc.getFrame(), this);
            validate();
        }
    }

    public void printArgs(XmlWriter xml) {
        if (Fixed && EX != null && EX.isValid()) {
            xml.printArg("x", EX.toString());
        } else {
            xml.printArg("x", "" + X);
        }
        if (Fixed && EY != null && EY.isValid()) {
            xml.printArg("y", EY.toString());
        } else {
            xml.printArg("y", "" + Y);
        }
        if (E != null) {
            xml.printArg("value", E.toString());
        } else {
            xml.printArg("value", "");
        }
        xml.printArg("prompt", Prompt);
        if (Fixed) {
            xml.printArg("fixed", "true");
        }
        if (Slider) {
            xml.printArg("slider", "true");
            xml.printArg("min", SMin.toString());
            xml.printArg("max", SMax.toString());
        }
    }

    public boolean equals(ConstructionObject o) {
        return false;
    }

    public void setExpression(String expr, Construction c)
            throws ConstructionException {
        E = new Expression(expr, c, this);
        updateText();
    }

    public void setFixed(String expr) {
        E = new Expression(expr, getConstruction(), this);
        updateText();
    }

    public String getExpression() {
        if (E != null) {
            return E.toString();
        } else {
            return "";
        }
    }

//    public Enumeration depending() {
//        DL.reset();
//        if (E != null) {
//            Enumeration e = E.getDepList().elements();
//            while (e.hasMoreElements()) {
//                DL.add((ConstructionObject) e.nextElement());
//            }
//        }
//        if (Fixed) {
//            Enumeration e;
//            if (EX != null) {
//                e = EX.getDepList().elements();
//                while (e.hasMoreElements()) {
//                    DL.add((ConstructionObject) e.nextElement());
//                }
//            }
//            if (EY != null) {
//                e = EY.getDepList().elements();
//                while (e.hasMoreElements()) {
//                    DL.add((ConstructionObject) e.nextElement());
//                }
//            }
//        }
//        return DL.elements();
//    }
    
    
    
    
    
    
    public Enumeration depending() {
        DL.reset();
        addDepending(E);
        addDepending(SMin);
        addDepending(SMax);
        if (Fixed) {
           addDepending(EX);
           addDepending(EY);
        }
        return DL.elements();
    }

    public void addDepending(Expression EE) {
        if (EE!=null) {
            Enumeration e=EE.getDepList().elements();
            while (e.hasMoreElements()) {
                DL.add((ConstructionObject) e.nextElement());
            }
        }
    }
    
    
    
    
    
    
    
    

    public double getValue()
            throws ConstructionException {
        if (!CurrentValueValid) {
            throw new InvalidException("");
        }
        return CurrentValue;
    }

    public String getPrompt() {
        return Prompt;
    }

    public void setPrompt(String prompt) {
        Prompt = prompt;
    }

    public void translate() {
        
        if (Slider) {
            SMin.translate();
            SMax.translate(); 
        }else{
            E.translate();
        }

        if (Fixed) {
            try {
                setFixed(EX.toString(), EY.toString());
                EX.translate();
                EY.translate();
            } catch (Exception e) {
            }
        }
        updateText();
    }

    public void validate() {
        try {
            CurrentValue = E.getValue();
            CurrentValueValid = true;
        } catch (Exception e) {
            CurrentValueValid = false;
        }
        Valid = true;
        if (Fixed && EX != null && EX.isValid()) {
            try {
                X = EX.getValue();
            } catch (Exception e) {
                Valid = false;
                return;
            }
        }
        if (Fixed && EY != null && EY.isValid()) {
            try {
                Y = EY.getValue();
            } catch (Exception e) {
                Valid = false;
                return;
            }
        }
    }

    public void setFixed(boolean flag) {
        Fixed = flag;
        if (!Fixed) {
            EX = EY = null;
        }
        updateText();
    }

    public void setFixed(String x, String y) {
        Fixed = true;
        EX = new Expression(x, getConstruction(), this);
        EY = new Expression(y, getConstruction(), this);
        updateText();
    }

    public ConstructionObject copy(double x, double y) {
        try {
            ExpressionObject o = (ExpressionObject) clone();
            setTranslation(o);
            
            if (Slider){
                o.setSlider(SMin.toString(), SMax.toString());
            }else{
                o.setExpression(E.toString(), getConstruction());
            }
            
            o.translateConditionals();
            o.translate();
            o.setName();
            o.updateText();
            o.setBreak(false);
            o.setTarget(false);
            return o;
        } catch (Exception e) {
            return null;
        }
    }

	
    public boolean moveable() {
        if (Slider) {
            return true;
        }
        return EX == null && EY == null;
    }

    public void reset() {
        if (E != null) {
            E.reset();
        }
    }

    public boolean fixed() {
        return Fixed;
    }

    public String getEX() {
        if (EX != null) {
            return EX.toString();
        } else {
            return "" + round(X);
        }
    }

    public String getEY() {
        if (EY != null) {
            return EY.toString();
        } else {
            return "" + round(Y);
        }
    }

    public void setCurrentValue(double x) {
        CurrentValue = x;
        CurrentValueValid = true;
    }
    // For the simulate function:
    private double OldE;

    /**
     * Set the simulation value, remember the old value.
     */
    public void setSimulationValue(double x) {
        OldE = CurrentValue;
        CurrentValue = x;
    }

    /**
     * Reset the old value.
     */
    public void resetSimulationValue() {
        CurrentValue = OldE;
    }

    /**
     * Set the slider to min, max und step values.
     * @param smin
     * @param smax
     * @param sstep
     */
    public void setSlider(String smin, String smax) {
        Slider = true;
        SMin = new Expression(smin, getConstruction(), this);
        SMax = new Expression(smax, getConstruction(), this);
    }

    /**
     * Set or clear the slider.
     * @param flag
     */
    public void setSlider(boolean flag) {
        Slider = flag;
    }
    double oldx, oldy, startx, starty;
    boolean DragSlider;

    public void startDrag(double x, double y) {
        oldx = X;
        oldy = Y;
        startx = x;
        starty = y;
    }

    public void dragTo(double x, double y) {
        if (DragSlider) {
            setSliderPosition((x - SX) / SD);
        } else {
            move(oldx + (x - startx), oldy + (y - starty));
        }
    }

    public double getOldX() {
        return oldx;
    }

    public double getOldY() {
        return oldy;
    }

    public boolean isSlider() {
        return Slider;
    }
    


    public String getMin() {
        if (Slider) {
            return SMin.toString();
        } else {
            return ("-5");
        }
    }
    

    public String getMax() {
        if (Slider) {
            return SMax.toString();
        } else {
            return ("5");
        }
    }

    public String getDisplayValue() {
        
        String s = "";
        try {
            E.getValue();
            double x = round(CurrentValue);
            if (Slider) {
                x = round(CurrentValue, 100);
            }
            if (Math.abs(x - Math.round(x)) < 1e-10) {
                
                s = s + (int) x;
            } else {
                s = s + x;
            }
        } catch (Exception e) {
            s = s + "???";
        }
        s = s + Unit; // add optional unit
        return s;
    }

    public String getEquation() {
        if (E == null) {
            return "???";
        } else {
            return E.toString();
        }
    }
    
    public boolean isDriverObject(){
        return true;
    }

    public boolean somethingChanged() {
        return (!getEquation().equals(LASTE));
    }

    public void clearChanges() {
        LASTE=getEquation();
    }
}
