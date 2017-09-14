package ags.implementations.breedPolicies.NBinaryASInt;

import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.genotypes.Genotype_NBinaryAsInt;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossMultiPoint_NBinaryAsInt extends BreedPolicy_CrossGeneric{
	
	private int points; //The amount of points for the cross
	
	public BreedPolicy_CrossMultiPoint_NBinaryAsInt(Random random, double crossChance, int points) {
		super(random, crossChance);
		this.points = points;
		
	}
	
	@Override
	public Cromosome[] breed(Cromosome cromosome1, Cromosome cromosome2) {
		//Get the needed data.
		int v1 [] = ((Genotype_NBinaryAsInt)cromosome1.getGenotype()).getValues();
		int v2 [] = ((Genotype_NBinaryAsInt)cromosome2.getGenotype()).getValues();

		int lengths [] = ((Genotype_NBinaryAsInt)cromosome2.getGenotype()).getLengths();
		int N = ((Genotype_NBinaryAsInt)cromosome2.getGenotype()).getN();
		
		//Repeat this for the amount of points for the cross
		for (int p = 0; p < points; p++) {
			//Select a value of the genotype to cross
			int pos = random.nextInt(N);
			
			//Select a point to cross the value
			int point = 1 + random.nextInt(lengths[pos] - 2);
	
			//Generate the mask for crossing, wich will be something like 0001111
			int mask = (int) (Math.pow(2, point) - 1);
			
			//swap the bits of the 2 values, according to mask
			int value1 = (v1[pos] & mask) | (v2[pos] & ~mask);
			int value2 = (v1[pos] & ~mask) | (v2[pos] & mask);
			
			v1 [pos] = value1;
			v2 [pos] = value2;
			
			//Swap all following values
			//TODO this is extremely inefficient, it can be done better
			for (int i = pos + 1; i < N; i++) {
				int aux = v1 [i];
				v1 [i] = v2 [i];
				v2 [i] = aux;
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