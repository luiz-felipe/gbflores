package extintor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.JPanel;

public class InfoItemPanel {
	int selected;
	
	public InfoItemPanel(int tableSelected){
		selected = tableSelected;
	}

	public String getItemName(){
		return Data.id.get(selected);
	}

	public JPanel getInfoPanel(){
		JPanel addPanel = new JPanel(new BorderLayout());

		JPanel addPanelGrid;
		JPanel addPanelGrid1;
		JPanel addPanelGrid2;

		addPanelGrid = new JPanel(new GridLayout(0,1));
		addPanelGrid1 = new JPanel(new GridLayout(0,1));
		addPanelGrid2 = new JPanel(new BorderLayout());
		final int tableSelectedRow = MyTableModel.valueChanged(selected);

		JPanel p1 = new JPanel(new BorderLayout());
		JPanel p11 = new JPanel(new BorderLayout());
		p1.add(new Label(Data.id.get(tableSelectedRow)));
		p11.add(new Label(Frame.getItens()[0]+": "));
		addPanelGrid.add(p1);
		addPanelGrid1.add(p11);

		JPanel p2 = new JPanel(new BorderLayout());
		JPanel p21 = new JPanel(new BorderLayout());
		p2.add(new Label(Data.cliente.get(tableSelectedRow)));
		p21.add(new Label(Frame.getItens()[1]+": "));
		addPanelGrid.add(p2);
		addPanelGrid1.add(p21);

		JPanel p3 = new JPanel(new BorderLayout());
		JPanel p31 = new JPanel(new BorderLayout());
		p3.add(new Label(Data.clienteID.get(tableSelectedRow)));
		p31.add(new Label(Frame.getItens()[2]+": "));
		addPanelGrid.add(p3);
		addPanelGrid1.add(p31);

		JPanel p4 = new JPanel(new BorderLayout());
		JPanel p41 = new JPanel(new BorderLayout());
		p4.add(new Label(Data.extintorClasse.get(tableSelectedRow)));
		p41.add(new Label(Frame.getItens()[3]+": "));
		addPanelGrid.add(p4);
		addPanelGrid1.add(p41);

		JPanel p5 = new JPanel(new BorderLayout());
		JPanel p51 = new JPanel(new BorderLayout());
		p5.add(new Label(Data.extintorCarga.get(tableSelectedRow)));
		p51.add(new Label(Frame.getItens()[4]+": "));
		addPanelGrid.add(p5);
		addPanelGrid1.add(p51);

		JPanel p6 = new JPanel(new BorderLayout());
		JPanel p61 = new JPanel(new BorderLayout());
		p6.add(new Label(Data.extintorCapacidade.get(tableSelectedRow)));
		p61.add(new Label(Frame.getItens()[5]+": "));
		addPanelGrid.add(p6);
		addPanelGrid1.add(p61);

		JPanel p7 = new JPanel(new BorderLayout());
		JPanel p71 = new JPanel(new BorderLayout());
		p7.add(new Label(Data.extintorVencimento.get(tableSelectedRow)));
		p71.add(new Label(Frame.getItens()[6]+": "));
		addPanelGrid.add(p7);
		addPanelGrid1.add(p71);

		JPanel p8 = new JPanel(new BorderLayout());
		JPanel p81 = new JPanel(new BorderLayout());
		p8.add(new Label(Data.extintorFabricacao.get(tableSelectedRow)));
		p81.add(new Label(Frame.getItens()[7]+": "));
		addPanelGrid.add(p8);
		addPanelGrid1.add(p81);

		JPanel p9 = new JPanel(new BorderLayout());
		JPanel p91 = new JPanel(new BorderLayout());
		p9.add(new Label(Data.extintorUltimaAtualizacao.get(tableSelectedRow)));
		p91.add(new Label(Frame.getItens()[8]+": "));
		addPanelGrid.add(p9);
		addPanelGrid1.add(p91);

		JPanel p10 = new JPanel(new BorderLayout());
		JPanel p101 = new JPanel(new BorderLayout());
		p10.add(new Label(Data.extintorPrimeiraAtualizacao.get(tableSelectedRow)));
		p101.add(new Label(Frame.getItens()[9]+": "));
		addPanelGrid.add(p10);
		addPanelGrid1.add(p101);

		addPanelGrid2.add(addPanelGrid1, BorderLayout.WEST);
		addPanelGrid2.add(addPanelGrid, BorderLayout.CENTER);
		addPanel.add(addPanelGrid2, BorderLayout.NORTH);
		
		return addPanel;
	}
}
