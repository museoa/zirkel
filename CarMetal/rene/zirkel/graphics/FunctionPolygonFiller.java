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
package rene.zirkel.graphics;

import java.util.Enumeration;
import java.util.Vector;

import rene.zirkel.objects.FunctionObject;
import rene.zirkel.structures.Coordinates;

/**
 * @author Rene
 *
 */
public class FunctionPolygonFiller {

    MyGraphics G;
    FunctionObject O;
    Vector V;
    double Ymin, Ymax;
    boolean cartesian = true;

    public FunctionPolygonFiller(MyGraphics g, FunctionObject o, double ymin, double ymax) {
        G = g;
        O = o;
        Ymin = ymin-100;
        Ymax = ymax+100;
        cartesian = O.isCartesian();
        V = new Vector();
    }

    public void add(double c, double r) {
        double r0 = r;
        if (r > Ymax) {
            r0 = Ymax;
        } else if (r < Ymin) {
            r0 = Ymin;
        }
        V.add(new Coordinates(c, r0));
    }

    public void fillPolygon(double origin) {
        int bord = cartesian ? 2 : 0;
        int nx = V.size() + bord;
        if (nx > bord) {
            double x[] = new double[nx];
            double y[] = new double[nx];
            Enumeration e = V.elements();
            int i = 0;
            if (e.hasMoreElements()) {
                Coordinates c = (Coordinates) e.nextElement();
                if (cartesian) {
                    x[i] = c.X;
                    y[i++] = origin;
                    x[i] = c.X;
                    y[i++] = c.Y;
                } else {
                    x[i] = c.X;
                    y[i++] = c.Y;
                }
            }
            while (e.hasMoreElements()) {
                Coordinates c = (Coordinates) e.nextElement();
                x[i] = c.X;
                y[i++] = c.Y;
            }
            if (cartesian) {
                x[i] = x[i - 1];
                y[i] = origin;
            }
            G.fillPolygon(x, y, nx, false, true, O);
            V.removeAllElements();
        }

    }
}

