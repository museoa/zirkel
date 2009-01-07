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

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class JFileFilter extends FileFilter{
    //Description et extension accept�e par le filtre
    public String description;
    public String extension;
    //Constructeur � partir de la description et de l'extension accept�e
    public JFileFilter(String description, String extension){
        if(description == null || extension ==null){
            throw new NullPointerException("La description (ou extension) ne peut �tre null.");
        }
        this.description = description;
        this.extension = extension;
        
    }
    //Impl�mentation de FileFilter
    public boolean accept(File file){
        if(file.isDirectory()) {
            return true;
        }
        String nomFichier = file.getName().toLowerCase();
        return nomFichier.endsWith(extension);
    }
    public String getDescription(){
        return description;
    }
    
    
    
    
}