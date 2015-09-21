package br.ufrgs.enq.gbflores.termo;

import java.text.DecimalFormat;

import br.com.vrtech.iise.IISEClient;
import br.com.vrtech.iise.ThermoServer;
import br.ufrgs.enq.gbflores.termo.VLEExperiment.Point;

public class Atividade3 {

	public static void main(String[] args) throws Exception {
		new Atividade3();
	}

	public Atividade3() throws Exception {

		// carregar experimento
		//		VLEExperiment vle = new VLEExperiment("benzene-n-heptane.txt");


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

		//		String comps[] = vle.getComps();
		String comps[] = {"ETHANOL", "WATER"};

		thermo.configureMixture(mix, comps);

		// Once the mixture is configured we can configure phases.
		thermo.configurePhase(liq, ThermoServer.Liquid, mix,
				"SRK", "SCMR", "Scatchard-Hildebrand");
		thermo.configurePhase(vap, ThermoServer.Vapour, mix,
				"SRK", "SCMR", "Scatchard-Hildebrand");

		// Set the state of the phases
		double T = 313.15;
		String Tunit = "C"; // Temperature in deg C
		double P = 22.068;
		String Punit = "kPa"; // Pressure in kPa

		// Formatação numérica
		DecimalFormat nf2 = new DecimalFormat("0.00");
		DecimalFormat nf3 = new DecimalFormat("0.000");
		DecimalFormat nf4 = new DecimalFormat("0.0000");

		// Carga
		double z[] = new double[comps.length];

		// Composições e coeficiente de partição
		double x[] = new double[z.length];
		double y[] = new double[z.length];
		double K[] = new double[z.length];

		// Fugacidades
		double[] fugliq;
		double[] fugvap;

		// Fração vaporizada
		double v = 0;

		// variaveis auxiliares
		double oldx[] = new double[x.length];
		double dx[] = new double[x.length];

		// Rotina de Rachford-Rice
		RachfordRice rR = new RachfordRice();

		System.out.println("T\tP\tz1\tv\tx1\ty1\t");

		//		for (Point p : vle.getPoints()) {
		//		T = p.T;
		//		P = p.P;
		T = 63;
		P = 101;

		for (int i = 0; i < z.length; i++)
			z[i] = 1. / z.length;
		//			z[i] = (p.getX()[i] + p.getY()[i]) / 2;

		System.arraycopy(z, 0, x, 0, z.length);
		System.arraycopy(z, 0, y, 0, z.length);
		System.arraycopy(z, 0, dx, 0, x.length);

		rR.setZ(z);

		while (RachfordRice.somaVetor(dx) > 1e-6) {

			System.arraycopy(x, 0, oldx, 0, x.length);

			thermo.setPhaseState(liq, T, P, x, Tunit, Punit);
			thermo.setPhaseState(vap, T, P, y, Tunit, Punit);
			fugliq = thermo.getPhaseProperty(liq,
					ThermoServer.FugacityCoefficient, "");
			fugvap = thermo.getPhaseProperty(vap,
					ThermoServer.FugacityCoefficient, "");

			for (int i = 0; i < K.length; i++)
				K[i] = fugliq[i] / fugvap[i];

			rR.setK(K);
			v = rR.getFracVap();
			x = rR.getX(v);
			y = rR.getY(v);

			for (int i = 0; i < dx.length; i++)
				dx[i] = Math.abs(oldx[i] - x[i]);

		}
		System.out.println(nf2.format(T) + "\t" + nf3.format(P) + "\t"
				+ nf4.format(z[0]) + "\t" + nf4.format(v) + "\t"
				+ nf4.format(x[0]) + "\t" + nf4.format(y[0]));
		//		}
	}
}