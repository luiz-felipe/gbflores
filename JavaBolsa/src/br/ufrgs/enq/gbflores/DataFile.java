package br.ufrgs.enq.gbflores;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Reading files.
 * 
 * @author
 * 
 */
public class DataFile {
	private Vector<Double> phi = new Vector<Double>();
	private Vector<Double> temp = new Vector<Double>();
	private Vector<Double> compNumber = new Vector<Double>();
	private Vector<Double> volMolar = new Vector<Double>();
	private Vector<Double> tempBuble = new Vector<Double>();
	private Vector<Double> molFrac = new Vector<Double>();
	private Scanner scan;
	private List<Component> compList = new ArrayList<Component>();


	public DataFile(File input) throws FileNotFoundException {
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

		int nComp = getSizeComp();
		int nNotNullComp = 0;

		for (int i = 0; i < nComp; i++) {
			if (getMolarFraction()[i] != 0){
				nNotNullComp++;
			}
		}

		for (int i = 0; i < nComp; i++) {
			if (getMolarFraction()[i] != 0){						
				Component comp = new Component();
				comp.setCompNumber(getCompNumber()[i]);
				comp.setMolarVolune(getMolarVolume()[i]);
				comp.setBoilingPoint(getBoilingPoint()[i]);
				comp.setMolarFraction(getMolarFraction()[i]);
				compList.add(comp);
			}
		}

		Collections.sort(compList,new Sort());

		int i = 0;
		int compNumber[] = new int[nNotNullComp];
		double molarVolume[] = new double[nNotNullComp];
		double boilingPoint[] = new double[nNotNullComp];
		double molarFraction[] = new double[nNotNullComp];

		//print the list
		for(Component co:compList)
		{
			if (co.getMolarFraction()!=0){
				compNumber[i] = (int) co.getCompNumber();
				molarVolume[i] = co.getMolarVolune();
				boilingPoint[i] = co.getBoilingPoint();
				molarFraction[i] = co.getMolarFraction();
				i++;
			}
		}

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

	public double[] getMolarVolume() {
		double[] vol = new double[volMolar.size()];
		for (int i = 0; i < volMolar.size(); i++)
			vol[i] = Double.valueOf(volMolar.get(i));
		return vol;
	}

	public double[] getBoilingPoint() {
		double[] tb = new double[tempBuble.size()];
		for (int i = 0; i < tempBuble.size(); i++)
			tb[i] = Double.valueOf(tempBuble.get(i));
		return tb;
	}

	public double[] getMolarFraction() {
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
