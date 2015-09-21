package test;

import java.util.ArrayList;
import java.util.List;

import org.leores.plot.JGnuplot.Plot;
import org.leores.util.DataTable;
import org.leores.util.DataTableSet;

public class SinCos {

	public SinCos() {
	//DataTableSet 2 3d add data using prepared lists
	DataTableSet dts2 = new DataTableSet("DataTableSet 3d");
	List<Double> x = new ArrayList<Double>(), y = new ArrayList<Double>(), z1 = new ArrayList<Double>(), z2 = new ArrayList<Double>();
	for (double i = -2*Math.PI; i <= 2*Math.PI; i += 0.05) {
		for (double j = -2*Math.PI; j <= 2*Math.PI; j += 0.05) {
			x.add(i);
			y.add(j);
			z1.add(Math.sin(i));
			z2.add(Math.sin(j));
		}
	}
	dts2.addNewDataTable("x^2+y^2", x, y, z1);
	dts2.addNewDataTable("4+x^2+y^2", x, y, z2);

	Plot plot2 = new Plot("plot2") {
		String zlabel = "'z axis'";
	};
	plot2.add(dts2);
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
