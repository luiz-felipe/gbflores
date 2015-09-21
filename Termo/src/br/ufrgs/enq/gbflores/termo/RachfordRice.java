package br.ufrgs.enq.gbflores.termo;

public class RachfordRice {

	private double[] z;
	private double K[];

	public double getFracVap() {

		double v = 0, v1 = 0, v2 = 1, e = 1;
		int k = 0;
		double f[] = new double[z.length];

		while (Math.abs(v1-v2) > 1e-10 && k < 1000) {
			v = (v1 + v2) / 2;
			
			for (int i = 0; i < z.length; i++)
				f[i] = (z[i] * K[i]) / (1 + v * (K[i] - 1));

			e = somaVetor(f) - 1;

			if (e > 0)
				v1 = v;
			else
				v2 = v;
			k++;
		}
		return v;
	}

	public static double somaVetor(double vetor[]) {
		double soma = 0;
		for (double d : vetor)
			soma += d;
		return soma;
	}

	public double[] getY(double v) {
		double[] y = new double[K.length];
		for (int i = 0; i < K.length; i++) {
			y[i] = z[i] * K[i] / (1 + v * (K[i] - 1));
		}
		return y;
	}

	public double[] getX(double v) {
		double[] y = getY(v);
		double[] x = new double[y.length];
		for (int i = 0; i < y.length; i++) {
			x[i] = y[i] / K[i];
		}
		return x;
	}

	public void setZ(double[] z) {
		this.z = new double[z.length];
		System.arraycopy(z, 0, this.z, 0, z.length);
	}

	public double[] getZ() {
		return this.z;
	}

	public void setK(double[] K) throws Exception {
		this.K = new double[K.length];
		System.arraycopy(K, 0, this.K, 0, K.length);
	}

	public double[] getK() {
		return this.K;
	}
}