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
 
 
 package rene.util;
public class SimpleStringBuffer
{	private int Size,N;
	private char Buf[];
	public SimpleStringBuffer (int size)
	{	Size=size;
		Buf=new char[size];
		N=0;
	}
	public SimpleStringBuffer (char b[])
	{	Size=b.length;
		Buf=b;
		N=0;
	}
	public void append (char c)
	{	if (N<Size) Buf[N++]=c;
		else
		{	Size=2*Size;
			char NewBuf[]=new char[Size];
			for (int i=0; i<N; i++) NewBuf[i]=Buf[i];
			Buf=NewBuf;
			Buf[N++]=c;
		}
	}
	public void append (String s)
	{	int n=s.length();
		for (int i=0; i<n; i++) append(s.charAt(i));
	}
	public void clear ()
	{	N=0;
	}
	public String toString ()
	{	if (N==0) return "";
		return new String(Buf,0,N);
	}
}

