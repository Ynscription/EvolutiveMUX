package gui.population;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import ags.AGS;
import ags.cromosome.Cromosome;
import ags.cromosome.Fitness;
import ags.cromosome.implementations.genotypes.Genotype_NBinaryAsInt;
import GUI.Utils.components.GenotypeVisualizer;
import gui.observers.PopulationObserver;

@SuppressWarnings("serial")
public class MainPopulationPanel extends JPanel implements PopulationObserver{
	
	private JPanel inside;
	
	private AGS ags;
	
	public MainPopulationPanel() {
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Population"));
		this.setLayout(new BorderLayout());
		inside = new JPanel();
		inside.setLayout(new BoxLayout(inside, BoxLayout.Y_AXIS));
		
		JScrollPane sp = new JScrollPane(inside);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.getVerticalScrollBar().setUnitIncrement(10);
		sp.getHorizontalScrollBar().setUnitIncrement(10);
		this.add(sp, BorderLayout.CENTER);
		
		
	}
	
	public void updateAGS (AGS ags) {
		this.ags = ags;
		ags.addPopulationObserver(this);
	}

	@Override
	public void updatePopulationStats(boolean init, Fitness ATBest, Fitness CGBest, Fitness CGAvg) {
	}

	@Override
	public void updatePopulation(Cromosome[] population, int size) {
		
		SwingUtilities.invokeLater(new Runnable() {
			private int fenoSize;
			private int fitSize;
			
			private JPanel paintCromosome (Cromosome c, int pos) {
				JPanel aux = new JPanel();
				aux.setLayout(new BoxLayout(aux, BoxLayout.X_AXIS));
				
				if (pos != -1) {
					JLabel posLabel = new JLabel("" + pos);
					aux.add(posLabel);
					int separation = 40 - getFontMetrics(posLabel.getFont()).stringWidth(posLabel.getText());
					aux.add(Box.createRigidArea(new Dimension(separation, 30)));
				}
				else {
					aux.add(Box.createRigidArea(new Dimension(40, 30)));
				}
				if (c.getGenotype() instanceof Genotype_NBinaryAsInt) {
					Genotype_NBinaryAsInt g = (Genotype_NBinaryAsInt) c.getGenotype();
					
					int values [] = g.getValues();
					int lengths [] = g.getLengths();
					
					aux.add(new GenotypeVisualizer(values, lengths, g.getN()));
					
				}else {
					JLabel l = new JLabel("Genotype: " + c.getGenotype());
					l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
					aux.add(l);
				}
				aux.add(Box.createRigidArea(new Dimension(12, 30)));
				
				JLabel l = new JLabel("Fenotype:   " + c.getFenotype());
				l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				int size = getFontMetrics(l.getFont()).stringWidth(l.getText());
				
				if (fenoSize == -1) {
					fenoSize = (int) (size + 30);
				}
				aux.add(l);
				aux.add(Box.createRigidArea(new Dimension(fenoSize - size, 30)));
				
				JLabel l2 = new JLabel("Fitness: " + c.getFitness());
				l2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				int size2 = getFontMetrics(l2.getFont()).stringWidth(l2.getText());
				
				//if (fitSize == -1) {
					fitSize = (int) (size2 + 30);
				//}
				aux.add(l2);
				aux.add(Box.createRigidArea(new Dimension(fitSize - size2, 30)));
				
				JPanel aux2 = new JPanel(new BorderLayout());
				aux2.add(aux, BorderLayout.WEST);
				
				return aux2;
			}
			
			@Override
			public void run() {
				invalidate();
				
				inside.removeAll();
				
				fenoSize = -1;
				fitSize = -1;
				
				JPanel bestAux = new JPanel();
				bestAux.setLayout(new BoxLayout(bestAux, BoxLayout.Y_AXIS));
				JPanel label1Aux = new JPanel(new BorderLayout());
				JLabel bestLabel = new JLabel("All-Time Best");
				bestLabel.setFont(bestLabel.getFont().deriveFont(18f));
				label1Aux.add(bestLabel, BorderLayout.WEST);
				bestAux.add(label1Aux);
				bestAux.add(paintCromosome(ags.bestCromosome(), -1));
				inside.add(bestAux);
				
				
				
				JPanel popAux = new JPanel();
				popAux.setLayout(new BoxLayout(popAux, BoxLayout.Y_AXIS));
				JPanel label2Aux = new JPanel(new BorderLayout());
				JLabel popLabel = new JLabel("Population");
				popLabel.setFont(popLabel.getFont().deriveFont(18f));
				label2Aux.add(popLabel, BorderLayout.WEST);
				popAux.add(label2Aux);
				if (ags.isMaximize()) {
					for (int i = size - 1; i >= 0; i--) {
						popAux.add(paintCromosome(population[i], size - i));
					}
				}
				else {
					for (int i = 0; i < size; i++) {
						popAux.add(paintCromosome(population[i], i + 1));
					}
				}
				inside.add(popAux);
				
				validate();
				repaint();
			}
		});
		
		try {
			Thread.sleep((long) (Math.round((double) ags.popSize())/9));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}
