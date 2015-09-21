package extintor;

import java.text.ParseException;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

	//	static Data dt = new Data();
	//	static Object[][] data = (Object[][]) dt.getData();
	//	private static boolean DEBUG = true;
	//	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	//	private static Date currientDate;
	//	static JTable table;


	private String[] columnNames = Frame.dt.headers;


	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return Frame.data.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		return Frame.data[row][col];
	}

	/*
	 * JTable uses this method to determine the default renderer/
	 * editor for each cell.  If we didn't implement this method,
	 * then the last column would contain text ("true"/"false"),
	 * rather than a check box.
	 */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's
	 * editable.
	 */
	public boolean isCellEditable(int row, int col) {
		//Note that the data/cell address is constant,
		//no matter where the cell appears onscreen.
		return false;
	}

	/*
	 * Don't need to implement this method unless your table's
	 * data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		if (Frame.DEBUG) {
			System.out.println("Setting value at " + row + "," + col
					+ " to " + value
					+ " (an instance of "
					+ value.getClass() + ")");
		}

		Frame.data[row][col] = value;
		fireTableCellUpdated(row, col);

		if (Frame.DEBUG) {
			System.out.println("New value of data:");
			printDebugData();
		}
	}
	public void addRow(String id, String cliente, String clienteID, 
			String extintorClasse, String extintorCarga, 
			String extintorCapacidade, String extintorVencimento,
			String extintorFabricacao) {
		try {
			Frame.currientDate = Frame.dateFormat.parse(Frame.dateFormat.format(new Date()));
		} catch (ParseException e1) {}

		String dateF = Frame.dateFormat.format(Frame.currientDate);

		Data.id.add(id);
		Data.cliente.add(cliente);
		Data.clienteID.add(clienteID);
		Data.extintorClasse.add(extintorClasse);
		Data.extintorCarga.add(extintorCarga);
		Data.extintorCapacidade.add(extintorCapacidade);
		Data.extintorVencimento.add(extintorVencimento);
		Data.extintorFabricacao.add(extintorFabricacao);
		Data.extintorUltimaAtualizacao.add(dateF);
		Data.extintorPrimeiraAtualizacao.add(dateF);


		fireTableDataChanged();
		fireTableRowsInserted(getRowCount()-2, getRowCount()-1);
	}

	public void modifyRow(int arg00, String id, String cliente, 
			String clienteID, String extintorClasse, 
			String extintorCarga , String extintorCapacidade,
			String extintorVencimento, String extintorFabricacao) {
		try {
			Frame.currientDate = Frame.dateFormat.parse(Frame.dateFormat.format(new Date()));
		} catch (ParseException e1) {}


		Data.id.set(arg00, id);
		Data.cliente.set(arg00, cliente);
		Data.clienteID.set(arg00, clienteID);
		Data.extintorClasse.set(arg00, extintorClasse);
		Data.extintorCarga.set(arg00, extintorCarga);
		Data.extintorCapacidade.set(arg00, extintorCapacidade);
		Data.extintorVencimento.set(arg00, (String) extintorVencimento);
		Data.extintorFabricacao.set(arg00, (String) extintorFabricacao);
		Data.extintorUltimaAtualizacao.set(arg00, 
				Frame.dateFormat.format(Frame.currientDate));

		fireTableDataChanged();
		fireTableRowsUpdated(getRowCount()-2, getRowCount()-1);
	}

	public void removeRow(int selected) {

		int selectedRow = valueChanged(selected);

		Data.id.remove(selectedRow);
		Data.cliente.remove(selectedRow);
		Data.clienteID.remove(selectedRow);
		Data.extintorClasse.remove(selectedRow);
		Data.extintorCarga.remove(selectedRow);
		Data.extintorCapacidade.remove(selectedRow);
		Data.extintorVencimento.remove(selectedRow);
		Data.extintorFabricacao.remove(selectedRow);
		Data.extintorUltimaAtualizacao.remove(selectedRow);
		Data.extintorPrimeiraAtualizacao.remove(selectedRow);
		fireTableDataChanged();
		fireTableRowsDeleted(getRowCount()-2, getRowCount()-1);
	}

	public void removeRows(int selected[]) {
		for (int i = selected.length-1; i >= 0; i--){
			int selectedRow = valueChanged(Frame.table.getSelectedRows()[i]);

			Data.id.remove(selectedRow);
			Data.cliente.remove(selectedRow);
			Data.clienteID.remove(selectedRow);
			Data.extintorClasse.remove(selectedRow);
			Data.extintorCarga.remove(selectedRow);
			Data.extintorCapacidade.remove(selectedRow);
			Data.extintorVencimento.remove(selectedRow);
			Data.extintorFabricacao.remove(selectedRow);
			Data.extintorUltimaAtualizacao.remove(selectedRow);
			Data.extintorPrimeiraAtualizacao.remove(selectedRow);
		}
		fireTableDataChanged();
		fireTableRowsDeleted(getRowCount()-2, getRowCount()-1);
	}



	private void printDebugData() {
		int numRows = getRowCount();
		int numCols = getColumnCount();

		for (int i=0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j=0; j < numCols; j++) {
				System.out.print("  " + Frame.data[i][j]);
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}
	public static int valueChanged(int tableSelectedRow) {
		int viewRow = tableSelectedRow;
		if (viewRow < 0) {
			//Selection got filtered away.
			return (Integer) null;
		} else {
			int modelRow = 
					Frame.table.convertRowIndexToModel(viewRow);
			return modelRow;
		}
	}

}
