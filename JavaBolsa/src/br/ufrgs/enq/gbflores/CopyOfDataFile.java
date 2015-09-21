package br.ufrgs.enq.gbflores;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Reading files.
 * 
 * @author
 * 
 */
public class CopyOfDataFile {
	private Vector<Double> phi = new Vector<Double>();
	private Vector<Double> temp = new Vector<Double>();
	private Vector<Double> compNumber = new Vector<Double>();
	private Vector<Double> volMolar = new Vector<Double>();
	private Vector<Double> tempBuble = new Vector<Double>();
	private Vector<Double> molFrac = new Vector<Double>();
	private Scanner scan;

	public CopyOfDataFile(File input) throws FileNotFoundException {
		// use a file for input
		File file = new File(input.getAbsolutePath());
		scan = new Scanner(file);

		scan.nextLine();
		
		while (scan.hasNextDouble()) {
			phi.add(Double.parseDouble(scan.next()));
			temp.add(Double.parseDouble(scan.next()));
		}
		
		scan.nextLine();
		scan.nextLine();
		scan.nextLine();
		
		while (scan.hasNext()) {
			compNumber.add(Double.parseDouble(scan.next()));
			volMolar.add(Double.parseDouble(scan.next()));
			tempBuble.add(Double.parseDouble(scan.next()));
			molFrac.add(Double.parseDouble(scan.next()));
		}
		scan.close();
		
		

		
	}


	public double[] getPhi() {
		double[] Phi = new double[phi.size()];
		for (int i = 0; i < phi.size(); i++)
			Phi[i] = Double.valueOf(phi.get(i));
		return Phi;
	}

	public double[] getTemp() {
		double[] t = new double[temp.size()];
		for (int i = 0; i < temp.size(); i++)
			t[i] = Double.valueOf(temp.get(i));
		return t;
	}

	public double[] getCompNumber() {
		double[] comp = new double[compNumber.size()];
		for (int i = 0; i < compNumber.size(); i++)
			comp[i] = Double.valueOf(compNumber.get(i));
		return comp;
	}

	public double[] getVolMolar() {
		double[] vol = new double[volMolar.size()];
		for (int i = 0; i < volMolar.size(); i++)
			vol[i] = Double.valueOf(volMolar.get(i));
		return vol;
	}

	public double[] getTempBuble() {
		double[] tb = new double[tempBuble.size()];
		for (int i = 0; i < tempBuble.size(); i++)
			tb[i] = Double.valueOf(tempBuble.get(i));
		return tb;
	}

	public double[] getMolFrac() {
		double[] mf = new double[molFrac.size()];
		for (int i = 0; i < molFrac.size(); i++)
			mf[i] = Double.valueOf(molFrac.get(i));
		return mf;
	}

	public int getSizeComp() {
		return compNumber.size();
	}

	public int getSizePhi() {
		return phi.size();
	}
}
