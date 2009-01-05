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
public class JGlobalPreferences {
    static int minfontsize,minpointsize,minlinesize,arrowsize,digits_lengths,
            digits_edit,digits_angles,colorbackgroundx,colorbackgroundy,colorbackgroundPal;
    static String colorbackground;
    static boolean undockpalette;
    /** Creates a new instance of JProperties */
   
    static public void initPreferences(){
        minfontsize=Global.getParameter("prefs.minfontsize",18);
        minpointsize=Global.getParameter("prefs.minpointsize",3);
        minlinesize=Global.getParameter("prefs.minlinesize",1);
        arrowsize=Global.getParameter("prefs.arrowsize",15);
        digits_lengths=Global.getParameter("prefs.digits.lengths",5);
        digits_edit=Global.getParameter("prefs.digits.edit",5);
        digits_angles=Global.getParameter("prefs.digits.angles",0);
        colorbackground=Global.getParameter("prefs.colorbackground","245,245,245");
        colorbackgroundx=Global.getParameter("prefs.colorbackgroundx",154);
        colorbackgroundy=Global.getParameter("prefs.colorbackgroundy",5);
        colorbackgroundPal=Global.getParameter("prefs.colorbackgroundPal",4);
        undockpalette=Global.getParameter("prefs.undockpalette",false);
    }
    
    static public void savePreferences(){
        Global.setParameter("prefs.minfontsize",minfontsize);
        Global.setParameter("prefs.minpointsize",minpointsize);
        Global.setParameter("prefs.minlinesize",minlinesize);
        Global.setParameter("prefs.arrowsize",arrowsize);
        Global.setParameter("prefs.digits.lengths",digits_lengths);
        Global.setParameter("prefs.digits.edit",digits_edit);
        Global.setParameter("prefs.digits.angles",digits_angles);
        Global.setParameter("prefs.colorbackground",getColor(colorbackground));
        Global.setParameter("prefs.colorbackgroundx",colorbackgroundx);
        Global.setParameter("prefs.colorbackgroundy",colorbackgroundy);
        Global.setParameter("prefs.colorbackgroundPal",colorbackgroundPal);
        Global.setParameter("prefs.undockpalette",undockpalette);
    }
    
    static public void setLocalPreferences(){
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
    }
    
    static public void ShowPreferencesDialog(){
        JGlobalPreferencesDlog d=new JGlobalPreferencesDlog();
        
        
        
    }
    
    // get a Color object from a string like "231,145,122"
    static private Color getColor(String s){
        StringParser p=new StringParser(s);
        p.replace(',',' ');
        int red,green,blue;
        red=p.parseint(); green=p.parseint(); blue=p.parseint();
        return new Color(red,green,blue);
    }
    
}
