package gui.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GUI.Utils.layout.FineGridConstraints;
import GUI.Utils.layout.FineGridLayout;
import ags.AGS;
import ags.BloatingController;
import ags.BreedPolicy;
import ags.DuplicateRemover;
import ags.Mutator;
import ags.ReplacementPolicy;
import ags.SelectionPolicy;
import ags.cromosome.FitnessFunction;
import ags.cromosome.Translator;
import ags.cromosome.implementations.genotypeTranslators.Translator_TreeAsProgram;
import ags.implementations.bloatingControllers.ProperlyFoundedPenaly_BloatingController;
import ags.implementations.bloatingControllers.Tarpeian_BloatingController;
import ags.implementations.breedPolicies.TreeAsProgram.BreedPolicy_SimpleCross_TreeAsProgram;
import ags.implementations.mutators.TreeAsProgram.Mutator_SimpleFunctional_TreeAsProgram;
import ags.implementations.mutators.TreeAsProgram.Mutator_SimpleTerminal_TreeAsProgram;
import ags.implementations.mutators.TreeAsProgram.Mutator_Tree_TreeAsProgram;
import ags.implementations.replacementPolicies.ReplacementPolicy_TotalReplacement;
import ags.implementations.replacementPolicies.ReplacementPolicy_UnfairElitistReplacement;
import ags.implementations.selectionPolicies.SelectionPolicy_DeterministicTournament_Double;
import ags.implementations.selectionPolicies.SelectionPolicy_ProbabilisticTournament_Double;
import ags.implementations.selectionPolicies.SelectionPolicy_Roulet_Double;
import ags.implementations.selectionPolicies.SelectionPolicy_Truncate_Double;
import ags.implementations.selectionPolicies.SelectionPolicy_UniversalStochastic_Double;
import gui.MainPanel;
import prac3Specific.MuxProgram_FitnessFunction;
import utils.EvMath;

@SuppressWarnings("serial")
public class Pr3_MainOptionsPanel extends MainOptionsPanel{
	private MainPanel mainPanel;
	
	private Random random;

	private JPanel inside;
	
	private JTextField muxInTxtFld;
	private JPanel muxInP;
	
	private JTextField treeTermDensTxtFld;
	private JTextField treeMaxDepthTxtFld;
	private JComboBox<String> treeInitComboBox;
	
	
	private JTextField gensTxtFld;
	private JTextField popTxtFld;
	
	private JComboBox<String> selectionComboBox;
	private JPanel selectionOpt;
	private JLabel selectionLabel;
	private JTextField selectionTxtFld;
	private JPanel selectionExtraPanel;
	private JTextField weakChance;
	private JCheckBox dupeRmvrChckBx;
	private JCheckBox dupeRmvRandom;
	
	private JPanel breedPanel;
	private JTextField breedChanceTxtFld;
	
	private JPanel mutPanel;
	private JComboBox<String> mutComboBox;
	private JPanel mutOptP;
	private JTextField mutChanceTxtFLd;
	private JCheckBox mutFullInit;
	private JCheckBox mutGrowInit;
	
	private JComboBox<String> bloatingComboBox;
	private JPanel bloatingP;
	private JTextField bloatingDeathTxtFld;
	private JTextField bloatingModTxtFld;
	
	//private JCheckBox inversionCheckBox;
	//private JTextField inversionTxtFld;
	
	private JCheckBox replacementCheckBox;
	private JTextField replacementTxtFld;
	
	private JButton generateButton;
	
	public Pr3_MainOptionsPanel(MainPanel mainPanel, Random random) {
		super();
		this.mainPanel = mainPanel;
		this.random = random;
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Options"));
		this.setLayout(new BorderLayout());
		inside = new JPanel(new FineGridLayout(1, 32));
		this.add(inside, BorderLayout.CENTER);
				
		fillExercise ();
		
		fillTreeOpt ();
		
		fillGenPop();
		
		fillSelection ();
		fillBreed ();
		fillMutation ();
		fillBloating();
		//fillInversion();
		fillReplacement();
		
		fillGenButton ();
		
	}


	private void fillExercise() {
		JPanel aux = new JPanel();
		aux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		
		aux.add(new JLabel("Exercise: MUX"));
		
		muxInP = new JPanel();
		muxInP.add(new JLabel("Nº Inputs: "));
		muxInTxtFld = new JTextField();
		muxInTxtFld.setColumns(3);
		muxInTxtFld.setText("4");
		muxInP.add(muxInTxtFld);
		muxInP.setVisible(true);
		
		aux.add(muxInP);
		
		FineGridConstraints exCons = new FineGridConstraints(0, 0, 1, 4);
		inside.add(aux, exCons);
		
	}
	
	private void fillTreeOpt() {
		JPanel aux = new JPanel();
		aux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		
		JPanel aux2 = new JPanel();
		aux2.add(new JLabel("Terminal Chance: "));
		treeTermDensTxtFld = new JTextField("0.1");
		treeTermDensTxtFld.setColumns(5);
		aux2.add(treeTermDensTxtFld);
		
		aux2.add(new JLabel("Max Depth: "));
		treeMaxDepthTxtFld = new JTextField("5");
		treeMaxDepthTxtFld.setColumns(3);
		aux2.add(treeMaxDepthTxtFld);
		
		aux.add(aux2);
		
		treeInitComboBox = new JComboBox<String>();
		treeInitComboBox.addItem("Full");
		treeInitComboBox.addItem("Grow");
		treeInitComboBox.addItem("Ramped & Half");
		treeInitComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		
		aux.add(treeInitComboBox);
		
		FineGridConstraints exCons = new FineGridConstraints(0, 4, 1, 3);
		inside.add(aux, exCons);
		
	}

	private void fillGenPop() {
		JPanel aux = new JPanel();
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		
		aux.add(fillGenerations());
		aux.add(fillPopulation());

		FineGridConstraints genCons = new FineGridConstraints(0, 7, 1, 4);
		inside.add(aux, genCons);
		
	}
	
	private JPanel fillGenerations() {
		JPanel aux = new JPanel();
		aux.add(new JLabel("Nº Gens: "));
		gensTxtFld = new JTextField();
		gensTxtFld.setColumns(5);
		gensTxtFld.setText("100");
		aux.add(gensTxtFld);
		
		return aux;
		
	}
	
	private JPanel fillPopulation() {
		JPanel aux = new JPanel();
		aux.add(new JLabel("Population size: "));
		popTxtFld = new JTextField();
		popTxtFld.setColumns(5);
		popTxtFld.setText("100");
		aux.add(popTxtFld);
		
		return aux;
		
	}
	
	private void fillSelection() {
		JPanel aux = new JPanel();
		aux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		
		JPanel aux2 = new JPanel();
		aux2.setLayout(new BoxLayout(aux2, BoxLayout.X_AXIS));
		aux2.add(new JLabel("Selection Policy: "));
		
		selectionComboBox = new JComboBox<String>();
		selectionComboBox.addItem("Roulet");
		selectionComboBox.addItem("Univ. Stochastic");
		selectionComboBox.addItem("Truncate");
		selectionComboBox.addItem("Det. Tournament");
		selectionComboBox.addItem("Prob. Tournament");
		selectionComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		aux2.add(selectionComboBox);
		
		aux.add(aux2);
		
		selectionOpt = new JPanel();
		selectionLabel = new JLabel("");
		selectionOpt.add(selectionLabel);
		selectionTxtFld = new JTextField();
		selectionTxtFld.setColumns(1);
		selectionOpt.add(selectionTxtFld);
		
		selectionExtraPanel = new JPanel();
		selectionExtraPanel.add(new JLabel("Weak: "));
		weakChance = new JTextField();
		weakChance.setColumns(6);
		selectionExtraPanel.add(weakChance);
		
		selectionOpt.add(selectionExtraPanel);
		selectionExtraPanel.setVisible(false);
		
		aux.add(selectionOpt);
		
		JPanel aux3 = new JPanel();
		dupeRmvrChckBx = new JCheckBox("Remove Dupes");
		dupeRmvrChckBx.addActionListener(new dupeActionListener ());
		dupeRmvrChckBx.setSelected(false);
		aux3.add(dupeRmvrChckBx);
		
		dupeRmvRandom = new JCheckBox("Random");
		dupeRmvRandom.setEnabled(false);
		aux3.add(dupeRmvRandom);
		
		aux.add(aux3);
		
		selectionComboBox.addActionListener(new selectionActionListener());
		selectionOpt.setVisible(false);
		
		//aux.add(aux, BorderLayout.CENTER);
		
		FineGridConstraints exCons = new FineGridConstraints(0, 11, 1, 5);
		inside.add(aux, exCons);
		
	}
	
	private void fillBreed() {
		breedPanel = new JPanel(new BorderLayout());
		
		JPanel aux1 = new JPanel(new BorderLayout());
		aux1.add(new JLabel("Simple Cross"), BorderLayout.CENTER);
		breedPanel.add(aux1,BorderLayout.NORTH);
		
		JPanel aux = new JPanel();
		aux.add(new JLabel("Breed Chance: "));
		breedChanceTxtFld = new JTextField();
		breedChanceTxtFld.setColumns(5);
		breedChanceTxtFld.setText("0.6");
		aux.add(breedChanceTxtFld);	
		
		breedPanel.add(aux, BorderLayout.CENTER);
		
		FineGridConstraints exCons = new FineGridConstraints(0, 16, 1, 2);
		inside.add(breedPanel, exCons);
		
	}
	
	private void fillMutation() {
		JPanel aux = new JPanel();
		aux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		aux.add(new JLabel("Mutation"));
		
		mutPanel = new JPanel();
		mutPanel.setLayout(new BoxLayout(mutPanel, BoxLayout.X_AXIS));
		mutPanel.add(new JLabel("Mutator: "));
		
		mutComboBox = new JComboBox<String>();
		mutComboBox.addItem("Simple Terminal");
		mutComboBox.addItem("Simple Functional");
		mutComboBox.addItem("Tree");
		mutComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		mutComboBox.addActionListener(new mutActionListener());
		mutPanel.add(mutComboBox);
		
		mutPanel.setVisible(true);
		
		aux.add(mutPanel);
		
		JPanel aux2 = new JPanel();
		aux2.add(new JLabel("Chance: "));
		mutChanceTxtFLd = new JTextField();
		mutChanceTxtFLd.setColumns(6);
		mutChanceTxtFLd.setText("0.05");
		aux2.add(mutChanceTxtFLd);
		aux.add(aux2);
		
		mutOptP = new JPanel();
		mutOptP.setLayout(new BoxLayout(mutOptP, BoxLayout.Y_AXIS));
		
		//mutLabel = new JLabel("Init: ");
		//mutOptP.add(mutLabel);
		
		JPanel aux3 = new JPanel();
		mutFullInit = new JCheckBox("Full: ");
		mutFullInit.addActionListener(new fullActionListener());
		mutFullInit.setSelected(true);
		mutGrowInit = new JCheckBox("Grow: ");
		mutGrowInit.addActionListener(new growActionListener());
		aux3.add(mutFullInit);
		aux3.add(mutGrowInit);
		mutOptP.add(aux3);
		
		aux.add(mutOptP);
		
		mutOptP.setVisible(false);
		
		FineGridConstraints exCons = new FineGridConstraints(0, 18, 1, 5);
		inside.add(aux, exCons);
		
		
	}
	
	private void fillBloating () {
		JPanel aux = new JPanel();
		aux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		
		bloatingComboBox = new JComboBox<String>();
		bloatingComboBox.addItem("Tarpeian");
		bloatingComboBox.addItem("Properly Fundamented");
		bloatingComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		bloatingComboBox.addActionListener(new bloatActionListener ());
		
		aux.add(bloatingComboBox);
		
		bloatingP = new JPanel();
		bloatingP.add(new JLabel("Death Chance: "));
		
		bloatingDeathTxtFld = new JTextField();
		bloatingDeathTxtFld.setColumns(5);
		bloatingDeathTxtFld.setText("0.5");
		bloatingP.add(bloatingDeathTxtFld);
		
		bloatingP.add(new JLabel("Modifier: "));
		bloatingModTxtFld = new JTextField();
		bloatingModTxtFld.setColumns(5);
		bloatingModTxtFld.setText("0.2");
		bloatingP.add(bloatingModTxtFld);
		
		aux.add(bloatingP);		
		
		FineGridConstraints exCons = new FineGridConstraints(0, 23, 1, 4);
		inside.add(aux, exCons);
		
	}
	
	private void fillReplacement() {
		JPanel aux = new JPanel(); 
		aux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		
		replacementCheckBox = new JCheckBox("Elitism");
		replacementCheckBox.setSelected(false);
		replacementCheckBox.addActionListener(new replacementActionListener());
		
		aux.add(replacementCheckBox);
		
		JPanel aux2 = new JPanel();
		aux2.add(new JLabel("Size: "));
		replacementTxtFld = new JTextField();
		replacementTxtFld.setColumns(5);
		replacementTxtFld.setEnabled(false);
		aux2.add(replacementTxtFld);
		
		aux.add(aux2);
		
		FineGridConstraints auxCons = new FineGridConstraints(0, 27, 1, 3);
		inside.add(aux, auxCons);
		
		
	}
	
	private void fillGenButton() {
		JPanel aux = new JPanel();
		generateButton = new JButton("Generate!");
		generateButton.addActionListener(new generateActionListener ());
		aux.add(generateButton);
		
		FineGridConstraints auxCons = new FineGridConstraints(0, 30, 1, 2);
		inside.add(aux, auxCons);
		
	}
	
	
	
	
	
	//ACTION LISTENERS =======================================================================
	private class dupeActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dupeRmvRandom.setEnabled(dupeRmvrChckBx.isSelected());
		}	
	}
	
	private class selectionActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectionComboBox.getSelectedIndex() == 2) {
				selectionLabel.setText("Threshold: ");
				selectionTxtFld.setColumns(6);
				selectionTxtFld.setText("0.5");
				selectionOpt.setVisible(true);
				selectionExtraPanel.setVisible(false);
			}
			else if (selectionComboBox.getSelectedIndex() == 3) {
				selectionLabel.setText("Nº: ");
				selectionTxtFld.setText("3");
				selectionTxtFld.setColumns(6);
				selectionOpt.setVisible(true);
				selectionExtraPanel.setVisible(false);
			}
			else if (selectionComboBox.getSelectedIndex() == 4) {
				selectionLabel.setText("Nº: ");
				selectionTxtFld.setText("3");
				selectionTxtFld.setColumns(6);
				selectionOpt.setVisible(true);
				weakChance.setText("0.1");
				selectionExtraPanel.setVisible(true);
			}
			else {
				selectionOpt.setVisible(false);
				selectionExtraPanel.setVisible(false);
			}
		}
	}
	
	private class mutActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (mutComboBox.getSelectedIndex() == 2) {				
				mutOptP.setVisible(true);
			}
			else {
				mutOptP.setVisible(false);
			}
		}	
	}
	
	private class bloatActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (bloatingComboBox.getSelectedIndex() == 0) {
				bloatingP.setVisible(true);
			}
			else {
				bloatingP.setVisible(false);
			}
		}	
	}
	
	private class fullActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mutGrowInit.setSelected(!mutFullInit.isSelected());
		}	
	}
	
	private class growActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mutFullInit.setSelected(!mutGrowInit.isSelected());
		}	
	}
	
	private class replacementActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (replacementCheckBox.isSelected()) {
				replacementTxtFld.setText("0.02");
				replacementTxtFld.setEnabled(true);
			}else {
				replacementTxtFld.setEnabled(false);
			}
		}	
	}
	
	private class generateActionListener implements ActionListener {
		
		private FitnessFunction fitnessFunction;
		
		private int select;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				
				int np = Integer.parseInt(popTxtFld.getText());
				int generations = Integer.parseInt(gensTxtFld.getText());
				
				double breedChance = Double.parseDouble(breedChanceTxtFld.getText());
				
				double mutateChance = Double.parseDouble(mutChanceTxtFLd.getText());
								
				fitnessFunction = getFitnessFunction();
								
				Translator translator = getTranslator(np);
				
				SelectionPolicy selectionPolicy = getSelectionPolicy ();
				
				BreedPolicy breedPolicy = getBreedPolicy (breedChance);
				
				Mutator mutator = getMutator (mutateChance); 
				
				ReplacementPolicy replacementPolicy = getReplacementPolicy ();
				
				Mutator inverter = getInverter ();
				
				BloatingController bloatingController = getBloatController();
				
				DuplicateRemover duplicateRemover = getDupeRemover();
								
				AGS ags = new AGS(fitnessFunction.maximize(), np, generations, translator, 
								selectionPolicy, fitnessFunction, breedPolicy, mutator, 
								bloatingController, replacementPolicy, duplicateRemover, 
								random, inverter);
				
				
				mainPanel.updateAGS(ags);
			}
			catch (NumberFormatException ex){
				System.out.println("ERROR! NumberFormatException");
				System.out.println(ex.getMessage());
			}
			catch (FileNotFoundException ex) {
				System.out.println("ERROR! FileNotFoundException");
				System.out.println(ex.getMessage());
			}
			catch (IOException ex) {
				System.out.println("ERROR! IOException");
				System.out.println(ex.getMessage());
			}
			catch (InvalidAlgorithmParameterException ex) {
				System.out.println("ERROR! Bad logarithm!");
				System.out.println(ex.getMessage());
			}
			
		}
		
		private BloatingController getBloatController () {
			BloatingController r = null;
			if (bloatingComboBox.getSelectedIndex() == 0) {
				double deathChance = Double.parseDouble(bloatingDeathTxtFld.getText());
				double modifier = Double.parseDouble(bloatingModTxtFld.getText());
				r = new Tarpeian_BloatingController(deathChance, random, modifier);
			}
			else if (bloatingComboBox.getSelectedIndex() == 1) {
				r = new ProperlyFoundedPenaly_BloatingController();
			}
			return r;
		}
		
		private FitnessFunction getFitnessFunction () throws InvalidAlgorithmParameterException {
			int inputs = Integer.parseInt(muxInTxtFld.getText());
			select = (int) Math.ceil(EvMath.log2(inputs)); 
			
			FitnessFunction r = new MuxProgram_FitnessFunction(inputs, select);
			return r;
		}

		private DuplicateRemover getDupeRemover() {
			DuplicateRemover dr = null;
			if (dupeRmvrChckBx.isSelected()) {
				if (dupeRmvRandom.isSelected()) {
					dr = new DuplicateRemover(random);
				}else {
					dr = new DuplicateRemover(null);
				}
			}
			return dr;
		}

		private Mutator getInverter() {
			return null;
		}

		private Mutator getMutator(double mutateChance) {
			Mutator mutator = null;
			
			if (mutComboBox.getSelectedIndex() == 0) {
				mutator = new Mutator_SimpleTerminal_TreeAsProgram(mutateChance, random);
			}
			else if (mutComboBox.getSelectedIndex() == 1) {
				mutator = new Mutator_SimpleFunctional_TreeAsProgram(mutateChance, random);
			}
			else if (mutComboBox.getSelectedIndex() == 2) {
				boolean fullInit = mutFullInit.isSelected();
				mutator = new Mutator_Tree_TreeAsProgram(mutateChance, fullInit, random);
			}
						
			return mutator;
		}

		private Translator getTranslator (int np) throws NumberFormatException, FileNotFoundException, IOException {
			int inputs = Integer.parseInt(muxInTxtFld.getText());
			double terminalDensity = Double.parseDouble(treeTermDensTxtFld.getText());
			int maxDepth = Integer.parseInt(treeMaxDepthTxtFld.getText());
			int initMode = treeInitComboBox.getSelectedIndex();
			int populationSize = np;
			Translator translator = new Translator_TreeAsProgram(inputs + select, terminalDensity, maxDepth, initMode, populationSize);
			return translator;
		}
		

		private SelectionPolicy getSelectionPolicy() {
			SelectionPolicy sp = null;
			
			if (selectionComboBox.getSelectedIndex() == 0) {
				sp = new SelectionPolicy_Roulet_Double(random);
			}
			else if (selectionComboBox.getSelectedIndex() == 1) {
				sp = new SelectionPolicy_UniversalStochastic_Double(random);
			}
			else if (selectionComboBox.getSelectedIndex() == 2) {
				sp = new SelectionPolicy_Truncate_Double(Double.parseDouble(selectionTxtFld.getText()));
			}
			else if (selectionComboBox.getSelectedIndex() == 3) {
				sp = new SelectionPolicy_DeterministicTournament_Double(random, Integer.parseInt(selectionTxtFld.getText()));
			}
			else if (selectionComboBox.getSelectedIndex() == 4) {
				sp = new SelectionPolicy_ProbabilisticTournament_Double(random, Integer.parseInt(selectionTxtFld.getText()), Double.parseDouble(weakChance.getText()));
			}
			
			
			return sp;
		}
		
		private BreedPolicy getBreedPolicy(double crossChance) {
			BreedPolicy bp = new BreedPolicy_SimpleCross_TreeAsProgram(random, crossChance);
			return bp;
		}
		
		private ReplacementPolicy getReplacementPolicy() {
			ReplacementPolicy rp = null;
			
			if (!replacementCheckBox.isSelected()) {
				rp = new ReplacementPolicy_TotalReplacement(); 
			}else {
				rp = new ReplacementPolicy_UnfairElitistReplacement(Double.parseDouble(replacementTxtFld.getText()));
			}
			
			return rp;
		}
	}
	
	
	
	
	@Override
	public void setGenerateEnabled(boolean enabled) {
		generateButton.setEnabled(enabled);
		
	}

}
