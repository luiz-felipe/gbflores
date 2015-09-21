package br.ufrgs.enq.gbflores;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class Frame extends JFrame{

	public Frame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JTabbedPane tabbedPane2;
		
		ArrayList<String> compundList = new ArrayList<String>();
		compundList.add("Water");
		compundList.add("methane");
		compundList.add("ACETONITRILE");
		CEOSChart2D eos;
		for (int i = 0; i < compundList.size(); i++) {
			try {
				tabbedPane2 = new JTabbedPane();
				
				eos = new CEOSChart2D(CEoS.PR, compundList.get(i));
				tabbedPane2.addTab(CEoS.PR, null, eos, eos.getToolTip());

				eos = new CEOSChart2D(CEoS.RK, compundList.get(i));
				tabbedPane2.addTab(CEoS.RK, null, eos, eos.getToolTip());
	
				eos = new CEOSChart2D(CEoS.SRK, compundList.get(i));
				tabbedPane2.addTab(CEoS.SRK, null, eos, eos.getToolTip());
	
				eos = new CEOSChart2D(CEoS.VdW, compundList.get(i));
				tabbedPane2.addTab(CEoS.VdW, null, eos, eos.getToolTip());
		
				tabbedPane.addTab(eos.getComp(), null, tabbedPane2, eos.getToolTip());
			} catch (MissingCompoundException e) {
				JOptionPane.showMessageDialog(this,
					    "Componente "+compundList.get(i).toUpperCase()+" não encontrado",
					    "Missing Compound Exception",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
		
//		tabbedPane.addTab("3D", null, new Chart3D().drawingPanel, "new 3D");
//		tabbedPane.add(new JLabel("4D"), "4D");
		
		if (tabbedPane.getTabCount()>0){
			add(tabbedPane);
			pack();
			setLocationRelativeTo(null);
			setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this,
				    "Nada para ser mostrado",
				    "Inane error",
				    JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}
	}

}
