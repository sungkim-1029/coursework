package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AboutProject extends JFrame {

	private static final int WIDTH = 280;
    private static final int HEIGHT = 150;

	AboutProject() {
		setTitle("About...");
		setSize(WIDTH, HEIGHT);
		setBackground(Color.white);
		setForeground(Color.black);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		add(new DrawingPane());

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		int x;
		int y;

		Dimension splash = Toolkit.getDefaultToolkit().getScreenSize();

		x = (splash.width - WIDTH) / 2;
		y = (splash.height - HEIGHT) / 2;
		setBounds(x, y, WIDTH, HEIGHT);
		setVisible(true);
		this.addMouseListener(new mousePressHandler());
	}

	class mousePressHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			requestFocus();
			System.out.println("Lines and Texts"); // debug print
			repaint();
			setVisible(false);
		}
	}

	private class DrawingPane extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

    		g.setColor(Color.blue.darker());
    		g.drawLine(110, 40, 170, 40);
    		g.drawLine(110, 60, 170, 60);
    		g.setColor(Color.blue);
    		g.drawLine(130, 30, 130, 70);
    		g.drawLine(150, 30, 150, 70);
    		g.setColor(Color.red);
    		g.drawString("Hello! It's the last project.", 25, 100);
    		g.drawString("Have fun with Tom and Jerry.", 25, 120);
        }
    }

	public static void main(String args[]) {
		new AboutProject();
	}
}
