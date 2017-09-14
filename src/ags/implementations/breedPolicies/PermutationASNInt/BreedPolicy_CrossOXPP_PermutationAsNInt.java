package ags.implementations.breedPolicies.PermutationASNInt;

import java.util.LinkedList;
import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossOXPP_PermutationAsNInt extends BreedPolicy_CrossGeneric{

	private int points;
	
	public BreedPolicy_CrossOXPP_PermutationAsNInt(Random random, double crossChance, int points) {
		super(random, crossChance);
		this.points = points;
	}

	@Override
	public Cromosome[] breed(Cromosome c1, Cromosome c2) {
		//Get the needed data.
		int v1 [] = ((Genotype_PermutationAsNInt)c1.getGenotype()).getValues();
		int v2 [] = ((Genotype_PermutationAsNInt)c2.getGenotype()).getValues();
	
		int N = ((Genotype_PermutationAsNInt)c1.getGenotype()).getN();
		
		//Get <positions> distinct random points between 0 and N-1
		LinkedList<Integer> select = new LinkedList<Integer> ();	
		for (int i = 0; i < points; i++) {
			int aux;
			do {
				aux = random.nextInt(N);
			}while (select.contains(aux));
			select.add(aux);
		}
		
		//The new genotypes
		Integer [] r1 = new Integer [N];
		Integer [] r2 = new Integer [N];
		
		//We swap the values of the parents in the selected positions.
		for (Integer i : select) {
			r1[i] = v2 [i];
			r2[i] = v1 [i];
		}
				
		/* For the rest of the values, we place the original parent's value,
		 * unless its in conflict. 
		 * If so, we try to place the next value.
		*/
		for (int i = 0, j1 = 0, j2 = 0; i < N; i++) {
			//Skip the swapped positions
			if (!select.contains(i)) {
				while (isPresent(v1[j1], r1, N)) {
					j1++;
				}
				r1 [i] = v1[j1];
				
				while (isPresent(v2[j2], r2, N)) {
					j2++;
				}
				r2 [i] = v2[j2];
				
				j1++;
				j2++;
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
