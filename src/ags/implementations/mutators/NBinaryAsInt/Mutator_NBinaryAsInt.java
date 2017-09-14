package ags.implementations.mutators.NBinaryAsInt;

import java.util.ArrayList;
import java.util.Random;

import ags.Mutator;
import ags.cromosome.Cromosome;
import ags.cromosome.Genotype;
import ags.cromosome.implementations.genotypes.Genotype_NBinaryAsInt;

public class Mutator_NBinaryAsInt implements Mutator{
	private double mutateRate; //The chance of mutating a bit
	private double mutateChance; //The chance of mutating a cromosome
	private Random random;
	private int totalMutations;
	
	public Mutator_NBinaryAsInt(double mutateRate, double mutateChance, Random random) {
		this.mutateRate = mutateRate;
		this.random = random;
		this.mutateChance = mutateChance;
		totalMutations = 0;
	}
	
	@Override
	public void mutateCollection(ArrayList<Cromosome> collection) {	
		//Mutating affects the cromosomes only at genotype level, so we need to iterate for every
		//cromosome in the collection and tell it to mutate using this mutator.
		for (Cromosome cromosome : collection) {
			//If the cromosome is selected, tell it to mutate.
			if (random.nextDouble() < mutateChance) {
				cromosome.mutate();
				totalMutations++;
			}
		}				
	}
	

	@Override
	public Genotype mutate(Genotype genotype) throws ClassCastException{
		//Cast the genotype to the specific one this mutator is designed for
		Genotype_NBinaryAsInt g = (Genotype_NBinaryAsInt) genotype;
		//Get data
		int v[] = g.getValues();
		int lengths []= g.getLengths();
		int N = g.getN();
		//For each value in the genotype
		for (int i = 0; i < N; i++) {
			//For each bit in the value
			for (int j = 0; j < lengths [i]; j++) {
				//If the bit is selected to mutate 
				if (mutateRate < random.nextDouble()) {
					//we create a mask of the type 0001000
					int k = 1 << j;
					//We assign the bit a random value 0 or 1; 
					if (random.nextBoolean()) {
						//Make the bit a 1
						v[i] = v[i] | k;
					}else {
						//Make the bit a 0
						v[i] = v[i] & ~k;
					}
					
				}
			}
		}
		//Returned a new genotype with the mutated values.
		return new Genotype_NBinaryAsInt(v, lengths, N);
	}

	@Override
	public int getMutations() {
		return totalMutations;
	}


	

}
