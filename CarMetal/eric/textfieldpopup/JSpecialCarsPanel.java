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

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

/**
 *
 * @author erichake
 */
public class JSpecialCarsPanel extends JMenuPanel {
int Linemax=10;

    private String greekmajLettersSet = "\u0391\u0392\u0393\u0394\u0395\u0396" +
            "\u0397\u0398\u0399\u039A\u039B\u039C\u039D\u039E\u039F\u03A0\u03A1\u03A3" +
            "\u03A4\u03A5\u03A6\u03A7\u03A8\u03A9";
    private String greekminLettersSet = "\u03B1\u03B2\u03B3\u03B4\u03B5\u03B6\u03B7\u03B8\u03B9" +
            "\u03BA\u03BB\u03BC\u03BD\u03BE\u03BF\u03C0\u03C1\u03C3\u03C4\u03C5\u03C6" +
            "\u03C7\u03C8\u03C9";

    public JSpecialCarsPanel(JPopupMenu men,JComponent jtf) {
        super(men);
        JTF=jtf;
        JPanel line = null;
        for (int i = 0; i < greekminLettersSet.length(); i++) {
            if ((i % Linemax) == 0) {
                add(line = getnewline());
            }
            line.add(getJButton(greekminLettersSet.substring(i, i + 1)));
        }
        add(new JSeparator());
        for (int i = 0; i < greekmajLettersSet.length(); i++) {
            if ((i % Linemax) == 0) {
                add(line = getnewline());
            }
            line.add(getJButton(greekmajLettersSet.substring(i, i + 1)));
        }
    }
}
