package prac2Specific;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ags.cromosome.Fenotype;
import ags.cromosome.Fitness;
import ags.cromosome.FitnessFunction;
import ags.cromosome.implementations.fenotypes.Fenotype_PermutationAsNInt;
import ags.cromosome.implementations.fitnesses.Fitness_Double;

public class QuadraticAssignment_FitnessFunction implements FitnessFunction{

	private int N;
	private int [][] F;
	private int [][] D;
	
	public QuadraticAssignment_FitnessFunction() {
	}
	
	public QuadraticAssignment_FitnessFunction(int N, int [][] F, int [][] D) {
		this.N = N;
		this.F = F;
		this.D = D;
	}
	
	public int getN () {
		return N;
	}
	
	public void createFromFile (String file) throws FileNotFoundException, IOException, NumberFormatException {
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			N = Integer.parseInt(line.split(" ")[0]);
			br.readLine();
			String [] aux;
			D = new int [N][N];
			for (int i = 0; i < N; i++) {
				line = br.readLine();
				aux = line.split(" ");
				for (int j = 0; j < N; j++) {
					D [i][j] = Integer.parseInt(aux [j]);
				}
			}
			br.readLine();
			
			F = new int [N][N];
			for (int i = 0; i < N; i++) {
				line = br.readLine();
				aux = line.split(" ");
				for (int j = 0; j < N; j++) {
					F [i][j] = Integer.parseInt(aux [j]);
				}
			}
			
			br.close();
			
		    
		}
	}
	
	
	@Override
	public Fitness evaluate(Fenotype fenotype) {
		Fenotype_PermutationAsNInt f = (Fenotype_PermutationAsNInt) fenotype;
		int res = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N;j ++) {
				res += F [i][j] * D[f.getValue(i)][f.getValue(j)];
			}
		}
		
		return new Fitness_Double(res);
	}

	@Override
	public boolean maximize() {
		return false;
	}
	
	@Override
	public String toString() {
		return "Práctica 2 - Asignación Cuadrática";
	}



}
