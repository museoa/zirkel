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

import java.awt.Color;

import rene.gui.Global;
import rene.util.parser.StringParser;

/**
 *
 * @author erichake
 */
public class JLocalPreferences {
    JZirkelFrame JZF;
    int minfontsize,minpointsize,minlinesize,arrowsize,digits_lengths,
            digits_edit,digits_angles,colorbackgroundx,colorbackgroundy,colorbackgroundPal;
    String colorbackground;
    /** Creates a new instance of JProperties */
    public JLocalPreferences(JZirkelFrame jzf) {
        JZF=jzf;
        getLocalPreferences();
    }
    
    public void getLocalPreferences(){
        minfontsize=Global.getParameter("minfontsize",12);
        minpointsize=Global.getParameter("minpointsize",3);
        minlinesize=Global.getParameter("minlinesize",1);
        arrowsize=Global.getParameter("arrowsize",10);
        digits_lengths=Global.getParameter("digits.lengths",2);
        digits_edit=Global.getParameter("digits.edit",4);
        digits_angles=Global.getParameter("digits.angles",2);
        colorbackground=Global.getParameter("colorbackground","245,245,245");
        colorbackgroundx=Global.getParameter("colorbackgroundx",154);
        colorbackgroundy=Global.getParameter("colorbackgroundy",5);
        colorbackgroundPal=Global.getParameter("colorbackgroundPal",4);
    }
    
    public void setLocalPreferences(){
        Global.setParameter("minfontsize",minfontsize);
        Global.setParameter("minpointsize",minpointsize);
        Global.setParameter("minlinesize",minlinesize);
        Global.setParameter("arrowsize",arrowsize);
        Global.setParameter("digits.lengths",digits_lengths);
        Global.setParameter("digits.edit",digits_edit);
        Global.setParameter("digits.angles",digits_angles);
        Global.setParameter("colorbackground",getColor(colorbackground));
        Global.setParameter("colorbackgroundx",colorbackgroundx);
        Global.setParameter("colorbackgroundy",colorbackgroundy);
        Global.setParameter("colorbackgroundPal",colorbackgroundPal);
        JZF.ZF.ZC.updateDigits();
//        JZF.ZF.ZC.resetGraphics();
        JZF.ZF.ZC.paint(JZF.ZF.ZC.getGraphics());
        JZF.JPM.initCursors();
    }
    
    // get a Color object from a string like "231,145,122"
    private Color getColor(String s){
        StringParser p=new StringParser(s);
        p.replace(',',' ');
        int red,green,blue;
        red=p.parseint(); green=p.parseint(); blue=p.parseint();
        return new Color(red,green,blue);
    }
    
}
