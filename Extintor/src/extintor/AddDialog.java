package extintor;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

public class AddDialog {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private Date currientDate;

	public void addDialog() throws ParseException{
		final JDialog dialog = new JDialog(Frame.frame, "Add", ModalityType.APPLICATION_MODAL);
		new BlockDialog(Frame.frame, dialog);

		try {
			currientDate = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e1) {}

		JPanel addPanel = new JPanel(new BorderLayout());
		JPanel addPanelGrid = new JPanel(new GridLayout(0,1));
		JPanel addPanelGrid1 = new JPanel(new GridLayout(0,1));
		JPanel addPanelGrid2 = new JPanel(new BorderLayout());
		//		addPanelGrid.setLayout(new BoxLayout(addPanelGrid, BoxLayout.Y_AXIS));

		JPanel p1 = new JPanel(new BorderLayout());
		JPanel p11 = new JPanel(new BorderLayout());
		final JTextField idField = new JTextField(Data.id.get(Data.id.size()-1));
		p11.add(new Label(Frame.getItens()[0]),BorderLayout.WEST);
		p1.add(idField,BorderLayout.CENTER);
		addPanelGrid.add(p1);
		addPanelGrid1.add(p11);

		JPanel p2 = new JPanel(new BorderLayout());
		JPanel p21 = new JPanel(new BorderLayout());
		final JTextField clienteField = new JTextField(Data.cliente.get(Data.cliente.size()-1));
		p21.add(new Label(Frame.getItens()[1]),BorderLayout.WEST);
		p2.add(clienteField,BorderLayout.CENTER);
		addPanelGrid.add(p2);
		addPanelGrid1.add(p21);
		
		JPanel p3 = new JPanel(new BorderLayout());
		JPanel p31 = new JPanel(new BorderLayout());
		final JTextField clienteIDField = new JTextField(Data.clienteID.get(Data.clienteID.size()-1));
		p31.add(new Label(Frame.getItens()[2]),BorderLayout.WEST);
		p3.add(clienteIDField,BorderLayout.CENTER);
		addPanelGrid.add(p3);
		addPanelGrid1.add(p31);

		JPanel p4 = new JPanel(new BorderLayout());
		JPanel p41 = new JPanel(new BorderLayout());
		p41.add(new Label(Frame.getItens()[3]),BorderLayout.WEST);
		final JComboBox<String> classeCombo = ComboBoxes.comboType();
		p4.add(classeCombo,BorderLayout.CENTER);
		classeCombo.setSelectedItem(Data.extintorClasse.get(Data.extintorClasse.size()-1));
		addPanelGrid.add(p4);
		addPanelGrid1.add(p41);

		JPanel p5 = new JPanel(new BorderLayout());
		JPanel p51 = new JPanel(new BorderLayout());
		p51.add(new Label(Frame.getItens()[4]),BorderLayout.WEST);
		final JComboBox<String> cargaCombo = ComboBoxes.comboCarga();
		p5.add(cargaCombo,BorderLayout.CENTER);
		cargaCombo.setSelectedItem(Data.extintorCarga.get(Data.extintorCarga.size()-1));
		addPanelGrid.add(p5);
		addPanelGrid1.add(p51);

		JPanel p6 = new JPanel(new BorderLayout());
		JPanel p61 = new JPanel(new BorderLayout());
		final JTextField capacidadeField = new JTextField(Data.extintorCapacidade.get(Data.extintorCapacidade.size()-1));
		p61.add(new Label(Frame.getItens()[5]),BorderLayout.WEST);
		p6.add(capacidadeField,BorderLayout.CENTER);
		addPanelGrid.add(p6);
		addPanelGrid1.add(p61);

		JPanel p7 = new JPanel(new BorderLayout());
		JPanel p71 = new JPanel(new BorderLayout());
		p71.add(new Label(Frame.getItens()[6]),BorderLayout.WEST);
		final JDateChooser dcVencimento = new JDateChooser("d-MMMM-yyyy", false);
		dcVencimento.setDate(dateFormat.parse(Data.extintorVencimento.get(Data.extintorVencimento.size()-1)));
		p7.add(dcVencimento,BorderLayout.CENTER);
		addPanelGrid.add(p7);
		addPanelGrid1.add(p71);

		JPanel p8 = new JPanel(new BorderLayout());
		JPanel p81 = new JPanel(new BorderLayout());
		p81.add(new Label(Frame.getItens()[7]),BorderLayout.WEST);
		final JDateChooser dcFabricacao = new JDateChooser("d-MMMM-yyyy", false);
		p8.add(dcFabricacao,BorderLayout.CENTER);
		dcFabricacao.setDate(dateFormat.parse(Data.extintorFabricacao.get(Data.extintorFabricacao.size()-1)));
		addPanelGrid.add(p8);
		addPanelGrid1.add(p81);

		addPanelGrid2.add(addPanelGrid1, BorderLayout.WEST);
		addPanelGrid2.add(addPanelGrid, BorderLayout.CENTER);
		addPanel.add(addPanelGrid2, BorderLayout.NORTH);


		JPanel bottonPanel = new JPanel();
		bottonPanel.setLayout(new BoxLayout(bottonPanel,
				BoxLayout.LINE_AXIS));
		bottonPanel.add(Box.createHorizontalGlue());
		bottonPanel.setBorder(BorderFactory.
				createEmptyBorder(0,0,5,5));


		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				if (dcVencimento.getDate().compareTo(currientDate)<0){
					JOptionPane
					.showMessageDialog(dialog,
							"O extintor n�o pode vencer "
									+ "em data anterior a de hoje.",
									"Erro de data",
									JOptionPane.ERROR_MESSAGE);
				} else if (dcFabricacao.getDate().compareTo(currientDate)>0){
					JOptionPane
					.showMessageDialog(dialog,
							"O extintor n�o pode "
									+ "ser fabricado amanh�.",
									"Erro de data",
									JOptionPane.ERROR_MESSAGE);
				}else{
					Frame.export();
					Frame.model.addRow(idField.getText(),
							clienteField.getText(),
							clienteIDField.getText(),
							classeCombo.getItemAt(classeCombo.getSelectedIndex()),
							cargaCombo.getItemAt(cargaCombo.getSelectedIndex()),
							capacidadeField.getText(),
							dateFormat.format(dcVencimento.getDate()),
							dateFormat.format(dcFabricacao.getDate()));

					Frame.data = null;
					Frame.data = (Object[][]) Frame.dt.getData();
					Frame.sorter = new TableRowSorter<MyTableModel>(Frame.model);
					Frame.table.setRowSorter(Frame.sorter);
					Frame.table.updateUI();
					Frame.export();
					dialog.dispose();
				}
			}

		});

	
		bottonPanel.add(okButton);
		bottonPanel.add(cancelButton);

		addPanel.add(bottonPanel, BorderLayout.PAGE_END);
		addPanel.setOpaque(true);
		dialog.setContentPane(addPanel);

		//Show it.
		dialog.setSize(new Dimension(400, 300));
		//        dialog.pack();
		dialog.setLocationRelativeTo(Frame.frame);
		dialog.setVisible(true);

	}

	
}
