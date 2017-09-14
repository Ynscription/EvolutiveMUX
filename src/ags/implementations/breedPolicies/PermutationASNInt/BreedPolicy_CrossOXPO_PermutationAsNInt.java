package ags.implementations.breedPolicies.PermutationASNInt;

import java.util.LinkedList;
import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossOXPO_PermutationAsNInt extends BreedPolicy_CrossGeneric {
	
	private int positions;

	public BreedPolicy_CrossOXPO_PermutationAsNInt(Random random, double crossChance, int positions) {
		super(random, crossChance);
		this.positions = positions;
	}

	@Override
	public Cromosome[] breed(Cromosome c1, Cromosome c2) {
		//Get the needed data.
		int v1 [] = ((Genotype_PermutationAsNInt)c1.getGenotype()).getValues();
		int v2 [] = ((Genotype_PermutationAsNInt)c2.getGenotype()).getValues();
	
		int N = ((Genotype_PermutationAsNInt)c1.getGenotype()).getN();
		
		//Get <positions> distinct random points between 0 and N-1
		LinkedList<Integer> select1 = new LinkedList<Integer> ();
		LinkedList<Integer> select2 = new LinkedList<Integer> ();
				
		for (int i = 0; i < positions; i++) {
			int aux;
			do {
				aux = random.nextInt(N);
			}while (select1.contains(aux));
			select1.add(aux);
			do {
				aux = random.nextInt(N);
			}while (select2.contains(aux));
			select2.add(aux);
		}
		
		//Order the positions in ascending order
		select1.sort(Integer::compare);
		select2.sort(Integer::compare);
		
		//Save the values of the ordered positions
		LinkedList<Integer> relOrder1 = new LinkedList<Integer>();
		LinkedList<Integer> relOrder2 = new LinkedList<Integer>();
		for (int i = 0; i < positions; i++) {
			relOrder1.add(v1[select1.pollFirst()]);
			relOrder2.add(v2[select2.pollFirst()]);
		}
		
		//The new genotypes
		int [] r1 = new int [N];
		int [] r2 = new int [N];
		
		//We place the values of the parents in the children.
		for (int i = 0; i < N; i++) {
			r1[i] = v2[i];
			r2[i] = v1[i];					
		}
		
		/* We substitute the values of the selected positions
		*/
		for (int i = 0; i < N; i++) {
			if (relOrder1.contains(r2[i])) {
				r2[i] = relOrder1.pollFirst();
			}
			if (relOrder2.contains(r1[i])) {
				r1[i] = relOrder2.pollFirst();
			}
		}
		
		//The new genotypes should be finished now
		Genotype_PermutationAsNInt g1 = new Genotype_PermutationAsNInt (r1, N);
		Genotype_PermutationAsNInt g2 = new Genotype_PermutationAsNInt (r2, N);
		
		//The new cromosomes
		Cromosome R [] = new Cromosome [2];
		R[0] = c1.newCromosome(g1);
		R[1] = c2.newCromosome(g2);
		return R;
	}
	
}
