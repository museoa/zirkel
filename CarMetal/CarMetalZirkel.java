
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

public class CarMetalZirkel {
    
    
    static public void main(String[] args) {
        final int PORT = 32145;
        int i=0;
        String filename="";
        
        String v=System.getProperty("java.version");
        if ((v.startsWith("1.1"))||(v.startsWith("1.2"))||
                (v.startsWith("1.3"))||(v.startsWith("1.4"))) {
            new eric.JOldJavaVersionDialog();
        }else{
        
        
        while (i<args.length) {
            if (args[i].startsWith("-l") && i<args.length-1) {
                i+=2;
            } else if (args[i].startsWith("-h") && i<args.length-1) {
                i+=2;
            } else if (args[i].startsWith("-s")) {
                i++;
            } else if (args[i].startsWith("-r")) {
                i++;
            } else if (args[i].startsWith("-d")) {
                i++;
            } else {
                filename+=args[i]+System.getProperty("path.separator");
                i++;
            }
        };
        
        
        
        
        final String FILES=filename;
        
        /*
        eric.JUniqueInstance uniqueInstance = new eric.JUniqueInstance(PORT,FILES);
       
        if(uniqueInstance.launch()) {
            if (System.getProperty("mrj.version") != null){
                eric.JMacOShandler app=new eric.JMacOShandler();
            };
        }
        */
            
            rene.zirkel.Zirkel.main(args);
        }
    }
    
   
    
    
    
}
