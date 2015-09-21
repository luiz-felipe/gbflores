package br.ufrgs.enq.gbflores.termo;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import br.com.vrtech.iise.IISEClient;
import br.com.vrtech.iise.ThermoServer;
import br.ufrgs.enq.gbflores.termo.Atividade2.VLEExperiment.Point;

public class Atividade2 {
	
	public static void main(String[] args) throws Exception {
		new Atividade2();	}

	public Atividade2() throws Exception {
		// Get a reference for the iiSE thermodynamics server
		ThermoServer thermo = IISEClient.getThermo();

		// Lets use the first mixture "slot" and two phase "slots".
		// There is no limit (other than the machine memory) for 
		// the number of "slots" for mixtures and phases.
		// Most applications will use only 1 or 2 slots.
		int mix = 1;
		int liq = 1;
		int vap = 2;
		// Configure the mixture with the desired components.
		String comps[] = { "hydrogen", "toluene" };
		thermo.configureMixture(mix, comps);

		// Once the mixture is configured we can configure phases.
		thermo.configurePhase(liq, ThermoServer.Liquid, mix, "PR");
		thermo.configurePhase(vap, ThermoServer.Vapour, mix, "PR");

		// Set the state of the phases
		double T = 313.15;
		String Tunit = "C"; // Temperature in deg C
		double P = 1.0;
		String Punit = "atm"; // Pressure in bar
		NumberFormat nf = new DecimalFormat("0.####");

		VLEExperiment vle = new VLEExperiment("Hydrogen + Toluene T at 188.7 C.txt");
//		VLEExperiment vle = new VLEExperiment("Hydrogen + Toluene T at 229.0 C.txt");
//		VLEExperiment vle = new VLEExperiment("Hydrogen + Toluene T at 269.0 C.txt");
//		VLEExperiment vle = new VLEExperiment("Hydrogen + Toluene T at 302.0 C.txt");

		for (Point p : vle.getPoints()) {
			T = p.T;
			P = p.P;
			double x[] = p.getX();
			double y[] = p.getY();
			// Check the flash equilibrium by fugacity coefficients
			thermo.setPhaseState(liq, T, P, x, Tunit, Punit);
			thermo.setPhaseState(vap, T, P, y, Tunit, Punit);
			double[] fugliq = thermo.getPhaseProperty(liq,
					ThermoServer.FugacityCoefficient, "");
			double[] fugvap = thermo.getPhaseProperty(vap,
					ThermoServer.FugacityCoefficient, "");

			double sumKx = 1.;
			double oldSumKx = 1.;
			// pressão
			double oldP = 0;
			while (Math.abs(oldP - P) > 1e-10) {
				oldP = P;
				// composição
				sumKx = 0;
				while (Math.abs(sumKx - oldSumKx) > 1e-10) {
					oldSumKx = sumKx;
					double K[] = new double[comps.length];
					sumKx = 0;
					for (int i = 0; i < K.length; i++) {
						K[i] = fugliq[i] / fugvap[i];
						sumKx += K[i] * x[i];
					}
					for (int i = 0; i < K.length; i++)
						y[i] = K[i] * x[i] / sumKx;

					thermo.setPhaseState(liq, T, P, x, Tunit, Punit);
					thermo.setPhaseState(vap, T, P, y, Tunit, Punit);
					fugliq = thermo.getPhaseProperty(liq,
							ThermoServer.FugacityCoefficient, "");
					fugvap = thermo.getPhaseProperty(vap,
							ThermoServer.FugacityCoefficient, "");
				}
				P = P / (2 - sumKx);
			}
			System.out.println(nf.format(T) + "\t" + nf.format(P) + "\t"
					+ nf.format(x[0]) + "\t" + nf.format(y[0]));
		}
	}

	public class VLEExperiment {
		static final String COMMENT = "#";
		public String filename;

		public class Point {
			public double T, P, x1, y1;

			Point(double T, double P, double x1, double y1) {
				this.T = T;
				this.P = P;
				this.x1 = x1;
				this.y1 = y1;
			}

			public double[] getX() {
				double x[] = { x1, (1 - x1) };
				return x;
			}

			public double[] getY() {
				double y[] = { y1, (1 - y1) };
				return y;
			}

			public VLEExperiment getExperiment() {
				return VLEExperiment.this;
			}
		};

		public String comp1;
		public String comp2;
		List<Point> points = new ArrayList<Point>();
		int NP;

		public VLEExperiment(String filename) throws Exception {
			this.filename = filename;

			Scanner input = new Scanner(new File(filename));
			input.useLocale(Locale.US);
			comp1 = input.next().replace('_', ' ');
			comp2 = input.next().replace('_', ' ');
			input.nextLine();
			input.nextLine();

			while (input.hasNext())
				points.add(new Point(input.nextDouble(), 
						input.nextDouble(), input.nextDouble(),
						input.nextDouble()));
			input.close();
		}

		public List<Point> getPoints() {return points;}
	}
}