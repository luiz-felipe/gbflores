package br.ufrgs.enq.gbflores;

public class teste1 {

	public teste1() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		double P;
		double Pold = 0;
		double x1 = 0.75;
		double x2 = 1-x1;
		
		double y1 = 0.65;
		double y2 = 1 - y1;
		
		double A = 0.67;

		double gamma1 = Math.exp(A*x2*x2);
		double gamma2 = Math.exp(A*x1*x1);
		
		double P1sat = 32.27;
		double P2sat = 73.14;
		
		P = x1*P1sat + x2*P2sat;
		System.out.println("Pbolha id "+P);

		P = x1*gamma1*P1sat + x2*gamma2*P2sat;
		System.out.println("Pbolha "+P);
		
		y1 = x1*gamma1*P1sat/P;
		y2 = x2*gamma2*P2sat/P;
		
		System.out.println(y1/x1);
		System.out.println(y2/x2);
		
		gamma1 = 1;
		gamma2 = 1;
		P = 1/(y1/(gamma1*P1sat)+y2/(gamma2*P2sat));
		System.out.println("Porvalho id "+P);

		while(Math.abs(P-Pold) > 1e-6){
			Pold = P;
			x1 = (y1*P)/(gamma1*P1sat);
			x2 = (1 - x1);
			gamma1 = Math.exp(A*x2*x2);
			gamma2 = Math.exp(A*x1*x1);
			P = 1/(y1/(gamma1*P1sat)+y2/(gamma2*P2sat));
			System.out.println("Porvalho "+P);
			System.out.println(x1);
		}
		System.out.println();
		System.out.println("Porvalho "+P);
		System.out.println(x1);
	}

}
