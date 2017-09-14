package ags.implementations.replacementPolicies;

import java.util.ArrayList;
import java.util.TreeSet;

import ags.ReplacementPolicy;
import ags.cromosome.Cromosome;

public class ReplacementPolicy_TotalReplacement implements ReplacementPolicy{

	@Override
	public TreeSet<Cromosome> replace(boolean maximize, TreeSet<Cromosome> population, ArrayList<Cromosome> replacement) {
		//Return a new treeset with all the elements in the replacement collection.
		return new TreeSet<Cromosome>(replacement);
	}

}
