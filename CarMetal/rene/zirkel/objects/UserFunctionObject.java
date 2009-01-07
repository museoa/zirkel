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

// file: Functionbject.java
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.swing.JPanel;

import rene.dialogs.Warning;
import rene.gui.Global;
import rene.gui.IconBar;
import rene.gui.MyLabel;
import rene.gui.TextFieldAction;
import rene.util.xml.XmlWriter;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Count;
import rene.zirkel.dialogs.EditConditionals;
import rene.zirkel.dialogs.ObjectEditDialog;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.InvalidException;
import rene.zirkel.graphics.MyGraphics;
import eric.bar.JProperties;

class FunctionEditDialog extends ObjectEditDialog {

    TextField Var, EY, X, Y;
    IconBar IC;
    ZirkelCanvas ZC;
    Checkbox Fixed;

    public FunctionEditDialog(ZirkelCanvas zc, UserFunctionObject o) {
        super(zc.getFrame(), Zirkel.name("edit.function.title"), o, "function");
        ZC=zc;
    }

    public void addFirst(JPanel P) {
        UserFunctionObject f=(UserFunctionObject) O;

        Var=new TextFieldAction(this, "var", ""+f.getVar(), 30);
        P.add(new MyLabel(Zirkel.name("function.vars")));
        P.add(Var);
        EY=new TextFieldAction(this, "ey", ""+f.getEY(), 30);
        P.add(new MyLabel(Zirkel.name("function.f")));
        P.add(EY);

        X=new TextFieldAction(this, "X", ""+f.round(f.getX()));
        P.add(new MyLabel(Zirkel.name("edit.point.x")));
        P.add(X);
        Y=new TextFieldAction(this, "Y", ""+f.round(f.getY()));
        P.add(new MyLabel(Zirkel.name("edit.point.y")));
        P.add(Y);

        Fixed=new Checkbox("");
        Fixed.setState(f.fixed());
        P.add(new MyLabel(Zirkel.name("edit.fixed")));
        P.add(Fixed);
        if (f.fixed()) {
            X.setText(f.getEXpos());
            Y.setText(f.getEYpos());
        }

    }

    public void setAction() {
        UserFunctionObject f=(UserFunctionObject) O;
        f.setExpressions(Var.getText(), EY.getText());
        try {
            double x=new Double(X.getText()).doubleValue();
            double y=new Double(Y.getText()).doubleValue();
            f.move(x, y);
        } catch (Exception e) {
        }
        if (Fixed.getState()==true) {
            f.setFixed(X.getText(), Y.getText());
        } else {
            try {
                double x=new Expression(X.getText(),
                        f.getConstruction(), f).getValue();
                double y=new Expression(Y.getText(),
                        f.getConstruction(), f).getValue();
                f.move(x, y);
                f.setFixed(false);
            } catch (Exception e) {
            }
        }
    }

    public void focusGained(FocusEvent e) {
        Var.requestFocus();
    }
}

/**
 * @author Rene
 * 
 * This class is for function of several variables. Those
 * functions cannot be drawn at all.
 */
public class UserFunctionObject extends ConstructionObject
        implements MoveableObject,DriverObject, Evaluator {
    static Count N=new Count();
    Expression EY=null;
    double X[]={0};
    String Var[]={"x"};
    protected double Xpos,  Ypos;
    protected boolean Fixed;
    protected Expression EXpos,  EYpos;
    String LASTE="";

    public UserFunctionObject(Construction c) {
        super(c);
        validate();
        updateText();
        N.reset();
    }

    public void setDefaults() {
        setShowName(Global.getParameter("options.text.shownames", false));
        setShowValue(Global.getParameter("options.text.showvalues", false));
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
        return "Function";
    }

    public int getN() {
        return N.next();
    }

    public void updateText() {
        setText(getDisplayValue());
    }
    
    public boolean isValid(){
        return Valid;
    }

    public void validate() {
        if (EY!=null) {
            Valid=EY.isValid();
        } else {
            Valid=false;
        }
        if (Fixed&&EXpos!=null&&EXpos.isValid()) {
            try {
                Xpos=EXpos.getValue();
            } catch (Exception e) {
                Valid=false;
                return;
            }
        }
        if (Fixed&&EYpos!=null&&EYpos.isValid()) {
            try {
                Ypos=EYpos.getValue();
            } catch (Exception e) {
                Valid=false;
                return;
            }
        }
    }

    public void setExpressions(String t, String ey) {
        StringTokenizer tok=new StringTokenizer(t);
        Var=new String[tok.countTokens()];
        X=new double[tok.countTokens()];
        int i=0;
        while (tok.hasMoreTokens()) {
            Var[i++]=tok.nextToken();
        }
        EY=new Expression(ey, getConstruction(), this, Var);
        validate();
    }

    public String getEY() {
        if (EY!=null) {
            return EY.toString();
        } else {
            return "0";
        }
    }
    double C, R, W, H;

    public void paint(MyGraphics g, ZirkelCanvas zc) {
        if (!Valid||mustHide(zc)) {
            return;
        }
        FontMetrics fm=g.getFontMetrics();
        H=fm.getHeight();
        C=zc.col(Xpos);
        R=zc.row(Ypos);
        g.setColor(this);
        setFont(g);
        String s=AngleObject.translateToUnicode(getDisplayValue());
        g.drawString(s, C, R);
        R-=H;
        W=fm.stringWidth(s);
    }

    public double getValue()
            throws ConstructionException {
        if (!Valid) {
            throw new InvalidException("exception.invalid");
        }
        return X[0];
    }

    public double getValue(String var)
            throws ConstructionException {
        if (!Valid) {
            throw new InvalidException("exception.invalid");
        }
        for (int i=0; i<Var.length; i++) {
            if (var.equals(Var[i])) {
                return X[i];
            }
        }
        return X[0];
    }

    public String getDisplayValue() {
        String s="";
        if (showName()) {
            if (getAlias()!=null) {
                s=getAlias()+" : ";
            }
            s=s+getName()+"("+Var[0];
            for (int i=1; i<Var.length; i++) {
                s=s+","+Var[i];
            }
            s=s+")";
            if (showValue()) {
                s=s+"=";
            }
        }
        if (showValue()) {
            s=s+((EY==null)?"":JProperties.Point_To_Comma(EY.toString(),Cn,true));
        }
        return s;
    }

    public boolean nearto(int cc, int rr, ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        return C<=cc&&R<=rr&&cc<=C+W&&rr<=R+H;
    }
    public boolean EditAborted;

    public void edit(ZirkelCanvas zc) {
        ObjectEditDialog d;
        if (!rene.zirkel.Zirkel.IsApplet) {
            EditAborted=false;
            eric.JGlobals.EditObject(this);
            return;
        }
        while (true) {
            d=new FunctionEditDialog(zc, this);
            d.setVisible(true);
            EditAborted=false;
            if (d.isAborted()) {
                EditAborted=true;
                break;
            } else if (!EY.isValid()) {
                Frame F=zc.getFrame();
                Warning w=new Warning(F, EY.getErrorText(),
                        Zirkel.name("warning"), true);
                w.center(F);
                w.setVisible(true);
            }
            if ((EXpos!=null&&!EXpos.isValid())) {
                Frame F=zc.getFrame();
                Warning w=new Warning(F, EXpos.getErrorText(),
                        Zirkel.name("warning"), true);
                w.center(F);
                w.setVisible(true);
            } else if ((EYpos!=null&&!EYpos.isValid())) {
                Frame F=zc.getFrame();
                Warning w=new Warning(F, EYpos.getErrorText(),
                        Zirkel.name("warning"), true);
                w.center(F);
                w.setVisible(true);
            } else {
                break;
            }
        }
        validate();
        updateText();
        zc.getConstruction().updateCircleDep();
        zc.repaint();
        if (d.wantsMore()) {
            new EditConditionals(zc.getFrame(), this);
            validate();
        }
    }

    public void printArgs(XmlWriter xml) {
        xml.printArg("f", EY.toString());
        if (Fixed&&EXpos!=null&&EXpos.isValid()) {
            xml.printArg("x", EXpos.toString());
        } else {
            xml.printArg("x", ""+Xpos);
        }
        if (Fixed&&EYpos!=null&&EYpos.isValid()) {
            xml.printArg("y", EYpos.toString());
        } else {
            xml.printArg("y", ""+Ypos);
        }
        if (Fixed) {
            xml.printArg("fixed", "true");
        }
        xml.printArg("var", getVar());
    }

    public void translate() {
        try {
            EY=new Expression(EY.toString(), getConstruction(), this, Var);
            ConstructionObject O=getTranslation();
            setTranslation(this);
            if (Fixed) {
                try {
                    setFixed(EXpos.toString(), EYpos.toString());
                    EXpos.translate();
                    EYpos.translate();
                } catch (Exception e) {
                }
            }
            validate();
            setTranslation(O);
        } catch (Exception e) {
            System.out.println();
            System.out.println(getName());
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void setFixed(boolean flag) {
        Fixed=flag;
        if (!Fixed) {
            EXpos=EYpos=null;
        }
        updateText();
    }

    public void setFixed(String x, String y) {
        Fixed=true;
        EXpos=new Expression(x, getConstruction(), this);
        EYpos=new Expression(y, getConstruction(), this);
        updateText();
    }

    public boolean fixed() {
        return Fixed;
    }

    public String getEXpos() {
        if (EXpos!=null) {
            return EXpos.toString();
        } else {
            return ""+round(Xpos);
        }
    }

    public String getEYpos() {
        if (EYpos!=null) {
            return EYpos.toString();
        } else {
            return ""+round(Ypos);
        }
    }

    public boolean onlynearto(int x, int y, ZirkelCanvas zc) {
        return false;
    //return nearto(x,y,zc);
    }

    public boolean equals(ConstructionObject o) {
        return false;
    }

    public Enumeration depending() {
        DL.reset();
        addDepending(EY);
        if (Fixed) {
            addDepending(EXpos);
            addDepending(EYpos);
        }
        return DL.elements();
    }

    public void addDepending(Expression E) {
        if (E!=null) {
            Enumeration e=E.getDepList().elements();
            while (e.hasMoreElements()) {
                DL.add((ConstructionObject) e.nextElement());
            }
        }
    }

    public boolean hasUnit() {
        return false;
    }

    public double evaluateF(double x[])
            throws ConstructionException {
        int n=x.length;
        if (n>X.length) {
            n=X.length;
        }
        for (int i=0; i<n; i++) {
            X[i]=x[i];
        }
        for (int i=n; i<X.length; i++) {
            X[i]=0;
        }
        try {
            return EY.getValue();
        } catch (Exception e) {
            throw new ConstructionException("");
        }
    }

    public double evaluateF(double x)
            throws ConstructionException {
        X[0]=x;
        for (int i=1; i<X.length; i++) {
            X[i]=0;
        }
        try {
            return EY.getValue();
        } catch (Exception e) {
            throw new ConstructionException("");
        }
    }

    public double evaluateF(double x, double y)
            throws ConstructionException {
        X[0]=x;
        X[1]=y;
        for (int i=2; i<X.length; i++) {
            X[i]=0;
        }
        try {
            return EY.getValue();
        } catch (Exception e) {
            throw new ConstructionException("");
        }
    }

    public boolean maybeTransparent() {
        return true;
    }

    public boolean canDisplayName() {
        return true;
    }

    public boolean isFilledForSelect() {
        return false;
    }

    public String getVar() {
        String vars=Var[0];
        for (int i=1; i<Var.length; i++) {
            vars=vars+" "+Var[i];
        }
        return vars;
    }

    public void dragTo(double x, double y) {
        move(oldx+(x-startx), oldy+(y-starty));
    }

    public void move(double x, double y) {
        Xpos=x;
        Ypos=y;
    }
    double oldx, oldy, startx, starty;

    public boolean moveable() {
        return !Fixed;
    }

    public void startDrag(double x, double y) {
        oldx=Xpos;
        oldy=Ypos;
        startx=x;
        starty=y;
    }

    public double getOldX() {
        return oldx;
    }

    public double getOldY() {
        return oldy;
    }

    public double getX() {
        return Xpos;
    }

    public double getY() {
        return Ypos;
    }

    public boolean isDriverObject(){
        return true;
    }

    public boolean somethingChanged() {
        return (!EY.toString().equals(LASTE));
    }

    public void clearChanges() {
        LASTE=EY.toString();
    }
}
