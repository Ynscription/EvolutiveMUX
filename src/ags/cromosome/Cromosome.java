package ags.cromosome;

import ags.BloatingController;
import ags.Mutator;
import ags.cromosome.implementations.fitnesses.FitnessWithPenalty;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class Cromosome implements Comparable<Cromosome>{
	
	private Genotype genotype; 	//The genotype of this cromosome
	private Fenotype fenotype; 	//The fenotype of this cromosome
	private Fitness fitness;	//The fitness of this cromosome
	private Translator translator; //The translator to calculate the fenotype from the genotype
	private Mutator mutator;
	private FitnessFunction fitnessFunction;
	
	public Cromosome (Genotype genotype, Translator translator, FitnessFunction fitnessFunction, Mutator mutator) {
		this.genotype = genotype;
		this.translator = translator;
		this.fenotype = translator.translateGenotype(genotype);
		this.fitnessFunction = fitnessFunction;
		this.mutator = mutator;
		
	}
	
	public Fenotype getFenotype() {
		return fenotype;
	}
	
	public final Genotype getGenotype () {
		return genotype;
	}
	
	public Fitness getFitness(BloatingController bloatingController) {
		return evaluate(bloatingController);
	}
	
	public Fitness getFitness() {
		return evaluate();
	}
	
	public Translator getTranslator() {
		return translator;
	}
	
	public int getSize () {
		return genotype.getSize();
	}
	
	private Fitness evaluate(BloatingController bloatingController) {
		//Assign the fitness as indicated by the fitness function.
		
		if (bloatingController == null) {
			fitness = fitnessFunction.evaluate(fenotype);
		}
		else {
			//((Fenotype_TreeAsProgram)fenotype).setTrueFitness(fitnessFunction.evaluate(fenotype));
			Fitness_Double f = null;
			if (fitnessFunction.maximize()) {
				f = (Fitness_Double) fitnessFunction.evaluate(fenotype).substract(bloatingController.getPenalty(this));
			}
			else {
				f = (Fitness_Double) fitnessFunction.evaluate(fenotype).add(bloatingController.getPenalty(this));
			}
			fitness = new FitnessWithPenalty(f, fitnessFunction.evaluate(fenotype), bloatingController.getPenalty(this));
		}
		
		return fitness;
	}
	
	private Fitness evaluate() {
		//Assign the fitness as indicated by the fitness function.
		if (fitness == null) {
			fitness = fitnessFunction.evaluate(fenotype);
		}
		return fitness;
	}
	
	public void mutate () {
		//Mutate the genotype of this cromosome
		genotype = mutator.mutate(genotype);
		this.fenotype = translator.translateGenotype(genotype);
		fitness = null;
	}
	
	public void mutate (Genotype gen) {
		genotype = gen;
		this.fenotype = translator.translateGenotype(genotype);
		fitness = null;
	}
	
	public Cromosome newCromosome (Genotype genotype) {
		//Returns a new cromosome with the same translator as this one, for ease of use.
		return new Cromosome(genotype, translator, fitnessFunction, mutator);
	}
	
	@Override
	public String toString () {
		//Returns a formatted string with the genotype, fenotype and fitness of this cromosome.
		return "		Ge: " + genotype + System.lineSeparator() + "		Fe: " + fenotype + System.lineSeparator() + "		Fi: " + fitness;
	}

	@Override
	public int compareTo(Cromosome o) {
		//Compares the fitnesses of the cromosomes. 
		//This function is called when inserting the cromosome into the tree, to sort it.
		return evaluate().compareTo(o.getFitness());
	}
	
	@Override
	public Object clone() {
		//Returns a new cromosome that is equal to this one, but changes made to one won't affect the other.
		Cromosome c = new Cromosome((Genotype) genotype.clone(), translator, fitnessFunction, mutator);
		c.fitness = (Fitness) fitness.clone();
		return c;		
	}
	
	@Override
	public boolean equals(Object obj) {
		Cromosome co = (Cromosome) obj;
		return this.genotype.equals(co.genotype);
	}
}
