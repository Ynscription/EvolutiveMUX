package ags;

import java.util.ArrayList;
import java.util.TreeSet;

import ags.cromosome.Cromosome;

public interface ReplacementPolicy {
	
	public TreeSet<Cromosome> replace (boolean maximize, TreeSet<Cromosome> population, ArrayList<Cromosome> replacement);

}
