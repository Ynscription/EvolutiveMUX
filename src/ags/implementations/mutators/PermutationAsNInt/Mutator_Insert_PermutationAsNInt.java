package ags.implementations.mutators.PermutationAsNInt;

import java.util.Random;

import ags.cromosome.Genotype;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;

public class Mutator_Insert_PermutationAsNInt extends Mutator_Generic_PermutationAsNInt{

	private int points;
	
	public Mutator_Insert_PermutationAsNInt(double mutateChance, Random random, int points) {
		super(mutateChance, random);
		this.points = points;
	}

	@Override
	public Genotype mutate(Genotype genotype) {
		Genotype_PermutationAsNInt gen = (Genotype_PermutationAsNInt) genotype;
		//Get the needed data.
		int v [] = gen.getValues();	
		int N = gen.getN();
		
		for (int p = 0; p < points; p++) {
			//Get 2 distinct random points
			int dest = random.nextInt(N);
			int orig;
			do {
				orig = random.nextInt(N);
			}while (orig == dest);
			
			if (dest < orig) {
				int value = v[orig];
				for (int i = orig; i > dest; i--) {
					v[i] = v[i-1];
				}
				v[dest] = value;
				
			}else {
				int value = v[orig];
				for (int i = orig; i < dest; i++) {
					v[i] = v[i+1];
				}
				v[dest] = value;
			}			
		}
		
		
		return new Genotype_PermutationAsNInt(v, N);
	}

}
