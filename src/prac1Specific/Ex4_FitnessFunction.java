package prac1Specific;

import ags.cromosome.Fenotype;
import ags.cromosome.Fitness;
import ags.cromosome.FitnessFunction;
import ags.cromosome.implementations.fenotypes.Fenotype_NDouble;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class Ex4_FitnessFunction implements FitnessFunction {
	
	public static double[] getMins(int n) {
		double mins [] = new double [n];
		for (int i = 0; i < n; i++) {
			mins [i] = 0;
		}
		return mins;
	}
	
	public static double[] getMaxs(int n) {
		double maxs [] = new double [n];
		for (int i = 0; i < n; i++) {
			maxs [i] = Math.PI; 
		}
		return maxs;
	}

	@Override
	public Fitness evaluate(Fenotype fenotype) {
		Fenotype_NDouble fo = (Fenotype_NDouble) fenotype;
		double res = 0;
		for (int i = 0; i < fo.getN(); i++) {
			double xi = fo.getValue(i);
			res -= Math.sin(xi)*(Math.pow(Math.sin(((i+2)*xi*xi)/Math.PI), 20));
		}
		
		return new Fitness_Double(res);		
	}
	
	@Override
	public String toString() {
		return "Ejercicio 4";
	}

	@Override
	public boolean maximize() {
		return false;
	}
}