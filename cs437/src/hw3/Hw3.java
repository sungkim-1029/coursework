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
    private List<Balloon> balloons;
    private Graphics2D g2;
    private final int MID = 200;
    private final int TOP = 200;
    
    Hw3() {

    	// make balloon group
        balloons = new ArrayList<Balloon>();
        
        setTitle("HW3");
        setSize(400, 400);
        setBackground(Color.cyan);
        setForeground(Color.black);
        
        // Make Balloons
        makeAndGroupBalloon(MID - 100, TOP - 40, 40, 50, Color.yellow);
        makeAndGroupBalloon(MID - 50, TOP, 40, 50, Color.white);
        makeAndGroupBalloon(MID + 16, TOP + 45, 50, 60, Color.red);
        makeAndGroupBalloon(MID + 25, TOP + 5, 46, 60, Color.orange);
        makeAndGroupBalloon(MID - 75, TOP + 55, 62, 70, Color.blue);
        
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        });
        
        setVisible(true);
        this.addMouseListener(new mousePressHandler());
    }
    
    public Balloon makeBalloon(double x, double y, double w, double h, Color colorOval) {
    	return new Balloon(x, y, w, h, colorOval);
    }
    
    public void groupBalloon(Balloon b) {
    	balloons.add(b);
    }
    
    public void makeAndGroupBalloon(double x, double y, double w, double h, Color colorOval) {
        balloons.add(new Balloon(x, y, w, h, colorOval));
    }
    
    public void drawBalloon(Balloon b) {
        g2.setColor(b.getColorOval());
        g2.fill(b.getOval());
        
        g2.setColor(b.getColorLine());
        g2.draw(b.getLine());
    }
    
    public void paint(Graphics g) {

        g2 = (Graphics2D) g;
        
        g.drawString("select an object with the mouse", 20, 80);
        
        for (Balloon b : balloons) {
            drawBalloon(b);
        }
    }

    class mousePressHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            
            for (Balloon b : balloons) {
               if (b.getOval().contains(e.getX(), e.getY())) {
            	   b.changeOvalColorTo();
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
