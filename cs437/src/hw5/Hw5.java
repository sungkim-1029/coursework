package hw5;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class Hw5 extends Frame {
	
	GregorianCalendar now;
	
	public Hw5() // constructor
    {
        setTitle("HW5 Clock");
        setSize(500, 500);
        setBackground(Color.cyan);
        setForeground(Color.black);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        });
        now = new GregorianCalendar();
        setVisible(true);
        
        turnClock();
    }

	public void turnClock () {
		while (true) {
			try {
				now = new GregorianCalendar();
	            requestFocus();
	            repaint();
	            TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				System.out.println("Error" + e);
			}
		}
	}
	
    public void paint(Graphics g) {

        g.setColor(Color.blue);
        Font f = new Font("Chalkboard", Font.BOLD, 30);
        g.setFont(f);
        g.drawString(now.get(GregorianCalendar.MONTH) + "/" + now.get(GregorianCalendar.DATE) + "/" + now.get(GregorianCalendar.YEAR), 180, 200);
        g.drawString(now.get(GregorianCalendar.HOUR) + ":" + now.get(GregorianCalendar.MINUTE) + ":" + now.get(GregorianCalendar.SECOND), 180, 250);
    }

    public static void main(String[] args) {

    	new Hw5(); // construct and execute
        
    }
}
