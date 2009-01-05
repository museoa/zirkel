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

import rene.util.xml.XmlWriter;
import rene.zirkel.construction.Construction;
import rene.zirkel.expression.Secant;

/**
 * An intersection between two object that can bind
 * points on them. The intersection is computed by
 * projecting the point to both objects and predicting
 * the intersection point. 
 * 
 * @author Rene
 */
public class PointonObjectIntersectionObject extends IntersectionObject {

    public double Eps=1e-5;
    private boolean xAxisFunctionIntersection=false,  yAxisFunctionIntersection=false;
    private FunctionObject F=null;

    public PointonObjectIntersectionObject(Construction c,
            ConstructionObject p1, ConstructionObject p2) {
        super(c, p1, p2);
        if ((p1 instanceof AxisObject)&&(p2 instanceof FunctionObject)) {

            F=(FunctionObject) p2;
            if (F.getEX().toString().equals("x")) {
                AxisObject ax=((AxisObject) p1);
                if (ax.DX==1) {
                    xAxisFunctionIntersection=true;
                } else {
                    yAxisFunctionIntersection=true;
                }
            }

        } else if ((p2 instanceof AxisObject)&&(p1 instanceof FunctionObject)) {
            F=(FunctionObject) p1;
            if (F.getEX().toString().equals("x")) {
                AxisObject ax=((AxisObject) p2);
                if (ax.DX==1) {
                    xAxisFunctionIntersection=true;
                } else {
                    yAxisFunctionIntersection=true;
                }
            }
        }

    }

    public void validate(double x, double y) {
        setXY(x, y);
        validate();
    }

    public void printArgs(XmlWriter xml) {
        xml.printArg("first", P1.getName());
        xml.printArg("second", P2.getName());
        xml.printArg("x", ""+getX());
        xml.printArg("y", ""+getY());
        if (getAway()!=null) {
            if (StayAway) {
                xml.printArg("awayfrom", getAway().getName());
            } else {
                xml.printArg("closeto", getAway().getName());
            }
        }
        printType(xml);
        if (!Restricted) {
            xml.printArg("valid", "true");
        }
    }

    /**
     * Troublesome function to intersect two objects.
     * This is done by projecting the intersection point to
     * each of the objects in turn.
     * 
     * To speed up the convergence, a line intersection is
     * computed, where the lines approximate the objects.
     */
    public void validate() {
        if (!P1.valid()||!P2.valid()) {
            Valid=false;
        } else {
            Valid=true;
        }
        if (!Valid) {
            return;
        }


        if (yAxisFunctionIntersection) {
            try {
                setXY(0, F.evaluateF(0));
                return;
            } catch (Exception ex) {
            }
        }

        if (xAxisFunctionIntersection) {
            try {
                
                double xn=0;
                double yn=0;
                for (int i=0; i<5; i++) {
                    double distold=projectOnce();
                    if (!Valid) {
                        return;
                    }
                    double dist1=projectOnce();
                    if (dist1>=distold&&dist1<Eps) {
                        break;
                    }
                    distold=dist1;
                    double a=x1-x;
                    double b=y1-y;
                    double s=Math.max(Math.abs(a), Math.abs(b));
                    if (Math.abs(s)>1e-13) {
                        a/=s;
                    }
                    b/=s;
                    double c=X-x1;
                    double d=Y-y1;
                    s=Math.max(Math.abs(c), Math.abs(d));
                    if (Math.abs(s)>1e-13) {
                        c/=s;
                    }
                    d/=s;
                    double e=a*x1+b*y1;
                    double f=c*X+d*Y;
                    double det=a*d-c*b;
                    if (Math.abs(det)>1e-13) {
                        xn=(e*d-f*b)/det;
                        double xold=X;
                        double yold=Y;
                        double dist2=projectOnce();
                        if (dist2<dist1) {
                            distold=dist2;
                        }
                    }
                }
                

                setXY(Secant.compute(F, xn-1e-1, xn+1e-1, 1e-10), 0);








                return;
            } catch (Exception ex) {
            }
        }




        double distold=projectOnce();
        if (!Valid) {
            return;
        }
        for (int i=0; i<10; i++) {
            double dist1=projectOnce();
            if (dist1>=distold&&dist1<Eps) {
                return;
            }
            distold=dist1;
            double a=(x1-x), b=(y1-y);
            double s=Math.max(Math.abs(a), Math.abs(b));
            if (Math.abs(s)>1e-13) {
                a/=s;
            }
            b/=s;
            double c=(X-x1), d=(Y-y1);
            s=Math.max(Math.abs(c), Math.abs(d));
            if (Math.abs(s)>1e-13) {
                c/=s;
            }
            d/=s;
            double e=a*x1+b*y1, f=c*X+d*Y;
            double det=a*d-c*b;
            if (Math.abs(det)>1e-13) {
                double xn=(e*d-f*b)/det;
                double yn=(a*f-c*e)/det;
                double xold=X, yold=Y;
                setXY(xn, yn);
                double dist2=projectOnce();
                if (dist2>dist1) // interpolation does not work
                {
                    setXY(xold, yold);
                } else {
                    distold=dist2;
                }
            }

        }




        Valid=false;
    }
    double x, y, x1, y1;

    public double projectOnce() {
        x=X;
        y=Y;
//		 System.out.println("Before: "+X+" "+Y);
        ((PointonObject) P1).project(this);
//		 System.out.println("Projected to "+P1.getName()+": "+X+" "+Y);
        double dist=Math.max(Math.abs(X-x), Math.abs(Y-y));
        x1=X;
        y1=Y;
        ((PointonObject) P2).project(this);
//		 System.out.println("Projected to "+P2.getName()+": "+X+" "+Y);
        double dist1=Math.max(Math.abs(X-x1), Math.abs(Y-y1));
        return Math.max(dist, dist1);
    }

    public boolean moveable() {
        return true;
    }
}

