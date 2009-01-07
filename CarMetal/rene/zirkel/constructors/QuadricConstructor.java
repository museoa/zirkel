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
 
 
 package rene.zirkel.constructors;

// file: QuadricConstructor.java

import java.awt.event.MouseEvent;

import rene.util.xml.XmlTag;
import rene.util.xml.XmlTree;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.QuadricObject;

public class QuadricConstructor extends ObjectConstructor {
    PointObject P[];
    int NPoints;
    static PointObject previewPoint=null;
    static QuadricObject previewQuadric=null;
    
    
    public static void deletePreview(ZirkelCanvas zc){
        if (previewQuadric!=null) {
            zc.delete(previewQuadric);
            previewPoint=null;
            previewQuadric=null;
            zc.reset();
        }
    }
    
    
    private void arrangeP(){
        if (NPoints<5){
            P[4]=previewPoint;
            for (int i=3;i>=NPoints;i--){
                P[i]=P[0];
            }
        }
    }
    
    public void mousePressed(MouseEvent e, ZirkelCanvas zc) {
        if (!zc.Visual) return;
        if (previewPoint==null) previewPoint=new PointObject(zc.getConstruction(),"PrevPoint");
        if (previewQuadric!=null) previewQuadric.setHidden(true);
        PointObject p=zc.selectCreatePoint(e.getX(),e.getY());
        if (previewQuadric!=null) previewQuadric.setHidden(false);
        if (p!=null) {
            P[NPoints++]=p;
            p.setSelected(true);
            arrangeP();
            if (previewQuadric==null) {
                previewQuadric=new QuadricObject(zc.getConstruction(),P);
                zc.addObject(previewQuadric);
            };
            previewQuadric.setDefaults();
            zc.repaint();
        }
        showStatus(zc);
        if (NPoints==5) {
            QuadricObject Q=new QuadricObject(zc.getConstruction(),P);
            zc.addObject(Q);
            Q.setDefaults();
            zc.clearSelected();
            deletePreview(zc);
        }
    }
    
    
    public void mouseMoved(MouseEvent e, ZirkelCanvas zc, boolean simple) {
        if (previewPoint!=null){
            previewQuadric.validate();
            previewPoint.move(zc.x(e.getX()),zc.y(e.getY()));
            zc.repaint();
        }
        super.mouseMoved(e,zc,simple);
    }
    
    
    
    
    public void showStatus(ZirkelCanvas zc) {
        zc.showStatus(Zirkel.name("message.quadric")+" "+(NPoints+1));
    }
    
    public void reset(ZirkelCanvas zc) {
        super.reset(zc);
        if (zc.Visual) {
            P=new PointObject[5]; NPoints=0;
            showStatus(zc);
        } else {
            zc.setPrompt(Zirkel.name("prompt.quadric"));
        }
    }
    
    public boolean construct(XmlTree tree, Construction c)
    throws ConstructionException {
        if (!testTree(tree,"Quadric")) return false;
        XmlTag tag=tree.getTag();
        for (int i=0; i<5; i++)
            if (!tag.hasParam("point"+(i+1)))
                throw new ConstructionException("Quadric points missing!");
        try {
            PointObject P[]=new PointObject[5];
            for (int i=0; i<5; i++)
                P[i]=(PointObject)c.find(tag.getValue("point"+(i+1)));
            QuadricObject p=new QuadricObject(c,P);
            setName(tag,p);
            set(tree,p);
            c.add(p);
            setConditionals(tree,c,p);
        } catch (ConstructionException e) {
            throw e;
        } catch (Exception e) {
            throw new ConstructionException("Quadric points illegal!");
        }
        return true;
    }
    
    public String getPrompt() {
        return Zirkel.name("prompt.quadric");
    }
    
    public String getTag() { return "Quadric"; }
    
    public void construct(Construction c,
            String name, String params[], int nparams)
            throws ConstructionException {
        if (nparams!=5)
            throw new ConstructionException(Zirkel.name("exception.nparams"));
        ConstructionObject P[]=new PointObject[5];
        for (int i=0; i<5; i++) {
            P[i]=c.find(params[i]);
            if (P[i]==null)
                throw new ConstructionException(Zirkel.name("exception.notfound")+" "+
                        params[i]);
            if (!(P[i] instanceof PointObject))
                throw new ConstructionException(Zirkel.name("exception.type")+" "+
                        params[i]);
        }
        QuadricObject s=new QuadricObject(c,(PointObject[])P);
        if (!name.equals("")) s.setNameCheck(name);
        c.add(s);
        s.setDefaults();
    }
    
}
