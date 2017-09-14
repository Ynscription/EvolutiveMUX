package ags;

import java.util.ArrayList;
import java.util.TreeSet;

import ags.cromosome.Cromosome;

public interface SelectionPolicy {
	
	public ArrayList<Cromosome> select (boolean maximize, TreeSet<Cromosome> population);

}
