package gui;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
	private Button liquidAdder = new Button("New Liquid...");
	
	public XYSeriesCollection getDataset(){
		return seriesCollection;
	}
	
	public Button getLiquidAdder(){
		return liquidAdder;
	}
	
	private void remove(LiquidButton button){
		button.close();
		remove((Button) button);
	}
	
	private class LiquidButton extends Button{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1816301676921356696L;
		

	
		private XYSeries liquidSeries;
		private Liquid liquid;
		private double start=0, end=0;
		private int steps=0;
		
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
		
		public void changeTo(Liquid liquid, double start, double end, int steps){
			if(liquidSeries!=null){
				seriesCollection.removeSeries(liquidSeries);
			}
			this.liquid=liquid;
			this.start=start;
			this.end=end;
			this.steps=steps;
			this.setLabel(liquid.getName() + "\n, " + start + "-"+ end );
			liquidSeries = liquid.generateSeries(start, end, steps);
			setPreferredSize(new java.awt.Dimension(80, 20));
			seriesCollection.addSeries(liquidSeries);
		}
		
		public void close(){
			seriesCollection.removeSeries(this.liquidSeries);
		}		

		public LiquidButton() {
			super();
		}
		
		
	}
	
	private void createButton(){
		createButton(new LiquidButton());
	}
	
	private void createButton(LiquidButton button){
		
		List<String> strings = new ArrayList<String>();
		Iterator<Liquid> i = liquidList.iterator();
		while(i.hasNext()){
			strings.add(i.next().getName());
		}
		DefaultComboBoxModel<String> namelist = new DefaultComboBoxModel<String>(strings.toArray(new String[strings.size()]));
		
		if(button.getLiquid()==null){
			namelist.setSelectedItem(strings.get(0));
		}else{
			namelist.setSelectedItem(strings.get(liquidList.indexOf(button.getLiquid())));
		}
		
		JComboBox<String> liquidChooser = new JComboBox<String>(namelist);
		
		JTextField start = new JTextField(Double.toString(button.getStart()));
		JTextField end = new JTextField(Double.toString(button.getEnd()));
		JTextField steps = new JTextField(Integer.toString(button.getSteps()));
        JCheckBox deletePrompt = new JCheckBox("Delete the series?");
		final JComponent[] inputs = new JComponent[] {
				new JLabel("Liquid"),
				liquidChooser,
		        new JLabel("Start"),
		        start,
		        new JLabel("End"),
		        end,
		        new JLabel("Steps"),
		        steps,
		        deletePrompt
		};
		int result = JOptionPane.showConfirmDialog(null, inputs, "Button options", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
		    System.out.println("You entered " +
		    		liquidChooser.getSelectedItem() + ", " +
		            start.getText() + ", " +
		            end.getText() + ", " +
		            steps.getText());
		    if (deletePrompt.isSelected()){
		    	button.close();
		    	remove(button);
		    } else {
		    	button.changeTo(liquidList.get(liquidChooser.getSelectedIndex()),Double.parseDouble(start.getText()),Double.parseDouble(end.getText()),Integer.parseInt(steps.getText()));
			    button.addActionListener(this);
			    add(button);
		    }
		    
		} else if (result ==JOptionPane.CANCEL_OPTION) {
		    System.out.println("User canceled / closed the dialog, result = " + result);
		} else {
			
		}
	}

	private void addLiquid(){
		
		JTextField name = new JTextField();
		JTextField tension = new JTextField();
		JTextField density = new JTextField();
		JTextField angle = new JTextField();
		final JComponent[] inputs = new JComponent[] {
				new JLabel("Liquid Name"),
				name,
		        new JLabel("Surface Tension"),
		        tension,
		        new JLabel("Density"),
		        density,
		        new JLabel("Angle"),
		        angle
		};
		int result = JOptionPane.showConfirmDialog(null, inputs, "Create New Liquid", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			liquidList.add(new Liquid(name.getText(),Double.parseDouble(tension.getText()), Double.parseDouble(density.getText()), Double.parseDouble(angle.getText())));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == buttonAdder){
			createButton();
			
		}else if(e.getSource() == liquidAdder){
			
			addLiquid();
			
		} else if (e.getSource() instanceof LiquidButton){
			createButton((LiquidButton) e.getSource());
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
		liquidAdder.addActionListener(this);
	}

}
