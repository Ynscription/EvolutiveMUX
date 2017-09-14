package ags.implementations.breedPolicies.TreeAsProgram;

import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.genotypes.Genotype_TreeAsProgram;
import ags.cromosome.implementations.genotypes.treeAsProgram.Node;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_SimpleCross_TreeAsProgram extends BreedPolicy_CrossGeneric {
	

	public BreedPolicy_SimpleCross_TreeAsProgram(Random random, double crossChance) {
		super(random, crossChance);
	}

	@Override
	public Cromosome[] breed(Cromosome c1, Cromosome c2) {

		Node p1 = ((Genotype_TreeAsProgram)c1.getGenotype()).getProgram();
		Node p2 = ((Genotype_TreeAsProgram)c2.getGenotype()).getProgram();
		
		int cut1;
		int cut2;
		int pos1 [] = {-1};
		int pos2 [] = {-1};	
		Node n1 [] = null;
		Node n2 [] = null;
		boolean fail = false;
		try {
			cut1 = 1 + random.nextInt(p1.getChildrenAmount() - 1);
			n1 = p1.depthGetParentNodeAtPosition(cut1, pos1);
		}
		catch (IllegalArgumentException ex) {
			fail = true;
			try {
				cut2 = 1 + random.nextInt(p2.getChildrenAmount() - 1);		
				n2 = p2.depthGetParentNodeAtPosition(cut2, pos2);
				n2[0].replaceChildren(pos2[0], p1);
				c1 = c1.newCromosome(new Genotype_TreeAsProgram(n2[1]));
			}
			catch (IllegalArgumentException ex2) {
				
			}
		}
		
		if (!fail) {
			try {
				cut2 = 1 + random.nextInt(p2.getChildrenAmount() - 1);		
				n2 = p2.depthGetParentNodeAtPosition(cut2, pos2);
			}
			catch (IllegalArgumentException ex) {
				fail = true;
				n1[0].replaceChildren(pos1[0], p2);
				c2 = c2.newCromosome(new Genotype_TreeAsProgram(n1[1]));
			}
		}
		if (!fail) {
			n1[0].replaceChildren(pos1[0], n2[1]);
			n2[0].replaceChildren(pos2[0], n1[1]);
		}
		
		Cromosome r [] = {c1, c2};
		
		return r;
	}

}
