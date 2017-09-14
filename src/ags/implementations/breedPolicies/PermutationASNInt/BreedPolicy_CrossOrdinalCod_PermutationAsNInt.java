package ags.implementations.breedPolicies.PermutationASNInt;

import java.util.LinkedList;
import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossOrdinalCod_PermutationAsNInt extends BreedPolicy_CrossGeneric{

	private LinkedList<Integer> dinamicList;
	
	public BreedPolicy_CrossOrdinalCod_PermutationAsNInt(Random random, double crossChance) {
		super(random, crossChance);
		dinamicList = null;
	}

	@Override
	public Cromosome[] breed(Cromosome c1, Cromosome c2) {
		//Get the needed data.
		int v1 [] = ((Genotype_PermutationAsNInt)c1.getGenotype()).getValues();
		int v2 [] = ((Genotype_PermutationAsNInt)c2.getGenotype()).getValues();
	
		int N = ((Genotype_PermutationAsNInt)c1.getGenotype()).getN();
		
		if (dinamicList == null) {
			dinamicList = new LinkedList<Integer>();
			for (int i = 0; i < N; i++) {
				dinamicList.add(v1[i]);
			}
			dinamicList.sort(Integer::compare);
		}
		
		int val1 [] = encodeGen(v1, N);
		int val2 [] = encodeGen(v2, N);
		
		
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
		
		//Swap the values within the cuts
		int aux; 
		for (int i = 0; i < N; i++) {
			if (i >= cut1 || i < cut2) {
				aux = val1 [i];
				val1[i] = val2[i];
				val2[i] = aux;
			}
		}		
		
		//The new genotypes
		int [] res1 = decodeGen(val1, N);
		int [] res2 = decodeGen(val2, N);
		
		Genotype_PermutationAsNInt g1 = new Genotype_PermutationAsNInt (res1, N);
		Genotype_PermutationAsNInt g2 = new Genotype_PermutationAsNInt (res2, N);
		
		//The new cromosomes
		Cromosome R [] = new Cromosome [2];
		R[0] = c1.newCromosome(g1);
		R[1] = c2.newCromosome(g2);
		return R;
	}
	
	@SuppressWarnings("unchecked")
	private int [] encodeGen (int [] v, int N) {
		LinkedList<Integer> aux = (LinkedList<Integer>) dinamicList.clone();
		int [] res = new int [N];
		
		for (int i = 0; i < N; i++) {
			res[i] = aux.indexOf(v[i]);
			aux.remove(aux.indexOf(v[i]));
		}
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	private int [] decodeGen (int [] v, int N) {
		LinkedList<Integer> aux = (LinkedList<Integer>) dinamicList.clone();
		int [] res = new int [N];
		
		for (int i = 0; i < N; i++) {
			res[i] = aux.get(v[i]);
			aux.remove(v[i]);
		}
		
		return res;
	}

	

}
