/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eric;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.java.dev.colorchooser.ColorChooser;
import net.java.dev.colorchooser.Palette;

/**
 *
 * @author erichake
 */
public class JColorPicker extends JButton {

    ColorChooser CC=null;
    int D, IN, STRK;
    public static Color DefaultC=Color.GRAY;
    Color CurrentC=DefaultC;
    boolean isSelected=false, isEntered=false, isDisabled=false;
    Vector group;
    int[] x={0, 10, 0};
    int[] y={0, 10, 10};

    @Override
    public void paintComponent(java.awt.Graphics g) {
        Graphics2D g2=(Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        AlphaComposite ac;
        if (isDisabled) {
            ac=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
            g2.setComposite(ac);
            g2.setColor(new Color(100, 100, 100));
            g2.fillRect(IN, IN, D-2*IN, D-2*IN);
            return;
        }
        if (isSelected) {
            ac=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
            g2.setComposite(ac);
            g2.setColor(new Color(0, 0, 100));
            g2.fillRect(0, 0, D, D);
        }
        if (isEntered) {
            ac=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
            g2.setComposite(ac);
            g2.setColor(new Color(0, 0, 80));
            Stroke stroke=new BasicStroke(3f);
            g2.setStroke(stroke);
            g2.drawRect(2, 2, D-4, D-4);
        }
        g2.setColor(CurrentC);
        g2.setComposite(AlphaComposite.Src);
        g2.setStroke(new BasicStroke(STRK, BasicStroke.CAP_SQUARE, 0));
        g2.clearRect(IN, IN, D-2*IN, D-2*IN);
        ac=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        g2.setComposite(ac);
        g2.fillRect(IN, IN, D-2*IN, D-2*IN);
        g2.setComposite(AlphaComposite.Src);
        g2.drawRect(IN, IN, D-2*IN, D-2*IN);
        int sze=6;
        x[0]=D-sze;
        y[0]=D;
        x[1]=D;
        y[1]=D-sze;
        x[2]=D;
        y[2]=D;
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(new Color(50, 50, 50));
        g2.fillPolygon(x, y, 3);
    }

    public JColorPicker(int d, int inset, int stroke, Vector mygroup) {
        super();
        group=mygroup;
        if (group!=null) {
            group.add(this);
        }
        D=d;
        IN=inset;
        STRK=stroke;
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));
        fixsize(this, d, d);
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setFocusable(false);
        CC=new ColorChooser() {

            public void paintComponent(java.awt.Graphics g) {
            }
        };
        CC.setBorder(BorderFactory.createEmptyBorder());
        CC.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                Color mc=CC.getColor();
                CurrentC=new Color(mc.getRed(), mc.getGreen(), mc.getBlue());
                doChange();
            }
        });
        CC.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent e) {
                Color mc=CC.getTransientColor();
                if (mc!=null) {
                    CurrentC=new Color(mc.getRed(), mc.getGreen(), mc.getBlue());
                    doChange();
                }
            }
        });
        CC.addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent arg0) {
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                Select();
                afterSelect();
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                setPalettes();
                isEntered=true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                isEntered=false;
                repaint();
            }
        });
        CC.setToolTipText("");
        this.add(CC);
    }

    public void setDisabled(boolean b) {
        isDisabled=b;
        if (b) {
            this.remove(CC);
        } else {
            this.add(CC);
        }
    }

    @Override
    public String getName() {
        return "scolor";
    }

    public void setPalettes() {
    }

    public void setUsedColors(Vector V) {
        Color[] cols;
        String[] strs;
        if (V.size()==0) {
            cols=new Color[1];
            strs=new String[1];
            cols[0]=Color.WHITE;
            strs[0]="";

        } else {
            cols=new Color[V.size()];
            strs=new String[V.size()];

            for (int i=0; i<V.size(); i++) {
                JColors jc=(JColors) V.get(i);
                cols[i]=jc.C;
                strs[i]=jc.S;
            }
        }
        Palette pals[]=CC.getPalettes();
        pals[7]=Palette.createPredefinedPalette(null, cols, strs);
        CC.setPalettes(pals);
    }

    public Color getCurrentColor() {
        return CurrentC;
    }

    public void setDefaultColor() {
        CurrentC=DefaultC;
        repaint();
    }

    public Color getDefaultColor() {
        return DefaultC;
    }

    public void setCurrentColor(Color c) {
        if (c!=null) {
            CurrentC=c;
            repaint();
        }
    }

    public void doChange() {
    }
    ;

    @Override
    public void setSelected(boolean b) {
        isSelected=b;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }
    // Graphical appearence of button(s) and changing of state :
    public final void Select() {
        if (group!=null) {
            for (int i=0; i<group.size(); i++) {
                JButton myicn=(JButton) group.get(i);
                if (myicn.isSelected()) {
                    myicn.setSelected(false);
                    myicn.repaint();
                }
            }
        }
        isSelected=true;
        isEntered=false;
        repaint();
    }

    public void afterSelect() {
    }
    ;

    static void fixsize(JComponent cp, int w, int h) {
        Dimension d=new Dimension(w, h);
        cp.setMaximumSize(d);
        cp.setMinimumSize(d);
        cp.setPreferredSize(d);
        cp.setSize(d);
    }

    static JPanel margin(int w) {
        JPanel mypan=new JPanel();
        fixsize(mypan, w, 1);
        mypan.setLayout(new javax.swing.BoxLayout(mypan, javax.swing.BoxLayout.X_AXIS));
        mypan.setAlignmentX(0F);
        mypan.setAlignmentY(0F);
        mypan.setOpaque(false);
        mypan.setFocusable(false);
        return mypan;
    }
}
