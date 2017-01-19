package gui;
import java.awt.*;
import java.awt.event.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import utils.Liquid;
import gui.SideContainer;
import gui.Chart;


public class MainWindow extends Frame{
	
	XYSeries testseries = new XYSeries("Prova");
	
	MainWindow(){
		
		setTitle("Grafici!");
		setLayout(new BorderLayout());
		setSize(500, 300);
		SideContainer sidepane = new SideContainer();
		add(sidepane, BorderLayout.WEST);
//		add(chartPanel, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent we)
			{
			System.exit(0);
			}
		});
		setVisible(true);
		
	};
	
	private JFreeChart makeChart(XYSeriesCollection dataset){//Simply returns an appropriate chart
		return ChartFactory.createXYLineChart("", "Radius", "Heigth",dataset, PlotOrientation.VERTICAL, true, true, false);
	}
	
	

	public static void main(String args[]){  
		MainWindow mainWindow = new MainWindow();  
	}
}



//Chart chart=new Chart();
//final XYSeries firefox = new XYSeries( "Firefox" );          
//  firefox.add( 1.0 , 1.0 );          
//  firefox.add( 2.0 , 4.0 );          
//  firefox.add( 3.0 , 3.0 );          
//  final XYSeries chrome = new XYSeries( "Chrome" );          
//  chrome.add( 1.0 , 4.0 );          
//  chrome.add( 2.0 , 5.0 );          
//  chrome.add( 3.0 , 6.0 );          
//  final XYSeries iexplorer = new XYSeries( "InternetExplorer" );          
//  iexplorer.add( 3.0 , 4.0 );          
//  iexplorer.add( 4.0 , 5.0 );          
//  iexplorer.add( 5.0 , 4.0 );          
//  final XYSeriesCollection dataset = new XYSeriesCollection( );          
//  dataset.addSeries( firefox );          
//  dataset.addSeries( chrome );          
//  dataset.addSeries( iexplorer );
//  
//  JFreeChart xylineChart = ChartFactory.createXYLineChart(
//	         "" ,
//	         "Radius",
//	         "Heigth",dataset,
//	         PlotOrientation.VERTICAL,
//	         true , true , false);
//	         
//	      ChartPanel chartPanel = new ChartPanel( xylineChart );
//	      chartPanel.setPreferredSize( new java.awt.Dimension( 400 , 280 ) );
//	      final XYPlot plot = xylineChart.getXYPlot( );
//	      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
//	      renderer.setSeriesPaint( 0 , Color.RED );
//	      renderer.setSeriesPaint( 1 , Color.GREEN );
//	      renderer.setSeriesPaint( 2 , Color.YELLOW );
//	      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
//	      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
//	      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
//	      plot.setRenderer( renderer );