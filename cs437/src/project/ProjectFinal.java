package project;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ProjectFinal extends JFrame {
	private static final String TITLE = "Project Tic Tac Toe";
	private static final int WIDTH = 450;
	private static final int HEIGHT = 472;

	public ProjectFinal() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		createMenuBar();
		
        //System.out.println(menuBar.getPreferredSize());
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
        JMenuItem pauseItem = new JMenuItem("Pause");
        gameMenu.add(pauseItem);
        // set up game items
        JMenuItem resumeItem = new JMenuItem("Resume");
        gameMenu.add(resumeItem);
        
        
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
