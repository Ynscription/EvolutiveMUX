package ags.implementations.bloatingControllers;

import java.util.ArrayList;
import java.util.Random;

import ags.BloatingController;
import ags.cromosome.Cromosome;
import ags.cromosome.Fitness;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class Tarpeian_BloatingController implements BloatingController{

	private double deathChance;
	private Random random;
	private double avgSize;
	private double modifier;
	
	public Tarpeian_BloatingController(double deathChance, Random random, double modifier) {
		this.deathChance = deathChance;
		this.random = random;
		this.modifier = modifier;
	}
	
	@Override
	public void controlBloating(ArrayList<Cromosome> population) {
		avgSize = 0;
		for (Cromosome cromosome : population) {
			avgSize += cromosome.getSize();			
		}
		avgSize = avgSize/population.size();
		for (Cromosome cromosome : population) {
			cromosome.getFitness(this);
		}
	}

	@Override
	public Fitness getPenalty(Cromosome cromosome) {
		Fitness r = null;
		
		if (cromosome.getSize() > avgSize && random.nextDouble() < deathChance) {
			r = new Fitness_Double(modifier*cromosome.getSize());
		}
		else {
			r = cromosome.getFitness().zero();
		}
		
		return r;
	}
	
	

}
