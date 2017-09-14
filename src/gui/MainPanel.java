package gui;


import java.util.Random;

import javax.swing.JPanel;

import ags.AGS;
import gui.controls.MainControlsPanel;
import GUI.Utils.layout.*;
import gui.options.MainOptionsPanel;
import gui.options.Pr3_MainOptionsPanel;
import gui.plot.MainPlotPanel;
import gui.population.MainPopulationPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	
	private MainOptionsPanel optP;
	private MainPlotPanel plotP;
	private MainPopulationPanel popP;
	private MainControlsPanel conP;
	
	private Random random;
		
	public MainPanel () {
		super();
		random = new Random();
		this.setLayout(new FineGridLayout(5,5));
		
		
		FineGridConstraints optCons = new FineGridConstraints(0, 0, 1, 5);
		optP = new Pr3_MainOptionsPanel(this, random);
		this.add(optP, optCons);
		
		
		FineGridConstraints plotCons = new FineGridConstraints(1, 0, 4, 3);
		plotP = new MainPlotPanel();
		this.add(plotP,plotCons);
		
		FineGridConstraints popCons = new FineGridConstraints(1, 3, 3, 2);
		popP = new MainPopulationPanel();
		this.add (popP, popCons);
		
		FineGridConstraints controlsCons = new FineGridConstraints(4, 3, 1, 2);
		conP = new MainControlsPanel(this);
		this.add (conP, controlsCons);
	
	}	
	
	public void updateAGS (AGS ags) {
		conP.updateAGS(ags);
		plotP.updateAGS(ags);
		popP.updateAGS(ags);
	}

	public void busy(boolean b) {
		optP.setGenerateEnabled(!b);
		
	}

}
