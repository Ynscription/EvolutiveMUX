package prac1Specific;

import ags.cromosome.Fenotype;
import ags.cromosome.Fitness;
import ags.cromosome.FitnessFunction;
import ags.cromosome.implementations.fenotypes.Fenotype_NDouble;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class Ex1_FitnessFunction implements FitnessFunction{
	
	public static double[] getMins() {
		double mins [] = {-250};
		return mins;
	}
	
	public static double[] getMaxs() {
		double maxs [] = {250};
		return maxs;
	}
	
	@Override
	public Fitness evaluate(Fenotype f) {
		double fv = ((Fenotype_NDouble) f).getValue(0);
		return new Fitness_Double(- Math.abs(fv*Math.sin(Math.sqrt(Math.abs(fv)))));
	}
	
	@Override
	public String toString() {
		return "Ejercicio 1";
	}

	@Override
	public boolean maximize() {
		return false;
	}

}
