package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class HelpProject {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 200;

    private JFrame mainFrame;
    private JTextArea myArea;
    private String data[] = {"Tic-tac-toe (also known as Noughts and crosses or Xs and Os) "
			, "is a paper-and-pencil game for two players, X and O, "
			, "who take turns marking the spaces in a 3×3 grid. "
			, "The player who succeeds in placing three of their marks in a horizontal,"
			, " vertical, or diagonal row wins the game."};


    private void initUI() {

        mainFrame = new JFrame("Help");
        mainFrame.setSize(WIDTH, HEIGHT); // can be changed by user
        mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        int x;
		int y;

		Dimension splash = Toolkit.getDefaultToolkit().getScreenSize();

		x = (splash.width - WIDTH) / 2;
		y = (splash.height - HEIGHT) / 2;
		mainFrame.setBounds(x, y, WIDTH, HEIGHT);
    }

    private void createTextArea() {

        myArea = new JTextArea();
        myArea.setForeground(Color.GREEN);
        myArea.setBackground(Color.black);

        myArea.setText(data[0] + "\n" + data[1] + "\n" + data[2] + "\n" + data[3] + "\n" + data[4]);


        myArea.addMouseListener(new mousePressHandler());
        mainFrame.add(myArea);
    }

    class mousePressHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			mainFrame.setVisible(false);
		}
	}
    public HelpProject() {

        initUI();
        createTextArea();

        mainFrame.setVisible(true);
    }

}
