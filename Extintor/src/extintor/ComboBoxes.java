package extintor;

import javax.swing.JComboBox;

public class ComboBoxes {
	public static JComboBox<String> comboType(){
		final JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("A");
		combo.addItem("B");
		combo.addItem("C");
		combo.addItem("D");
		combo.addItem("E");
		combo.addItem("AB");
		combo.addItem("ABC");
		combo.addItem("BC");
		combo.addItem("AC");
		return combo;
	}

	public static JComboBox<String> comboCarga(){
		final JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("H2O");
		combo.addItem("CO2");
		combo.addItem("P� Qu�mico");
		combo.addItem("Espuma");
		return combo;
	}
}
