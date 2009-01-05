/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.structures;

import rene.zirkel.expression.Expression;
import rene.zirkel.objects.ConstructionObject;

/**
 *
 * @author erichake
 */
public class MagnetObj {

    private ConstructionObject obj;
//    private int ray = -1;
    private Expression rayexp=null;

//    public MagnetObj(ConstructionObject o, int i) {
//        obj = o;
//        ray = i;
//        rayexp=new Expression(""+i, o.getConstruction(), o);
//    }

    public MagnetObj(ConstructionObject o, String s) {
        obj = o;
        rayexp=new Expression(s, o.getConstruction(), o);
    }

    public void setSelected(boolean b){
        obj.setSelected(b);
    }

    public void setStrongSelected(boolean b){
        obj.setStrongSelected(b);
    }

    public boolean isInConstruction(){
        return obj.isInConstruction();
    }

    public ConstructionObject obj(){
        return obj;
    }

    public String name(){
        return obj.getName();
    }

    public String rayexp(){
        return rayexp.toString();
    }

    public void translate(){
        rayexp.translate();
    }

    public int ray(){
        int i=-1;
        try {
            i=(int) Math.round(rayexp.getValue());
        } catch (Exception ex) {
        }
        return i;
    }
}
