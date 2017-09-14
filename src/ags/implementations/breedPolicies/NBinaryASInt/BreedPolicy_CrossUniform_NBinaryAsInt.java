package ags.implementations.breedPolicies.NBinaryASInt;

import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.genotypes.Genotype_NBinaryAsInt;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossUniform_NBinaryAsInt extends BreedPolicy_CrossGeneric{
	
	double crossRate; //The rate at wich the bits will be swapped
	
	public BreedPolicy_CrossUniform_NBinaryAsInt(Random random, double crossChance,double crossRate) {
		super(random, crossChance);
		this.crossRate = crossRate;
	}
	
	@Override
	public Cromosome[] breed(Cromosome cromosome1, Cromosome cromosome2) {
		//Get data
		int v1 [] = ((Genotype_NBinaryAsInt)cromosome1.getGenotype()).getValues();
		int v2 [] = ((Genotype_NBinaryAsInt)cromosome2.getGenotype()).getValues();

		int lengths [] = ((Genotype_NBinaryAsInt)cromosome2.getGenotype()).getLengths();
		int N = ((Genotype_NBinaryAsInt)cromosome2.getGenotype()).getN();
		
		//For every value in the genotypes
		for (int pos = 0; pos < N; pos++){
			
			//For every bit in the values
			for (int point = 0; point < lengths[pos]; point++) {
				//If this bit is selected
				if (crossRate < random.nextDouble()) {
					//Create a mask of the type 001000
					int mask = 1 << point;
					
					//Swap the bits according to the mask
					int value1 = (v1[pos] & mask) | (v2[pos] & ~mask);
					int value2 = (v1[pos] & ~mask) | (v2[pos] & mask);
					
					v1 [pos] = value1;
					v2 [pos] = value2;
					
					
				}
			}
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
