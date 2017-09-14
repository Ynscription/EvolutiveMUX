package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{

	private Container mainPanel;
	
	public MainWindow () throws HeadlessException{
		//Titulo de la ventana
		super ("PEV Practica 1");
		
		//Calculamos la dimension de la ventana de acuerdo a la pantalla
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int taskBarSize = scnMax.bottom;
		Dimension screenDimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		screenDimension.height = screenDimension.height - taskBarSize;

		this.setLocation((int)(screenDimension.width*0.25/2), (int)(screenDimension.height*0.25/2));
		
		screenDimension.height = (int) (screenDimension.height*0.75);
		screenDimension.width = (int) (screenDimension.width*0.75);
		
		//Establecemos parametros
		this.setSize(screenDimension);
		this.setResizable(true);
		
		//dividimos el contenedor
		this.getContentPane().setLayout(new BorderLayout());
		
		//TODO make this and populate it
		mainPanel = new MainPanel();
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		/*
		actionButtonP = new ActionButtonP(dc);
		mainPanel.add (actionButtonP, BorderLayout.NORTH);
		generalP = new GeneralP(dc);
		mainPanel.add (generalP,BorderLayout.CENTER);
		stateP = new StateP();
		mainPanel.add (stateP,BorderLayout.SOUTH);
		*/
		
		
		//Mostramos la ventana
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	

}
