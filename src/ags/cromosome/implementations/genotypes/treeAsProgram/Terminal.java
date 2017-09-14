package ags.cromosome.implementations.genotypes.treeAsProgram;

public class Terminal extends Node {

	private int terminalID;
	
	public Terminal(int terminalID) {
		this.terminalID = terminalID;
	}
	
	public void setTerminalID(int terminalID) {
		this.terminalID = terminalID;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean r = false;
		if (obj instanceof Terminal) {
			Terminal o = (Terminal) obj;
			r = o.terminalID == this.terminalID;
		}
		return r;
	}

	
	@Override
	public boolean evaluate(TerminalValues tv) {
		return tv.getValue(terminalID);
	}

	@Override
	public Object clone() {
		return new Terminal(terminalID);
	}

	@Override
	public String toString() {
		return "i[" + terminalID + "]";
	}

	@Override
	public int getNeededChildren() {
		return 0;
	}
}
