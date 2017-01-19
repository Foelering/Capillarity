package gui;

import java.util.List;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.*;
import java.util.AbstractList;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import utils.Liquid;

public class SideContainer extends Panel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2718216431652224748L;
	
	
	private List<Liquid> liquidList = new ArrayList<Liquid>(); //editable list of liquids available, gets it from conf file
	private XYSeriesCollection seriescollection = new XYSeriesCollection(); //collection which will be shown on the graph
	
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
		    XYSeries f = b.getSeries();
		    System.out.println(f.getItems());
		    this.seriescollection.addSeries(f);
		    b.addActionListener(this);
		    add(b);
		    revalidate();
		    
		} else {
		    System.out.println("User canceled / closed the dialog, result = " + result);
		}
	}
	
	SideContainer(){
		liquidList.add(new Liquid("Acqua", 7.26, 1, 30));
		liquidList.add(new Liquid("Alcool", 2.2, 0.789, 0));
		liquidList.add(new Liquid("Ioduro di Metilene", 5.08, 3.3, 0));
		liquidList.add(new Liquid("Mercurio", 43.55, 13.6, 148));
		
		
		setSize(100, 300);
		setLayout(new GridLayout(0,1));
		
		Button Adder = new Button("+");
		Adder.setSize(100,100);
		Adder.addActionListener(this);
		
		this.add(Adder);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		createDialog();
		
	}
}
