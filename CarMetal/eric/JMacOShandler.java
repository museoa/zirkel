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

import com.apple.eawt.*;

/**
 *
 * @author erichake
 */
public class JMacOShandler extends Application {

    /** Creates a new instance of JMacOShandler */
    public JMacOShandler() {
        addApplicationListener(new OpenHandler());
    }

    class OpenHandler extends ApplicationAdapter {

        public void handleOpenFile(com.apple.eawt.ApplicationEvent evt) {
            final String filename=evt.getFilename();
            
            if (JMacrosTools.isStartup) {
                JMacrosTools.StartupFiles.add(filename);
            } else {
                if ((filename.endsWith(".mcr"))) {
                    JMacrosTools.OpenMacro(filename);
                } else {
                                JMacrosTools.OpenFile(filename, null, false);
                                JMacrosTools.RefreshDisplay();
                    
                }

            }
        }

        public void handleQuit(com.apple.eawt.ApplicationEvent e) {
            JMacrosTools.disposeAllJZFs();
        }
    }
}
