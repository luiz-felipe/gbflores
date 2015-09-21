package br.ufrgs.enq.gbflores;

public class PR extends CEoS{
	public PR(Comp comp) {
		super (PR, comp);
	}
	
	public PR(String comp) throws MissingCompoundException {
		super (PR, comp);
	}

	public PR() {
		OMEGA = 0.07780;
		PSI = 0.45724;
		ALPHA1 = 0.37464;
		ALPHA2 = 1.54226;
		ALPHA3 = -0.26992;
		EPSILON = 1-Math.sqrt(2);
		SIGMA = 1+Math.sqrt(2);
	}
}
