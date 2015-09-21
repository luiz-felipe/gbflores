package br.ufrgs.enq.gbflores;

import com.csvreader.CsvReader;

public class Comp {

	protected String name;
	protected String formula;
	protected String CAS;
	protected double Pc;
	protected double Tc;
	protected double omega;

	public Comp(String name, double Pc, double Tc, double omega) {
		this.name = name.toUpperCase();
		this.Pc = Pc;
		this.Tc = Tc;
		this.omega = omega;
	}


	public Comp(String name) throws MissingCompoundException {
		try{
			CsvReader reader = new CsvReader("Comp.csv");;
			reader.readHeaders();

			while(reader.readRecord()){
				if(reader.get(0).equalsIgnoreCase(name)){
					this.name = reader.get(0);
					formula = reader.get(1);
					CAS = reader.get(2);
					Tc = Double.parseDouble(reader.get(3));
					Pc = Double.parseDouble(reader.get(4))*1000;
					omega = Double.parseDouble(reader.get(6));
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (CAS==null){
			throw new MissingCompoundException("Compound \""+ name.toUpperCase() + "\" not found!");
		}
	}
}
