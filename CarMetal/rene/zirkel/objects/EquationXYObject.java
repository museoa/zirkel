/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.objects;

import java.util.Enumeration;
import java.util.Vector;
import rene.gui.Global;
import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.expression.Expression;
import rene.zirkel.expression.InvalidException;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.structures.Coordinates;

/**
 *
 * @author erichake
 */
public class EquationXYObject extends ConstructionObject implements PointonObject {

    Vector V=new Vector();
    //Number of horizontal divisions for the full zero search algorithm :
    int Dhor=100;
    // canvas left,top,width,height :
    double Wl=0, Wt=0, Ww=0, Wh=0;
    Construction C;
    Expression EY=null;
    double X[]={0, 0};
    String Var[]={"x", "y"};

    public EquationXYObject(Construction c, String eq) {
        super(c);
        EY=new Expression(eq, c, this, Var);
        updateText();
        C=c;
    }
    
   public EquationXYObject(Construction c, String eq,int d) {
        this(c,eq);
        Dhor=d;
    }
    
    public int getDhor(){
        return Dhor;
    }

    public void setDhor(int newDHor){
        Dhor=newDHor;
        compute();
        updateText();
    }
    
    public void setDefaults() {
        setShowName(Global.getParameter("options.locus.shownames", false));
        setShowValue(Global.getParameter("options.locus.showvalues", false));
        setColor(Global.getParameter("options.locus.color", 0));
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
        setColor(Global.getParameter("options.locus.color", 0));
        setColorType(Global.getParameter("options.locus.colortype", 0));
        setFilled(Global.getParameter("options.locus.filled", false));
    }
    
    
    public void setEquation(String eq, ZirkelCanvas zc) {
        EY=new Expression(eq, C, this, Var);
        compute();
        updateText();

    }

    public double getValue(String var)
            throws ConstructionException {
        if (!Valid) {
            throw new InvalidException("exception.invalid");
        }

        return (var.equals("x"))?X[0]:X[1];

    }

    public double evaluateF(double x, double y)
            throws ConstructionException {
        X[0]=x;
        X[1]=y;
        try {
            return EY.getValue();
        } catch (Exception e) {
            throw new ConstructionException("");
        }
    }

    public void compute() {
        Wl=C.getX()-C.getW();
        Wt=C.getY()-C.getH()/2;
        Ww=2*C.getW();
        Wh=C.getH();
        searchZerosSegments();
    }

    public void searchZerosSegments() {
        V.clear();
        double dx=Ww/Dhor;
        double dy=dx*Math.sqrt(3)/2;
        int xn=Dhor+2;
        int yn=(int) (Wh/dy)+1;

        double[] x=new double[xn+1];
        double[] y=new double[yn+1];
        double[][] z=new double[xn+1][yn+1];

        for (int i=0; i<=xn; i++) {
            x[i]=Wl-dx+i*dx;
        }
        for (int j=0; j<=yn; j++) {
            y[j]=Wt+j*dy;
        }
        for (int i=0; i<=xn; i++) {
            for (int j=0; j<=yn; j++) {
                xv[0]=x[i];
                xv[1]=y[j];
                if (j%2==0) {
                    xv[0]+=dx/2;
                }
                try {
                    z[i][j]=evaluateF(xv[0], xv[1]);
                } catch (Exception e) {
                    z[i][j]=0;
                }
            }
        }
        for (int i=0; i<=xn-1; i++) {
            for (int j=0; j<=yn-1; j++) {
                if (j%2==0) {
                    searchOneZerosSegment(V, x[i]+dx/2, y[j], z[i][j], x[i], y[j+1], z[i][j+1], x[i+1], y[j+1], z[i+1][j+1]);
                    searchOneZerosSegment(V, x[i]+dx/2, y[j], z[i][j], x[i+1]+dx/2, y[j], z[i+1][j], x[i+1], y[j+1], z[i+1][j+1]);
                } else {
                    searchOneZerosSegment(V, x[i], y[j], z[i][j], x[i+1], y[j], z[i+1][j], x[i]+dx/2, y[j+1], z[i][j+1]);
                    searchOneZerosSegment(V, x[i+1], y[j], z[i+1][j], x[i+1]-dx/2, y[j+1], z[i][j+1], x[i+1]+dx/2, y[j+1], z[i+1][j+1]);
                }
            }
        }
    }

    public void searchOneZerosSegment(Vector v, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3) {
        if (z1*z2<0) {
            double l1=z2/(z2-z1);
            l1=fix(x1, y1, z1, x2, y2, z2, l1);
            double m1=1-l1;
            if (z1*z3<0) {
                double l2=z3/(z3-z1);
                l2=fix(x1, y1, z1, x3, y3, z3, l2);
                double m2=1-l2;
                v.add(new Coordinates(l1*x1+m1*x2, l1*y1+m1*y2, l2*x1+m2*x3, l2*y1+m2*y3));
            } else {
                double l2=z3/(z3-z2);
                l2=fix(x2, y2, z2, x3, y3, z3, l2);
                double m2=1-l2;
                v.add(new Coordinates(l1*x1+m1*x2, l1*y1+m1*y2, l2*x2+m2*x3, l2*y2+m2*y3));
            }
        } else if (z1*z3<0) {
            double l1=z3/(z3-z1);
            l1=fix(x1, y1, z1, x3, y3, z3, l1);
            double m1=1-l1;
            double l2=z3/(z3-z2);
            l2=fix(x2, y2, z2, x3, y3, z3, l2);
            double m2=1-l2;
            v.add(new Coordinates(l1*x1+m1*x3, l1*y1+m1*y3, l2*x2+m2*x3, l2*y2+m2*y3));
        }
    }
    double xv[]=new double[2];

    public double fix(double x1, double y1, double z1, double x2, double y2, double z2, double l1) {
        try {
            double z=evaluateF(x1*l1+x2*(1-l1), y1*l1+y2*(1-l1));
            if (Math.abs(z)>(Math.abs(z1)+Math.abs(z2))*1e-5) {
                double mu=1-l1, mu2=mu*mu, mu3=mu2*mu, mu4=mu3*mu;
                double h=Math.sqrt(mu4*z2*z2+((-2*mu4+4*mu3-2*mu2)*z1-2*mu2*z)*z2+(mu4-4*mu3+6*mu2-4*mu+1)*z1*z1+(-2*mu2+4*mu-2)*z*z1+z*z);
                double h1=(mu2*z2-mu2*z1+z1-z+h)/(2*(mu*z2-mu*z1+z1-z));
                double h2=(mu2*z2-mu2*z1+z1-z-h)/(2*(mu*z2-mu*z1+z1-z));
                if (h1>=0&&h1<=1) {
                    l1=1-h1;
                } else if (h2>=0&&h2<=1) {
                    l1=1-h2;
                }
            }
        } catch (Exception e) {
        }
        return l1;
    }

    public void paint(MyGraphics g, ZirkelCanvas zc) {
        if (!Valid||mustHide(zc)) {
            return;
        }
        Coordinates c1;
        g.setColor(this);
        Enumeration e=V.elements();
        while (e.hasMoreElements()) {
            c1=(Coordinates) e.nextElement();
            g.drawLine(zc.col(c1.X), zc.row(c1.Y), zc.col(c1.X1), zc.row(c1.Y1),this);
        }
    }

    public boolean nearto(int cc, int rr, ZirkelCanvas zc) {

        Enumeration e=V.elements();
        Coordinates c1;
        while (e.hasMoreElements()) {
            c1=(Coordinates) e.nextElement();
            if (((Math.abs(zc.col(c1.X)-cc)+Math.abs(zc.row(c1.Y)-rr))<5)||
                    ((Math.abs(zc.col(c1.X1)-cc)+Math.abs(zc.row(c1.Y1)-rr))<5)) {
                return true;
            }
        }


        return false;
    }

    public void project(PointObject P) {

        xx=P.getX();
        yy=P.getY();
        Coordinates c;
        double dd, dm=0, xm1=0, ym1=0, zm1=0, xm2=0, ym2=0, zm2=0;

        // search the closer segment [M1M2] :
        Enumeration e=V.elements();
        if (e.hasMoreElements()) {
            c=(Coordinates) e.nextElement();
            xm1=c.X;
            ym1=c.Y;
            xm2=c.X1;
            ym2=c.Y1;
            dm=Math.sqrt((c.X-xx)*(c.X-xx)+(c.Y-yy)*(c.Y-yy))+
                    Math.sqrt((c.X1-xx)*(c.X1-xx)+(c.Y1-yy)*(c.Y1-yy))-
                    Math.sqrt((c.X-c.X1)*(c.X-c.X1)+(c.Y-c.Y1)*(c.Y-c.Y1));
        } else {
            return;
        }
        while (e.hasMoreElements()) {
            c=(Coordinates) e.nextElement();
            dd=Math.sqrt((c.X-xx)*(c.X-xx)+(c.Y-yy)*(c.Y-yy))+
                    Math.sqrt((c.X1-xx)*(c.X1-xx)+(c.Y1-yy)*(c.Y1-yy))-
                    Math.sqrt((c.X-c.X1)*(c.X-c.X1)+(c.Y-c.Y1)*(c.Y-c.Y1));
            if (dd<dm) {
                xm1=c.X;
                ym1=c.Y;
                xm2=c.X1;
                ym2=c.Y1;
                dm=dd;
            }
        }

        // coords du vecteur unitaire directeur de (M1M2) :
        double lg=Math.sqrt((xm2-xm1)*(xm2-xm1)+(ym2-ym1)*(ym2-ym1));
        double dx=(xm2-xm1)/lg;
        double dy=(ym2-ym1)/lg;

        // coords du point H projet� ortho de P sur (M1M2) :
        double h=(xx-xm1)*dx+(yy-ym1)*dy;
        double xh=xm1+h*dx;
        double yh=ym1+h*dy;

        // coords du vecteur unitaire directeur de (HP) :
        lg=Math.sqrt((xx-xh)*(xx-xh)+(yy-yh)*(yy-yh));
        dx=(xx-xh)/lg;
        dy=(yy-yh)/lg;

        // ten pixel length :
        double d10=10/C.getPixel();

        // Try to have a very accurate precision :
        try {
            xm1=xh+d10*dx;
            ym1=yh+d10*dy;
            zm1=evaluateF(xm1, ym1);
            xm2=xh-d10*dx;
            ym2=yh-d10*dy;
            zm2=evaluateF(xm2, ym2);
            if (zm1*zm2<0) {
                findRootBetween(xm1, ym1, zm1, xm2, ym2, zm2);
                P.move(xx, yy);
                return;
            }
        } catch (Exception ex) {
        }

        // If nothing found, then project on the segment (low precision) :
        P.move(xh, yh);
    }
    double xx, yy, zz;

    public void findRootBetween(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        try {
            xx=(x1+x2)/2;
            yy=(y1+y2)/2;
            zz=evaluateF(xx, yy);
            if (Math.abs(zz)<1e-10) {
                return;
            }
            if (zz*z1<0) {
                findRootBetween(x1, y1, z1, xx, yy, zz);
            } else {
                findRootBetween(xx, yy, zz, x2, y2, z2);
            }
        } catch (Exception e) {
        }
    }

    public void project(PointObject P, double alpha) {
        project(P);
    }

    public boolean canInteresectWith(ConstructionObject o) {
        return false;
    }

    public String getEY() {
        if (EY!=null) {
            return EY.toString();
        } else {
            return "0";
        }
    }

    public String getDisplayValue() {
        return EY.toString();
    }

    public String getTag() {
        return "EqXY";
    }

    public void printArgs(XmlWriter xml) {
        xml.printArg("f", EY.toString());
        xml.printArg("Dhor", String.valueOf(Dhor));
    }

    public void updateText() {
        setText(getDisplayValue()+"=0");
    }

    public void edit(ZirkelCanvas zc) {
        if (!rene.zirkel.Zirkel.IsApplet) {
            eric.JGlobals.EditObject(this);
            return;
        }
    }
}
