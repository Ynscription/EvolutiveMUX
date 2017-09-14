package ags.implementations.mutators.TreeAsProgram;

import java.util.ArrayList;
import java.util.Random;

import ags.cromosome.Cromosome;
import ags.cromosome.Genotype;
import ags.cromosome.implementations.genotypeTranslators.Translator_TreeAsProgram;
import ags.cromosome.implementations.genotypes.Genotype_TreeAsProgram;
import ags.cromosome.implementations.genotypes.treeAsProgram.Function;
import ags.cromosome.implementations.genotypes.treeAsProgram.Node;
import ags.cromosome.implementations.genotypes.treeAsProgram.Terminal;

public class Mutator_SimpleTerminal_TreeAsProgram extends Mutator_Generic_TreeAsProgram {

	private int inputs;
	
	public Mutator_SimpleTerminal_TreeAsProgram(double mutateChance, Random random) {
		super(mutateChance, random);
		inputs = -1;
	}
	
	@Override
	public void mutateCollection(ArrayList<Cromosome> collection) {
		//Mutating affects the cromosomes only at genotype level, so we need to iterate for every
		//cromosome in the collection and tell it to mutate
		for (Cromosome cromosome : collection) {
			if (inputs == -1) {
				inputs = ((Translator_TreeAsProgram)cromosome.getTranslator()).getInputs();
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
		Genotype_TreeAsProgram g = (Genotype_TreeAsProgram) genotype;
		Terminal t = getRandomTerminal(g.getProgram());
		t.setTerminalID(random.nextInt(inputs));
		
		return genotype;
	}
	
	private Terminal getRandomTerminal (Node root) {
		Terminal r = null;
		if (root instanceof Terminal) {
			r = (Terminal) root;
		}
		else {
			r = getRandomTerminal(root.getChildren(random.nextInt(((Function)root).getNeededChildren())));
		}		
		return r;
	}

}
