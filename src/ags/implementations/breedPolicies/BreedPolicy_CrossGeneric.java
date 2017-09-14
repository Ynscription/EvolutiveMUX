package ags.implementations.breedPolicies;

import java.util.ArrayList;
import java.util.Random;

import ags.BreedPolicy;
import ags.cromosome.Cromosome;

public abstract class BreedPolicy_CrossGeneric implements BreedPolicy {
	protected Random random; //Random generator to allow the cross to be random
	protected double crossChance; //The chance to cross a certain individual of the population.
	private int totalCrossovers;

	public BreedPolicy_CrossGeneric(Random random, double crossChance) {
		//Initialize everything.
		this.random = random;
		this.crossChance = crossChance;
		totalCrossovers = 0;
	}
	
	@Override
	public int getCrossovers () {
		return totalCrossovers;
	}

	//Selects elements to breed from a collection, breeds them and returns the union of the bred and the untouched.
	@Override
	public ArrayList<Cromosome> breedSelection(ArrayList<Cromosome> select) {
		//For ease of use we put the cromosmes in an array.
		Cromosome inSelect [] = new Cromosome [select.size()];
		select.toArray(inSelect);
		//This array will contain the cromosomes seleceted for crossing.
		Cromosome crossSelected [] = new Cromosome [select.size()];
		int crossSelectedSize = 0;

		//For each cromosome in the collection, if they are selected move them to the crossSelecetd array.
		for (int i = 0; i < select.size(); i++){
			if (random.nextDouble() < crossChance) {
				crossSelected[crossSelectedSize] = inSelect[i];
				crossSelectedSize++;
				inSelect[i] = null;
			}
		}
		//If we have an odd number of selected cromosomes for breeding, we remove the last one to make it even.
		Cromosome extra = null;
		if (crossSelectedSize % 2 == 1) {
			extra = crossSelected[crossSelectedSize - 1];
			crossSelected[crossSelectedSize - 1] = null;
			crossSelectedSize--;
		}
		
		//Save this for later use
		int initialCrossSelectedSize = crossSelectedSize;
		//The crossed ArrayList is the collection of cromosomes crossed and untouched.
		ArrayList<Cromosome> crossed = new ArrayList<Cromosome>();
		
		//As long as there are at least 2 cromosomes ramining to be crossed
		while (crossSelectedSize >= 2) {
			//Select two random cromosomes
			Cromosome c1 = getRandomCromosomeFromArray(initialCrossSelectedSize, crossSelected);
			crossSelectedSize--;
			
			Cromosome c2 = getRandomCromosomeFromArray(initialCrossSelectedSize, crossSelected);
			crossSelectedSize--;
			
			//Breed them
			Cromosome c [] = breed(c1, c2);
			totalCrossovers++;
			
			//Add them to the collection we will be returning.
			crossed.add(c[0]);
			crossed.add(c[1]);

		}
		//We add to the crossed collection the untouched cromosomes
		for (int i = 0; i < select.size(); i++) {
			if (inSelect[i] != null) 
				crossed.add(inSelect[i]);
		}
		//The extra if there was one is added too
		if (extra != null) {
			crossed.add(extra);
		}

		return crossed;
	}
	
	private Cromosome getRandomCromosomeFromArray(int initialCrossSelectedSize, Cromosome[] crossSelected) {
		//Get a random position
		int pos = random.nextInt(initialCrossSelectedSize);
		
		//Since when we select a cromosome we remove it from the array,
		//rather than randomly choosing a position untill its not null (wich could potentially not terminate)
		//We select the next cromosome to the position that is not null.
		Cromosome c1 = crossSelected [pos];
		while (c1 == null) {
			pos++;
			if (pos >= initialCrossSelectedSize) {
				pos = 0;
			}
			c1 = crossSelected[pos];
		}
		//We remove the selected cromosome from the array
		crossSelected[pos] = null;
		return c1;
	}
	
	//The breed function will be implemented in specific classes, as depending on the policy it will be different. 
	@Override
	public abstract Cromosome[] breed(Cromosome c1, Cromosome c2);


}
