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
 
 
 package rene.zirkel.structures;

// file: Coordinates.java

public class Coordinates
{	public double X,Y,X1,Y1;
        public boolean join=true;
	public boolean flag=true;
	public int Color=-1,Thickness=-1;
        public Coordinates (double x, double y,boolean j)
	{	X=x; Y=y;join=j;
	}
	public Coordinates (double x, double y)
	{	X=x; Y=y;
	}
	public Coordinates (double x, double y, double x1, double y1)
	{	X=x; Y=y; X1=x1; Y1=y1;
	}
        public Coordinates(){
            X=Double.NaN;Y=Double.NaN;
        }
}
