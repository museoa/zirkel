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

// file: SegmentObject.java
import java.util.Enumeration;

import rene.util.xml.XmlWriter;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.graphics.MyGraphics;

public class AxisObject extends PrimitiveLineObject {

    public AxisObject(Construction c, boolean xaxis) {
        super(c,(xaxis)?"xAxis":"yAxis");

        X1=0;
        Y1=0;
        if (xaxis) {
            DX=1;
            DY=0;

        } else {
            DX=0;
            DY=1;
        }
        updateText();
    }

    public void setName() {

//        if (DX==1) {
//            Name="xaxis"+getN();
//        } else {
//            Name="yaxis"+getN();
//        }
//

    }

    public boolean nearto(int c, int r, ZirkelCanvas zc) {
        if (!displays(zc)) {
            return false;
        }
        if (!zc.showGrid()) {
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
    
    
//    public static Coordinates intersect(PrimitiveLineObject l, FunctionObject q) {
//        
//        
//        
//        return new Coordinates(0, 0);
//    }

//    public String getTag() {
//        return "Axis";
//    }
    public void printArgs(XmlWriter xml) {
        if (DX==1) {
            xml.printArg("xaxis", "true");
        } else {
            xml.printArg("yaxis", "true");
        }
    }

    public Enumeration depending() {
        DL.reset();

        return DL.elements();
    }

    public double getLength() {
        return 0;
    }

    public void translate() {
        P1=(PointObject) P1.getTranslation();
    }

    public boolean contains(PointObject p) {
        return false;
    }

    public void paint(MyGraphics g, ZirkelCanvas zc) {
        if (!Valid||mustHide(zc)) {
            return;
        }

//        if (isStrongSelected()&&g instanceof MyGraphics13) {
//            ((MyGraphics13) g).drawMarkerLine(zc.col(-5),zc.row(1),zc.col(5),zc.row(1));
//        }




        if (zc.showGrid()) {
            if (indicated()) {
                g.setColor(this);
                if (DX==1) {
                    g.drawLine(0, zc.row(0), zc.IW, zc.row(0), this);
                } else {
                    g.drawLine(zc.col(0), 0, zc.col(0), zc.IH, this);
                }
            }
        }

    }

    public void updateText() {
        if (DX==1) {
            setText("X axis");
        } else {
            setText("Y axis");
        }
    }

    public void dragTo(double x, double y) {

    }

    public void move(double x, double y) {
    }

    public boolean moveable() {
        return false;
    }
    double x1, y1, x2, y2, x3, y3;

    public void startDrag(double x, double y) {

    }

    public void snap(ZirkelCanvas zc) {

    }

    public double getOldX() {
        return 0;
    }

    public double getOldY() {
        return 0;
    }
}
