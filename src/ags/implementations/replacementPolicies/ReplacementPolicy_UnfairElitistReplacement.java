package ags.implementations.replacementPolicies;

import java.util.ArrayList;
import java.util.TreeSet;

import ags.ReplacementPolicy;
import ags.cromosome.Cromosome;

public class ReplacementPolicy_UnfairElitistReplacement implements ReplacementPolicy{
	
	private double elite;
	
	public ReplacementPolicy_UnfairElitistReplacement(double elite) {
		this.elite = elite;
	}

	@Override
	public TreeSet<Cromosome> replace(boolean maximize, TreeSet<Cromosome> population, ArrayList<Cromosome> replacement) {
		TreeSet<Cromosome> newPop = new TreeSet<Cromosome>(replacement);
		int nElite = (int) Math.round(population.size() * elite);
		for (int i = 0; i < nElite; i++) {
			//Remove the worst individuals from replacement, to leave room for the elite.
			if (maximize) {
				newPop.pollFirst();
			}else {
				newPop.pollLast();
			}
		}
		for (int i = 0; i < nElite; i++) {
			//Add the best of the current population to the new population.
			if (maximize) {
				newPop.add((Cromosome) population.pollLast().clone());
			}else {
				newPop.add((Cromosome) population.pollFirst().clone());
			}
		}		
		
		return newPop;
	}
}
