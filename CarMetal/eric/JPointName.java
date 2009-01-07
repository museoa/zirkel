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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import rene.gui.Global;
import rene.zirkel.construction.Construction;
import rene.zirkel.objects.ConstructionObject;

/**
 *
 * @author erichake
 */
public class JPointName {
//    private String StartLetter="\u03b6";
    private static String GenericLetter="P";
    private static String majLettersSet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String minLettersSet="abcdefghijklmnopqrstuvwxyz";
    private static String greekmajLettersSet="\u0391\u0392\u0393\u0394\u0395\u0396" +
            "\u0397\u0398\u0399\u039A\u039B\u039C\u039D\u039E\u039F\u03A0\u03A1\u03A3" +
            "\u03A4\u03A5\u03A6\u03A7\u03A8\u03A9";
    private static String greekminLettersSet="\u03B1\u03B2\u03B3\u03B4\u03B5\u03B6\u03B7\u03B8\u03B9" +
            "\u03BA\u03BB\u03BC\u03BD\u03BE\u03BF\u03C0\u03C1\u03C3\u03C4\u03C5\u03C6" +
            "\u03C7\u03C8\u03C9";
    private static ArrayList LettersSet=new ArrayList();
    
    
    private String LetterSuffix="";
    //Ct should be the ZF.ZC.getConstruction() object :
    private Construction ZCcn=null;
    private JButton PaletteBtn=null;
    private JZirkelFrame JZF=null;
    
    public static int minLettersSetCode=1;
    
    
//    private String Letters=majLettersSet;
    //0:majLettersSet   ,   1:minLettersSet ,   2:greekmajLettersSet    ,   3:greekminLettersSet
    private int LetterSetCode=0;
    private int StartLetter=0;

    
    /** Creates a new instance of JPointLabel
     * @param jzf 
     */
    public JPointName(JZirkelFrame jzf) {
        JZF=jzf;
        ZCcn=JZF.ZF.ZC.getConstruction();
        PaletteBtn=new JPaletteButton(JZF);
    }
    
    public JPointName() {
        LettersSet.add(majLettersSet);
        LettersSet.add(minLettersSet);
        LettersSet.add(greekmajLettersSet);
        LettersSet.add(greekminLettersSet);
    }
    
    public String getCurrentLetterSet(){
        return (String) LettersSet.get(LetterSetCode);
    }
    
    public int getCurrentLetterSetCode(){
        return LetterSetCode;
    }
    
    static void fixsize(Component cp,int w,int h){
        Dimension d=new Dimension(w,h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }
    
    public void addPaletteJLabel(JPanel jp){
        fixsize(PaletteBtn,28,jp.getSize().height);
        jp.add(PaletteBtn);
    }
    
    public void setEnabledJLabel(boolean bool){
        PaletteBtn.setEnabled(bool);
        
    }
    
    public void addSuffixChar(){
        if (LetterSuffix.equals("''")) LetterSuffix="";
        else LetterSuffix+="'";
    }
    
    void setSuffixChar(String suff){
        LetterSuffix=suff;
    }
    
    public static String getGenericName(Construction myC){
        int i=1;
        ConstructionObject o=myC.find(GenericLetter+i);
        while (o!=null) {
            i++;
            o=myC.find(GenericLetter+i);
        };
        return (GenericLetter+i);
    }
    
    public String getBetterName(Construction myC,boolean setPaletteTxt){
        String Letters=getCurrentLetterSet();
        String s=Letters.substring(StartLetter,StartLetter+1);
        if (ZCcn==null) {
            s=getGenericName(myC);
        } else if (Global.getParameter("options.point.shownames",false)) {
            int i=Letters.indexOf(s),k=i;
            s+=LetterSuffix;
            ConstructionObject o=ZCcn.find(s);
            while ((i<(Letters.length()-1))&&(o!=null)) {
                i++;
                s=Letters.substring(i,i+1)+LetterSuffix;
                o=ZCcn.find(s);
            };
            i=-1;
            while ((i<k)&&(o!=null)) {
                i++;
                s=Letters.substring(i,i+1)+LetterSuffix;
                o=ZCcn.find(s);
            };
            if (o!=null) {
                s=getGenericName(ZCcn);
            }
            
        } else s=getGenericName(ZCcn);
        if ((PaletteBtn!=null)&&(setPaletteTxt)) PaletteBtn.setText(s);
        return s;
    }
    
    public static int findSet(String s){
        for (int i=0;i<LettersSet.size();i++){
            String mySet=(String)LettersSet.get(i);
            if (mySet.indexOf(s)!=-1) return i;
        }
        return -1;
    }
    
    public boolean isLetterAllowed(String s){
        return (getCurrentLetterSet().indexOf(s)!=-1);
    }
    
    public boolean isLetterAccepted(String s){
        if (ZCcn!=null) return (ZCcn.find(s)==null);
        else return false;
    }
    
    public String setStartLetter(String s){
        if (isLetterAllowed(s)) {
            StartLetter=getCurrentLetterSet().indexOf(s);
        };
        return getBetterName(null,true);
    }
    
    public String setLetterSet(int i){
        LetterSetCode=i;
        StartLetter=0;
        return getBetterName(null,true);
    }
    
}


class JPaletteButton extends JButton implements MouseListener{
    private JZirkelFrame JZF;
    int[] x={0,10,0};
    int[] y={0,10,10};
    
    public void paintComponent(java.awt.Graphics g){
        
        Dimension d = this.getSize();
        

        
        
        
        int sze=6;
        x[0]=d.width-sze;y[0]=d.height;
        x[1]=d.width;y[1]=d.height-sze;
        x[2]=d.width;y[2]=d.height;
        Graphics2D g2 = (Graphics2D) g;
        
        
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(new Color(50,50,50));
        g2.fillPolygon(x,y,3);
        
//        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.15f));
//        g2.setColor(new Color(0,0,255));
//        g2.fillPolygon(x,y,3);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        super.paintComponent(g);
    }
    
    public JPaletteButton(JZirkelFrame jzf){
        JZF=jzf;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder());
        setFont(new java.awt.Font(JGlobals.GlobalFont, 1, 14));
        setForeground(new Color(50,50,50));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        addMouseListener(this);
    }
    
    public void mouseClicked(MouseEvent e) {
    }
    
    public void mousePressed(MouseEvent e) {
        if (isEnabled()) {
            JZF.ZF.setinfo("nom_points",false);
            new JCharacterPalette(JZF,this);
        }
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
}








 



