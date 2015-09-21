package br.ufrgs.enq.gbflores.termo;

import br.com.vrtech.iise.IISEClient;
import br.com.vrtech.iise.ThermoServer;

public class ThermoTest {

	public static void main(String[] args) {
		try{
			// Get a reference for the iiSE thermodynamics server
			ThermoServer thermo = IISEClient.getThermo();

			// Lets use the first mixture "slot" and two phase "slots".
			// There is no limit (other than the machine memory) for the number
			// of "slots" for mixtures and phases.
			// Most applications will use only 1 or 2 slots.
			int mix = 1;
			int liq = 1;
			int vap = 2;

			// Configure the mixture with the desired components.
			String comps[] = {"n-butane", "isobutane", "n-pentane", "n-hexane"};
			thermo.configureMixture(mix, comps);

			// With the mixture set, it is possible to retrieve
			// pure component properties:
			double Tb[] = thermo.getPureProperty(mix, ThermoServer.NormalBoilingPoint, "K");
			double[] Tf = thermo.getPureProperty(mix, ThermoServer.NormalFreezingPoint, "K");
			double[] Tc = thermo.getPureProperty(mix, ThermoServer.CriticalTemperature, "K");
			double[] Mw = thermo.getPureProperty(mix, ThermoServer.MolecularWeight, "g/mol");
			
			// Once the mixture is configured we can configure phases.
			// In the phase configuration we need to specify:
			//  - the mixture to be used
			//  - the phase type (Liquid or Vapour)
			//  - the equation of state for calculations ("PR", "SRK", ...)
			thermo.configurePhase(liq, ThermoServer.Liquid, mix, "PR", "SCMR", "UNIFAC(Do)");
			thermo.configurePhase(vap, ThermoServer.Vapour, mix, "PR", "SCMR", "UNIFAC(Do)");
			
			// Set the state of the phases
			double T = 25.0;   String Tunit = "C";   // Temperature in deg C
			double P = 1.0;    String Punit = "bar"; // Pressure in bar
			double []z = {0.2, 0.3, 0.1, 0.4};       // composition
			thermo.setPhaseState(liq, T, P, z, Tunit, Punit);
			thermo.setPhaseState(vap, T, P, z, Tunit, Punit);
			
			// After the phases are configured and set,
			// we can retrieve the phase properties:
			double []hv = thermo.getPhaseProperty(vap, ThermoServer.Enthalpy, "J/mol");
			double []hl = thermo.getPhaseProperty(liq, ThermoServer.Enthalpy, "J/mol");
			double []gl = thermo.getPhaseProperty(liq, ThermoServer.GibbsFreeEnergy, "J/mol");
			double []gv = thermo.getPhaseProperty(vap, ThermoServer.GibbsFreeEnergy, "J/mol");
			double []cpv = thermo.getPhaseProperty(vap, ThermoServer.Cp, "J/mol K");
			double []viscv = thermo.getPhaseProperty(vap, ThermoServer.Viscosity, "cP");
			double []vl = thermo.getPhaseProperty(liq, ThermoServer.Volume, "m^3/mol");
			
			// Flash calculation routines are also available:
			double [][]flash = thermo.flashTP(vap, liq, T, P, z, Tunit, Punit);
			double fracv = flash[0][0];
			double fracl = flash[1][0];
			double y[] = new double[comps.length];
			double x[] = new double[comps.length];
			for (int i = 0; i < x.length; i++) {
				y[i] = flash[0][i+1];
				x[i] = flash[1][i+1];
			}
			
			// Check the flash equilibrium by fugacity coefficients
			thermo.setPhaseState(liq, T, P, x, Tunit, Punit);
			thermo.setPhaseState(vap, T, P, y, Tunit, Punit);
			double[] fugliq = thermo.getPhaseProperty(liq, ThermoServer.FugacityCoefficient, "");
			double[] fugvap = thermo.getPhaseProperty(vap, ThermoServer.FugacityCoefficient, "");
			
			// At equilibrium x*fugliq - y*fugvap = 0
			System.out.println("Residuals of flash calculation:");
			for (int i = 0; i < x.length; i++)
				System.out.print( (x[i]*fugliq[i] - y[i]*fugvap[i]) + " ");
			System.out.println();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
