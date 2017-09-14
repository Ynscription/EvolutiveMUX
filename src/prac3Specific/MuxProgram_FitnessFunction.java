package prac3Specific;

import ags.cromosome.Fenotype;
import ags.cromosome.Fitness;
import ags.cromosome.FitnessFunction;
import ags.cromosome.implementations.fenotypes.Fenotype_TreeAsProgram;
import ags.cromosome.implementations.fitnesses.Fitness_Double;
import ags.cromosome.implementations.genotypes.treeAsProgram.TerminalValues;

public class MuxProgram_FitnessFunction implements FitnessFunction {
	
	private int inputs;
	private int select;
	private int combinations;
	
	public MuxProgram_FitnessFunction(int inputs, int select) {
		this.inputs = inputs;
		this.select = select;
		combinations = (int) Math.pow(2, inputs + select);
	}
	
	public MuxProgram_FitnessFunction() {
	}
	
	public void setInputs(int inputs) {
		this.inputs = inputs;
	}
	
	public void setSelect(int select) {
		this.select = select;
	}
	
	@Override
	public Fitness evaluate(Fenotype fenotype) {
		Fenotype_TreeAsProgram f = (Fenotype_TreeAsProgram) fenotype;
		
		int fitness = 0;
		for (int i = 0; i < combinations; i++) {
			TerminalValues tv = new TerminalValues(i, inputs + select);
			int input = i & (((int)Math.pow(2, select)) - 1);
			boolean r = tv.getValue(select + input);
			try {
				if (f.evaluate(tv) == r) {
					fitness++;
				}
			} catch (Exception e) {
				//Not Happenning
				e.printStackTrace();
			}
		}
		
		Fitness_Double fit = new Fitness_Double(fitness);		
		return fit;
	}

	@Override
	public boolean maximize() {
		return true;
	}
	
	@Override
	public String toString() {
		return "Práctica 3 - MUX";
	}

}
