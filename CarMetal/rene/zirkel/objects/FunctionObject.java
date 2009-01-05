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
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import rene.gui.*;
import rene.util.xml.*;
import rene.zirkel.*;
import rene.zirkel.construction.*;
import rene.zirkel.dialogs.*;
import rene.zirkel.expression.*;
import rene.zirkel.graphics.*;
import rene.dialogs.*;
import rene.zirkel.structures.Coordinates;

class CurveEditDialog extends ObjectEditDialog {

    TextField VarMin, VarMax, DVar, Var, EX, EY;
    IconBar IC, TypeIB;
    ZirkelCanvas ZC;
    Checkbox Special;

    public CurveEditDialog(ZirkelCanvas zc, FunctionObject o) {
        super(zc.getFrame(), Zirkel.name("edit.function.title"), o, "function");
        ZC = zc;
    }

    public void addFirst(JPanel P) {
        FunctionObject f = (FunctionObject) O;

        VarMin = new TextFieldAction(this, "varmin", "" + f.VarMin, 30);
        P.add(new MyLabel(Zirkel.name("function.varmin")));
        P.add(VarMin);
        VarMax = new TextFieldAction(this, "varmax", "" + f.VarMax, 30);
        P.add(new MyLabel(Zirkel.name("function.varmax")));
        P.add(VarMax);
        DVar = new TextFieldAction(this, "dvar", "" + f.DVar, 30);
        P.add(new MyLabel(Zirkel.name("function.dvar")));
        P.add(DVar);

        Var = new TextFieldAction(this, "var", "" + f.getVar(), 30);
        P.add(new MyLabel(Zirkel.name("function.var")));
        P.add(Var);
        String ex = f.getEX();
        if (ex.equals(f.Var)) {
            ex = "";
        }
        EX = new TextFieldAction(this, "ex", "" + ex, 30);
        P.add(new MyLabel(Zirkel.name("function.x")));
        P.add(EX);
        EY = new TextFieldAction(this, "ey", "" + f.getEY(), 30);
        P.add(new MyLabel(Zirkel.name("function.y")));
        P.add(EY);

    }

    public void addSecond(JPanel P) {
        FunctionObject Func = (FunctionObject) O;

        IC = new IconBar(F);
        IC.setIconBarListener(this);
        IC.addOnOffLeft("filled");
        IC.setState("filled", Func.isFilled());
        IC.setIconBarListener(this);
        P.add(new MyLabel(""));
        P.add(IC);

        TypeIB = new IconBar(F);
        TypeIB.addToggleGroupLeft("type", 6);
        TypeIB.toggle("type", Func.getType());
        P.add(new MyLabel(""));
        P.add(TypeIB);

        P.add(new MyLabel(Zirkel.name("edit.discrete")));
        P.add(Special = new Checkbox());
        Special.setState(Func.isSpecial());
    }

    public void addButton(JPanel P) {
        FunctionObject f = (FunctionObject) O;
        if (!f.Var.equals(f.EX)) {
            if (f.Center == null) {
                P.add(new ButtonAction(this, Zirkel.name("edit.function.center"),
                        "SetCenter"));
            } else {
                P.add(new ButtonAction(this, Zirkel.name("edit.function.free"),
                        "SetFree"));
            }
            P.add(new MyLabel(" "));
        }
    }

    public void iconPressed(String o) {
        if (o.equals("filled")) {
            if (IC.getState("filled")) {
                IB.setState("isback", true);
                ThicknessIB.setEnabled("solid", true);
            } else {
                IB.setState("isback", false);
                ThicknessIB.setState("solid", false);
                ThicknessIB.setEnabled("solid", false);
            }
        }
        super.iconPressed(o);
    }

    public void setAction() {
        FunctionObject f = (FunctionObject) O;
        f.setExpressions(Var.getText(), EX.getText(), EY.getText());
        f.setRange(VarMin.getText(), VarMax.getText(), DVar.getText());
        f.setFilled(IC.getState("filled"));
        f.setType(TypeIB.getToggleState("type"));
        f.setSpecial(Special.getState());
    }

    public void doAction(String o) {
        if (o.equals("SetCenter")) {
            ZC.setCurveCenter((FunctionObject) O);
            super.doAction("OK");
        } else if (o.equals("SetFree")) {
            ((FunctionObject) O).Center = null;
            super.doAction("OK");
        } else {
            super.doAction(o);
        }
    }

    public void focusGained(FocusEvent e) {
        VarMin.requestFocus();
    }
}

/**
 * @author Rene
 *
 * Function objects are parametric or cartesian curves.
 * For parametric functions, depending on a parameter t ,
 * x(t) and y(t) are computed and drawn on the screen.
 * For cartesian functions, the parameter x will be used.
 *
 */
public class FunctionObject extends ConstructionObject
        implements PointonObject, HeavyObject, DriverObject, Evaluator {

    Expression EX = null, EY = null;
    public Expression VarMin = null, VarMax = null, DVar = null;
    String LASTEX = "", LASTEY = "", LASTVarMin = "", LASTVarMax = "", LASTDVar = "";
    double X[] = {0};
    public String Var[] = {"x"};
    boolean Filled = false;
    Expression Center = null;
    protected int Type = 0;
    public final static int SQUARE = 0, DIAMOND = 1, CIRCLE = 2, DOT = 3, CROSS = 4, DCROSS = 5;
    protected boolean Special = false;
//    Vector RefreshList=new Vector();
    public Vector V = new Vector();
    double cx, cy, ww, wh;
    //this is a main object list which tells if the object needs to recompute :
    Vector DriverObjectList = new Vector();
    double c0, r0, c, r;

    /**
     * Functions are HeavyObjects which means we always must see if it
     * really neads to recompute. Computing a function can be time consuming,
     * but once it's computed, the paint method is fast enough.
     * @param c
     */
    public FunctionObject(Construction c) {
        super(c);
        VarMin = new Expression("windowcx-windoww", c, this);
        VarMax = new Expression("windowcx+windoww", c, this);
        DVar = new Expression("0", c, this);
        validate();
        updateText();
        cx = c.getX();
        cy = c.getY();
        ww = c.getW();
        wh = c.getH();
        Type = CROSS;

    }

    public void setDefaults() {
        setShowName(Global.getParameter("options.locus.shownames", false));
        setShowValue(Global.getParameter("options.locus.showvalues", false));
        setColor(Global.getParameter("options.locus.color", 0), Global.getParameter("options.locus.pcolor", (Color) null));
        setColorType(Global.getParameter("options.locus.colortype", 0));
        setFilled(Global.getParameter("options.locus.filled", false));
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
        setSolid(Cn.Solid);
        setLarge(Cn.LargeFont);
        setBold(Cn.BoldFont);

    }

    public void setTargetDefaults() {
        setShowName(Global.getParameter("options.locus.shownames", false));
        setShowValue(Global.getParameter("options.locus.showvalues", false));
        setColor(Global.getParameter("options.locus.color", 0), Global.getParameter("options.locus.pcolor", (Color) null));
        setColorType(Global.getParameter("options.locus.colortype", 0));
        setFilled(Global.getParameter("options.locus.filled", false));
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
        setSolid(Cn.Solid);
        setLarge(Cn.LargeFont);
        setBold(Cn.BoldFont);
    }

    /**
     * In order to see if the function must be recomputed or not, we have
     * to register, in the DriverObjectList, all objects the function depends on.
     * There are many possibilities for an object to be in this list : if it's used
     * in the def of the function, if it's in the min or max text box, etc...
     * @param c
     */
    public void searchDependencies(Construction c) {
        DriverObjectList.clear();
        if (RekValidating) {
            return;
        } // should not happen!
        RekValidating = true;
        Enumeration e = c.elements();
        while (e.hasMoreElements()) {
            ((ConstructionObject) e.nextElement()).setRekFlag(false);
        }
        ConstructionObject oEX[] = EX.getDepList().getArray();
        ConstructionObject oEY[] = EY.getDepList().getArray();
        ConstructionObject oVarMin[] = VarMin.getDepList().getArray();
        ConstructionObject oVarMax[] = VarMax.getDepList().getArray();
        ConstructionObject oDVar[] = DVar.getDepList().getArray();


        for (int i = 0; i < oEX.length; i++) {
            recursiveSearchDependencies((ConstructionObject) oEX[i]);
        }
        for (int i = 0; i < oEY.length; i++) {
            recursiveSearchDependencies((ConstructionObject) oEY[i]);
        }
        for (int i = 0; i < oVarMin.length; i++) {
            recursiveSearchDependencies((ConstructionObject) oVarMin[i]);
        }
        for (int i = 0; i < oVarMax.length; i++) {
            recursiveSearchDependencies((ConstructionObject) oVarMax[i]);
        }
        for (int i = 0; i < oDVar.length; i++) {
            recursiveSearchDependencies((ConstructionObject) oDVar[i]);
        }

        e = c.elements();
        while (e.hasMoreElements()) {
            ConstructionObject oc = (ConstructionObject) e.nextElement();
            if ((oc.isRekFlag()) && (oc.isDriverObject())) {
                DriverObjectList.addElement(oc);
            }
        }
        RekValidating = false;
        NeedsRecompute = true;
    }

    /**
     * Recursive method called by the searchDependencies method
     * @param o
     */
    public void recursiveSearchDependencies(ConstructionObject o) {

        if (o.isRekFlag()) {
            return;
        }
        o.setRekFlag(true);
        ConstructionObject d[] = o.getDepArray();
        for (int i = 0; i < d.length; i++) {
            recursiveSearchDependencies(d[i]);
        }
    }

    /**
     * Time consuming method which is called only if it's really necessary :
     */
    public void compute() {
        // Empty the vector which contains the point set of the plot :
        V.clear();
        if (!Valid) {
            return;
        }

        // Initialisation of varmin, varmax and d (the step variable) :
        double varmin, varmax, d;
        try {
            varmin = VarMin.getValue();
            varmax = VarMax.getValue();
            d = DVar.getValue();
            if (varmin > varmax) {
                double h = varmin;
                varmin = varmax;
                varmax = h;
            }
            if (d < 0) {
                d = -d;
            }

        } catch (Exception e) {
            Valid = false;
            return;
        }

        // X[0] represents the 't' variable for parametric plots.
        // When you give X[0] a t value, EX.getValue() and EY.getValue()
        // automatically returns the x(t) and y(t) value.
        // If it's a cartesian function, X[0] represents the 'x' value
        // then EX.getValue() always returns x and EY.getValue() returns
        // the f(x) value.
        X[0] = varmin;

        // If the function may have discontinuity problems, computing is
        // slower, because it checks for each point if a segment will have
        // to be displayed or not :
        if (mayHaveDiscontinuityPb()) {
            if (d == 0) {
                try {
                    // if the user leaves the step text box empty in the
                    // properties bar, the step d will represents only one pixel :
                    d = new Expression("1/pixel", getConstruction(), this).getValue();
                } catch (Exception ex) {
                }
            // if the step d defined by user is too small, then correct it :
            } else if (d < (varmax - varmin) / 1000) {
                d = (varmax - varmin) / 1000;
            }

            double x1 = 0, y1 = 0, x2 = 0, y2 = 0, xM = 0, yM = 0, X0 = 0;
            int nbsteps = (int) Math.round((varmax - varmin) / d) + 1;

            try {
                x1 = EX.getValue();
                y1 = EY.getValue();
                V.add(new Coordinates(x1, y1, false));
            } catch (Exception ex) {
            }

            /* A(x1,y1=f(x1)) is the first point. B(x2,y2=f(x2)) is the second point.
             * In order to know if a segment must join these two points,
             * we compute the point M(xM,yM=f(xM)), where xM=(x1+x2)/2 :
             */
            for (int i = 1; i < nbsteps; i++) {
                try {
                    X0 = X[0];
                    X[0] += d / 2;
                    xM = EX.getValue();
                    yM = EY.getValue();
                    X[0] += d / 2;
                    x2 = EX.getValue();
                    y2 = EY.getValue();

                    // If A,M and B are on the same horizontal line
                    // then segment must be drawn (true) :
                    if ((y1 == yM) && (y2 == yM)) {
                        V.add(new Coordinates(x2, y2, true));

                    // if f(xM) is in open interval ]f(x1),f(x2)[ :
                    } else if (((yM > y1) && (yM < y2)) || ((yM > y2) && (yM < y1))) {

                        double mm = Math.abs(y1 - yM) / Math.abs(y2 - yM);

                        // This is a weird thing, but it seems to work in
                        // lots of "basic" situations :
                        // If the distance |yM-y1| (or |yM-y2|)
                        // represents 10% of the distance |yM-y2| (or |yM-y1|)
                        // then may be it's a discontinuity problem, so
                        // don't draw the segment [AB] (false)
                        if ((mm < 0.1) || (mm > 10)) {
                            V.add(new Coordinates(x2, y2, false));
                        // Otherwise draw the segment [AB] :
                        } else {
                            V.add(new Coordinates(x2, y2, true));
                        }
                    // if f(xM) is not in interval [f(x1),f(x2)], don't
                    // draw the segment [AB] (false) :
                    } else {
                        V.add(new Coordinates(x2, y2, false));
                    }

                    x1 = x2;
                    y1 = y2;
                } catch (Exception ex) {
                    try {
                        X[0] = X0 + d;
                        x1 = EX.getValue();
                        y1 = EY.getValue();
                        V.add(new Coordinates(x1, y1, false));
                    } catch (Exception ex1) {
                    }
                }
            }
        } else {
            if (d == 0) {
                d = (varmax - varmin) / 100;
            } else if (d < (varmax - varmin) / 1000) {
                d = (varmax - varmin) / 1000;
            }
            int nbsteps = (int) Math.round((varmax - varmin) / d) + 1;
            for (int i = 0; i < nbsteps; i++) {
                try {
                    V.add(new Coordinates(EX.getValue(), EY.getValue()));
                } catch (Exception ex) {
                }
                X[0] += d;
            }
        }
    }

    public void setNeedsToRecompte(boolean n) {
        NeedsRecompute = n;
    }

    /**
     * Check all objects the function depends on.
     * If one of the have changed, this function return true.
     * @return
     */
    public boolean needsToRecompute() {
        boolean needs = false;
        Enumeration pl = DriverObjectList.elements();
        while (pl.hasMoreElements()) {
            DriverObject oc = (DriverObject) pl.nextElement();
            if (oc.somethingChanged()) {
                // There is a "ClearList" which will be cleared
                // at the end of the Construction.computeHeavyObjects method :
                Global.addClearList(oc);
                needs = true;
            }
        }

        // Also needs to compute when user zoom or move figure in the window :
        if ((Cn.getX() != cx) || (Cn.getY() != cy) || (Cn.getW() != ww) || (Cn.getH() != wh)) {
            cx = Cn.getX();
            cy = Cn.getY();
            ww = Cn.getW();
            wh = Cn.getH();
            needs = true;
        }

        if (NeedsRecompute) {
            NeedsRecompute = false;
            return true;
        }

        return needs;
    }

    public void setFilled(boolean flag) {
        Filled = flag;
    }

    public boolean isFilled() {
        return Filled;
    }

    public String getTag() {
        return "Function";
    }

    public int getN() {
        return N.next();
    }

    public void updateText() {
        if (EX != null && EY != null) {
            setText(text2(Zirkel.name("text.function"), EX.toString(), EY.toString()));
        } else {
            setText(text2(Zirkel.name("text.function"), "", ""));
        }
    }

    public void validate() {
        if (EX != null && EY != null) {
            Valid = EX.isValid() && EY.isValid() && VarMin.isValid() && VarMax.isValid() && DVar.isValid();
        } else {
            Valid = false;
        }
    }

    public void setExpressions(String t, String ex, String ey) {

        StringTokenizer tok = new StringTokenizer(t);
        Var = new String[tok.countTokens()];
        X = new double[tok.countTokens()];
        int i = 0;
        while (tok.hasMoreTokens()) {
            Var[i++] = tok.nextToken();
        }
        if (ex.equals("")) {
            ex = Var[0];
        }
        EX = new Expression(ex, getConstruction(), this, Var);
        EY = new Expression(ey, getConstruction(), this, Var);
        validate();
        searchDependencies(Cn);
    }

    public boolean isCartesian() {
        return EX.toString().equals("x");
    }

    public boolean mayHaveDiscontinuityPb() {
        String Pbs[] = {"floor(", "ceil(", "tan(", "sign("};
        // Continuity pbs are only checked for cartesian functions :
        if (EX.toString().equals("x")) {
            for (int i = 0; i < Pbs.length; i++) {
                if ((EY.toString().indexOf(Pbs[i]) != -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setRange(String min, String max, String d) {
        VarMin = new Expression(min, getConstruction(), this);
        VarMax = new Expression(max, getConstruction(), this);
        DVar = new Expression(d, getConstruction(), this);
        searchDependencies(Cn);
    }

    public String getEX() {
        if (EX != null) {
            return EX.toString();
        } else {
            return Var[0];
        }
    }

    public String getEY() {
        if (EY != null) {
            return EY.toString();
        } else {
            return "0";
        }
    }
    FunctionPolygonFiller PF = null;
    double C1, C2;
    int C, R, W, H;

    public void paint(MyGraphics g, ZirkelCanvas zc) {
        if (!Valid || mustHide(zc)) {
            return;
        }
        Coordinates C = null;
        Enumeration e = V.elements();
        double c, r;
        g.setColor(this);

        if (Special) {
            if (Filled) {
                PF = new FunctionPolygonFiller(g, this, zc.getY(), zc.getY() + zc.getHeight());
                while (e.hasMoreElements()) {
                    C = (Coordinates) e.nextElement();
                    PF.add(zc.col(C.X), zc.row(C.Y));
                }
                PF.fillPolygon(zc.row(0));
                e = V.elements();
                g.setColor(this);
            }

            while (e.hasMoreElements()) {
                C = (Coordinates) e.nextElement();
                PointObject.drawPoint(g, zc, this, C.X, C.Y, Type);
            }
        } else if (Tracked) {
            zc.UniversalTrack.TrackIG.setColor(this);
            zc.UniversalTrack.setActive(true);
            PolygonDrawer pd = new PolygonDrawer(g, this);
            PolygonDrawer pdt = new PolygonDrawer(zc.UniversalTrack.TrackIG, this);
            if (e.hasMoreElements()) {
                C = (Coordinates) e.nextElement();
                c0 = zc.col(C.X);
                r0 = zc.row(C.Y);
                pd.startPolygon(c0, r0);
                pdt.startPolygon(c0, r0);
            }
            while (e.hasMoreElements()) {
                C = (Coordinates) e.nextElement();
                c = zc.col(C.X);
                r = zc.row(C.Y);
                if (Math.abs(pd.c() - c) < 1000 && Math.abs(pd.r() - r) < 1000) {
                    pd.drawTo(c, r);
                    pdt.drawTo(c, r);
                } else {
                    pd.finishPolygon();
                    pdt.finishPolygon();
                    pd.startPolygon(c, r);
                    pdt.startPolygon(c, r);
                }
            }
            pd.finishPolygon();
            pdt.finishPolygon();
        } else {
            if (Filled) {
                PF = new FunctionPolygonFiller(g, this, zc.getY(), zc.getY() + zc.getHeight());
                while (e.hasMoreElements()) {
                    C = (Coordinates) e.nextElement();
                    PF.add(zc.col(C.X), zc.row(C.Y));
                }
                PF.fillPolygon(zc.row(0));
                e = V.elements();
                g.setColor(this);
            }
            PolygonDrawer pd = new PolygonDrawer(g, this);
            if (e.hasMoreElements()) {
                C = (Coordinates) e.nextElement();
                c0 = zc.col(C.X);
                r0 = zc.row(C.Y);
                pd.startPolygon(c0, r0);
            }
            while (e.hasMoreElements()) {
                C = (Coordinates) e.nextElement();
                c = zc.col(C.X);
                r = zc.row(C.Y);
                if (C.join) {
                    pd.drawTo(c, r);
                } else {
                    pd.finishPolygon();
                    pd.startPolygon(c, r);
                }
            }
            pd.finishPolygon();
        }
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
        for (int i = 0; i < Var.length; i++) {
            if (var.equals(Var[i])) {
                return X[i];
            }
        }
        return X[0];
    }

    public double getIntegral()
            throws ConstructionException {
        return getSum();
    }

    public String getDisplayValue() {
        if (getEX().equals(getVar())) {
            return EY.toString();
        } else {
            return "(" + EX.toString() + "," + EY.toString() + ")";
        }
    }

    // Mainly to select the track for delete
    public boolean nearto(int x, int y, ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        Enumeration e = V.elements();
        double xx = zc.x(x), yy = zc.y(y);
        double mymax = (7 / Cn.getPixel());
        if (Special) {
            Coordinates CS;
            while (e.hasMoreElements()) {
                CS = (Coordinates) e.nextElement();
                if ((Math.abs(CS.X - xx) < mymax) && (Math.abs(CS.Y - yy) < mymax)) {
                    return true;
                }
            }
        } else {
            double xA = 0, yA = 0, xB = 0, yB = 0;
            Coordinates CS0, CS1;
            if (e.hasMoreElements()) {
                CS0 = (Coordinates) e.nextElement();
                xA = CS0.X;
                yA = CS0.Y;
            }
            while (e.hasMoreElements()) {
                CS1 = (Coordinates) e.nextElement();
                xB = CS1.X;
                yB = CS1.Y;


                double p1 = (xx - xA) * (xB - xA) + (yy - yA) * (yB - yA);
                double p2 = (xx - xB) * (xA - xB) + (yy - yB) * (yA - yB);
                if ((p1 > 0) && (p2 > 0)) {
                    double aa = xB - xA, bb = yB - yA, cc = bb * xA - aa * yA;
                    double d = Math.abs(-bb * xx + aa * yy + cc) / Math.sqrt(aa * aa + bb * bb);
                    if (d < mymax) {
                        return true;
                    }
                }
                xA = xB;
                yA = yB;
            }
        }



        return false;
    }
    public boolean EditAborted;

    public void edit(ZirkelCanvas zc) {
        ObjectEditDialog d;
        if (!rene.zirkel.Zirkel.IsApplet) {
            eric.JGlobals.EditObject(this);
            return;
        }
        while (true) {
            d = new CurveEditDialog(zc, this);
            d.setVisible(true);
            EditAborted = false;
            if (d.isAborted()) {
                EditAborted = true;
                break;
            } else if (!EX.isValid()) {
                Frame F = zc.getFrame();
                Warning w = new Warning(F, EX.getErrorText(),
                        Zirkel.name("warning"), true);
                w.center(F);
                w.setVisible(true);
            } else if (!EY.isValid()) {
                Frame F = zc.getFrame();
                Warning w = new Warning(F, EY.getErrorText(),
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
        xml.printArg("x", EX.toString());
        xml.printArg("y", EY.toString());
        xml.printArg("var", getVar());
        xml.printArg("min", "" + VarMin);
        xml.printArg("max", "" + VarMax);
        xml.printArg("d", "" + DVar);
        if (Special) {
            xml.printArg("special", "true");
        }
        printType(xml);
        if (Filled) {
            xml.printArg("filled", "true");
        }
        if (getCenter() != null) {
            xml.printArg("center", getCenter().getName());
        }
    }

    public void setType(int type) {
        Type = type;
    }

    public int getType() {
        return Type;
    }

    public void printType(XmlWriter xml) {
        if (Type != 0) {
            switch (Type) {
                case DIAMOND:
                    xml.printArg("shape", "diamond");
                    break;
                case CIRCLE:
                    xml.printArg("shape", "circle");
                    break;
                case DOT:
                    xml.printArg("shape", "dot");
                    break;
                case CROSS:
                    xml.printArg("shape", "cross");
                    break;
                case DCROSS:
                    xml.printArg("shape", "dcross");
                    break;
            }
        }

    }

    public ConstructionObject copy(double x, double y) {


        FunctionObject fo = new FunctionObject(getConstruction());
        fo.copyProperties(this);


        fo.EX = new Expression(EX.toString(), getConstruction(), fo, Var);
        fo.EY = new Expression(EY.toString(), getConstruction(), fo, Var);
        fo.VarMin = new Expression(VarMin.toString(), getConstruction(), fo);
        fo.VarMax = new Expression(VarMax.toString(), getConstruction(), fo);
        fo.DVar = new Expression(DVar.toString(), getConstruction(), fo);
        fo.Special = Special;
        ConstructionObject O = getTranslation();
        fo.setTranslation(this);
        fo.EX.translate();
        fo.EY.translate();
        fo.VarMin.translate();
        fo.VarMax.translate();
        fo.DVar.translate();
        fo.translateConditionals();
        fo.X = X;
        fo.Var = Var;
        fo.validate();
        fo.setTranslation(O);
        fo.searchDependencies(Cn.getTranslation());
        return fo;
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
        addDepending(EX);
        addDepending(EY);
        addDepending(VarMin);
        addDepending(VarMax);
        addDepending(DVar);
        return DL.elements();
    }

    public void addDepending(Expression E) {
        if (E != null) {
            Enumeration e = E.getDepList().elements();
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
        int n = x.length;
        if (n > X.length) {
            n = X.length;
        }
        for (int i = 0; i < n; i++) {
            X[i] = x[i];
        }
        for (int i = n; i < X.length; i++) {
            X[i] = 0;
        }
        try {
            return EY.getValue();
        } catch (Exception e) {
            throw new ConstructionException("");
        }
    }

    public double evaluateF(double x)
            throws ConstructionException {
        X[0] = x;
        for (int i = 1; i < X.length; i++) {
            X[i] = 0;
        }
        try {
            return EY.getValue();
        } catch (Exception e) {

            throw new ConstructionException("");
        }
    }

    public int getDistance(PointObject P) {
        double varmin, varmax, dvar;
        try {
            varmin = VarMin.getValue();
            varmax = VarMax.getValue();
            dvar = DVar.getValue();
            if (varmin > varmax) {
                double h = varmin;
                varmin = varmax;
                varmax = h;
            }


            if (dvar < 0) {
                dvar = -dvar;
            }
            if (dvar == 0) {
                dvar = (varmax - varmin) / 100;
            } else if (dvar < (varmax - varmin) / 1000) {
                dvar = (varmax - varmin) / 1000;
            }

        } catch (Exception e) {
            Valid = false;
            return 1000;
        }


        try {
            // if it's a cartesian function, try to calculate the "real" coords :
            if ((getEX().equals("x"))) {
                double x = (P.getX() < varmin) ? varmin : P.getX();
                x = (P.getX() > varmax) ? varmax : x;
                double y = evaluateF(x);
                double dd = Math.sqrt((P.getX() - x) * (P.getX() - x) + (P.getY() - y) * (P.getY() - y));
                return (int) Math.round(dd * Cn.getPixel());
            }
        } catch (Exception e) {
        }



        try {
            // if it's a parmetric curve and function is just plot with points :
            if ((!getEX().equals("x")) && (Special)) {
                if (P.haveBoundOrder()) {
                    X[0] = P.getBoundOrder();
                    double dd = Math.sqrt((P.getX() - EX.getValue()) * (P.getX() - EX.getValue()) + (P.getY() - EY.getValue()) * (P.getY() - EY.getValue()));
                    return (int) Math.round(dd * Cn.getPixel());
                } else {
                    Coordinates CS;
                    Enumeration e = V.elements();
                    double delta0 = 0, delta1 = 0, xx = 0, yy = 0;
                    int i = 0, k = 0;
                    if (e.hasMoreElements()) {
                        CS = (Coordinates) e.nextElement();
                        delta0 = Math.abs(CS.X - P.getX()) + Math.abs(CS.Y - P.getY());
                        xx = CS.X;
                        yy = CS.Y;
                    }

                    while (e.hasMoreElements()) {
                        i++;
                        CS = (Coordinates) e.nextElement();
                        delta1 = Math.abs(CS.X - P.getX()) + Math.abs(CS.Y - P.getY());
                        if (delta1 < delta0) {
                            k = i;
                            delta0 = delta1;
                            xx = CS.X;
                            yy = CS.Y;

                        }
                    }
                    double dd = Math.sqrt((P.getX() - xx) * (P.getX() - xx) + (P.getY() - yy) * (P.getY() - yy));
                    return (int) Math.round(dd * Cn.getPixel());

                }

            }

        } catch (Exception e) {
            Valid = false;
            return 1000;
        }

        // Otherwise, at Least get the approx coordinates on the polygon :
        if (needsToRecompute()) {
            compute();
        }
        Enumeration e = V.elements();
        double x = 0, y = 0, x0 = 0, y0 = 0, dmin = 0;
        boolean started = false;
        while (e.hasMoreElements()) {
            Coordinates c = (Coordinates) e.nextElement();
            double x1 = c.X;
            double y1 = c.Y;
            if (!started) {
                dmin = Math.sqrt((P.getX() - x1) * (P.getX() - x1) +
                        (P.getY() - y1) * (P.getY() - y1));
                x0 = x = x1;
                y0 = y = y1;
                started = true;
            } else {
                if (c.flag) {
                    double h = (x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0);
                    if (h < 1e-10) {
                        h = 1e-10;
                    }
                    double g = (P.getX() - x0) * (x1 - x0) + (P.getY() - y0) * (y1 - y0);
                    if (g < 0) {
                        g = 0;
                    }
                    if (g > h) {
                        g = h;
                    }
                    double x2 = x0 + g / h * (x1 - x0), y2 = y0 + g / h * (y1 - y0);
                    double d = Math.sqrt((P.getX() - x2) * (P.getX() - x2) +
                            (P.getY() - y2) * (P.getY() - y2));
                    if (d < dmin) {
                        dmin = d;
                        x = x2;
                        y = y2;
                    }
                }
                x0 = x1;
                y0 = y1;
            }
        }
        if (started) {
            P.Valid = true;
            double dd = Math.sqrt((P.getX() - x) * (P.getX() - x) + (P.getY() - y) * (P.getY() - y));
            return (int) Math.round(dd * Cn.getPixel());

        } else {
            P.Valid = false;
        }
        return 1000;
    }

 public void project(PointObject P) {
        double varmin, varmax, dvar;
        try {



            varmin=VarMin.getValue();
            varmax=VarMax.getValue();
            dvar=DVar.getValue();
            if (varmin>varmax) {
                double h=varmin;
                varmin=varmax;
                varmax=h;
            }


            if (dvar<0) {
                dvar=-dvar;
            }
            if (dvar==0) {
                dvar=(varmax-varmin)/100;
            } else if (dvar<(varmax-varmin)/1000) {
                dvar=(varmax-varmin)/1000;
            }

        } catch (Exception e) {
            Valid=false;
            return;
        }



        try {
            // if P is a PointOn (a parmetric curve) and function is just plot with points :
            if ((!getEX().equals("x"))&&(P.isPointOnOrMagnet())&&(Special)) {
                if (P.haveBoundOrder()) {
                    X[0]=P.getBoundOrder();
                    P.setXY(EX.getValue(), EY.getValue());
                    return;
                } else {
                    Coordinates CS;
                    Enumeration e=V.elements();
                    double delta0=0, delta1=0, xx=0, yy=0;
                    int i=0, k=0;
                    if (e.hasMoreElements()) {
                        CS=(Coordinates) e.nextElement();
                        delta0=Math.abs(CS.X-P.getX())+Math.abs(CS.Y-P.getY());
                        xx=CS.X;
                        yy=CS.Y;
                    }

                    while (e.hasMoreElements()) {
                        i++;
                        CS=(Coordinates) e.nextElement();
                        delta1=Math.abs(CS.X-P.getX())+Math.abs(CS.Y-P.getY());
                        if (delta1<delta0) {
                            k=i;
                            delta0=delta1;
                            xx=CS.X;
                            yy=CS.Y;

                        }
                    }
                    P.setXY(xx, yy);
                    P.setBoundOrder(varmin+k*dvar);
                    return;
                }

            }

        } catch (Exception e) {
            Valid=false;
            return;
        }



        try {
            // if P is a PointOn (a cartesian function), try to calculate the "real" coords :
            if ((P.isPointOnOrMagnet())&&(getEX().equals("x"))) {
                double x=(P.getX()<varmin)?varmin:P.getX();
                x=(P.getX()>varmax)?varmax:x;
                double y=evaluateF(x);
                P.move(x, y);
                return;
            }
        } catch (Exception e) {
        }

        // Otherwise, at Least get the approx coordinates on the polygon :
        if (needsToRecompute()) {
            compute();
        }
        Enumeration e=V.elements();
        double x=0, y=0, x0=0, y0=0, dmin=0;
        boolean started=false;
        while (e.hasMoreElements()) {
            Coordinates c=(Coordinates) e.nextElement();
            double x1=c.X;
            double y1=c.Y;
            if (!started) {
                dmin=Math.sqrt((P.getX()-x1)*(P.getX()-x1)+
                        (P.getY()-y1)*(P.getY()-y1));
                x0=x=x1;
                y0=y=y1;
                started=true;
            } else {
                if (c.flag) {
                    double h=(x1-x0)*(x1-x0)+(y1-y0)*(y1-y0);
                    if (h<1e-10) {
                        h=1e-10;
                    }
                    double g=(P.getX()-x0)*(x1-x0)+(P.getY()-y0)*(y1-y0);
                    if (g<0) {
                        g=0;
                    }
                    if (g>h) {
                        g=h;
                    }
                    double x2=x0+g/h*(x1-x0), y2=y0+g/h*(y1-y0);
                    double d=Math.sqrt((P.getX()-x2)*(P.getX()-x2)+
                            (P.getY()-y2)*(P.getY()-y2));
                    if (d<dmin) {
                        dmin=d;
                        x=x2;
                        y=y2;
                    }
                }
                x0=x1;
                y0=y1;
            }
        }

        if (started) {
            P.setXY(x, y);
            P.Valid=true;
        } else {
            P.Valid=false;
        }


    }


    public double getSum() {
        double varmin, varmax, dvar;
        boolean reverse = false;
        boolean parametric = !getEX().equals(getVar());
        try {
            varmin = VarMin.getValue();
            varmax = VarMax.getValue();
            dvar = DVar.getValue();
            if (varmin > varmax) {
                double h = varmin;
                varmin = varmax;
                varmax = h;
                reverse = true;
            }
            if (dvar < 0) {
                dvar = -dvar;
            }
            if (dvar == 0) {
                dvar = (varmax - varmin) / 100;
            } else if (dvar < (varmax - varmin) / 1000) {
                dvar = (varmax - varmin) / 1000;
            }
        } catch (Exception e) {
            Valid = false;
            return 0;
        }
        X[0] = varmin;
        double x0 = 0, y0 = 0;
        boolean started = false;
        double sum = 0;
        while (true) {
            try {
                double x1 = EX.getValue();
                double y1 = EY.getValue();
                if (parametric) {
                    double x = 0, y = 0;
                    if (getCenter() != null) {
                        x = getCenter().getX();
                        y = getCenter().getY();
                    }
                    if (started) {
                        sum += ((x0 - x) * (y1 - y) - (y0 - y) * (x1 - x)) / 2;
                    }
                } else {
                    if (started) {
                        if (Special) {
                            if (reverse) {
                                sum += (x1 - x0) * y1;
                            } else {
                                sum += (x1 - x0) * y0;
                            }
                        } else {
                            sum += (x1 - x0) * (y0 + y1) / 2;
                        }
                    }
                }
                x0 = x1;
                y0 = y1;
                started = true;
            } catch (Exception e) {
            }
            if (X[0] >= varmax) {
                break;
            }
            X[0] = X[0] + dvar;
            if (X[0] > varmax) {
                X[0] = varmax;
            }
        }
        return sum;
    }

    public double getLength() {
        double varmin, varmax, dvar;
        boolean reverse = false;
        boolean parametric = !getEX().equals(getVar());
        try {
            varmin = VarMin.getValue();
            varmax = VarMax.getValue();
            dvar = DVar.getValue();
            if (varmin > varmax) {
                double h = varmin;
                varmin = varmax;
                varmax = h;
                reverse = true;
            }
            if (dvar < 0) {
                dvar = -dvar;
            }
            if (dvar == 0) {
                dvar = (varmax - varmin) / 100;
            } else if (dvar < (varmax - varmin) / 1000) {
                dvar = (varmax - varmin) / 1000;
            }
        } catch (Exception e) {
            Valid = false;
            return 0;
        }
        X[0] = varmin;
        double x0 = 0, y0 = 0;
        boolean started = false;
        double sum = 0;
        while (true) {
            try {
                double x1 = EX.getValue();
                double y1 = EY.getValue();
                if (started) {
                    sum += Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
                }
                started = true;
                x0 = x1;
                y0 = y1;
            } catch (Exception e) {
            }
            if (X[0] >= varmax) {
                break;
            }
            X[0] = X[0] + dvar;
            if (X[0] > varmax) {
                X[0] = varmax;
            }
        }
        return sum;
    }

    public boolean isSpecial() {
        return Special;
    }

    public void setSpecial(boolean f) {
        Special = f;
    }

    public void project(PointObject P, double alpha) {
        project(P);
    }

    public boolean maybeTransparent() {
        return true;
    }

    public boolean canDisplayName() {
        return false;
    }

    public void setCenter(String s) {
        if (Cn == null) {
            return;
        }
        Center = new Expression("@\"" + s + "\"", Cn, this);
    }

    public boolean isFilledForSelect() {
        return false;
    }

    public PointObject getCenter() {
        try {
            return (PointObject) Center.getObject();
        } catch (Exception e) {
            return null;
        }
    }

    public String getVar() {
        String vars = Var[0];
        for (int i = 1; i < Var.length; i++) {
            vars = vars + " " + Var[i];
        }
        return vars;
    }

    public boolean canInteresectWith(ConstructionObject o) {
        return true;
    }

    public boolean isDriverObject() {
        return true;
    }

    public boolean somethingChanged() {
        boolean b = !EX.toString().equals(LASTEX);
        b = b || !EY.toString().equals(LASTEY);

        return b;
    }

    public void clearChanges() {
        LASTEX = EX.toString();
        LASTEY = EY.toString();
    }
}
