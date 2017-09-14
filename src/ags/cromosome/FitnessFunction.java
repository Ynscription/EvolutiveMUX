package ags.cromosome;

public interface FitnessFunction {
	public Fitness evaluate(Fenotype fenotype);
	
	public boolean maximize();
}
