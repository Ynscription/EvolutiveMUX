package ags.implementations.mutators.PermutationAsNInt;

import java.util.Random;

import ags.cromosome.Genotype;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;

public class Mutator_Swap_PermutationAsNInt extends Mutator_Generic_PermutationAsNInt {

	private int pairs;
	
	public Mutator_Swap_PermutationAsNInt(double mutateChance, Random random, int pairs) {
		super(mutateChance, random);
		this.pairs = pairs;
	}

	@Override
	public Genotype mutate(Genotype genotype) {
		Genotype_PermutationAsNInt gen = (Genotype_PermutationAsNInt) genotype;
		//Get the needed data.
		int v [] = gen.getValues();	
		int N = gen.getN();
		
		for (int p = 0; p < pairs; p++) {
			//Get 2 distinct random points
			int p1 = random.nextInt(N);
			int p2;
			do {
				p2 = random.nextInt(N);
			}while (p2 == p1);
			
			
			int aux = v[p1];
			v[p1] = v[p2];
			v[p2] = aux;
			
		}		
		
		return new Genotype_PermutationAsNInt(v, N);
	}
}
