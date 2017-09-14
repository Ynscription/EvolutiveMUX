package ags.cromosome.implementations.genotypes;

import ags.cromosome.Genotype;

public class Genotype_PermutationAsNInt implements Genotype{
	private int values [];
	private int N;
	
	public Genotype_PermutationAsNInt(int [] values, int N) {
		this.values = values;
		this.N = N;
	}
	
	public int getValue(int pos) {
		return values[pos];
	}
	
	public int[] getValues() {
		return values;
	}
	
	public int getN() {
		return N;
	}
	
	
	@Override
	public String toString() {
		String r = "[";
		for (int i = 0; i < N; i++) {
			r += (values[i]);
			if (i != N-1) {
				r += ",";
			}
		}
		r += "]";
		return r;
	}
	
	@Override
	public Object clone() {
		int copyV [] = new int [N];
		for (int i = 0; i < N; i++) {
			copyV [i] = values [i];
		}
		return new Genotype_PermutationAsNInt(copyV, N);
	}
	
	@Override
	public boolean equals(Object obj) {
		Genotype_PermutationAsNInt go = (Genotype_PermutationAsNInt) obj;
		boolean r = true;
		
		r = N == go.N;
		
		for (int i = 0; i < N && r; i++) {
			r = values [i] == go.values [i];
		}
		
		return r;		
	}

	@Override
	public int getSize() {
		return N;
	}
	
}
