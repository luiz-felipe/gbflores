package br.ufrgs.enq.gbflores.termo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

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

	public String[] getComps(){
		return new String []{comp1, comp2};
	}
	
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

