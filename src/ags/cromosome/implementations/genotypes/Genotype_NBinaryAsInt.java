package ags.cromosome.implementations.genotypes;

import ags.cromosome.Genotype;

//The genotype is represented by N strings of bits as integers.
public class Genotype_NBinaryAsInt implements Genotype {
	
	private int values [];
	private int lengths [];
	private int N;
	
	public Genotype_NBinaryAsInt(int [] values, int lengths[],int N) {
		this.values = values;
		this.lengths = lengths;
		this.N = N;
	}
	
	public int[] getValues() {
		return values;
	}
	
	public int getN() {
		return N;
	}
	
	public int[] getLengths() {
		return lengths;
	}

	@Override
	public String toString() {
		String r = "";
		for (int i = 0; i < N; i++) {
			r += ("GV" + (i+1) + ": " + Integer.toBinaryString(values[i]) + "	");
		}
		return r;
	}

	@Override
	public Object clone() {
		int copyV [] = new int [N];
		int copyL [] = new int [N];
		for (int i = 0; i < N; i++) {
			copyV [i] = values [i];
			copyL [i] = lengths [i];
		}
		
		return new Genotype_NBinaryAsInt(copyV, copyL,N);
	}
	
	@Override
	public boolean equals(Object obj) {
		Genotype_NBinaryAsInt go = (Genotype_NBinaryAsInt) obj;
		boolean r = true;
		
		r = N == go.N;
		
		for (int i = 0; i < N && r; i++) {
			r = lengths [N] == go.lengths [N];
			
			if (r) {
				r = values [N] == values [N];
			}
		}
		
		return r;
	}

	@Override
	public int getSize() {
		return N;
	}

}
