package ags.cromosome.implementations.genotypeTranslators;

import java.util.Random;

import ags.cromosome.Fenotype;
import ags.cromosome.Genotype;
import ags.cromosome.Translator;
import ags.cromosome.implementations.fenotypes.Fenotype_TreeAsProgram;
import ags.cromosome.implementations.genotypes.Genotype_TreeAsProgram;
import ags.cromosome.implementations.genotypes.treeAsProgram.Function;
import ags.cromosome.implementations.genotypes.treeAsProgram.Node;
import ags.cromosome.implementations.genotypes.treeAsProgram.Terminal;

public class Translator_TreeAsProgram implements Translator {

	private int inputs;
	private double terminalDensity;
	private int maxDepth;
	private int initMode;
	private int populationSize;
	private int calls;
	
	public Translator_TreeAsProgram(int inputs, double terminalDensity, int maxDepth, int initMode, int populationSize) {
		this.inputs = inputs;
		this.terminalDensity = terminalDensity;
		this.maxDepth = maxDepth;
		this.initMode = initMode;
		this.populationSize = populationSize;
		calls = 0;
	}
	
	public int getInputs() {
		return inputs;
	}
	
	public int getMaxDepth() {
		return maxDepth;
	}
	
	@Override
	public Fenotype translateGenotype(Genotype genotype) {		
		return new Fenotype_TreeAsProgram((Genotype_TreeAsProgram) genotype);
	}

	@Override
	public Genotype getRandomGenotype(Random random) {
		Node p = null;
		if (calls >= populationSize) {
			initMode = 1;
		}
		if (initMode == 0) {
			p = fullInit(random, 1);
		}
		else if (initMode == 1) {
			p = growInit (random, 1);
		}
		else if (initMode == 2) {
			p = growRampedHalf (random);
		}
		return new Genotype_TreeAsProgram(p);
	}

	public Node growInit(Random random, int depth) {
		Node r;
		if (random.nextDouble() < terminalDensity || depth >= maxDepth) {
			//Make r a terminal.
			r = new Terminal(random.nextInt(inputs));
		}else {
			//Make a function.
			Function f;
			try {
				f = Function.newFunction(random.nextInt(Function.maxFunctionID()));
				for (int i = 0; i < f.getNeededChildren(); i++) {
					f.addChildren(growInit(random, depth + 1));
				}
				r = f;
			} catch (Exception e) {
				//Can't happen
				r = null;
			}
			
		}
		return r;
	}
	
	public Node fullInit(Random random, int depth) {
		Node r;
		if (depth >= maxDepth) {
			//Make r a terminal.
			r = new Terminal(random.nextInt(inputs));
		}else {
			//Make a function.
			Function f;
			try {
				f = Function.newFunction(random.nextInt(Function.maxFunctionID()));
				for (int i = 0; i < f.getNeededChildren(); i++) {
					f.addChildren(growInit(random, depth + 1));
				}
				r = f;
			} catch (Exception e) {
				//Can't happen
				r = null;
			}
			
		}
		return r;
	}

	private Node growRampedHalf(Random random) {
		Node r = null;
		int groupSize = (int)populationSize / (maxDepth - 1); //11 for 107/9
		int leftover = populationSize % (maxDepth - 1); // 8 for 107/9
		int currGroup = (int)calls / groupSize;
		if (calls >= populationSize - leftover) {
			currGroup--;
		}
		boolean fullInit = calls % groupSize < (int)(groupSize/2);
		
		int aux = maxDepth;
		maxDepth = currGroup + 2;
		if (fullInit) {
			r = fullInit(random, 1);
		}else {
			r = growInit(random, 1);
		}
		maxDepth = aux;
		
		calls ++;	
		
		return r;
	}


}
