package ags.cromosome;

public interface Fitness extends Comparable<Fitness>{
	
	public Fitness add (Fitness op);
	
	public Fitness divide (int pop);
	
	public Fitness zero ();
	
	public Fitness substract (Fitness op);

	@Override
	public boolean equals (Object o);
	
	
	@Override
	public String toString ();

	public Object clone();
	
	
}