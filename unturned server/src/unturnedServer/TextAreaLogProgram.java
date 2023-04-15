/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unturnedServer;

/**
 * This takes the console and puts it in the textarea on the GUI
 * @author root
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

@SuppressWarnings("serial")
public class TextAreaLogProgram extends JFrame {

	/**
	 * The text area which is used for displaying logging information.
	 */
	private JTextArea textArea;

	private JButton buttonStart = new JButton("Start");
	private JButton buttonClear = new JButton("Clear");

	private PrintStream standardOut;

	static File cmd = new File("server\\steamcmd.exe");
	static String path = cmd.getAbsolutePath();

	public TextAreaLogProgram() {
		super(" CONSOLE ");

		textArea = new JTextArea(50, 10);
		textArea.setEditable(false);
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));

		// keeps reference of standard output stream
		standardOut = System.out;

		// re-assigns standard output stream and error output stream
		System.setOut(printStream);
		System.setErr(printStream);

		// creates the GUI
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.anchor = GridBagConstraints.WEST;

		add(buttonStart, constraints);

		constraints.gridx = 1;
		add(buttonClear, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		add(new JScrollPane(textArea), constraints);

		// adds event handler for button Start
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				printLog(cmd, path);
			}
		});

		// adds event handler for button Clear
		buttonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// clears the text area
				try {
					textArea.getDocument().remove(0, textArea.getDocument().getLength());
					standardOut.println("Text area cleared");
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480, 320);
		setLocationRelativeTo(null); // centers on screen
	}

	/**
	 * Prints log statements for testing in a thread
	 */
	static void printLog(File cmd, String path) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				System.out.println("Time now is " + (new Date()));
				System.out.println("please wait for cmd to start...");
				@SuppressWarnings("unused")
				String s = null;

				try {

					if (!cmd.exists()) {
						throw new IllegalArgumentException("The file " + path + " does not exist");
					}
					Process process = Runtime.getRuntime().exec(path + " +runscript Commands.txt");

					InputStream lsOut = process.getInputStream();
					InputStreamReader r = new InputStreamReader(lsOut);
					BufferedReader in = new BufferedReader(r);

					// read the child process' output
					String line;
					while ((line = in.readLine()) != null) {
						System.out.println(line);
						// You should set JtextArea
					}
					System.out.println("you may now press continue");
				} catch (Exception e) { // exception thrown

					System.err.println("Command failed!");

				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}

			}
		});
		thread.start();
	}

	/**
	 * Runs the program
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TextAreaLogProgram().setVisible(true);
			}
		});

	}
}
