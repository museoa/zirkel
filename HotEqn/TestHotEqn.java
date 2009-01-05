/*
 * Created on 03.12.2005
 *
 */

import java.awt.*;
import java.awt.event.*;
import atp.sHotEqn;

public class TestHotEqn 
{	static sHotEqn HE;

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{	Frame F=new Frame("Test Hot Eqn");
		F.addWindowListener(new WindowAdapter ()
			{	public void windowClosing (WindowEvent e)
				{	System.exit(0);
				}
			});
		
		Panel P=new Panel()
		{	public void paint (Graphics g)
			{	g.drawLine(0,100,100,100);
				HE.paint(0,100,g);
			}
		};
		HE=new sHotEqn(P);
		HE.setEquation("2_{x^y+2^t}^2+\\frac{\\sqrt{\\alpha}+2}{x+\\frac{1}{x}}");		
		
		F.setLayout(new BorderLayout());
		F.add("Center",P);

		F.setSize(500,500);
		F.setVisible(true);
	}

}
