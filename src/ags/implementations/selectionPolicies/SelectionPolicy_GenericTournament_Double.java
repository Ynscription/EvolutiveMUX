package ags.implementations.selectionPolicies;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import ags.SelectionPolicy;
import ags.cromosome.Cromosome;

public abstract class SelectionPolicy_GenericTournament_Double implements SelectionPolicy{
	protected int nOponents;
	protected Random random;
	
	public SelectionPolicy_GenericTournament_Double(Random random, int nOponents) {
		this.nOponents = nOponents;
		this.random = random;
	}

	@Override
	public ArrayList<Cromosome> select(boolean maximize, TreeSet<Cromosome> population) {
		Cromosome inPopulation [] = new Cromosome [population.size()];
		population.toArray(inPopulation);
		
		ArrayList<Cromosome> selection = new ArrayList<Cromosome>();
		Cromosome oponents [] = new Cromosome [nOponents];
		
		for (int i = 0; i < population.size(); i++) {
			for (int j = 0; j < nOponents; j++) {
				oponents [j] = inPopulation[random.nextInt(population.size())];
			}
			selection.add(selectWinner(maximize, oponents));
		}
		
		return selection;		
	}
	
	abstract Cromosome selectWinner (boolean maximize, Cromosome oponents[]);
	
}
