package ags.cromosome.implementations.genotypes.treeAsProgram.functions;

import ags.cromosome.implementations.genotypes.treeAsProgram.Function;
import ags.cromosome.implementations.genotypes.treeAsProgram.Node;
import ags.cromosome.implementations.genotypes.treeAsProgram.TerminalValues;

public class Function_OR extends Function{

	@Override
	public int getNeededChildren() {
		return 2;
	}

	@Override
	public boolean evaluate(TerminalValues tv) throws Exception {
		if (children.size() != 2) {
			throw new Exception("Children amount missmatch");
		}
		return children.getFirst().evaluate(tv) || children.getLast().evaluate(tv);
	}

	@Override
	public Object clone() {
		Function_OR r = new Function_OR();
		for (Node n : children) {
			r.addChildren((Node) n.clone());
		}
		return r;
	}

	@Override
	public boolean equals(Object obj) {
		boolean r = true;
		r = obj instanceof Function_OR;
		if (r) {
			for (int i = 0; i < children.size() && r; i++) {
				r = children.get(i).equals(((Node)obj).getChildren(i));
			}
		}
		return r;
	}
	
	@Override
	public String toString() {
		return "OR(" + children.getFirst().toString() + ", " + children.getLast() + ")";
	}

}
