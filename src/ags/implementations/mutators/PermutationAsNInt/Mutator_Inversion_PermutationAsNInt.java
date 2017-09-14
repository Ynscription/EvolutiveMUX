package ags.implementations.mutators.PermutationAsNInt;

import java.util.Random;

import ags.cromosome.Genotype;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;

public class Mutator_Inversion_PermutationAsNInt extends Mutator_Generic_PermutationAsNInt{
	
	public Mutator_Inversion_PermutationAsNInt (double mutateChance, Random random) {
		super(mutateChance, random);
	}

	@Override
	public Genotype mutate(Genotype genotype) {
		Genotype_PermutationAsNInt gen = (Genotype_PermutationAsNInt) genotype;
		//Get the needed data.
		int v [] = gen.getValues();	
		int N = gen.getN();
		
		//Get 2 distinct random cut points between 1 and N-1: x|xx...xxx 
		int cut1 = random.nextInt(N-1) + 1;
		int cut2;
		do {
			cut2 = random.nextInt(N-1) + 1;
		}while (cut1 == cut2);
		//Make sure cut1 is lesser than cut2
		if (cut2 < cut1) {
			int aux = cut1;
			cut1 = cut2;
			cut2= aux;
		}
		
		for (int i = cut1, j = cut2 - 1; j-i > 0; i++, j--) {
			int aux = v[i];
			v[i] = v[j];
			v[j] = aux;
		}
		
		
		return new Genotype_PermutationAsNInt(v, N);
	}

}






