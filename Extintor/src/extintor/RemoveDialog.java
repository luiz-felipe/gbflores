package extintor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableRowSorter;

public class RemoveDialog {

	boolean deleteAll = false;
	boolean delete = false;
	boolean keepAll = false;
	boolean notDelete = false;

	public void removeRows(int selected){

		final JDialog dialog = new JDialog(Frame.frame, "Dlete", ModalityType.APPLICATION_MODAL);
		new BlockDialog(Frame.frame, dialog);
		final int tableSelectedRow = Frame.valueChanged(selected);
		InfoItemPanel info = new InfoItemPanel(tableSelectedRow);

		JPanel addPanel = new JPanel(new BorderLayout());
		JPanel upPanel = new JPanel(new GridLayout(0,1));
		upPanel.add(new JLabel());
		upPanel.add(new JLabel("Tem certeza que deseja remover o "
				+ "\nitem :\""+info.getItemName()+"\""));
		upPanel.add(new JLabel());

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(info.getInfoPanel());
		centerPanel.setBackground(Color.RED);
		centerPanel.setBorder(BorderFactory.
				createLoweredBevelBorder());
		//					createLineBorder(Color.BLACK));

		JPanel bottonPanel = new JPanel();
		bottonPanel.setLayout(new BoxLayout(bottonPanel,
				BoxLayout.LINE_AXIS));
		bottonPanel.add(Box.createHorizontalGlue());
		bottonPanel.setBorder(BorderFactory.
				createEmptyBorder(0,0,5,5));

		JButton yesButton = new JButton("Sim");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete = true;
				deleteAll = false;
				keepAll = false;
				notDelete = false;
				dialog.dispose();
			}
		});

		JButton yesAllButton = new JButton("Sim para todos");
		yesAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAll = true;
				delete = false;
				keepAll = false;
				notDelete = false;
				dialog.dispose();
			}
		});

		JButton noButton = new JButton("N�o");
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAll = false;
				delete = false;
				keepAll = false;
				notDelete = true;
				dialog.dispose();
			}
		});

		JButton noAllButton = new JButton("N�o para todos");
		noAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteAll = true;
				delete = false;
				keepAll = false;
				notDelete = false;
				dialog.dispose();
			}
		});

		bottonPanel.add(yesButton);
		bottonPanel.add(yesAllButton);
		bottonPanel.add(noAllButton);
		bottonPanel.add(noButton);

		addPanel.add(upPanel, BorderLayout.PAGE_START);
		addPanel.add(centerPanel, BorderLayout.CENTER);
		addPanel.add(bottonPanel, BorderLayout.PAGE_END);
		addPanel.setOpaque(true);
		dialog.setContentPane(addPanel);

		//Show it.
		dialog.pack();
		dialog.setLocationRelativeTo(Frame.frame);
		dialog.setVisible(true);	

	}

	
	public void removeOneRow(int selected){

		final JDialog dialog = new JDialog(Frame.frame, "Dlete", ModalityType.APPLICATION_MODAL);
		new BlockDialog(Frame.frame, dialog);
		final int tableSelectedRow = Frame.valueChanged(selected);
		InfoItemPanel info = new InfoItemPanel(tableSelectedRow);

		JPanel addPanel = new JPanel(new BorderLayout());
		JPanel upPanel = new JPanel(new GridLayout(0,1));
		upPanel.add(new JLabel());
		upPanel.add(new JLabel("Tem certeza que deseja remover o "
				+ "\nitem :\""+info.getItemName()+"\""));
		upPanel.add(new JLabel());

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(info.getInfoPanel());
		centerPanel.setBackground(Color.RED);
		centerPanel.setBorder(BorderFactory.
				createLoweredBevelBorder());
		//					createLineBorder(Color.BLACK));

		JPanel bottonPanel = new JPanel();
		bottonPanel.setLayout(new BoxLayout(bottonPanel,
				BoxLayout.LINE_AXIS));
		bottonPanel.add(Box.createHorizontalGlue());
		bottonPanel.setBorder(BorderFactory.
				createEmptyBorder(0,0,5,5));

		JButton yesButton = new JButton("Sim");
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame.model.removeRow(tableSelectedRow);
				dialog.dispose();
				Frame.data = null;
				Frame.data = (Object[][]) Frame.dt.getData();
				Frame.export();
				Frame.sorter = new TableRowSorter<MyTableModel>(Frame.model);
				Frame.table.setRowSorter(Frame.sorter);
				Frame.table.updateUI();

			}
		});

		JButton noButton = new JButton("N�o");
		noButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notDelete = true;
				dialog.dispose();
			}
		});

		bottonPanel.add(yesButton);
		bottonPanel.add(noButton);

		addPanel.add(upPanel, BorderLayout.PAGE_START);
		addPanel.add(centerPanel, BorderLayout.CENTER);
		addPanel.add(bottonPanel, BorderLayout.PAGE_END);
		addPanel.setOpaque(true);
		dialog.setContentPane(addPanel);

		//Show it.
		dialog.pack();
		dialog.setLocationRelativeTo(Frame.frame);
		dialog.setVisible(true);	

	}

	public RemoveDialog(int [] tableSelected){
		if (tableSelected.length==1){
			removeOneRow(tableSelected[0]);
		}else{
			for (int i = tableSelected.length-1; i >=0 ; i--) {
				if (i==tableSelected.length-1)
					removeRows(tableSelected[i]);
				else{
					if (deleteAll){
						Frame.model.removeRow(tableSelected[i]);
						Frame.data = null;
						Frame.data = (Object[][]) Frame.dt.getData();
						Frame.export();
						Frame.sorter = new TableRowSorter<MyTableModel>(Frame.model);
						Frame.table.setRowSorter(Frame.sorter);
						Frame.table.updateUI();
					}
					if (delete){
						deleteAll = false;
						delete = false;
						keepAll = false;
						notDelete = false;
						Frame.model.removeRow(tableSelected[i]);
						Frame.data = null;
						Frame.data = (Object[][]) Frame.dt.getData();
						Frame.export();
						Frame.sorter = new TableRowSorter<MyTableModel>(Frame.model);
						Frame.table.setRowSorter(Frame.sorter);
						Frame.table.updateUI();
						if (i==0){
							removeOneRow(tableSelected[i]);
						} else{
							removeRows(tableSelected[i]);
						}
					}
					if (keepAll){
						return;
					}
					if (notDelete){
						deleteAll = false;
						delete = false;
						keepAll = false;
						notDelete = false;
						if (i==0){
							removeOneRow(tableSelected[i]);
						} else{
							removeRows(tableSelected[i]);
						}
					}
				}
			}
		}
	}
}
