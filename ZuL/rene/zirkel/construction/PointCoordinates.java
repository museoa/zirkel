/*
 * Created on 09.06.2008 
 * This is to read coordinates from Intergeo files.
 */
package rene.zirkel.construction;

import rene.util.xml.*;

public class PointCoordinates
{	double xre,xim,yre,yim,zre,zim;
	double x,y;
	static double h[]=new double[2];
	boolean homog=false;
	
	public static PointCoordinates read (XmlTree tree)
		throws ConstructionException
	{	PointCoordinates pc=new PointCoordinates();
		for (XmlTree t1 : tree)
		{	if (t1.isTag("vec3d"))
			{	for (XmlTree t : t1)
				{	if (t.isTag("x"))
					{	readValue(t);
						pc.xre=h[0]; pc.xim=h[1];
					}
					else if (t.isTag("y"))
					{	readValue(t);
						pc.yre=h[0]; pc.yim=h[1];
					}
					else if (t.isTag("z"))
					{	readValue(t);
						pc.zre=h[0]; pc.zim=h[1];
					}
					else
						throw new ConstructionException("Illegal vec3d tag "+t.getTag().name());
				}
				pc.homog=true;
			}
			else
				throw new ConstructionException("Illegal coordinate tag "+t1.getTag().name());
		}
		pc.computeXY();
		return pc;
	}
	
	void computeXY ()
	{	if (homog)
		{	double r=zre*zre+zim*zim;
			x=(xre*zre+xim*zim)/r;
			y=(yre*zre+yim*zim)/r;
		}
		else
		{	x=xre; y=yre;
		}
	}
	
	public static void readValue (XmlTree tree)
		throws ConstructionException
	{	h[0]=h[1]=0;
		for (XmlTree t1 : tree)
		{	if (t1.isTag("complex"))
			{	for (XmlTree t : t1)
				{	if (t.isTag("re"))
					{	h[0]=readNumber(t);
					}
					else if (t.isTag("im"))
					{	h[1]=readNumber(t);
					}
					else
						throw new ConstructionException("Illegal tag in complex!");
				}
			}
			else
				throw new ConstructionException("Illegal tag in vec3d!");
		}
	}
	
	public static double readNumber (XmlTree tree)
		throws ConstructionException
	{	try
		{	return Double.parseDouble(tree.getText());
		}
		catch (Exception e)
		{	throw new ConstructionException(tree.getText());
		}
	}
	
	public double getX()
	{	return x;
	}
	public double getY()
	{	return y;
	}
}
