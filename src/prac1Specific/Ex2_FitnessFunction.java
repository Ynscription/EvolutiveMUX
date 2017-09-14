package prac1Specific;

import ags.cromosome.Fenotype;
import ags.cromosome.Fitness;
import ags.cromosome.FitnessFunction;
import ags.cromosome.implementations.fenotypes.Fenotype_NDouble;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class Ex2_FitnessFunction implements FitnessFunction{
	
	public static double[] getMins() {
		double mins [] = {-512, -512};
		return mins;
	}
	
	public static double[] getMaxs() {
		double maxs [] = {512, 512};
		return maxs;
	}
	
	@Override
	public Fitness evaluate(Fenotype fenotype) {
		double x1 = ((Fenotype_NDouble) fenotype).getValue(0);
		double x2 = ((Fenotype_NDouble) fenotype).getValue(1);
		double r = -((x2 + 47f)*Math.sin(Math.sqrt(Math.abs(x2 + x1/2 + 47f)))) - x1*Math.sin(Math.sqrt(Math.abs(x1 - x2 - 47f)));
		
		return new Fitness_Double(r);
		
	}
	
	@Override
	public String toString() {
		return "Ejercicio 2";
	}

	@Override
	public boolean maximize() {
		return false;
	}
}
