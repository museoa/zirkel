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
package rene.zirkel.macro;

/**
 * This is an ObjectConstructor, which can run a macro.
 */
import eric.JLocusTrackObject;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;

import java.util.Vector;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.construction.DepList;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.*;
import rene.zirkel.objects.*;

public class MacroRunner extends ObjectConstructor
        implements Selector {

    String S[];
    int Param;
    Macro M;
    ArrayList OCs = new ArrayList();
    ArrayList PROMPTs = new ArrayList();
    ZirkelCanvas ZC;
    ConstructionObject Params[]; // array of parameters.
    boolean NewPoint[]; // are the parameters new points?
    boolean Fixed[];
    double LastX = 0, LastY = 0;
    static ConstructionObject previewPoint = null;
    static boolean keepLine = true;
    static boolean keepCircle = true;

    /**
     * Must be called, when this constructor is started.
     * @param m The macro to be run
     */
    public void setMacro(Macro m, ZirkelCanvas zc) {
        S = m.getPrompts();
        Param = 0;
        M = m;
        Params = new ConstructionObject[S.length];
        Fixed = new boolean[S.length];
        NewPoint = new boolean[S.length];
        for (int i = 0; i < S.length; i++) {
            Fixed[i] = false;
        }
    }

    /**
     * At each mouse press, one parameter is chosen. The valid objects
     * are determined by the type of the parameter stored in the macro
     * and retrieved by getParams(). Once all parameters are entered, the
     * marco is run.
     */
    public void mousePressed(MouseEvent e, ZirkelCanvas zc) {
        if (!zc.Visual) {
            return;
        }

        if (!e.isAltDown() && isMultipleFinalAccepted()) {
            ConstructionObject o = zc.selectMultipleFinal(e.getX(), e.getY(), false);
            if (o != null) {
                LaunchMultipleFinalMacro(e, zc);
                reset(zc);
                return;
            }
        }

        ConstructionObject o = null;
        ConstructionObject p[] = M.getParams();
        if (p[Param] instanceof PointObject) {
            o = zc.selectCreatePoint(e.getX(), e.getY());
        } else {
            o = zc.selectWithSelector(e.getX(), e.getY(), this);
        }
        if (o == null) {
            return;
        }
        int ip = Param;
        if (!setNextParameter(o, zc, e.isShiftDown())) {
            return;
        }
        NewPoint[ip] = (o instanceof PointObject && zc.isNewPoint());
        if (Param >= S.length) {
            doMacro(zc);
            reset(zc);
        } else {
            getFixed(zc);
        }
    }

    public boolean isAdmissible(ZirkelCanvas zc, ConstructionObject o) {
        ConstructionObject p[] = M.getParams();
        if (p[Param] instanceof PointObject) {
            return (o instanceof PointObject);
        } else if (p[Param] instanceof FixedAngleObject) {
            return (o instanceof FixedAngleObject);
        } else if (p[Param] instanceof SegmentObject) {
            return (o instanceof SegmentObject);
        } else if (p[Param] instanceof RayObject) {
            return (o instanceof RayObject);
        } else if (p[Param] instanceof TwoPointLineObject) {
            return (o instanceof TwoPointLineObject);
        } else if (p[Param] instanceof PrimitiveLineObject) {
            return (o instanceof PrimitiveLineObject);
        } else if (p[Param] instanceof PrimitiveCircleObject) {
            return (o instanceof PrimitiveCircleObject);
        } else if (p[Param] instanceof FunctionObject) {
            return (o instanceof FunctionObject);
        } else if (p[Param] instanceof UserFunctionObject) {
            return (o instanceof UserFunctionObject);
        } else if (p[Param] instanceof AngleObject) {
            return (o instanceof AngleObject);
        } else if (p[Param] instanceof QuadricObject) {
            return (o instanceof QuadricObject);
        } else if (p[Param] instanceof ExpressionObject) {
            return (o instanceof ExpressionObject ||
                    o instanceof AngleObject ||
                    o instanceof FixedAngleObject ||
                    o instanceof AreaObject);
        } else if (p[Param] instanceof AreaObject) {
            return (o instanceof AreaObject);
        } else {
            return false;
        }
    }

    private void checkIfKeepLine(ZirkelCanvas zc) {
        Construction c = zc.getConstruction();
        Params[Params.length - 1] = new PointObject(c, -3, 4);
        runMacroPreview(zc, false);
        PointObject p1 = (PointObject) previewPoint;
        Params[Params.length - 1] = new PointObject(c, -1, 1);
        runMacroPreview(zc, false);
        PointObject p2 = (PointObject) previewPoint;
        Params[Params.length - 1] = new PointObject(c, 1, -2);
        runMacroPreview(zc, false);
        PointObject p3 = (PointObject) previewPoint;

        double x1 = p2.getX() - p1.getX();
        double y1 = p2.getY() - p1.getY();
        double x2 = p3.getX() - p1.getX();
        double y2 = p3.getY() - p1.getY();
        keepLine = (Math.abs(x1 * y2 - x2 * y1) < 1e-11);
    }

    private void checkIfKeepCircle(ZirkelCanvas zc) {
        Construction c = zc.getConstruction();
        Params[Params.length - 1] = new PointObject(c, 1, 1);
        runMacroPreview(zc, false);
        PointObject p0 = (PointObject) previewPoint;

        Params[Params.length - 1] = new PointObject(c, -2, 2);
        runMacroPreview(zc, false);
        PointObject p1 = (PointObject) previewPoint;
        Params[Params.length - 1] = new PointObject(c, 2, 4);
        runMacroPreview(zc, false);
        PointObject p2 = (PointObject) previewPoint;
        Params[Params.length - 1] = new PointObject(c, 4, 2);
        runMacroPreview(zc, false);
        PointObject p3 = (PointObject) previewPoint;
        Params[Params.length - 1] = new PointObject(c, 2, -2);
        runMacroPreview(zc, false);
        PointObject p4 = (PointObject) previewPoint;

        double x1 = Math.sqrt((p1.getX() - p0.getX()) * (p1.getX() - p0.getX()) + (p1.getY() - p0.getY()) * (p1.getY() - p0.getY()));
        double x2 = Math.sqrt((p2.getX() - p0.getX()) * (p2.getX() - p0.getX()) + (p2.getY() - p0.getY()) * (p2.getY() - p0.getY()));
        double x3 = Math.sqrt((p3.getX() - p0.getX()) * (p3.getX() - p0.getX()) + (p3.getY() - p0.getY()) * (p3.getY() - p0.getY()));
        double x4 = Math.sqrt((p4.getX() - p0.getX()) * (p4.getX() - p0.getX()) + (p4.getY() - p0.getY()) * (p4.getY() - p0.getY()));

        boolean b = Math.abs(x1 - x2) < 1e-11;
        b = b && Math.abs(x1 - x3) < 1e-11;
        b = b && Math.abs(x1 - x4) < 1e-11;

        keepCircle = b;
    }

    public boolean isMultipleFinalAccepted() {
        ConstructionObject p[] = M.getParams();
        if (Param < p.length - 1) {
            return false;
        }
        if (!(p[p.length - 1] instanceof PointObject)) {
            return false;
        }
        Vector t = M.getTargets();
        if (t.size() != 1) {
            return false;
        }
        ConstructionObject o = (ConstructionObject) t.get(0);
        if (!(o instanceof PointObject)) {
            return false;
        }
        return true;
    }

    public void LaunchLocus(ZirkelCanvas zc, Construction c, ConstructionObject o) {
        ConstructionObject po[] = new ConstructionObject[0];
        int pn = 0;

        PointObject pm = new PointObject(c, 0, 0);
        Params[Params.length - 1] = pm;
        doMacro(zc);
        PointObject p = (PointObject) previewPoint;

        JLocusTrackObject jl = new JLocusTrackObject(c, p, po, pn, o, pm);

        pm.setKeep(false); // necessary for jobs
        pm.setTarget(false); // necessary for descriptive constructions
        pm.setBound(o);
        pm.setSuperHidden(true);
        c.addNoCheck(pm);
        o.setTranslation(pm);
        pm.validate();
        c.added(pm);

        p.setKeep(false); // necessary for jobs
        p.setTarget(false); // necessary for descriptive constructions
        p.setSuperHidden(true);
        c.addNoCheck(p);
        o.setTranslation(p);
        p.validate();
        c.added(p);

        jl.setKeep(false); // necessary for jobs
        jl.setTarget(false); // necessary for descriptive constructions
        jl.setDefaults();
        c.addNoCheck(jl);
        o.setTranslation(jl);
        jl.validate();
        c.added(jl);
    }

    public void LaunchLocusPreview(ZirkelCanvas zc, Construction c, ConstructionObject o) {
        ConstructionObject po[] = new ConstructionObject[0];
        int pn = 0;

        PointObject pm = new PointObject(c, 0, 0);
        Params[Params.length - 1] = pm;
        runMacroPreview(zc, false);
        PointObject p = (PointObject) previewPoint;

        JLocusTrackObject jl = new JLocusTrackObject(c, p, po, pn, o, pm);

        jl.setKeep(false); // necessary for jobs
        jl.setTarget(false); // necessary for descriptive constructions
        jl.setSelectable(false);
        jl.setIndicated(true);
        c.addNoCheck(jl);
        o.setTranslation(jl);
        jl.validate();
        c.added(jl);
    }

    public boolean isLineObject(ConstructionObject o) {
        boolean b = o instanceof PrimitiveLineObject;
        b = b || (o instanceof AreaObject);
        return b;
    }

    public boolean isCircleObject(ConstructionObject o) {
        boolean b = o instanceof PrimitiveCircleObject;
        return b;
    }

    public boolean isArcObject(ConstructionObject c) {
        if (c instanceof PrimitiveCircleObject) {
            PrimitiveCircleObject cc = (PrimitiveCircleObject) c;
            return cc.hasRange();
        }
        return false;
    }

    public void LaunchMultipleFinalMacro(MouseEvent e, ZirkelCanvas zc) {
        ConstructionObject o = zc.selectMultipleFinal(e.getX(), e.getY(), false);
        Construction c = zc.getConstruction();
        if (o != null) {
            int myX = e.getX();
            int myY = e.getY();
            LastX = zc.x(myX);
            LastY = zc.y(myY);

            if ((!keepLine) && (isLineObject(o))) {
                LaunchLocus(zc, c, o);
                return;
            }
            if ((!keepCircle) && (isCircleObject(o))) {
                LaunchLocus(zc, c, o);
                return;
            }
            if (isArcObject(o)) {
                LaunchLocus(zc, c, o);
                return;
            }

            if (o instanceof TwoPointLineObject) {
                TwoPointLineObject tplo = (TwoPointLineObject) o;
                TwoPointLineObject oc = (TwoPointLineObject) tplo.copy(LastX, LastY);
                Params[Params.length - 1] = tplo.getP1();
                doMacro(zc);
                PointObject p1 = (PointObject) previewPoint;
                Params[Params.length - 1] = tplo.getP2();
                doMacro(zc);
                PointObject p2 = (PointObject) previewPoint;
                oc.setP1P2(p1, p2);
                finaliseMacro(c, o, oc);
            } else if (o instanceof PrimitiveLineObject) {
                PrimitiveLineObject tplo = (PrimitiveLineObject) o;
                Params[Params.length - 1] = tplo.getP1();
                doMacro(zc);
                PointObject p1 = (PointObject) previewPoint;
                PointObject p3 = tplo.getP1();
                String Pnm = p3.getName();
                String Lnm = tplo.getName();
                PointObject p4 = new PointObject(c, p3.getX() + tplo.getDX(), p3.getY() + tplo.getDY());
                p4.setFixed("x(" + Pnm + ")+x(" + Lnm + ")", "y(" + Pnm + ")+y(" + Lnm + ")");
                p4.setSuperHidden(true);
                zc.addObject(p4);
                Params[Params.length - 1] = p4;
                doMacro(zc);
                PointObject p2 = (PointObject) previewPoint;
                p1.setSuperHidden(true);
                p2.setSuperHidden(true);
                LineObject oc = new LineObject(c, p1, p2);
                finaliseMacro(c, o, oc);
            } else if (o instanceof CircleObject) {
                CircleObject tplo = (CircleObject) o;
                CircleObject oc = (CircleObject) tplo.copy(LastX, LastY);
                Params[Params.length - 1] = tplo.getP1();
                doMacro(zc);
                PointObject p1 = (PointObject) previewPoint;
                Params[Params.length - 1] = tplo.getP2();
                doMacro(zc);
                PointObject p2 = (PointObject) previewPoint;
                p2.setSuperHidden(true);
                oc.setP1P2(p1, p2);
                finaliseMacro(c, o, oc);
            } else if (o instanceof PrimitiveCircleObject) {
                PrimitiveCircleObject tplo = (PrimitiveCircleObject) o;
                Params[Params.length - 1] = tplo.getP1();
                doMacro(zc);
                PointObject p1 = (PointObject) previewPoint;
                PointObject p4 = new PointObject(c, 0, 0);
                p4.setBound(tplo);
                p4.setSuperHidden(true);
                zc.addObject(p4);
                Params[Params.length - 1] = p4;
                doMacro(zc);
                PointObject p2 = (PointObject) previewPoint;
                p2.setSuperHidden(true);
                CircleObject oc = new CircleObject(c, p1, p2);
                finaliseMacro(c, o, oc);
            } else if (o instanceof AreaObject) {
                AreaObject ao = (AreaObject) o;
                AreaObject oc = (AreaObject) ao.copy(LastX, LastY);
                oc.V.removeAllElements();
                Enumeration en = ao.V.elements();
                while (en.hasMoreElements()) {
                    PointObject pt = (PointObject) en.nextElement();
                    Params[Param] = pt;
                    doMacro(zc);
                    oc.V.add((PointObject) previewPoint);
                }
                finaliseMacro(c, o, oc);
            } else {
                LaunchLocus(zc, c, o);
            }
        }
    }

    public void finaliseMacro(Construction c, ConstructionObject o, ConstructionObject oc) {
        oc.setKeep(false); // necessary for jobs
        oc.setTarget(false); // necessary for descriptive constructions
        oc.setDefaults();
        c.addNoCheck(oc);
        o.setTranslation(oc);
        oc.validate();
        c.added(oc);
    }

    public void LaunchMultipleFinalMacroPreview(MouseEvent e, ZirkelCanvas zc) {
        ConstructionObject o = zc.selectMultipleFinal(e.getX(), e.getY(), false);
        Construction c = zc.getConstruction();
        if (o != null) {
            int myX = e.getX();
            int myY = e.getY();
            zc.prepareForPreview(e);
            checkIfKeepLine(zc);
            checkIfKeepCircle(zc);
            LastX = zc.x(myX);
            LastY = zc.y(myY);
            if ((!keepLine) && (isLineObject(o))) {
                LaunchLocusPreview(zc, c, o);
                return;
            }
            if ((!keepCircle) && (isCircleObject(o))) {
                LaunchLocusPreview(zc, c, o);
                return;
            }
            if (isArcObject(o)) {
                LaunchLocusPreview(zc, c, o);
                return;
            }


            if (o instanceof TwoPointLineObject) {
                TwoPointLineObject tplo = (TwoPointLineObject) o;
                TwoPointLineObject oc = (TwoPointLineObject) tplo.copy(LastX, LastY);
                Params[Params.length - 1] = tplo.getP1();
                runMacroPreview(zc, true);
                PointObject p1 = (PointObject) previewPoint;
                Params[Params.length - 1] = tplo.getP2();
                runMacroPreview(zc, true);
                PointObject p2 = (PointObject) previewPoint;
                oc.setP1P2(p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof PrimitiveLineObject) {
                PrimitiveLineObject tplo = (PrimitiveLineObject) o;
                Params[Params.length - 1] = tplo.getP1();
                runMacroPreview(zc, false);
                PointObject p1 = (PointObject) previewPoint;
                PointObject p0 = tplo.getP1();
                Params[Params.length - 1] = new PointObject(c, p0.getX() + tplo.getDX(), p0.getY() + tplo.getDY());
                runMacroPreview(zc, false);
                PointObject p2 = (PointObject) previewPoint;
                LineObject oc = new LineObject(c, p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof CircleObject) {
                CircleObject tplo = (CircleObject) o;
                CircleObject oc = (CircleObject) tplo.copy(LastX, LastY);
                Params[Params.length - 1] = tplo.getP1();
                runMacroPreview(zc, true);
                PointObject p1 = (PointObject) previewPoint;
                Params[Params.length - 1] = tplo.getP2();
                runMacroPreview(zc, false);
                PointObject p2 = (PointObject) previewPoint;
                oc.setP1P2(p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof PrimitiveCircleObject) {
                PrimitiveCircleObject tplo = (PrimitiveCircleObject) o;
                Params[Params.length - 1] = tplo.getP1();
                runMacroPreview(zc, true);
                PointObject p1 = (PointObject) previewPoint;
                PointObject p4 = new PointObject(c, tplo.getP1().getX() + tplo.getR(), tplo.getP1().getY());
                Params[Params.length - 1] = p4;
                runMacroPreview(zc, false);
                PointObject p2 = (PointObject) previewPoint;
                CircleObject oc = new CircleObject(c, p1, p2);
                finaliseMacroPreview(c, o, oc);
            } else if (o instanceof AreaObject) {
                AreaObject ao = (AreaObject) o;
                AreaObject oc = (AreaObject) ao.copy(LastX, LastY);
                oc.V.removeAllElements();
                Enumeration en = ao.V.elements();
                while (en.hasMoreElements()) {
                    PointObject pt = (PointObject) en.nextElement();
                    Params[Params.length - 1] = pt;
                    runMacroPreview(zc, true);
                    oc.V.add((PointObject) previewPoint);
                }
                finaliseMacroPreview(c, o, oc);
            } else {
                LaunchLocusPreview(zc, c, o);
            }
        }
    }

    public void finaliseMacroPreview(Construction c, ConstructionObject o, ConstructionObject oc) {
        oc.setKeep(false); // necessary for jobs
        oc.setTarget(false); // necessary for descriptive constructions
        oc.setSelectable(false);
        oc.setIndicated(true);
        c.addNoCheck(oc);
        o.setTranslation(oc);
        oc.validate();
        c.added(oc);
    }

    public void mouseMoved(MouseEvent e, ZirkelCanvas zc, boolean simple) {
        ConstructionObject p[] = M.getParams();
        zc.clearIndicated();
        zc.clearPreview();
        zc.repaint();
        if (!e.isAltDown() && isMultipleFinalAccepted()) {

            ConstructionObject o = zc.selectMultipleFinal(e.getX(), e.getY(), false);
            if (o != null) {

                int myX = e.getX();
                int myY = e.getY();
                zc.prepareForPreview(e);
                LastX = zc.x(myX);
                LastY = zc.y(myY);

                LaunchMultipleFinalMacroPreview(e, zc);
                zc.indicateMultipleFinal(e.getX(), e.getY());
                return;
            }
        }

        if (!(p[Param] instanceof PointObject) && Param == p.length - 1) {
//            zc.clearPreview();
//            zc.repaint();
            ConstructionObject o = zc.selectWithSelector(e.getX(), e.getY(), this, false);
            if (o != null) {
                int myX = e.getX();
                int myY = e.getY();
                zc.prepareForPreview(e);
                Params[Param] = o;
                LastX = zc.x(myX);
                LastY = zc.y(myY);
                runMacroPreview(zc, true);

                zc.indicateWithSelector(myX, myY, this);
                return;
            }
        }

        if (p[Param] instanceof PointObject) {
            zc.indicateCreatePoint(e.getX(), e.getY(), true);
        } else {
            zc.indicateWithSelector(e.getX(), e.getY(), this);
        }

        if (!simple && waitForLastPoint()) {
            if (zc.isPreview()) {
                zc.movePreview(e);
            } else {
                zc.prepareForPreview(e);
                finishConstruction(e, zc);
                return;
            }
        }


    }

    public boolean waitForLastPoint() {
        if (M.countPrompts() > 0) {
            return false;
        }
        ConstructionObject p[] = M.getParams();
        return (p[Param] instanceof PointObject) && Param == p.length - 1;
    }

    public void finishConstruction(MouseEvent e, ZirkelCanvas zc) {
        ConstructionObject p[] = M.getParams();
        ConstructionObject o;
        if (p[Param] instanceof PointObject) {
            o = zc.selectCreatePoint(e.getX(), e.getY());
        } else {
            return;
        }
        NewPoint[Param] = true;
        Params[Param] = o;
        runMacroPreview(zc, true);
    }

    public void reset(ZirkelCanvas zc) {
        if (zc.Visual) {
            super.reset(zc);
            Param = 0;
            if (M != null && M.hasFixed()) {
                getFixed(zc);
            }
            showStatus(zc);
        } else if (M != null) // show the input pattern
        {
            StringBuffer b = new StringBuffer();
            b.append('=');
            String name = M.getName();
            if (name.indexOf("(") > 0) {
                b.append("\"" + M.getName() + "\"");
            } else {
                b.append(M.getName());
            }
            b.append('(');
            for (int i = 0; i < M.getParams().length - 1; i++) {
                b.append(',');
            }
            b.append(')');
            zc.setPrompt(b.toString());
        }
    }

    public void getFixed(ZirkelCanvas zc) {
        if (M == null || !zc.Visual) {
            return;
        }
        boolean start = (Param == 0);
        while ((M.isFixed(Param) || M.getPrompts()[Param].startsWith("=")) && Param < (start ? S.length - 1 : S.length)) {
            String name;
            if (M.isFixed(Param)) {
                name = M.getLast(Param);
            } else {
                name = M.getPrompts()[Param].substring(1);
            }
            if (name.equals("")) {
                M.setFixed(Param, false);
                break;
            }
            ConstructionObject o = zc.getConstruction().find(name);
            if (o == null) {
                M.setFixed(Param, false);
                break;
            }
            if (!setNextParameter(o, zc, false)) {
                return;
            }
            if (Param >= S.length) {
                doMacro(zc);
                reset(zc);
                break;
            }
        }
        showStatus(zc);
    }

    public void returnPressed(ZirkelCanvas zc) {
        if (M == null || !zc.Visual) {
            return;
        }
        String name = M.getLast(Param);
        if (name.equals("")) {
            return;
        }
        ConstructionObject o = zc.getConstruction().find(name);
        if (!setNextParameter(o, zc, false)) {
            return;
        }
        if (Param >= S.length) {
            doMacro(zc);
            reset(zc);
        } else {
            getFixed(zc);
        }
    }

    public boolean setNextParameter(ConstructionObject o,
            ZirkelCanvas zc, boolean fix) {
        if (!isAdmissible(zc, o)) {
            return false;
        }
        Params[Param] = o;
        o.setSelected(true);
        if (fix) {
            Fixed[Param] = true;
        }
        zc.getConstruction().addParameter(o);
        zc.repaint();
        Param++;
        return true;
    }

    public void doMacro(ZirkelCanvas zc) {
        String value[] = new String[0];
        runMacro(zc, value);
    }
    static DepList DL = new DepList();

    public void showStatus(ZirkelCanvas zc) {
        if (M != null) {
            ConstructionObject p[] = M.getParams();
            String type = "???";
            // Determine the expected type and display in status line
            if (p[Param] instanceof PointObject) {
                type = Zirkel.name("name.Point");
            } else if (p[Param] instanceof FixedAngleObject) {
                type = Zirkel.name("name.FixedAngle");
            } else if (p[Param] instanceof SegmentObject) {
                type = Zirkel.name("name.Segment");
            } else if (p[Param] instanceof LineObject) {
                type = Zirkel.name("name.TwoPointLine");
            } else if (p[Param] instanceof RayObject) {
                type = Zirkel.name("name.Ray");
            } else if (p[Param] instanceof PrimitiveLineObject) {
                type = Zirkel.name("name.Line");
            } else if (p[Param] instanceof PrimitiveCircleObject) {
                type = Zirkel.name("name.Circle");
            } else if (p[Param] instanceof ExpressionObject) {
                type = Zirkel.name("name.Expression");
            } else if (p[Param] instanceof AreaObject) {
                type = Zirkel.name("name.Polygon");
            } else if (p[Param] instanceof AngleObject) {
                type = Zirkel.name("name.Angle");
            }
            String s = M.getLast(Param);
            String prompt;
            if (s.equals("")) {
                prompt = ConstructionObject.text4(
                        Zirkel.name("message.runmacro"),
                        M.getName(), "" + (Param + 1), type, S[Param]);
            } else {
                prompt = ConstructionObject.text4(
                        Zirkel.name("message.runmacro"),
                        M.getName(), "" + (Param + 1), type, S[Param]) + " " +
                        ConstructionObject.text1(
                        Zirkel.name("message.runmacro.return"), M.getLast(Param));
            }
            zc.showStatus(prompt);
        }
    }

    /**
     * Run a macro. The macro parameters have already been determined.
     * This is a rather complicated function.
     */
    public void runMacro(ZirkelCanvas zc, Construction c, String value[]) {

        OCs.clear();
        PROMPTs.clear();

        M.setTranslation(c);
        ConstructionObject LastBefore = c.last();
        int N = Params.length;
        // First clear all parameter flags. This makes it possible to
        // check for proper translation of secondary parameters later.
        // Secondary parameters without a translation will be
        // constructed.
        Enumeration e = M.elements();
        while (e.hasMoreElements()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            o.clearParameter();
            o.setTranslation(null);
        }
        M.clearTranslations();
        c.clearTranslators();
        ConstructionObject p[] = M.getParams();
        // For all macro paramters, determine the translation to the
        // real construction, and do the same for the secondary
        // parameters, which belong to the parameter. The secondary
        // parameters are stored in the macro at its definition, as
        // the primary ones. Also the parameters in the macros are marked
        // as such to make sure and prevent construction.
        M.initLast(); // Macros remember the parameters for next call
        for (int i = 0; i < N; i++) {
            M.setLast(Params[i].getName(), i);
            p[i].setTranslation(Params[i]);
            p[i].setMainParameter();
            if (NewPoint[i] && p[i].isHidden()) {
                Params[i].setHidden(true);
            }
            if (Params[i] instanceof PointObject && p[i] instanceof PointObject && NewPoint[i]) {
                PointObject pa = (PointObject) Params[i], pp = (PointObject) p[i];
                pa.setIncrement(pp.getIncrement());
                if (pp.getBound() != null) {
                    pa.setBound(pp.getBound());
                    pa.setInside(pp.isInside());
                    pa.translate();
                }
            }
            // translate parameters that depend on themself only
            if (Params[i] instanceof PointObject && p[i] instanceof PointObject && ((PointObject) p[i]).dependsOnItselfOnly()) {
                PointObject P = (PointObject) Params[i];
                // Do not transfer self reference to objects that depend on something!
                // This might crash the construction.
                if (!P.depending().hasMoreElements()) {
                    P.setConstruction(M);
                    P.setFixed(((PointObject) p[i]).getEX(), ((PointObject) p[i]).getEY());
                    P.translateConditionals();
                    P.translate();
                    P.setConstruction(c);
                }
            }
            if (p[i].isMainParameter()) {	// System.out.println("Main Parameter "+p[i].getName());
                e = p[i].secondaryParams();
                // Copy the list of secondary parameters in the macro,
                // which depend from p[i] to DL.
                DL.reset();
                while (e.hasMoreElements()) {
                    ConstructionObject o = (ConstructionObject) e.nextElement();
                    // System.out.println("Secondary Parameter "+o.getName()+" of "+p[i].getName());
                    DL.add(o);
                    o.setParameter();
                }
                e = DL.elements();
                // Get a list of actual secondary params in the
                // construction. Then translate the scecondary params
                // in the macro definition to the true construction objects.
                Enumeration ep = Params[i].secondaryParams();
                while (ep.hasMoreElements() && e.hasMoreElements()) {
                    ConstructionObject o =
                            (ConstructionObject) e.nextElement();
                    ConstructionObject op =
                            (ConstructionObject) ep.nextElement();
                    if (o.getTranslation() != op && o.getTranslation() != null) {
                        zc.warning(Zirkel.name("macro.usage"));
                        return;
                    }
                    o.setTranslation(op);
                }
            }
        }
        // Now we generate the objects.
        e = M.elements();
        while (e.hasMoreElements()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            // System.out.println(o.getName()+" "+o.isParameter());
            if (!o.isParameter()) // else do not construct!
            {	// Copy the object and add to construction. Then
                // translate the dependencies properly


                ConstructionObject oc = (ConstructionObject) o.copy(LastX, LastY);
                previewPoint = oc;
                oc.setKeep(false); // necessary for jobs
                oc.setTarget(false); // necessary for descriptive constructions
                c.addNoCheck(oc);
                o.setTranslation(oc);
                oc.validate();
                c.added(oc);
                // For the target objects, use default values instead
                // of values stored in the macro (point style etc.)
                if (o.isTarget()) {
                    oc.setTargetDefaults();
                }
//                else if (o.isHidden()) {
//                    if (o.isSuperHidden()) oc.setSuperHidden(true);
//                    else oc.setHidden(true);
//                }
                // For black objects, use the default color.
                if (oc.getColorIndex() == 0) {
                    oc.setColor(c.DefaultColor);
                // Handle objects to prompt for:
                }
                if ((oc instanceof FixedCircleObject ||
                        oc instanceof FixedAngleObject ||
                        oc instanceof ExpressionObject) &&
                        M.promptFor(o.getName())) {
                    c.updateCircleDep();
                    c.dovalidate();
                    zc.repaint();
                    int index = M.getPromptFor(o.getName());
                    String v = "";
                    if (index >= value.length || value[index].equals("")) {
                        OCs.add(oc);
                        PROMPTs.add(M.getPromptName(o.getName()));
                    }
                }
            }
        }
        // Now fix the objects, which depend on later objects
        e = M.elements();
        while (e.hasMoreElements()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            if (!o.isParameter()) {
                o.laterTranslate(M);
            }
        }
        c.updateCircleDep();
        c.runTranslators(M);
        c.dovalidate();
        zc.repaint();
        int fixed = 0;
        for (int i = 0; i < Fixed.length; i++) {
            if (Fixed[i]) {
                fixed++;
            }
        }
        if (fixed > 0 && fixed < Fixed.length && !M.hasFixed()) {
            String name = M.getName() + " -";
            for (int i = 0; i < Fixed.length; i++) {
                if (Fixed[i]) {
                    name = name + " " + M.LastParams[i];
                }
            }
            M = zc.copyMacro(M, name, Fixed);
            for (int i = 0; i < Fixed.length; i++) {
                Fixed[i] = false;
            }
            reset(zc);
        }
        if (LastBefore != null && M.hideDuplicates()) {
            zc.hideDuplicates(LastBefore);
        }
        ZC = zc;
        Thread thread = new Thread(new Runnable() {

            public void run() {
                for (int i = 0; i < OCs.size(); i++) {
                    String myName = (String) PROMPTs.get(i);
                    ConstructionObject myCO = (ConstructionObject) OCs.get(i);
                    eric.JMacroPrompt mp = new eric.JMacroPrompt(ZC.getFrame(), ZC,
                            myName, myCO);
                }
            }
            ;
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void runMacro(ZirkelCanvas zc, String value[]) {
        runMacro(zc, zc.getConstruction(), value);
    }

    public void runMacroPreview(ZirkelCanvas zc, boolean visible) {
        Construction c = zc.getConstruction();
        M.setTranslation(c);
        int N = Params.length;
        // First clear all parameter flags. This makes it possible to
        // check for proper translation of secondary parameters later.
        // Secondary parameters without a translation will be
        // constructed.
        Enumeration e = M.elements();
        while (e.hasMoreElements()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            o.clearParameter();
            o.setTranslation(null);
        }
        M.clearTranslations();
        c.clearTranslators();
        ConstructionObject p[] = M.getParams();
        // For all macro paramters, determine the translation to the
        // real construction, and do the same for the secondary
        // parameters, which belong to the parameter. The secondary
        // parameters are stored in the macro at its definition, as
        // the primary ones. Also the parameters in the macros are marked
        // as such to make sure and prevent construction.
        for (int i = 0; i < N; i++) {
            M.setLast(Params[i].getName(), i);
            p[i].setTranslation(Params[i]);
            p[i].setMainParameter();
            if (NewPoint[i] && p[i].isHidden()) {
                Params[i].setHidden(true);
            }
            if (p[i].isMainParameter()) {
                e = p[i].secondaryParams();
                // Copy the list of secondary parameters in the macro,
                // which depend from p[i] to DL.
                DL.reset();
                while (e.hasMoreElements()) {
                    ConstructionObject o = (ConstructionObject) e.nextElement();
                    DL.add(o);
                    o.setParameter();
                }
                e = DL.elements();
                // Get a list of actual secondary params in the
                // construction. Then translate the scecondary params
                // in the macro definition to the true construction objects.
                Enumeration ep = Params[i].secondaryParams();
                while (ep.hasMoreElements() && e.hasMoreElements()) {
                    ConstructionObject o =
                            (ConstructionObject) e.nextElement();
                    ConstructionObject op =
                            (ConstructionObject) ep.nextElement();
                    if (o.getTranslation() != op && o.getTranslation() != null) {	//zc.warning(Zirkel.name("macro.usage"));
                        return;
                    }
                    if (o != null) {
                        o.setTranslation(op);
                    }
                }
            }
        }
        // Now we generate the objects.
        e = M.elements();
        while (e.hasMoreElements()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            if (!o.isParameter()) // else do not construct!
            {	// Copy the object and add to construction. Then
                // translate the dependencies properly
                ConstructionObject oc = (ConstructionObject) o.copy(LastX, LastY);
                previewPoint = oc;
                oc.setKeep(false); // necessary for jobs
                oc.setTarget(false); // necessary for descriptive constructions
                oc.setSelectable(false);
                if (visible) {
                    oc.setIndicated(true);
                } else {
                    oc.setSuperHidden(true);
                }
                c.addNoCheck(oc);
                o.setTranslation(oc);
                oc.validate();
                c.added(oc);
                // For the target objects, use default values instead
                // of values stored in the macro (point style etc.)
                if (o.isTarget()) {
                    oc.setTargetDefaults();
                }
                if (o.isHidden()) {
                    oc.setHidden(true);
                // For black objects, use the default color.
                }
                if (oc.getColorIndex() == 0) {
                    oc.setColor(c.DefaultColor);
                }
            }
        }
        // All objects have the chance to translate anything
        // (used by start and end points of arcs)
        e = M.elements();
        while (e.hasMoreElements()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            if (!o.isParameter()) {
                o.laterTranslate(M);
            }
        }
        c.updateCircleDep();
        // Run the translations of forward references of type @...
        c.runTranslators(M);
        c.dovalidate();
//        c.computeHeavyObjects(zc);
        zc.repaint();
    }

    /**
     * Run a macro in nonvisual mode. This needs a previous setMacro()
     * call!
     */
    public void run(ZirkelCanvas zc, Construction c,
            String name, String params[], int nparams)
            throws ConstructionException {
        ConstructionObject p[] = M.getParams();
        if (nparams != p.length + M.countPrompts()) {
            throw new ConstructionException(Zirkel.name("exception.nparams"));
        }
        String value[] = new String[M.countPrompts()];
        for (int i = 0; i < M.countPrompts(); i++) {
            value[i] = params[p.length + i];
        }
        for (int i = 0; i < p.length; i++) {
            ConstructionObject o = c.find(params[i]);
            if (o == null) {
                throw new ConstructionException(
                        Zirkel.name("exception.notfound"));
            }
            if (p[Param] instanceof PointObject) {
                if (!(o instanceof PointObject)) {
                    throw new ConstructionException(
                            Zirkel.name("exception.type"));
                } else if (p[Param] instanceof SegmentObject) {
                    if (!(o instanceof SegmentObject)) {
                        throw new ConstructionException(
                                Zirkel.name("exception.type"));
                    } else if (p[Param] instanceof LineObject) {
                        if (!(o instanceof LineObject)) {
                            throw new ConstructionException(
                                    Zirkel.name("exception.type"));
                        } else if (p[Param] instanceof RayObject) {
                            if (!(o instanceof RayObject)) {
                                throw new ConstructionException(
                                        Zirkel.name("exception.type"));
                            } else if (p[Param] instanceof PrimitiveLineObject) {
                                if (!(o instanceof PrimitiveLineObject)) {
                                    throw new ConstructionException(
                                            Zirkel.name("exception.type"));
                                } else if (p[Param] instanceof PrimitiveCircleObject) {
                                    if (!(o instanceof PrimitiveCircleObject)) {
                                        throw new ConstructionException(
                                                Zirkel.name("exception.type"));
                                    } else {
                                        throw new ConstructionException(
                                                Zirkel.name("exception.type"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Params[i] = o;
        }
        runMacro(zc, c, value);
        StringTokenizer t = new StringTokenizer(name, ",");
        Enumeration e = M.getTargets().elements();
        while (e.hasMoreElements() && t.hasMoreTokens()) {
            ConstructionObject o = (ConstructionObject) e.nextElement();
            o.getTranslation().setName(t.nextToken().trim());
        }
        zc.repaint();
    }
}
