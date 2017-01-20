package gui;
import java.awt.*;
import java.awt.event.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;




public class MainWindow extends Frame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4654752977065080419L;
	
	private SideContainer sidePane = new SideContainer();
	
	MainWindow(){
		
		setTitle("Grafici!");
		setLayout(new BorderLayout());
		add(sidePane, BorderLayout.WEST);
		
		setSize(500, 300);
		
		JFreeChart xylineChart = ChartFactory.createXYLineChart(
	        "" ,
	        "Radius",
	        "Heigth",sidePane.getDataset(),
	        PlotOrientation.VERTICAL,
	        true , true , false);
		
		ChartPanel chartPanel = new ChartPanel( xylineChart );
		
		add(chartPanel, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent we)
			{
			System.exit(0);
			}
		});
		setVisible(true);
		
	};

	public static void main(String args[]){  
		@SuppressWarnings("unused")
		MainWindow mainWindow = new MainWindow();  
	}
}