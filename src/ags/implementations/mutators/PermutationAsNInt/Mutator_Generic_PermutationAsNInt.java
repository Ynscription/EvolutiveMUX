package ags.implementations.mutators.PermutationAsNInt;

import java.util.ArrayList;
import java.util.Random;

import ags.Mutator;
import ags.cromosome.Cromosome;
import ags.cromosome.Genotype;

public abstract class Mutator_Generic_PermutationAsNInt implements Mutator{

	protected double mutateChance; //The chance of mutating a cromosome
	protected Random random;
	protected int totalMutations;
	
	public Mutator_Generic_PermutationAsNInt (double mutateChance, Random random) {
		this.random = random;
		this.mutateChance = mutateChance;
		totalMutations = 0;
	}
	
	public int getMutations () {
		return totalMutations;
	}
	
	@Override
	public void mutateCollection(ArrayList<Cromosome> collection) {	
		//Mutating affects the cromosomes only at genotype level, so we need to iterate for every
		//cromosome in the collection and tell it to mutate
		for (Cromosome cromosome : collection) {
			//If the cromosome is selected, tell it to mutate.
			if (random.nextDouble() < mutateChance) {
				cromosome.mutate();
				totalMutations++;
			}
		}				
	}

	@Override
	public abstract Genotype mutate(Genotype genotype);
}
