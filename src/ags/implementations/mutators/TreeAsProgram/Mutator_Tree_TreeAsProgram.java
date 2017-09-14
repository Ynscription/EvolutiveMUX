package ags.implementations.mutators.TreeAsProgram;

import java.util.ArrayList;
import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.Genotype;
import ags.cromosome.implementations.genotypeTranslators.Translator_TreeAsProgram;
import ags.cromosome.implementations.genotypes.Genotype_TreeAsProgram;
import ags.cromosome.implementations.genotypes.treeAsProgram.Node;

public class Mutator_Tree_TreeAsProgram extends Mutator_Generic_TreeAsProgram {

	private Translator_TreeAsProgram translator;
	private boolean fullInit;
	
	public Mutator_Tree_TreeAsProgram(double mutateChance, boolean fullInit, Random random) {
		super(mutateChance, random);
		translator = null;
		this.fullInit = fullInit;
	}
	
	@Override
	public void mutateCollection(ArrayList<Cromosome> collection) {
		//Mutating affects the cromosomes only at genotype level, so we need to iterate for every
		//cromosome in the collection and tell it to mutate
		for (Cromosome cromosome : collection) {
			if (translator == null) {
				translator = (Translator_TreeAsProgram)cromosome.getTranslator();
			}
			//If the cromosome is selected, tell it to mutate.
			if (random.nextDouble() < mutateChance) {	
				cromosome.mutate();
				totalMutations++;
			}
		}
	}

	@Override
	public Genotype mutate(Genotype genotype) {
		Node root = ((Genotype_TreeAsProgram)genotype).getProgram();
		Genotype g = genotype;
		int childN [] = new int [1];
		if (root.getChildrenAmount() > 1) {
			int cut = 1 + random.nextInt(root.getChildrenAmount() - 1);
			Node nodes [] = root.depthGetParentNodeAtPosition(cut, childN);
			
			if (fullInit) {
				nodes[0].replaceChildren(childN[0], translator.fullInit(random, root.getDepthAt(cut)));
			}else {
					nodes[0].replaceChildren(childN[0], translator.growInit(random, root.getDepthAt(cut)));
			}
		}
		else {
			if (fullInit) {
				g = new Genotype_TreeAsProgram(translator.fullInit(random, 1));
			}else {
				g = new Genotype_TreeAsProgram(translator.growInit(random, 1));
			}
		}
		return g;
	}

}








