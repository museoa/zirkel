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

import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import rene.gui.Global;


public class JPaletteManager {
    private rene.zirkel.ZirkelFrame ZF;
    JZirkelFrame MW;
    public JPalette MainPalette;
    JLabel AspectLabel;
    public JIcon propertiesicon=null;
    public JIcon ctrlJSlider,ctrlJPopup,ctrlJCheckBox,ctrlJTextField,ctrlJButton;
//    public JLabel PointNameLabel;
    Vector JPs=new Vector();
    Vector AllIcons=new Vector();
    Vector geom=new Vector();
    Vector gridaspect=new Vector();
    Vector gridaspect2=new Vector();
    Vector point=new Vector();
    Vector aspect1=new Vector();
    Vector aspect2=new Vector();
    Vector photos=new Vector();
    public int paletteiconsize;
    public rene.zirkel.dialogs.Replay Dreplay;
    
    JPaletteZone JPDisk,JPEdit,JP3D,JPGeom,JPfunc,JPTest,JPAspect,JPControls,JPHist,JPSizes,JPColors,JPPrec,JPGrid;
    private String PalettesOrder[]={"JPDisk","JPEdit","JP3D","JPGeom","JPPoint","JPAspect","JPfunc","JPTest","JPControls","JPGrid","JPHist","JPColors","JPSizes","JPPrec"};
    
    /** Creates a new instance of JPaletteManager */
    public JPaletteManager(rene.zirkel.ZirkelFrame zf,JZirkelFrame mywin,int iconsize) {
        ZF=zf;
        MW=mywin;
        paletteiconsize=iconsize;
        MW.Pwidth=iconsize*6+2;
        
        Dreplay=new rene.zirkel.dialogs.Replay(ZF,ZF.ZC);
        MainPalette=new JPalette(ZF,MW,true);
        JPDisk=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.file"),"JPDisk");
        JPEdit=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.edit"),"JPEdit");
        JP3D=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.3D"),"JP3D");
        JPGeom=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.construction"),"JPGeom");
        JPfunc=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.function"),"JPfunc");
        JPTest=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.test"),"JPTest");
        JPAspect=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.aspect"),"JPAspect");
        JPControls=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.controls"),"JPControls");
        JPHist=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.history"),"JPHist");
        JPColors=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.colors"),"JPColors");
        JPSizes=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.sizes"),"JPSizes");
        JPPrec=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.prec"),"JPPrec");
        JPGrid=new JPaletteZone(ZF,this,MainPalette,MW.Strs.getString("palette.grid"),"JPGrid");
        
        String ic1[]={"new","load","save","copy","exportpng","exporteps"};
        String ic6[]={"move","@@rename","edit","@@zoom","animate"};
        
        String ic18[]={"bi_3Dcoords","boundedpoint","bi_3Darete","bi_3Dtetra","bi_3Dcube","bi_3Ddode"};
        String ic2[]={"point","intersection","midpoint","bi_syma","bi_symc","bi_trans","line","ray","parallel","plumb","bi_med","bi_biss","segment","fixedsegment","vector","area","angle","fixedangle","circle","fixedcircle","circle3","bi_circ","bi_arc","quadric"};
        
        String ic8[]={"text","expression","image3"};
        String ic3[]={"type0","type1","type2","type3","type4","type5"};
        String ic4[]={"thickness0","thickness1","thickness2"};
        String ic5[]={"color0","color1","color2","color3","color4","color5"};
        String ic9[]={"allback","fastback","oneback","oneforward","fastforward","allforward","setbreak","nextbreak"};
        String ic10[]={"delete"};
        String ic11[]={"hide"};
//        String ic11b[]={"text","expression","image3"};
        String ic12[]={"imstretch","imcenter","imtile"};
        String ic13[]={"acolor0","acolor1","acolor2","acolor3","acolor4","acolor5"};
        String ic14[]={"athickness0","athickness1","athickness2"};
//        String ic15[]={"objecttracker","tracker","locus"};
//        String ic15[]={};
        String ic16[]={"bi_t_align","bi_t_para","bi_t_perp","bi_t_equi","bi_t_app","bi_t_conf"};
//        String ic17[]={,};
        String ic19[]={"ctrl_edit"};
        
    
        String ic21[]={"tracker","objecttracker","locus"};
        
        JPDisk.addIcons(ic1,null);
        JPDisk.addSimpleIcon("newmacro");
        JPDisk.addSimpleIcon("loadmacros");
        
        MW.ZContent.macros.SetJIcon(JPDisk.addToggleIcon("manage_macros"));
        MW.ZContent.history.SetJIcon(JPDisk.addToggleIcon("history_panel"));
        MW.ZContent.help.SetJIcon(JPDisk.addToggleIcon("help_panel"));
        
        
        
        JPEdit.addIcons(ic6,geom);
        JP3D.addIcons(ic18,geom);
        JPGeom.addIcons(ic2,geom);
        
        JPGeom.addIcons(ic8,geom);
        
        JPfunc.addIcon("bi_function_u", geom);
        JPfunc.addSimpleIcon("function");
        JPfunc.addSimpleIcon("equationxy");
        JPfunc.addIcons(ic21,geom);
        
        JPTest.addIcons(ic16,geom);
        
        JPGrid.addIcons(ic13,gridaspect);
        JPGrid.addToggleIcon("dottedgrid");
        JPGrid.addToggleIcon("numgrid");
        JPGrid.addSimpleIcon("blank");
        JPGrid.addIcons(ic14,gridaspect2);
        
//        JPAspect.setLabel("");
        JPAspect.addLabel(Loc("pointshape"));
        JPAspect.addPointIcons(ic3,point);
        AspectLabel=JPAspect.addLabel("");
        JPAspect.addColorIcons(ic5,aspect1);
        JPAspect.addIcons(ic4,aspect2);
        JPAspect.addToggleIcon("partial");
        JPAspect.addToggleIcon("plines");
        JPAspect.addToggleIcon("showvalue");
        JPAspect.addToggleIcon("showname");
        JPAspect.addToggleIcon("bold");
        JPAspect.addToggleIcon("large");
//        JPAspect.addToggleIcon("longnames");
        JPAspect.addToggleIcon("filled");
        JPAspect.addToggleIcon("obtuse");
        JPAspect.addToggleIcon("solid");
               
        JPControls.addIcons(ic19, geom);
        ctrlJSlider=JPControls.addIcon("ctrl_slider",geom);
        ctrlJPopup=JPControls.addIcon("ctrl_popup",geom);
        ctrlJCheckBox=JPControls.addIcon("ctrl_chkbox",geom);
        ctrlJTextField=JPControls.addIcon("ctrl_txtfield",geom);
        ctrlJButton=JPControls.addIcon("ctrl_button",geom);
        
        JPHist.addIcons(ic9,null);
        
        initCursors();
        
        JPColors.addColorPicker(new JColorPanel(ZF,this));
        JPColors.addNewLine();
        JPColors.addToggleIcon("background");
        JPColors.addSimpleIcon("blank");
        JPColors.addSimpleIcon("blank");
        JPColors.addIcons(ic12,photos);
        
        JPEdit.addSimpleIcon("back");
        JPEdit.addIcons(ic10,geom);
        JPEdit.addSimpleIcon("undo");
        JPEdit.addIcons(ic11,geom);
        JPEdit.addToggleIcon("hidden");
//        JPEdit.addIcons(ic11b,geom);
        JPEdit.addToggleIcon("grid");
        
        
        MainPalette.addzone(JPDisk);
        MainPalette.addzone(JPEdit);
        
        
//        if (MW.is3D) MainPalette.addzone(JP3D);
        
//        MainPalette.addzone(JP3D);
        
        
        MainPalette.addzone(JPGeom);
        MainPalette.addzone(JPAspect);
        MainPalette.addzone(JPfunc);
        MainPalette.addzone(JPTest);
        MainPalette.addzone(JPControls);
        MainPalette.addzone(JPGrid);
        MainPalette.addzone(JPHist);
        MainPalette.addzone(JPColors);
        MainPalette.addzone(JPSizes);
        MainPalette.addzone(JPPrec);
        

        
        JPDisk.CollapseOrExpand();
//        JPEdit.CollapseOrExpand();
        JP3D.CollapseOrExpand();
//        JPGeom.CollapseOrExpand();       
//        JPAspect.CollapseOrExpand();  
        JPfunc.CollapseOrExpand();
        JPTest.CollapseOrExpand();        
        JPControls.CollapseOrExpand();        
        JPGrid.CollapseOrExpand();        
        JPHist.CollapseOrExpand();        
        JPColors.CollapseOrExpand();        
        JPSizes.CollapseOrExpand();        
        JPPrec.CollapseOrExpand();        
                
                

        
        MainPalette.pack();
        MainPalette.setVisible(false);
        if (MW.restrictedSession) {
            setSelected("move",true);
        }else{
            setSelected("point",true);
        };
        
        setDefault();
//        resetPointNameIcon();
    }
    

    
    public void initCursors(){
        JPSizes.ZoneContent.removeAll();
        JPSizes.addCursor(new JCursor(ZF,this,"minpointsize",MW.Strs.getString("palette.sizes.point"),1,9,3));
        JPSizes.addCursor(new JCursor(ZF,this,"minlinesize",MW.Strs.getString("palette.sizes.line"),1,9,1));
        JPSizes.addCursor(new JCursor(ZF,this,"arrowsize",MW.Strs.getString("palette.sizes.arrow"),3,50,15));
        JPSizes.addCursor(new JCursor(ZF,this,"minfontsize",MW.Strs.getString("palette.sizes.font"),1,64,12));
        JPSizes.ZoneContent.revalidate();
        JPSizes.ZoneContent.repaint();
        
        JPPrec.ZoneContent.removeAll();
        JPPrec.addCursor(new JCursor(ZF,this,"digits.lengths",MW.Strs.getString("palette.prec.lengths"),0,12,5));
        JPPrec.addCursor(new JCursor(ZF,this,"digits.edit",MW.Strs.getString("palette.prec.edit"),0,12,5));
        JPPrec.addCursor(new JCursor(ZF,this,"digits.angles",MW.Strs.getString("palette.prec.angles"),0,12,0));
        JPPrec.ZoneContent.revalidate();
        JPPrec.ZoneContent.repaint();
        
    }
    
    public void setDefault(){
        setSelected("type"+Global.getParameter("options.type",0),true);
//        setSelected("color"+Global.getParameter("options.color",0),true);
//        setSelected("thickness"+Global.getParameter("options.colortype",0),true);
        setSelected("obtuse",Global.getParameter("options.obtuse",true));
        setSelected("acolor"+Global.getParameter("grid.colorindex",0),true);
        setSelected("athickness"+Global.getParameter("grid.thickness",0),true);
        setSelected("numgrid",Global.getParameter("grid.labels",true));
        setSelected("dottedgrid",Global.getParameter("axesonly",true));
        setSelected("imcenter",true);
    }
    
    
    
    public void deselectgeomgroup(){
        JIcon myicon;
        for (int i=0;i<geom.size();i++){
            myicon=(JIcon)geom.get(i);
            if (myicon.isSelected){
                myicon.isSelected=false;
                myicon.repaint();
                return;
            }
        }
    }
    
    
    public String geomSelectedIcon(){
        JIcon myicon;
        for (int i=0;i<geom.size();i++){
            myicon=(JIcon)geom.get(i);
            if (myicon.isSelected){
                return myicon.name;
            }
        }
        return "";
    }
    
    
    
    public boolean isIconWithProperties(String name){
        String acceptedIcons=",expression,locus,bi_function_u,text,area,ray,segment," +
                "line,point,parallel,plumb,intersection,midpoint,bi_syma," +
                "bi_symc,bi_trans,bi_med,bi_biss,vector,fixedsegment,circle," +
                "circle3,fixedcircle,bi_arc,bi_circ,angle,fixedangle,quadric," +
                "boundedpoint,";
        return (acceptedIcons.indexOf(","+name+",")!=-1);
    }
    
    public String IconFamily(String name){
        String f=",ray,parallel,plumb,bi_med,bi_biss,";
        if (f.indexOf(","+name+",")!=-1) return "line";
        f=",intersection,midpoint,bi_syma,bi_symc,bi_trans,boundedpoint,";
        if (f.indexOf(","+name+",")!=-1) return "point";
        f=",vector,fixedsegment,";
        if (f.indexOf(","+name+",")!=-1) return "segment";
        f=",circle3,fixedcircle,bi_arc,bi_circ,quadric,";
        if (f.indexOf(","+name+",")!=-1) return "circle";
        f=",bi_function_u,expression,";
        if (f.indexOf(","+name+",")!=-1) return "text";
        f=",fixedangle,";
        if (f.indexOf(","+name+",")!=-1) return "angle";
        return name;
        
    }
    
    public String Loc(String name){
        return MW.Strs.getString("palette.aspect.label."+name);
    }
    
    public void setAspectDisabledState(String iconname){
        String AspectIcons=" bold large filled" +
                " thickness0 thickness1 thickness2" +
                " color0 color1 color2 color3 color4 color5" +
                " obtuse plines partial solid showvalue showname ";
        setDisabledIcons(AspectIcons,false);
        
        String familyIcon=IconFamily(iconname);
        if (familyIcon.equals("point")){
            setDisabledIcons(" filled obtuse plines partial solid ",true);
        }else if (familyIcon.equals("line")){
            setDisabledIcons(" filled obtuse partial solid showvalue ",true);
        }else if (familyIcon.equals("segment")){
            setDisabledIcons(" filled obtuse partial plines solid ",true);
        }else if (familyIcon.equals("angle")){
            setDisabledIcons(" partial plines ",true);
        }else if (familyIcon.equals("circle")){
            setDisabledIcons(" obtuse plines ",true);
        }else if (familyIcon.equals("area")){
            setDisabledIcons(" bold large obtuse plines partial showname ",true);
        }else if (familyIcon.equals("text")){
            setDisabledIcons(" showvalue filled obtuse plines partial solid ",true);
        }else if (familyIcon.equals("locus")){
            setDisabledIcons(" bold large obtuse plines partial solid showvalue showname ",true);
        }else if (familyIcon.equals("tracker")){
            setDisabledIcons(" bold large filled thickness0 thickness1 thickness2 color0 color1 color2 color3 color4 color5 obtuse plines partial solid showvalue showname ",true);
        }else {
            setDisabledIcons(AspectIcons,true);
        }
        
        if (iconname.equals("quadric")){
            setDisabledIcons(" partial filled ",true);
        };
        if (iconname.equals("text")){
            setDisabledIcons(" showname ",true);
        };
    }
    
    
    public void setGoodProperties(String iconname){
        int i=0;
        boolean b=false;
        if (isIconWithProperties(iconname)) {
            String familyIcon=IconFamily(iconname);
            i=Global.getParameter("options."+familyIcon+".color",0);
            Color col=Global.getParameter("options."+familyIcon+".pcolor",(Color)null);
            if (col==null) {
                setSelected("color"+i,true);
                MW.ColorPicker.setDefaultColor();
            }else{
                MW.ColorPicker.Select();
                MW.ColorPicker.setCurrentColor(col);
            }
            i=Global.getParameter("options."+familyIcon+".colortype",0);
            setSelected("thickness"+i,true);
            b=Global.getParameter("options."+familyIcon+".shownames",false);
            setSelected("showname",b);
            b=Global.getParameter("options."+familyIcon+".showvalues",false);
            setSelected("showvalue",b);
            b=Global.getParameter("options."+familyIcon+".filled",false);
            setSelected("filled",b);
            b=Global.getParameter("options."+familyIcon+".solid",false);
            setSelected("solid",b);
            b=Global.getParameter("options."+familyIcon+".bold",false);
            setSelected("bold",b);
            b=Global.getParameter("options."+familyIcon+".large",false);
            setSelected("large",b);
            b=Global.getParameter("options."+familyIcon+".obtuse",false);
            setSelected("obtuse",b);
            AspectLabel.setText(Loc("text")+Loc(familyIcon)+" :");
            
        }else{
            i=Global.getParameter("options.color",0);
            setSelected("color"+i,true);
            i=Global.getParameter("options.colortype",0);
            setSelected("thickness"+i,true);
            b=Global.getParameter("options.shownames",false);
            setSelected("showname",b);
            b=Global.getParameter("options.showvalues",false);
            setSelected("showvalue",b);
            b=Global.getParameter("options.filled",false);
            setSelected("filled",b);
            b=Global.getParameter("options.bold",false);
            setSelected("bold",b);
            b=Global.getParameter("options.large",false);
            setSelected("large",b);
            b=Global.getParameter("options.obtuse",false);
            setSelected("obtuse",b);
            AspectLabel.setText("");
        }
        setAspectDisabledState(iconname);
    }
    
   
    
    
    public void setObjectColor(int i){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".pcolor",(Color)null);
            Global.setParameter("options."+iconname+".color",i);
            MW.ColorPicker.setDefaultColor();
        }else{
            ZF.setcolor(i);
        }
    }
    
    public void setObjectColor(Color c){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".pcolor",c);
        };
    }
    
    
    
    public void setObjectColorType(int i){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".colortype",i);
        }else{
            ZF.setcolortype(i);
        }
    }
    
    public void setObjectShowName(boolean bool){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".shownames",bool);
        }else{
            ZF.setShowNames(bool);
        }
        ZF.setinfo("defaults");
        MW.PointLabel.getBetterName(null,true);
//        PointNameLabel.setText(PointObject.getBetterName(ZF.ZC.getConstruction(),CurrentPointName));
    }
    
    public void setObjectShowValue(boolean bool){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".showvalues",bool);
        }else{
            ZF.ZC.setShowValues(bool);
        }
    }
    
    public void setObjectFilled(boolean bool){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".filled",bool);
        }else{
//            ZF.ZC.setShowValues(bool);
        }
    }
    
    public void setObjectSolid(boolean bool){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".solid",bool);
        }else{
//            ZF.ZC.setShowValues(bool);
        }
    }
    
    public void setObjectLarge(boolean bool){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".large",bool);
        }else{
            ZF.ZC.setLargeFont(bool);
        }
        
    }
    
    public void setObjectBold(boolean bool){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".bold",bool);
        }else{
            ZF.ZC.setBoldFont(bool);
        }
    }
    
    public void setObjectObtuse(boolean bool){
        String iconname=geomSelectedIcon();
        if (isIconWithProperties(iconname)) {
            iconname=IconFamily(iconname);
            Global.setParameter("options."+iconname+".obtuse",bool);
        }else{
            ZF.ZC.setObtuse(bool);
        }
    }
    
    
    
    public void setRestrictedIcon(String name,boolean restrict,boolean newline){
        if (restrict){
            if (!isRestrictedIcon(name)) {
                String nl=(newline)?"ln ":"";
                JGlobals.RestrictedIcons+=nl+name+" ";
            }
        }else{
            if (isRestrictedIcon(name)) {
                int i=JGlobals.RestrictedIcons.indexOf(" "+name+" ");
                int j=i+name.length()+1;
                int rg=i-3;
                i=((rg>-1)&&(JGlobals.RestrictedIcons.substring(rg,rg+4).equals(" ln ")))?rg:i;
                JGlobals.RestrictedIcons=JGlobals.RestrictedIcons.substring(0,i)+JGlobals.RestrictedIcons.substring(j);
            }
        };
        Global.setParameter("restrictedicons",JGlobals.RestrictedIcons);
    }
    
    public boolean isFirstIconOnLine(String name){
        int rg=JGlobals.RestrictedIcons.indexOf(" "+name+" ")-3;
        if (rg<0) return false;
        return (JGlobals.RestrictedIcons.substring(rg,rg+4).equals(" ln "));
    }
    
    public boolean isRestrictedIcon(String name){
        return (JGlobals.RestrictedIcons.indexOf(" "+name+" ")>-1);
    }
    
    public boolean acceptedIcon(String name){
        if ((MW.restricted)&&(!MW.EditRestricted)){
            return (JGlobals.RestrictedIcons.indexOf(" "+name+" ")>-1);
        }else{
            return true;
        }
    }
    
    public boolean MustBeFirstIconOnLine(String name){
        if ((MW.restricted)&&(!MW.EditRestricted)){
            int rg=JGlobals.RestrictedIcons.indexOf(" "+name+" ")-3;
            if (rg<0) return false;
            return (JGlobals.RestrictedIcons.substring(rg,rg+4).equals(" ln "));
        }else{
            return false;
        }
    }
    
    
    // Disable/Enable Icons list : string with space separator
    public void setDisabledIcons(String iconname,boolean dis){
        JIcon myicon;
        
        for (int i=0;i<AllIcons.size();i++){
            myicon=(JIcon)AllIcons.get(i);
            if (iconname.indexOf(" "+myicon.name+" ")!=-1) {
//            if (myicon.name.equals(iconname)) {
                if (myicon.isDisabled!=dis) myicon.isDisabled=dis;
                
                myicon.repaint();
                if (myicon.name.equals("color0")) {
                    MW.ColorPicker.setDisabled(dis);
                    MW.ColorPicker.repaint();
                }
//                return;
            }
        }
    }
    
    
    public void setSelected(String iconname,boolean sel){
        JIcon myicon;
        for (int i=0;i<AllIcons.size();i++){
            myicon=(JIcon)AllIcons.get(i);
            if (myicon.name.equals(iconname)) {
                if (myicon.isSelected!=sel) myicon.ClicOnMe();
                return;
            }
        }
    }
    
    public boolean isSelected(String iconname){
        JIcon myicon;
        for (int i=0;i<AllIcons.size();i++){
            myicon=(JIcon)AllIcons.get(i);
            if (myicon.name.equals(iconname)) {
                return myicon.isSelected;
            }
        }
        return false;
    }
    
    public void setSelectedWithoutClic(String iconname,boolean sel){
        JIcon myicon;
        for (int i=0;i<AllIcons.size();i++){
            myicon=(JIcon)AllIcons.get(i);
            if (myicon.name.equals(iconname)) {
                if (myicon.isSelected!=sel) myicon.isSelected=sel;
                myicon.repaint();
                return;
            }
        }
    }
    
    public void ClicOn(String iconname){
        JIcon myicon;
        for (int i=0;i<AllIcons.size();i++){
            myicon=(JIcon)AllIcons.get(i);
            if (myicon.name.equals(iconname)) {
                myicon.ClicOnMe();
                return;
            }
        }
    }
    
    
    private int JPOrder(JPaletteZone mypal){
        int i;
        for (i=0;i<PalettesOrder.length;i++){
            if (PalettesOrder[i]==mypal.name) break;
        };
        return i;
    }
    
    public void addpalette(JPalette JP,JPaletteZone JPZ,boolean isdock){
        int j;
        int JPZorder=JPOrder(JPZ);
        Component[] myzones=MainPalette.Content.getComponents();
        JPaletteZone dockJPZ=new JPaletteZone(ZF,this,MainPalette,JPZ.title,JPZ.name);
        Component[] mylines=JPZ.ZoneContent.getComponents();
        int max=mylines.length;
        for (int i=0;i<max;i++){
            dockJPZ.ZoneContent.add((JPanel)mylines[i]);
        };
        
        int maxzones=MainPalette.Content.getComponentCount();
        for (j=0;j<maxzones;j++){
            if (JPZorder<JPOrder((JPaletteZone)MainPalette.Content.getComponent(j))) break;
        };
        
        MainPalette.Content.add(dockJPZ,j);
        if (isdock) dockJPZ.collapse();
    }
    
    public void fix3Dpalette(){
        if (MW.is3D) {
            addpalette(MainPalette,JP3D,false);
            MainPalette.pack();
        }
    }
    
    public void dockpalette(JPalette JP,JPaletteZone JPZ){
        addpalette(JP,JPZ,true);
        MainPalette.pack();
        JPs.remove(JP);
        JP.dispose();
    }
    
    public void CollapseToFitScreenHeight(JPaletteZone askfrom){
        JPaletteZone myZone;
        int Ybottom=MainPalette.getY()+MainPalette.getPreferredSize().height;
        
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenRect=ge.getMaximumWindowBounds();
        
        int ScreenBottom=screenRect.height;
        // Only for mac users, reduce because of menubar height :
        if (System.getProperty("mrj.version") != null){
            ScreenBottom+=22;
        };
//        int ScreenBottom=Toolkit.getDefaultToolkit().getScreenSize().height;
        if (Ybottom>ScreenBottom){
            for (int i=MainPalette.Content.getComponentCount()-1;i>-1;i--){
                myZone=(JPaletteZone)MainPalette.Content.getComponent(i);
                if (myZone.name!=askfrom.name){
                    if (myZone.ContentVisible) myZone.collapse();
                    Ybottom=MainPalette.getY()+MainPalette.getPreferredSize().height;
                }
                if (Ybottom<ScreenBottom) break;
            }
            if (Ybottom>ScreenBottom){
                MainPalette.setLocation(MainPalette.getX(),MainPalette.getY()-(Ybottom-ScreenBottom));
            }
        }
    }
    
    
    public JPalette detachpalette1(JPaletteZone JPZ){
        JPalette freeJP=new JPalette(ZF,MW,false);
        
        JPaletteZone freeJPZ=new JPaletteZone(ZF,this,freeJP,JPZ.title,JPZ.name);
        // adding all content of JPZ to freeJPZ :
        Component[] mylines=JPZ.ZoneContent.getComponents();
        int max=mylines.length;
        for (int i=0;i<max;i++){
            freeJPZ.ZoneContent.add((JPanel)mylines[i]);
        };
        
        // add zone to the new floating palette :
        freeJP.addzone(freeJPZ);
        
        // remove JPZ from the origin palette :
        //JP.Content.remove(JPZ);
        
        Point JPZLoc=JPZ.getLocation();
        Point JPLoc=MainPalette.getLocation();
        freeJP.setLocation(JPLoc.x+JPZLoc.x,JPLoc.y+JPZLoc.y);
        
        JPs.add(freeJP);
        freeJP.pack();
        freeJP.setVisible(true);
        //JP.pack();
        
        return freeJP;
    }
    
    public void detachpalette2(JPalette freeJP,JPaletteZone JPZ){
        Point freeJPLoc=freeJP.getLocation();
        for (int i=0;i<30;i++){
            freeJP.setLocation(freeJPLoc.x+i,freeJPLoc.y+i);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) { }
        };
        MainPalette.Content.remove(JPZ);
        MainPalette.pack();
    }
    
    
    public void dispose(){
        MainPalette.dispose();
        for (int i=0;i<JPs.size();i++){
            JPalette myJP=(JPalette)JPs.get(i);
            myJP.dispose();
        }
    }
    
    
}
