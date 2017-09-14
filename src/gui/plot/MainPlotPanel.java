package gui.plot;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ags.AGS;
import ags.cromosome.Cromosome;
import ags.cromosome.Fitness;
import ags.cromosome.implementations.fitnesses.Fitness_Double;
import GUI.Utils.components.PlotPanel;
import gui.observers.PopulationObserver;



@SuppressWarnings("serial")
public class MainPlotPanel extends JPanel implements PopulationObserver{
		
	private PlotPanel linesPanel;

	private int popSize;
	private boolean maximize;
	
	
	public MainPlotPanel() {
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Evolution Plot"));
		this.setLayout(new BorderLayout());
		linesPanel = new PlotPanel(100, 0, 1, "Generations", "Fitness");
		this.add(linesPanel, BorderLayout.CENTER);
	}
	
	public void updateAGS (AGS ags) {
		ags.addPopulationObserver(this);
		maximize = ags.isMaximize();
		resetPlot();		
		
	}
	
	private void resetPlot () {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				invalidate();
				
				removeAll();
				linesPanel = new PlotPanel(100, 100, 0, 1, "Generations", "Fitness");
				add(linesPanel, BorderLayout.CENTER);
				linesPanel.addLine("CGB", Color.RED, maximize, Color.BLUE, false);//Current Gen Best
				linesPanel.addLine("CGA", Color.GREEN);//Current Gen Average
				
				validate();
				repaint();
				
			}
		});
	}
	
	

	@Override
	public void updatePopulation(Cromosome [] population, int size) {	
	}

	@Override
	public void updatePopulationStats(boolean init, Fitness ATBest, Fitness CGBest, Fitness CGAvg) {
		if (init) {
			resetPlot();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				//linesPanel.addPoint("ATB", ((Fitness_Double)ATBest).getValue());
				linesPanel.addPoint("CGB", ((Fitness_Double)CGBest).getValue());
				linesPanel.addPoint("CGA", ((Fitness_Double)CGAvg).getValue());				
			}
		});
		try {
			Thread.sleep((long) (Math.round((double) popSize)/9));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
