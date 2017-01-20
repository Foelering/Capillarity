package gui;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import utils.Liquid;

public class SideContainer extends Panel implements ActionListener{
	
	private static final long serialVersionUID = -4807314086451566347L;
	private List<Liquid> liquidList = new ArrayList<Liquid>(); //editable list of liquids available, gets it from conf file
	private XYSeriesCollection seriesCollection = new XYSeriesCollection(); //collection which will be shown on the graph
	private Button buttonAdder = new Button("+");
	
	public XYSeriesCollection getDataset(){
		return seriesCollection;
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
		
		@SuppressWarnings("unused")
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
		
		public void close(){
			seriesCollection.removeSeries(LiquidSeries);
		}
		
		LiquidButton(Liquid liquid, double start, double end, int steps){
			
			super(liquid.getName() + ", " + start + "-"+ end );
			this.liquid=liquid;
			this.start=start;
			this.end=end;
			this.steps=steps;	
			LiquidSeries = liquid.generateSeries(start, end, steps);
			setPreferredSize(new java.awt.Dimension(80, 20));
			seriesCollection.addSeries(LiquidSeries);
		}
		
		
	}
	
	private void createButton(){
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
		    b.addActionListener(this);
		    add(b);
		    
		} else {
		    System.out.println("User canceled / closed the dialog, result = " + result);
		}
	}
	
	private void createButton(double oldstart, double oldend, int oldsteps){
		JTextField start = new JTextField(Double.toString(oldstart));
		JTextField end = new JTextField(Double.toString(oldend));
		JTextField steps = new JTextField(Integer.toString(oldsteps));
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
		    b.addActionListener(this);
		    add(b);
		    
		} else {
		    System.out.println("User canceled / closed the dialog, result = " + result);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println(e.getSource().getClass().getSimpleName());
		
		if(e.getSource() == buttonAdder){
			createButton();
			
		} else if (e.getSource() instanceof LiquidButton){
			((LiquidButton) e.getSource()).close();
			double S = ((LiquidButton) e.getSource()).getStart();
			double E = ((LiquidButton) e.getSource()).getEnd();
			int N = ((LiquidButton) e.getSource()).getSteps();
			remove((LiquidButton) e.getSource());
			createButton(S,E,N);
		}

		revalidate();
	}
	
	SideContainer(){
		
		liquidList.add(new Liquid("Acqua", 7.26, 1, 30));
		liquidList.add(new Liquid("Alcool", 2.2, 0.789, 0));
		liquidList.add(new Liquid("Ioduro di Metilene", 5.08, 3.3, 0));
		liquidList.add(new Liquid("Mercurio", 43.55, 13.6, 148));
		
		setLayout(new GridLayout(0,1));
		setPreferredSize(new java.awt.Dimension(100, 300));
		add(buttonAdder);
		buttonAdder.addActionListener(this);
	}

}
