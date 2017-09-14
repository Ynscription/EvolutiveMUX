package ags.implementations.selectionPolicies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import ags.SelectionPolicy;
import ags.cromosome.Cromosome;

public class SelectionPolicy_Truncate_Double implements SelectionPolicy{
	private double truncateThreshold;
	
	public SelectionPolicy_Truncate_Double(double truncateThreshold) {
		this.truncateThreshold = 1/truncateThreshold;
	}

	@Override
	public ArrayList<Cromosome> select(boolean maximize, TreeSet<Cromosome> population) {		
		
		ArrayList<Cromosome> selection = new ArrayList<Cromosome>();
		int threshold = (int) (population.size()/truncateThreshold);
		int overflow = (int) (population.size()%threshold);
		
		Iterator<Cromosome> it;
		if (maximize) {
			it = population.descendingIterator();
		}else {
			it = population.iterator();
		}
		
		for (int i = 0; i < threshold; i++) {
			int extra = 0;
			if (i < overflow) {
				extra = 1;
			}
			Cromosome c = it.next();
			for (int j = 0; j < (int)truncateThreshold + extra; j++) {
				selection.add((Cromosome) c.clone());
			}			
		}		
		
		return selection;
	}
}
