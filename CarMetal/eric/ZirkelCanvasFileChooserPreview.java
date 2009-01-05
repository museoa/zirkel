/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import rene.zirkel.ZirkelCanvas;
import rene.zirkel.ZirkelFrame;

/**
 *
 * @author thecat
 */
public class ZirkelCanvasFileChooserPreview extends JPanel implements PropertyChangeListener {

    static String previewString=JGlobals.Loc("opendialog.preview");
    File file=null;
    ZirkelCanvas CurrentZC=null;
    int w=250;
    int h=250;
    Image img=null;
    
    public void paint(Graphics g) {
            super.paint(g);
            if (img!=null) {
                g.drawImage(img, 20, 25, w-40, h-40, this);
                g.drawRect(20, 25, w-40, h-40);
            }
        }
    
    public ZirkelCanvasFileChooserPreview(JFileChooser fc) {
        fixsize(this, w, h);
        fc.addPropertyChangeListener(this);
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(previewString));

    }

    private static void fixsize(JComponent cp, int w, int h) {
        Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    public void loadConstruction() {
        
        ZirkelCanvas zc=new ZirkelCanvas(true, false, false);
        CurrentZC=zc;
        zc.setVisible(false);
        add(zc);

        try {
            InputStream o=new FileInputStream(file);
            if (ZirkelFrame.isCompressed(file.getPath())) {
                o=new GZIPInputStream(o);
            }
            zc.load(o);
            o.close();

            if (!isShowing()) {
                return;
            }
            if (!(CurrentZC.equals(zc))) {
                return;
            }

            int zcW=zc.getPreferredSize().width;
            int zcH=zc.getPreferredSize().height;
            if (zcW==0) {
                zcW=1;
            }
            if (zcH==0) {
                zcH=1;
            }
            fixsize(zc, zcH, zcW);
            img=new BufferedImage(zcW, zcH, BufferedImage.TYPE_INT_RGB);

            Graphics g2=img.getGraphics();
            zc.paint(g2);

            img=img.getScaledInstance(w-40, h-40, Image.SCALE_SMOOTH);

        } catch (Exception e) {
            // if any trouble, display nothing :
            img=null;
        }

        repaint();
    }

    public void propertyChange(PropertyChangeEvent e) {
        boolean update=false;
        String prop=e.getPropertyName();
        img=null;
        removeAll();
        revalidate();
        paintImmediately(0, 0, w, h);
        //If the directory changed, don't show an image.
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file=null;
            update=false;
        //If a file became selected, find out which one.
        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {

            file=(File) e.getNewValue();

            if (file!=null) {
                if (file.isDirectory()) {
                    
                    update=false;
                } else {
                    update=true;
                }
            }

        }

        
        //Update the preview accordingly.
        if ((update)&&(isShowing())) {
            Thread thread=new Thread(new Runnable() {
                public void run() {
                    loadConstruction();
                }
                });
            thread.start();
        }else{
            
        }
    }

}
