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

// file: SetParameter.java

import java.awt.event.*;

import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Selector;
import rene.zirkel.constructors.*;
import rene.zirkel.objects.*;

/**
 * Class to choose parameters for macro definition.
 * @author Rene
 */
public class SetParameterTool extends ObjectConstructor
	implements Selector
{	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	double x=zc.x(e.getX()),y=zc.y(e.getY());
		ConstructionObject o=zc.selectWithSelector(e.getX(),e.getY(),this);
		if (o==null || o.isMainParameter()) return;
		o.setMainParameter();
		if (o.isMainParameter())
		{	o.setSelected(true);
			zc.getConstruction().addParameter(o);
			zc.repaint();
		}
		if (e.isShiftDown()) o.setSpecialParameter(true);
	}

	public boolean isAdmissible (ZirkelCanvas zc, ConstructionObject o)
	{	return (o instanceof PointObject ||
			o instanceof PrimitiveLineObject ||
			o instanceof PrimitiveCircleObject ||
			o instanceof ExpressionObject ||
			o instanceof AngleObject ||
			o instanceof AreaObject ||
			o instanceof FunctionObject ||
			o instanceof UserFunctionObject);
	}

	public void mouseMoved (MouseEvent e, ZirkelCanvas zc, boolean simple)
	{	zc.indicateWithSelector(e.getX(),e.getY(),this);
	}
	
	public void reset (ZirkelCanvas zc)
	{	super.reset(zc);
		zc.clearSelected();
		zc.getConstruction().clearParameters();
	}
	
	public void showStatus (ZirkelCanvas zc)
	{	zc.showStatus(
			Zirkel.name("message.parameters",
				"Macro Parameters: Select the Parameters!"));
	}
}
