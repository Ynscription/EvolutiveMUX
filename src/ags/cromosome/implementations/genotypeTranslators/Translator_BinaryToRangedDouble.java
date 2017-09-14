package ags.cromosome.implementations.genotypeTranslators;

import ags.cromosome.Fenotype;
import ags.cromosome.Genotype;
import ags.cromosome.implementations.fenotypes.Fenotype_NDouble;
import ags.cromosome.implementations.genotypes.Genotype_NBinaryAsInt;
import utils.EvMath;

public class Translator_BinaryToRangedDouble extends Translator_Binary_Generic{
	
	
	private double mins [];
	private double maxs [];
		
	
	public Translator_BinaryToRangedDouble(int N, double [] precisions, double [] mins, double []maxs) {
		super(N);
		this.mins = mins;
		this.maxs = maxs;
		for (int i =0; i < N; i++) {
			lengths[i] = (int) Math.ceil(EvMath.log2(1+ ((maxs[i] - mins[i]) / precisions[i])));
			if (lengths[i] > 31) {
				throw new ExceptionInInitializerError("Precision is too high for a 32 bit integer");
			}		
		}		
	}
	
	public Translator_BinaryToRangedDouble(int N, double precision, double [] mins, double []maxs) {
		super(N);
		this.mins = mins;
		this.maxs = maxs;
		for (int i =0; i < N; i++) {
			lengths[i] = (int) Math.ceil(EvMath.log2(1+ ((maxs[i] - mins[i]) / precision)));
			if (lengths[i] > 31) {
				throw new ExceptionInInitializerError("Precision is too high for a 32 bit integer");
			}
		}		
	}

	@Override
	public Fenotype translateGenotype(Genotype genotype) {
		Genotype_NBinaryAsInt g = (Genotype_NBinaryAsInt) genotype;
		int gv [] = g.getValues();
		double fv [] = new double [N];
		for (int i = 0; i < N; i++) {
			fv[i] = mins[i] + ((maxs[i] - mins[i]) / Math.pow(2, lengths[i])) * gv [i];
		}
		return new Fenotype_NDouble(fv, N);
	}


	

}
