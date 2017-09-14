package ags.implementations.bloatingControllers;

import java.util.ArrayList;

import ags.BloatingController;
import ags.cromosome.Cromosome;
import ags.cromosome.Fitness;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class ProperlyFoundedPenaly_BloatingController implements BloatingController {

	private double modifier;
	
	public ProperlyFoundedPenaly_BloatingController() {
		modifier = 0;
	}
	
	
	@Override
	public void controlBloating(ArrayList<Cromosome> population) {
		double avgSize = 0;
		double avgFitness = 0;
		
		for (Cromosome cromosome : population) {
			avgSize += cromosome.getSize();
			avgFitness += ((Fitness_Double)cromosome.getFitness()).getValue();
		}
		avgSize = avgSize/population.size();
		avgFitness = avgFitness/population.size();
		
		double sizeVariance = 0;
		double sizeFitnessCovariance = 0;
		for (Cromosome cromosome : population) {
			sizeVariance += Math.pow(cromosome.getSize() - avgSize, 2);
			sizeFitnessCovariance += cromosome.getSize() * ((Fitness_Double)cromosome.getFitness()).getValue();
		}
		
		sizeVariance = sizeVariance/population.size();
		sizeFitnessCovariance = sizeFitnessCovariance / population.size();
		sizeFitnessCovariance = sizeFitnessCovariance - avgSize*avgFitness;
		modifier =  Math.abs(sizeFitnessCovariance/sizeVariance);
		
		for (Cromosome cromosome : population) {
			cromosome.getFitness(this);
		}
	}

	@Override
	public Fitness getPenalty(Cromosome cromosome) {
		return new Fitness_Double(modifier * cromosome.getSize());
	}

}
