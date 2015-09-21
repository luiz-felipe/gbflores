package br.ufrgs.enq.gbflores;

public class RK extends CEoS{
	public RK(Comp comp) {
		super (RK, comp);
	}
	
	public RK(String comp) throws MissingCompoundException {
		super (RK, comp);
	}
	
	public RK() {
		OMEGA = 0.08664;
		PSI = 0.42784;
		SIGMA = 1D;
		ALPHA1 = 0;
		ALPHA2 = 0;
		ALPHA3 = 0;
		EPSILON = 0;
	}
}
