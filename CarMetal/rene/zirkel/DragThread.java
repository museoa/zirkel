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
 
 
 package rene.zirkel;

import java.awt.event.MouseEvent;

/**
 * @author Rene
 * A thread for reporting drags to the main program, avoiding
 * to many drag messages, so that the program seems to hang.
 */
class DragThread extends Thread
{	MouseEvent EWaiting=null;
	ZirkelCanvas ZC;
	Object Ready=new Object();
	boolean Working=false;
	
	public DragThread (ZirkelCanvas zc)
	{	ZC=zc;
		start();
	}
	
	public void run ()
	{	MouseEvent e;
		while (true)
		{	if (EWaiting==null)
			{	try
				{	synchronized (this)
					{	wait();
					}
				}
				catch (Exception ex) {}
			}
			if (EWaiting!=null)
			{	synchronized (this)
				{	e=EWaiting;
					EWaiting=null;
				}
				Working=true;
				ZC.doMouseDragged(e);
				try
				{	sleep(0);
				}
				catch (Exception ex) {}
				Working=false;
				synchronized (Ready)
				{	Ready.notify();
				}
			}
		}
	}
	
	public synchronized void mouseDragged (MouseEvent e)
	{	EWaiting=e;
		notify();
	}
	
	public void waitReady ()
	{	if (!Working) return;
		synchronized (Ready)
		{	try
			{	Thread.currentThread().wait(1000);
			}
			catch (Exception ex) {}
		}
	}
}


