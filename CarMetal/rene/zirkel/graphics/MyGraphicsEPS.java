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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.OutputStream;

import rene.gui.Global;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.objects.ConstructionObject;

public class MyGraphicsEPS extends MyGraphics {

    EPSGraphics G;
    static public int MaxR=1000000;
    double LineWidth=1;

    public MyGraphicsEPS(OutputStream out, int w, int h) {
        G=new EPSGraphics(out, w, h, EPSGraphics.PORTRAIT, true);
    }

    public void setLineWidth(double w) {
        LineWidth=w;
        G.setLineWidth(w);
    }

    public void clearRect(int x, int y, int w, int h, Color c) {
        G.setColor(c);
        G.fillRect(x, y, w, h);
    }

    public void setColor(Color c) {
        G.setColor(c);
    }

    public void setColor(ConstructionObject o) {
        if (o.isJobTarget()) {
            setColor(ZirkelFrame.TargetColor);
        } else if (o.selected()) {
            setColor(ZirkelFrame.SelectColor);
        } else if (o.getColorType()==ConstructionObject.THIN) {
            int i=o.getColorIndex();
            if (o.isHidden()) {
                setColor(ZirkelFrame.BrighterLightColors[i]);
            } else {
                setColor(ZirkelFrame.LightColors[i]);
            }
        } else {
            int i=o.getColorIndex();
            if (o.isHidden()) {
                setColor(ZirkelFrame.BrighterColors[i]);
            } else {
                setColor(ZirkelFrame.Colors[i]);
            }
        }
    }

    public void setFillColor(ConstructionObject o) {
        if (o.isJobTarget()) {
            setColor(ZirkelFrame.TargetColor);
        } else if (o.getColorType()!=ConstructionObject.THICK) {
            int i=o.getColorIndex();
            if (o.isHidden()) {
                setColor(ZirkelFrame.BrighterLightColors[i]);
            } else {
                setColor(ZirkelFrame.LightColors[i]);
            }
        } else {
            int i=o.getColorIndex();
            if (o.isHidden()) {
                setColor(ZirkelFrame.BrighterColors[i]);
            } else {
                setColor(ZirkelFrame.Colors[i]);
            }
        }
    }

    public void setLabelColor(ConstructionObject o) {
        if (o.labelSelected()) {
            setColor(ZirkelFrame.SelectColor);
        } else if (o.isFilled()) {
            int type=o.getColorType();
            o.setColorType(ConstructionObject.NORMAL);
            setColor(o);
            o.setColorType(type);
        } else {
            setColor(o);
        }
    }

    public void drawRect(double x, double y, double w, double h) {
        G.drawRect(x, y, w, h);
    }

    public void drawLine(double x, double y, double x1, double y1, ConstructionObject o) {
        if (o.getColorType()==ConstructionObject.THICK) {
            drawThickLine(x, y, x1, y1);
        } else if (o.getColorType()==ConstructionObject.THIN) {
            drawThinLine(x, y, x1, y1);
        } else {
            drawLine(x, y, x1, y1);
        }
    }

    public void drawLine(double x, double y, double x1, double y1) {
        G.drawLine(x, y, x1, y1);
    }

    public void drawThickLine(double c1, double r1, double c2, double r2) {
        G.setLineWidth(3*LineWidth);
        G.drawLine(c1, r1, c2, r2);
        G.setLineWidth(LineWidth);
    }

    public void drawThinLine(double c1, double r1, double c2, double r2) {
        G.setDash(4, 4);
        G.drawLine(c1, r1, c2, r2);
        G.clearDash();
    }

    public void drawArc(double x, double y, double w, double h, double a, double b) {
        G.drawArc(x, y, w, h, a, b);
    }

    public void drawThickArc(double x, double y, double w, double h, double a, double b) {
        G.setLineWidth(3*LineWidth);
        drawArc(x+1, y+1, w-2, w-2, a, b);
        G.setLineWidth(LineWidth);
    }

    public void drawThinArc(double x, double y, double w, double h, double a, double b) {
        G.setDash(4, 4);
        drawArc(x+1, y+1, w-2, w-2, a, b);
        G.clearDash();
    }

    public void drawArc(double x, double y, double w, double h, double a, double b,
            ConstructionObject o) {
        if (o.getColorType()==ConstructionObject.THICK) {
            drawThickArc(x, y, w, h, a, b);
        } else if (o.getColorType()==ConstructionObject.THIN) {
            drawThinArc(x, y, w, h, a, b);
        } else {
            drawArc(x, y, w, h, a, b);
        }
    }

    public void drawString(String s, double x, double y) {
        G.drawString(s, x, y);
    }

    public void drawOval(double x, double y, double w, double h) {
        if (w<MaxR) {
            G.drawOval(x, y, w, h);
        }
    }

    public void drawThickOval(double x, double y, double w, double h) {
        drawOval(x, y, w, h);
        drawOval(x-1, y-1, w+2, h+2);
        drawOval(x+1, y+1, w-2, h-2);
    }

    public void drawOval(double x, double y, double w, double h,
            ConstructionObject o) {
        setColor(o);
        if (o.getColorType()==ConstructionObject.THICK) {
            drawThickOval(x, y, w, h);
        } else {
            drawOval(x, y, w, h);
        }
    }

    public void fillRect(double x, double y, double w, double h,
            boolean outline, boolean transparent, ConstructionObject o) {
        setFillColor(o);
        G.fillRect(x, y, w, h);
        if (outline) {
            setColor(o);
            G.drawRect(x, y, w, h);
        }
    }

    public void fillArc(double x, double y, double w, double h, double a, double b,
            boolean outline, boolean transparent, boolean arc, ConstructionObject o) {
        setFillColor(o);
        if (arc) {
            G.fillArc(x, y, w, h, a, b);
        } else {
            G.fillChord(x, y, w, h, a, b);
        }
        if (outline) {
            setColor(o);
            G.drawArc(x, y, w, h, a, b);
        }
    }

    public void fillOval(double x, double y, double w, double h,
            boolean outline, boolean transparent, ConstructionObject o) {
        if (w>=MaxR) {
            return;
        }
        setFillColor(o);
        G.fillOval(x, y, w, h);
        if (outline) {
            setColor(o);
            G.drawOval(x, y, w, h);
        }
    }

    public void fillPolygon(double x[], double y[], int n,
            boolean outline, boolean transparent, ConstructionObject o) {
        if (o.getColorType()!=ConstructionObject.INVISIBLE) {
            setFillColor(o);
            G.fillPolygon(x, y, n);
        }
        if (outline) {
            setColor(o);
            G.drawPolygon(x, y, n);
        }
    }

    public void drawImage(Image i, int x, int y, ImageObserver o) {
    }

    public void drawImage(Image i, int x, int y, int w, int h,
            ImageObserver o) {
    }

    public void close()
            throws IOException {
        G.close();
    }
    int fsize;
    boolean flarge, fbold;
    int ffactor=Global.getParameter("ffactor", 130);

    public void setDefaultFont(int h, boolean large, boolean bold) {
        fsize=h;
        flarge=large;
        fbold=bold;
        setFont(large, bold);
    }

    public void setFont(boolean large, boolean bold) {
        int size=fsize;
        if (large) {
            size=size*ffactor/100;
        }
        if (flarge) {
            size=size*ffactor/100;
        }
        setFont(size, bold||fbold);
    }

    public void setFont(int h, boolean bold) {
        G.setFont(new Font(
                Global.getParameter("font.name", "dialog"),
                bold?Font.BOLD:Font.PLAIN,
                h));
    }

    public FontMetrics getFontMetrics() {
        return G.getFontMetrics();
    }

    public void drawImage(Image i, double x, double y, double x1, double y1,
            double x2, double y2, ImageObserver o) {
    }

    public Graphics getGraphics() {
        return null;
    }

    public int stringWidth(String s) {
        return getFontMetrics().stringWidth(s);
    }

    public int stringHeight(String s) {
        return getFontMetrics().getHeight();
    }

    public int drawStringExtended(String s, double x, double y) {
        drawString(s, x, y+getFontMetrics().getAscent());
        return getFontMetrics().getHeight();
    }

    public int stringAscent(String s) {
        return getFontMetrics().getAscent();
    }

    public void fillOval(double x, double y, double w, double h, Color WithColor) {
        try {
            G.setColor(WithColor);
            G.fillOval(x, y, w, h);
        } catch (Exception e) {
        }
    }

    public void fillRect(double x, double y, double w, double h, Color WithColor) {
        try {
            G.setColor(WithColor);
            G.fillRect(x, y, w, h);
        } catch (Exception e) {
        }
    }

    public void drawDiamond(double x, double y, double w, boolean isThick, ConstructionObject o) {
        double dw=w/2;
        double dx[]=new double[4], dy[]=new double[4];
        dx[0]=x+dw;
        dy[0]=y;
        dx[1]=x+w;
        dy[1]=y+dw;
        dx[2]=x+dw;
        dy[2]=y+w;
        dx[3]=x;
        dy[3]=y+dw;
        if (isThick) {
            G.setColor(o.getColor());
        } else {
            G.setColor(new Color(250, 250, 250));
        }
        G.fillPolygon(dx, dy, 4);
        if (!isThick) {
            G.setColor(o.getColor());
            G.drawPolygon(dx, dy, 4);
        }
    }

    public void drawDcross(double x, double y, double w, boolean isThick, ConstructionObject o) {
        double x1=x+w, y1=y+w;
        setColor(o);

        if (isThick) {
            G.setLineWidth(2*LineWidth);
        } else {
            G.setLineWidth(LineWidth);
        }
        drawLine(x, y, x1, y1);
        drawLine(x, y1, x1, y);
        G.setLineWidth(LineWidth);
    }

    public void setAntialiasing(boolean bool) {
    }

    public void drawAxisLine(double x, double y, double x1, double y1) {
        G.setLineWidth(LineWidth/2);
        drawLine(x, y, x1, y1);
        G.setLineWidth(LineWidth);
    }

    @Override
    public void fillPolygon(double[] x, double[] y, int n, ConstructionObject o) {
        if (o.isFilled()) {
            setFillColor(o);
            G.fillPolygon(x, y, n);
        }
        if (!o.isFilled()||o.indicated()||o.selected()||o.getColorType()==ConstructionObject.NORMAL) {
            setColor(o);
            G.drawPolygon(x, y, n);
        }
    }
}
