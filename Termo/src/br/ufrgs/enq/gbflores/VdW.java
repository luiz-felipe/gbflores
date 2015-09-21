package br.ufrgs.enq.gbflores;

public class VdW extends CEoS{
	public VdW(Comp comp) {
		super (VdW, comp);
	}
	
	public VdW(String comp) throws MissingCompoundException {
		super (VdW, comp);
	}
	
	public VdW() {
		OMEGA = 1/8D;
		PSI = 27/64D;
		ALPHA1 = 0;
		ALPHA2 = 0;
		ALPHA3 = 0;
		EPSILON = 0;
		SIGMA = 0;
	}
}
