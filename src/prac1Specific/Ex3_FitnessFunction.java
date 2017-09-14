package prac1Specific;

import ags.cromosome.Fenotype;
import ags.cromosome.Fitness;
import ags.cromosome.FitnessFunction;
import ags.cromosome.implementations.fenotypes.Fenotype_NDouble;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class Ex3_FitnessFunction implements FitnessFunction {
	
	public static double[] getMins() {
		double mins [] = {-3, 4.1};
		return mins;
	}
	
	public static double[] getMaxs() {
		double maxs [] = {12.1, 5.8};
		return maxs;
	}

	@Override
	public Fitness evaluate(Fenotype fenotype) {
		double x1 = ((Fenotype_NDouble) fenotype).getValue(0);
		double x2 = ((Fenotype_NDouble) fenotype).getValue(1);
		double r = 21.5f + x1*Math.sin(4f*Math.PI*x1) + x2*Math.sin(20f*Math.PI*x2);
		
		return new Fitness_Double(r);		
	}
	
	@Override
	public String toString() {
		return "Ejercicio 3";
	}

	@Override
	public boolean maximize() {
		return true;
	}
}
