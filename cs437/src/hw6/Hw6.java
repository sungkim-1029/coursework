package hw6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class Hw6 extends JWindow {
	
	private long time;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;

	public Hw6(long time) {
		this.time = time;
		splashWindow();
	}

	public void splashWindow() {
		
		int x;
		int y;
		
		JPanel cp = (JPanel) getContentPane();
		cp.setBackground(Color.cyan);

		Dimension splash = Toolkit.getDefaultToolkit().getScreenSize();
		
		x = (splash.width - WIDTH) / 2;
		y = (splash.height - HEIGHT) / 2;
		setBounds(x, y, WIDTH, HEIGHT);

		// splash window
		JLabel title = new JLabel("HW 6",JLabel.CENTER);
		JLabel count = new JLabel("3", JLabel.CENTER);
		title.setFont(new Font("Chalkboard", Font.BOLD, 20));
		count.setFont(new Font("Tahoma", Font.ITALIC, 16));
		cp.add(title, BorderLayout.CENTER);
		cp.add(count, BorderLayout.SOUTH);

		setVisible(true);

		try {
            TimeUnit.SECONDS.sleep(time/3);
            count.setText("2");
            TimeUnit.SECONDS.sleep(time/3);
            count.setText("1");
            TimeUnit.SECONDS.sleep(time/3);
		} catch (Exception e) {
			System.out.println("Error" + e);
		}

		setVisible(false);
		System.exit(0);
	}

	public static void main(String[] args) {
		new Hw6(3);
	}
	
}
