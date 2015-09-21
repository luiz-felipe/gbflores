package extintor;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ProprietyDialog {
	public void proprietyDialog(int [] tableSelected){
		
		final JDialog dialog = new JDialog(Frame.frame, "Propriety", ModalityType.APPLICATION_MODAL);
		new BlockDialog(Frame.frame, dialog);
		
		JPanel addPanel = new JPanel(new BorderLayout());
		
		final int tableSelectedRows[] = tableSelected;
		JTabbedPane t1 = new JTabbedPane();
		for (int i = 0; i < tableSelectedRows.length; i++) {
			InfoItemPanel info = new InfoItemPanel(tableSelectedRows[i]);
			t1.addTab((info.getItemName()), info.getInfoPanel());
		}

		addPanel.add(t1,BorderLayout.NORTH);

		JPanel bottonPanel = new JPanel();
		bottonPanel.setLayout(new BoxLayout(bottonPanel,
				BoxLayout.LINE_AXIS));
		bottonPanel.add(Box.createHorizontalGlue());
		bottonPanel.setBorder(BorderFactory.
				createEmptyBorder(0,0,5,5));


		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
			}
		});

		bottonPanel.add(closeButton);

		addPanel.add(bottonPanel, BorderLayout.PAGE_END);
		addPanel.setOpaque(true);
		dialog.setContentPane(addPanel);

		//Show it.
		dialog.setSize(new Dimension(400, 300));
		dialog.pack();
		dialog.setLocationRelativeTo(Frame.frame);
		dialog.setVisible(true);
	}
	

}
