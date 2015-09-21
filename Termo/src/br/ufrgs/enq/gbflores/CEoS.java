package br.ufrgs.enq.gbflores;

import java.io.EOFException;

import org.jfree.data.xy.XYSeries;

public class CEoS {

	private static final double R = 8.3145; // J/(mol*K)

	public final static String VdW = "VdW";
	public final static String RK = "RK";
	public final static String SRK = "SRK";
	public final static String PR = "PR";


	public static double OMEGA;
	public static double PSI;
	public static double ALPHA1;
	public static double ALPHA2;
	public static double ALPHA3;
	public static double EPSILON;
	public static double SIGMA;
	public String type;


	public static double Tc;
	public static double Tr;
	public static double Pc;
	public static double b;
	public static double omega;
	public static double a;
	protected static Comp comp;
	protected static double precision = 1e-15;

	public CEoS(String type, Comp compound) {
		if (type.equals(VdW))
			new VdW();
		if (type.equals(RK))
			new RK();
		if (type.equals(SRK))
			new SRK();
		if (type.equals(PR))
			new PR();

		this.type = type;
		comp = compound;
		Tc = comp.Tc;
		omega = comp.omega;
		Pc = comp.Pc;
		b = OMEGA*R*Tc/Pc;
	}

	public CEoS(String type, String compoundName) throws MissingCompoundException {
		this.type = type;
		comp = new Comp(compoundName);
		new CEoS(type, comp);
	}
	public CEoS() { }
	
	public double getP(double T, double v){

		b = OMEGA*R*Tc/Pc;
		a = (PSI*calcAlpha(T/Tc)*R*R*Tc*Tc)/Pc;
		double P = (R*T)/(v-b)-a/((v+EPSILON*b)*(v+SIGMA*b));
		if (v>1.1*b)
			return P;
		else
			return Double.NaN;
	}
	public double getPsat(double T){

		double Pmin = 0;
		double Pmax = Pc;
		double P = (Pmin + Pmin) / 2;
		double e = 0.5;
		double dP = 0.5;
		int kk = 0;
		double vVap, vLiq, deltaV;
		while (Math.abs(dP) > precision && kk < 1000) {
			P = (Pmin + Pmax) / 2;
			vVap = getVvap(T, P);
			vLiq = getVliq(T, P);
			deltaV = vVap-vLiq;
			double[]Pi = new double[100];
			for (int i = 0; i < Pi.length; i++) {
				double v = vLiq+deltaV*((double)(i)/(Pi.length-1));
				Pi[i] = getP(T, v);
			}
			Integral i = new Integral(Pi, deltaV/(Pi.length-1));
			e = P*deltaV-i.simpson();
			if (e < 0) {
				Pmin = P;
				dP = Pmin - Pmax;
			} else {
				Pmax = P;
				dP = Pmin - Pmax;
			}
			kk++;
		}
		return P;
	}
	public double getVvap(double T, double P){

		if (T>Tc||P>Pc)
			return 0;

		b = OMEGA*R*Tc/Pc;
		a = (PSI*calcAlpha(T/Tc)*R*R*Tc*Tc)/Pc;

		double v = R*T/P; 
		double oldV = R*T/P+1;
		int k = 0;
		while(Math.abs(oldV-v)>precision && k<100){
			oldV = v;
			v = ((R*T)/P) + b - (a/P)* ((v-b)/((v+EPSILON*b)*(v+SIGMA*b)));
			k++;
			if(k>100)
				System.err.println("Vvap problem");
		}
		return v;
	}
	public double getVliq(double T, double P){

		if (T>Tc||P>Pc)
			return 0;

		b = OMEGA*R*Tc/Pc;
		a = (PSI*calcAlpha(T/Tc)*R*R*Tc*Tc)/Pc;

		double v = b; 
		double oldV = b+1;
		int k = 0;
		while(Math.abs(oldV-v)>precision && k<100){
			oldV = v;
			v = b+((v+EPSILON*b)*(v+SIGMA*b))*((R*T+b*P-v*P)/a);
			k++;
			if(k>100)
				System.err.println("Vliq problem");
		}
		return v;
	}


	protected double calcAlpha(double Tr){
		double alpha = 1;
		if (type.equals(VdW)){
			alpha = 1;
		}if (type.equals(RK)){
			alpha = Math.sqrt(1/Tr);
		}if (type.equals(SRK)||type.equals(PR)){
			double alphaomega = ALPHA1 + ALPHA2*omega + ALPHA3*omega*omega;
			alpha = Math.pow(1+alphaomega*(1-Math.sqrt(Tr)), 2);
		}
		return alpha;
	}
	public double getB(){
		return b;
	}


	public XYSeries addIsoT(double T){
		return addSerie(T/Tc, false);
	}	
	
	public XYSeries addIsoTr(double Tr){
		return addSerie(Tr, true);
	}

	private XYSeries addSerie(double Tr, boolean reduzida){
		XYSeries s;
		if (reduzida)
			s = new XYSeries("Tr = "+Tr);
		else 
			s = new XYSeries("T = "+(Tr*Tc));
			
		double coVolume = getB();
		double maxV = 1000D;

		double Psat = 0;
		double vVap = 0;
		double vLiq = 0;
		if(Tr <= 1){
			Psat = getPsat(Tr*comp.Tc);
			vLiq = getVliq(Tr*comp.Tc, Psat);
			vVap = getVvap(Tr*comp.Tc,  Psat);
		}

		boolean cond = true;
		double j = coVolume;

		while (cond){
			if(Tr <= 1){
				
//				j = j + coVolume/100;
				if (j<vLiq){
					s.add(j, getP(Tr*comp.Tc, j)/1e5);
					j = j + coVolume/100;
				}else if (j>vVap){
					s.add(j, getP(Tr*comp.Tc, j)/1e5);
					j = j + coVolume/10;
				}else{
					s.add(j,Psat/1e5);
					j = j + coVolume/100;
				}
			
			}else{
			
				j = j + coVolume/10;
				double P = getP(Tr*comp.Tc, j);
				s.add(j, P/1e5);
			
			}

			if (j>(coVolume*maxV)) cond = false;
		}
		return s;
	}
	public XYSeries addEnvelope(){

		XYSeries s = new XYSeries("Envelope");
		double Psat = 0;
		double vVap = 0;
		double vLiq = 0;
		double Tr = 0;
		int max = 50;
		for (int i = max/2; i < max+1; i++) {
			Tr = ((double)i/max);
			Psat = getPsat(Tr*comp.Tc);
			vLiq = getVliq(Tr*comp.Tc, Psat);
			vVap = getVvap(Tr*comp.Tc,  Psat);

			if((vVap-vLiq) > precision){
				s.add(vLiq,Psat/1e5);
				s.add(vVap,Psat/1e5);
			}
		}
		return s;
	}
}
