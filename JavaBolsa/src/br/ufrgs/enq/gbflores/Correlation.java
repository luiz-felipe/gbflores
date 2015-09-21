package br.ufrgs.enq.gbflores;

import java.text.NumberFormat;

import org.apache.commons.math.optimization.CostException;
import org.apache.commons.math.optimization.CostFunction;

public class Correlation implements CostFunction {
	int pontos;
	double tempExp[] = new double[pontos];
	double phi[] = new double[pontos];
	private double parameter[] = new double[2];
	public double temp;
	double totalcost;
	int evoluations = 0;

	public Correlation(double tempExp[], double phi[], int pontos) {
		this.tempExp = tempExp;
		this.phi = phi;
		this.pontos = pontos;
	}

	public double cost(double parameter[]) throws CostException {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(4);

		this.parameter = parameter;
		totalcost = 0;
		for (int i = 0; i < pontos; i++) {
			
			double cost1 = tempExp[i] - getTemp(phi[i], parameter);
			totalcost = totalcost + Math.abs(cost1);
		}
		
		evoluations++;
		return totalcost / pontos;
	}


	public double getTemp(double phi, double parameter[]) {
		temp = Math.pow((parameter[0]/parameter[1])* Math.log(1/(1-phi)),1/parameter[1]);
		return temp;
	}

	/**
	 * Return the tau vector after the estimation
	 * 
	 * @return A, B
	 */
	public double[] getParameter() {
		return parameter;
	}

	/**
	 * Return the error value after the estimation
	 * 
	 * @return Error value
	 */
	public double getError() {
		return totalcost / pontos;
	}
	
	public int getEvoluations(){
		return evoluations;
	}
}
