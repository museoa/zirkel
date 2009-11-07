/*
 * Created on 31.10.2009
 *
 */
package rene.zirkel.expression;

public class Fraction
{	double MAX=1e+10;
	double MIN=1e-10;
	
	public long n,d;
	public int signum;

	public boolean frac (double v, double error)
	{
		long D, N, t;
		double epsilon,r=0,m;
		int count=0;
		
		if (v<0) { signum=-1; v=-v; }
		else signum=1;
		
		if (v==0) { n=0; d=1; return true; }
	
		if (v < MIN || v > MAX || error < 0.0)
			return false;
		d = D = 1;
		n = (int)v;
		N = n + 1;
	
		while (true)
		{	if (v*d != (double)n)
			{	r = (N - v*D)/(v*d - n);
				if (r <= 1.0)
				{	t = N;
					N = n;
					n = t;
					t = D;
					D = d;
					d = t;
				}
			}
			epsilon = Math.abs(1.0-n/(v*d));
			if (epsilon > error)
			{	m = 1.0;
				do {
					m *= 10.0;
				} while (m*epsilon < 1.0);
				epsilon = 1.0/m * ((int)(0.5 + m*epsilon));
			}
			if (epsilon <= error) return true;
			if (r == 0.0 || count>=1000) break;
			count++;
			if (r <= 1.0) r = 1.0/r;
			N += n*(long)r;
			D += d*(long)r;
			n += N;
			d += D;
		}
		return false;
	}
	
	public static void main (String args[])
	{	Fraction f=new Fraction();
		System.out.println(f.frac(112.0/37.0,1e-10));
		System.out.println(f.n+" "+f.d);
	}

}
