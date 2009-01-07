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
 
 
 package rene.zirkel.tools;

import java.awt.event.MouseEvent;
import java.util.Enumeration;

import rene.util.MyVector;
import rene.util.xml.XmlWriter;
import rene.zirkel.Zirkel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.constructors.ObjectConstructor;
import rene.zirkel.objects.ConstructionObject;

public class BreakpointAnimator extends ObjectConstructor
	implements Runnable
{	MyVector Breaks; // Vector of breakpoints
	ZirkelCanvas ZC;
	boolean Loop=false;

	public BreakpointAnimator ()
	{	Breaks=new MyVector();
	}

	public void mousePressed (MouseEvent e, ZirkelCanvas zc)
	{	double x=zc.x(e.getX()),y=zc.y(e.getY());
		ZC=zc;
		if (Running)
		{	if (e.isShiftDown())
				Loop=!Loop;
			else
			{	Stopped=true;
				zc.paintUntil(null);
			}
		}
		else
		{	reset(zc);
		}
	}

	public synchronized void reset (ZirkelCanvas zc)
	{	super.reset(zc);
		ZC=zc;
		showStatus(zc);
		if (Running)
		{	Stopped=true;
			zc.paintUntil(null);
		}
		else
		{	Stopped=false;
			new Thread(this).start();
		}
	}

	public void showStatus (ZirkelCanvas zc)
	{	zc.showStatus(
			Zirkel.name("message.animatebreak"));
	}

	public void save (XmlWriter xml)
	{	xml.startTagStart("AnimateBreakpoints"); 
		xml.printArg("time",""+SleepTime);
		if (Loop) xml.printArg("loop","true"); 
		xml.finishTagNewLine(); 
	}
	
	boolean Running=false,Stopped=false;
	long SleepTime=1024;

	public void run ()
	{	ZC.getConstruction().setOriginalOrder(true);
		Running=true;
		Breaks=new MyVector();
		Enumeration e=ZC.getConstruction().elements();
		while (e.hasMoreElements())
		{	ConstructionObject o=(ConstructionObject)e.nextElement();
			if (o.isBreak()) Breaks.addElement(o);
		}
		Object H[]=Breaks.getArray();
		int N=0;
		if (Breaks.size()==0)
		{	Running=false;
			ZC.getConstruction().setOriginalOrder(false);
			return;
		}
		ConstructionObject O=(ConstructionObject)H[0];
		boolean forward=true;
		while (!Stopped)
		{	if (!Stopped) ZC.paintUntil(O);
			try
			{	Thread.sleep(SleepTime); 
			}
			catch (Exception ex) {	}		
			if (Stopped) break;
			if (Loop)
			{	if (forward)
				{	if (N<Breaks.size()-1)
					{	N++;
						O=(ConstructionObject)H[N];					
					}
					else if (N==Breaks.size()-1)
					{	N++;
						forward=false;
						O=null;
					}
				}
				else
				{	if (N>0)
					{	N--;
						O=(ConstructionObject)H[N];
					}
					else
					{	N=1;
						O=(ConstructionObject)H[N];
						forward=true;
					}
				}
			}
			else
			{	if (N<Breaks.size()-1)
				{	N++;
					O=(ConstructionObject)H[N];
				}
				else if (N==Breaks.size()-1)
				{	N++;
					O=null;
				}
				else
				{	N=0;
					O=(ConstructionObject)H[N];
				}
			}
		}
		Running=false;
		ZC.getConstruction().setOriginalOrder(false);
	}

	public synchronized void invalidate (ZirkelCanvas zc)
	{	Stopped=true;
		ZC.paintUntil(null);
	}

	public boolean useSmartBoard ()
	{	return false;
	}
	
	public void increaseSpeed ()
	{	if (SleepTime>=32000) return;
		SleepTime=SleepTime*2;
	}

	public void decreaseSpeed ()
	{	if (SleepTime==1) return;
		SleepTime=SleepTime/2;
	}
	
	public void setSpeed (long delay)
	{	SleepTime=delay;
	}
	
	public void setLoop (boolean flag)
	{	Loop=flag;
	}
}

