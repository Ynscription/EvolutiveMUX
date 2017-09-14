package ags.cromosome.implementations.genotypes.treeAsProgram.functions;

import ags.cromosome.implementations.genotypes.treeAsProgram.Function;
import ags.cromosome.implementations.genotypes.treeAsProgram.Node;
import ags.cromosome.implementations.genotypes.treeAsProgram.TerminalValues;

public class Function_IF extends Function {

	@Override
	public int getNeededChildren() {
		return 3;
	}

	@Override
	public boolean evaluate(TerminalValues tv) throws Exception {
		if (children.size() != 3) {
			throw new Exception("Children amount missmatch");
		}
		if (children.getFirst().evaluate(tv)) {
			return children.get(1).evaluate(tv);
		}else {
			return children.getLast().evaluate(tv);
		}
	}
	
	@Override
	public Object clone() {
		Function_IF r = new Function_IF();
		for (Node n : children) {
			r.addChildren((Node) n.clone());
		}
		return r;
	}

	@Override
	public boolean equals(Object obj) {
		boolean r = true;
		r = obj instanceof Function_IF;
		if (r) {
			for (int i = 0; i < children.size() && r; i++) {
				r = children.get(i).equals(((Node)obj).getChildren(i));
			}
		}
		return r;
	}

	@Override
	public String toString() {
		return "IF(" + children.getFirst().toString() + ", " + children.get(1).toString() + ", " + children.getLast().toString() + ")";
	}

}
