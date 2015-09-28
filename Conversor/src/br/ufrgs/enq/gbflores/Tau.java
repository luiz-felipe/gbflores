package br.ufrgs.enq.gbflores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class Tau {
	static final String COMMENT = "#"; // comment
	public File fileIn;// comment2
	public File fileOut;
	JFrame frame  = new JFrame();
	JTabbedPane tabbedPane = new JTabbedPane();

	public Tau(String filename, int compressao) throws IOException {

		Scanner input0 = new Scanner(new File(filename));
		Scanner input = new Scanner(new File(filename));
		input.useLocale(Locale.US);
		fileIn = new File(filename);
		fileOut = new File(filename+".txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(fileOut));

		int monitoringPoints = 0;
		String cur = input0.nextLine();
		if (cur.startsWith(COMMENT) && cur.toUpperCase().contains("X")){
			cur = cur.replace('#', ' ');
			cur = cur.replace('x', ' ');
			cur = cur.replace('X', ' ');
			System.out.println(cur);
			cur = cur.trim();
			String teste[] = cur.split(" ");
			for (String string : teste) {
				if (!string.isEmpty()){
					System.out.println(string);
					monitoringPoints++;
				}
			}
		}

		input0.close();


		cur = input.next();
		while(cur.startsWith(COMMENT)){
			out.append(COMMENT + input.nextLine()+"\n");
			cur = input.next();
		}
	
		
		double time;
		double tau1[] = new double[monitoringPoints];
		double tau2[] = new double[monitoringPoints];
		double tau3[] = new double[monitoringPoints];
		double tau4[] = new double[monitoringPoints];
		double tau5[] = new double[monitoringPoints];
		double tau6[] = new double[monitoringPoints];
		int i = 0;
		while (input.hasNext()){
			if (i==0)
			time = Double.parseDouble(cur);
			else
			time = input.nextDouble();
			
			for (int j = 0; j < tau1.length; j++) {
				tau1[j] = Double.parseDouble(input.next().replace("(", ""));
				tau2[j] = Double.parseDouble(input.next());
				tau3[j] = Double.parseDouble(input.next());
				tau4[j] = Double.parseDouble(input.next());
				tau5[j] = Double.parseDouble(input.next());
				tau6[j] = Double.parseDouble(input.next().replace(")", ""));
			}
			
			if ((double)i%compressao==0){
				out.append(time+"");
				for (int j = 0; j < tau1.length; j++) {
					out.append("\t"+tau1[j]+"\t"+tau2[j]+"\t"+tau3[j]+
							+tau4[j]+"\t"+tau5[j]+"\t"+tau6[j]+"\t");
				}
				out.append("\n");
			}
			i++;
		}
		out.close();
		input.close();
		for (int j = 0; j < monitoringPoints; j++) {
			tabbedPane.addTab("Monitoramento "+(j+1), Chart2D(j, fileOut));
		}
		frame.add(tabbedPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public ChartPanel Chart2D(int index, File file) throws FileNotFoundException {
		XYSeriesCollection dataset = new XYSeriesCollection();
		JFreeChart chart2D = ChartFactory.createXYLineChart("", "Time", "Temperature", dataset);
		ChartPanel chartPanel = new ChartPanel(chart2D);
		XYPlot plot2D = chart2D.getXYPlot();
		XYSeries xx = new XYSeries("tau xx");
		XYSeries xy = new XYSeries("tau xy");
		XYSeries xz = new XYSeries("tau xz");
		XYSeries yy = new XYSeries("tau yy");
		XYSeries yz = new XYSeries("tau yz");
		XYSeries zz = new XYSeries("tau zz");

		Scanner input0 = new Scanner(file);
		Scanner input = new Scanner(file);
		input.useLocale(Locale.US);
		int monitoringPoints = 0;
		String cur = input0.nextLine();
		if (cur.startsWith(COMMENT) && cur.toUpperCase().contains("X")){
			cur = cur.replace('#', ' ');
			cur = cur.replace('x', ' ');
			cur = cur.replace('X', ' ');
			cur = cur.trim();
			String teste[] = cur.split(" ");
			for (String string : teste) {
				if (!string.isEmpty()){
					monitoringPoints++;
				}
			}
		}
		input0.close();

		cur = input.next();
		while(cur.startsWith(COMMENT)){
			input.nextLine();
			cur = input.next();
		}


		double time;
		double txx[] = new double[monitoringPoints];
		double txy[] = new double[monitoringPoints];
		double txz[] = new double[monitoringPoints];
		double tyy[] = new double[monitoringPoints];
		double tyz[] = new double[monitoringPoints];
		double tzz[] = new double[monitoringPoints];
		int i = 0;
		while (input.hasNext()){
			if (i==0)
				time = Double.parseDouble(cur);
			else
				time = input.nextDouble();

			for (int j = 0; j < txx.length; j++) {
				txx[j] = Double.parseDouble(input.next());
				txy[j] = Double.parseDouble(input.next());
				txz[j] = Double.parseDouble(input.next());
				tyy[j] = Double.parseDouble(input.next());
				tyz[j] = Double.parseDouble(input.next());
				tzz[j] = Double.parseDouble(input.next());
			}

			xx.add(time, txx[index]);
			xy.add(time, txy[index]);
			xz.add(time, txz[index]);
			yy.add(time, tyy[index]);
			yz.add(time, tyz[index]);
			zz.add(time, tzz[index]);
			i++;
		}
		input.close();

		dataset.addSeries(xx);
		dataset.addSeries(xy);
		dataset.addSeries(xz);
		dataset.addSeries(yy);
		dataset.addSeries(yz);
		dataset.addSeries(zz);
		NumberAxis temperature  = new NumberAxis("Velocity");
		temperature.setAutoRangeIncludesZero(false);
		temperature.setAutoRange(true);
		plot2D.setRangeAxis(temperature);
		plot2D.getRangeAxis().setAutoRange(true);

		return chartPanel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frameButton = new JFrame("T");
		final JFileChooser fc = new JFileChooser(new File("/home/gbflores/Área de Trabalho"));
		fc.setMultiSelectionEnabled(true);
		int returnVal = fc.showOpenDialog(frameButton);
		String name = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File []file = fc.getSelectedFiles();

			for (File file2 : file) {
				name = file2.getAbsolutePath();
				System.out.println(name);

				try {
					@SuppressWarnings("unused")
					Tau u = new Tau(name,100);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
			System.exit(0);
		}System.exit(0);
	}
}
