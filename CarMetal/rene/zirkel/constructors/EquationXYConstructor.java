/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rene.zirkel.constructors;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.EquationXYObject;

/**
 *
 * @author erichake
 */
public class EquationXYConstructor extends ObjectConstructor {

    public boolean construct(XmlTree tree, Construction c)
            throws ConstructionException {
        if (!testTree(tree, "EqXY")) {
            return false;
        }
        XmlTag tag=tree.getTag();
        if (tag.hasParam("f")) // function
        {
            EquationXYObject eq=new EquationXYObject(c, tag.getValue("f"),Integer.parseInt(tag.getValue("Dhor")));
            eq.setName(tag.getValue("name"));
            
            
            
//            eq.setDefaults();
            set(tree,eq);
            c.add(eq);
            eq.updateText();
        }
        return true;
    }
}
