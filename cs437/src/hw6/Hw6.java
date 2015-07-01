package hw6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class Hw6 extends JWindow {

	private long time;
	private static final int WIDTH = 350;
	private static final int HEIGHT = 200;
	private ImageIcon iconChasing;
	private boolean debug = false;

	public Hw6(long time) {
		this(time, false);
	}

	public Hw6(long time, boolean status) {
		this.time = time;
		splashWindow();
		this.debug = status;
	}

	public void splashWindow() {

		Image imgChasing = null, dImgChasing = null;

		int x;
		int y;

		JPanel cp = (JPanel) getContentPane();
		cp.setBackground(Color.white);

		Dimension splash = Toolkit.getDefaultToolkit().getScreenSize();

		x = (splash.width - WIDTH) / 2;
		y = (splash.height - HEIGHT) / 2;
		setBounds(x, y, WIDTH, HEIGHT);



		try {
		    imgChasing = ImageIO.read(new File((this.getClass().getResource("images/chasing.jpg")).toURI()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		dImgChasing = imgChasing.getScaledInstance(320, 180, Image.SCALE_SMOOTH);
		iconChasing = new ImageIcon(dImgChasing);

		// splash window
		JLabel label = new JLabel(iconChasing);
		JLabel title = new JLabel("TicTacToe W/ Tom and Jerry",JLabel.CENTER);
		JLabel count = new JLabel("3", JLabel.CENTER);
		title.setFont(new Font("Chalkboard", Font.BOLD, 20));
		count.setFont(new Font("Tahoma", Font.ITALIC, 16));
		cp.add(label, BorderLayout.CENTER);
		cp.add(title, BorderLayout.SOUTH);
		//cp.add(count, BorderLayout.SOUTH);
		cp.setBorder(BorderFactory.createLineBorder(Color.blue, 10));



		setVisible(true);

		try {
            TimeUnit.SECONDS.sleep(time/time);
            count.setText("2");
            TimeUnit.SECONDS.sleep(time/time);
            count.setText("1");
            TimeUnit.SECONDS.sleep(time/time);
		} catch (Exception e) {
			System.out.println("Error" + e);
		}

		setVisible(false);

		//if (!debug) {
			//System.exit(0);
		//}
	}

	public static void main(String[] args) {
		new Hw6(3);
	}

}
