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

import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import rene.util.xml.XmlReader;
import rene.util.xml.XmlTag;
import rene.util.xml.XmlTagPI;
import rene.util.xml.XmlTagText;
import rene.util.xml.XmlTree;
import rene.zirkel.construction.Construction;
import rene.zirkel.construction.ConstructionException;
import rene.zirkel.objects.ConstructionObject;
import rene.zirkel.objects.PointObject;
import rene.zirkel.objects.PrimitiveCircleObject;
import rene.zirkel.objects.PrimitiveLineObject;
import rene.zirkel.objects.SegmentObject;
import rene.zirkel.tools.AnimatorTool;
import rene.zirkel.tools.ObjectTracker;
import rene.zirkel.tools.Tracker;

public class DemoRunner
	implements Runnable, MouseListener
{	ZirkelCanvas ZC;
	ZirkelApplet ZA;
	boolean Stopped=false,Continue=false,Hold=false;
	int delay=10;
	Label L;
	XmlTree Tree;

	public DemoRunner (ZirkelCanvas zc, ZirkelApplet za, String filename, Label label)
	{	ZC=zc; ZA=za; L=label;
		try
		{	URL url;
			if (filename.toUpperCase().startsWith("HTTP"))
				url=new URL(filename);
			else
				url=new URL(ZA.getCodeBase(),filename);
			InputStream in=url.openStream();
			XmlReader xml=new XmlReader();
			xml.init(in);
			XmlTree tree=xml.scan();
			Enumeration e=tree.getContent();
			while (e.hasMoreElements())
			{	tree=(XmlTree)e.nextElement();
				if (tree.getTag() instanceof XmlTagPI) continue;
				if (!tree.getTag().name().equals("Demo"))
					throw new ConstructionException("Demo tag not found");
				else
				{	XmlTag tag=tree.getTag();
					if (tag.hasParam("delay"))
					{	try
						{	delay=Integer.parseInt(tag.getValue("delay"));
						}
						catch (Exception ex) {}
					}
					break;
				}
			}
			Tree=tree;
			e=tree.getContent();
			if (!e.hasMoreElements()) return;
			while (e.hasMoreElements())
			{	tree=(XmlTree)e.nextElement();
				if (!tree.getTag().name().equals("File"))
					throw new ConstructionException("Illegal tag "+
						tree.getTag().name());
			}			
			in.close();
		}
		catch (ConstructionException e)
		{	label.setText(e.toString());
		}	
		catch (Exception e)
		{	label.setText("Error loading "+filename);
		}
		zc.addMouseListener(this);	
		new Thread(this).start();
	}
	
	public void run ()
	{	Enumeration e=Tree.getContent();
		ZC.setFrozen(true);
		while (true)
		{	Continue=false;
			int D=delay;
			if (e.hasMoreElements())
			{	try
				{	XmlTree tree=(XmlTree)e.nextElement();
					XmlTag tag=tree.getTag();
					String filename=tag.getValue("name");
					URL url;
					if (filename.toUpperCase().startsWith("HTTP"))
						url=new URL(filename);
					else
						url=new URL(ZA.getCodeBase(),filename);
					InputStream in=url.openStream();
					ZC.clearMacros();
					ZC.load(in);
					in.close();
					ZC.recompute();
					if (tag.hasParam("delay"))
					{	try
						{	delay=Integer.parseInt(tag.getValue("delay"));
						}
						catch (Exception ex) {}
					}					
					Enumeration en=tree.getContent();
					while (en.hasMoreElements())
					{	tree=(XmlTree)en.nextElement();
						if (tree.getTag() instanceof XmlTagText)
						{	L.setText(((XmlTagText)tree.getTag()).getContent());
						}
					}
					startZC();
				}
				catch (Exception ex)
				{	L.setText("Error loading file!");
				}
				try
				{	for (int i=0; i<delay*2 || Hold; i++)
					{	Thread.sleep(500);
						if (i==0)
						{	ZC.setFrozen(false);
							ZC.repaint();
						}
						if (Stopped) return;
						if (Continue)
						{	Hold=false;
							break;
						}
					}
					ZC.setFrozen(true);
				}
				catch (Exception ex) {}
				delay=D;
			}
			else
			{	e=Tree.getContent();
			}
		}
	}

	AnimatorTool A;
	
	public void startZC ()
	{	Construction C=ZC.getConstruction();
		ZC.setInteractive(false);
		if (C.TrackP!=null)
		{	try
			{	ConstructionObject
					P=C.find(C.TrackP);
				if (!((P instanceof PointObject) 
					|| (P instanceof PrimitiveLineObject)))
						throw new ConstructionException("");				
				ConstructionObject po[]=
					new ConstructionObject[C.TrackPO.size()];
				for (int i=0; i<po.length; i++)
				{	ConstructionObject o=C.find(
						(String)C.TrackPO.elementAt(i));
					if (o==null || !((o instanceof PointObject) 
						|| (o instanceof PrimitiveLineObject)
						|| (o instanceof PointObject)
						))
							throw new ConstructionException("");
					po[i]=o;
				}
				PointObject
					PM=(PointObject)C.find(C.TrackPM);
				if (C.TrackO!=null)
				{	ConstructionObject O=C.find(C.TrackO);
					if (P==null || PM==null || O==null)
						throw new ConstructionException("");
					ObjectTracker ot=new ObjectTracker(P,PM,O,ZC,
						C.Animate,C.Paint,po);
					if (C.Animate) ot.Interactive=false;
					ot.setOmit(C.Omit);
					ZC.setTool(ot);
					ZC.allowRightMouse(false);
					ZC.validate();
					ZC.repaint();
				}
				else
				{	if (P==null)
						throw new ConstructionException("");
					ZC.setTool(new Tracker(P,po));
					if (PM!=null) PM.setSelected(true);
					ZC.validate();
					ZC.repaint();
				}
			}
			catch (Exception e)
			{	e.printStackTrace();	
			}			
		}
		else if (C.AnimateP!=null)
		{	try
			{	PointObject
					P=(PointObject)C.find(C.AnimateP);
				if (P==null || !P.moveable())
					throw new ConstructionException("");
				Enumeration e=C.AnimateV.elements();
				while (e.hasMoreElements())
				{	String s=(String)e.nextElement();
					ConstructionObject o=C.find(s);
					if (o==null || !(o instanceof SegmentObject
						|| o instanceof PrimitiveCircleObject ||
						o instanceof PointObject))
							throw new ConstructionException("");				
				}
				ZC.setTool(A=new AnimatorTool(P,C.AnimateV,ZC,C.AnimateNegative,
						C.AnimateOriginal,C.AnimateDelay));
				ZC.allowRightMouse(false);
				A.setInteractive(false);
			}
			catch (Exception e)
			{	e.printStackTrace();
			}			
		}
		ZC.repaint();
	}

	public void stop ()
	{	Stopped=true;
	}
	
	public void mousePressed (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited (MouseEvent e) {}
	public void mouseClicked (MouseEvent e)
	{	if (e.isMetaDown()) Hold=true;
		else Continue=true;
	}
}
