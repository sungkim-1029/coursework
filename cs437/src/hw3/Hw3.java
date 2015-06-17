package hw3;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Hw3 extends Frame {
    
    private Ellipse2D oval;
    private Line2D line;
    private List<Ballon> Ballons;
    private Graphics2D g2;
    private final int MID = 200;
    private final int TOP = 200;
    
    Hw3() {

        Ballons = new ArrayList<Ballon>();
        setTitle("HW3");
        setSize(400, 400);
        setBackground(Color.cyan);
        setForeground(Color.black);
        
        // Make Balloons
        makeBallon(MID - 100, TOP - 40, 40, 50, Color.yellow);
        makeBallon(MID - 50, TOP, 40, 50, Color.white);
        makeBallon(MID + 16, TOP + 45, 50, 60, Color.red);
        makeBallon(MID + 25, TOP + 5, 46, 60, Color.orange);
        makeBallon(MID - 75, TOP + 55, 62, 70, Color.blue);
        
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        });
        setVisible(true);
        this.addMouseListener(new mousePressHandler());
    }
    
    public void makeBallon(double x, double y, double w, double h, Color colorOval) {
        Ballons.add(new Ballon(x, y, w, h, colorOval));
    }
    
    
    public void drawBallon(Ballon b) {
        g2.setColor(b.getColorOval());
        g2.fill(b.getOval());
        
        g2.setColor(b.getColorLine());
        g2.draw(b.getLine());
    }
    
    public void paint(Graphics g) {



        g2 = (Graphics2D) g;
        
        g.drawString("select an object with the mouse", 20, 80);
        
        for (Ballon b : Ballons) {
            drawBallon(b);
        }
        
 
    }

    class mousePressHandler extends MouseAdapter {
    
        Color temp;
        public void mouseClicked(MouseEvent e) {
            
            for (Ballon b : Ballons) {
                
               if (b.getOval().contains(e.getX(), e.getY())) {
                   if (b.getTempOval() == null) {
                       b.setTempOval(b.getColorOval());
                       b.setColorOval(b.getDefaultColorOval());
                   } else {
                       b.setColorOval(b.getTempOval());
                       b.setTempOval(null);
                   }
                   
               } 
            }
            requestFocus();
            repaint();
        }
    }

    public static void main(String args[]) {

        new Hw3();
    }
}
