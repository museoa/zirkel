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

// file: LineObject.java
import eric.JGlobals;
import java.awt.*;
import java.util.*;

import javax.swing.JPanel;
import rene.gui.*;
import rene.util.xml.*;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.Count;
import rene.zirkel.dialogs.EditConditionals;
import rene.zirkel.dialogs.ObjectEditDialog;
import rene.zirkel.expression.InvalidException;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.MyGraphics13;

class AreaObjectDialog extends ObjectEditDialog {

    public AreaObjectDialog(Frame f, String title, ConstructionObject o) {
        super(f, title, o);
    }

    public void addFirst(JPanel P) {
        AreaObject AO = (AreaObject) O;

        MyTextField A = new MyTextField("" + AO.area(), 30);
        P.add(new MyLabel(Zirkel.name("edit.area.area")));
        P.add(A);
        A.setEditable(false);
    }
}

public class AreaObject extends ConstructionObject
        implements InsideObject, PointonObject, MoveableObject {

    static Count N = new Count();
    double xpoints[] = new double[3], ypoints[] = new double[3];
    double R, X, Y, A;
    public Vector V;
    boolean Filled = true;

    public AreaObject(Construction c, Vector v) {
        super(c);
        V = v;
        validate();
        updateText();
        Global.getParameter("unit.area", "");
    }

    public void setDefaults() {
        setShowName(Global.getParameter("options.area.shownames", false));
        setShowValue(Global.getParameter("options.area.showvalues", false));
        setColor(Global.getParameter("options.area.color", 0), Global.getParameter("options.area.pcolor", (Color) null));
        setColorType(Global.getParameter("options.area.colortype", 0));
        setFilled(Global.getParameter("options.area.filled", true));
        setSolid(Global.getParameter("options.area.solid", false));
        setHidden(Cn.Hidden);
        setObtuse(Cn.Obtuse);
//        setSolid(Cn.Solid);
        setLarge(Cn.LargeFont);
        setBold(Cn.BoldFont);
        setPartial(Cn.Partial);
    }

    public void setTargetDefaults() {
        setShowName(Global.getParameter("options.area.shownames", false));
        setShowValue(Global.getParameter("options.area.showvalues", false));
        setColor(Global.getParameter("options.area.color", 0), Global.getParameter("options.area.pcolor", (Color) null));
        setColorType(Global.getParameter("options.area.colortype", 0));
        setFilled(Global.getParameter("options.area.filled", true));
        setSolid(Global.getParameter("options.area.solid", false));
    }

    public String getTag() {
        return "Polygon";
    }

    public void updateText() {
        String Text = Zirkel.name("text.area");
        Enumeration en = V.elements();
        boolean first = true;
        while (en.hasMoreElements()) {
            PointObject p = (PointObject) en.nextElement();
            if (!first) {
                Text = Text + ", ";
            } else {
                Text = Text + " ";
            }
            first = false;
            Text = Text + p.getName();
        }
        setText(Text);
    }

    public void validate() {
        Enumeration e = V.elements();
        while (e.hasMoreElements()) {
            if (!((ConstructionObject) e.nextElement()).valid()) {
                Valid = false;
                return;
            }
        }
        if (V.size() < 2) {
            Valid = false;
            return;
        }
        Enumeration en = V.elements();
        double x = 0, y = 0;
        while (en.hasMoreElements()) {
            PointObject p = (PointObject) en.nextElement();
            x += p.getX();
            y += p.getY();
        }
        X = x / V.size();
        Y = y / V.size();
        A = area();
        Valid = true;
    }

    public void edit(ZirkelCanvas zc) {
        ObjectEditDialog d = new AreaObjectDialog(zc.getFrame(), Zirkel.name("edit.area.title"),
                this);
        d.setVisible(true);
        Global.setParameter("unit.area", Unit);
        zc.repaint();
        if (d.wantsMore()) {
            new EditConditionals(zc.getFrame(), this);
            validate();
        }
    }

    public void paint(MyGraphics g, ZirkelCanvas zc) {
        if (!Valid || mustHide(zc)) {
            return;
        }
        int n = V.size();
        if (xpoints.length != n) {
            xpoints = new double[n];
            ypoints = new double[n];
        }
        if (visible(zc)) {
            Enumeration e = V.elements();
            int i = 0;
            while (e.hasMoreElements()) {
                PointObject p = (PointObject) e.nextElement();

                xpoints[i] = zc.col(p.getX());
                ypoints[i] = zc.row(p.getY());
                if (i > 0) {
                    if (isStrongSelected() && g instanceof MyGraphics13) {
                        ((MyGraphics13) g).drawMarkerLine(xpoints[i - 1], ypoints[i - 1], xpoints[i], ypoints[i]);
                    }
                }
                i++;
            }
            if (i > 1) {
                if (isStrongSelected() && g instanceof MyGraphics13) {
                    ((MyGraphics13) g).drawMarkerLine(xpoints[i - 1], ypoints[i - 1], xpoints[0], ypoints[0]);
                }
            }
            g.fillPolygon(xpoints, ypoints, n, this);
        }
        String s = getDisplayText();
        if (!s.equals("")) {
//            if (getColorIndex() == 0 && getColorType() == THICK) {
//                g.setColor(Color.gray.brighter());
//            } else {
//                g.setColor(Color.black);
//            }
            DisplaysText = true;
            TX1 = zc.col(X + XcOffset);
            TY1 = zc.row(Y + YcOffset);
            setFont(g);
            drawLabel(g, s);
        }
    }

    public String getDisplayValue() {
//        return ""+round(Math.abs(A), ZirkelCanvas.LengthsFactor);
        return JGlobals.getLocaleNumber(Math.abs(A), "lengths");
    }

    public void printArgs(XmlWriter xml) {
        if (!Filled) {
            xml.printArg("filled", "false");
        }
        Enumeration e = V.elements();
        int n = 1;
        while (e.hasMoreElements()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            xml.printArg("point" + n, o.getName());
            n++;
        }

        super.printArgs(xml);
    }

    public Enumeration depending() {
        super.depending();
        Enumeration e = V.elements();
        while (e.hasMoreElements()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            DL.add(o);
        }
        return DL.elements();
    }

    public void translate() {
        Enumeration e = V.elements();
        Vector w = new Vector();
        while (e.hasMoreElements()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            w.addElement(o.getTranslation());
        }
        V = w;
    }

    public boolean nearto(int c, int r, ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        return contains(zc.x(c), zc.y(r));
    }

    public boolean nearto(int c, int r, boolean ignorefill, ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        if (contains(zc.x(c), zc.y(r), zc.dx(zc.selectionSize()))) {
            if (ignorefill) {
                return OnBoundary;
            } else {
                return true;
            }
        }
        return false;
    }
    boolean OnBoundary;

    public boolean contains(double x, double y, double eps) {
        if (!Valid) {
            return false;
        }
        OnBoundary = false;
        PointObject First = (PointObject) V.elementAt(0);
        PointObject P = First;
        if (Math.max(Math.abs(P.getX() - x), Math.abs(P.getY() - y)) < eps) {
            OnBoundary = true;
            return true;
        }
        Enumeration e = V.elements();
        double a = Math.atan2(P.getX() - x, P.getY() - y);
        double sum = 0;
        PointObject Q;
        while (e.hasMoreElements()) {
            Q = (PointObject) e.nextElement();
            if (Math.max(Math.abs(Q.getX() - x), Math.abs(Q.getY() - y)) < eps) {
                OnBoundary = true;
                return true;
            }
            double b = Math.atan2(Q.getX() - x, Q.getY() - y);
            double d = b - a;
            if (d > Math.PI) {
                d = d - 2 * Math.PI;
            } else if (d < -Math.PI) {
                d = d + 2 * Math.PI;
            }
            if (Math.abs(Math.abs(d) - Math.PI) < 0.1) {
                OnBoundary = true;
                return true;
            }
            a = b;
            P = Q;
            sum += d;
        }
        Q = First;
        double b = Math.atan2(Q.getX() - x, Q.getY() - y);
        double d = b - a;
        if (d > Math.PI) {
            d = d - 2 * Math.PI;
        } else if (d < -Math.PI) {
            d = d + 2 * Math.PI;
        }
        if (Math.abs(Math.abs(d) - Math.PI) < 0.1) {
            OnBoundary = true;
            return true;
        }
        sum += d;
        return Math.abs(sum) >= Math.PI / 2;
    }

    public boolean contains(double x, double y) {
        return contains(x, y, 1e-4);
    }
    // This one is better than "contains" method :

    public boolean contains2(double x, double y) {
        if (!Valid) {
            return false;
        }
        int npoints = V.size();
        if (npoints <= 2) {
            return false;
        }
        int hits = 0;
        PointObject last = (PointObject) V.get(npoints - 1);
        double lastx = last.getX();
        double lasty = last.getY();



        double curx, cury;

        Enumeration e = V.elements();
        for (int i = 0; i < npoints; lastx = curx, lasty = cury, i++) {
            PointObject p = (PointObject) e.nextElement();
            curx = p.getX();
            cury = p.getY();

            if (cury == lasty) {
                continue;
            }

            double leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }








            double test1,
                    test2;


            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }

    public double area() {
        if (!Valid) {
            return -1;
        }
        PointObject First = (PointObject) V.elementAt(0);
        PointObject P = First;
        Enumeration e = V.elements();
        double sum = 0;
        PointObject Q;
        while (e.hasMoreElements()) {
            Q = (PointObject) e.nextElement();
            sum += (Q.getX() - X) * (P.getY() - Y) - (Q.getY() - Y) * (P.getX() - X);
            P = Q;
        }
        Q = First;
        sum += (Q.getX() - X) * (P.getY() - Y) - (Q.getY() - Y) * (P.getX() - X);
        return sum / 2;
    }

    public boolean equals(ConstructionObject o) {
        if (!(o instanceof AreaObject) || !o.valid()) {
            return false;
        }
        AreaObject a = (AreaObject) o;






        int n = V.size(), m = a.V.size();
        PointObject p[] = new PointObject[n];
        V.copyInto(p);
        PointObject pa[] = new PointObject[m];
        a.V.copyInto(pa);
        double x0 = 0, y0 = 0;
        for (int i = 0; i < m; i++) {
            boolean r = true;
            int j = 0, kj = 0;
            while (true) {
                int k = i + kj;
                if (k >= m) {
                    k -= m;
                }
                if (!p[j].equals(pa[k])) {
                    if (j == 0 || !between(x0, y0, p[j].getX(), p[j].getY(),
                            pa[k].getX(), pa[k].getY())) {
                        r = false;
                        break;
                    }
                } else {
                    x0 = p[j].getX();
                    y0 = p[j].getY();
                    j++;
                }
                kj++;
                if (j >= n || kj >= m) {
                    break;
                }
            }
            if (r && kj >= m) {
                return true;
            }
        }
        for (int i = 0; i < m; i++) {
            boolean r = true;
            int j = 0, kj = 0;
            while (true) {
                int k = i + kj;
                if (k >= m) {
                    k -= m;
                }
                if (!p[n - j - 1].equals(pa[k])) {
                    if (j == 0 || !between(x0, y0, p[n - j - 1].getX(), p[n - j - 1].getY(),
                            pa[k].getX(), pa[k].getY())) {
                        r = false;
                        break;
                    }
                } else {
                    x0 = p[n - j - 1].getX();
                    y0 = p[n - j - 1].getY();
                    j++;
                }
                kj++;
                if (j >= n || kj >= m) {
                    break;
                }
            }
            if (r && kj >= m) {
                return true;
            }
        }
        return false;
    }

    public boolean between(double x0, double y0, double x1, double y1,
            double x, double y) {
        double lambda;
        if (Math.abs(x1 - x0) > Math.abs(y1 - y0)) {
            lambda = (x - x0) / (x1 - x0);
        } else {
            lambda = (y - y0) / (y1 - y0);
        }
        return Math.abs(x0 + lambda * (x1 - x0) - x) < 1e-10 &&
                Math.abs(y0 + lambda * (y1 - y0) - y) < 1e-10;
    }

    public double getValue()
            throws ConstructionException {
        if (!Valid) {
            throw new InvalidException("exception.invalid");
        } else {
            return A;
        }
    }

    public boolean maybeTransparent() {
        return true;
    }

    public void setFilled(boolean flag) {
        Filled = flag;
    }

    public boolean isFilled() {
        return Filled;
    }

    public boolean onlynearto(int x, int y, ZirkelCanvas zc) {
        return false;
    }

    public void keepBaricentricCoords(PointObject P) {
        if ((P.BarycentricCoordsInitialzed) && (V.size() > 2)) {
            PointObject AA = (PointObject) V.get(0);
            PointObject BB = (PointObject) V.get(1);
            PointObject CC = (PointObject) V.get(2);






            double xa = AA.getX(), ya = AA.getY();







            double xb = BB.getX(), yb = BB.getY();


            double xc = CC.getX(), yc = CC.getY();

            double xm = xa + P.Gx * (xb - xa) + P.Gy * (xc - xa);
            double ym = ya + P.Gx * (yb - ya) + P.Gy * (yc - ya);

            P.move(xm, ym);
        } else {
            P.computeBarycentricCoords();
        }
    }

    public int getDistance(PointObject P) {
        double x = P.getX(), y = P.getY();
        Enumeration e = V.elements();
        PointObject p = (PointObject) e.nextElement();
        double x1 = p.getX(), y1 = p.getY();
        double xstart = x1, ystart = y1;
        int count = 0;


        double xmin = x1, ymin = y1, dmin = 1e20, hmin = 0;
        while (e.hasMoreElements()) {
            p = (PointObject) e.nextElement();
            double x2 = p.getX(), y2 = p.getY();
            double dx = x2 - x1, dy = y2 - y1;
            double r = dx * dx + dy * dy;
            if (r > 1e-5) {
                double h = dx * (x - x1) / r + dy * (y - y1) / r;
                if (h > 1) {
                    h = 1;
                } else if (h < 0) {
                    h = 0;
                }
                double xh = x1 + h * dx,
                        yh = y1 + h * dy;
                double dist = Math.sqrt((x - xh) * (x - xh) + (y - yh) * (y - yh));
                if (dist < dmin) {
                    dmin = dist;
                    xmin = xh;
                    ymin = yh;
                    hmin = count + h;
                }
            }
            count++;
            x1 = x2;
            y1 = y2;
        }
        double x2 = xstart, y2 = ystart;
        double dx = x2 - x1, dy = y2 - y1;
        double r = dx * dx + dy * dy;
        if (r > 1e-5) {
            double h = dx * (x - x1) / r + dy * (y - y1) / r;
            if (h > 1) {
                h = 1;
            } else if (h < 0) {
                h = 0;
            }
            double xh = x1 + h * dx,
                    yh = y1 + h * dy;
            double dist = Math.sqrt((x - xh) * (x - xh) + (y - yh) * (y - yh));
            if (dist < dmin) {
                dmin = dist;
                xmin = xh;
                ymin = yh;
                hmin = count + h;
            }
        }
        double dd = Math.sqrt((P.getX() - xmin) * (P.getX() - xmin) + (P.getY() - ymin) * (P.getY() - ymin));
        return (int) Math.round(dd * Cn.getPixel());
    }

    public void project(PointObject P) {
        double x = P.getX(), y = P.getY();
        Enumeration e = V.elements();
        PointObject p = (PointObject) e.nextElement();
        double x1 = p.getX(), y1 = p.getY();
        double xstart = x1, ystart = y1;
        int count = 0;


        double xmin = x1, ymin = y1, dmin = 1e20, hmin = 0;
        while (e.hasMoreElements()) {
            p = (PointObject) e.nextElement();
            double x2 = p.getX(), y2 = p.getY();
            double dx = x2 - x1, dy = y2 - y1;
            double r = dx * dx + dy * dy;
            if (r > 1e-5) {
                double h = dx * (x - x1) / r + dy * (y - y1) / r;
                if (h > 1) {
                    h = 1;
                } else if (h < 0) {
                    h = 0;
                }
                double xh = x1 + h * dx,
                        yh = y1 + h * dy;
                double dist = Math.sqrt((x - xh) * (x - xh) + (y - yh) * (y - yh));
                if (dist < dmin) {
                    dmin = dist;
                    xmin = xh;
                    ymin = yh;
                    hmin = count + h;
                }
            }
            count++;
            x1 = x2;
            y1 = y2;
        }
        double x2 = xstart, y2 = ystart;
        double dx = x2 - x1, dy = y2 - y1;
        double r = dx * dx + dy * dy;
        if (r > 1e-5) {
            double h = dx * (x - x1) / r + dy * (y - y1) / r;
            if (h > 1) {
                h = 1;
            } else if (h < 0) {
                h = 0;
            }
            double xh = x1 + h * dx,
                    yh = y1 + h * dy;
            double dist = Math.sqrt((x - xh) * (x - xh) + (y - yh) * (y - yh));
            if (dist < dmin) {
                dmin = dist;
                xmin = xh;
                ymin = yh;
                hmin = count + h;
            }
        }
        P.move(xmin, ymin);
        P.setA(hmin);
    }

    public void project(PointObject P, double alpha) {
        int i = (int) Math.floor(alpha);
        double h = alpha - i;
        if (i < 0 || i >= V.size()) {
            project(P);
            return;
        }
        PointObject P1 = (PointObject) V.elementAt(i);
        PointObject P2;
        if (i == V.size() - 1) {
            P2 = (PointObject) V.elementAt(0);
        } else {
            P2 = (PointObject) V.elementAt(i + 1);
        }
        P.setXY(P1.getX() + h * (P2.getX() - P1.getX()), P1.getY() + h * (P2.getY() - P1.getY()));
    }

    public double containsInside(PointObject P) {
        boolean inside = contains2(P.getX(), P.getY());
        if (inside && OnBoundary) {
            return 0.5;
        } else if (inside) {
            return 1;
        }
        return 0;
    }

    public boolean keepInside(PointObject P) {
        keepBaricentricCoords(P);
        if (contains2(P.getX(), P.getY())) {
            return true;
        }
        project(P);
        return false;
    }

    public void dragTo(double x, double y) {
        Enumeration e = V.elements();
        int i = 0;
        while (e.hasMoreElements()) {
            PointObject p = (PointObject) e.nextElement();
            p.move(xd[i] + (x - x1), yd[i] + (y - y1));
            i++;
        }
    }

    public void move(double x, double y) {
    }

    public boolean moveable() {
        if (V == null) {
            return false;
        }
        Enumeration e = V.elements();
        while (e.hasMoreElements()) {
            if (!((PointObject) e.nextElement()).moveable()) {
                return false;
            }
        }
        return true;
    }
    double xd[], yd[], x1, y1;

    public void startDrag(double x, double y) {
        if (xd == null || xd.length != V.size()) {
            xd = new double[V.size()];
            yd = new double[V.size()];
        }
        Enumeration e = V.elements();
        int i = 0;
        while (e.hasMoreElements()) {
            PointObject p = (PointObject) e.nextElement();
            xd[i] = p.getX();
            yd[i] = p.getY();
            i++;
        }
        x1 = x;
        y1 = y;
    }

    public double getOldX() {
        return 0;
    }

    public double getOldY() {
        return 0;
    }

    public void snap(ZirkelCanvas zc) {
        if (moveable()) {
            Enumeration e = V.elements();
            while (e.hasMoreElements()) {
                PointObject p = (PointObject) e.nextElement();
                p.snap(zc);
            }
        }
    }

    public boolean canInteresectWith(ConstructionObject o) {
        if (o instanceof PointonObject) {
            ConstructionObject line = (ConstructionObject) o;
            Enumeration e = V.elements();
            {
                PointObject P = (PointObject) e.nextElement();
                if (line.contains(P)) {
                    return false;
                }
            }
        }
        return true;
    }
}
