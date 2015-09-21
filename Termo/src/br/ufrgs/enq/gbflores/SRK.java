package br.ufrgs.enq.gbflores;

public class SRK extends CEoS{
	public SRK(Comp comp) {
		super (SRK, comp);
	}
	
	public SRK(String comp) throws MissingCompoundException {
		super (SRK, comp);
	}
	
	public SRK() {
		OMEGA = 0.08664;
		PSI = 0.42748;
		ALPHA1 = 0.48;
		ALPHA2 = 1.57;
		ALPHA3 = -1.176;
		SIGMA = 1D;
		EPSILON = 0;
	}
}
