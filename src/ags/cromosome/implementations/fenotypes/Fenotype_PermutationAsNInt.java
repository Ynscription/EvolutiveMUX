package ags.cromosome.implementations.fenotypes;

import ags.cromosome.Fenotype;
import ags.cromosome.implementations.genotypes.Genotype_PermutationAsNInt;

public class Fenotype_PermutationAsNInt implements Fenotype{
	
	private Genotype_PermutationAsNInt genotype;
	
	public Fenotype_PermutationAsNInt(Genotype_PermutationAsNInt genotype) {
		this.genotype = genotype;
	}
	
	
	public int getValue(int pos) {
		return genotype.getValue(pos);
	}
	public int[] getValues() {
		return genotype.getValues();
	}
	
	public int getN() {
		return genotype.getN();
	}
	
	
	
	@Override
	public boolean equals(Object o) throws ClassCastException{
		Fenotype_NDouble fo = (Fenotype_NDouble) o;
		boolean eq = true;
		for (int i = 0; (i < getN()) && eq; i++) {
			eq = getValue(i) == fo.getValue(i);
		}
		return eq;
	}
	
	@Override
	public String toString () {
		String r = "[";
		for (int i = 0; i < getN(); i++) {
			r += (getValues()[i]);
			if (i != getN()-1) {
				r += ",";
			}
		}
		r += "]";
		return r;
	}

}
