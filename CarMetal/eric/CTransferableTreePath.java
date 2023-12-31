/* 
 
Copyright 2006 Eric Hakenholz

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
 
 
 package eric;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.tree.TreePath;




/**
* This represents a TreePath (a node in a JTree) that can be transferred between a drag source and a drop target.
*/
class CTransferableTreePath implements Transferable
{
	// The type of DnD object being dragged...
	public static final DataFlavor TREEPATH_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "TreePath");

	private TreePath		_path;

	private DataFlavor[]	_flavors = 
							{
								TREEPATH_FLAVOR
							};
	
	/**
	* Constructs a transferrable tree path object for the specified path.
	*/
	public CTransferableTreePath(TreePath path)
	{
		_path = path;
	}
	
	// Transferable interface methods...
	public DataFlavor[] getTransferDataFlavors()
	{
		return _flavors;
	}
	
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return java.util.Arrays.asList(_flavors).contains(flavor);
	}
	
	public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
	{
            
		if (flavor.isMimeTypeEqual(TREEPATH_FLAVOR.getMimeType())) // DataFlavor.javaJVMLocalObjectMimeType))
			return _path;
		else
			throw new UnsupportedFlavorException(flavor);	
	}

	
}
	
