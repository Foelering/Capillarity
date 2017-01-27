package utils;

import java.lang.Math;
import org.jfree.data.xy.XYSeries;


/**
 *A class to register the information needed to calculate the information
 * needed for Jurin's law, and generate series when needed.
 *
 */


public class Liquid{
	
	private static int numberofseries;
	
	private String name=null;
	private double tension, density, cos; //using CGS system. Cos is the cosine of the contact angle.
	private final double g=9806.65; //g in mm/s^2
	
	//creates a new liquid, these fields cannot be changed afterwards in any way.
	public Liquid(String name, double tension, double density, double angle){
		this.name = name;
		this.tension = tension;
		this.density = density;
		this.cos = Math.cos(Math.toRadians(angle));
	}
	
	public String getName(){
		return name;
	}
	
	private double heigth(double r){
		return 2*tension*cos/(r*density*g);
	}
	
	//only real public interface, generates a series spanning from start to end with some steps in the middle
	public XYSeries generateSeries(double start, double end, int steps){
		double step = (end-start)/steps;
		numberofseries++;
		String a = this.name + "_" + numberofseries;
				
		XYSeries series = new XYSeries(a);
		
		series.add(start,heigth(start));
		for (int i=1;i<=steps;i++){
			series.add(start + i*step, heigth(start + i*step));
		}
		series.add(end,heigth(end));
		
		return series;
	}
}
