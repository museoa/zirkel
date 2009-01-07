/* 
 
Copyright 2006 Eric Hakenholz

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
 
 
 package eric;

import javax.swing.SwingUtilities;

import rene.zirkel.ZirkelFrame;
import rene.zirkel.construction.ChangedListener;
import rene.zirkel.construction.Construction;
import rene.zirkel.objects.ConstructionObject;

/**
 *
 * @author erichake
 */
public class JZirkelChanges implements ChangedListener {

    Construction C;
    ConstructionObject O;
    int CLength = 0;
    ZirkelFrame ZF;
    JZirkelFrame JZF;
    String APoint = ",PointObject,MidpointObject,IntersectionObject,PointonObject,PointonObjectIntersectionObject,LineCircleIntersectionObject,LineIntersectionObject,CircleIntersectionObject,";
    String AEditableObject = ",UserFunctionObject,EquationObject,";

    /** Creates a new instance of JZirkelChanges */
    public JZirkelChanges(ZirkelFrame zf, JZirkelFrame jzf) {
        ZF = zf;
        JZF = jzf;
        // Important ! every change in the construction will go
        // throught the notifyChanged method of this class :
        C = ZF.ZC.getConstruction();
        C.CL = this;
    }

    public void notifyChanged() {
        // Skip if the objects are not really added to the construction :
        
        

        if (ZF.ZC.isPreview()) {
            return;
        }
        if (C.Loading) {
            return;
        }
        
        int CRealSize = C.V.size();
        if ((CRealSize == 0) || (CLength >= CRealSize)) {
            if (CLength>CRealSize){
                JGlobals.JPB.clearme();
            }
            CLength = CRealSize;
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                C.reorderConstruction();
                ZF.ZC.reloadCD();
                ZF.ZC.recompute();
                ZF.ZC.validate();
                ZF.ZC.repaint();
            }
        });


        String[] tab = C.V.get(CRealSize - 1).getClass().getName().split("\\.");
        String typecode = "," + tab[tab.length - 1] + ",";


        // Force editing mode when the added object is a user function :
        if (JZF.JPM.isSelected("bi_function_u")) {
            if (AEditableObject.indexOf(typecode) != -1) {
                O = (ConstructionObject) C.V.get(CRealSize - 1);
                O.setFixed(false);
                JGlobals.EditObject(O);
                CLength = CRealSize;
                return;
            }
        }
        



        if ((APoint.indexOf(typecode) != -1)) {
            O = (ConstructionObject) C.V.get(CRealSize - 1);
            if ((O.isSelectable())&&(!O.isHidden()) && (!O.isSuperHidden())) {
                
//                JGlobals.EditObject(O, false,true);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                            JGlobals.EditObject(O, false,true);
                    }
                });
            }

        }
        
        
        CLength = CRealSize;

    }
    }
