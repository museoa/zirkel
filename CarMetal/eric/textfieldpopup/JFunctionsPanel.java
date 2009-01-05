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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author erichake
 */
public class JFunctionsPanel extends JMenuPanel {

    int Linemax = 3;
    String funcs = "&& || ! < > <= >= == =~ $ " +
            "x() y() d(,) if(,,) a(,,) inside(,) $ " +
            "sqrt() exp() log() round() ceil() floor() abs() sign() $ " +
            "sin() cos() tan() rsin() rcos() rtan() " +
            "arcsin() arccos() arctan() rarcsin() rarccos() rarctan() " +
            "deg() rad() sinhyp() coshyp() angle180() angle360() $ " +
            "integrate(,,) zero(,,) diff(,,) min(,,) max(,,) $ " +
            "windoww windowh windowcx windowcy pixel";

    public JFunctionsPanel(JPopupMenu men,JComponent jtf) {
        super(men);
        JTF = jtf;
        iconwidth = 75;
        iconheight = 20;
        String[] f = funcs.split(" ");
        JPanel line = null;
        int j=0;
        for (int i = 0; i < f.length; i++) {
            if ((j % Linemax) == 0) {
                add(line = getnewline());
            }
            if(f[i].equals("$")) {
                add(new JSeparator());
                j=0;
            } else {
                line.add(getJButton(f[i]));
                j++;
            }
        }
    }

    public void doAction(JButton mybtn) {
        String s=mybtn.getText();
        mybtn.setOpaque(false);
        mybtn.setContentAreaFilled(false);
        if (JTF != null) {
            JTextComponent jt = (JTextComponent) JTF;
            if ((s.endsWith(")"))&&(jt.getSelectedText()!=null)) {
                String mytxt = jt.getText().substring(0, jt.getSelectionStart());
                mytxt+=s.substring(0, s.length()-1);
                mytxt+=jt.getSelectedText()+")";
                int car = mytxt.length();
                mytxt += jt.getText().substring(jt.getSelectionEnd());
                jt.setText(mytxt);
                jt.setCaretPosition(car);
            } else {
                String mytxt = jt.getText().substring(0, jt.getSelectionStart());
                mytxt += (s.endsWith(")"))?s.substring(0,s.length()-1):s;
                int car = mytxt.length();
                mytxt += jt.getText().substring(jt.getSelectionEnd());
                jt.setText(mytxt);
                jt.setCaretPosition(car);
               
            }
            MEN.setVisible(false);
        }
    }
}
