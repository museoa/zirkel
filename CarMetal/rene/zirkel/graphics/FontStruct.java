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
 
 
 package rene.zirkel.graphics;

import java.awt.Font;

/**
 * @author Rene
 * An internal class to hold a font sructure (size, face
 * and font object)
 */
public class FontStruct
{	final int max=4;
	int Size[]=new int[max];
	boolean Bold[]=new boolean[max];
	Font F[]=new Font[4];
	int Next=0;

	public void storeFont (int size, boolean bold, Font f)
	{	if (Next>=max) Next=0;
		Size[Next]=size;
		Bold[Next]=bold;
		F[Next]=f;
		Next++;
	}
	
	public Font getFont (int size, boolean bold)
	{	for (int i=0; i<max; i++)
		{	if (F[i]==null) break;
			if (Size[i]==size && Bold[i]==bold)
				return F[i];
		}
		return null;
	}

	public static void main(String[] args)
	{}
}
