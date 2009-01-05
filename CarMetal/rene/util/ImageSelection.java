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

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @author unknown
 * Class to hold an image for the clipboad,
 * implements the Transferable class properly.
 */
public class ImageSelection 
	implements Transferable 
{
//	the Image object which will be housed by the ImageSelection
	private Image image;
	
	public ImageSelection(Image image) {
		this.image = image;
	}
	
//	Returns the supported flavors of our implementation
	public DataFlavor[] getTransferDataFlavors() 
	{
		return new DataFlavor[] {DataFlavor.imageFlavor};
	}
	
//	Returns true if flavor is supported
	public boolean isDataFlavorSupported(DataFlavor flavor) 
	{
		return DataFlavor.imageFlavor.equals(flavor);
	}
	
//	Returns Image object housed by Transferable object
	public Object getTransferData(DataFlavor flavor)
	throws UnsupportedFlavorException,IOException 
	{
		if (!DataFlavor.imageFlavor.equals(flavor)) 
		{
			throw new UnsupportedFlavorException(flavor);
		}
		// else return the payload
		return image;
	}
}


