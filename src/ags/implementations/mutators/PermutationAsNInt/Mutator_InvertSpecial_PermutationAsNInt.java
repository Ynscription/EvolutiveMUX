package ags.implementations.mutators.PermutationAsNInt;

import java.util.ArrayList;
import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.Genotype;

public class Mutator_InvertSpecial_PermutationAsNInt extends Mutator_Inversion_PermutationAsNInt {

	private boolean maximize;
	private int totalInversions;
	
	public Mutator_InvertSpecial_PermutationAsNInt(double mutateChance, Random random, boolean maximize) {
		super(mutateChance, random);
		this.maximize = maximize;
		totalInversions = 0;
	}
	
	public int getInversions () {
		return totalInversions;
	}
	
	@Override
	public void mutateCollection(ArrayList<Cromosome> collection) {	
		//Mutating affects the cromosomes only at genotype level, so we need to iterate for every
		//cromosome in the collection and tell it to mutate
		for (Cromosome cromosome : collection) {
			//If the cromosome is selected, tell it to mutate.
			if (random.nextDouble() < mutateChance) {
				Genotype inverted = mutate((Genotype) cromosome.getGenotype().clone());
				Cromosome c = cromosome.newCromosome(inverted);
				if (c.compareTo(cromosome) == 1) {
					//c is greater than cromosome
					if (maximize) {
						cromosome.mutate(inverted);
						totalInversions++;
					}
				}else if (c.compareTo(cromosome) == -1) {
					//c is smaller than cromosome
					if (!maximize) {
						cromosome.mutate(inverted);
						totalInversions++;
					}
				}
			}
		}				
	}

}
