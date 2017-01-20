package utils;

import java.lang.Math;
import org.jfree.data.xy.XYSeries;

//A class to register the information needed to calculate the information needed for Jurin's law.

public class Liquid{
	
	private static int numberofseries;
	
	private String Name;
	private double Tension, Density, Cos; //using millimeters, grams, seconds. Cos is the cosine of the contact angle.
	private final double g=9806.65; //g in mm/s^2
	
	public Liquid(String Name, double Tension, double Density, double Angle){
		this.Name = Name;
		this.Tension = Tension;
		this.Density = Density;
		this.Cos = Math.cos(Math.toRadians(Angle));
	}
	
	public String getName(){
		return Name;
	}
	
	private double heigth(double r){
		return 2*Tension*Cos/(r*Density*g);
	}
	
	public XYSeries generateSeries(double start, double end, int steps){
		double step = (end-start)/steps;
		numberofseries++;
		String a = this.Name + "_" + numberofseries;
		
		
		XYSeries series = new XYSeries(a);
		for (int i=0;i<steps;i++){
			series.add(start + i*step, heigth(start + i*step));
		}
		
		return series;
	}
}
