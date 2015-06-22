package project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

public class ProjectFinal extends JFrame {
	private static final String TITLE = "Project Tic Tac Toe";
	private static final int WIDTH = 450;
	private static final int HEIGHT = 472;

	private Container contentPane;
	private JPanel panel;
	private JButton[] cells;
	private JLabel playerJerry, playerTom;
	private JLabel jerryWinText, tomWinText;
	private JLabel jerryWinScore, tomWinScore;
	private JLabel gameClock;
	private Box scoreBoard;

	public ProjectFinal() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		createMenuBar();

		contentPane = getContentPane();
		contentPane.setBackground(Color.blue.darker());
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		createScoreBoard();
		createPlayerBoard();
		createGameBoard();


        //System.out.println(menuBar.getPreferredSize());
		pack();
        setVisible(true);
	}

	private void createGameBoard() {

	}

	private void createScoreBoard() {

		scoreBoard = Box.createHorizontalBox();

		jerryWinText = new JLabel("WINS", JLabel.CENTER);
		jerryWinText.setFont(new Font("Chalkboard", Font.BOLD, 20));
		jerryWinText.setForeground(Color.WHITE.darker());

		jerryWinScore = new JLabel("0", JLabel.CENTER);
		jerryWinScore.setFont(new Font("Chalkboard", Font.BOLD, 20));
		jerryWinScore.setForeground(Color.WHITE.darker());

		gameClock = new JLabel("5", JLabel.CENTER);
		gameClock.setFont(new Font("Chalkboard", Font.BOLD, 20));
		gameClock.setOpaque(true);
		gameClock.setBackground(Color.GRAY.brighter());
		gameClock.setForeground(Color.black.darker());
		//System.out.println(gameClock.getPreferredSize());
		gameClock.setPreferredSize(new Dimension(50, 30));

		tomWinScore = new JLabel("0", JLabel.CENTER);
		tomWinScore.setFont(new Font("Chalkboard", Font.BOLD, 20));
		tomWinScore.setForeground(Color.WHITE.darker());

		tomWinText = new JLabel("WINS", JLabel.CENTER);
		tomWinText.setFont(new Font("Chalkboard", Font.BOLD, 20));
		tomWinText.setForeground(Color.WHITE.darker());

		scoreBoard.add(jerryWinText);
		scoreBoard.add(jerryWinScore);
		scoreBoard.add(Box.createHorizontalGlue());
		scoreBoard.add(gameClock);
		scoreBoard.add(Box.createHorizontalGlue());
		scoreBoard.add(tomWinScore);
		scoreBoard.add(tomWinText);

		contentPane.add(scoreBoard);

	}

	private void createPlayerBoard() {

		panel = new JPanel(new GridLayout(0, 2));

		playerJerry = new JLabel("Jerry", JLabel.CENTER);
		playerJerry.setFont(new Font("Chalkboard", Font.BOLD, 20));
		playerJerry.setOpaque(true);
		playerJerry.setBackground(Color.gray.brighter());
		playerJerry.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		playerJerry.setPreferredSize(new Dimension(220, 50));

		playerTom = new JLabel("Tom", JLabel.CENTER);
		playerTom.setFont(new Font("Chalkboard", Font.BOLD, 20));
		playerTom.setOpaque(true);
		playerTom.setBackground(Color.gray.brighter());
		playerTom.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		playerTom.setPreferredSize(new Dimension(220, 50));

		panel.add(playerJerry);
		panel.add(playerTom);

		contentPane.add(panel);
	}

	private void createMenuBar() {

        // Just create menubar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu ticTacToeMenu = new JMenu("Tic Tac Toe");
        menuBar.add(ticTacToeMenu);
        // set up Tic Tac Toe items
        JMenuItem aboutItem = new JMenuItem("About Tic Tac Toe");
        ticTacToeMenu.add(aboutItem);

        // set up Tic Tac Toe items
        JMenuItem quitItem = new JMenuItem("Quit");
        QuitItemHandler quitHandler = new QuitItemHandler();
        quitItem.setMnemonic(KeyEvent.VK_Q);
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        quitItem.setToolTipText("Exit application");
        quitItem.addActionListener(quitHandler);
        ticTacToeMenu.add(quitItem);

        // set up file menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu); // add Set Value menu to menu bar
        // set up File items
        JMenuItem newItem = new JMenuItem("New");
        fileMenu.add(newItem);
        // set up File items
        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);
        // set up File items
        JMenuItem saveItem = new JMenuItem("Save");
        fileMenu.add(saveItem);

        // set up game menu
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu); // add Set Value menu to menu bar
        // set up game items
        JMenuItem togglePauseItem = new JMenuItem("Pause/Resume");
        gameMenu.add(togglePauseItem);
        // Add separator
        gameMenu.add(new JSeparator());
        // set up game menu
        JMenuItem clockUpItem = new JMenuItem("Clock Up 1s");
        gameMenu.add(clockUpItem);
        // set up game menu
        JMenuItem clockDownItem = new JMenuItem("Clock Down 1s");
        gameMenu.add(clockDownItem);

        // shift to the right
        menuBar.add(Box.createGlue());

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        // set up Help items
        JMenuItem howToPlayItem = new JMenuItem("How to play");
        helpMenu.add(howToPlayItem);
	}

	public static void main(String[] args) {
		// Create ProjectFinal object
		ProjectFinal pf = new ProjectFinal();

	}

	private class QuitItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

}

/*
ImageIcon iconJerryStanding = new ImageIcon(this.getClass().getResource("images/standing_jerry.jpg"));
ImageIcon iconTomStanding = new ImageIcon(this.getClass().getResource("images/standing_tom.jpg"));
*/

/*
playerJerry = new JLabel("", iconJerryStanding, JLabel.CENTER);
playerJerry.setSize(225, 20);
playerTom = new JLabel("", iconTomStanding, JLabel.CENTER);
playerTom.setSize(225, 20);
panel.add(playerJerry);
panel.add(playerTom);
*/
