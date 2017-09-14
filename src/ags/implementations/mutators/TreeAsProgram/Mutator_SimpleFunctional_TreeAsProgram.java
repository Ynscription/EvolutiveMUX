package ags.implementations.mutators.TreeAsProgram;

import java.util.ArrayList;
import java.util.Random;

import ags.cromosome.Genotype;
import ags.cromosome.implementations.genotypes.Genotype_TreeAsProgram;
import ags.cromosome.implementations.genotypes.treeAsProgram.Node;
import ags.cromosome.implementations.genotypes.treeAsProgram.functions.Function_AND;
import ags.cromosome.implementations.genotypes.treeAsProgram.functions.Function_OR;

public class Mutator_SimpleFunctional_TreeAsProgram extends Mutator_Generic_TreeAsProgram {

	
	public Mutator_SimpleFunctional_TreeAsProgram(double mutateChance, Random random) {
		super(mutateChance, random);
	}

	@Override
	public Genotype mutate(Genotype genotype) {
		Node root = ((Genotype_TreeAsProgram)genotype).getProgram();
		ArrayList<ParentHolder> possibleNodes = new ArrayList<ParentHolder>();
		
		if (root instanceof Function_AND || root instanceof Function_OR) {
			ParentHolder ph = new ParentHolder(root);
			ph.plausibleChildren = null;
			possibleNodes.add(ph);			
		}
		
		getPossibleFunctions(possibleNodes, root);
		
		Genotype_TreeAsProgram g = (Genotype_TreeAsProgram) genotype;
		
		if (possibleNodes.size() > 0) {
			ParentHolder ph = possibleNodes.get(random.nextInt(possibleNodes.size()));
			if (ph.plausibleChildren == null) {
				Node newNode;
				if (ph.parent instanceof Function_OR) {
					 newNode = new Function_AND();
				}
				else {
					 newNode = new Function_OR();
				}
				for (int i = 0; i < ph.parent.getNeededChildren(); i++) {
					newNode.addChildren(ph.parent.getChildren(i));
				}
				g = new Genotype_TreeAsProgram(newNode);
			}
			else {
				int pos = ph.plausibleChildren.get(random.nextInt(ph.plausibleChildren.size()));
				Node targetNode = ph.parent.getChildren(pos);
				Node newNode = null;
				if (targetNode instanceof Function_OR) {
					 newNode = new Function_AND();
				}
				else {
					 newNode = new Function_OR();
				}
				for (int i = 0; i < targetNode.getNeededChildren(); i++) {
					newNode.addChildren(targetNode.getChildren(i));
				}
				ph.parent.replaceChildren(pos, newNode);
				g = (Genotype_TreeAsProgram) genotype;
			}
		}
				
		return g;
	}
	
	public void getPossibleFunctions (ArrayList<ParentHolder> possibleNodes, Node root) {
		ParentHolder ph = new ParentHolder(root);
		for (int i = 0; i < ((Node)root).getNeededChildren(); i++) {
			if (root.getChildren(i) instanceof Function_AND || root.getChildren(i) instanceof Function_OR) {
				ph.plausibleChildren.add(i);
			}
			getPossibleFunctions(possibleNodes, root.getChildren(i));
		}
		if (ph.plausibleChildren.size() > 0) {
			possibleNodes.add(ph);
		}		
	}
	
	private class ParentHolder  {
		public Node parent;
		public ArrayList<Integer> plausibleChildren;
		
		public ParentHolder(Node parent) {
			this.parent = parent;
			plausibleChildren = new ArrayList<Integer>();
		}
	}

}
