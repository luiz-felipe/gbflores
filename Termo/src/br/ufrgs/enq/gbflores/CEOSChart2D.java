package br.ufrgs.enq.gbflores;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;


@SuppressWarnings("serial")
public class CEOSChart2D extends JPanel{

	private XYSeriesCollection dataset = new XYSeriesCollection();
	private JFreeChart chart2D = ChartFactory.createXYLineChart("title", "v", "P", dataset);
	private XYPlot plot2D = chart2D.getXYPlot();
	ChartPanel chartPanel = new ChartPanel(chart2D);
	private static double isoTr[] = {0.7, 0.8, 0.9, 0.95, 0.99, 1, 1.01, 1.05, 1.1, 1.2, 1.3};
	JButton zoom = new JButton("Restore zoom");
	private double maxP;
	private double minV;
	CEoS eos;


	public CEOSChart2D(String comp) throws MissingCompoundException {
		Comp c = new Comp(comp);
		eos = new PR(c);
		createChart(eos);
	}

	public CEOSChart2D(Comp comp) {
		eos = new PR(comp);
		createChart(eos);
	}
	public CEOSChart2D(String CEoS, String comp) throws MissingCompoundException {
//		Comp c = new Comp(comp);
		eos = new CEoS(CEoS, comp);
		createChart(eos);
	}

	public CEOSChart2D(String CEoS, Comp comp) {
		eos = new CEoS(CEoS, comp);
		createChart(eos);
	}

	private void createChart(CEoS eos){
		dataset.addSeries(eos.addEnvelope());

		for (int i = 0; i < isoTr.length; i++) {
			dataset.addSeries(eos.addIsoTr(isoTr[i]));
		}
		if (eos.comp.name.equalsIgnoreCase("water")){
			dataset.addSeries(eos.addIsoT(273));
			dataset.addSeries(eos.addIsoT(373));
		}
		
		plot2D.setRangeAxis(new NumberAxis("P (bar)"));
		plot2D.getRangeAxis().setUpperBound(2.5*eos.comp.Pc/1e5);
		plot2D.getRangeAxis().setLowerBound(0);
		maxP = 2.5*eos.comp.Pc/1e5;
		chart2D.setTitle(eos.comp.name+" using "+eos.type);

		plot2D.setDomainAxis(new LogarithmicAxis("v (m³/mol)"));
		plot2D.getDomainAxis().setLowerBound(eos.getB());
		minV = eos.getB();
		setLayout(new BorderLayout());
		add(chartPanel);
		zoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				plot2D.getRangeAxis().setUpperBound(maxP);
				plot2D.getRangeAxis().setLowerBound(0);
				plot2D.getDomainAxis().setLowerBound(minV);;
			}
		});
		add(zoom, BorderLayout.AFTER_LAST_LINE);
	}

	public String getComp(){
		return eos.comp.name;
	}

	public String getToolTip(){
		return (eos.comp.name+" using the CEoS "+eos.type);
	}
	
	public static void setIsoTr(double [] isotermasReduzidas){
		isoTr = isotermasReduzidas;
	}
	public static void setIsoTr(double isotermaReduzida){
		isoTr = new double[] {isotermaReduzida};
	}

	public static double[] getIsoTr(){
		return isoTr;
	}
}
