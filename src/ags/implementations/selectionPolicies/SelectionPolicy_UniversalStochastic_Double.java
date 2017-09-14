package ags.implementations.selectionPolicies;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import ags.cromosome.Cromosome;

public class SelectionPolicy_UniversalStochastic_Double extends SelectionPolicy_FitnessProporcional_Double{
	
	public SelectionPolicy_UniversalStochastic_Double(Random random) {
		super (random);
	}

	@Override
	protected ArrayList<Cromosome> selectFromRoulet(TreeMap<Double, Cromosome> roulet, int size) {
		//The selection that we will return
		ArrayList <Cromosome> select = new ArrayList<Cromosome>();
		
		//Set distance between marks; and first mark.
		double distance = 1f/size;
		double mark = random.nextDouble()*distance;
		
		//For as many marks as intividuals we want in the population.
		for (int i =0; i < size; i++) {
			//Add the corresponding Cromosome to the selection
			select.add((Cromosome) roulet.get(roulet.higherKey(mark)).clone());
			mark += distance;
		}
				
		return select;
	}

	
}
