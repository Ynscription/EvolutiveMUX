package ags.cromosome.implementations.genotypeTranslators;

import java.util.LinkedList;
import java.util.Random;

import ags.cromosome.Fenotype;
import ags.cromosome.Genotype;
import ags.cromosome.Translator;
import ags.cromosome.implementations.fenotypes.Fenotype_PermutationAsNInt;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;

public class Translator_PermutationAsNInt_Generic implements Translator{

	private LinkedList<Integer> dinamicList;
	private int N;
	
	public Translator_PermutationAsNInt_Generic(int N) {
		dinamicList = new LinkedList<Integer>();
		this.N = N;
		for (int i = 0; i < N; i++) {
			dinamicList.add(i);
		}
	}
	
	@Override
	public Fenotype translateGenotype(Genotype genotype) {
		return new Fenotype_PermutationAsNInt((Genotype_PermutationAsNInt) genotype);
	}	
	

	@SuppressWarnings("unchecked")
	@Override
	public Genotype getRandomGenotype(Random random) {
		LinkedList<Integer> positions = (LinkedList<Integer>) dinamicList.clone();
		int [] values = new int [N];
		for (int i = 0; i < N; i++) {
			int spot = random.nextInt(positions.size());
			values [i] = positions.get(spot);
			positions.remove(spot);
		}
		return new Genotype_PermutationAsNInt(values, N);
	}

}
