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
package rene.zirkel.tools;

// file: Tracker.java
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.graphics.MyGraphics;
import rene.zirkel.graphics.MyGraphics13;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;

public class UniversalTracker extends javax.swing.JPanel {

    public BufferedImage TrackI=null;
    public MyGraphics TrackIG=null;
    int IW=0, IH=0;
    double DX=0, Xmin=0, Ymin=0;
    boolean isActive=false;
    ZirkelCanvas ZC;

    public UniversalTracker(ZirkelCanvas zc) {
        ZC=zc;
    }

    public void setActive(boolean b) {
        isActive=b;
    }

    public boolean isActive() {
        return isActive;
    }

    public void clearTrackObjects() {
        Enumeration e=ZC.getConstruction().V.elements();
        while (e.hasMoreElements()) {
            ConstructionObject o=(ConstructionObject) e.nextElement();
            o.setTracked(false);
        }
        isActive=false;
    }

    public synchronized void draw() {
        if (TrackI==null) {
            return;
        }
        if (!isActive) {
            return;
        }
// in case of zoom or coordinates system move :
        if ((ZC.DX!=DX)||(ZC.Xmin!=Xmin)||(ZC.Ymin!=Ymin)) {
            IW=ZC.IW;
            IH=ZC.IH;
            DX=ZC.DX;
            Xmin=ZC.Xmin;
            Ymin=ZC.Ymin;
            clearTrackImage();
        }
        ZC.I.getGraphics().drawImage(TrackI, 0, 0, this);
    }

    public void createTrackImage() {
        IW=ZC.IW;
        IH=ZC.IH;
        DX=ZC.DX;
        Xmin=ZC.Xmin;
        Ymin=ZC.Ymin;
        TrackI=new BufferedImage(IW, IH, BufferedImage.TYPE_INT_ARGB);
        clearTrackImage();
        TrackIG=new MyGraphics13(TrackI.getGraphics(), ZC);
    }

    public void clearTrackImage() {
        Graphics2D g2D=TrackI.createGraphics();
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
        Rectangle2D.Double rect=new Rectangle2D.Double(0, 0, IW, IH);
        g2D.fill(rect);
    }

    public void drawTrackCircle(PrimitiveCircleObject o, double d, double d0, double r) {
        isActive=true;
        TrackIG.setColor(o);
        TrackIG.drawCircle(d, d0, r, o);
    }

    public void drawTrackLine(ConstructionObject o, double c1, double r1, double c2, double r2) {
        isActive=true;
        TrackIG.setColor(o);
        TrackIG.drawLine(c1, r1, c2, r2, o);
    }

    public void drawTrackPoint(ConstructionObject o, double X, double Y, int type) {
        isActive=true;
        if (TrackIG==null) {
            return;
        }
        double size=ZC.pointSize();
        if (size<1) {
            size=1;
        }
        double r=ZC.col(X), c=ZC.row(Y);
        if (o.visible(ZC)) {
            boolean ind=o.indicated();
            boolean sel=o.selected();
            o.setIndicated(false);
            o.setSelected(false);
            switch (type) {
                case PointObject.SQUARE:
                    double sx=r-size-1,
                     sy=c-size-1,
                     sw=2*size+2;
                    if (o.getColorType()==ConstructionObject.THICK) {
                        TrackIG.fillRect(sx, sy, sw, sw, true, false, o);
                    } else {
                        TrackIG.fillRect(sx, sy, sw, sw, new Color(250, 250, 250));
                    }
                    TrackIG.setColor(o);
                    TrackIG.drawRect(sx, sy, sw, sw);
                    break;
                case PointObject.DIAMOND:
                    double dx=r-size-2,
                     dy=c-size-2,
                     dw=2*size+4;
                    TrackIG.drawDiamond(dx, dy, dw, (o.getColorType()==ConstructionObject.THICK), o);
                    break;
                case PointObject.CIRCLE:
                    double cx=r-size-1,
                     cy=c-size-1,
                     cw=2*size+2;
                    if (o.getColorType()==ConstructionObject.THICK) {
                        TrackIG.fillOval(cx, cy, cw, cw, true, false, o);
                    } else {
                        TrackIG.fillOval(cx, cy, cw, cw, new Color(250, 250, 250));
                        TrackIG.setColor(o);
                        TrackIG.drawOval(cx, cy, cw, cw);
                    }
                    break;
                case PointObject.DOT:
                    if (o.getColorType()==ConstructionObject.THICK) {
                        TrackIG.fillRect(r, c, 1, 1, true, false, o);
                    } else {
                        TrackIG.fillRect(r, c, 1, 1, false, false, o);
//                        TrackIG.drawLine(r, c, r, c);
                    }
                    break;
                case PointObject.CROSS:
                    if (o.getColorType()==ConstructionObject.THICK) {
                        TrackIG.drawThickLine(r-size, c, r+size, c);
                        TrackIG.drawThickLine(r, c-size, r, c+size);
                    } else {
                        TrackIG.drawLine(r-size, c, r+size, c);
                        TrackIG.drawLine(r, c-size, r, c+size);
                    }
                    break;
                case PointObject.DCROSS:
                    double dcx=r-size-1,
                     dcy=c-size-1,
                     dcw=2*size+1;
                    TrackIG.drawDcross(dcx, dcy, dcw, (o.getColorType()==ConstructionObject.THICK), o);
                    break;
            }
            o.setIndicated(ind);
            o.setSelected(sel);
        }
    }
}
