package hw1;

//W1frame.java   a very basic, yet complete java frame application

import java.awt.*;
import java.awt.event.*;

public class Hw1Second extends Frame {

	Hw1Second() {
		setTitle("W1frame");
		setSize(280, 150);
		setBackground(Color.white);
		setForeground(Color.black);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setVisible(true);
		this.addMouseListener(new mousePressHandler());
	}

	class mousePressHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			requestFocus();
			System.out.println("Lines and Texts"); // debug print
			repaint();
			System.exit(0);
		}
	}

	public void paint(Graphics g) {

		g.setColor(Color.blue);
		g.drawLine(20, 80, 250, 80);
		g.setColor(Color.red);
		g.drawString("Hello! This is the second example.", 25, 70);
	}

	public static void main(String args[]) {
		new Hw1Second();
	}
}
