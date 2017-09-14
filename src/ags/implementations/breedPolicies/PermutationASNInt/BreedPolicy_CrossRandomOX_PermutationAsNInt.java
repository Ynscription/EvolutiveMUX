package ags.implementations.breedPolicies.PermutationASNInt;

import java.util.Random;

import ags.cromosome.Cromosome;
import ags.implementations.breedPolicies.BreedPolicy_CrossGeneric;

public class BreedPolicy_CrossRandomOX_PermutationAsNInt extends BreedPolicy_CrossGeneric{

	int points;
	
	public BreedPolicy_CrossRandomOX_PermutationAsNInt(Random random, double crossChance, int points) {
		super(random, crossChance);
		this.points = points;
	}

	@Override 
	public Cromosome[] breed(Cromosome c1, Cromosome c2) {
		//TODO implement!!		
		return null;
	}

}
