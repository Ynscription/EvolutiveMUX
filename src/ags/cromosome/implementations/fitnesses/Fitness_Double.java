package ags.cromosome.implementations.fitnesses;

import ags.cromosome.Fitness;

//The Fitness as a real value
public class Fitness_Double implements Fitness{
	protected double value;
	
	public Fitness_Double(double v) {
		value = v;
	}
	
	public double getValue() {
		return value;
	}
	
	@Override
	public Fitness add(Fitness op) {
		return new Fitness_Double(value + ((Fitness_Double) op).getValue());
	}
	
	@Override
	public Fitness substract(Fitness op) {
		return new Fitness_Double(value - ((Fitness_Double) op).getValue());
	}

	@Override
	public Fitness zero() {
		return new Fitness_Double(0);
	}
	
	@Override
	public Fitness divide(int pop) {
		return new Fitness_Double(value/pop);
	}
	
	//Compares the value of this fitness to the value of another fitness.
	@Override
	public int compareTo(Fitness arg0) {
		Fitness_Double fo = (Fitness_Double) arg0;
		//This is a hack for the TreeSet, since it won't allow 2 equal values.
		//Still we can use the function to know whether they are equal or not.
		if (value == fo.value) {
			return 2;
		}else if (value > fo.value) {
			return 1;
		}else {
			return -1;
		}
	}

	@Override
	public boolean equals(Object o) {
		Fitness_Double fo = (Fitness_Double) o;
		return compareTo(fo) == 2;
	}

	@Override
	public String toString() {
		return "" + value;
	}
	
	@Override
	public Object clone() {
		return new Fitness_Double(value);
	}

	
}
