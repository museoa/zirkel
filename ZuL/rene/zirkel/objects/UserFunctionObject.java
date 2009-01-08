package rene.zirkel.objects;

// file: Functionbject.java

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import rene.gui.*;
import rene.util.xml.*;
import rene.zirkel.*;
import rene.zirkel.construction.*;
import rene.zirkel.dialogs.*;
import rene.zirkel.expression.*;
import rene.zirkel.graphics.*;
import rene.dialogs.*;

class UserFunctionEditDialog extends ObjectEditDialog
{	TextField Var,EY,X,Y;
	IconBar IC;
	ZirkelCanvas ZC;
	Checkbox Fixed,Zeros;

	public UserFunctionEditDialog (ZirkelCanvas zc, UserFunctionObject o)
	{	super(zc.getFrame(),Zirkel.name("edit.function.title"),o,"function");
		ZC=zc;
	}
	
	public void addFirst (Panel P)
	{	UserFunctionObject f=(UserFunctionObject)O;
	
		Var=new TextFieldAction(this,"var",""+f.getVar(),30);
		P.add(new MyLabel(Zirkel.name("function.vars"))); P.add(Var);
		EY=new TextFieldAction(this,"ey",""+f.getEY(),30);
		P.add(new MyLabel(Zirkel.name("function.f"))); P.add(EY);
		
		Zeros=new Checkbox(""); 
		P.add(new MyLabel(Zirkel.name("function.drawzeros"))); P.add(Zeros);
		Zeros.setState(f.isDrawZeros());
		
		X=new TextFieldAction(this,"X",""+f.round(f.getX()));
		P.add(new MyLabel(Zirkel.name("edit.point.x"))); P.add(X);
		Y=new TextFieldAction(this,"Y",""+f.round(f.getY()));
		P.add(new MyLabel(Zirkel.name("edit.point.y"))); P.add(Y);
		
		Fixed=new Checkbox("");
		Fixed.setState(f.fixed());
		P.add(new MyLabel(Zirkel.name("edit.fixed")));
		P.add(Fixed);
		if (f.fixed())
		{	X.setText(f.getEXpos());
			Y.setText(f.getEYpos());
		}

	}
	
	public void setAction ()
	{	UserFunctionObject f=(UserFunctionObject)O;
		f.setExpressions(Var.getText(),EY.getText());		
		try
		{	double x=new Double(X.getText()).doubleValue();
			double y=new Double(Y.getText()).doubleValue();
			f.move(x,y);
		}
		catch (Exception e) {}
		f.setDrawZeros(Zeros.getState());
		if (Fixed.getState()==true)	f.setFixed(X.getText(),Y.getText());
		else
		{	try
			{	double x=new Expression(X.getText(),
					f.getConstruction(),f).getValue();
				double y=new Expression(Y.getText(),
					f.getConstruction(),f).getValue();
				f.move(x,y);
				f.setFixed(false);
			}
			catch (Exception e) {}
		}
	}
	
	public void focusGained (FocusEvent e)
	{	Var.requestFocus();
	}
	
}

/**
 * @author Rene
 * 
 * This class is for function of several variables. Those
 * functions cannot be drawn at all.
 */
public class UserFunctionObject extends ConstructionObject
	implements MoveableObject, Evaluator
{	Expression EY=null;
	double X[]={0};
	String Var[]={"x y"};
	protected double Xpos,Ypos;
	protected boolean Fixed;
	protected Expression EXpos,EYpos;
	boolean DrawZeros;

	public UserFunctionObject (Construction c)
	{	super(c);
		validate();
		updateText();
	}
	
	public String getTag () { return "Function"; }
	public int getN () { return N.next(); }
	
	public void updateText ()
	{	setText(getDisplayValue());
	}
	
	public void validate ()
	{	if (EY!=null)
		{	Valid=EY.isValid();
		}
		else Valid=false;	
		if (Fixed && EXpos!=null && EXpos.isValid())
		
		{	try
			{	Xpos=EXpos.getValue();
			}
			catch (Exception e) { Valid=false; return; }
		}
		if (Fixed && EYpos!=null && EYpos.isValid())
		{	try
			{	Ypos=EYpos.getValue();
			}
			catch (Exception e) { Valid=false; return; }
		}
	}

	public void setExpressions (String t, String ey)
	{	StringTokenizer tok=new StringTokenizer(t);
		Var=new String[tok.countTokens()];
		X=new double[tok.countTokens()];
		int i=0;
		while (tok.hasMoreTokens())
		{	Var[i++]=tok.nextToken();
		}
		EY=new Expression(ey,getConstruction(),this,Var);
		validate();
	}

	public String getEY ()
	{	if (EY!=null) return EY.toString();
		else return "0";
	}

	double C,R,W,H;
	double x[],y[],z[][];
	int xn=20,yn=20;
	
	/**
	 * This function assumes that the values on the ends of a segment have
	 * opposite signs.
	 * Then it will compute the value on a point in the segment and compute
	 * the zero of the parabola. This is returned as new point on the segment
	 * @param l1 is the convex combination for the zero on the segment
	 * @return l the new convex combination.
	 */
	public double fix (double x1, double y1, double z1, 
			double x2, double y2, double z2, 
			double l1)
	{	try
		{	double z=evaluateF(x1*l1+x2*(1-l1),y1*l1+y2*(1-l1));
			if (Math.abs(z)>(Math.abs(z1)+Math.abs(z2))*1e-5)
			{	double mu=1-l1,mu2=mu*mu,mu3=mu2*mu,mu4=mu3*mu;
				double h=Math.sqrt(mu4*z2*z2+((-2*mu4+4*mu3-2*mu2)*z1-2*mu2*z)*z2
					+(mu4-4*mu3+6*mu2-4*mu+1)*z1*z1+(-2*mu2+4*mu-2)*z*z1+z*z);
				double h1=(mu2*z2-mu2*z1+z1-z+h)/(2*(mu*z2-mu*z1+z1-z));
				double h2=(mu2*z2-mu2*z1+z1-z-h)/(2*(mu*z2-mu*z1+z1-z));
				if (h1>=0 && h1<=1)	l1=1-h1;
				else if (h2>=0 && h2<=1) l1=1-h2;
			}
		}
		catch (Exception e) {}
		return l1;
	}
	
	/**
	 * Draw the zeros of the function in two dimensions on a triangle.
	 * Given are the corners and the values at the corner.
	 * The algorithms checks, if two of the corners have opposite sign.
	 * In this case, an interpolation step (fix function) is applied to
	 * determine the exact location of the zero on this side of the triangle.
	 * The function draws a line connecting two zeros on the sides.
	 */
	public void drawZeros (MyGraphics g, ZirkelCanvas zc,
			double x1, double y1, double z1, 
			double x2, double y2, double z2, 
			double x3, double y3, double z3)
	{	if (z1*z2<0)
		{	double l1=z2/(z2-z1);
			// l1=fix(x1,y1,z1,x2,y2,z2,l1);
			double m1=1-l1;
			if (z1*z3<0)
			{	double l2=z3/(z3-z1);
				// l2=fix(x1,y1,z1,x3,y3,z3,l2);
				double m2=1-l2;
				g.drawLine(zc.col(l1*x1+m1*x2),zc.row(l1*y1+m1*y2),
						zc.col(l2*x1+m2*x3),zc.row(l2*y1+m2*y3));
			}
			else
			{	double l2=z3/(z3-z2);
				// l2=fix(x2,y2,z2,x3,y3,z3,l2);
				double m2=1-l2;
				g.drawLine(zc.col(l1*x1+m1*x2),zc.row(l1*y1+m1*y2),
						zc.col(l2*x2+m2*x3),zc.row(l2*y2+m2*y3));
			}				
		}
		else if (z1*z3<0)
		{	double l1=z3/(z3-z1);
			// l1=fix(x1,y1,z1,x3,y3,z3,l1);
			double m1=1-l1;
			double l2=z3/(z3-z2);
			// l2=fix(x2,y2,z2,x3,y3,z3,l2);
			double m2=1-l2;
			g.drawLine(zc.col(l1*x1+m1*x3),zc.row(l1*y1+m1*y3),
					zc.col(l2*x2+m2*x3),zc.row(l2*y2+m2*y3));
		}
	}

	public void drawZeros (MyGraphics g, ZirkelCanvas zc,
			double x1, double y1, double z1, 
			double x2, double y2, double z2, 
			double x3, double y3, double z3,
			int level)
	{	if (level==0)
		{	drawZeros(g,zc,x1,y1,z1,x2,y2,z2,x3,y3,z3);
		}
		else
		{	if (z1*z2<=0 || z2*z3<=0)
			{	double x12=(x1+x2)/2,y12=(y1+y2)/2;
				double x23=(x2+x3)/2,y23=(y2+y3)/2;
				double x13=(x1+x3)/2,y13=(y1+y3)/2;
				double x1l=x1+(x2-x3)/2,y1l=y1+(y2-y3)/2;
				double x1r=x1+(x3-x2)/2,y1r=y1+(y3-y2)/2;
				double x2l=x2+(x3-x1)/2,y2l=y2+(y3-y1)/2;
				double x2r=x2+(x1-x3)/2,y2r=y2+(y1-y3)/2;
				double x3l=x3+(x1-x2)/2,y3l=y3+(y1-y2)/2;
				double x3r=x3+(x2-x1)/2,y3r=y3+(y2-y1)/2;
				try
				{	double z12=evaluateF(x12,y12);
					double z23=evaluateF(x23,y23);
					double z13=evaluateF(x13,y13);
					drawZeros(g,zc,x1,y1,z1,x13,y13,z13,x12,y12,z12,level-1);
					drawZeros(g,zc,x2,y2,z2,x12,y12,z12,x23,y23,z23,level-1);
					drawZeros(g,zc,x3,y3,z3,x23,y23,z23,x13,y13,z13,level-1);
					drawZeros(g,zc,x12,y12,z12,x13,y13,z13,x23,y23,z23,level-1);
					if (z2*z1<=0)
					{	double z1l=evaluateF(x1l,y1l);
						drawZeros(g,zc,x1l,y1l,z1l,x1,y1,z1,x12,y12,z12,level-1);
						double z2r=evaluateF(x2r,y2r);
						drawZeros(g,zc,x2r,y2r,z2r,x12,y12,z12,x1,y1,z1,level-1);
					}
					if (z3*z1<=0)
					{	double z1r=evaluateF(x1r,y1r);
						drawZeros(g,zc,x1,y1,z1,x1r,y1r,z1r,x13,y13,z13,level-1);
						double z3l=evaluateF(x3l,y3l);
						drawZeros(g,zc,x13,y13,z13,x3l,y3l,z3l,x3,y3,z3,level-1);
					}
					if (z3*z2<=0)
					{	double z2l=evaluateF(x2l,y2l);
						drawZeros(g,zc,x2,y2,z2,x23,y23,z23,x2l,y2l,z2l,level-1);
						double z3r=evaluateF(x3r,y3r);
						drawZeros(g,zc,x23,y23,z23,x3,y3,z3,x3r,y3r,z3r,level-1);
					}
				}
				catch (Exception e) {}
			}
		}
	}

	
	double xv[]=new double[2];
	
	public void paint (MyGraphics g, ZirkelCanvas zc)
	{	if (!Valid || mustHide(zc)) return;
		int steps=50;
		if (DrawZeros && X.length==2)
		{	double xmin=zc.Xmin,xmax=zc.Xmin+zc.DX;
			double ymin=zc.Ymin,ymax=zc.Ymin+zc.DY;
			double dx=(xmax-xmin)/steps;
			double dy=dx*Math.sqrt(3)/2;
			xn=(int)((xmax-xmin)/dx)+2;
			yn=(int)((ymax-ymin)/dy)+1;
			if (x==null || x.length!=xn || y.length!=yn)
			{	x=new double[xn+1]; y=new double[yn+1];
				z=new double[xn+1][yn+1];
			}
			for (int i=0; i<=xn; i++) x[i]=xmin-dx+i*dx;
			for (int j=0; j<=yn; j++) y[j]=ymin+j*dy;
			for (int i=0; i<=xn; i++)
				for (int j=0; j<=yn; j++)
				{	xv[0]=x[i]; xv[1]=y[j];
					if (j%2==0) xv[0]+=dx/2;
					try
					{	z[i][j]=evaluateF(xv);
						//g.setColor(Color.red);
						//g.drawLine(zc.col(xv[0]),zc.row(xv[1]),zc.col(xv[0]),zc.row(xv[1]));
					}
					catch (Exception e)
					{	z[i][j]=1;
					}
				}
			g.setColor(this);
			int maxlevel=2;
			for (int i=0; i<=xn-1; i++)
				for (int j=0; j<=yn-1; j++)
				{	// check, which kind of triangle we have:
					if (j%2==0) 
					{	drawZeros(g,zc,x[i]+dx/2,y[j],z[i][j],
							x[i],y[j+1],z[i][j+1],x[i+1],y[j+1],z[i+1][j+1],maxlevel);
						drawZeros(g,zc,x[i]+dx/2,y[j],z[i][j],
								x[i+1],y[j+1],z[i+1][j+1],x[i+1]+dx/2,y[j],z[i+1][j],maxlevel);
					}
					else
					{	drawZeros(g,zc,x[i],y[j],z[i][j],
							x[i]+dx/2,y[j+1],z[i][j+1],x[i+1],y[j],z[i+1][j],maxlevel);
						drawZeros(g,zc,x[i+1],y[j],z[i+1][j],
								x[i+1]+dx/2,y[j+1],z[i+1][j+1],x[i+1]-dx/2,y[j+1],z[i][j+1],maxlevel);
					}
				}
		}
		FontMetrics fm=g.getFontMetrics();
		W=H=fm.getHeight();
		C=zc.col(Xpos); R=zc.row(Ypos);
		g.setColor(this);
		setFont(g);
		g.drawString(AngleObject.translateToUnicode(getDisplayValue()),C,R);
		R-=H;
	}
	
	public double getValue ()
		throws ConstructionException
	{	if (!Valid) throw new InvalidException("exception.invalid");
		return X[0];
	}
	
	public double getValue (String var)
		throws ConstructionException
	{	if (!Valid) throw new InvalidException("exception.invalid");
		for (int i=0; i<Var.length; i++)
			if (var.equals(Var[i])) return X[i];
		return X[0];
	}

	public String getDisplayValue ()
	{	String s="";
		if (showName())
		{	if (getAlias()!=null) s=getAlias()+" : ";
			s=s+getName()+"("+Var[0];
			for (int i=1; i<Var.length; i++) s=s+","+Var[i];
			s=s+")";
			if (showValue()) s=s+"=";
		}
		if (showValue()) s=s+((EY==null)?"":EY.toString());
		return s; 
	}
	
	public boolean check (double size, double c, double r, 
			double c1, double r1, 
			double c2, double r2)
	{	if (Math.abs(r-(r1+r2)/2)+Math.abs(c-(c1+c2)/2)<2*size) return true;
		return false;
	}
	
	public boolean nearto (ZirkelCanvas zc, int cc, int rr,
			double x1, double y1, double z1, 
			double x2, double y2, double z2, 
			double x3, double y3, double z3)
	{	int size=(int)zc.selectionSize();
		if (z1*z2<0)
		{	double l1=z2/(z2-z1);
			l1=fix(x1,y1,z1,x2,y2,z2,l1);
			double m1=1-l1;
			if (z1*z3<0)
			{	double l2=z3/(z3-z1);
				l2=fix(x1,y1,z1,x3,y3,z3,l2);
				double m2=1-l2;
				return check(size,cc,rr,zc.col(l1*x1+m1*x2),zc.row(l1*y1+m1*y2),
						zc.col(l2*x1+m2*x3),zc.row(l2*y1+m2*y3));
			}
			else
			{	double l2=z3/(z3-z2);
				l2=fix(x2,y2,z2,x3,y3,z3,l2);
				double m2=1-l2;
				return check(size,cc,rr,zc.col(l1*x1+m1*x2),zc.row(l1*y1+m1*y2),
						zc.col(l2*x2+m2*x3),zc.row(l2*y2+m2*y3));
			}				
		}
		else if (z1*z3<0)
		{	double l1=z3/(z3-z1);
			l1=fix(x1,y1,z1,x3,y3,z3,l1);
			double m1=1-l1;
			double l2=z3/(z3-z2);
			l2=fix(x2,y2,z2,x3,y3,z3,l2);
			double m2=1-l2;
			return check(size,cc,rr,zc.col(l1*x1+m1*x3),zc.row(l1*y1+m1*y3),
					zc.col(l2*x2+m2*x3),zc.row(l2*y2+m2*y3));
		}
		return false;
	}
	
	public boolean nearto (int cc, int rr, ZirkelCanvas zc)
	{	if (!displays(zc)) return false;
		if (C<=cc && R<=rr && cc<=C+W && rr<=R+H) return true;
		if (DrawZeros && X.length==2 && z!=null)
		{	double xmin=zc.Xmin,xmax=zc.Xmin+2*zc.DX;
			double ymin=zc.Ymin,ymax=zc.Ymin+2*zc.DY;
			double dx=(xmax-xmin)/50;
			double dy=dx*Math.sqrt(3)/2;
			for (int i=0; i<=xn-1; i++)
				for (int j=0; j<=yn-1; j++)
				{	if (j%2==0) 
					{	if (nearto(zc,cc,rr,x[i]+dx/2,y[j],z[i][j],
							x[i],y[j+1],z[i][j+1],x[i+1],y[j+1],z[i+1][j+1])) 
								return true;
						if (nearto(zc,cc,rr,x[i]+dx/2,y[j],z[i][j],
								x[i+1]+dx/2,y[j],z[i+1][j],x[i+1],y[j+1],z[i+1][j+1])) 
									return true;
					}
					else
					{	if (nearto(zc,cc,rr,x[i],y[j],z[i][j],
							x[i+1],y[j],z[i+1][j],x[i]+dx/2,y[j+1],z[i][j+1])) 
								return true;
						if (nearto(zc,cc,rr,x[i+1],y[j],z[i+1][j],
								x[i+1]-dx/2,y[j+1],z[i][j+1],x[i+1]+dx/2,y[j+1],z[i+1][j+1])) 
									return true;
					}
				}
		}
		return false;
	}
	
	public boolean EditAborted;
		
	public void edit (ZirkelCanvas zc)
	{	ObjectEditDialog d;
		while (true)
		{	d=new UserFunctionEditDialog(zc,this);
			d.setVisible(true);
			EditAborted=false;
			if (d.isAborted())
			{	EditAborted=true;
				break;
			}
			else if (!EY.isValid())
			{	Frame F=zc.getFrame();
				Warning w=new Warning(F,EY.getErrorText(),
					Zirkel.name("warning"),true);
				w.center(F);
				w.setVisible(true);
			}
			if ((EXpos!=null && !EXpos.isValid()))
			{	Frame F=zc.getFrame();
				Warning w=new Warning(F,EXpos.getErrorText(),
					Zirkel.name("warning"),true);
				w.center(F);
				w.setVisible(true);
			}
			else if ((EYpos!=null && !EYpos.isValid()))
			{	Frame F=zc.getFrame();
				Warning w=new Warning(F,EYpos.getErrorText(),
					Zirkel.name("warning"),true);
				w.center(F);
				w.setVisible(true);
			}
			else break;
		}
		validate();
		updateText();
		zc.getConstruction().updateCircleDep();
		zc.repaint();
		if (d.wantsMore())
		{	new EditConditionals(zc.getFrame(),this);
			validate();
		}
	}

	public void printArgs (XmlWriter xml)
	{	xml.printArg("f",EY.toString());
		if (DrawZeros) xml.printArg("drawzeros","true");
		if (Fixed && EXpos!=null && EXpos.isValid()) xml.printArg("x",EXpos.toString());
		else xml.printArg("x",""+Xpos);
		if (Fixed && EYpos!=null && EYpos.isValid()) xml.printArg("y",EYpos.toString());
		else xml.printArg("y",""+Ypos);	
		if (Fixed) xml.printArg("fixed","true");
		xml.printArg("var",getVar());
	}
	
	public void translate ()
	{	try
		{	EY=new Expression(EY.toString(),getConstruction(),this,Var);
			ConstructionObject O=getTranslation();
			setTranslation(this);
			EY.translate();
			if (Fixed)
			{	try
				{	setFixed(EXpos.toString(),EYpos.toString());
					EXpos.translate(); EYpos.translate();
				}
				catch (Exception e) {}
			}
			validate();
			setTranslation(O);
		}
		catch (Exception e)
		{ 	System.out.println();
			System.out.println(getName());
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void setFixed (boolean flag)
	{	Fixed=flag; 
		if (!Fixed) EXpos=EYpos=null;
		updateText();
	}
	
	public void setFixed (String x, String y)
	{	Fixed=true;
		EXpos=new Expression(x,getConstruction(),this);
		EYpos=new Expression(y,getConstruction(),this);
		updateText();
	}
	
	public boolean fixed () { return Fixed; }

	public String getEXpos ()
	{	if (EXpos!=null) return EXpos.toString();
		else return ""+round(Xpos);
	}

	public String getEYpos ()
	{	if (EYpos!=null) return EYpos.toString();
		else return ""+round(Ypos);
	}


	public boolean onlynearto (int x, int y, ZirkelCanvas zc)
	{	return false;
		//return nearto(x,y,zc);
	}

	public boolean equals (ConstructionObject o)
	{	return false;
	}

	public Enumeration depending ()
	{	DL.reset();
		addDepending(EY);
		if (Fixed)
		{	addDepending(EXpos);
			addDepending(EYpos);
		}
		return DL.elements();
	}
	
	public void addDepending (Expression E)
	{	if (E!=null)
		{	Enumeration e=E.getDepList().elements();
			while (e.hasMoreElements())
			{	DL.add((ConstructionObject)e.nextElement());
			}
		}
	}

	public boolean hasUnit ()
	{	return false;
	}
	
	public double evaluateF (double x, double y)
		throws ConstructionException
	{	xv[0]=x; xv[1]=y; 
		return evaluateF(xv);
	}
	
	public double evaluateF (double x[])
		throws ConstructionException
	{	int n=x.length;
		if (n>X.length) n=X.length;
		for (int i=0; i<n; i++) X[i]=x[i];
		for (int i=n; i<X.length; i++) X[i]=0;
		try
		{	return EY.getValue();
		}
		catch (Exception e)
		{	throw new ConstructionException("");
		}
	}

	public double evaluateF (double x)
		throws ConstructionException
	{	X[0]=x;
		for (int i=1; i<X.length; i++) X[i]=0;
		try
		{	return EY.getValue();
		}
		catch (Exception e)
		{	throw new ConstructionException("");
		}
	}

	public boolean maybeTransparent ()
	{	return true;
	}

	public boolean canDisplayName ()
	{	return true;
	}
	
	public boolean isFilledForSelect ()
	{	return false;
	}
	
	public String getVar ()
	{	String vars=Var[0];
		for (int i=1; i<Var.length; i++) vars=vars+" "+Var[i];
		return vars;
	}

	public boolean dragTo(double x, double y) 
	{	move(oldx+(x-startx),oldy+(y-starty));
		return true;
	}

	public void move (double x, double y) 
	{	Xpos=x; Ypos=y;
	}

	double oldx,oldy,startx,starty;
	
	public boolean moveable() 
	{	return !Fixed;
	}

	public boolean startDrag(double x, double y) 
	{	oldx=Xpos; oldy=Ypos;
		startx=x; starty=y;
		return true;
	}

	public double getX () { return Xpos; }
	public double getY () { return Ypos; }

	public boolean canFillBackground ()
	{	return false;
	}

	public boolean isDrawZeros() {
		return DrawZeros;
	}

	public void setDrawZeros (boolean drawZeros) 
	{	DrawZeros = drawZeros;
		if (X.length!=2 && EY!=null && drawZeros)
		{	setExpressions("x y",EY.toString());	
		}
	}
}
