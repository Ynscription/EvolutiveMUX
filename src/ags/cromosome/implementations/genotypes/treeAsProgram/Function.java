package ags.cromosome.implementations.genotypes.treeAsProgram;

import ags.cromosome.implementations.genotypes.treeAsProgram.functions.Function_AND;
import ags.cromosome.implementations.genotypes.treeAsProgram.functions.Function_IF;
import ags.cromosome.implementations.genotypes.treeAsProgram.functions.Function_NOT;
import ags.cromosome.implementations.genotypes.treeAsProgram.functions.Function_OR;

public abstract class Function extends Node {
	
	private static final Function [] functions = {new Function_AND(), new Function_OR(), new Function_NOT(), new Function_IF()};
	public static int maxFunctionID (){
		return functions.length;
	}

	public abstract int getNeededChildren ();
	
	@Override
	public abstract boolean evaluate(TerminalValues tv) throws Exception;
	
	public static Function newFunction (int id) throws Exception {
		if (id < 0 || id >= functions.length) {
			throw new Exception ("No such function ID.");
		}
		return (Function) functions[id].clone();
	}
}
