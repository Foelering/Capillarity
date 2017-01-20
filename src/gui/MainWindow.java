package gui;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import utils.Liquid;


public class MainWindow extends Frame implements ActionListener{
	
	private List<Liquid> liquidList = new ArrayList<Liquid>(); //editable list of liquids available, gets it from conf file
	private XYSeriesCollection seriescollection = new XYSeriesCollection(); //collection which will be shown on the graph
	private Panel sidepane = new Panel();	
	private Button Adder = new Button("+");
	private List<LiquidButton> buttonlist = new ArrayList<LiquidButton>();
	
	public XYSeriesCollection getDataset(){
		return seriescollection;
	}
	
	private class LiquidButton extends Button{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1816301676921356696L;
		
		
		private XYSeries LiquidSeries;
		private Liquid liquid;
		private double start, end;
		private int steps;
		
		public XYSeries getSeries(){
			return LiquidSeries;
		}
		
		public Liquid getLiquid(){
			return liquid;
		}
		
		public double getStart(){
			return start;
		}
		
		public double getEnd(){
			return end;
		}
		
		public int getSteps(){
			return steps;
		}
		
		LiquidButton(Liquid liquid, double start, double end, int steps){
			
			super(liquid.getName() + ", " + start + "-"+ end );
			this.liquid=liquid;
			this.start=start;
			this.end=end;
			this.steps=steps;	
			LiquidSeries = liquid.generateSeries(start, end, steps);
		}
	}
	
	void createDialog(){
		JTextField start = new JTextField();
		JTextField end = new JTextField();
		JTextField steps = new JTextField();
		final JComponent[] inputs = new JComponent[] {
		        new JLabel("Start"),
		        start,
		        new JLabel("End"),
		        end,
		        new JLabel("Steps"),
		        steps
		};
		int result = JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
		    System.out.println("You entered " +
		            start.getText() + ", " +
		            end.getText() + ", " +
		            steps.getText());
		    
		    LiquidButton b = new LiquidButton(liquidList.get(0),Double.parseDouble(start.getText()),Double.parseDouble(end.getText()),Integer.parseInt(steps.getText()));
		    buttonlist.add(b);
		    XYSeries f = b.getSeries();
		    System.out.println(f.getItems());
		    this.seriescollection.addSeries(f);
		    b.addActionListener(this);
		    sidepane.add(b);
		    revalidate();
		    
		} else {
		    System.out.println("User canceled / closed the dialog, result = " + result);
		}
	}
	
	MainWindow(){

		liquidList.add(new Liquid("Acqua", 7.26, 1, 30));
		liquidList.add(new Liquid("Alcool", 2.2, 0.789, 0));
		liquidList.add(new Liquid("Ioduro di Metilene", 5.08, 3.3, 0));
		liquidList.add(new Liquid("Mercurio", 43.55, 13.6, 148));
		
		setTitle("Grafici!");
		setLayout(new BorderLayout());
		sidepane.setLayout(new GridLayout(0,1));
		
		setSize(500, 300);
		add(sidepane, BorderLayout.WEST);
		Adder.setSize(100,100);
		Adder.addActionListener(this);
		
		sidepane.add(Adder);
		
		JFreeChart xylineChart = ChartFactory.createXYLineChart(
	        "" ,
	        "Radius",
	        "Heigth",getDataset(),
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
	
	public void actionPerformed(ActionEvent e){
		System.out.println(e.getSource().getClass().getSimpleName());
		
		if(e.getSource() == Adder){
			createDialog();
			
		} else {
			
			for(LiquidButton x : buttonlist){
				if(x.equals(e.getSource())){
					remove(x);
				}
			}
			
			createDialog();
			
			
			
		}
	}

	public static void main(String args[]){  
		@SuppressWarnings("unused")
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