package ags.implementations.breedPolicies.NBinaryASInt;

import java.util.Random;

import ags.cromosome.Cromosome;

import ags.cromosome.implementations.genotypes.Genotype_NBinaryAsInt;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossSinglePoint_NBinaryAsInt extends BreedPolicy_CrossGeneric{
	
	
	public BreedPolicy_CrossSinglePoint_NBinaryAsInt(Random random, double crossChance) {
		super(random, crossChance);
		
	}
	
	@Override
	public Cromosome[] breed(Cromosome cromosome1, Cromosome cromosome2) {
		//Get needed data
		int v1 [] = ((Genotype_NBinaryAsInt)cromosome1.getGenotype()).getValues();
		int v2 [] = ((Genotype_NBinaryAsInt)cromosome2.getGenotype()).getValues();

		int lengths [] = ((Genotype_NBinaryAsInt)cromosome2.getGenotype()).getLengths();
		int N = ((Genotype_NBinaryAsInt)cromosome2.getGenotype()).getN();
		
		//Select one of the values
		int pos = random.nextInt(N);
		
		//Select a point in that value to swap
		int point = 1 + random.nextInt(lengths[pos] - 2);

		//Mask for the swap, will be of the form 0000111
		int mask = (int) (Math.pow(2, point) - 1);
		
		//Swap the bits of values according to mask
		int value1 = (v1[pos] & mask) | (v2[pos] & ~mask);
		int value2 = (v1[pos] & ~mask) | (v2[pos] & mask);
		
		v1 [pos] = value1;
		v2 [pos] = value2;
		
		//Swap the rest of the values
		for (int i = pos + 1; i < N; i++) {
			int aux = v1 [i];
			v1 [i] = v2 [i];
			v2 [i] = aux;
		}			
		//The new genotypes for the new cromosomes to be returned
		Genotype_NBinaryAsInt g1 = new Genotype_NBinaryAsInt(v1, lengths, N);
		Genotype_NBinaryAsInt g2 = new Genotype_NBinaryAsInt(v2, lengths, N);
		
		//The new cromosomes
		Cromosome R [] = new Cromosome [2];
		R[0] = cromosome1.newCromosome(g1);
		R[1] = cromosome2.newCromosome(g2);
		return R;
	}

}
