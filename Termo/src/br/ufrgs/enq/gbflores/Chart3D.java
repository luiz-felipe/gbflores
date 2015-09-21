package br.ufrgs.enq.gbflores;

import org.opensourcephysics.display.DrawingPanel;
import org.opensourcephysics.display2d.ArrayData;
import org.opensourcephysics.display2d.GridData;
import org.opensourcephysics.display2d.SurfacePlot;
import org.opensourcephysics.display2d.SurfacePlotMouseController;



public class Chart3D {
	public DrawingPanel drawingPanel = new DrawingPanel();
	public Chart3D() {
		
//		DrawingPanel3D d = new DrawingPanel3D();
		
		
		SurfacePlot surface = new SurfacePlot();
		drawingPanel.addDrawable(surface);
		
		drawingPanel.setAutoscaleX(true);
		drawingPanel.setAutoscaleY(true);

		// Add a mouse interaction (then we can rotate the plot)
		SurfacePlotMouseController mouseController = 
				new SurfacePlotMouseController(drawingPanel,surface);
		drawingPanel.addMouseListener(mouseController);
		drawingPanel.addMouseMotionListener(mouseController);

		
		 //Create a surface plot and add to the panel
		surface.setAxisLabels("v", "Tr", "P");

		// Get a grid data and evaluate the funciton at the grid points
		double xl[] = new double[2];
		double xu[] = new double[2];


		xl[0] = 0;
		xu[0] = 10;

		xl[1] = 0;
		xu[1] = 10;

		// Create some test data and add it to the contour
		int grid = 100;
		GridData data = new ArrayData(grid, grid, 2);
		data.setScale(xl[0], xu[0], xu[1], xl[1]);
		
		data.setComponentName(0, "teste1");
		data.setComponentName(1, "teste2");
		
		for (int i = 0; i < data.getNx(); i++) {
			for (int j = 0; j < data.getNy(); j++) {

				double a1 = xl[0] + (xu[0] - xl[0])*(i)/((double)data.getNx()-1);
				double a2 = xl[1] + (xu[1] - xl[1])*(j)/((double)data.getNy()-1);
				
				double teste1 = a1*a1+a2;
				double teste2 = a1-a2*a2;

				data.setValue(i, j, 0, teste1);
				data.setValue(i, j, 1, teste2);
			}
		}
		surface.setGridData(data);
		
//		Comp c = new Comp("water");
//		CEoSVdW e = new CEoSVdW(c);
		
		
		
		
//		surface.setGridData(e.addSurface());
		// Set autoscale
		surface.setAutoscaleZ(true, 0, 0);
		

		

	}

}
