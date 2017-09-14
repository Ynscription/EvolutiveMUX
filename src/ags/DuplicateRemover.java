package ags;

import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;

import ags.cromosome.Cromosome;

public class DuplicateRemover {

	private boolean replaceRandom;
	private Random random;
	
	public DuplicateRemover(Random random) {
		if (random == null) {
			replaceRandom = false;
		}else {
			this.random = random;
			replaceRandom= true;
		}
	}
	
	public void setRandom (Random random) {
		if (random == null) {
			replaceRandom = false;
		}else {
			this.random = random;
			replaceRandom= true;
		}
	}
	
	public boolean removeDuplicates (TreeSet<Cromosome> population) {
		if (replaceRandom && random == null) {
			throw new IllegalStateException();
		}
		
		int N = population.size();
		boolean changed = false;
		Cromosome inPopulation [] = new Cromosome [N];
		population.toArray(inPopulation);
		
		Cromosome removedArray [] = new Cromosome [N-1];
		
		int removed = 0;
		
		LinkedList<Cromosome> newPopulation = new LinkedList<Cromosome>();
		for (int i = 0; i < N; i ++) {
			if (!newPopulation.contains(inPopulation[i])) {
				newPopulation.add(inPopulation[i]);
			}else {
				removedArray [removed] = (Cromosome) inPopulation[i].clone();
				removed ++;
				changed = true;
			}
		}
		
		population.clear();
		population.addAll(newPopulation);
		
		Cromosome auxC = (Cromosome) population.first().clone(); 
				
		for (int i = 0; i < removed; i++) {
			if (replaceRandom) {
				population.add(auxC.newCromosome(auxC.getTranslator().getRandomGenotype(random)));
			}else {
				removedArray [i].mutate();
				population.add(removedArray [i]);
			}
		}
		
		
		return changed;
	}
	
	 
	
}
