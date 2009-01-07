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

import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class JPalette extends JDialog {
    private rene.zirkel.ZirkelFrame ZF;
    private JZirkelFrame MW;
    JPanel Content;
    boolean MainPalette;
    JToolTip ToolTip;
    
    public void FollowWindow(){
        Point MWloc=MW.getLocation();
        this.setLocation(MWloc.x+MW.getSize().width+3,MWloc.y);
        Toolkit.getDefaultToolkit().sync();
    }
    
    public JPalette(rene.zirkel.ZirkelFrame zf,JZirkelFrame mywin,boolean mainpal) {
        super(mywin,false);
        ZF=zf;
        MW=mywin;
        MainPalette=mainpal;
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Palette");
        setBackground(new java.awt.Color(240, 240, 240));
        setFocusable(false);
//        setFocusableWindowState(false);
        FollowWindow();
        this.setUndecorated(true);
        Content=new JPaletteContainer();
        this.setContentPane(Content);
        
        ToolTip=new JToolTip(this);
        
        
        
        
    }
    
        public void paintImmediately() {
            Content.paintImmediately(0, 0, Content.getWidth(), Content.getHeight());
        }
    
    
    public void addzone(JPaletteZone myzone){
        if (myzone.ZoneContent.getComponentCount()>0) Content.add(myzone);
    }
    
    
    
    
    private class JPaletteContainer extends JPanel{
        public JPaletteContainer() {
            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        }
    }

    
    
}
