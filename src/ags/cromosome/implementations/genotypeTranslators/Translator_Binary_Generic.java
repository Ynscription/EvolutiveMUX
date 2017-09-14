package ags.cromosome.implementations.genotypeTranslators;


import java.util.Random;

import ags.cromosome.Genotype;
import ags.cromosome.Translator;
import ags.cromosome.implementations.genotypes.Genotype_NBinaryAsInt;

public abstract class Translator_Binary_Generic implements Translator{
	protected int lengths [];

	protected int N;
	
	public Translator_Binary_Generic(int N) {
		this.N = N;	
		lengths = new int [N];
	}
	
	
	public int[] getLengths() {
		return lengths;
	}
	
	public int getN() {
		return N;
	}
	
	@Override
	public Genotype getRandomGenotype(Random random) {
		int values [] = new int [N];
		for (int i = 0; i < N; i++) {
			values [i] = random.nextInt(1 << lengths[i]);
		}
		return new Genotype_NBinaryAsInt(values, lengths,N);
	}
}
