package ags;

import java.util.ArrayList;

import ags.cromosome.Cromosome;
import ags.cromosome.Genotype;

public interface Mutator {
	
	public void mutateCollection (ArrayList<Cromosome> collection);

	public Genotype mutate(Genotype genotype);

	public int getMutations ();
}
