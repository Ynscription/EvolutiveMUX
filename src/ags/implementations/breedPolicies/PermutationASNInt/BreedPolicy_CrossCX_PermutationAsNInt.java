package ags.implementations.breedPolicies.PermutationASNInt;

import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossCX_PermutationAsNInt extends BreedPolicy_CrossGeneric{

	public BreedPolicy_CrossCX_PermutationAsNInt(Random random, double crossChance) {
		super(random, crossChance);
	}

	@Override
	public Cromosome[] breed(Cromosome c1, Cromosome c2) {
		//Get the needed data.
		int v1 [] = ((Genotype_PermutationAsNInt)c1.getGenotype()).getValues();
		int v2 [] = ((Genotype_PermutationAsNInt)c2.getGenotype()).getValues();
	
		int N = ((Genotype_PermutationAsNInt)c1.getGenotype()).getN();
		
		Integer [] r1 = new Integer [N];
		Integer [] r2 = new Integer [N];
		for (int i = 0; i < N; i++) {
			r1 [i] = null;
			r2 [i] = null;
		}
		
		//In both genotypes, we do the cicle process, filling some spots with the original parents' values 
		boolean cicle1 = false, cicle2 = false;
		int pos1 = 0, pos2 = 0;
		do {
			if (!cicle1) {
				r1 [pos1] = v1 [pos1];
			}
			if (!cicle2) {
				r2 [pos2] = v2 [pos2];
			}
			
			boolean p1 = cicle1, p2 = cicle2;
			int lookup1 = v2 [pos1], lookup2 = v1 [pos2];
			for (int i = 0; !(p1 && p2); i++) {
				if (!p1 && lookup1 == v1 [i]) {
					pos1 = i;
					p1 = true;
				}
				if (!p2 && lookup2 == v2 [i]) {
					pos2 = i;
					p2 = true;
				}
			}
			cicle1 = pos1 == 0;
			cicle2 = pos2 == 0;
		}while (!(cicle1 || cicle2));
		
		//Fill the rest of the values with the other parents'
		for (int i = 0; i < N; i++) {
			if (r1[i] == null) {
				r1 [i] = v2 [i];
			}
			if (r2[i] == null) {
				r2 [i] = v1 [i];
			}
		}
		
		//The new genotypes should be finished now
		int [] res1 = new int [N];
		int [] res2 = new int [N];
		
		for (int i = 0; i < N; i++) {
			res1 [i] = r1 [i];
			res2 [i] = r2 [i];
		}
		
		Genotype_PermutationAsNInt g1 = new Genotype_PermutationAsNInt (res1, N);
		Genotype_PermutationAsNInt g2 = new Genotype_PermutationAsNInt (res2, N);
		
		//The new cromosomes
		Cromosome R [] = new Cromosome [2];
		R[0] = c1.newCromosome(g1);
		R[1] = c2.newCromosome(g2);
		return R;
	}

	

}
