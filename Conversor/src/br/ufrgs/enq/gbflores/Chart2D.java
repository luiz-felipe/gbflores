package br.ufrgs.enq.gbflores;

import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart2D {


	public Chart2D(ArrayList<Double> time, ArrayList<double[]>value) {
		XYSeriesCollection dataset = new XYSeriesCollection();

//		JFreeChart chart2D = ChartFactory.createXYLineChart("title", "v", "P", dataset);

//		ChartPanel chartPanel = new ChartPanel(chart2D);
//
//		XYPlot plot2D = chart2D.getXYPlot();

		XYSeries x1 = new XYSeries("x1");
		XYSeries x2 = new XYSeries("x2");
		XYSeries x3 = new XYSeries("x3");
		XYSeries x4 = new XYSeries("x4");
		XYSeries x5 = new XYSeries("x5");

		for (int i = 1; i <= 10; i++) {
			double x = Math.pow(2, i)/1000000;
//			double x = i/100000D;
			x1.add(x, 1);
			x2.add(x, 2);
			x3.add(x, 3);
			x4.add(x, 4);
			x5.add(x, 5);
		}

		dataset.addSeries(x1);
		dataset.addSeries(x2);
		dataset.addSeries(x3);
		dataset.addSeries(x4);
		dataset.addSeries(x5);
	}

}
