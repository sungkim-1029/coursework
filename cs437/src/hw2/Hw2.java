package hw2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Hw2 {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private JFrame mainFrame;
    private JTextArea myArea;

    private String usrInput;

    private void initUI() {

        mainFrame = new JFrame("HW2");
        mainFrame.setSize(WIDTH, HEIGHT); // can be changed by user
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
    }

    private void createMenuBar() {

        // create menu bar and attach it to AwtMenuTest window
        JMenuBar bar = new JMenuBar();
        mainFrame.setJMenuBar(bar);

        // set up file menu
        JMenu fileMenu = new JMenu("File");
        bar.add(fileMenu); // add Set Value menu to menu bar
        // set up File items
        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);
        JMenuItem quitItem = new JMenuItem("Quit");
        fileMenu.add(quitItem);

        // set up edit menu
        JMenu editMenu = new JMenu("Edit");
        bar.add(editMenu); // add Set Value menu to menu bar
        // set up File items
        JMenuItem clearItem = new JMenuItem("Clear");
        editMenu.add(clearItem);

        // Listners with actions in line
        openItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                usrInput = JOptionPane.showInputDialog("Please enter a file name:");
                if (usrInput == null) {
                    System.out.println("cancelled!");
                } else {
                    if (readFromFile(usrInput)) {
                        System.out.println("Success!");
                    } else {
                        myArea.setText("");
                        myArea.setText("File not found!");
                        System.out.println("File not found!");
                    }
                }
            }
        });

        quitItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                System.exit(0);
            }
        });

        clearItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                myArea.setText(null);

            }
        });
    }

    private void createTextArea() {

        myArea = new JTextArea();
        myArea.setForeground(Color.GREEN);
        myArea.setBackground(Color.black);

        mainFrame.add(myArea);
    }

    public void showHw2() {

        mainFrame.setVisible(true);
    }

    public Hw2() {

        initUI();
        createMenuBar();
        createTextArea();
    }

    public static void main(String[] args) // no args expected
    {

        Hw2 h = new Hw2();
        h.showHw2();

    }

    private boolean readFromFile(String fileName) {

        final String PATH = "./src/";
        FileReader file = null;
        BufferedReader buf = null;

        try {

            file = new FileReader(PATH + fileName);
            buf = new BufferedReader(file);

            String line = null;

            myArea.setText(null);

            // Read the file until the end of the file
            while ((line = buf.readLine()) != null) {

                myArea.append(line);
                myArea.append("\n");
            }

            return true;

        } catch (IllegalArgumentException e) {
            System.out.println();
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;

        } catch (FileNotFoundException e) {
            System.out.println();
            System.err.println("File cannot be found: " + e.getMessage());
            return false;

        } catch (IOException e) {
            System.out.println();
            System.err.println("Input/Output errors occurred: "
                    + e.getMessage());
            return false;

        }/* catch (Exception e) {
            System.out.println();
            System.err.println("something wrong: " + e.getMessage());
            return false;
        }*/ finally {
            try {
                buf.close();
                file.close();
            } catch (IOException e) {
                System.out.println();
                System.err.println("Input/Output errors occurred: " + e.getMessage());
                e.printStackTrace();
                return false;
            } catch (NullPointerException e) {
                // if there is no file, then close() can't be executed because
                // buf has null
                System.out.println();
                System.err.println("File not found (Null Pointer): " + e.getMessage());
                return false;
            }
        }

    }

}
