package ags.implementations.mutators.PermutationAsNInt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.Genotype;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;

public class Mutator_Heuristic_PermutationAsNInt extends Mutator_Generic_PermutationAsNInt {

	private int n;
	private Cromosome bestPermutation;
	private boolean maximize;
	
	public Mutator_Heuristic_PermutationAsNInt(double mutateChance, Random random, int n, boolean maximize) {
		super(mutateChance, random);
		this.n = n;
		this.maximize = maximize;
	}
	
	@Override
	public void mutateCollection(ArrayList<Cromosome> collection) {	
		//Mutating affects the cromosomes only at genotype level, so we need to iterate for every
		//cromosome in the collection and tell it to mutate
		for (Cromosome cromosome : collection) {
			//If the cromosome is selected, tell it to mutate.
			if (random.nextDouble() < mutateChance) {
				bestPermutation = (Cromosome) cromosome.clone();
				cromosome.mutate();
				totalMutations++;
			}
		}				
	}

	@Override
	public Genotype mutate(Genotype genotype) {
		bestPermutation = bestPermutation.newCromosome(genotype);
		Genotype_PermutationAsNInt gen = (Genotype_PermutationAsNInt) genotype;
		//Get the needed data.
		int v [] = gen.getValues();	
		int N = gen.getN();

		//Get n distinct random points between 0 and N-1
		LinkedList<Integer> select = new LinkedList<Integer> ();	
		for (int i = 0; i < n; i++) {
			int aux;
			do {
				aux = random.nextInt(N);
			}while (select.contains(aux));
			select.add(aux);
		}
		
		//Get the values of said points
		LinkedList<Integer> values = new LinkedList<Integer> ();
		Iterator<Integer> selIt = select.iterator();
		while (selIt.hasNext()) {
			values.add(v[selIt.next()]);
		}
		Genotype a = getBestPermutation (select, values, v, N, 0); 
		return a;
	}

	private Genotype getBestPermutation(LinkedList<Integer> select, LinkedList<Integer> values, int[] v, int N, int k) {
		for(int i = k; i < values.size(); i++){
			java.util.Collections.swap(values, i, k);
			getBestPermutation(select, values, v, N, k+1);
			java.util.Collections.swap(values, k, i);
		}
		if (k == values.size() -1){
			int [] auxv = new int [N];
			for (int i = 0; i < N; i++) {
				auxv [i] = v [i];
			}
			
			Iterator<Integer> selIt = select.iterator();
			Iterator<Integer> valIt = values.iterator();
			while (selIt.hasNext()) {
				auxv [selIt.next()] = valIt.next();
			}
			
			Cromosome c = bestPermutation.newCromosome(new Genotype_PermutationAsNInt(auxv, N));
			if (c.compareTo(bestPermutation) == 1) {
				//c is greater than bp
				if (maximize) {
					bestPermutation = c;
				}
			}else if (c.compareTo(bestPermutation) == -1) {
				//c is smaller than bp
				if (!maximize) {
					bestPermutation = c;
				}
			}
		}
		return bestPermutation.getGenotype();
	}

}
