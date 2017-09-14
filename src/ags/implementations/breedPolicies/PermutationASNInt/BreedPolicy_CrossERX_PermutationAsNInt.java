package ags.implementations.breedPolicies.PermutationASNInt;

import java.util.LinkedList;
import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossERX_PermutationAsNInt extends BreedPolicy_CrossGeneric{

	public BreedPolicy_CrossERX_PermutationAsNInt(Random random, double crossChance) {
		super(random, crossChance);
	}

	@Override
	public Cromosome[] breed(Cromosome c1, Cromosome c2) {
		//Get the needed data.
		int v1 [] = ((Genotype_PermutationAsNInt)c1.getGenotype()).getValues();
		int v2 [] = ((Genotype_PermutationAsNInt)c2.getGenotype()).getValues();
	
		int N = ((Genotype_PermutationAsNInt)c1.getGenotype()).getN();
				
		LinkedList<Integer> [] routes = getRouteTable(v1, v2, N);
		
		Integer [] r1 = getGen (routes, v1, v2, N);
		Integer [] r2 = getGen (routes, v2, v1, N);
		
		
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
	
	private Integer[] getGen(LinkedList<Integer>[] routes, int[] v1, int[] v2, int N) {
		Integer [] gen = new Integer [N];
		gen [0] = v2 [0];
		
		recGetGen(1, v2[0], gen, routes, v1, v2, N);
		
		return gen;
	}
	
	private boolean recGetGen (int i, int pos, Integer [] gen, LinkedList<Integer>[] routes, int[] v1, int[] v2, int N){
		if (i == N) {
			return true;
		}
		LinkedList<Integer> connections = new LinkedList<Integer>();
		//Fill connections with the possible values, not present and the ones with the lowest amount of connections
		for (Integer k : routes [pos]) {
			if (!isPresent(k, gen, N)) {
				connections.add(k);
			}
		}
		
		boolean valid = false;
		while (!valid && connections.size() > 0){
			int p = random.nextInt(connections.size());
			gen [i] = connections.get(p);
			valid = recGetGen(i + 1, connections.get(p), gen, routes, v1, v2, N);
			if (!valid) {
				connections.remove(p);
			}
		}
		
		if (!valid) {
			gen [i] = null;
		}
		
		return valid;		
	}

	@SuppressWarnings("unchecked")
	private LinkedList<Integer> [] getRouteTable (int [] v1, int [] v2, int N) {
		LinkedList<Integer> [] res = (LinkedList<Integer>[]) new LinkedList<?>[N];
		for (int i = 0; i < N; i++) {
			res [i] = new LinkedList<Integer>();
		}
		
		for (int i = 0; i < N; i++) {
			//Since Java is stupid and thinks -1 mod N = -1...
			int left = ((i-1)%N + N)%N;
			if (!res[v1[i]].contains(v1[left])) {
				res[v1[i]].add(v1[left]);
			}
			if (!res[v1[i]].contains(v1[(i+1)%N])) {
				res[v1[i]].add(v1[(i+1)%N]);
			}
			if (!res[v2[i]].contains(v2[left])) {
				res[v2[i]].add(v2[left]);
			}
			if (!res[v2[i]].contains(v2[(i+1)%N])) {
				res[v2[i]].add(v2[(i+1)%N]);
			}
		}
		
		
		return res;
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
