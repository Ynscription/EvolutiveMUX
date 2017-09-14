package ags.implementations.selectionPolicies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

import ags.cromosome.Cromosome;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class SelectionPolicy_PressureRoulet_Double extends SelectionPolicy_FitnessProporcional_Double {

	public SelectionPolicy_PressureRoulet_Double(Random random) {
		super(random);
		
	}
	
	@Override
	public ArrayList<Cromosome> select(boolean maximize, TreeSet<Cromosome> population) {
		//Calculate the sum of all fitnesses of the population.
		double worstValue;
		double bestValue;
		if (maximize) {
			worstValue = ((Fitness_Double)population.first().getFitness()).getValue();
			bestValue = ((Fitness_Double)population.last().getFitness()).getValue() - worstValue;
		}else {
			worstValue = ((Fitness_Double)population.last().getFitness()).getValue();
			bestValue = ((Fitness_Double)population.first().getFitness()).getValue() - worstValue;
		}
		double totalFitness = 0;
		
		Iterator<Cromosome> it = population.descendingIterator();
		while (it.hasNext()) {
			Cromosome c = it.next();
			double f = ((Fitness_Double)c.getFitness()).getValue() - worstValue;
			totalFitness += f;
		}
		
		double avgFitness = totalFitness/population.size();
		double SP = bestValue/avgFitness;
		double a = ((SP-1)*avgFitness) / (bestValue- avgFitness);
		double b = (1 - a)*avgFitness;
		
		//We are going to use a TreeMap, since its very convenient
		//The key will be the acumulated percentage of fitness if each cromosome.
		TreeMap<Double, Cromosome> roulet = new TreeMap<Double, Cromosome>();
		//Iterate from less fitness to higher fitness.
		it = population.iterator();
		//Keep track of the acumulated percentage
		double acumulated = 0;
		while (it.hasNext()) {
			Cromosome c = it.next();
			double f = (((Fitness_Double)c.getFitness()).getValue() -worstValue);
			acumulated += (a*f + b)/totalFitness;
			//Save each cromosome with its acumulated percentage as the key.
			roulet.put(acumulated , (Cromosome) c.clone());
		}
		
		return selectFromRoulet (roulet, population.size());
	}

	@Override
	protected ArrayList<Cromosome> selectFromRoulet(TreeMap<Double, Cromosome> roulet, int size) {
		
		return null;
	}

}
