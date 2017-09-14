package ags.implementations.selectionPolicies;

import java.util.Random;

import ags.cromosome.Cromosome;

public class SelectionPolicy_DeterministicTournament_Double extends SelectionPolicy_GenericTournament_Double{

	public SelectionPolicy_DeterministicTournament_Double(Random random, int nOponents) {
		super(random, nOponents);
	}

	@Override
	Cromosome selectWinner(boolean maximize, Cromosome[] oponents) {
		int comparison = -1;
		if (!maximize) {
			comparison = 1;
		}
		int winner = 0;
		for (int i = 1; i < nOponents; i++) {
			if (oponents[winner].compareTo(oponents[i]) == comparison) {
				winner = i;
			}
		}
		return (Cromosome) oponents[winner].clone();
	}

}
