package ags.cromosome.implementations.genotypes;

import ags.cromosome.Genotype;
import ags.cromosome.implementations.genotypes.treeAsProgram.Node;
import ags.cromosome.implementations.genotypes.treeAsProgram.TerminalValues;

public class Genotype_TreeAsProgram implements Genotype {
	
	private Node program;
	
	public Genotype_TreeAsProgram(Node p) {
		program = p;
	}
	
	public boolean evaluate (TerminalValues tv) throws Exception {
		return program.evaluate(tv);
	}
	
	public Node getProgram () {
		return program;
	}
	
	
	@Override
	public String toString () {
		return program.toString();
	}
	
	@Override
	public boolean equals(Object obj){
		Genotype_TreeAsProgram go = (Genotype_TreeAsProgram) obj;
		return program.equals(go.program);
	}
	
	@Override
	public Object clone() {
		return new Genotype_TreeAsProgram((Node) program.clone());
	}

	@Override
	public int getSize() {
		return program.getChildrenAmount();
	}
	
}
