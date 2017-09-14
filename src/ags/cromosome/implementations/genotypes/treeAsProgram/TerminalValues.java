package ags.cromosome.implementations.genotypes.treeAsProgram;

public class TerminalValues {

	private boolean [] values;
	
	public boolean getValue(int i) {
		return values[i];
	}
	
	public TerminalValues(boolean [] values) {
		this.values = values;
	}

	public TerminalValues(int values, int length) {
		this.values = new boolean [length];
		for (int i = 0; i < length; i ++) {
			int mask = 1 << i; // Looks like 000...00100...000
			int value = values & mask; //Is either 0 or 000...00100...000
			this.values [i] = value != 0;
		}
	}
}
