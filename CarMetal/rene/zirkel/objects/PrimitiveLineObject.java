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

// file: PrimitiveLineObject.java
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Frame;
import javax.swing.JPanel;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.util.Enumeration;

import rene.gui.Global;
import rene.gui.IconBar;
import rene.gui.MyLabel;
import rene.gui.TextFieldAction;
import rene.util.xml.XmlWriter;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.dialogs.EditConditionals;
import rene.zirkel.dialogs.ObjectEditDialog;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.MyGraphics13;
import rene.zirkel.structures.Coordinates;

class LineEditDialog extends ObjectEditDialog {

    TextField Length;
    Checkbox Fixed, Restricted;
    IconBar IC;

    public LineEditDialog(Frame f, PrimitiveLineObject o) {
        super(f, Zirkel.name("edit.line.title"), o);
    }

    public void addFirst(JPanel P) {
        PrimitiveLineObject l=(PrimitiveLineObject) O;

        if (l instanceof SegmentObject) {
            Length=new TextFieldAction(this, "Length", ((SegmentObject) l).getStringLength(), 30);
            if (((SegmentObject) l).canFix()) {
                Fixed=new Checkbox("");
                Fixed.setState(((SegmentObject) l).fixed());
            } else {
                Length.setEditable(false);
            }
        }

        if ((l instanceof PlumbObject)&&
                ((PlumbObject) l).canRestrict()) {
            Restricted=new Checkbox("");
            Restricted.setState(((PlumbObject) l).isRestricted());
        }

        if (Length!=null) {
            P.add(new MyLabel(Zirkel.name("edit.segment.length")));
            P.add(Length);
        }
        if (Fixed!=null) {
            P.add(new MyLabel(Zirkel.name("edit.fixed")));
            P.add(Fixed);
        }

        if (Restricted!=null) {
            P.add(new MyLabel(Zirkel.name("edit.plumb.restricted")));
            P.add(Restricted);
        }

    }

    public void addSecond(JPanel P) {
        PrimitiveLineObject l=(PrimitiveLineObject) O;
        IC=new IconBar(F);
        IC.setIconBarListener(this);
        if (!(l instanceof SegmentObject)) {
            IC.addToggleLeft("plines");
            IC.setState("plines", l.isPartial());
        }
        if (l instanceof SegmentObject) {
            IC.addToggleLeft("arrow");
            IC.setState("arrow", ((SegmentObject) l).isArrow());
        }
        P.add(new MyLabel(""));
        P.add(IC);
    }

    public boolean showsValue() {
        return Length!=null;
    }

    public void doAction(String o) {
        if (o.equals("Length")&&Fixed!=null) {
            Fixed.setState(true);
            super.doAction("OK");
        } else {
            super.doAction(o);
        }
    }

    public void setAction() {
        if (Fixed!=null) {
            try {
                ((SegmentObject) O).setFixed(Fixed.getState(), Length.getText());
            } catch (Exception e) {
            }
        }
        if (Restricted!=null) {
            ((PlumbObject) O).setRestricted(Restricted.getState());
        }
        if (!(O instanceof SegmentObject)) {
            ((PrimitiveLineObject) O).setPartial(IC.getState("plines"));
        } else if (O instanceof SegmentObject) {
            ((SegmentObject) O).setArrow(IC.getState("arrow"));
            Global.setParameter("unit.length", Unit.getText());
        }
    }

    public void focusGained(FocusEvent e) {
        if (Fixed!=null&&Fixed.getState()) {
            Length.requestFocus();
        } else {
            super.focusGained(e);
        }
    }
}

public class PrimitiveLineObject extends ConstructionObject
        implements PointonObject {

    protected double X1,  Y1,  DX,  DY;
    protected PointObject P1;
    PointObject Dep[];
    int NDep;
    boolean Partial=false;

    public PrimitiveLineObject(Construction c) {
        super(c);
        setColor(ColorIndex,SpecialColor);
    }
    
    public PrimitiveLineObject(Construction c,String Nme) {
        super(c,Nme);
        setColor(ColorIndex,SpecialColor);
    }
    
    public void setP1DXDY(PointObject p,double dx,double dy){
        P1=p;
        DX=dx;
        DY=dy;
        X1=P1.getX(); Y1=P1.getY();
    }

    public void setDefaults() {
        setShowName(Global.getParameter("options.line.shownames", false));
        setShowValue(Global.getParameter("options.line.showvalues", false));
        setColor(Global.getParameter("options.line.color", 0), Global.getParameter("options.line.pcolor", (Color)null));
        setColorType(Global.getParameter("options.line.colortype", 0));
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
        setSolid(Cn.Solid);
        setLarge(Global.getParameter("options.line.large", false));
        setBold(Global.getParameter("options.line.bold", false));
    }

    public void setTargetDefaults() {
        setShowName(Global.getParameter("options.line.shownames", false));
        setShowValue(Global.getParameter("options.line.showvalues", false));
        setColor(Global.getParameter("options.line.color", 0), Global.getParameter("options.line.pcolor", (Color)null));
        setColorType(Global.getParameter("options.line.colortype", 0));
        setLarge(Global.getParameter("options.line.large", false));
        setBold(Global.getParameter("options.line.bold", false));
    }

    public String getTag() {
        return "Line";
    }
    double k1, k2;
    boolean k12valid=false;

    public void paint(MyGraphics g, ZirkelCanvas zc) {
        if (!Valid||mustHide(zc)) {
            return;
        }
        
        //compute middle of the screen:
        double xm=(zc.minX()+zc.maxX())/2, ym=(zc.minY()+zc.maxY())/2;
        // compute distance from middle to line:
        double d=(xm-X1)*DY-(ym-Y1)*DX;
        // compute point with minimal distance
        double x=xm-d*DY, y=ym+d*DX;
        // compute size of the screen
        double a=Math.max(zc.maxX()-zc.minX(), zc.maxY()-zc.minY());
        if (Math.abs(d)>a) {
            return;
        }
        // compute distance from closest point to source
        double b=(x-X1)*DX+(y-Y1)*DY;
        // compute the two visible endpoints
        k1=b-a;
        k2=b+a;
        k12valid=true;
        if (Partial&&Dep!=null&&!zc.showHidden()) {
            double dd=(zc.maxX()-zc.minX())/20;
            double dmin=-dd, dmax=+dd;
            if (Dep!=null) {
                for (int i=0; i<NDep; i++) {
                    if (!Dep[i].valid()||Dep[i].mustHide(zc)) {
                        continue;
                    }
                    double s=project(Dep[i].getX(), Dep[i].getY());
                    if (s-dd<dmin) {
                        dmin=s-dd;
                    } else if (s+dd>dmax) {
                        dmax=s+dd;
                    }
                }
            }
            if (k1<dmin) {
                k1=dmin;
            }
            if (k2>dmax) {
                k2=dmax;
            }
        }
        double c1=zc.col(X1+k1*DX), c2=zc.col(X1+k2*DX),
                r1=zc.row(Y1+k1*DY), r2=zc.row(Y1+k2*DY);
        // paint:
        if (isStrongSelected()&&g instanceof MyGraphics13) {
            ((MyGraphics13) g).drawMarkerLine(c1, r1, c2, r2);
        }



        g.setColor(this);

        if (tracked()) zc.UniversalTrack.drawTrackLine(this, c1, r1, c2, r2);

        g.drawLine(c1, r1, c2, r2, this);
        String s=getDisplayText();
        if (!s.equals("")) {
            g.setLabelColor(this);
            setFont(g);
            DisplaysText=true;
            if (KeepClose) {
                double side=(YcOffset<0)?1:-1;
                drawLabel(g, s, zc, X1+XcOffset*DX, Y1+XcOffset*DY,
                        side*DX, side*DY, 0, 0);
            } else {
                drawLabel(g, s, zc, x+a/5*DX, y+a/5*DY, DX, DY, XcOffset, YcOffset);
            }
        }
    }

    public boolean canKeepClose() {
        return true;
    }

    public void setKeepClose(double x, double y) {
        KeepClose=true;
        XcOffset=(x-X1)*DX+(y-Y1)*DY;
        YcOffset=(x-X1)*DY-(y-Y1)*DX;
    }

    public boolean nearto(int c, int r, ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        //compute point at c,r
        double x=zc.x(c), y=zc.y(r);
        // compute distance from x,y
        double d=(x-X1)*DY-(y-Y1)*DX;
        // scale in screen coordinates
        Value=Math.abs(zc.col(zc.minX()+d)-zc.col(zc.minX()));
        return Value<zc.selectionSize()*2;
    }

    public boolean onlynearto(int c, int r, ZirkelCanvas zc) {
        return false;
    }

    public static Coordinates intersect(PrimitiveLineObject l1, PrimitiveLineObject l2) // compute the intersection coordinates of two lines
    {
        double det=-l1.DX*l2.DY+l1.DY*l2.DX;
        if (Math.abs(det)<1e-10) {
            return null;
        }
        double a=(-(l2.X1-l1.X1)*l2.DY+(l2.Y1-l1.Y1)*l2.DX)/det;
        return new Coordinates(l1.X1+a*l1.DX, l1.Y1+a*l1.DY);
    }

    public static Coordinates intersect(PrimitiveLineObject l, PrimitiveCircleObject c) // compute the intersection coordinates of a line with a circle
    {
        double x=c.getX(), y=c.getY(), r=c.getR();
        double d=(x-l.X1)*l.DY-(y-l.Y1)*l.DX;
        if (Math.abs(d)>r+1e-10) {
            return null;
        }
        x-=d*l.DY;
        y+=d*l.DX;
        double h=r*r-d*d;
        if (h>0) {
            h=Math.sqrt(h);
        } else {
            h=0;
        }
        return new Coordinates(x+h*l.DX, y+h*l.DY, x-h*l.DX, y-h*l.DY);
    }

    public static Coordinates intersect(PrimitiveLineObject l, QuadricObject q) {
        // compute the intersection coordinates of a line with a quadric
        // done with XCAS :
//        System.out.println("l.DX="+l.DX+" l.DY="+l.DY);
        double M=-l.DY, N2=l.DX, P=-(M*l.X1+N2*l.Y1);
//        System.out.println("M="+M+" N2="+N2+" P="+P);
        double A=q.X[0], B=q.X[1], C=q.X[2], D=q.X[3], E=q.X[4], F=q.X[5];
        double x1=0, x2=0, y1=0, y2=0;
        if (N2!=0) {
            double part1=-2*B*M*P-C*N2*N2+D*M*N2+E*N2*P;
            double part2=Math.abs(N2)*Math.sqrt(-2*M*D*N2*C+
                    4*P*D*A*N2+4*P*M*B*C+4*E*M*N2*F-
                    2*E*P*N2*C-2*E*P*M*D-4*M*M*B*F-4*P*P*A*B-
                    4*A*N2*N2*F+N2*N2*C*C+M*M*D*D+E*E*P*P);

            double part3=2*A*N2*N2+2*B*M*M+(-2*E)*M*N2;
            x1=(part1+part2)/part3;
            if (Double.isNaN(x1)) {
                return null;
            }
            y1=(-M*x1-P)/N2;
            x2=(part1-part2)/part3;
            y2=(-M*x2-P)/N2;
            if (((x2-x1)/l.DX)<0) {
                return new Coordinates(x2, y2, x1, y1);
            }
        } else {
            x1=-P/M;
            x2=x1;
            double part1=-D*M*M+E*M*P;
            double part2=Math.abs(M)*Math.sqrt(4*P*M*B*C-2*E*P*M*D-4*M*M*B*F-
                    4*P*P*A*B+M*M*D*D+E*E*P*P);
            double part3=2*M*M*B;
            y1=(part1+part2)/part3;
            if (Double.isNaN(y1)) {
                return null;
            }
            y2=(part1-part2)/part3;
            if (((y2-y1)/l.DY)<0) {
                return new Coordinates(x2, y2, x1, y1);
            }
        }
        
        return new Coordinates(x1, y1, x2, y2);
    }

    public double getDX() {
        return DX;
    }

    public double getDY() {
        return DY;
    }

    public double getX() {
        return X1;
    }

    public double getY() {
        return Y1;
    }

    public void edit(ZirkelCanvas zc) {
        ObjectEditDialog d=new LineEditDialog(zc.getFrame(), this);
        d.setVisible(true);
        zc.getConstruction().updateCircleDep();
        zc.repaint();
        if (d.wantsMore()) {
            new EditConditionals(zc.getFrame(), this);
            validate();
        }
    }

    public String getEquation() {
        double y=DX, x=-DY;
        double c=y*Y1+x*X1;
        if (c<0) {
            c=-c;
            x=-x;
            y=-y;
        }
        if (Math.abs(x)<1e-10&&y<0) {
            c=-c;
            x=-x;
            y=-y;
        } else if (Math.abs(y)<1e-10&&x<0) {
            c=-c;
            x=-x;
            y=-y;
        }
        String s=helpDisplayValue(true, x, "x");
        return s+
                helpDisplayValue(s.equals(""), y, "y")+"="+
                ((Math.abs(c)<1e-10)?"0":helpDisplayNumber(true, c));
    }

    /**
    Test, if the projection of (x,y) to the line contains
    that point.
     */
    public boolean contains(double x, double y) {
        return true;
    }

    public void printArgs(XmlWriter xml) {
        if (Partial) {
            xml.printArg("partial", "true");
        }
    }

    public double project(double x, double y) {
        return (x-X1)*DX+(y-Y1)*DY;
    }

    public boolean equals(ConstructionObject o) {
        if (!(o instanceof PrimitiveLineObject)||!o.valid()) {
            return false;
        }
        PrimitiveLineObject l=(PrimitiveLineObject) o;
        return equals(DX*l.DY-DY*l.DX, 0)&&
                equals((X1-l.X1)*DY-(Y1-l.Y1)*DX, 0);
    }

    public PointObject getP1() {
        return P1;
    }

    public Enumeration points() {
        return depending();
    }

    public boolean locallyLike(ConstructionObject o) {
        if (!(o instanceof PrimitiveLineObject)) {
            return false;
        }
        return (equals(DX, ((PrimitiveLineObject) o).DX)&&
                equals(DY, ((PrimitiveLineObject) o).DY))||
                (equals(-DX, ((PrimitiveLineObject) o).DX)&&
                equals(-DY, ((PrimitiveLineObject) o).DY));
    }

    public boolean isPartial() {
        return Partial;
    }

    public void setPartial(boolean partial) {
        if (Partial==partial) {
            return;
        }
        Partial=partial;
        if (partial) {
            Dep=new PointObject[16];
            NDep=0;
        } else {
            Dep=null;
        }
    }

    public void addDep(PointObject p) {
        if (!Partial||Dep==null||NDep>=Dep.length) {
            return;
        }
        Dep[NDep++]=p;
    }

    public void clearCircleDep() {
        NDep=0;
    }

    public int getDistance (PointObject P)
	{
        double h=project(P.getX(), P.getY());
        double dx=P.getX()-(getX()+h*getDX());
        double dy=P.getY()-(getY()+h*getDY());
        double d=Math.sqrt(dx*dx+dy*dy);
        return (int) Math.round(d*Cn.getPixel());
	}

    public void project(PointObject P) {
        double h=project(P.getX(), P.getY());
        P.setXY(getX()+h*getDX(), getY()+h*getDY());
        P.setA(h);
    }

    public void project(PointObject P, double alpha) {
        P.setXY(getX()+alpha*getDX(), getY()+alpha*getDY());
    }

    public boolean canInteresectWith(ConstructionObject o) {
        return true;
    }
}
