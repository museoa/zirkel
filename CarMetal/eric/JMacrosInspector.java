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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import rene.zirkel.ZirkelFrame;
import rene.zirkel.macro.Macro;
import rene.zirkel.objects.ConstructionObject;


/* This class represent the macro properties inspector
 */
public class JMacrosInspector {
    ZirkelFrame ZF;
    JZirkelFrame JZF;
    JPalette freeJP;
    Macro m;
    int PW=310; //Palette width
    JDefaultMutableTreeNode node;
    IContent content;
    
    public JMacrosInspector(ZirkelFrame zf,JZirkelFrame jzf) {
        ZF=zf;
        JZF=jzf;
        freeJP=new JPalette(ZF,JZF,false);
        
        freeJP.setFocusableWindowState(true);
        freeJP.setFocusable(false);
        
        JPaletteZone freeJPZ=new JPaletteZone(ZF,null,freeJP,getLocal("mi.pal.name"),"macrosproperties",PW);
        freeJPZ.setFocusable(false);
        freeJPZ.ZoneContent.setFocusable(false);
        
        // content is a JPanel which represents the content of the palette
        content=new IContent(this);
        freeJPZ.ZoneContent.removeAll();
        freeJPZ.ZoneContent.add(content);
        freeJP.addWindowListener(new WindowAdapter(){
            public void windowDeactivated(WindowEvent e) {
                // if palette being closed :
                if (!(freeJP.isVisible())){
                    content.changemacro();
                    node=null;
                    m=null;
                }
            }       
        });
       
        freeJP.addzone(freeJPZ);
        
        freeJP.pack();
    }
    
    
    // find localisation strings from eric.docs.JZirkelProperties :
    public String getLocal(String s){
        return JZF.Strs.getString(s);
    }
    
    // set location of the palette (near the right border of the macro panel) :
    public void setStandardLocation(){
        freeJP.setLocation(JZF.getLocation().x+JZF.ZContent.leftpanelwidth+10,JZF.getLocation().y+100);
    }
    
    public void clearPalette(){
        if (m!=null) content.changemacro();
        node=null;
        m=null;
        content.clearfields();
    }
    
    // method called each time the user ask properties or select another macro in the tree
    public void setMacro(JDefaultMutableTreeNode mynode){
        if (m!=null) content.changemacro();
        node=mynode;
        m=node.m;
        content.fillfields();
    }
    
    public void setVisible(boolean bool){
        freeJP.setVisible(bool);
    }
    
    
    // this embedded class represents the content of the palette :
    public class IContent extends JPanel{
        JMacrosInspector JMI;
        JLabel name;
        JTextArea comment;
        JMacrosProperties props;
        JCheckBox hideDuplicates;
        
        private JPanel margin(int w){
            JPanel mypan=new JPanel();
            fixsize(mypan,w,1);
            mypan.setOpaque(false);
            return mypan;
        }
        
        public IContent(JMacrosInspector jmi){
            JMI=jmi;
            setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
            this.setFocusable(false);
            name=new JLabel();
            comment=new JTextArea();
            props=new JMacrosProperties(JMI);
            hideDuplicates=new JCheckBox(getLocal("mi.hideduplicates"));
            
            newnameline();
            this.add(new mySep(1));
            newcommentline();
            this.add(new mySep(1));
            newproperties();
            this.add(new mySep(1));
            newhideproperties();
            this.add(new mySep(1));
            newcontrolline();
            
            
        }
        
        // set sizes of a palette's JComponent :
        private void fixsize(JComponent cp,int w,int h){
            Dimension d=new Dimension(w,h);
            cp.setMaximumSize(d);
            cp.setMinimumSize(d);
            cp.setPreferredSize(d);
            cp.setSize(d);
        }
        
        // add the "name" topic of the palette :
        public void newnameline(){
            JPanel rub=new myRub();
            JPanel myline1=new ContentLine(25);
            fixsize(name,PW-10,18);
            myline1.add(margin(5));
            myline1.add(name);
            rub.add(myline1);
            this.add(rub);
        }
        
        // add the "comment" topic of the palette :
        public void newcommentline(){
            JPanel rub=new myRub();
            JScrollPane jScroll = new JScrollPane();
            JPanel myline1=new ContentLine(22);
            JLabel namelabel = new JLabel(getLocal("mi.comment"));
            fixsize(namelabel,PW-10,14);
            myline1.add(margin(5));
            myline1.add(namelabel);
            JPanel myline2=new ContentLine(100);
            
            comment.setLineWrap(true);
            jScroll.setViewportView(comment);
            jScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            fixsize(jScroll,PW-10,80);
            myline2.add(margin(5));
            myline2.add(jScroll);
            rub.add(myline1);
            rub.add(myline2);
            this.add(rub);
        }
        
        // add the "target properties" topic of the palette :
        public void newhideproperties(){
            JPanel rub=new myRub();
            rub.setLayout(new javax.swing.BoxLayout(rub, javax.swing.BoxLayout.Y_AXIS));
            JPanel myline1=new ContentLine(22);
            JLabel namelabel = new JLabel(getLocal("mi.hideproperties"));
            fixsize(namelabel,PW-10,14);
            myline1.add(margin(5));
            myline1.add(namelabel);
            JPanel mylineC3=new ContentLine(27);
            
            hideDuplicates.setOpaque(false);
            mylineC3.add(margin(20));
            mylineC3.add(hideDuplicates);
            rub.add(myline1);
            rub.add(mylineC3);
            this.add(rub);
        }
        
        // add the apply button to the bottom of the palette :
        public void newcontrolline(){
            JPanel rub=new myRub();
            JPanel myline=new ContentLine(25);
            JButton applybtn=new JButton("Apply");
            applybtn.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    changemacro();
                    // It's important to refresh the inspector because parameters position may have change :
                    fillfields();
                }
            });
//            applybtn.setBorder(BorderFactory.createRaisedBevelBorder());
//            applybtn.setBorder(BorderFactory.createEmptyBorder());
            fixsize(applybtn,90,18);
            applybtn.setFont(new Font("System",Font.BOLD,11));
            JPanel sep=new JPanel();
            sep.setOpaque(false);
            myline.add(sep);
            
//            JButton jb=new JButton("Aaaa");
//            fixsize(jb,90,18);
            
            
            myline.add(applybtn);
            myline.add(margin(5));
            rub.add(myline);
            this.add(rub);
        }
        
        // add the parameters properties to the palette :
        public void newproperties(){
            JPanel rub=new myRub();
            JPanel myline1=new ContentLine(22);
            JLabel namelabel = new JLabel(getLocal("mi.properties"));
            myline1.add(margin(5));
            myline1.add(namelabel);
            fixsize(namelabel,PW-10,14);
            JPanel myline2=new ContentLine(130);           
            fixsize(props,PW-10,100);
            myline2.add(margin(5));
            myline2.add(props);
            rub.add(myline1);
            rub.add(myline2);
            this.add(rub);
        }
        

        /*************************************************
         * this is the tricky method : it reads the inspector changes
         * and then store the new values in the macro m.
         * A macro contains two types of parameters :
         *      1) normal parameters (the one you shows at the first step 
         *      of macro's creation ). They are inside the m.Params array
         *      for ConstructionObjects and m.Prompts array for prompts
         *      2) numerical input parameters (it's possible to make macros 
         *      with numerical inputs ). Name of Objects are in the PromptFor
         *      array and prompts in the PromptName array
         *************************************************/
        public void changemacro(){
            ConstructionObject[] params;
            Vector newparams=new Vector();
            Vector newprompts=new Vector();
            Vector newpromptFor=new Vector();
            Vector newpromptName=new Vector();
            props.stopCellEditing();
            if (m==null) return;
            if (isError()) return;
            m.setComment(comment.getText());
            params=m.getParams();
            
            // read "normal" parameters and store them in :
            // newparams and newprompts if "ask" is not checked (stays "normal")
            // newPromptFor and newPromptName if "ask" is not checked (becomes "numerical input")
            for (int i=0;i<params.length;i++){
                params[i].setName(props.getOName(i));
                if (props.getOAsk(i)){
                    newpromptFor.add(props.getOName(i));
                    newpromptName.add(props.getOPrompt(i));
                    params[i].setHidden(true);
                    params[i].clearParameter();
                }else{
                    newparams.add(params[i]);
                    if (props.getOFix(i)){
                        newprompts.add("="+props.getOName(i));
                    }else{
                        newprompts.add(props.getOPrompt(i));
                    };
                }
            };
            
            int rang=0;
            // read "numerical input" parameters and store them in :
            // newparams and newprompts if "ask" is not checked (stays "normal")
            // newPromptFor and newPromptName if "ask" is not checked (becomes "numerical input")
            for (int i=params.length;i<props.getRowCount();i++){
                ConstructionObject E=null;
                    // looking for the expression with the name m.PromptFor[i-params.length]
                    for(int j=0;j<m.V.size();j++){
                        //sure it's going to find one :
                        if (((ConstructionObject)m.V.get(j)).getName().equals(m.PromptFor[i-params.length])) {
                            E=(ConstructionObject) m.V.get(j);
                            break;
                        }
                    };
                    E.setName(props.getOName(i));
                if (props.getOAsk(i)){
                    newpromptFor.add(props.getOName(i));
                    newpromptName.add(props.getOPrompt(i));
                }else{
                    newparams.add(rang,E);
                    if (props.getOFix(i)){
                        newprompts.add(rang,"="+props.getOName(i));
                    }else{
                        newprompts.add(rang,props.getOPrompt(i));
                    };
                    rang++;
                }
            }
            
            int ln=newparams.size();
            // Clear and prepare the Params, Prompts and LastParams arrays :
            m.Params=new ConstructionObject[ln];
            m.Prompts=new String[ln];
            m.LastParams=new String[ln];
            
            // Store the newparams and newprompts in the macro :
            for (int i=0;i<ln;i++){
                m.Params[i]=(ConstructionObject) newparams.get(i);
                m.Prompts[i]=(String) newprompts.get(i);
                m.LastParams[i]=null;
                m.Params[i].setHidden(false);
                m.Params[i].setMainParameter();
                m.Params[i].setParameter();
            }
            
            ln=newpromptFor.size();
            // Clear and prepare the PromptFor, PromptName arrays :
            m.PromptFor=new String[ln];
            m.PromptName=new String[ln];
            // Store the newpromptFor and newpromptName in the macro :
            for (int i=0;i<ln;i++){
                m.PromptFor[i]=(String) newpromptFor.get(i);
                m.PromptName[i]=(String) newpromptName.get(i);
            }
           
            // Conform the macro's hideduplicate property to the checkbox value :
            m.hideDuplicates(hideDuplicates.isSelected());
        }
        
        // this method is called by the JMacroProperties object
        // each time a user check/uncheck a "fix" JCheckBox :
        public void fixObject(int i,boolean fix){
            String newprompt=(fix)?"":props.getOName(i);
            props.setOPrompt(newprompt,i);
            if (fix) props.setOAsk(false,i);
        }
        
        // this method is called by the JMacroProperties object
        // each time a user check/uncheck a "ask" JCheckBox :
         public void askObject(int i,boolean ask){
            if ((ask) && props.getOFix(i)){
                props.setOFix(false,i);
                fixObject(i,false);
            }
        }
         
        public void clearfields(){
            name.setText(getLocal("mi.name"));
            comment.setText("");
            props.removeAllRows();
            hideDuplicates.setSelected(false);
        }
        
        public boolean isError(){
           boolean isErr=false;
           // first see if at least one row contains no selected checkbox :
           int ln=props.getRowCount();
           boolean err=true;
            for (int i=0;i<ln;i++){
               err=(err)&&((props.getOFix(i))||(props.getOAsk(i)));
            }
           if (err){
               JOptionPane.showMessageDialog(null, getLocal("mi.error.initial"));
               return true;
           }
           return isErr;
        }
        
        
        // read the params, prompts, PromptFor, PromptName arrays of
        // the macro and fill the inspector fields :
        public void fillfields(){
            ConstructionObject[] params;
            String[] prompts;
            if (m==null) return;
            name.setText(getLocal("mi.name")+" "+(String) node.getUserObject());
            comment.setText(m.Comment);
            props.removeAllRows();
            params=m.getParams();
            prompts=m.getPrompts();
            String pr="";
            // fill JTable first lines with "normal" parameters :
            for (int i=0;i<params.length;i++){
                pr="="+params[i].getName();
                String tpe="";
                try {
                    tpe = JZF.Strs.getString(params[i].getClass().getName());
                } catch(Exception e) {
                    tpe=params[i].getClass().getName();
                };
                boolean withask=params[i].getClass().getName().endsWith("ExpressionObject");
//                withask=(withask)||(params[i].getClass().getName().endsWith("FixedAngleObject"));
//                withask=(withask)||(params[i].getClass().getName().endsWith("PrimitiveCircleObject"));
                if (withask) {
                    props.addRow(tpe,params[i].getName(),prompts[i],prompts[i].equals(pr),false);
                }else{
                    props.addRow(tpe,params[i].getName(),prompts[i],prompts[i].equals(pr));
                }
            };
            // fill the rest of JTable with PromptFor Expressions, if any :
            for (int i=0;i<m.PromptFor.length;i++){
                String tpe=JZF.Strs.getString("rene.zirkel.objects.ExpressionObject");
                props.addRow(tpe,m.PromptFor[i],m.PromptName[i],false,true);
            };
            hideDuplicates.setSelected(m.hideDuplicates());
        };
        
        
        
        class myRub extends javax.swing.JPanel{
            public void paintComponent(java.awt.Graphics g){
                super.paintComponent(g);
                java.awt.Dimension d = this.getSize();
                g.drawImage(JZF.JZT.getImage("palbackground2.gif"),0,0,d.width, d.height,this);
            }
            
            public myRub(){
                this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
                this.setAlignmentX(0F);
            }
            
        }
        
        class ContentLine extends javax.swing.JPanel{
            
            public ContentLine(int height){
                this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
                this.setAlignmentX(0F);
                this.setMaximumSize(new java.awt.Dimension(PW, height));
                this.setMinimumSize(new java.awt.Dimension(PW, height));
                this.setPreferredSize(new java.awt.Dimension(PW, height));
                this.setSize(PW,height);
                this.setOpaque(false);
            }
            
        }
        
        class mySep extends javax.swing.JPanel{
           
            
            public mySep(int height){
                this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
                this.setAlignmentX(0F);
                this.setMaximumSize(new java.awt.Dimension(PW, height));
                this.setMinimumSize(new java.awt.Dimension(PW, height));
                this.setPreferredSize(new java.awt.Dimension(PW, height));
                this.setSize(PW,height);
                this.setBackground(new Color(200,200,200));
            }
            
        }
    }
    
    
}
