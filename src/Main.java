//Desarrollado por Enrique Ávila Rodríguez

import java.util.Random;

import javax.swing.SwingUtilities;

import ags.*;
import ags.implementations.breedPolicies.NBinaryASInt.BreedPolicy_CrossMultiPoint_NBinaryAsInt;
import ags.implementations.breedPolicies.NBinaryASInt.BreedPolicy_CrossSinglePoint_NBinaryAsInt;
import ags.implementations.breedPolicies.NBinaryASInt.BreedPolicy_CrossUniform_NBinaryAsInt;
import ags.implementations.mutators.NBinaryAsInt.Mutator_NBinaryAsInt;
import ags.implementations.replacementPolicies.*;
import ags.implementations.selectionPolicies.*;
import gui.MainWindow;
import ags.cromosome.*;
import ags.cromosome.implementations.genotypeTranslators.*;
import prac1Specific.*;

@SuppressWarnings("unused")
public class Main {

	public static void main(String[] args) {
		Thread t = new Thread() {
			
			@Override
			public void run() {
				super.run();

				MainWindow mw = new MainWindow();
			}
		};
		
		SwingUtilities.invokeLater(t);
		
		
		//Random random = new Random();
		
		//Cromosome c1 = exercise1(random);
		
		
		//Cromosome c2 = exercise2 (random);
		
		//Cromosome c3 = exercise3 (random);
		
		//Cromosome c4 = exercise4(random, 3);
		
		//Cromosome c5 = exercise5 (random);
		
		/*
		printBest(c1, 1);
		printBest(c2, 2);
		printBest(c3, 3);
		printBest(c4, 4);
		printBest(c5, 5);
		*/
		
		
	}
	

	private static Cromosome exercise1(Random random) {
		
		double precision = 0.001;
		
		int np = 100;
		int generations = 100;
		
		double breedChance = 0.6;
		double breedRate = 0.5;
		
		double mutateChance = 0.05;
		double mutateRate = 0.5;
		
		
		Translator translator = new Translator_BinaryToRangedDouble(1, precision, Ex1_FitnessFunction.getMins(), Ex1_FitnessFunction.getMaxs());
		
		SelectionPolicy selectionPolicy = new SelectionPolicy_DeterministicTournament_Double(random, 3);
		FitnessFunction fitnessFunction = new Ex1_FitnessFunction();
		BreedPolicy breedPolicy = new BreedPolicy_CrossMultiPoint_NBinaryAsInt(random, breedChance, 3);
		
		Mutator mutator = new Mutator_NBinaryAsInt(mutateRate, mutateChance, random);
		
		BloatingController bloatingController = null;
		
		ReplacementPolicy replacementPolicy = new ReplacementPolicy_TotalReplacement();
		
		
		AGS ags = new AGS(fitnessFunction.maximize(), np, generations, translator, selectionPolicy, fitnessFunction, breedPolicy, mutator, bloatingController, replacementPolicy, null, random, null);
		
		return ags.run();
		
	}


	private static Cromosome exercise2 (Random random) {
		
		double precision = 0.0001;
		
		int np = 100;
		int generations = 100;
		
		double breedChance = 0.6;
		double breedRate = 0.5;
		
		double mutateChance = 0.05;
		double mutateRate = 0.5;
		
		Translator translator = new Translator_BinaryToRangedDouble(2, precision,Ex2_FitnessFunction.getMins(), Ex2_FitnessFunction.getMaxs());
		
		SelectionPolicy selectionPolicy = new SelectionPolicy_ProbabilisticTournament_Double(random, 3, 0.1f);
		FitnessFunction fitnessFunction = new Ex2_FitnessFunction();
		BreedPolicy breedPolicy = new BreedPolicy_CrossSinglePoint_NBinaryAsInt(random, breedChance);
		
		Mutator mutator = new Mutator_NBinaryAsInt(mutateRate, mutateChance, random);
		
		BloatingController bloatingController = null;
		
		ReplacementPolicy replacementPolicy = new ReplacementPolicy_UnfairElitistReplacement(3);
		
		AGS ags = new AGS(fitnessFunction.maximize(), np, generations, translator, selectionPolicy, fitnessFunction, breedPolicy, mutator, bloatingController, replacementPolicy, null, random, null);
		
		return ags.run();
	}
	
	private static Cromosome exercise3(Random random) {
		
		double precision = 0.001;
		
				
		int np = 100;
		int generations = 100;
		
		double breedChance = 0.6;
		double breedRate = 0.5;
		
		double mutateChance = 0.05;
		double mutateRate = 0.5;
		
		Translator translator = new Translator_BinaryToRangedDouble(2, precision, Ex3_FitnessFunction.getMins(), Ex3_FitnessFunction.getMaxs());
		
		SelectionPolicy selectionPolicy = new SelectionPolicy_Roulet_Double(random);
		FitnessFunction fitnessFunction = new Ex3_FitnessFunction();
		BreedPolicy breedPolicy = new BreedPolicy_CrossUniform_NBinaryAsInt(random, breedChance, breedRate);
		
		Mutator mutator = new Mutator_NBinaryAsInt(mutateRate, mutateChance, random);
		
		BloatingController bloatingController = null;
		
		ReplacementPolicy replacementPolicy = new ReplacementPolicy_TotalReplacement();
		
		AGS ags = new AGS(fitnessFunction.maximize(), np, generations, translator, selectionPolicy, fitnessFunction, breedPolicy, mutator, bloatingController, replacementPolicy, null, random, null);
		
		return ags.run();
		
	}
	
	private static Cromosome exercise4(Random random, int n) {
		
		double precision = 0.00001;
		
		
		int np = 100;
		int generations = 100;
		
		double breedChance = 0.6;
		double breedRate = 0.5;
		
		double mutateChance = 0.05;
		double mutateRate = 0.5;
		
		Translator translator = new Translator_BinaryToRangedDouble(n, precision, Ex4_FitnessFunction.getMins(n), Ex4_FitnessFunction.getMaxs(n));
		
		SelectionPolicy selectionPolicy = new SelectionPolicy_Truncate_Double(0.5f);
		FitnessFunction fitnessFunction = new Ex4_FitnessFunction();
		BreedPolicy breedPolicy = new BreedPolicy_CrossMultiPoint_NBinaryAsInt(random, breedChance, n/2);
		
		Mutator mutator = new Mutator_NBinaryAsInt(mutateRate, mutateChance, random);
		
		BloatingController bloatingController = null;
		
		ReplacementPolicy replacementPolicy = new ReplacementPolicy_UnfairElitistReplacement(3);
		
		AGS ags = new AGS(fitnessFunction.maximize(), np, generations, translator, selectionPolicy, fitnessFunction, breedPolicy, mutator, bloatingController, replacementPolicy, null, random, null);
		
		return ags.run();
		
	}
	
	private static Cromosome exercise5(Random random) {
		
		double precision = 0.0001;
						
		int np = 100;
		int generations = 100;
		
		double breedChance = 0.6;
		double breedRate = 0.5;
		
		double mutateChance = 0.05;
		double mutateRate = 0.5;
		
		Translator translator = new Translator_BinaryToRangedDouble(2, precision, Ex5_FitnessFunction.getMins(), Ex5_FitnessFunction.getMaxs());
		
		SelectionPolicy selectionPolicy = new SelectionPolicy_UniversalStochastic_Double(random);
		FitnessFunction fitnessFunction = new Ex5_FitnessFunction();
		BreedPolicy breedPolicy = new BreedPolicy_CrossSinglePoint_NBinaryAsInt(random, breedChance);
		
		Mutator mutator = new Mutator_NBinaryAsInt(mutateRate, mutateChance, random);
		
		BloatingController bloatingController = null;
		
		ReplacementPolicy replacementPolicy = new ReplacementPolicy_TotalReplacement();
		
		AGS ags = new AGS(fitnessFunction.maximize(), np, generations, translator, selectionPolicy, fitnessFunction, breedPolicy, mutator, bloatingController, replacementPolicy, null, random, null);
		
		return ags.run();
		
	}
	
	private static void printBest(Cromosome bestCromosome, int ex) {
		System.out.println("All-Time Best (Ex " + ex + ")" + System.lineSeparator() + bestCromosome);
		
	}
	
	

}
