

package br.ufrgs.enq.gbflores;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Frame extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField fileInText;
	private JTextField fileOutText;
	private JTextField compressaoText;
	private JButton tButton;
	private JButton uButton;
	private JButton tauButton;

	public Frame() {
		super();
		//		setLayout(new BorderLayout());
		//Create a table with a sorter.
		
		//Create a separate form for filterText and statusText
		JPanel form = new JPanel(new GridLayout(0,1));
		JPanel formButtons = new JPanel(new FlowLayout());
		JPanel formText = new JPanel(new GridLayout(0,1));
		JPanel formText1 = new JPanel(new BorderLayout());
		JPanel formText2 = new JPanel(new BorderLayout());
		JLabel l1 = new JLabel("File input:", SwingConstants.TRAILING);
		formText1.add(l1, BorderLayout.WEST);
		fileInText = new JTextField();
		l1.setLabelFor(fileInText);
		formText1.add(fileInText);

		JLabel l2 = new JLabel("File output:", SwingConstants.TRAILING);
		formText2.add(l2, BorderLayout.WEST);
		fileOutText = new JTextField();
		l2.setLabelFor(fileOutText);
		formText2.add(fileOutText);

		formText.add(formText1);
		formText.add(formText2);



		tButton = new JButton("Convert T");
		tButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t1 = new Thread(new Runnable() {
					public void run() {
						frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						String name = fileChooser();
						if (name!=null)
							try {
								T t = new T(name, Integer.parseInt(compressaoText.getText()));
								fileInText.setText(t.fileIn.toString());
								fileOutText.setText(t.fileOut.toString());

							} catch (IOException e1) {
								fileInText.setText("");
								fileInText.setText("");
								frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

							}

						frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}

				});
				t1.start();
			}
		});
		formButtons.add(tButton);

		uButton = new JButton("Convert U");
		uButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t1 = new Thread(new Runnable() {
					public void run() {
						frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						String name = fileChooser();
						if (name!=null)
							try {
								U t = new U(name, Integer.parseInt(compressaoText.getText()));
								fileInText.setText(t.fileIn.toString());
								fileOutText.setText(t.fileOut.toString());

							} catch (IOException e1) {
								fileInText.setText("");
								fileInText.setText("");
								frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

							}

						frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}

				});
				t1.start();
			}
		});
		formButtons.add(uButton);


		tauButton = new JButton("Convert Tau");
		tauButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Thread t1 = new Thread(new Runnable() {
					public void run() {
						frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						String name = fileChooser();
						if (name!=null)
							try {
								Tau t = new Tau(name, Integer.parseInt(compressaoText.getText()));
								fileInText.setText(t.fileIn.toString());
								fileOutText.setText(t.fileOut.toString());

							} catch (IOException e1) {
								fileInText.setText("");
								fileInText.setText("");
								frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							}

						frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}

				});
				t1.start();
			}
		});
		formButtons.add(tauButton);

		JLabel l3 = new JLabel("Compresao:", SwingConstants.TRAILING);
		formButtons.add(l3, BorderLayout.WEST);
		compressaoText = new JTextField("100",4);
		l3.setLabelFor(compressaoText);
		formButtons.add(compressaoText);


		form.add(formText);
		form.add(formButtons);

		add(form, BorderLayout.PAGE_END);
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	static JFrame frame = new JFrame("TableFilterDemo");
	private static void createAndShowGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		//Create and set up the window.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		Frame newContentPane = new Frame();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);
		//		frame.setUndecorated(true);
		//		frame.setOpacity(0.5f);

		//Display the window.
		frame.pack();
		//		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public String fileChooser(){
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(frame);
		String name = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			name = file.getAbsolutePath();
		}
		return name;
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}