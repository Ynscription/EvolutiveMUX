package ags;


import java.util.ArrayList;

import ags.cromosome.Cromosome;

public interface BreedPolicy {

	public ArrayList<Cromosome> breedSelection(ArrayList<Cromosome> select);
	

	public Cromosome[] breed(Cromosome c1, Cromosome c2);
	
	public int getCrossovers ();
}
