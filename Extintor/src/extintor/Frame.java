/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package extintor;

/*
 * TableFilterDemo.java requires SpringUtilities.java
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

public class Frame extends JPanel{

	static boolean DEBUG = true;
	static JTable table;
	private JTextField filterText;
	private JTextField statusText;
	static TableRowSorter<MyTableModel> sorter;

	static Data dt = new Data();
	private JButton saveButton;
	private JButton addButton;
	private JButton modifyButton;
	private JButton removeButton;
	private JButton printButton;
	private JButton proprietyButton;
	static Object[][] data = (Object[][]) dt.getData();
	final static MyTableModel model = new MyTableModel();
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	static Date currientDate;


	public Frame() {
		super();
		setLayout(new BorderLayout());
		try {
			currientDate = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e1) {}

		//Create a table with a sorter.
		sorter = new TableRowSorter<MyTableModel>(model);
		table = new JTable(model);
		table.setRowSorter(sorter);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		//When selection changes, provide user with row numbers for
		//both view and model.
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int viewRow = table.getSelectedRow();
						if (viewRow < 0) {
							//Selection got filtered away.
							statusText.setText("");
						} else {
							int modelRow = 
									table.convertRowIndexToModel(viewRow);
							statusText.setText(
									String.format("Selected Row in view: %d. " +
											"Selected Row in model: %d.", 
											viewRow, modelRow));
						}
					}
				});


		//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		//Add the scroll pane to this panel.
		add(scrollPane, BorderLayout.CENTER);

		//Create a separate form for filterText and statusText
		JPanel form = new JPanel(new FlowLayout());
		JLabel l1 = new JLabel("Filter Text:", SwingConstants.TRAILING);
		form.add(l1);
		filterText = new JTextField(5);
		//Whenever filterText changes, invoke newFilter.
		filterText.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						newFilter();
					}
					public void insertUpdate(DocumentEvent e) {
						newFilter();
					}
					public void removeUpdate(DocumentEvent e) {
						newFilter();
					}
				});
		l1.setLabelFor(filterText);
		form.add(filterText);
		JLabel l2 = new JLabel("Status:", SwingConstants.TRAILING);
		form.add(l2);
		statusText = new JTextField(5);
		l2.setLabelFor(statusText);
		form.add(statusText);
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				export();
				dt.write();
			}
		});
		form.add(saveButton);

		modifyButton = new JButton("Modify");
		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ModifyDialog().modifyDialog(table.getSelectedRows());
			}
		});
		form.add(modifyButton);


		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new AddDialog().addDialog();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		form.add(addButton);

		proprietyButton = new JButton("Propriety");
		proprietyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRows().length>0)
					new ProprietyDialog()
				.proprietyDialog(table.getSelectedRows());
			}
		});
		form.add(proprietyButton);


		removeButton = new JButton("Delete");
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RemoveDialog(table.getSelectedRows());
//				if (table.getSelectedRowCount()>1){
//					if (deleteDialogs()>1){
//						model.removeRows(table.getSelectedRows());
//						data = null;
//						data = (Object[][]) dt.getData();
//						export();
//						sorter = new TableRowSorter<MyTableModel>(model);
//						table.setRowSorter(sorter);
//						table.updateUI();
//					}
//
//				} else{
//					if (deleteDialog()<1){
//						model.removeRow(table.getSelectedRow());
//						data = null;
//						data = (Object[][]) dt.getData();
//						export();
//						sorter = new TableRowSorter<MyTableModel>(model);
//						table.setRowSorter(sorter);
//						table.updateUI();
//					}
//				}
			}
		});
		form.add(removeButton);


		printButton = new JButton("Print");
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat("Page {0,number,integer}");
				try {
					table.print(JTable.PrintMode.FIT_WIDTH, null, null);
				} catch (java.awt.print.PrinterException e1) {
					System.err.format("Cannot print %s%n", e1.getMessage());
				}
			}
		});
		form.add(printButton);

		add(form, BorderLayout.AFTER_LAST_LINE);
	}

	/** 
	 * Update the row filter regular expression from the expression in
	 * the text box.
	 */
	private void newFilter() {
		RowFilter<MyTableModel, Object> rf = null;
		//If current expression doesn't parse, don't update.
		try {
			rf = RowFilter.regexFilter("(?i)"+filterText.getText(),0,1,2,3,4,5);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}
	static void export() {
		for (int j = 0; j < Data.id.size(); j++) {
			Data.id.set(j, (String) data[j][0]);
			Data.cliente.set(j, (String) data[j][1]);
			Data.clienteID.set(j, (String) data[j][2]);
			Data.extintorClasse.set(j, (String) data[j][3]);
			Data.extintorCarga.set(j, (String) data[j][4]);
			Data.extintorCapacidade.set(j, (String) data[j][5]);
			Data.extintorVencimento.set(j, (String) data[j][6]);
			Data.extintorFabricacao.set(j, (String) data[j][7]);
			Data.extintorUltimaAtualizacao.set(j, (String) data[j][8]);
			Data.extintorPrimeiraAtualizacao.set(j, (String) data[j][9]);
		}
		dt.write();
	}


	public int deleteDialogs(){
		Object[] options = {"Sim",
				"Sim para todos","N�o",
		"N�o para todos"};
		int n = JOptionPane.showOptionDialog(this,
				"Tem certeza que deseja remover os itens selecionados",
				"Aviso",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[2]);
		return n;
	}

	public int deleteDialog(){
		Object[] options = {"Sim","N�o"};
		int n = JOptionPane.showOptionDialog(this,
				"Tem certeza que deseja remover os itens selecionados",
				"Aviso",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[1]);
		return n;
	}


	public static int valueChanged(int tableSelectedRow) {
		int viewRow = tableSelectedRow;
		if (viewRow < 0) {
			//Selection got filtered away.
			return (Integer) null;
		} else {
			int modelRow = 
					table.convertRowIndexToModel(viewRow);
			return modelRow;
		}
	}

	public static String[] getItens(){
		String item[] = {
				"Id",
				"Cliente",
				"Cliente ID",
				"Classe",
				"Carga",
				"Capacidade",
				"Vencimento",
				"Fabrica��o",
				"Ultima atualiza��o",
		"Primeira atualiza��o"};
		return item;
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
		//		frame.pack();
		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
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