package ags.cromosome.implementations.fenotypes;

import ags.cromosome.Fenotype;

//The fenotype is comprised of N real values.
public class Fenotype_NDouble implements Fenotype{
	
	private double values [];
	private int N;
	
	public Fenotype_NDouble(double [] values, int N) {
		this.values = values;
		this.N = N;
	}
	
	public double getValue(int pos) {
		return values[pos];
	}
	public double[] getValues(int pos) {
		return values;
	}
	
	public int getN() {
		return N;
	}
	

	@Override
	public boolean equals(Object o) throws ClassCastException{
		Fenotype_NDouble fo = (Fenotype_NDouble) o;
		boolean eq = true;
		for (int i = 0; (i < N) && eq; i++) {
			eq = values[i] == fo.getValue(i);
		}
		return eq;
	}
	
	@Override
	public String toString () {
		String r = "";
		for (int i = 0; i < N; i++) {
			r += ("FV" + (i+1) + ": " + values[i] + "    ");
		}
		return r;
	}
}
