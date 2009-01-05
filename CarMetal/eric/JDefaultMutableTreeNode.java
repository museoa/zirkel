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

import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import rene.gui.MyMenuItem;
import rene.zirkel.ZirkelFrame;
import rene.zirkel.macro.Macro;


public class JDefaultMutableTreeNode extends DefaultMutableTreeNode{
    MyMenuItem PMmenuitem;
    JMenuItem MainMenuItem;
    ZirkelFrame ZF;
    JZirkelFrame JZF;
    String name="";
    Macro m;
    int macrotype;// 0: library macro  1 : library+file macro   2 : file macro      3 : temporary macro
    
    
    
    
    public JDefaultMutableTreeNode(String s){
        super(s);
        
        macrotype=2;


    }
    
    public JDefaultMutableTreeNode(ZirkelFrame zf,JZirkelFrame jzf,Macro mcr){
        super();

        


        ZF=zf;
        JZF=jzf;
        macrotype=(mcr.isProtected())?0:2;
        name=mcr.getName();
        m=mcr;
        String[] mytab=mcr.getName().split(("/"));
        MainMenuItem=new JMenuItem(mytab[mytab.length-1]);
        MainMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuaction();
            }
        });
        MainMenuItem.setFont(new java.awt.Font("System", 0, 13));
        PMmenuitem=new MyMenuItem(mytab[mytab.length-1]);
        PMmenuitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuaction();
                
            }
        });
        this.setUserObject(mytab[mytab.length-1]);
        
    }
    
    public void menuaction(){
        JZF.JPM.deselectgeomgroup();
        JZF.ZContent.help.setMacroHelp(m);
        ZF.runMacro(m);
    }
    
    public void runZmacro(){
        ZF.runMacro(m);
    }
    
    public void setType(int newtype){
        if (macrotype>1) macrotype=newtype;
        if ((macrotype==0)&&(newtype==2)) macrotype=1;
        if ((macrotype==1)&&(newtype==3)) macrotype=0;
        if (macrotype<2) m.setProtected(true);
    }
    
    public void removefromlib(){
        if (macrotype<2){
            macrotype=3-macrotype;
            m.setProtected(false);
        }
    }
    
    
    public void ActualisePath(){
        if (this.isLeaf()){
            TreeNode[] mypath=this.getPath();
            name="";
            for (int i=1;i<mypath.length-1;i++){
                name+=mypath[i].toString()+"/";
            }
            name+=mypath[mypath.length-1].toString();
            ZF.ZC.renameMacro(m,name);
            PMmenuitem.setLabel(mypath[mypath.length-1].toString());
            MainMenuItem.setText(mypath[mypath.length-1].toString());
        }
    }


    
    //new name est donnŽ sous forme de path dŽbutant par "root/Macros/"
//    public void setName(String newname){
//        String [] mypath=newname.split("/");
//        this.setUserObject(mypath[mypath.length-1]);
//        newname=newname.replaceFirst("root/Macros/","");
//        ZF.ZC.renameMacro(m,newname);
//        name=newname;
//        PMmenuitem.setName(mypath[mypath.length-1]);
//    }
    
    
    
//    public void rename(){
//        String[] mypath;
//
//        mypath=name.split("/");
//        mypath[mypath.length-1]=(String)this.getUserObject();
//        String newname=mypath[0];
//        for(int i=1;i<mypath.length;i++){
//            newname+="/"+mypath[i];
//        }
//        ZF.ZC.renameMacro(m,newname);
//        name=newname;
//
//        PMmenuitem.setLabel((String)this.getUserObject());
//
//    }
    
    
    
    
    
    
}
