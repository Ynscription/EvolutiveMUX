package ags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

import ags.cromosome.Cromosome;
import ags.cromosome.Fitness;
import ags.cromosome.FitnessFunction;
import ags.cromosome.Translator;
import ags.implementations.mutators.PermutationAsNInt.Mutator_InvertSpecial_PermutationAsNInt;
import gui.observers.DoneObserver;
import gui.observers.PopulationObserver;

/*The AGS class strives to be an all purpose Genetic Algorithm.
 *The abstraction and occlusion of lower level classes allows to run the algorithm independently
 *of specific implementation.
*/
public class AGS {
	
	private ArrayList<DoneObserver> doneObservers;
	private ArrayList<PopulationObserver> popObservers;
	
	private TreeSet<Cromosome> population; //The population of a generation. Ordered by Fitness.
	private int nPopulation; //The amount of individuals in each generation.
	private int generations; //The amount of generations the AGS will run for.

	private Translator translator; //The Translator is an adapter, that transforms Genotype to Fenotype.
	
	private SelectionPolicy selectionPolicy; //The policy by wich cromosomes are selected for the next gen.
	
	private FitnessFunction fitnessFunction; //The function to calculate the fitness.
	
	private BreedPolicy breedPolicy; //The Policy by wich cromosomes will be bred.

	private Mutator mutator; //The algorithm for mutating a certain genotype.
	
	private BloatingController bloatingController;
	
	private ReplacementPolicy replacementPolicy;
	
	private DuplicateRemover duplicateRemover;
	
	private Random random; //Used in many steps of the process
	
	private Cromosome bestCromosome; //The all-time best cromosome.
	private Cromosome worstCromosome; //The all-time worst cromosome.
	private Fitness avgFitness;	//The average fitness of a generation.
	
	private Fitness globalAvgFitness; //The average fitness of the algorithm.
	
	private Mutator inverter;
	
	//private int bestGen; //The generation in wich the all-time best cromosome was found.
	
	private int currGen; //Tracks the number of the current generation
	
	private boolean maximize;
	
	private volatile boolean stop;
	

	public AGS( boolean maximize, int np, int generations, Translator translator, 
				SelectionPolicy selectionPolicy, FitnessFunction fitnessFunction, 
				BreedPolicy breedPolicy, Mutator mutator, BloatingController bloatingController, 
				ReplacementPolicy replacementPolicy, DuplicateRemover duplicateRemover, 
				Random random, Mutator inverter) {
		
		//Assign everything
		this.maximize = maximize;
		this.population = new TreeSet<Cromosome>();
		nPopulation = np;
		this.generations = generations;
		
		this.translator = translator;
		
		this.selectionPolicy = selectionPolicy;
		this.fitnessFunction = fitnessFunction;
		
		this.breedPolicy = breedPolicy;
		this.mutator = mutator;
		
		this.bloatingController = bloatingController;
		
		this.replacementPolicy = replacementPolicy;
		
		this.duplicateRemover = duplicateRemover;
		
		this.random = random;	
		
		this.inverter = inverter;
		
		doneObservers = new ArrayList<DoneObserver>();
		popObservers = new ArrayList<PopulationObserver>();
		
		currGen = 0;
		stop = false;
		
	}
	
	public boolean isMaximize () {
		return maximize;
	}
	
	public Cromosome bestCromosome () {
		return bestCromosome;
	}
	
	public int generations () {
		return generations;
	}
	
	public void stop () {
		stop = true;
	}
	
	//This are the general steps of the algorithm.	
	public Cromosome run () {
		//For each generation
		for (; (currGen < generations) && (!stop); currGen++) {
			//Select the cromosomes for the next generation unsing the Selection Policy.
			ArrayList<Cromosome> select = selectionPolicy.select(maximize, population);
			
			//Breed the selected cromosomes for the next generation using the Breed Policy.
			ArrayList<Cromosome> breed =  breedPolicy.breedSelection (select);
			
			//Mutate the selection after breeding.
			mutator.mutateCollection(breed);
			
			if (inverter != null) {
				//Invert the selection after mutation, if applies.
				inverter.mutateCollection(breed);
			}
			
			if (bloatingController != null) {
				//Control bloating after mutation (and inversion), if applies.
				bloatingController.controlBloating(breed);
			}
			
			//Replace the population using the Replacement Policy
			population = replacementPolicy.replace(maximize, population, breed); 
			
			//Check for duplicates
			if (duplicateRemover != null) {
				int attempts = 0;
				while (duplicateRemover.removeDuplicates(population) && attempts < 1000)
					attempts++;
				if (attempts == 1000){
					System.out.println("Impossible to eliminate duplicates in current setting! Changing to random substitution!");
					duplicateRemover.setRandom(random);
				}
			}
			
			//Replace the all-time best cromosome if needed
			checkBest(currGen + 1);
			
			//Calculate average Fitness of this generation
			calculateAvg();
			
			for (PopulationObserver obs : popObservers) {
				Cromosome [] pop = new Cromosome [population.size()];
				population.toArray(pop);
				obs.updatePopulation(pop, population.size());
				Cromosome CGBest;
				if (maximize) {
					CGBest = population.last();
				}
				else {
					CGBest = population.first();
				}
				obs.updatePopulationStats(false, bestCromosome.getFitness(), CGBest.getFitness(), avgFitness);
			}
			
		}
		
		
		for (DoneObserver obs : doneObservers) {
			if (!stop) {
				obs.done("RUN");
			}else {
				if (currGen >= generations) {
					obs.done("RUN");
				}else {
					obs.done("STEP");
				}
			}
		}
		
		stop = false;
		//Once the algorythm is done return the all-time best cromosome
		return bestCromosome;
	}

	public void printStats () {
		System.out.println("Best Cromosome: ");
		System.out.println(bestCromosome);
		System.out.println("");
		System.out.println("Worst Cromosome: ");
		System.out.println(worstCromosome);
		System.out.println("");
		System.out.println("Avg Fitness: " + globalAvgFitness);
		System.out.println("Crossovers: " + breedPolicy.getCrossovers());
		System.out.println("Mutations: " + mutator.getMutations());
		if (inverter != null) {
			System.out.println("Inversions: " + ((Mutator_InvertSpecial_PermutationAsNInt) inverter).getInversions());
		}
	}


	public void initializePopulation() {
		population.clear();
		
		//Generate random Cromosomes untill the population is full
		for (int i = 0; i < nPopulation; i++) {
			/*We generate a random cromosome by giving it a random Genotype.
			 *All information on how the genotype behaves is in the translator 
			 *allowing it to generate random genotypes.
			*/
			Cromosome c = new Cromosome(translator.getRandomGenotype(random), translator, fitnessFunction, mutator);
			
			//Add the new Cromosome to the population tree. 
			//The TreeSet wil call compareTo, wich will call evaluate, no need to worry about explicitely evaluating
			population.add(c);
			
		}
		//Since its the first generation, the bestCromosome is the highest in the tree.
		if (maximize) {
			bestCromosome = population.last();
			worstCromosome = population.first();
		}else {
			bestCromosome = population.first();
			worstCromosome = population.last();
		}
		
		currGen = 0;
		
		//Calculate average Fitness for this generation.
		calculateAvg();
		
		for (PopulationObserver obs : popObservers) {
			Cromosome [] pop = new Cromosome [population.size()];
			population.toArray(pop);
			obs.updatePopulation(pop, population.size());
			Cromosome CGBest;
			if (maximize) {
				CGBest = population.last();
			}
			else {
				CGBest = population.first();
			}
			obs.updatePopulationStats(true, bestCromosome.getFitness(), CGBest.getFitness(), avgFitness);
		}
		
		for (DoneObserver obs : doneObservers) {
			obs.done("INIT");
		}

		
		
		
	}
	
	public void step () {
		//Select the cromosomes for the next generation unsing the Selection Policy.
		ArrayList<Cromosome> select = selectionPolicy.select(maximize, population);
		
		//Breed the selected cromosomes for the next generation using the Breed Policy.
		ArrayList<Cromosome> breed =  breedPolicy.breedSelection (select);
		
		//Mutate the selection after breeding.
		mutator.mutateCollection(breed);
		
		if (inverter != null) {
			//Invert the selection after mutation, if applies.
			inverter.mutateCollection(breed);
		}
		
		if (bloatingController != null) {
			//Control bloating after mutation (and inversion), if applies.
			bloatingController.controlBloating(breed);
		}
					
		//Replace the population using the Replacement Policy
		population = replacementPolicy.replace(maximize, population, breed); 
		
		//Check for duplicates
		if (duplicateRemover != null) {
			int attempts = 0;
			while (duplicateRemover.removeDuplicates(population) && attempts < 100)
				attempts++;
		}
		
		//Replace the all-time best cromosome if needed
		checkBest(currGen + 1);
		
		//Calculate average Fitness of this generation
		calculateAvg();
		
		//Now we are at the next generation.
		currGen++;
		
		for (PopulationObserver obs : popObservers) {
			Cromosome [] pop = new Cromosome [population.size()];
			population.toArray(pop);
			obs.updatePopulation(pop, population.size());
			Cromosome CGBest;
			if (maximize) {
				CGBest = population.last();
			}
			else {
				CGBest = population.first();
			}
			obs.updatePopulationStats(false, bestCromosome.getFitness(), CGBest.getFitness(), avgFitness);
		}
		
		for (DoneObserver obs : doneObservers) {
			obs.done("STEP");
			if (currGen == generations) {
				obs.done("RUN");
			}
		}
		
			
	}
		
	private void checkBest(int generation) {
		//If this gens best is beter than the all-time best, replace it.
		if (maximize) {
			if (bestCromosome.compareTo(population.last()) == -1) {
				bestCromosome = (Cromosome) population.last().clone();
			}
			if (worstCromosome.compareTo(population.first()) == 1) {
				worstCromosome = (Cromosome) population.first().clone();
			}
		}else {
			if (bestCromosome.compareTo(population.first()) == 1) {
				bestCromosome = population.first();
				
			}
			if (worstCromosome.compareTo(population.last()) == -1) {
				worstCromosome = (Cromosome) population.last().clone();
			}
		}
	}
	
	private void calculateAvg() {
		//Since we don't know the values that the fitness takes, we need to do Fitness operations.
		//Inicialize the average to 0;
		avgFitness = bestCromosome.getFitness().zero();
		Iterator<Cromosome> it = population.iterator();
		while (it.hasNext()) {
			//For every cromosome we add its fitness to the average;
			avgFitness = avgFitness.add(it.next().getFitness());
		}
		//Divide the total fitness by the number of cromosomes to get the average.
		avgFitness = avgFitness.divide(nPopulation);
		if (globalAvgFitness == null) {
			globalAvgFitness = avgFitness.add(avgFitness.zero());
		}
		else {
			globalAvgFitness = globalAvgFitness.add(avgFitness).divide(2);
		}
		
	}
	
	public void addDoneObserver (DoneObserver obs) {
		doneObservers.add(obs);
	}
	
	public void addPopulationObserver (PopulationObserver obs) {
		popObservers.add(obs);
	}

	public int popSize() {
		return nPopulation;
	}
}
