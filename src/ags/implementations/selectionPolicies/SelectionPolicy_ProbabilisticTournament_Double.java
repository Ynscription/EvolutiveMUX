package ags.implementations.selectionPolicies;

import java.util.Random;

import ags.cromosome.Cromosome;

public class SelectionPolicy_ProbabilisticTournament_Double extends SelectionPolicy_GenericTournament_Double{

	private double weakChance;
	
	public SelectionPolicy_ProbabilisticTournament_Double(Random random, int nOponents, double weakChance) {
		super(random, nOponents);
		this.weakChance = weakChance;
	}

	@Override
	Cromosome selectWinner(boolean maximize, Cromosome[] oponents) {
		int winner = 0;
		//By default we select the best of the oponents.
		int comparison = -1;
		if (!maximize) {
			comparison = 1;
		}
		//Decide by random chance if the weakest wins.
		if (random.nextDouble() < weakChance) {
			if (maximize) {
				comparison = 1;
			}else {
				comparison = -1;
			}
		}
		for (int i = 1; i < nOponents; i++) {
			if (oponents[winner].compareTo(oponents[i]) == comparison) {
				winner = i;
			}
		}
		return (Cromosome) oponents[winner].clone();
	}
}
