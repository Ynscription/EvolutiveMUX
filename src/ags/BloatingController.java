package ags;

import java.util.ArrayList;

import ags.cromosome.Cromosome;
import ags.cromosome.Fitness;

public interface BloatingController {
	
	public void controlBloating (ArrayList<Cromosome> population);
	
	public Fitness getPenalty (Cromosome cromosome);
}
