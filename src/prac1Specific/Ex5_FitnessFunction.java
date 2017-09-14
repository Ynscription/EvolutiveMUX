package prac1Specific;

import ags.cromosome.Fenotype;
import ags.cromosome.Fitness;
import ags.cromosome.FitnessFunction;
import ags.cromosome.implementations.fenotypes.Fenotype_NDouble;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class Ex5_FitnessFunction implements FitnessFunction {
	
	public static double[] getMins() {
		double mins [] = {-10, -10};
		return mins;
	}
	
	public static double[] getMaxs() {
		double maxs [] = {10, 10};
		return maxs;
	}

	@Override
	public Fitness evaluate(Fenotype fenotype) {
		Fenotype_NDouble fo = (Fenotype_NDouble) fenotype;
		double x1 = fo.getValue(0);
		double x2 = fo.getValue(1);
		
		double res1 = 0;
		double res2 = 0;
		
		for (int i = 1; i <= 5; i++) {
			res1 += i*Math.cos((i+1)*x1 + i);
			res2 += i*Math.cos((i+1)*x2 + i);
		}		
		
		double res = res1*res2;
		
		return new Fitness_Double(res);		
	}
	
	@Override
	public String toString() {
		return "Ejercicio 5";
	}

	@Override
	public boolean maximize() {
		return false;
	}
}
