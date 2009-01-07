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
 
 
 package eric.textfieldpopup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import atp.sHotEqn;
import eric.JGlobals;

/**
 *
 * @author erichake
 */
public class JTexPanel extends JMenuPanel {

    int Linemax = 4;
    String funcs = "\\frac{a}{b} \\sqrt{a} \\sqrt[n]{a} \\vec{u} \\widehat{ABC} \\hat{a} a_1 a^2 " +
            "\\sum_{i=0}^{n} \\prod_{i=0}^{n} \\int_{a}^{b} \\oint_{a}^{b} \\bar{z} \\fbox{box}";
//            +"\\left|\\begin{array}{cc}a_{11}&a_{12}\\\\\\\\a_{21}&a_{22}\\end{array}\\right| " +
//            "\\left(\\begin{array}{cc}a_{11}&a_{12}\\\\\\\\a_{21}&a_{22}\\end{array}\\right) ";

    public JTexPanel(JPopupMenu men, JComponent jtf) {
        super(men);
        JTF = jtf;
        iconwidth = 50;
        iconheight = 50;
        String[] f = funcs.split(" ");
        JPanel line = null;
        for (int i = 0; i < f.length; i++) {
            if ((i % Linemax) == 0) {
                add(line = getnewline());
            }
            line.add(getJButton(f[i]));
        }
    }

    public void doAction(JButton mybtn) {
        String s = ((myJButton) mybtn).EQ;
        JTextComponent jt = (JTextComponent) JTF;
        mybtn.setOpaque(false);
        mybtn.setContentAreaFilled(false);
        String mytxt = jt.getText().substring(0, jt.getSelectionStart());
        int nbDollars=mytxt.split("\\$").length-1;
        if (nbDollars % 2==0){
            s = "$" + s + "$";
        }   
        if (jt.getSelectedText() != null) {
            Pattern p = Pattern.compile("\\{([^\\}]*)\\}", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(s);
            if (m.find()) {
                s=m.replaceFirst("{"+jt.getSelectedText()+"}");
            }
        }
        mytxt +=s;
        int car = mytxt.length();
        mytxt += jt.getText().substring(jt.getSelectionEnd());
        jt.setText(mytxt);
        jt.setCaretPosition(car);
        MEN.setVisible(false);

    }

    public JButton getJButton(String s) {
        myJButton mybtn = new myJButton(s);
        mybtn.setBorder(BorderFactory.createEmptyBorder());
        mybtn.setBorderPainted(false);
        mybtn.setFocusPainted(false);
        mybtn.setFocusable(false);
        mybtn.setBackground(new Color(228, 222, 255));
        mybtn.setOpaque(false);
        mybtn.setContentAreaFilled(false);
        mybtn.addMouseListener(this);
        mybtn.setFont(new java.awt.Font(JGlobals.GlobalFont, 1, 14));
        mybtn.setForeground(new Color(20, 20, 20));
        fixsize(mybtn, iconwidth, iconheight);
        return mybtn;
    }

    class myJButton extends JButton {
        String EQ = null;
        sHotEqn HE = null;

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension d = this.getSize();
            Dimension eq = HE.getSizeof(EQ, g);
            HE.paint((d.width - eq.width) / 2, (d.height - eq.height) / 2, g);
        }

        public myJButton(String s) {
            super();
            EQ = s;
            HE = new sHotEqn(this);
            HE.setHAlign("center");
            HE.setEquation(s);

        }
    }
}
