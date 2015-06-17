package hw1;

//W1frame.java   a very basic, yet complete java frame application

import java.awt.*;
import java.awt.event.*;

public class Hw1First extends Frame {

	Hw1First() {
		setTitle("W1frame");
		setSize(200, 125);
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
			System.out.println("press"); // debug print
			repaint();
			System.exit(0);
		}
	}

	public void paint(Graphics g) {
		// draw a boundary
		g.drawRect(50, 75, 100, 25);
		g.drawString("Click here to exit", 50, 50);
	}

	public static void main(String args[]) {
		new Hw1First();
	}
}
