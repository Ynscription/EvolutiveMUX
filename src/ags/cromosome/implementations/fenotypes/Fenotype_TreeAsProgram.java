package ags.cromosome.implementations.fenotypes;

import ags.cromosome.Fenotype;
import ags.cromosome.implementations.genotypes.Genotype_TreeAsProgram;
import ags.cromosome.implementations.genotypes.treeAsProgram.TerminalValues;

public class Fenotype_TreeAsProgram implements Fenotype {
	
	private Genotype_TreeAsProgram genotype;
	
	public Fenotype_TreeAsProgram(Genotype_TreeAsProgram genotype) {
		this.genotype = genotype;
	}
	
	public boolean evaluate (TerminalValues tv) throws Exception {
		return genotype.evaluate(tv);
	}
		
	
	@Override
	public boolean equals(Object o) throws ClassCastException{
		Fenotype_TreeAsProgram fo = (Fenotype_TreeAsProgram) o;
		return genotype.equals(fo.genotype);
	}
	
	@Override
	public String toString () {	
		String r = "";
		return r;
	}
}
