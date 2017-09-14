package gui.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ags.AGS;
import ags.BloatingController;
import ags.BreedPolicy;
import ags.DuplicateRemover;
import ags.Mutator;
import ags.ReplacementPolicy;
import ags.SelectionPolicy;
import ags.cromosome.FitnessFunction;
import ags.cromosome.Translator;
import ags.cromosome.implementations.genotypeTranslators.Translator_BinaryToRangedDouble;
import ags.cromosome.implementations.genotypeTranslators.Translator_PermutationAsNInt_Generic;
import ags.implementations.breedPolicies.NBinaryASInt.BreedPolicy_CrossMultiPoint_NBinaryAsInt;
import ags.implementations.breedPolicies.NBinaryASInt.BreedPolicy_CrossSinglePoint_NBinaryAsInt;
import ags.implementations.breedPolicies.NBinaryASInt.BreedPolicy_CrossUniform_NBinaryAsInt;
import ags.implementations.breedPolicies.PermutationASNInt.BreedPolicy_CrossCX_PermutationAsNInt;
import ags.implementations.breedPolicies.PermutationASNInt.BreedPolicy_CrossERX_PermutationAsNInt;
import ags.implementations.breedPolicies.PermutationASNInt.BreedPolicy_CrossOXPO_PermutationAsNInt;
import ags.implementations.breedPolicies.PermutationASNInt.BreedPolicy_CrossOXPP_PermutationAsNInt;
import ags.implementations.breedPolicies.PermutationASNInt.BreedPolicy_CrossOX_PermutationAsNInt;
import ags.implementations.breedPolicies.PermutationASNInt.BreedPolicy_CrossOrdinalCod_PermutationAsNInt;
import ags.implementations.breedPolicies.PermutationASNInt.BreedPolicy_CrossPMX_PermutationAsNInt;
import ags.implementations.mutators.NBinaryAsInt.Mutator_NBinaryAsInt;
import ags.implementations.mutators.PermutationAsNInt.Mutator_Heuristic_PermutationAsNInt;
import ags.implementations.mutators.PermutationAsNInt.Mutator_Insert_PermutationAsNInt;
import ags.implementations.mutators.PermutationAsNInt.Mutator_Inversion_PermutationAsNInt;
import ags.implementations.mutators.PermutationAsNInt.Mutator_InvertSpecial_PermutationAsNInt;
import ags.implementations.mutators.PermutationAsNInt.Mutator_Swap_PermutationAsNInt;
import ags.implementations.replacementPolicies.ReplacementPolicy_TotalReplacement;
import ags.implementations.replacementPolicies.ReplacementPolicy_UnfairElitistReplacement;
import ags.implementations.selectionPolicies.SelectionPolicy_DeterministicTournament_Double;
import ags.implementations.selectionPolicies.SelectionPolicy_ProbabilisticTournament_Double;
import ags.implementations.selectionPolicies.SelectionPolicy_Roulet_Double;
import ags.implementations.selectionPolicies.SelectionPolicy_Truncate_Double;
import ags.implementations.selectionPolicies.SelectionPolicy_UniversalStochastic_Double;
import gui.MainPanel;
import GUI.Utils.layout.*;
import prac1Specific.*;
import prac2Specific.QuadraticAssignment_FitnessFunction;
import utils.EvMath;

@SuppressWarnings("serial")
public class Pr1_Pr2_MainOptionsPanel extends MainOptionsPanel{
	
	private MainPanel mainPanel;
	
	private Random random;

	private JPanel inside;
	
	private JComboBox<FitnessFunction> fitnessComboBox;
	
	private JTextField ex4TxtFld;
	private JPanel ex4OptP;
	
	private JPanel pr2OptP;
	private String pr2File;
	private JLabel pr2FileName;
	
	private JTextField gensTxtFld;
	private JTextField popTxtFld;
	private JPanel accuPanel;
	private JTextField accuTxtFld;
	
	private int selectionState;
	private JComboBox<String> selectionComboBox;
	private JPanel selectionOpt;
	private JLabel selectionLabel;
	private JTextField selectionTxtFld;
	private JPanel selectionExtraPanel;
	private JTextField weakChance;
	private JCheckBox dupeRmvrChckBx;
	private JCheckBox dupeRmvRandom;
	
	private JPanel breedPanel;
	private JPanel pr1BreedPanel;
	private JPanel pr2BreedPanel;
	private JComboBox<String> breedComboBox;
	private JComboBox<String> breed2ComboBox;
	private JPanel breedTxtP;
	private JTextField breedChanceTxtFld;
	private JPanel breedOpt;
	private JLabel breedLabel;	
	private JTextField breedOptTxtFld;
	
	private JPanel mutPanel;
	private JComboBox<String> mutComboBox;
	private JPanel mutOptp;
	private JLabel mutLabel;
	private JTextField mutChanceTxtFLd;
	private JTextField mutOptTxtFld;
	
	private JCheckBox inversionCheckBox;
	private JTextField inversionTxtFld;
	
	private JCheckBox replacementCheckBox;
	private JTextField replacementTxtFld;
	
	private JButton generateButton;
	
	public Pr1_Pr2_MainOptionsPanel(MainPanel mainPanel, Random random) {
		super();
		this.mainPanel = mainPanel;
		this.random = random;
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Options"));
		this.setLayout(new BorderLayout());
		inside = new JPanel(new FineGridLayout(1, 32));
		this.add(inside, BorderLayout.CENTER);
		pr2File = "";
		
		fillExercise ();
		
		fillGenPopAccu();
		
		fillSelection ();
		fillBreed ();
		fillMutation ();
		fillInversion();
		fillReplacement();
		
		fillGenButton ();
		
	}


	private void fillExercise() {
		JPanel aux = new JPanel();
		aux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		
		aux.add(new JLabel("Exercise: "));
		
		fitnessComboBox = new JComboBox<FitnessFunction>();
		fitnessComboBox.addItem(new Ex1_FitnessFunction());
		fitnessComboBox.addItem(new Ex2_FitnessFunction());
		fitnessComboBox.addItem(new Ex3_FitnessFunction());
		fitnessComboBox.addItem(new Ex4_FitnessFunction());
		fitnessComboBox.addItem(new Ex5_FitnessFunction());
		fitnessComboBox.addItem(new QuadraticAssignment_FitnessFunction());
		fitnessComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		aux.add(fitnessComboBox);
		
		ex4OptP = new JPanel();
		ex4OptP.add(new JLabel("Value n: "));
		ex4TxtFld = new JTextField();
		ex4TxtFld.setColumns(3);
		ex4OptP.add(ex4TxtFld);
		ex4OptP.setVisible(false);
		
		aux.add(ex4OptP);
		
		pr2OptP = new JPanel();
		pr2FileName = new JLabel("");
		pr2OptP.add(pr2FileName);
		JButton fileChooser = new JButton("Choose File");
		fileChooser.addActionListener(new fileChooserActionListener());
		pr2OptP.add(fileChooser);
		pr2OptP.setVisible(false);
		
		aux.add(pr2OptP);
				
		fitnessComboBox.addActionListener(new fitnessActionListener());
		
		
		FineGridConstraints exCons = new FineGridConstraints(0, 0, 1, 4);
		inside.add(aux, exCons);
		
	}
	
	private void fillGenPopAccu () {
		JPanel aux = new JPanel();
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		
		aux.add(fillGenerations());
		aux.add(fillPopulation());
		fillPrecision();
		aux.add(accuPanel);

		FineGridConstraints genCons = new FineGridConstraints(0, 4, 1, 5);
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
	
	private void fillPrecision() {
		accuPanel = new JPanel();
		accuPanel.add(new JLabel("Precision: "));
		accuTxtFld = new JTextField();
		accuTxtFld.setColumns(10);
		accuTxtFld.setText("0.001");
		accuPanel.add(accuTxtFld);
		
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
		
		FineGridConstraints exCons = new FineGridConstraints(0, 9, 1, 5);
		inside.add(aux, exCons);
		
	}
	
	private void fillBreed() {
		breedPanel = new JPanel(new BorderLayout());
		
		pr1BreedPanel = new JPanel();
		pr1BreedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pr1BreedPanel.setLayout(new BoxLayout(pr1BreedPanel, BoxLayout.Y_AXIS));
		
		JPanel aux11 = new JPanel();
		aux11.setLayout(new BoxLayout(aux11, BoxLayout.X_AXIS));
		
		aux11.add(new JLabel("Breed Policy: "));
		
		breedComboBox = new JComboBox<String>();
		breedComboBox.addItem("Single Pnt.");
		breedComboBox.addItem("Multi Pnt.");
		breedComboBox.addItem("Uniform");
		breedComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		breedComboBox.addActionListener(new breedActionListener());
		aux11.add(breedComboBox);
		pr1BreedPanel.add(aux11);
		
		breedTxtP = new JPanel();
		breedTxtP.add(new JLabel("Chance"));
		breedChanceTxtFld = new JTextField();
		breedChanceTxtFld.setColumns(5);
		breedChanceTxtFld.setText("0.6");
		breedTxtP.add(breedChanceTxtFld);
		
		pr1BreedPanel.add(breedTxtP);
		
		breedOpt = new JPanel();
		breedLabel = new JLabel("");
		breedOpt.add(breedLabel);
		breedOptTxtFld = new JTextField();
		breedOptTxtFld.setColumns(5);
		breedOpt.add(breedOptTxtFld);
		
		pr1BreedPanel.add(breedOpt);
				
		breedOpt.setVisible(false);
		
		breedPanel.add(pr1BreedPanel, BorderLayout.CENTER);
		
		//Make pr2BreedPanel
		pr2BreedPanel = new JPanel();
		pr2BreedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pr2BreedPanel.setLayout(new BoxLayout(pr2BreedPanel, BoxLayout.Y_AXIS));
		
		JPanel aux21 = new JPanel();
		aux21.setLayout(new BoxLayout(aux21, BoxLayout.X_AXIS));
		
		aux21.add(new JLabel("Breed Policy: "));
		
		breed2ComboBox = new JComboBox<String>();
		breed2ComboBox.addItem("PMX");
		breed2ComboBox.addItem("OX");
		breed2ComboBox.addItem("OX Orden Prioritario");
		breed2ComboBox.addItem("OX Posiciones Prioritarias");
		breed2ComboBox.addItem("CX");
		breed2ComboBox.addItem("ERX");
		breed2ComboBox.addItem("Codificación Ordinal");
		breed2ComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		breed2ComboBox.addActionListener(new breed2ActionListener());
		aux21.add(breed2ComboBox);
		pr2BreedPanel.add(aux21);
		
		pr2BreedPanel.setVisible(false);
		
		FineGridConstraints exCons = new FineGridConstraints(0, 14, 1, 4);
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
		mutComboBox.addItem("Insertion");
		mutComboBox.addItem("Swap");
		mutComboBox.addItem("Inversion");
		mutComboBox.addItem("Heuristic");
		mutComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
		mutComboBox.addActionListener(new mutActionListener());
		mutPanel.add(mutComboBox);
		
		mutPanel.setVisible(false);
		
		aux.add(mutPanel);
		
		JPanel aux2 = new JPanel();
		aux2.add(new JLabel("Chance: "));
		mutChanceTxtFLd = new JTextField();
		mutChanceTxtFLd.setColumns(6);
		mutChanceTxtFLd.setText("0.05");
		aux2.add(mutChanceTxtFLd);
		aux.add(aux2);
		
		mutOptp = new JPanel();
		mutLabel = new JLabel("Rate: ");
		mutOptp.add(mutLabel);
		mutOptTxtFld = new JTextField();
		mutOptTxtFld.setColumns(6);
		mutOptTxtFld.setText("0.5");
		mutOptp.add(mutOptTxtFld);
		aux.add(mutOptp);
		
		FineGridConstraints exCons = new FineGridConstraints(0, 18, 1, 6);
		inside.add(aux, exCons);
		
		
	}
	
	private void fillInversion () {
		JPanel aux = new JPanel(); 
		aux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		aux.setLayout(new BoxLayout(aux, BoxLayout.Y_AXIS));
		
		inversionCheckBox = new JCheckBox("Inversion");
		inversionCheckBox.setSelected(false);
		inversionCheckBox.addActionListener(new inversionActionListener());
		
		aux.add(inversionCheckBox);
		
		JPanel aux2 = new JPanel();
		aux2.add(new JLabel("Chance: "));
		inversionTxtFld = new JTextField();
		inversionTxtFld.setColumns(5);
		inversionTxtFld.setEnabled(false);
		aux2.add(inversionTxtFld);
		
		aux.add(aux2);
		
		FineGridConstraints auxCons = new FineGridConstraints(0, 24, 1, 3);
		inside.add(aux, auxCons);
		
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
	
	private class fitnessActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			/*Selection State:
			 * 0 = Standard, no extra panels
			 * 1 = Pr1Ex4 optPanel
			 * 2 = Pr2 optPanel
			*/
			
			if (fitnessComboBox.getSelectedIndex() == 3) {
				if (selectionState == 2) {
					pr2OptP.setVisible(false);
					pr2BreedPanel.setVisible(false);
					pr1BreedPanel.setVisible(true);
					breedPanel.removeAll();
					breedPanel.add(pr1BreedPanel, BorderLayout.CENTER);
					
					pr2BreedPanel.remove(breedTxtP);
					pr2BreedPanel.remove(breedOpt);
					pr1BreedPanel.add(breedTxtP);
					pr1BreedPanel.add(breedOpt);
										
					breedPanel.revalidate();
					
					breedComboBox.setSelectedIndex(0);
					mutPanel.setVisible(false);
					mutOptp.setVisible(true);
					mutLabel.setText("Rate: ");
					mutOptTxtFld.setText("0.5");
					accuPanel.setVisible(true);
					accuTxtFld.setText("0.001");
				}
				
				
				ex4TxtFld.setText("1");
				ex4OptP.setVisible(true);
				
				selectionState = 1;
			}
			else if (fitnessComboBox.getSelectedIndex() == 5) {
				if (selectionState == 0 || selectionState == 1) {
					if (selectionState == 1) {
						ex4OptP.setVisible(false);
					}
					pr1BreedPanel.setVisible(false);
					pr2BreedPanel.setVisible(true);
					breedPanel.removeAll();
					breedPanel.add(pr2BreedPanel, BorderLayout.CENTER);
					
					pr1BreedPanel.remove(breedTxtP);
					pr1BreedPanel.remove(breedOpt);
					pr2BreedPanel.add(breedTxtP);
					pr2BreedPanel.add(breedOpt);
					
					breedPanel.revalidate();
					
					breed2ComboBox.setSelectedIndex(0);
					mutPanel.setVisible(true);
					mutComboBox.setSelectedIndex(0);
					accuPanel.setVisible(false);
				}
				
				
				pr2OptP.setVisible(true);
				selectionState = 2;
			}
			else {
				if (selectionState == 1) {
					ex4OptP.setVisible(false);
				}
				else if (selectionState == 2) {
					pr2OptP.setVisible(false);
					pr2BreedPanel.setVisible(false);
					pr1BreedPanel.setVisible(true);
					breedPanel.removeAll();
					breedPanel.add(pr1BreedPanel, BorderLayout.CENTER);
					
					pr2BreedPanel.remove(breedTxtP);
					pr2BreedPanel.remove(breedOpt);
					pr1BreedPanel.add(breedTxtP);
					pr1BreedPanel.add(breedOpt);
					
					breedPanel.revalidate();
					
					breedComboBox.setSelectedIndex(0);
					mutPanel.setVisible(false);
					mutOptp.setVisible(true);
					mutLabel.setText("Rate: ");
					mutOptTxtFld.setText("0.5");
					accuPanel.setVisible(true);
					accuTxtFld.setText("0.001");
				}
								
				selectionState = 0;
			}			
		}	
	}
	
	private class fileChooserActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser("./src/resources");
			int r = fc.showOpenDialog(Pr1_Pr2_MainOptionsPanel.this);
			if (r == JFileChooser.APPROVE_OPTION) {
				pr2File = fc.getSelectedFile().getAbsolutePath();
				pr2FileName.setText(fc.getSelectedFile().getName());
			}
		}	
	}
	
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
	
	private class breedActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (breedComboBox.getSelectedIndex() == 1) {
				breedLabel.setText("Points: ");
				breedOptTxtFld.setText("3");
				breedOpt.setVisible(true);
			}else if (breedComboBox.getSelectedIndex() == 2) {
				breedLabel.setText("Rate: ");
				breedOptTxtFld.setText("0.5");
				breedOpt.setVisible(true);
			}else {
				breedOpt.setVisible(false);
			}
		}	
	}
	
	private class breed2ActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (breed2ComboBox.getSelectedIndex() == 2) {
				breedLabel.setText("Positions: ");
				breedOptTxtFld.setText("3");
				breedOpt.setVisible(true);
			}else if (breed2ComboBox.getSelectedIndex() == 3) {
				breedLabel.setText("Points: ");
				breedOptTxtFld.setText("4");
				breedOpt.setVisible(true);
			}else {
				breedOpt.setVisible(false);
			}
		}	
	}
	
	private class mutActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (mutComboBox.getSelectedIndex() == 0) {
				mutLabel.setText("Points: ");
				mutOptTxtFld.setText("3");
				mutOptp.setVisible(true);
			}else if (mutComboBox.getSelectedIndex() == 1) {
				mutLabel.setText("Pairs: ");
				mutOptTxtFld.setText("3");
				mutOptp.setVisible(true);
			}else if (mutComboBox.getSelectedIndex() == 3) {
				mutLabel.setText("N: ");
				mutOptTxtFld.setText("3");
				mutOptp.setVisible(true);
			}else {
				mutOptp.setVisible(false);
			}
		}	
	}
	
	private class inversionActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (inversionCheckBox.isSelected()) {
				inversionTxtFld.setText("0.05");
				inversionTxtFld.setEnabled(true);
			}else {
				inversionTxtFld.setEnabled(false);
			}
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
		
		private int maxPop;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				double precision = Double.parseDouble(accuTxtFld.getText());
				
				int np = Integer.parseInt(popTxtFld.getText());
				int generations = Integer.parseInt(gensTxtFld.getText());
				
				double breedChance = Double.parseDouble(breedChanceTxtFld.getText());
				
				double mutateChance = Double.parseDouble(mutChanceTxtFLd.getText());
								
				fitnessFunction = (FitnessFunction) fitnessComboBox.getSelectedItem();
								
				Translator translator = getTranslator(precision);
				
				SelectionPolicy selectionPolicy = getSelectionPolicy ();
				
				BreedPolicy breedPolicy = getBreedPolicy (breedChance);
				
				Mutator mutator = getMutator (mutateChance); 
				
				ReplacementPolicy replacementPolicy = getReplacementPolicy ();
				
				Mutator inverter = getInverter ();
				
				BloatingController bloatingController = null;
				
				DuplicateRemover duplicateRemover = getDupeRemover();
				if (duplicateRemover != null && !EvMath.smallerThanFactorial(maxPop, np)) {
					throw new IndexOutOfBoundsException();
				}
				
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
			catch (IndexOutOfBoundsException ex) {
				System.out.println("ERROR! Population is too big to not allow dupes!");
				System.out.println(ex.getMessage());
			}
			
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
			Mutator res = null;
			if (fitnessComboBox.getSelectedIndex() == 5 && inversionCheckBox.isSelected()) {
				double mutateChance = Double.parseDouble(mutChanceTxtFLd.getText());
				res = new Mutator_InvertSpecial_PermutationAsNInt(mutateChance, random, fitnessFunction.maximize());
			}
			return res;
		}

		private Mutator getMutator(double mutateChance) {
			Mutator mutator = null;

			if (fitnessComboBox.getSelectedIndex() != 5) {
				double mutateRate = Double.parseDouble(mutOptTxtFld.getText());
				mutator = new Mutator_NBinaryAsInt(mutateRate, mutateChance, random);
			}
			else {
				if (mutComboBox.getSelectedIndex() == 0) {
					int points = Integer.parseInt(mutOptTxtFld.getText());
					mutator = new Mutator_Insert_PermutationAsNInt(mutateChance, random, points);
				}
				else if (mutComboBox.getSelectedIndex() == 1) {
					int points = Integer.parseInt(mutOptTxtFld.getText());
					mutator = new Mutator_Swap_PermutationAsNInt(mutateChance, random, points);
				}
				else if (mutComboBox.getSelectedIndex() == 2) {
					mutator = new Mutator_Inversion_PermutationAsNInt(mutateChance, random);
				}
				else if (mutComboBox.getSelectedIndex() == 3) {
					int points = Integer.parseInt(mutOptTxtFld.getText());
					mutator = new Mutator_Heuristic_PermutationAsNInt(mutateChance, random, points, fitnessFunction.maximize());
				} 
			}
			
			return mutator;
		}

		private Translator getTranslator (double precision) throws NumberFormatException, FileNotFoundException, IOException {
			int N = 2;
			Translator translator = null;
			int exSel = fitnessComboBox.getSelectedIndex(); 
			if (exSel == 0) {
				N = 1;
				translator = new Translator_BinaryToRangedDouble(N, precision,Ex1_FitnessFunction.getMins(), Ex1_FitnessFunction.getMaxs());
			}
			else if (exSel == 1) {
				N = 2;
				translator = new Translator_BinaryToRangedDouble(N, precision,Ex2_FitnessFunction.getMins(), Ex2_FitnessFunction.getMaxs());
			}
			else if (exSel == 2) {
				N = 2;
				translator = new Translator_BinaryToRangedDouble(N, precision,Ex3_FitnessFunction.getMins(), Ex3_FitnessFunction.getMaxs());
			}
			else if (exSel == 3) {
				N = Integer.parseInt(ex4TxtFld.getText());
				translator = new Translator_BinaryToRangedDouble(N, precision,Ex4_FitnessFunction.getMins(N), Ex4_FitnessFunction.getMaxs(N));
				
			}
			else if (exSel == 4) {
				N = 2;
				translator = new Translator_BinaryToRangedDouble(N, precision,Ex5_FitnessFunction.getMins(), Ex5_FitnessFunction.getMaxs());
			}
			else if (exSel == 5) {
				QuadraticAssignment_FitnessFunction ff = (QuadraticAssignment_FitnessFunction) fitnessFunction;
				ff.createFromFile(pr2File);
				N = ff.getN();
				translator = new Translator_PermutationAsNInt_Generic(N);
				maxPop = N;
			}
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
			BreedPolicy bp = null;
			if (fitnessComboBox.getSelectedIndex() != 5) {
				if (breedComboBox.getSelectedIndex() == 0) {
					bp = new BreedPolicy_CrossSinglePoint_NBinaryAsInt(random, crossChance);
				}
				else if (breedComboBox.getSelectedIndex() == 1) {
					bp = new BreedPolicy_CrossMultiPoint_NBinaryAsInt(random, crossChance, Integer.parseInt(breedOptTxtFld.getText()));
				}
				else if (breedComboBox.getSelectedIndex() == 2) {
					bp = new BreedPolicy_CrossUniform_NBinaryAsInt(random, crossChance, Double.parseDouble(breedOptTxtFld.getText()));
				}
			}
			
			else {
				if (breed2ComboBox.getSelectedIndex() == 0) {
					bp = new BreedPolicy_CrossPMX_PermutationAsNInt(random, crossChance);
				}
				else if (breed2ComboBox.getSelectedIndex() == 1) {
					bp = new BreedPolicy_CrossOX_PermutationAsNInt(random, crossChance);
				}
				else if (breed2ComboBox.getSelectedIndex() == 2) {
					bp = new BreedPolicy_CrossOXPO_PermutationAsNInt(random, crossChance, Integer.parseInt(breedOptTxtFld.getText()));
				}
				else if (breed2ComboBox.getSelectedIndex() == 3) {
					bp = new BreedPolicy_CrossOXPP_PermutationAsNInt(random, crossChance, Integer.parseInt(breedOptTxtFld.getText()));
				}
				else if (breed2ComboBox.getSelectedIndex() == 4) {
					bp = new BreedPolicy_CrossCX_PermutationAsNInt(random, crossChance);
				}
				else if (breed2ComboBox.getSelectedIndex() == 5) {
					bp = new BreedPolicy_CrossERX_PermutationAsNInt(random, crossChance);
				}
				else if (breed2ComboBox.getSelectedIndex() == 6) {
					bp = new BreedPolicy_CrossOrdinalCod_PermutationAsNInt(random, crossChance);
				}
			}
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
	public void setGenerateEnabled (boolean enabled) {
		generateButton.setEnabled(enabled);
	}
}
