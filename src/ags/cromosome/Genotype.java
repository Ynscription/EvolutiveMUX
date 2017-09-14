package ags.cromosome;


public interface Genotype {
	
	@Override
	public String toString ();
	
	@Override
	public boolean equals(Object obj);
	
	public Object clone();

	public int getSize();

}
