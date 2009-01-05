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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class JUniqueInstance {

    private int port;
    private String message;

    public JUniqueInstance(int port, String message) {
        assert port>0&&port<1<<16 : "Le port doit être entre 1 et 65535";
        this.port=port;
        this.message=message;
    }

    public JUniqueInstance(int port) {
        this(port, null);
    }

    public boolean launch() {
        boolean unique;

        try {
            final ServerSocket server=new ServerSocket(port);
            unique=true;
            Thread portListenerThread=new Thread() {

                public void run() {
                    while (true) {
                        try {
                            final Socket socket=server.accept();
                            new Thread() {

                                public void run() {
                                    receive(socket);
                                }
                            }.start();
                        } catch (IOException e) {
                            Logger.getLogger("UniqueProgInstance").warning("Attente de connexion échouée.");
                        }
                    }
                }
            };
            portListenerThread.setDaemon(true);
            portListenerThread.start();

        } catch (IOException e) {
            unique=false;
//            javax.swing.JOptionPane.showMessageDialog(null, "launch()->send()");
            send();
        }
        return unique;
    }

    public void send() {
        PrintWriter pw=null;
        try {
            Socket socket=new Socket("localhost", port);
            pw=new PrintWriter(socket.getOutputStream());
            pw.write(message);
        } catch (IOException e) {
            Logger.getLogger("UniqueProgInstance").warning("Écriture de sortie échoué.");
        } finally {
            if (pw!=null) {
                pw.close();
            }
        }
    }

    public synchronized void launchFiles(String f) {
        if (!f.equals("")) {
            String[] files=f.split(System.getProperty("path.separator"));
            for (int i=0; i<files.length; i++) {
                if (JMacrosTools.isStartup) {
                    JMacrosTools.StartupFiles.add(files[i]);
                } else {
                    String filename=files[i];
                    if ((filename.endsWith(".mcr"))) {
                        JMacrosTools.OpenMacro(filename);
                    } else {
                        JMacrosTools.OpenFile(filename, null, false);
                        JMacrosTools.RefreshDisplay();
                    }
                    
                }
            }
        }
    }

    private synchronized void receive(Socket socket) {
        Scanner sc=null;

        try {
            socket.setSoTimeout(5000);
            sc=new Scanner(socket.getInputStream());
            final String filename=sc.nextLine();
            
            launchFiles(filename);


        } catch (Exception e) {
//        javax.swing.JOptionPane.showMessageDialog(null, "receive()->error");
        } finally {
            if (sc!=null) {
                sc.close();
            }
        }

    }


}