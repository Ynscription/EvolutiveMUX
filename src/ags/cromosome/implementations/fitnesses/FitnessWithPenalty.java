package ags.cromosome.implementations.fitnesses;

import ags.cromosome.Fitness;

public class FitnessWithPenalty extends Fitness_Double {

	private Fitness realValue;
	private Fitness penalty;
	
	public FitnessWithPenalty(Fitness_Double v, Fitness realValue, Fitness penalty) {
		super(v.getValue());
		this.realValue = realValue;
		this.penalty = penalty;
	}
	
	public FitnessWithPenalty(double v, Fitness realValue, Fitness penalty) {
		super(v);
		this.realValue = realValue;
		this.penalty = penalty;
	}
	
	@Override
	public String toString() {
		return super.toString() + "(" + realValue + " - " + penalty + ")";
	}
	
	@Override
	public Object clone() {
		return new FitnessWithPenalty(value, realValue, penalty);
	}

}
