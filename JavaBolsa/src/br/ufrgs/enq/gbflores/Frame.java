package br.ufrgs.enq.gbflores;

/**
 * Final version of the work to complete the java class (ENG07049-U)
 * Work done by students
 * @author Deborah Hansen Lauxen
 * @author Guilherme Braganholo Fl�res
 * 
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.math.ConvergenceException;
import org.apache.commons.math.optimization.CostException;
import org.apache.commons.math.optimization.NelderMead;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Create the main window
 * 
 */
@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener {
	private Container content;
	private JFileChooser chooser;
	private FileNameExtensionFilter filter;

	private JButton ChooserButton;
	private JButton CalcButton;
	private JRadioButton radiotbp;
	private JRadioButton radioPontosMeio;

	private XYSeriesCollection datasetD86;
	private XYSeriesCollection datasetTBP;

	private JFreeChart TBPChart;
	private JFreeChart D86Chart;
	private DataFile data;

	private JPanel precisionPanel;
	private JPanel interationsPanel;
	static JTextArea resultTextArea;
	static JTextArea convergenceTextArea;
	final private String choose = "choose";
	final private String help = "help";
	final private String about = "about";
	private List<Component> compList = new ArrayList<Component>();
	private int prescision = 1000;
	private int interations = 1000000;
	private JTextField precisionTextField;
	private JTextField interationsTextField;
	private Correlation correlationTBP;
	private NelderMead optimizer;
	private double dist[] = { 0, 5, 10, 30, 50, 70, 90, 95, 100 };

	public Frame() {
		setTitle("Estimator");

		content = getContentPane();
		content.setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Creates a menubar for a JFrame
		JMenuBar menuBar = new JMenuBar();

		// Add the menubar to the frame
		setJMenuBar(menuBar);

		// Define and add two drop down menu to the menubar
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		// Create and add simple menu item to one of the drop down menu
		JMenuItem chooseAction = new JMenuItem("Choose file");
		JMenuItem exitAction = new JMenuItem("Exit");
		JMenuItem helpAction = new JMenuItem("Help");
		JMenuItem aboutAction = new JMenuItem("About");

		fileMenu.add(chooseAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);
		helpMenu.add(helpAction);
		helpMenu.add(aboutAction);

		// Add a listener to the New menu item. actionPerformed() method will
		// invoked, if user triggred this menu item
		chooseAction.addActionListener(this);
		chooseAction.setActionCommand(choose);

		exitAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		helpAction.addActionListener(this);
		helpAction.setActionCommand(help);

		aboutAction.addActionListener(this);
		aboutAction.setActionCommand(about);

		// Create the buttons
		ChooserButton = new JButton("      Choose File      ");
		CalcButton = new JButton("      Calculus      ");
		radiotbp = new JRadioButton(" radiotbp ");
		radioPontosMeio = new JRadioButton("radioPontosMeio");

		// Create the group for radio choice
		ButtonGroup group = new ButtonGroup();

		// Set the ActionListener(); and the radio option
		ChooserButton.addActionListener(this);
		ChooserButton.setActionCommand(choose);
		CalcButton.addActionListener(this);

		group.add(radiotbp);
		group.add(radioPontosMeio);

		// create the panels
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());

		JPanel northCenterPanel = new JPanel();
		northCenterPanel.setLayout(new GridLayout(0, 1));

		JPanel northEastPanel = new JPanel();
		northEastPanel.setLayout(new GridLayout(0, 1));

		JPanel northWestPanel = new JPanel();
		northWestPanel.setLayout(new GridLayout(0, 1));

		resultTextArea = new JTextArea();
		resultTextArea.setEditable(false);

		convergenceTextArea = new JTextArea();
		convergenceTextArea.setEditable(false);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout());

		JScrollPane scroll = new JScrollPane(resultTextArea);
		scroll.setPreferredSize(new Dimension(250, 300));

		eastPanel.add(scroll);

		// add things at the panels

		northEastPanel.add(ChooserButton);
		northEastPanel.add(CalcButton);

		precisionTextField = new JTextField("10000", 5);
		interationsTextField = new JTextField("10000", 5);
		precisionPanel = new JPanel();
		interationsPanel = new JPanel();
		precisionPanel.add(new JLabel("Presision"));
		interationsPanel.add(new JLabel("Interations"));
		precisionPanel.add(precisionTextField);
		interationsPanel.add(interationsTextField);
		northCenterPanel.add(precisionPanel);
		northCenterPanel.add(interationsPanel);
		northWestPanel.add(radiotbp);
		northWestPanel.add(radioPontosMeio);
		radioPontosMeio.setSelected(true);

		northPanel.add(northCenterPanel, BorderLayout.CENTER);
		northPanel.add(northEastPanel, BorderLayout.EAST);
		northPanel.add(northWestPanel, BorderLayout.WEST);

		// create the datasets
		datasetTBP = new XYSeriesCollection();
		datasetD86 = new XYSeriesCollection();

		// Create the charts
		TBPChart = ChartFactory.createXYLineChart("", "phi", "T[K]",
				datasetTBP, PlotOrientation.VERTICAL, true, false, false);

		D86Chart = ChartFactory.createXYLineChart("", "phi", "T[K]",
				datasetD86, PlotOrientation.VERTICAL, true, false, false);

		// Create and add the tabs

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Convergence", null, new JScrollPane(
				convergenceTextArea), "Results of convergence");

		tabbedPane.addTab("TBP", null, new ChartPanel(TBPChart), "Char of TBP");

		tabbedPane
				.addTab("D86", null, new ChartPanel(D86Chart), "Chart of D86");

		// add things to content
		content.add(northPanel, BorderLayout.NORTH);
		content.add(tabbedPane, BorderLayout.CENTER);
		content.add(eastPanel, BorderLayout.EAST);

		pack();
	}

	/**
	 * Main function of the executable
	 * 
	 * @param args
	 * @throws CostException
	 * @throws ConvergenceException
	 */
	public static void main(String[] args) throws CostException,
			ConvergenceException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		Frame frame = new Frame();
		frame.setSize(1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ChooserButton) {
			Choose();
		}

		if (e.getSource() == CalcButton) {
			if (chooser == null) {
				Choose();
			}
			
			prescision = Integer.parseInt(precisionTextField.getText());
			interations = Integer.parseInt(interationsTextField.getText());
			// use the selected file to read the data
			File input = chooser.getSelectedFile();
			// read the data file
			try {
				data = new DataFile(input);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "DEU PAU!", "title",
						JOptionPane.ERROR_MESSAGE);
			}
			content.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			// Create and set the number format
			NumberFormat nf2 = NumberFormat.getInstance();
			NumberFormat nf3 = NumberFormat.getInstance();
			NumberFormat nf4 = NumberFormat.getInstance();
			nf2.setMaximumFractionDigits(2);
			nf3.setMaximumFractionDigits(3);
			nf4.setMaximumFractionDigits(4);

			// Clear all series to a new estimation
			convergenceTextArea.setText("");
			datasetTBP.removeAllSeries();
			datasetD86.removeAllSeries();
			compList.clear();

			// create the series of all things
			XYSeries tbpEscada = new XYSeries("tbpEscada", false);
			XYSeries tbpAjustada = new XYSeries("tbpAjustada", false);
			XYSeries tbpMeioPonto = new XYSeries("tbpMeioPonto", false);
			XYSeries tbpNormal = new XYSeries("tbpNormal", false);

			XYSeries d86normal = new XYSeries("d86normal", false);
			XYSeries d86Ajustada = new XYSeries("d86Ajustada", false);
			XYSeries d86Experimental = new XYSeries("d86Experimental", false);

			// Calculus section
			int numberOfComponents = data.getSizeComp();
			int numberOfNotNullComponents = 0;


			for (int i = 0; i < numberOfComponents; i++) {
				if (data.getMolarFraction()[i] != 0)
					numberOfNotNullComponents++;
			}

			for (int i = 0; i < numberOfComponents; i++) {
				if (data.getMolarFraction()[i] != 0) {
					Component comp = new Component();
					comp.setCompNumber(data.getCompNumber()[i]);
					comp.setMolarVolune(data.getMolarVolume()[i]);
					comp.setBoilingPoint(data.getBoilingPoint()[i]);
					comp.setMolarFraction(data.getMolarFraction()[i]);
					compList.add(comp);
				}
			}

			// sort the students by descending grade
			Collections.sort(compList, new Sort());

			int indexSort = 0;
			int compNumber[] = new int[numberOfNotNullComponents];
			double molarVolume[] = new double[numberOfNotNullComponents];
			double boilingPoint[] = new double[numberOfNotNullComponents];
			double molarFraction[] = new double[numberOfNotNullComponents];

			// print the list
			for (Component co : compList) {
				if (co.getMolarFraction() != 0) {
					compNumber[indexSort] = (int) co.getCompNumber();
					molarVolume[indexSort] = co.getMolarVolune();
					boilingPoint[indexSort] = co.getBoilingPoint();
					molarFraction[indexSort] = co.getMolarFraction();
					indexSort++;
				}
			}
			
			// 
			double volumeMolar[] = new double[numberOfNotNullComponents];
			for (int i = 0; i < numberOfNotNullComponents; i++) {
				volumeMolar[i] = molarVolume[i] * molarFraction[i];
			}
			
			double volumeTotal = somaVetor(volumeMolar);
			double[] volumeFraction = new double[numberOfNotNullComponents];
			double[] acumuledVolumeFraction = new double[numberOfNotNullComponents];

			for (int i = 0; i < numberOfNotNullComponents; i++) {
				volumeFraction[i] = volumeMolar[i] / volumeTotal;
			}
			
			
			for (int i = 0; i < numberOfNotNullComponents; i++) {
				double vol = 0;
				for (int j = 0; j <= i; j++){
					vol = volumeFraction[j] + vol;
				}
				acumuledVolumeFraction[i] = vol;
			}

			double pontosmeio[] = new double[numberOfNotNullComponents];
			for (int i = 0; i < pontosmeio.length; i++) {
				if (i == 0) {
					pontosmeio[0] = acumuledVolumeFraction[0] / 2;
				} else {
					pontosmeio[i] = (acumuledVolumeFraction[i] - acumuledVolumeFraction[i - 1])
							/ 2 + acumuledVolumeFraction[i - 1];
				}
				tbpMeioPonto.add(pontosmeio[i], boilingPoint[i]);
			}
			datasetTBP.addSeries(tbpMeioPonto);


			
			// Ciração da curva completa de TBP
			double tbp[] = new double[prescision];
			double phi[] = new double[tbp.length];
			for (int i = 0; i < tbp.length; i++) {
				double distFrac = i / ((double) prescision);
				phi[i] = distFrac;
				if (phi[i] < acumuledVolumeFraction[0]) {
					tbp[i] = boilingPoint[0];
				}
				if (phi[i] > acumuledVolumeFraction[0]) {
					for (int k = 1; k < acumuledVolumeFraction.length; k++) {
						if (phi[i] > acumuledVolumeFraction[k - 1]
								&& phi[i] < acumuledVolumeFraction[k]) {
							tbp[i] = boilingPoint[k];
						}
					}
				}
				tbpEscada.add(phi[i], tbp[i]);
			}
			datasetTBP.addSeries(tbpEscada);



			
			if (radioPontosMeio.isSelected() == true) {
				correlationTBP = new Correlation(boilingPoint, pontosmeio,
						boilingPoint.length);
				optimizer = new NelderMead();
				try {
					optimizer.minimize(correlationTBP, interations,
							new ValueChecker(0.0001), new double[][] {
									{ 100, 100 }, { 100, 99 }, { 99, 100 } });
				} catch (org.apache.commons.math.ConvergenceException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Not converge",
							"Error", JOptionPane.ERROR_MESSAGE);
				} catch (CostException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Error at cost function", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if (radiotbp.isSelected() == true) {
				correlationTBP = new Correlation(tbp, phi, tbp.length);
				optimizer = new NelderMead();
				try {
					optimizer.minimize(correlationTBP, interations,
							new ValueChecker(0.0001), new double[][] {
									{ 100, 100 }, { 100, 90 }, { 90, 100 } });
				} catch (org.apache.commons.math.ConvergenceException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Not converge",
							"Error", JOptionPane.ERROR_MESSAGE);
				} catch (CostException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Error at cost function", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			System.out.println(correlationTBP.getEvoluations());
			
			
//			Graficar a curva de destilação ajustada
			for (int  i = 100; i < prescision; i++) {
				double x = (i / (double) prescision);
				tbpAjustada.add(
						x,
						correlationTBP.getTemp(x,
								correlationTBP.getParameter()));
			}
			datasetTBP.addSeries(tbpAjustada);
			datasetD86.addSeries(tbpAjustada);

			
			
			resultTextArea.append("\n   A = "
					+ nf4.format(correlationTBP.getParameter()[0]));
			resultTextArea.append("\n   B = "
					+ nf4.format(correlationTBP.getParameter()[1]));

			resultTextArea.append("\n   Error = "
					+ nf4.format(correlationTBP.getError()));

			convergenceTextArea.append("\n   A = "
					+ nf4.format(correlationTBP.getParameter()[0]));
			convergenceTextArea.append("\n   B = "
					+ nf4.format(correlationTBP.getParameter()[1]));

			convergenceTextArea.append("\n   Final Error = "
					+ nf4.format(correlationTBP.getError()) + "\n\n");
			
			
			
			double valorTBPAjustada[] = new double[dist.length];
			

			for (int j = 0; j < dist.length; j++) {
				if (dist[j] == 0) {
					valorTBPAjustada[j] = correlationTBP.getTemp(0.01,
							correlationTBP.getParameter());
				}
				if (dist[j] == 100) {
					valorTBPAjustada[j] = correlationTBP.getTemp(0.99,
							correlationTBP.getParameter());
				}
				if ((dist[j] != 0) && (dist[j] != 100)) {
					valorTBPAjustada[j] = correlationTBP.getTemp(
							(double) dist[j] / 100,
							correlationTBP.getParameter());
				}
			}

			//
			double valorTBPNormal[] = new double[dist.length];
			for (int i = 0; i < valorTBPNormal.length; i++) {
				if (dist[i] / 100 < acumuledVolumeFraction[0]) {
					valorTBPNormal[i] = boilingPoint[0];
				}
				if (dist[i] / 100 > acumuledVolumeFraction[0]) {
					for (int k = 1; k < acumuledVolumeFraction.length; k++) {
						if (dist[i] / 100 > acumuledVolumeFraction[k - 1]
								&& dist[i] / 100 < acumuledVolumeFraction[k]) {
							valorTBPNormal[i] = boilingPoint[k];
						}
					}
				}
				if (i == dist.length - 1) {
					valorTBPNormal[i] = boilingPoint[boilingPoint.length - 1];
				}
				tbpNormal.add(dist[i] / 100, valorTBPNormal[i]);
			}
			datasetTBP.addSeries(tbpNormal);

			// D86 Calc normal
			double deltaTNormal[] = new double[valorTBPNormal.length];
			for (int j = 0; j < deltaTNormal.length; j++) {
				if (dist[j] < 50) {
					deltaTNormal[j] = (valorTBPNormal[j + 1] - valorTBPNormal[j]) * 1.8;
					if (deltaTNormal[j] <= 0) {
						deltaTNormal[j] = 1e-4;
					}
				}
				if (dist[j] == 50) {
					deltaTNormal[j] = 0;
				}
				if (dist[j] > 50) {
					deltaTNormal[j] = (valorTBPNormal[j] - valorTBPNormal[j - 1]) * 1.8;
					if (deltaTNormal[j] <= 0) {
						deltaTNormal[j] = 1e-4;
					}
				}
			}

			double deltaTAjustado[] = new double[dist.length];
			for (int j = 0; j < deltaTAjustado.length; j++) {
				if (dist[j] < 50) {
					deltaTAjustado[j] = (valorTBPAjustada[j + 1] - valorTBPAjustada[j]) * 1.8;
					if (deltaTAjustado[j] <= 0) {
						deltaTAjustado[j] = 1e-4;
					}
				}
				if (dist[j] == 50) {
					deltaTAjustado[j] = 0;
				}
				if (dist[j] > 50) {
					deltaTAjustado[j] = (valorTBPAjustada[j] - valorTBPAjustada[j - 1]) * 1.8;
					if (deltaTAjustado[j] <= 0) {
						deltaTAjustado[j] = 1e-4;
					}
				}
			}

			double deltaSomaNormal[] = new double[deltaTNormal.length];
			double deltaSomaAjustado[] = new double[deltaTAjustado.length];

			double parametroA[] = { 7.4012, 7.4012, 4.9004, 3.0305, 0, 2.5282,
					3.0419, 0.11798, 0.11798 };
			
			double parametroB[] = { 0.60244, 0.60244, 0.71644, 0.80076, 0,
					0.82002, 0.751497, 1.6606, 1.6606 };

			for (int j = 0; j < deltaSomaNormal.length; j++) {
				if (dist[j] != 50) {
					deltaSomaNormal[j] = Math.exp(Math.log(deltaTNormal[j]
							/ parametroA[j])
							/ parametroB[j]) / 1.8;
				}
				if (dist[j] == 50) {
					deltaSomaNormal[j] = (Math.exp(Math
							.log((valorTBPNormal[j]*1.8-459.67) / 0.8718) / 1.0258)+459.67)/1.8;
				}
			}
			
			for (int j = 0; j < deltaSomaAjustado.length; j++) {
				if (dist[j] != 50) {
					deltaSomaAjustado[j] = Math.exp(Math.log(deltaTAjustado[j]
							/ parametroA[j])
							/ parametroB[j]) / 1.8;
				}
				if (dist[j] == 50) {
					deltaSomaAjustado[j] = (Math.exp(Math
							.log((valorTBPAjustada[j]*1.8-459.67) / 0.8718) / 1.0258)+459.67)/1.8;
				}
			}

			double d86Normal[] = new double[dist.length];
			double d86Ajustado[] = new double[dist.length];

			d86Normal[4] = deltaSomaNormal[4];
			d86Normal[3] = d86Normal[4] - deltaSomaNormal[3];
			d86Normal[2] = d86Normal[3] - deltaSomaNormal[2];
			d86Normal[1] = d86Normal[2] - deltaSomaNormal[1];
			d86Normal[0] = d86Normal[1] - deltaSomaNormal[0];

			d86Normal[5] = d86Normal[4] + deltaSomaNormal[5];
			d86Normal[6] = d86Normal[5] + deltaSomaNormal[6];
			d86Normal[7] = d86Normal[6] + deltaSomaNormal[7];
			d86Normal[8] = d86Normal[7] + deltaSomaNormal[8];

			d86Ajustado[4] = deltaSomaAjustado[4];
			d86Ajustado[3] = d86Ajustado[4] - deltaSomaAjustado[3];
			d86Ajustado[2] = d86Ajustado[3] - deltaSomaAjustado[2];
			d86Ajustado[1] = d86Ajustado[2] - deltaSomaAjustado[1];
			d86Ajustado[0] = d86Ajustado[1] - deltaSomaAjustado[0];

			d86Ajustado[5] = d86Ajustado[4] + deltaSomaAjustado[5];
			d86Ajustado[6] = d86Ajustado[5] + deltaSomaAjustado[6];
			d86Ajustado[7] = d86Ajustado[6] + deltaSomaAjustado[7];
			d86Ajustado[8] = d86Ajustado[7] + deltaSomaAjustado[8];

			convergenceTextArea.append("\n	");
			for (int j = 0; j < dist.length; j++) {
				d86Ajustada.add((double) dist[j] / 100, d86Ajustado[j]);
				d86normal.add((double) dist[j] / 100, d86Normal[j]);
			}
			for (int i = 0; i < data.getSizePhi(); i++) {
				d86Experimental.add((double)data.getPhi()[i] / 100, data.getTemp()[i]);

			}
			
			// Erro em relação aos dados experimentais
			double erroAjustado [] = new double [dist.length];
			double erroNormal [] = new double [dist.length];
			double erroMedioAjustado = 0;
			double erroMedioNormal = 0;
			for (int i = 0; i < d86Ajustado.length; i++) {
				for (int i2 = 0; i2 < data.getSizePhi(); i2++) {
					if(dist[i]==data.getPhi()[i2]){
						erroAjustado[i] = d86Ajustado[i]-data.getTemp()[i2];
						erroNormal[i] = d86Normal[i]-data.getTemp()[i2];
						if (dist[i]!=0&&dist[i]!=100){
							erroMedioNormal = erroMedioNormal +Math.abs(erroNormal[i]);
							erroMedioAjustado = erroMedioAjustado +Math.abs(erroAjustado[i]);
						}
					}
				}
			}
			convergenceTextArea.append("\n	"+erroMedioAjustado/((double)dist.length-2) +"	"+erroMedioNormal/((double)dist.length-2));

			datasetD86.addSeries(d86Ajustada);
			datasetD86.addSeries(d86normal);
			datasetD86.addSeries(d86Experimental);

			XYPlot plotTBP = (XYPlot) TBPChart.getPlot();
			// adjusting the plot range for the "X Y" axis
			plotTBP.getDomainAxis().setAutoRange(false);
			plotTBP.getDomainAxis().setRange(new Range(0, 1));

			// adjusting the plot range for the "Gamma" axis
			TBPChart.getXYPlot()
					.getRangeAxis()
					.setRange((0.99 * datasetTBP.getRangeLowerBound(true)),
							(1.01 * datasetTBP.getRangeUpperBound(true)));
			
			XYPlot plotD86 = (XYPlot) D86Chart.getPlot();
			// adjusting the plot range for the "X Y" axis
			plotD86.getDomainAxis().setAutoRange(false);
			plotD86.getDomainAxis().setRange(new Range(0, 1));

			// adjusting the plot range for the "Gamma" axis
			D86Chart.getXYPlot()
					.getRangeAxis()
					.setRange((0.99 * datasetD86.getRangeLowerBound(true)),
							(1.01 * datasetD86.getRangeUpperBound(true)));


		}
		content.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

	}

	/**
	 * Choose function to call JFileChooser
	 */
	private void Choose() {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter("Data Files", "txt", "dat");
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(getParent());
		// TODO remove error when nothing is selected
		if (chooser.getSelectedFile() == null) {
			System.exit(0);
		}
	}

	static double somaVetor(double vetor[]) {
		double soma = 0;
		for (int i = 0; i < vetor.length; i++) {
			soma += vetor[i];
		}
		return soma;
	}

}