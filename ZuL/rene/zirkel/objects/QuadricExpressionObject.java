package rene.zirkel.objects;

// file: QuadricObject.java

import java.awt.*;
import java.util.*;

import rene.gui.*;
import rene.util.xml.*;
import rene.zirkel.*;
import rene.zirkel.construction.*;
import rene.zirkel.dialogs.*;
import rene.zirkel.expression.*;
import rene.zirkel.graphics.*;

class QuadricExpressionEditDialog extends ObjectEditDialog
{	TextFieldAction A;
	
	public QuadricExpressionEditDialog (Frame f, String title, QuadricExpressionObject o)
	{	super(f,Zirkel.name("edit.point.title"),o);
	}

	public void addFirst (Panel P)
	{	QuadricExpressionObject Q=(QuadricExpressionObject)O;
		
		A=new TextFieldAction(this,"a",Q.getExpression(),30);
		P.add(new MyLabel(Zirkel.name("edit.quadricexpression"))); P.add(A);
	}
	
	public void setAction ()
	{	QuadricExpressionObject Q=(QuadricExpressionObject)O;
		Q.setExpression(A.getText());
		Q.validate();
	}
}

public class QuadricExpressionObject extends ConstructionObject
	implements PointonObject, MoveableObject
{	Expression E;
	static Count N=new Count();
	double X[]=new double[6];
	static String Var[]={"x","y"};
	double VX[]=new double[2];
	
	public QuadricExpressionObject (Construction c)
	{	super(c);
		validate();
		E=new Expression("x^2+y^2-1",c,this,Var);
		updateText();
	}
	
	public void setExpression (String s)
	{	E=new Expression(s,getConstruction(),this,Var);
		updateText();
	}

	public String getTag () { return "Quadric"; }
	public int getN () { return N.next(); }
	
	public void updateText ()
	{	try
		{	setText(E.toString()+"=1");
		} 
		catch (Exception e) {}
	}
	
	static double x[]={-1,1,-2,2,-1,1}; 
	static double y[]={-1,-2,0,0,1,2};
	static double A[][]={	
	{-2.0/9,   1.0/18,   11.0/36,   1.0/36,   -2.0/9,   1.0/18}, 
	{-1.0/6,    1.0/6,    1.0/6,   -1.0/6,   -1.0/6,    1.0/6}, 
	{0,     0,   -1.0/4,    1.0/4,     0,     0}, 
	{-1.0/4,   -1.0/8,     0,     0,    1.0/4,    1.0/8}, 
	{1.0/4,   -1.0/8,     0,     0,   -1.0/4,    1.0/8}, 
	{8.0/9,   -2.0/9,  -13.0/18,   7.0/18,    8.0/9,   -2.0/9}};
	
	static double t[]=new double[6];
	
	public void validate ()
	{	Valid=true;
		try
		{	for (int i=0; i<6; i++) t[i]=evaluateF(x[i],y[i]);
			for (int i=0; i<6; i++)
			{	X[i]=0;
				for (int j=0; j<6; j++) X[i]+=A[i][j]*t[j];
			}
		}
		catch (Exception e)
		{	Valid=false; return;
		}
	}
	
	public void paint (MyGraphics g, ZirkelCanvas zc)
	{	if (!Valid || mustHide(zc)) return;
		g.setColor(this);
		// Draw the lower part of the quadric (minus the root):
		double start=zc.minX(),x=start;
		double end=zc.maxX();
		double h=zc.dx(1);
		boolean valid=false,ptext=false;
		int c0=0,r0=0,ctext=20,rtext=20;
		PolygonDrawer pd=new PolygonDrawer(g,this);
		// Draw the lower part of the quadric (plus the root):
		while (x<=end)
		{	try
			{	double y=computeLower(x);
				int c=(int)zc.col(x),r=(int)zc.row(y);
				if (valid)
				{	pd.drawTo(c,r);
					if (!ptext && r0-r>c-c0 && zc.isInside(x,y))
					{	ctext=c; rtext=r; ptext=true; 
					}
				}
				else pd.startPolygon(c,r);
				c0=c; r0=r;
				valid=true;
			}
			catch (RuntimeException e)
			{	valid=false;
			}
			x+=h;
		}
		pd.finishPolygon();
		// Draw the upper part of the quadric (plus the root):
		x=start-2*h;
		valid=false;
		while (x<=end+2*h)
		{	try
			{	double y=computeUpper(x);
				int c=(int)zc.col(x),r=(int)zc.row(y);
				if (valid)
				{	pd.drawTo(c,r);
					// Try to find a position for the label:
					if (!ptext && r0-r>c-c0 && zc.isInside(x,y))
					{	ctext=c; rtext=r; ptext=true; 
					}
				}
				else // left edge of quadric, connect with lower part
				{	try
					{	double y1=computeLower(x);
						if (x>=start-h && x<=end+h)
							g.drawLine(c,zc.row(y1),c,r,this);
					}
					catch (RuntimeException e) {}
					pd.startPolygon(c,r);
				}
				c0=c; r0=r;
				valid=true;
			}
			catch (RuntimeException e) // no points in that range
			{	if (valid) // we just left the right edge of the quadric
				{	try
					{	double y1=computeLower(x-h);
						if (x-h>=start-h && x-h<=end+h)
							g.drawLine(c0,zc.row(y1),c0,r0,this);
					}
					catch (RuntimeException ex) {}
				}
				valid=false;
			}
			x+=h;
		}
		pd.finishPolygon();
		String s=getDisplayText();
		if (!s.equals(""))
		{	g.setLabelColor(this);
			setFont(g);
			DisplaysText=true;
			TX1=ctext+zc.col(XcOffset)-zc.col(0);
			TY1=rtext+zc.row(YcOffset)-zc.row(0);
			drawLabel(g,s);
		}
	}
	
	static public String Tags[]={"x^2","y^2","x","y","xy"};
	
	public String getDisplayValue ()
	{	String s="";
		for (int i=0; i<5; i++)
			s=s+helpDisplayValue(i==0,-X[i],Tags[i]);
		return s+"="+roundDisplay(X[5]);
	}
	
	public String getEquation ()
	{	return getDisplayValue();
	}
	
	public boolean nearto (int cc, int rr, ZirkelCanvas zc)
	{	if (!displays(zc)) return false;
		int size=(int)zc.selectionSize();
		double start=zc.minX(),x=start;
		double end=zc.maxX();
		double h=zc.dx(1);
		while (x<=end)
		{	try
			{	double y=computeUpper(x);
				double c=zc.col(x),r=zc.row(y);
				if (Math.abs(cc-c)<=size*3/2 && Math.abs(rr-r)<=size*3/2)
					return true;
			}
			catch (Exception e) {}
			try
			{	double y=computeLower(x);
				double c=zc.col(x),r=zc.row(y);
				if (Math.abs(cc-c)<=size*3/2 && Math.abs(rr-r)<=size*3/2)
					return true;
			}
			catch (Exception e) {}
			x+=h;
		}
		return false;
	}
	
	public boolean EditAborted;		
		
	public void edit (ZirkelCanvas zc)
	{	QuadricExpressionEditDialog d=new QuadricExpressionEditDialog(zc.getFrame(),"",this);
		while (true)
		{	d.setVisible(true);
			EditAborted=false;
			if (d.isAborted())
			{	EditAborted=true;
				break;
			}
			boolean error=false;
			if (!E.isValid())
			{	error=true; break;
			}
			if (!error) break;
		}
		zc.repaint();
		if (d.wantsMore())
		{	new EditConditionals(zc.getFrame(),this);
			validate();
		}
	}

	double computeUpper (double x)
	{	if (Math.abs(X[1])>1e-13)
		{	double p=(X[3]+x*X[4])/X[1],q=(X[0]*x*x+X[2]*x+X[5])/X[1];
			double h=p*p/4-q;
			if (h<0) throw new RuntimeException("");
			return -p/2+Math.sqrt(h);
		}
		else
		{	return -(X[0]*x*x+X[2]*x+X[5])/(X[3]+X[4]*x);
		}
	}
	
	double computeLower (double x)
	{	if (Math.abs(X[1])>1e-13)
		{	double p=(X[3]+x*X[4])/X[1],q=(X[0]*x*x+X[2]*x+X[5])/X[1];
			double h=p*p/4-q;
			if (h<0) throw new RuntimeException("");
			return -p/2-Math.sqrt(h);
		}
		else throw new RuntimeException("");
	}
	
	public void printArgs (XmlWriter xml)
	{	xml.printArg("expr",E.toString());
	}

	public Enumeration depending ()
	{	super.depending();
		DL.reset();
		addDepending(E);
		return DL.elements();		
	}

	public void translate ()
	{	X=new double[6];
		super.translate();
		try
		{	setExpression(E.toString());
			setTranslation(this);
			E.translate();
		}
		catch (Exception e) {}
	}

	public boolean onlynearto (int x, int y, ZirkelCanvas zc)
	{	return false;
	}

	public boolean equals (ConstructionObject o)
	{	if (!(o instanceof QuadricObject) || !o.valid()) return false;
		try
		{	for (int i=0; i<6; i++)
			{	if (!equals(X[i],((QuadricObject)o).X[i]))
					return false;
			}
		}
		catch (RuntimeException e)
		{	return false;
		}
		return true;
	}
	
	public boolean hasUnit ()
	{	return false;
	}

	public void project (PointObject P) 
	{	double a=X[0],b=X[1],c=X[2],d=X[3],e=X[4],r=X[5];
		double xc=P.getX(),yc=P.getY();
		if (Math.abs(a*xc*xc+b*yc*yc+c*xc+d*yc+e*xc*yc+r)<1e-13) // close enough
			return;
		double t[]=new double[5],s[]=new double[5],si[]=new double[5];
		// Coefficients for fourth order polynomial for lambda (Lagrange factor)
		// Minimize (x-xc)^2+(y-yc)^2 with a*x^2+b*y^2+c*x+d*y+e*x*y+r=0
		// Computed with Maple
		t[0] = a*e*e*d*d-4*a*b*b*c*c+4*a*e*d*b*c-4*b*a*a*d*d+b*c*c*e*e
			-c*Math.pow(e,3)*d+r*Math.pow(e,4)-8*r*e*e*b*a+16*r*b*b*a*a;
		t[1] = 8*b*b*c*c+8*a*a*d*d-8*e*d*b*c-8*a*d*c*e+8*r*e*e*b+8*a*b*c*c
			+8*b*a*d*d+8*r*e*e*a-32*r*b*b*a-32*r*b*a*a;
		t[2] = 12*e*d*c+16*r*b*b-4*b*d*d-8*r*e*e+4*e*e*d*yc+16*b*b*xc*c-16*b*c*c
			-16*a*d*d-4*a*c*c+16*r*a*a+16*a*a*d*yc+4*xc*e*e*c-8*e*d*b*xc-8*e*yc*b*c
			-8*a*d*xc*e-8*a*yc*c*e+16*a*b*b*xc*xc-4*a*e*e*yc*yc+16*b*a*a*yc*yc
			-4*b*xc*xc*e*e+4*Math.pow(e,3)*yc*xc+64*r*b*a-16*a*b*xc*e*yc;
		t[3] = -32*r*b+8*d*d+8*c*c+16*e*d*xc+8*e*e*yc*yc+8*xc*xc*e*e-32*r*a
			-32*b*xc*c+16*e*yc*c-32*a*d*yc-32*a*b*xc*xc-32*b*a*yc*yc;
		t[4] = 16*b*yc*yc+16*d*yc+16*c*xc+16*xc*e*yc+16*r+16*a*xc*xc;
		int k=Quartic.solve(t,s);
		// System.out.println(k+"Solutions found.");
		double dmin=1e30,xmin=xc,ymin=yc;
		for (int i=0; i<k; i++) // Choose closest solution of Lagrange equation
		{	double l=s[i];
			// Solve for x,y when lambda is known.
			// Computed with Maple
			double px = -(-e*d+4*b*l*xc-2*e*l*yc-4*l*l*xc+2*b*c-2*l*c)/(-e*e+4*b*a-4*b*l-4*l*a+4*l*l);
			double py = -(2*a*d+4*a*l*yc-2*l*d-4*l*l*yc-2*l*xc*e-c*e)/(-e*e+4*b*a-4*b*l-4*l*a+4*l*l);
			double dist=(px-xc)*(px-xc)+(py-yc)*(py-yc);
			if (dist<dmin)
			{	dmin=dist; xmin=px; ymin=py;
			}
		}
		P.move(xmin,ymin);
	}

	public void project (PointObject P, double alpha) 
	{	project(P);
	}

	public boolean dragTo (double x, double y) 
	{	return false;
	}

	public void move (double x, double y) 
	{
	}

	public boolean moveable () 
	{	return false;
	}
	
	double xd[],yd[],x1,y1;

	public boolean startDrag (double x, double y) 
	{	return false;
	}

	public boolean canInteresectWith(ConstructionObject o) 
	{	return true;
	}

	public boolean canFillBackground ()
	{	return false;
	}

	public boolean canBeReplacedBy (ConstructionObject o)
	{	return o instanceof QuadricObject;
	}
	
	public String getExpression ()
	{	return E.toString(); 
	}
	
	public double getValue (String var)
		throws ConstructionException
	{	if (!Valid) throw new InvalidException("exception.invalid");
		for (int i=0; i<Var.length; i++)
			if (var.equals(Var[i])) return VX[i];
		return VX[0];
	}

	public double evaluateF (double x, double y)
		throws ConstructionException
	{	VX[0]=x; VX[1]=y;
		try
		{	return E.getValue();
		}
		catch (Exception e)
		{	throw new ConstructionException("");
		}
	}

}
