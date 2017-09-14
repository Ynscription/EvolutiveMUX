package ags.implementations.selectionPolicies;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;


import ags.cromosome.Cromosome;

//Class to make a selection for fitnesses expressed as double values
public class SelectionPolicy_Roulet_Double extends SelectionPolicy_FitnessProporcional_Double {
	
	public SelectionPolicy_Roulet_Double(Random random) {
		super(random);
		
	}

	@Override
	protected ArrayList<Cromosome> selectFromRoulet(TreeMap<Double, Cromosome> roulet, int size) {
		//The selection that we will return
		ArrayList <Cromosome> select = new ArrayList<Cromosome>();
		
		//Repeat untill we have a full population
		while (select.size() < size) {
			//Select a random number [0,1] that will indicate wich cromosome is selected
			double chance = random.nextDouble();			
			//Add to the selection the cromosome with acumulated percentage immidiately higher to the random number generated.
			//Also note that we make a clone, since we can pick the same one several times.
			select.add((Cromosome) roulet.get(roulet.higherKey(chance)).clone());
			
		}
		
		return select;
	}

	

}
