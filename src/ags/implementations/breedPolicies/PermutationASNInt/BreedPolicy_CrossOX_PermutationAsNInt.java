package ags.implementations.breedPolicies.PermutationASNInt;

import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossOX_PermutationAsNInt extends BreedPolicy_CrossGeneric{

	public BreedPolicy_CrossOX_PermutationAsNInt(Random random, double crossChance) {
		super(random, crossChance);
	}

	@Override
	public Cromosome[] breed(Cromosome c1, Cromosome c2) {
		//Get the needed data.
		int v1 [] = ((Genotype_PermutationAsNInt)c1.getGenotype()).getValues();
		int v2 [] = ((Genotype_PermutationAsNInt)c2.getGenotype()).getValues();
	
		int N = ((Genotype_PermutationAsNInt)c1.getGenotype()).getN();
		
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
		
		//The new genotypes
		Integer [] r1 = new Integer [N];
		Integer [] r2 = new Integer [N];
		
		//We place the values of the parents within the cut, in the children.
		for (int i = cut1; i < cut2; i++) {
			r1[i] = v2[i];
			r2[i] = v1[i];					
		}
		
		/* For the rest of the values, we place the original parent's value,
		 * unless its in conflict. 
		 * If so, we try to place the next value.
		*/
		for (int i = cut2, j1 = cut2, j2 = cut2; i%N != cut1; i++) {
			while (isPresent(v1[j1%N], r1, N)) {
				j1++;
			}
			r1 [i%N] = v1[j1%N];
			
			while (isPresent(v2[j2%N], r2, N)) {
				j2++;
			}
			r2 [i%N] = v2[j2%N];
			
			j1++;
			j2++;
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
			
	private boolean isPresent (int k, Integer []v, int N) {
		boolean r = false;
		
		for (int i = 0; i < N && !r; i++) {
			if (v[i] != null) {
				r = v[i] == k;
			}
		}
		
		return r;
	}

	
}
