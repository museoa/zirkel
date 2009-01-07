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
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;

import rene.dialogs.Warning;
import rene.gui.ButtonAction;
import rene.gui.CheckboxAction;
import rene.gui.Global;
import rene.gui.IconBar;
import rene.gui.MyLabel;
import rene.gui.MyPanel;
import rene.gui.MyTextField;
import rene.gui.TextFieldAction;
import rene.util.xml.XmlWriter;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.dialogs.EditConditionals;
import rene.zirkel.dialogs.ObjectEditDialog;
import rene.zirkel.expression.Expression;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.MyGraphics13;
import rene.zirkel.structures.MagnetObj;
import eric.JGlobals;
import eric.JPointName;

class PointEditDialog extends ObjectEditDialog {

    TextFieldAction X, Y;
    MyTextField Away, Bound, Increment;
    Checkbox Fixed, Close, Restricted, Alternate, Inside;
    IconBar TypeIB;
    ZirkelCanvas ZC;

    public PointEditDialog(Frame f, PointObject o) {
        super(f, Zirkel.name("edit.point.title"), o);
    }

    public PointEditDialog(ZirkelCanvas zc, PointObject o) {
        this(zc.getFrame(), o);
        ZC=zc;
    }

    public void addFirst(JPanel P) {
        PointObject p=(PointObject) O;

        X=new TextFieldAction(this, "X", ""+p.round(p.getX()), 30);
        P.add(new MyLabel(Zirkel.name("edit.point.x")));
        P.add(X);
        Y=new TextFieldAction(this, "Y", ""+p.round(p.getY()), 30);
        P.add(new MyLabel(Zirkel.name("edit.point.y")));
        P.add(Y);

        if (p.moveablePoint()) {
            Fixed=new Checkbox("");
            Fixed.setState(p.fixed());
            P.add(new MyLabel(Zirkel.name("edit.fixed")));
            P.add(Fixed);
            if (p.fixed()) {
                X.setText(p.getEX());
                Y.setText(p.getEY());
            }
            P.add(new MyLabel(Zirkel.name("edit.point.increment")));
            P.add(Increment=new MyTextField(""+p.round(p.getIncrement())));
        } else {
            X.setEditable(false);
            Y.setEditable(false);
        }

        if ((p instanceof IntersectionObject&&
                ((IntersectionObject) p).isSwitchable())) {
            Away=new MyTextField("", 5);
            Away.setText(((IntersectionObject) p).away());
            Close=new CheckboxAction(this, Zirkel.name("edit.point.close"));
            Close.setState(!((IntersectionObject) p).stayAway());
            P.add(new MyLabel(Zirkel.name("edit.point.intersection")));
            JPanel ap=new MyPanel();
            ap.setLayout(new GridLayout(1, 2));
            ap.add(Away);
            ap.add(Close);
            P.add(ap);
        }

        if (p instanceof IntersectionObject) {
            P.add(new MyLabel(Zirkel.name("edit.plumb.restricted")));
            Restricted=new CheckboxAction(this, "", "Restricted");
            Restricted.setState(((IntersectionObject) p).isRestricted());
            P.add(Restricted);
            if (((IntersectionObject) p).canAlternate()) {
                P.add(new MyLabel(Zirkel.name("edit.intersection.alternate")));
                Alternate=new CheckboxAction(this, "", "Alternate");
                Alternate.setState(((IntersectionObject) p).isAlternate());
                P.add(Alternate);
            }
        }

        if (p.isPointOn()) {
            P.add(new MyLabel(Zirkel.name("edit.point.bound")));
            P.add(Bound=new MyTextField(p.getBound().getName()));
            Bound.setEditable(false);
            if (p.getBound() instanceof InsideObject) {
                P.add(new MyLabel(Zirkel.name("edit.point.inside")));
                P.add(Inside=new CheckboxAction(this, "", "Inside"));
                Inside.setState(p.isInside());
            }
            if (Fixed!=null) {
                Fixed.setState(p.useAlpha());
            }
        }

    }
    Button BoundButton;

    public void addButton(JPanel P) {
        PointObject p=(PointObject) O;
        if (p.moveablePoint()) {
            if (p.isPointOn()) {
                BoundButton=new ButtonAction(this,
                        Zirkel.name("bound.release"), "Release");
            } else {
                BoundButton=new ButtonAction(this,
                        Zirkel.name("bound.bind"), "Bind");
            }
            P.add(BoundButton);
            P.add(new MyLabel(" "));
        } else if (p instanceof IntersectionObject&&
                ((IntersectionObject) p).isSwitchable()) {
            P.add(new ButtonAction(this, Zirkel.name("edit.point.away"),
                    "SetAway"));
            P.add(new ButtonAction(this, Zirkel.name("edit.point.close"),
                    "SetClose"));
            if (!((IntersectionObject) p).away().equals("")) {
                P.add(new ButtonAction(this, Zirkel.name("edit.point.free"),
                        "SetFree"));
            }
            P.add(new MyLabel(" "));
        }
    }

    public void addSecond(JPanel P) {
        PointObject p=(PointObject) O;

        TypeIB=new IconBar(F);
        TypeIB.addToggleGroupLeft("type", 6);
        TypeIB.toggle("type", p.getType());
        P.add(new MyLabel(""));
        P.add(TypeIB);
    }

    public void doAction(String o) {
        if ((o.equals("Y")||o.equals("X"))&&Fixed!=null) {
            Fixed.setState(true);
            super.doAction("OK");
        } else if (o.equals("Release")) {
            ((PointObject) O).setBound("");
            O.getConstruction().updateCircleDep();
            if (Fixed!=null) {
                Fixed.setState(false);
            }
            super.doAction("OK");
        } else if (o.equals("Bind")) {
            ZC.bind((PointObject) O);
            super.doAction("OK");
        } else if (o.equals("SetAway")) {
            ZC.setAway((IntersectionObject) O, true);
            super.doAction("OK");
        } else if (o.equals("SetClose")) {
            ZC.setAway((IntersectionObject) O, false);
            super.doAction("OK");
        } else if (o.equals("SetFree")) {
            ((IntersectionObject) O).setAway("");
            Away=null;
            super.doAction("OK");
        } else if (o.equals("OK")) {
            if (Fixed!=null&&X.isChanged()||Y.isChanged()) {
                Fixed.setState(true);
            }
            super.doAction("OK");
        } else {
            super.doAction(o);
        }
    }

    public void setAction() {
        PointObject p=(PointObject) O;

        if ((X.isChanged()||Y.isChanged())&&p.isPointOn()) {
            try {
                double x=new Expression(X.getText(),
                        p.getConstruction(), p).getValue();
                double y=new Expression(Y.getText(),
                        p.getConstruction(), p).getValue();
                p.move(x, y);
                p.validate();
            } catch (Exception e) {
            }
        }

        if (Fixed!=null&&Fixed.getState()==true) {
            if (p.isPointOn()) {
                p.setUseAlpha(true);
            } else {
                p.setFixed(X.getText(), Y.getText());
            }
        } else {
            try {
                double x=new Expression(X.getText(),
                        p.getConstruction(), p).getValue();
                double y=new Expression(Y.getText(),
                        p.getConstruction(), p).getValue();
                if (p.moveable()) {
                    p.move(x, y);
                }
            } catch (Exception e) {
            }
        }
        if (Fixed!=null&&Fixed.getState()==false) {
            if (p.isPointOn()) {
                p.setUseAlpha(false);
            } else {
                p.setFixed(false);
            }
        }

        if (Away!=null) {
            if (!((IntersectionObject) p).setAway(Away.getText(), !Close.getState())) {
                Warning w=new Warning(F, Zirkel.name("bound.error"),
                        Zirkel.name("warning"));
                w.center(F);
                w.setVisible(true);
            }
        }
        if (Restricted!=null) {
            ((IntersectionObject) p).setRestricted(Restricted.getState());
        }
        if (Alternate!=null) {
            ((IntersectionObject) p).setAlternate(Alternate.getState());
        }
        if (Increment!=null) {
            try {
                p.setIncrement(new Expression(Increment.getText(),
                        p.getConstruction(), p).getValue());
            } catch (Exception e) {
            }
        }
        p.setType(TypeIB.getToggleState("type"));
        if (Inside!=null) {
            p.setInside(Inside.getState());
        }
    }

    public void focusGained(FocusEvent e) {
        if (Fixed!=null&&Fixed.getState()) {
            X.requestFocus();
        } else {
            super.focusGained(e);
        }
    }
}

public class PointObject extends ConstructionObject
        implements MoveableObject, DriverObject {

    protected double X,  Y;
    protected boolean BarycentricCoordsInitialzed=false;
    protected double Gx=0,  Gy=0; // Barycentric coords, if it's inside a polygon.
    protected double Alpha; // parameter relative zu object
    protected boolean AlphaValid=false; // Alpha is valid
    protected boolean UseAlpha=false; // Use Alpha at all
    protected boolean Moveable,  Fixed;
//    private static Count N=new Count();
    private static JPointName PointLabel=new JPointName();
    protected int Type=0;
    public final static int SQUARE=0,  DIAMOND=1,  CIRCLE=2,  DOT=3,  CROSS=4,  DCROSS=5;
    public static int MaxType=3;
    protected Expression EX,  EY;
//    private ConstructionObject Bound=null; // point is on a line etc.
    private boolean Later; // bound is later in construction
    private String LaterBind="";
    private boolean KeepInside; // keep point inside bound
    private boolean DontUpdate=false;
    private double Increment=0; // increment to keep on grid
    private ConstructionObject Bound=null; // point is on a line etc.
    private double BoundOrder=Double.NaN; //Only for points on parametric curves made with "points only"
    protected ConstructionObject MovedBy;
    private double LASTX=Double.NaN,  LASTY=Double.NaN;
    private Vector magnetObjects=new Vector();
    Expression magnetRayExp=null;
    private ConstructionObject CurrentMagnetObject=null;
    public ConstructionObject VirtualBound=null;

    // The object that may have moved this point
    public PointObject(Construction c, double x, double y) {
        super(c);
        X=x;
        Y=y;
        Moveable=true;
        Fixed=false;
        setColor(ColorIndex, SpecialColor);
        setShowName(false);
        updateText();
        Type=0;
        setMagnetRayExp("20");
    }

    public PointObject(Construction c, double x, double y,
            ConstructionObject bound) {

        this(c, x, y);
        Bound=bound;

    }

    public PointObject(Construction c, String name) {
        super(c, name);
        X=0;
        Y=0;
        Moveable=true;
        Fixed=false;
        setColor(ColorIndex, SpecialColor);
        updateText();
        Type=0;
        setMagnetRayExp("20");
    }

    public static void setPointLabel(JPointName jpl) {
        PointLabel=jpl;
    }

    public void setName() {
        if ((!SuperHidden)&&(!Hidden)) {
            Name=PointLabel.getBetterName(Cn, false);
        } else {
            Name=JPointName.getGenericName(Cn);
        }
    }

    public void setNameWithNumber(String n) {
        Name="";
        if (Cn!=null) {
            ConstructionObject o=Cn.find(n);
            if (o!=null) {
                while (o!=null&&o!=this) {
                    Name=JPointName.getGenericName(Cn);
                    n=Name;
                    Name="";
                    o=Cn.find(n);
                }
                Name=n;
            } else {
                Name=n;
            }
        } else {
            Name=n;
        }
    }

    public String getTag() {
        if (Bound==null) {
            return "Point";
        } else {
            return "PointOn";
        }
    }

    public int getN() {
        return N.next();
    }

//	public void setDefaults ()
//	{	super.setDefaults();
//		Type=Cn.DefaultType;
//	}
    public void setDefaults() {
        setShowName(Global.getParameter("options.point.shownames", false));
        setShowValue(Global.getParameter("options.point.showvalues", false));
        setColor(Global.getParameter("options.point.color", 0), Global.getParameter("options.point.pcolor", (Color) null));
        setColorType(Global.getParameter("options.point.colortype", 0));
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
        setSolid(Cn.Solid);
        setLarge(Global.getParameter("options.point.large", false));
        setBold(Global.getParameter("options.point.bold", false));
        Type=Cn.DefaultType;

    }

    public void setTargetDefaults() {
        setShowName(Global.getParameter("options.point.shownames", false));
        setShowValue(Global.getParameter("options.point.showvalues", false));
        setColor(Global.getParameter("options.point.color", 0), Global.getParameter("options.point.pcolor", (Color) null));
        setColorType(Global.getParameter("options.point.colortype", 0));
        setLarge(Global.getParameter("options.point.large", false));
        setBold(Global.getParameter("options.point.bold", false));
        Type=Cn.DefaultType;
    }
    private double Delta;

    public double changedBy() {
        return Delta;
    }

    public void validate() {

        if (DontUpdate) {
            return;
        }

        updateMagnetObjects();
        followMagnetObject();
//        magnet();
//        System.out.println(getName()+" : validate !");

        MovedBy=null;
        Delta=0.0;
        Valid=true;
        if (Bound!=null&&!Bound.isInConstruction()) {
            Bound=null;
        }
        if (Bound!=null&&!Bound.valid()) {
            Valid=false;
            return;
        }
        if (Increment>1e-4) {
            X=Math.floor(X/Increment+0.5)*Increment;
            Y=Math.floor(Y/Increment+0.5)*Increment;
        }
        if (Bound!=null) {
            double x=X, y=Y;
            if (KeepInside&&Bound instanceof InsideObject) {
                ((InsideObject) Bound).keepInside(this);
            } else if (!KeepInside&&Bound instanceof PointonObject) {
                if (!AlphaValid||!UseAlpha) {
                    project(Bound);
                } else {
                    project(Bound, Alpha);
                }
            }
            if (Later) {
                Delta=Math.sqrt((x-X)*(x-X)+(y-Y)*(y-Y));
            }
        }

        if (Fixed&&EX!=null&&EX.isValid()) {
            try {
                X=EX.getValue();
            } catch (Exception e) {
                Valid=false;
                return;
            }
        }
        if (Fixed&&EY!=null&&EY.isValid()) {
            try {
                Y=EY.getValue();
            } catch (Exception e) {
                Valid=false;
                return;
            }
        }
    }

    public void updateText() {
        if (Bound!=null) {
            setText(text1(Zirkel.name("text.boundedpoint"), Bound.getName()));
        } else if (EX!=null&&EY!=null) {
            setText(text2(Zirkel.name("text.point"), "\""+EX+"\"", "\""+EY+"\""));
        } else {
            setText(text2(Zirkel.name("text.point"), ""+round(X), ""+round(Y)));
        }
    }
    static double x[]=new double[4],  y[]=new double[4];

    public void paint(MyGraphics g, ZirkelCanvas zc) {
        DisplaysText=false;
        if (!Valid||mustHide(zc)) {
            return;
        }
        double size=drawPoint(g, zc, this, X, Y, Type);
        if (tracked()) {
            zc.UniversalTrack.drawTrackPoint(this, X, Y, Type);
        }
        String s=AngleObject.translateToUnicode(getDisplayText());

        if (!s.equals("")) {
            g.setLabelColor(this);
            DisplaysText=true;
            setFont(g);
            double d=Math.sqrt(XcOffset*XcOffset+YcOffset*YcOffset);
            if (!KeepClose||d<1e-10) {
                TX1=zc.col(X+XcOffset)+2*size;
                TY1=zc.row(Y+YcOffset)+2*size;
                drawLabel(g, s);
            } else {
                drawPointLabel(g, s, zc, X, Y, YcOffset/d, -XcOffset/d, 0, 0);
            }
        }
    }

    static public double drawPoint(MyGraphics g, ZirkelCanvas zc,
            ConstructionObject o, double X, double Y, int type) {
        double size=zc.pointSize();

        double r=zc.col(X), c=zc.row(Y);
        if (size<1) {
            size=1;
        }
//        System.out.println(size);
        if (o.visible(zc)) {
            if (o.isStrongSelected()&&g instanceof MyGraphics13) {
                ((MyGraphics13) g).drawMarkerLine(r, c, r, c);
            }
            g.setColor(o);
            switch (type) {
                case SQUARE:
                    double sx=r-size-1,
                     sy=c-size-1,
                     sw=2*size+2;
                    if (o.getColorType()==THICK) {
                        g.fillRect(sx, sy, sw, sw, true, false, o);
                    } else {
                        g.fillRect(sx, sy, sw, sw, new Color(255, 255, 255));
                    }
                    g.setColor(o);
                    g.drawRect(sx, sy, sw, sw);
                    break;
                case DIAMOND:
                    double dx=r-size-2,
                     dy=c-size-2,
                     dw=2*size+4;
                    g.drawDiamond(dx, dy, dw, (o.getColorType()==THICK), o);
                    break;
                case CIRCLE:
                    double cx=r-size-1,
                     cy=c-size-1,
                     cw=2*size+2;
                    if (o.getColorType()==THICK) {
                        g.fillOval(cx, cy, cw, cw, true, false, o);
                    } else {
                        g.fillOval(cx, cy, cw, cw, new Color(255, 255, 255));
                        g.setColor(o);
                        g.drawOval(cx, cy, cw, cw);
                    }
                    break;
                case DOT:
                    if (o.getColorType()==THICK) {
                        g.fillRect(r, c, 1, 1, true, false, o);
                    } else {
                        g.drawLine(r, c, r, c);
                    }
                    break;
                case CROSS:
                    if (o.getColorType()==THICK) {
                        g.drawThickLine(r-size, c, r+size, c);
                        g.drawThickLine(r, c-size, r, c+size);
                    } else {
                        g.drawLine(r-size, c, r+size, c);
                        g.drawLine(r, c-size, r, c+size);
                    }
                    break;
                case DCROSS:
                    double dcx=r-size-1,
                     dcy=c-size-1,
                     dcw=2*size+1;
                    g.drawDcross(dcx, dcy, dcw, (o.getColorType()==THICK), o);
                    break;
            }
        }
        return size;
    }

    public String getDisplayValue() {
//        return "("+roundDisplay(X)+
//                (Global.getParameter("options.germanpoints", false)?"|":",")+roundDisplay(Y)+")";
        return "("+JGlobals.getLocaleNumber(X, "lengths")+
                (Global.getParameter("options.germanpoints", false)?"|":";")+JGlobals.getLocaleNumber(Y, "lengths")+")";
    }

    public boolean nearto(int x, int y, ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        double c=zc.col(X), r=zc.row(Y);
        int size=(int) zc.selectionSize();
        Value=Math.abs(x-c)+Math.abs(y-r);
        return Value<=size*3/2;
    }

    public boolean nearto(PointObject p) {
        if (!Valid) {
            return false;
        }
        double dx=p.X-X, dy=p.Y-Y;
        return Math.sqrt(dx*dx+dy*dy)<1e-9;
    }

    public double distanceTo(int x, int y, ZirkelCanvas zc) {
        double dx=x-zc.col(X), dy=y-zc.row(Y);
        return Math.sqrt(dx*dx+dy*dy);
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public boolean moveable() {
        boolean fixed=Fixed;
        if (dependsOnItselfOnly()) {
            fixed=false;
        }
        return Moveable&&!fixed&&!Keep;
    }

    /**
     * Check if the point depends on itself and no other object.
     * Such points are moveable, even if they are fixed.
     * @return
     */
    public boolean dependsOnItselfOnly() {
        boolean res=false;
        Enumeration e=depending();
        while (e.hasMoreElements()) {
            if ((ConstructionObject) e.nextElement()==this) {
                res=true;
                break;
            }
        }
        e=depending();
        while (e.hasMoreElements()) {
            if ((ConstructionObject) e.nextElement()!=this) {
                res=false;
                break;
            }
        }
        return res;
    }

    public boolean dependsOnParametersOnly() {
        Enumeration e=depending();
        while (e.hasMoreElements()) {
            if (!((ConstructionObject) e.nextElement()).isParameter()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return o can move this point (moveable and not moved by something else)
     */
    public boolean moveableBy(ConstructionObject o) {
        if (Bound!=null) {
            return false;
        }
        return moveable()&&(MovedBy==null||MovedBy==o);
    }

    public boolean moveablePoint() {
        if (Bound!=null) {
            return true;
        }
        return Moveable&&!Keep;
    }

    public boolean setBound(String name) {
        if (name.equals("")) {
            Bound=null;
            setFixed(false);
            Later=false;
            return true;
        }
        try {
            Bound=null;
            ConstructionObject o=Cn.find(name);
            if (o instanceof PointonObject) {
                Bound=o;
                Moveable=true;
                Fixed=false;
                KeepInside=false;
            } else if (o instanceof InsideObject) {
                Bound=o;
                Moveable=true;
                Fixed=false;
                KeepInside=true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        if (Cn.before(this, Bound)) {
            Cn.needsOrdering();
            Cn.dovalidate();
        }
        updateText();
        clearMagnetObjects();
        return true;
    }

    public void setBound(ConstructionObject bound) {
        Bound=bound;
    }

    public boolean haveBoundOrder() {
        return (!Double.isNaN(BoundOrder));
    }

    public void setBoundOrder(double boundorder) {
        BoundOrder=boundorder;
    }

    public void clearBoundOrder() {
        BoundOrder=Double.NaN;
    }

    public ConstructionObject getBound() {
        return Bound;
    }

    public double getBoundOrder() {
        return BoundOrder;
    }

    public void setMoveable(boolean flag) {
        Moveable=flag;
    }

    public boolean fixed() {
        return Fixed;
    }

    public void setFixed(boolean flag) {
        Fixed=flag;
        if (!Fixed) {
            EX=EY=null;
        }
        updateText();
    }

    public void setFixed(String x, String y) {
        Fixed=true;
        EX=new Expression(x, getConstruction(), this);
        EY=new Expression(y, getConstruction(), this);
        updateText();
    }

    public void move(double x, double y) {
        if ((X==x)&&(Y==y)) {
            return;
        }
//        System.out.println(getName()+" : move !");
        X=x;
        Y=y;
        AlphaValid=false;
        computeBarycentricCoords();

    }

    public void setXY(double x, double y) {
        if ((X==x)&&(Y==y)) {
            return;
        }
//        System.out.println(getName()+" : setXY !");
        X=x;
        Y=y;
    }

    public void setA(double alpha) {
        Alpha=alpha;
    }



    public void setMagnetRayExp(String s){
        magnetRayExp=new Expression(s, Cn, this);
    }
    public String getMagnetRayExp(){
        return magnetRayExp.toString();
    }

    public int getMagnetRay(){
        int i=20;
        try {
            i=(int) Math.round(magnetRayExp.getValue());
        } catch (Exception ex) {
        }
        return i;
    }

//        public String getMagnetRayExp(){
//return magnetRayExp.toString();
//    }



    private boolean isMagnetObject(String name) {
        Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            MagnetObj mo=(MagnetObj) e.nextElement();
            if (mo.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeMagnetObject(String name) {
        Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            MagnetObj mo=(MagnetObj) e.nextElement();
            if (mo.name().equals(name)) {
                magnetObjects.remove(mo);
            }
        }
    }

    /**
     * s represents the name of an object, or the string "name:ray"
     * where ray is the specific magnetic attraction
     * @param s
     */
    public void addMagnetObject(String s) {
        String[] mo=s.split(":");
        String name=mo[0];
//        int ray=-1;
        String ray="-1";
        if (mo.length>1) {
            ray=mo[1];
        }

        if (name.equals(getName())) {
            return;
        }
        ConstructionObject o=Cn.find(name);
        if ((o!=null)&&(!isMagnetObject(name))) {
            magnetObjects.add(new MagnetObj(o, ray));
        }
    }

    public void setMagnetObjects(String namelist) {
        String st="";
        char t;   
        int p=0;
        CharSequence cs=namelist;
        for (int i=0;i<namelist.length();i++){
            t=namelist.charAt(i);
            if (t=='(') p++;
            else if (t==')') p--;
            if ((p>0)&&(t==',')) st+="@@";
            else st+=t;
        }

        String[] s=st.split(",");
        magnetObjects.removeAllElements();
        for (int i=0; i<s.length; i++) {
            st=s[i].replaceAll("@@", ",");
            addMagnetObject(st);
        }
    }

    public void selectMagnetObjects(boolean b) {
        Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            MagnetObj mo=(MagnetObj) e.nextElement();
            if (mo.isInConstruction()) {
                mo.setSelected(b);
            }
        }
    }

    public void updateMagnetObjects() {
        Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            MagnetObj mo=(MagnetObj) e.nextElement();
            if (!mo.isInConstruction()) {
                magnetObjects.remove(mo);
            }
        }
    }

    public void clearMagnetObjects(){
        magnetObjects.removeAllElements();
        setMagnetRayExp("20");

    }

    public void translateMagnetObjects(){
        Enumeration e=magnetObjects.elements();
        while (e.hasMoreElements()) {
            MagnetObj mo=(MagnetObj) e.nextElement();
            mo.translate();
        }
    }

    public Vector getMagnetObjects() {
        return magnetObjects;
    }

    public String getMagnetObjectsString() {
        updateMagnetObjects();
        String s="";
        Enumeration e=magnetObjects.elements();
        if (e.hasMoreElements()) {
            MagnetObj mo=(MagnetObj) e.nextElement();
            s=mo.name();
            if (mo.ray()>-1) {
                s+=":"+mo.rayexp();
            }
        }
        while (e.hasMoreElements()) {
            MagnetObj mo=(MagnetObj) e.nextElement();
            s+=","+mo.name();
            if (mo.ray()>-1) {
                s+=":"+mo.rayexp();
            }
        }
        return s;
    }

    public void magnet() {
        ConstructionObject PtOnObject=null;
        ConstructionObject PtObject=null;
        Enumeration e=magnetObjects.elements();
        int dp=1000, dm=1000;
        while (e.hasMoreElements()) {
            MagnetObj mo=(MagnetObj) e.nextElement();
            ConstructionObject o=mo.obj();
//            int mRay=(mo.ray()>-1)?mo.ray():magnetRay;
            int mRay=(mo.ray()>-1)?mo.ray():getMagnetRay();
            if (mRay==0) continue;
            if (!o.valid()) continue;
            if (o instanceof PointObject) {
                PointObject pt=(PointObject) o;
                int i=o.getDistance(this);

                if ((i<=mRay)&&(i<dp)) {
                    PtObject=o;
                    dp=i;
                }
            } else if (o instanceof PointonObject) {
                PointonObject pt=(PointonObject) o;
                int i=o.getDistance(this);
                if ((i<=mRay)&&(i<dm)) {
                    PtOnObject=o;
                    dm=i;
                }
            }
        }
        if (PtObject!=null) {
            PointObject pt=(PointObject) PtObject;
            setXY(pt.getX(), pt.getY());
            if (PtObject!=CurrentMagnetObject) {
                CurrentMagnetObject=PtObject;
                Cn.reorderConstruction();
            }
        } else if (PtOnObject!=null) {
            PointonObject pt=(PointonObject) PtOnObject;
            if (PtOnObject!=CurrentMagnetObject) {
                CurrentMagnetObject=PtOnObject;
                Cn.reorderConstruction();
            }
            pt.project(this);
        } else {
            CurrentMagnetObject=null;
        }
    }


    public void setCurrentMagnetObject() {
        ConstructionObject PtOnObject=null;
        ConstructionObject PtObject=null;
        Enumeration e=magnetObjects.elements();
        int dp=1000, dm=1000;
        while (e.hasMoreElements()) {
            MagnetObj mo=(MagnetObj) e.nextElement();
            ConstructionObject o=mo.obj();
            int mRay=(mo.ray()>-1)?mo.ray():getMagnetRay();
            if (o instanceof PointObject) {
                PointObject pt=(PointObject) o;
                int i=o.getDistance(this);

                if ((i<=mRay)&&(i<dp)) {
                    PtObject=o;
                    dp=i;
                }
            } else if (o instanceof PointonObject) {
                PointonObject pt=(PointonObject) o;
                int i=o.getDistance(this);
                if ((i<=mRay)&&(i<dm)) {
                    PtOnObject=o;
                    dm=i;
                }
            }
        }
        if (PtObject!=null) {
            CurrentMagnetObject=PtObject;
        } else if (PtOnObject!=null) {
            CurrentMagnetObject=PtOnObject;
        } else {
            CurrentMagnetObject=null;
        }
    }

    public ConstructionObject getCurrentMagnetObject() {
        return CurrentMagnetObject;
    }

    public void followMagnetObject() {
        if (CurrentMagnetObject!=null) {
            if (CurrentMagnetObject instanceof PointObject) {
                PointObject pt=(PointObject) CurrentMagnetObject;
                setXY(pt.getX(), pt.getY());
            } else if (CurrentMagnetObject instanceof PointonObject) {
                PointonObject pt=(PointonObject) CurrentMagnetObject;
                pt.project(this);
            }
        };
        magnet();
    }

    public int getDistance(PointObject P) {
        double d=Math.sqrt(((getX()-P.getX())*(getX()-P.getX())+(getY()-P.getY())*(getY()-P.getY())));
        return (int) Math.round(d*Cn.getPixel());
    }

    public void project(ConstructionObject o) {
        if (!(o instanceof PointonObject)) {
            return;
        }
        ((PointonObject) o).project(this);
        if (UseAlpha) {
            AlphaValid=true;
        }
    }

    public void project(ConstructionObject o, double alpha) {
        ((PointonObject) o).project(this, alpha);
    }

    public void edit(ZirkelCanvas zc) {
        if (!rene.zirkel.Zirkel.IsApplet) {
            eric.JGlobals.EditObject(this);
            return;
        }
        ObjectEditDialog d=new PointEditDialog(zc, this);
        d.setVisible(true);
        zc.repaint();
        if ((EX!=null&&!EX.isValid())) {
            Frame F=zc.getFrame();
            Warning w=new Warning(F, EX.getErrorText(),
                    Zirkel.name("warning"), true);
            w.center(F);
            w.setVisible(true);
        } else if ((EY!=null&&!EY.isValid())) {
            Frame F=zc.getFrame();
            Warning w=new Warning(F, EY.getErrorText(),
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
        updateText();
        updateMagnetObjects();
        if (Bound!=null) {
            xml.printArg("on", Bound.getName());
            if (KeepInside) {
                xml.printArg("inside", "true");
            }
        }
        if (magnetObjects.size()>0) {
            xml.printArg("magnetobjs", getMagnetObjectsString());
            xml.printArg("magnetd", ""+magnetRayExp.toString());
        }
        if (haveBoundOrder()) {
            xml.printArg("boundorder", String.valueOf(BoundOrder));
        }
        if (Fixed&&EX!=null) {
            xml.printArg("x", EX.toString());
            xml.printArg("actx", ""+X);
        } else {
            if (Bound!=null&&AlphaValid&&UseAlpha) {
                xml.printArg("alpha", ""+Alpha);
            }
            xml.printArg("x", ""+X);
        }
        if (Fixed&&EY!=null) {
            xml.printArg("y", EY.toString());
            xml.printArg("acty", ""+Y);
        } else {
            xml.printArg("y", ""+Y);
        }
        printType(xml);
        if (Fixed) {
            xml.printArg("fixed", "true");
        }
        if (Increment>1e-4) {
            xml.printArg("increment", ""+getIncrement());
        }
    }

    public void printType(XmlWriter xml) {
        if (Type!=0) {
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

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type=type;
    }

    public void movedBy(ConstructionObject o) {
        MovedBy=o;
    }

    public boolean equals(ConstructionObject o) {
        if (!(o instanceof PointObject)||!o.valid()) {
            return false;
        }
        PointObject p=(PointObject) o;
        return equals(X, p.X)&&equals(Y, p.Y);
    }

    public String getEX() {
        if (EX!=null) {
            return EX.toString();
        } else {
            return ""+round(X);
        }
    }

    public String getEY() {
        if (EY!=null) {
            return EY.toString();
        } else {
            return ""+round(Y);
        }
    }

    public boolean isOn(ConstructionObject o) {
        if (Bound!=null) {
            return o==Bound;
        }
        return o.contains(this);
    }

    public void translate() {
        if (Bound!=null) {
            Bound=Bound.getTranslation();
        } else if (Fixed) {
            try {
                setFixed(EX.toString(), EY.toString());
                EX.translate();
                EY.translate();
            } catch (Exception e) {
            }
        }
        magnetRayExp.translate();
        translateMagnetObjects();
    }

    public Enumeration depending() {
        super.depending();
        if (Bound!=null) {
            DL.add(Bound);
        }
        if (Fixed) {
            if (EX!=null) {
                EX.addDep(this);
            }
            if (EY!=null) {
                EY.addDep(this);
            }
        }
        return DL.elements();

    }

    public void snap(ZirkelCanvas zc) {
        double d=zc.getGridSize()/2;
        X=Math.round(X/d)*d;
        Y=Math.round(Y/d)*d;
        updateText();
    }

    public void updateCircleDep() {
        if (Bound!=null&&Bound instanceof PrimitiveCircleObject) {
            ((PrimitiveCircleObject) Bound).addDep(this);
        }
        if (Bound!=null&&Bound instanceof PrimitiveLineObject) {
            ((PrimitiveLineObject) Bound).addDep(this);
        }
    }

    public boolean isPointOn() {
        return Bound!=null;
    }

    public boolean isPointOnOrMagnet() {
        return (Bound!=null)||(CurrentMagnetObject!=null);
    }

    public void setLaterBind(String s) {
        LaterBind=s;
    }

    // for macro constructions :
    public ConstructionObject copy(double x, double y) {
        ConstructionObject o=null;
        try {
            o=(ConstructionObject) clone();
            setTranslation(o);
            o.translateConditionals();
            o.translate();
            o.setName();
            o.updateText();
            o.setBreak(false);
            // o.setTarget(false);
            // if the target is a Point inside polygon, then try
            // to follow the mouse ! :
            if (KeepInside&&Bound!=null&&Bound instanceof AreaObject) {
                o.move(x, y);
            }
        } catch (Exception e) {
        }
        return o;
    }

    public void computeBarycentricCoords() {
        if (Bound!=null&&Bound instanceof QuadricObject) {
            QuadricObject quad=((QuadricObject) Bound);
            PointObject A=quad.P[0];
            PointObject B=quad.P[1];
            PointObject C=quad.P[2];
            double a=B.getX()-A.getX();
            double b=C.getX()-A.getX();
            double c=this.getX()-A.getX();
            double d=B.getY()-A.getY();
            double e=C.getY()-A.getY();
            double f=this.getY()-A.getY();
            double det=a*e-d*b;
            if (det!=0) {
                Gx=(c*e-b*f)/det;
                Gy=(a*f-c*d)/det;
                BarycentricCoordsInitialzed=true;
            }
        } else if (KeepInside&&Bound!=null&&Bound instanceof AreaObject) {
            AreaObject area=((AreaObject) Bound);
            if (area.V.size()>2) {
                PointObject A=(PointObject) area.V.get(0);
                PointObject B=(PointObject) area.V.get(1);
                PointObject C=(PointObject) area.V.get(2);
                double a=B.getX()-A.getX();
                double b=C.getX()-A.getX();
                double c=this.getX()-A.getX();
                double d=B.getY()-A.getY();
                double e=C.getY()-A.getY();
                double f=this.getY()-A.getY();
                double det=a*e-d*b;
                if (det!=0) {
                    Gx=(c*e-b*f)/det;
                    Gy=(a*f-c*d)/det;
                    BarycentricCoordsInitialzed=true;
                }
            }
        }
    }

    public void setInside(boolean flag) {
        KeepInside=flag;
        computeBarycentricCoords();
    }

    public boolean isInside() {
        return KeepInside;
    }

    public void laterBind(Construction c) {
        if (LaterBind.equals("")) {
            return;
        }
        ConstructionObject o=c.find(LaterBind);
        if (o!=null&&((o instanceof PointonObject)||(o instanceof InsideObject))) {
            Bound=o;
            updateText();
            validate();
        }
        LaterBind="";
    }

    public void setAlpha(double alpha) {
        Alpha=alpha;
        AlphaValid=true;
    }

    public void setUseAlpha(boolean flag) {
        UseAlpha=flag;
    }

    public boolean useAlpha() {
        return UseAlpha;
    }

    public double getAlpha() {
        return Alpha;
    }

    public void round() {
        move(round(X, ZirkelCanvas.LengthsFactor),
                round(Y, ZirkelCanvas.LengthsFactor));
    }

    /**
     * For bounded points.
     */
    public void setKeepClose(double x, double y) {
        KeepClose=true;
        XcOffset=x-X;
        YcOffset=y-Y;
    }

    public boolean canKeepClose() {
        return true;
    }

    public boolean dontUpdate() {
        return DontUpdate;
    }

    public void dontUpdate(boolean flag) {
        DontUpdate=flag;
    }

    /**
     * Overwrite setting of default things for macro targets.
     */
//	public void setTargetDefaults ()
//	{	super.setTargetDefaults();
//		Type=Cn.DefaultType;
//	}
    public void startDrag(double x, double y) {
    }

    public void dragTo(double x, double y) {
        clearBoundOrder();
        if ((X==x)&&(Y==y)) {
            return;
        }
//        System.out.println(getName()+" : dragTo !");
        move(x, y);
    }

    public double getOldX() {
        return 0;
    }

    public double getOldY() {
        return 0;
    }

    public void setIncrement(double inc) {
        Increment=inc;
    }

    public double getIncrement() {
        return Increment;
    }

    public boolean isDriverObject() {
        if (Bound!=null) {
            return true;
        }
//        if (Fixed) return false;
        return Moveable&&!Keep;
    }

    public boolean somethingChanged() {
        return ((getX()!=LASTX)||(getY()!=LASTY));
    }

    public void clearChanges() {
        LASTX=getX();
        LASTY=getY();
    }
}
