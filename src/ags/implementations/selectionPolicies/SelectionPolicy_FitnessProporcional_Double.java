package ags.implementations.selectionPolicies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

import ags.SelectionPolicy;
import ags.cromosome.Cromosome;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public abstract class SelectionPolicy_FitnessProporcional_Double implements SelectionPolicy{
	protected Random random;
	
	
	public SelectionPolicy_FitnessProporcional_Double(Random random) {
		this.random = random;
	}

	public ArrayList<Cromosome> select(boolean maximize, TreeSet<Cromosome> population) {
		//Calculate the sum of all fitnesses of the population.
		double worstValue;
		if (maximize) {
			worstValue = ((Fitness_Double)population.first().getFitness()).getValue();
		}else {
			worstValue = ((Fitness_Double)population.last().getFitness()).getValue();
		}
		double totalFitness = 0;
		Iterator<Cromosome> it = population.descendingIterator();
		while (it.hasNext()) {
			Cromosome c = it.next();
			double f = ((Fitness_Double)c.getFitness()).getValue() - worstValue;
			totalFitness += f;
		}
		
		//We are going to use a TreeMap, since its very convenient
		//The key will be the acumulated percentage of fitness if each cromosome.
		TreeMap<Double, Cromosome> roulet = new TreeMap<Double, Cromosome>();
		//Iterate from less fitness to higher fitness.
		it = population.iterator();
		//Keep track of the acumulated percentage
		double acumulated = 0;
		while (it.hasNext()) {
			Cromosome c = it.next();
			double f = (((Fitness_Double)c.getFitness()).getValue() -worstValue)/totalFitness;
			acumulated += f;
			//Save each cromosome with its acumulated percentage as the key.
			roulet.put(acumulated , (Cromosome) c.clone());
		}
		
		return selectFromRoulet (roulet, population.size());
	}
	
	protected abstract ArrayList<Cromosome> selectFromRoulet (TreeMap<Double, Cromosome> roulet, int size);

}
