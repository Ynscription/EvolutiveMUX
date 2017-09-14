package ags.cromosome;

import java.util.Random;

public interface Translator {
	
	public Fenotype translateGenotype (Genotype genotype);
	
	public Genotype getRandomGenotype (Random random);

}
