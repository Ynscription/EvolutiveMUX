package gui.controls;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ags.AGS;
import gui.MainPanel;
import gui.observers.DoneObserver;

@SuppressWarnings("serial")
public class MainControlsPanel extends JPanel implements DoneObserver{
	
	private MainPanel mainPanel;
	
	private AGS ags;

	private JButton initButton;
	private JButton stepButton;
	private JButton runButton;
	private JButton stopButton;

	public MainControlsPanel(MainPanel mainPanel) {
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Controls"));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.mainPanel = mainPanel;
		
		initButton = new JButton("Initialize");
		initButton.addActionListener(new initActionListener());
		this.add(initButton);
		
		stepButton = new JButton("Step");
		stepButton.addActionListener(new stepActionListener());
		this.add(stepButton);
		
		runButton = new JButton("Run");
		runButton.addActionListener(new runActionListener());
		this.add(runButton);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(new stopActionListener());
		this.add(stopButton);

		initButton.setEnabled(false);
		stepButton.setEnabled(false);
		runButton.setEnabled(false);
		stopButton.setEnabled(false);
		
	}
	
	public void updateAGS (AGS ags) {
		this.ags = ags;
		ags.addDoneObserver(this);
		initButton.setEnabled(true);
		stepButton.setEnabled(false);
		runButton.setEnabled(false);
		stopButton.setEnabled(false);
	}
	
	private class initActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ags!= null) {
				mainPanel.busy (true);
				initButton.setEnabled(false);
				stepButton.setEnabled(false);
				runButton.setEnabled(false);
				stopButton.setEnabled(false);
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {

						ags.initializePopulation();
						
					}
				});
				
				t.start();
			}
		}		
	}
	
	private class stepActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ags != null) {
				mainPanel.busy (true);
				initButton.setEnabled(false);
				stepButton.setEnabled(false);
				runButton.setEnabled(false);
				stopButton.setEnabled(false);
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {

						ags.step();
						
					}
				});
				t.start();
				
				
			}			
		}		
	}
	private class runActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ags!= null) {
				mainPanel.busy (true);
				initButton.setEnabled(false);
				stepButton.setEnabled(false);
				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						ags.run();
						
					}
				});
				t.start();
				
			}
		}		
	}
	
	private class stopActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ags!= null) {
				initButton.setEnabled(false);
				stepButton.setEnabled(false);
				runButton.setEnabled(false);
				stopButton.setEnabled(false);
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						ags.stop();
						
					}
				});
				t.start();
				
			}
		}
		
	}
	
	@Override
	public void done(String op) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if (op.equals("INIT")) {
					initButton.setEnabled(true);
					stepButton.setEnabled(true);
					runButton.setEnabled(true);
				}else if (op.equals("STEP")) {
					initButton.setEnabled(true);
					stepButton.setEnabled(true);
					runButton.setEnabled(true);
				}else if (op.equals("RUN")) {
					initButton.setEnabled(true);
					stepButton.setEnabled(false);
					runButton.setEnabled(false);
					stopButton.setEnabled(false);
					
					ags.printStats();
				}
				mainPanel.busy(false);
			}
		});
		
	}
}
