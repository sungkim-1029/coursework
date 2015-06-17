package hw4;

import java.awt.*;
import java.awt.event.*;

public class Hw4 extends Frame {
    public Hw4() // constructor
    {
        setTitle("HW4 Display Fonts");
        setSize(500, 400);
        setBackground(Color.cyan);
        setForeground(Color.black);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        });
        setVisible(true);
    }

    public void paint(Graphics g) {

        g.setColor(Color.blue);
        Font f20 = new Font("Chalkboard", Font.BOLD, 20);
        g.setFont(f20);
        g.drawString("This is Chalkboard 18pt BOLD", 40, 50);

        g.setColor(Color.red);
        Font f16 = new Font("Tahoma", Font.ITALIC, 16);
        g.setFont(f16);
        g.drawString("This is Tahoma 16pt ITALIC", 50, 100);
        
        g.setColor(Color.yellow);
        Font f30 = new Font("AppleGothic", Font.PLAIN, 30);
        g.setFont(f30);
        g.drawString("This is AppleGothic 30pt BOLD", 20, 200);
    }

    public static void main(String[] args) {

        new Hw4(); // construct and execute
    }
}
