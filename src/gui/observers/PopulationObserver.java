package gui.observers;


import ags.cromosome.Cromosome;
import ags.cromosome.Fitness;

public interface PopulationObserver {
	
	public void updatePopulationStats (boolean init, Fitness ATBest, Fitness CGBest, Fitness CGAvg);
	
	public void updatePopulation(Cromosome[] population, int size);
	
}
