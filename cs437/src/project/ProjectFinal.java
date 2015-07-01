package project;

import hw6.Hw6;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;

public class ProjectFinal extends JFrame {
	private static final String TITLE = "Project Tic Tac Toe";
	private static final int WIDTH = 450;
	private static final int HEIGHT = 480;
	private final static int ONE_SECOND = 1000;
	private final static int DEFAULT_CLOCK_TIME = 5;
	private final static int MIN_CLOCK_TIME = 1;
	private final static int UP = 0;
	private final static int RESET = 1;
	private final static int DOWN = 2;
	private final static int DEFAULT_BLINK_DURATION = 2;
	private final static int DEFAULT_BLINK_DELAY = 100;

	private Container contentPane;
	private JPanel panel;
	private JButton[] cells;
	private HelpItemHandler helpHandler;
	private AboutItemHandler aboutHandler;
	private NewItemHandler newHandler;
	private PauseItemHandler pauseHandler;
	private OpenItemHandler openHandler;
	private WithdrawalItemHandler WithdrawalHandler;
	private SaveItemHandler saveHandler;
	private CellButtonHandler[] cellHandlers;
	private GameClockHandler[] clockHandlers;
	private JMenuItem[] clockControls;
	private JMenuItem togglePauseItem, withdrawalItem;
	private JLabel playerJerry, playerTom;
	private JLabel jerryWinText, tomWinText;
	private JLabel jerryWinScore, tomWinScore;
	private JLabel gameClock;
	private Box playerBoard, scoreBoard;
	private Integer clock;
	private Timer timer, blinkTimer;
	private int blinkTimerDelay;
	private boolean timeOut, pauseGame, gameOver;
	private boolean jerry, tom, pauseItemStatus, withdrawalItemStatus,
			clockControlStatus;
	private int userTime;
	int blinkDuration;
	private Integer jerryWin, tomWin;

	private ImageIcon iconJerryStanding;
	private ImageIcon iconTomStanding;
	private ImageIcon iconCheese;
	private ImageIcon iconJerryWin, iconTomWin;
	private ImageIcon iconChasing;

	private List<String> dataList;

	// private GregorianCalendar now;

	public ProjectFinal() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Set the window's bounds, centering the window
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - WIDTH) / 2;
		int y = (screen.height - HEIGHT) / 2;
		setBounds(x, y, WIDTH, HEIGHT);

		clock = userTime = DEFAULT_CLOCK_TIME;
		blinkDuration = DEFAULT_BLINK_DURATION;
		blinkTimerDelay = DEFAULT_BLINK_DELAY;
		jerryWin = 0;
		tomWin = 0;

		createMenuBar();

		new Hw6(2);

		makeIcon();

		contentPane = getContentPane();
		contentPane.setBackground(Color.blue.brighter());
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		createScoreBoard();
		createPlayerBoard();
		createGameBoard();

		init();
	}

	private void makeIcon() {

		Image imgJerry = null, dImgJerry = null;
		Image imgTom = null, dImgTom = null;
		Image imgCheese = null, dImgCheese = null;
		Image imgJerryWin = null, dImgJerryWin = null;
		Image imgTomWin = null, dImgTomWin = null;
		Image imgChasing = null, dImgChasing = null;

		try {
			imgJerry = ImageIO.read(new File((this.getClass()
					.getResource("images/standing_jerry.jpg")).toURI()));
			imgTom = ImageIO.read(new File((this.getClass()
					.getResource("images/standing_tom.jpg")).toURI()));
			imgCheese = ImageIO.read(new File((this.getClass()
					.getResource("images/cheese.jpg")).toURI()));
			imgJerryWin = ImageIO.read(new File((this.getClass()
					.getResource("images/winJerry.jpg")).toURI()));
			imgTomWin = ImageIO.read(new File((this.getClass()
					.getResource("images/winTom.jpg")).toURI()));
			imgChasing = ImageIO.read(new File((this.getClass()
					.getResource("images/chasing.jpg")).toURI()));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		dImgJerry = imgJerry.getScaledInstance(140, 110, Image.SCALE_SMOOTH);
		dImgTom = imgTom.getScaledInstance(140, 110, Image.SCALE_SMOOTH);
		dImgCheese = imgCheese.getScaledInstance(140, 110, Image.SCALE_SMOOTH);
		dImgJerryWin = imgJerryWin
				.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		dImgTomWin = imgTomWin.getScaledInstance(100, 70, Image.SCALE_SMOOTH);
		dImgChasing = imgChasing.getScaledInstance(120, 80, Image.SCALE_SMOOTH);

		iconJerryStanding = new ImageIcon(dImgJerry);
		iconTomStanding = new ImageIcon(dImgTom);
		iconCheese = new ImageIcon(dImgCheese);
		iconJerryWin = new ImageIcon(dImgJerryWin);
		iconTomWin = new ImageIcon(dImgTomWin);
		iconChasing = new ImageIcon(dImgChasing);
	}

	private void changePlayer() {
		if (jerry) {
			tom = true;
			jerry = false;
		} else {
			tom = false;
			jerry = true;
		}
	}

	private void countingClock(int userTime) {

		if (clock.intValue() > 0 || clock.intValue() < userTime) {
			timer.stop();
			clock = userTime;
			gameClock.setText(clock.toString());

		} else {
			// Do nothing
		}

		timer.start();
	}

	private void countingClock() {
		countingClock(DEFAULT_CLOCK_TIME);
	}

	private void init() {
		// Initialize booleans
		// noughts = true;
		// gameOver = false;

		timeOut = false;
		pauseGame = false;
		gameOver = false;

		jerry = true;
		tom = false;

		dataList = null;

		// Initialize text in buttons
		for (int i = 0; i < 9; i++) {
			char ch = (char) ('0' + i + 1);
			cells[i].setText("" + ch);
		}

		// Initialize result label
		// result.setText("Noughts");

		// Create a timer.
		timer = new Timer(ONE_SECOND, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clock--;
				gameClock.setText(clock.toString());

				if (clock.intValue() <= 2) {
					blinkTimer.start();
				}

				if (clock.intValue() == 0) {
					setEndGame();
				}
			}
		});

		// Create a timer.
		blinkTimer = new Timer(blinkTimerDelay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				gameClock.setBackground(gameClock.getBackground().equals(
						Color.GRAY.brighter()) ? Color.red : Color.GRAY
						.brighter());
				blinkDuration--;

				if (blinkDuration == 0) {
					blinkTimer.stop();
					blinkDuration = DEFAULT_BLINK_DURATION;
				}
			}
		});

		// pack();
		setVisible(true);
	}

	private void createMenuBar() {

		// Just create menubar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu ticTacToeMenu = new JMenu("Tic Tac Toe");
		menuBar.add(ticTacToeMenu);
		// set up Tic Tac Toe items
		JMenuItem aboutItem = new JMenuItem("About Tic Tac Toe");
		aboutHandler = new AboutItemHandler();
		aboutItem.addActionListener(aboutHandler);
		ticTacToeMenu.add(aboutItem);

		// set up Tic Tac Toe items
		JMenuItem quitItem = new JMenuItem("Quit");
		QuitItemHandler quitHandler = new QuitItemHandler();
		quitItem.setMnemonic(KeyEvent.VK_Q);
		quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask()));
		quitItem.setToolTipText("Exit application");
		quitItem.addActionListener(quitHandler);
		ticTacToeMenu.add(quitItem);

		// set up file menu
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu); // add Set Value menu to menu bar
		// set up File items
		JMenuItem newItem = new JMenuItem("New");
		newHandler = new NewItemHandler();
		newItem.addActionListener(newHandler);
		fileMenu.add(newItem);
		// set up File items
		JMenuItem openItem = new JMenuItem("Open");
		openHandler = new OpenItemHandler();
		openItem.addActionListener(openHandler);
		fileMenu.add(openItem);
		// set up File items
		JMenuItem saveItem = new JMenuItem("Save");
		saveHandler = new SaveItemHandler();
		saveItem.addActionListener(saveHandler);
		fileMenu.add(saveItem);

		// set up game menu
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu); // add Set Value menu to menu bar
		// set up game items
		togglePauseItem = new JMenuItem("Pause/Resume");
		pauseHandler = new PauseItemHandler();
		togglePauseItem.addActionListener(pauseHandler);
		enableTogglePauseItem(false);

		gameMenu.add(togglePauseItem);
		// Add separator
		gameMenu.add(new JSeparator());

		// set up game items
		withdrawalItem = new JMenuItem("Withdrawal");
		WithdrawalHandler = new WithdrawalItemHandler();
		withdrawalItem.addActionListener(WithdrawalHandler);
		enableWithdrawalItem(false);
		gameMenu.add(withdrawalItem);
		// Add separator
		gameMenu.add(new JSeparator());

		clockHandlers = new GameClockHandler[3];
		clockControls = new JMenuItem[3];
		clockControls[UP] = new JMenuItem("Clock Up");
		clockControls[RESET] = new JMenuItem("Clock Reset");
		clockControls[DOWN] = new JMenuItem("Clock Down");
		enableClockControls(true);

		for (int i = 0; i < 3; i++) {
			clockHandlers[i] = new GameClockHandler();
			clockControls[i].addActionListener(clockHandlers[i]);
			gameMenu.add(clockControls[i]);
		}

		// shift to the right
		menuBar.add(Box.createGlue());
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		// set up Help items
		JMenuItem howToPlayItem = new JMenuItem("How to play");
		helpHandler = new HelpItemHandler();
		howToPlayItem.addActionListener(helpHandler);
		helpMenu.add(howToPlayItem);
	}

	private void createScoreBoard() {

		scoreBoard = Box.createHorizontalBox();

		jerryWinText = new JLabel("WINS: ", JLabel.CENTER);
		jerryWinText.setFont(new Font("Chalkboard", Font.BOLD, 20));
		jerryWinText.setForeground(Color.WHITE.darker());

		jerryWinScore = new JLabel(jerryWin.toString(), JLabel.RIGHT);
		jerryWinScore.setFont(new Font("Chalkboard", Font.BOLD, 20));
		jerryWinScore.setForeground(Color.WHITE.brighter());

		gameClock = new JLabel(clock.toString(), JLabel.CENTER);
		gameClock.setFont(new Font("Chalkboard", Font.BOLD, 20));
		gameClock.setOpaque(true);
		gameClock.setBackground(Color.GRAY.brighter());
		gameClock.setForeground(Color.black.darker());
		gameClock.setPreferredSize(new Dimension(50, 30));

		tomWinScore = new JLabel(tomWin.toString(), JLabel.LEFT);
		tomWinScore.setFont(new Font("Chalkboard", Font.BOLD, 20));
		tomWinScore.setForeground(Color.WHITE.brighter());

		tomWinText = new JLabel(" :WINS", JLabel.CENTER);
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

		playerBoard = Box.createHorizontalBox();

		playerJerry = new JLabel("Jerry", JLabel.CENTER);
		playerJerry.setFont(new Font("Chalkboard", Font.BOLD, 20));
		playerJerry.setOpaque(true);
		playerJerry.setBackground(Color.gray.brighter());
		playerJerry.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		playerJerry.setPreferredSize(new Dimension(200, 30));

		playerTom = new JLabel("Tom", JLabel.CENTER);
		playerTom.setFont(new Font("Chalkboard", Font.BOLD, 20));
		playerTom.setOpaque(true);
		playerTom.setBackground(Color.gray.brighter());
		playerTom.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		playerTom.setPreferredSize(new Dimension(200, 30));

		playerBoard.add(playerJerry);
		playerBoard.add(Box.createHorizontalGlue());
		playerBoard.add(playerTom);

		contentPane.add(playerBoard);
	}

	private void createGameBoard() {

		panel = new JPanel(new GridLayout(3, 3));
		panel.setOpaque(false);

		// Create cells and handlers
		cells = new JButton[9];
		cellHandlers = new CellButtonHandler[9];
		for (int i = 0; i < 9; i++) {
			// char ch = (char) ('0' + i + 1);
			// cells[i] = new JButton("" + ch);
			cells[i] = new JButton("");
			cellHandlers[i] = new CellButtonHandler();
			cells[i].addActionListener(cellHandlers[i]);
			cells[i].setIcon(iconCheese);
		}

		// Add elements to the grid content pane
		for (int i = 0; i < 9; i++) {
			panel.add(cells[i]);
		}

		contentPane.add(panel);
	}

	public static void main(String[] args) {
		// Create ProjectFinal object
		ProjectFinal pf = new ProjectFinal();

	}

	private void enableClockControls(boolean status) {
		this.clockControlStatus = status;
		for (int i = 0; i < 3; i++) {
			clockControls[i].setEnabled(this.clockControlStatus);
			;
		}
	}

	private void enableGameBoard(boolean status) {
		for (int i = 0; i < 9; i++) {
			cells[i].setEnabled(status);
		}
	}

	private void resetGameBoard() {
		for (int i = 0; i < 9; i++) {
			cells[i].setText("");
			cells[i].setIcon(iconCheese);
		}
	}

	private boolean checkDraw() {
		String sample = null;
		for (int i = 0; i < 9; i++) {
			sample = cells[i].getText();
			if (!sample.equals("O") && !sample.equals("X")) {
				return false;
			} else {
				continue;
			}
		}

		return true;
	}

	private void restoreGameBoard() {
		for (int i = 0; i < 9; i++) {
			System.out.println("before: cells.gettext: " + cells[i].getText());
			cells[i].setText(dataList.get(i + 3));

			System.out.println("after: cells.gettext: " + cells[i].getText());
			switch (cells[i].getText()) {

			case "O":
				cells[i].setIcon(iconJerryStanding);
				break;
			case "X":
				cells[i].setIcon(iconTomStanding);
				break;
			default:
				cells[i].setIcon(iconCheese);
				break;

			}
		}
	}

	private void restoreGame() {
		clock = userTime;
		gameClock.setText(clock.toString());
		restoreGameBoard();
		enableTogglePauseItem(false);
		enableWithdrawalItem(false);
		enableClockControls(true);

		timeOut = false;
		pauseGame = false;
		gameOver = false;

		jerry = true;
		tom = false;

		dataList = null;

		// Create a timer.
		timer = new Timer(ONE_SECOND, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clock--;
				gameClock.setText(clock.toString());

				if (clock.intValue() <= 2) {
					blinkTimer.start();
				}

				if (clock.intValue() == 0) {
					setEndGame();
				}
			}
		});

		// Create a timer.
		blinkTimer = new Timer(blinkTimerDelay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				gameClock.setBackground(gameClock.getBackground().equals(
						Color.GRAY.brighter()) ? Color.red : Color.GRAY
						.brighter());
				blinkDuration--;

				if (blinkDuration == 0) {
					blinkTimer.stop();
					blinkDuration = DEFAULT_BLINK_DURATION;
				}
			}
		});

		// pack();
		setVisible(true);

	}

	private void enableTogglePauseItem(boolean status) {
		this.pauseItemStatus = status;
		togglePauseItem.setEnabled(this.pauseItemStatus);
	}

	private void enableWithdrawalItem(boolean status) {
		this.withdrawalItemStatus = status;
		withdrawalItem.setEnabled(this.withdrawalItemStatus);
	}

	private class GameClockHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// Get button pressed
			JMenuItem pressed = (JMenuItem) (e.getSource());

			// Get text of button
			String text = pressed.getText().substring(6);

			if (text.equals("Up")) {
				userTime++;
			} else if (text.equals("Down")) {
				if (userTime <= MIN_CLOCK_TIME) {
					blinkTimer.start();
					userTime = MIN_CLOCK_TIME;
				} else {
					userTime--;
				}
			} else {
				userTime = DEFAULT_CLOCK_TIME;
			}

			clock = userTime;
			gameClock.setText(clock.toString());
		}
	}

	private boolean checkWinner() {
		if (cells[0].getText().equals(cells[1].getText())
				&& cells[1].getText().equals(cells[2].getText())) {
			return true;
		} else if (cells[3].getText().equals(cells[4].getText())
				&& cells[4].getText().equals(cells[5].getText())) {
			return true;
		} else if (cells[6].getText().equals(cells[7].getText())
				&& cells[7].getText().equals(cells[8].getText())) {
			return true;
		} else if (cells[0].getText().equals(cells[3].getText())
				&& cells[3].getText().equals(cells[6].getText())) {
			return true;
		} else if (cells[1].getText().equals(cells[4].getText())
				&& cells[4].getText().equals(cells[7].getText())) {
			return true;
		} else if (cells[2].getText().equals(cells[5].getText())
				&& cells[5].getText().equals(cells[8].getText())) {
			return true;
		} else if (cells[0].getText().equals(cells[4].getText())
				&& cells[4].getText().equals(cells[8].getText())) {
			return true;
		} else if (cells[2].getText().equals(cells[4].getText())
				&& cells[4].getText().equals(cells[6].getText())) {
			return true;
		} else {
			return false;
		}
	}


	private void checkContinueOrNot(String player, boolean draw) {
		String comment = null;
		Object[] options = { "Yes, one more try!", "No, I want a new game." };
		int n;

		if (draw) {
			comment =  "Draw! Continue?";
			n = JOptionPane.showOptionDialog(contentPane, comment, "Don't give up!!!",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, iconChasing, options, options[0]);
		} else {
			comment = player + " Wins! Continue?";
			n = JOptionPane.showOptionDialog(contentPane, comment, "Don't give up!!!",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, player
							.equals(playerJerry.getText()) ? iconJerryWin
							: iconTomWin, options, options[0]);
		}

		if (n == JOptionPane.YES_OPTION) {
			setContinueGame();
		} else if (n == JOptionPane.NO_OPTION) {
			setNewGame();
		} else {
			setContinueGame();
		}
	}

	private void setNewGame() {
		timer.stop();
		clock = userTime = DEFAULT_CLOCK_TIME;
		gameClock.setText(clock.toString());
		jerryWin = 0;
		tomWin = 0;
		jerryWinScore.setText(jerryWin.toString());
		tomWinScore.setText(tomWin.toString());
		resetGameBoard();
		enableTogglePauseItem(false);
		enableWithdrawalItem(false);
		enableClockControls(true);
		enableGameBoard(true);

		init();
	}

	private void setContinueGame() {
		clock = userTime;
		gameClock.setText(clock.toString());
		resetGameBoard();
		enableTogglePauseItem(false);
		enableWithdrawalItem(false);
		enableClockControls(true);
		init();
	}

	private void setEndGame() {
		// End of game
		timer.stop();
		gameOver = true;

		playSound("tada.wav");

		if (jerry) {
			tomWin++;
			tomWinScore.setText(tomWin.toString());
			checkContinueOrNot(playerTom.getText(), false);

		} else {
			jerryWin++;
			jerryWinScore.setText(jerryWin.toString());
			checkContinueOrNot(playerJerry.getText(), false);
		}


	}

	private void drawEndGame() {
		// End of game
		timer.stop();
		gameOver = true;
		playSound("chord.wav");
		checkContinueOrNot(playerTom.getText(), true);
	}

	private class NewItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setNewGame();
		}
	}

	private class PauseItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// Resume when paused
			if (pauseGame) {
				timer.start();
				enableClockControls(true);
				enableGameBoard(true);

				pauseGame = false;

				// Pause when not paused
			} else {
				timer.stop();
				enableClockControls(false);
				enableGameBoard(false);

				pauseGame = true;
			}
		}
	}

	private class SaveItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int temp;
			// declare JFileChooser
			JFileChooser fileChooser = new JFileChooser();

			// let the user choose the destination file
			if (fileChooser.showSaveDialog(ProjectFinal.this) == JFileChooser.APPROVE_OPTION) {
				// indicates whether the user still wants to export the settings
				boolean doExport = true;

				// indicates whether to override an already existing file
				boolean overrideExistingFile = false;

				// get destination file
				File destinationFile = new File(fileChooser.getSelectedFile()
						.getAbsolutePath());

				// check if file already exists
				while (doExport && destinationFile.exists()
						&& !overrideExistingFile) {

					temp = JOptionPane.showConfirmDialog(ProjectFinal.this, "Replace file?","Export Settings", JOptionPane.YES_NO_OPTION);
					System.out.println("temp: " + temp);
					System.out.println("the other: " + JOptionPane.YES_OPTION);
					// let the user decide whether to override the existing file
					overrideExistingFile = (temp == JOptionPane.YES_OPTION);
					System.out.println("override: " + overrideExistingFile);


					// let the user choose another file if the existing file
					// shall not be overridden
					if (!overrideExistingFile) {
						if (fileChooser.showSaveDialog(ProjectFinal.this) == JFileChooser.APPROVE_OPTION) {
							// get new destination file
							destinationFile = new File(fileChooser
									.getSelectedFile().getAbsolutePath());
						} else {
							// seems like the user does not want to export the
							// settings any longer
							//doExport = false;
							destinationFile.delete();
						}
					} else {
						destinationFile.delete();
					}
				}

				// perform the actual export
				//if (doExport) {
					writeFile(destinationFile, Integer.toString(userTime));
					writeFile(destinationFile, jerryWin.toString());
					writeFile(destinationFile, tomWin.toString());

					for (int i = 0; i < 9; i++) {
						writeFile(destinationFile, cells[i].getText());
					}
					// userTime, jerryWin, tomWin, cells[]
				//}
			}
		}
	}

	private void playSound(String soundName)
	 {
		/*
		new File((this.getClass().getResource("sounds/" + soundName )).toURI())
				*/
	   try
	   {
		System.out.println((this.getClass().getResource("sounds/" + soundName)).toURI());
	    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File((this.getClass().getResource("sounds/" + soundName)).toURI()));
	    Clip clip = AudioSystem.getClip( );
	    clip.open(audioInputStream);
	    clip.start( );
	   }
	   catch(Exception ex)
	   {
	     System.out.println("Error with playing sound.");
	     ex.printStackTrace( );
	   }
	 }

	private class OpenItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// declare JFileChooser
			JFileChooser fileChooser = new JFileChooser();

			// let the user choose the destination file
			if (fileChooser.showOpenDialog(ProjectFinal.this) == JFileChooser.APPROVE_OPTION) {
				// indicates whether the user still wants to export the settings
				// boolean doExport = true;

				// get destination file
				File readingFile = new File(fileChooser.getSelectedFile()
						.getAbsolutePath());

				readFromFile(readingFile);
			}

			userTime = Integer.parseInt(dataList.get(0));
			jerryWin = Integer.parseInt(dataList.get(1));
			jerryWinScore.setText(jerryWin.toString());
			tomWin = Integer.parseInt(dataList.get(2));
			tomWinScore.setText(tomWin.toString());

			restoreGame();
		}
	}

	private void readFromFile(File readingFile) {

		FileReader file = null;
		BufferedReader buf = null;
		dataList = new ArrayList<String>();

		try {

			file = new FileReader(readingFile);
			buf = new BufferedReader(file);

			String line = null;

			// myArea.setText(null);

			// Read the file until the end of the file
			while ((line = buf.readLine()) != null) {
				dataList.add(line);
			}

		} catch (IllegalArgumentException e) {
			System.out.println();
			System.err.println(e.getMessage());
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			System.out.println();
			System.err.println("File cannot be found: " + e.getMessage());

		} catch (IOException e) {
			System.out.println();
			System.err.println("Input/Output errors occurred: "
					+ e.getMessage());

		} finally {
			try {
				buf.close();
				file.close();
			} catch (IOException e) {
				System.out.println();
				System.err.println("Input/Output errors occurred: "
						+ e.getMessage());
				e.printStackTrace();
			} catch (NullPointerException e) {
				// if there is no file, then close() can't be executed because
				// buf has null
				System.out.println();
				System.err.println("File not found (Null Pointer): "
						+ e.getMessage());
			}
		}

	}

	private void writeFile(File destinationFile, String data) {

		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			// if file already exists, append the file
			fw = new FileWriter(destinationFile.getAbsolutePath(), true);
			bw = new BufferedWriter(fw);
			bw.write(data);
			bw.newLine();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class AboutItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new AboutProject();
		}
	}

	private class QuitItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	private class HelpItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new HelpProject();
		}
	}

	private class WithdrawalItemHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setEndGame();
		}
	}

	private class CellButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// If game over, ignore
			if (gameOver) {
				return;
			}

			enableClockControls(false);
			enableTogglePauseItem(true);
			enableWithdrawalItem(true);

			countingClock(userTime);

			// Get button pressed
			JButton pressed = (JButton) (e.getSource());

			// Get text of button
			String text = pressed.getText();

			// If noughts or crosses, ignore
			if (text.equals("O") || text.equals("X")) {
				return;
			}

			// Add nought or cross
			if (jerry) {
				pressed.setText("O");
				pressed.setIcon(iconJerryStanding);
				playSound("jerry.wav");
			} else {
				pressed.setText("X");
				pressed.setIcon(iconTomStanding);
				playSound("tom.wav");
			}



			changePlayer();
			// Check winner
			if (checkWinner()) {
				setEndGame();
			} else {
				if (checkDraw()) {
					drawEndGame();
				}
			}
		}
	}

}
