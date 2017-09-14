package ags.cromosome.implementations.genotypes.treeAsProgram;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class Node {

	protected LinkedList<Node> children;
	
	public int getChildrenAmount () {
		int r = 1;
		for (Node node : children) {
			r += node.getChildrenAmount();
		}
		return r;
	}
	
	
	public void addChildren (Node c) {
		children.add(c);
	}
	
	public Node getChildren (int index) {
		return children.get(index);
	}
	
	public void replaceChildren (int index, Node node) {
		children.set(index, node);
	}	
		
	
	public Node() {
		children = new LinkedList<Node>();
	}
	
	public void replaceNodeAtPosition (int position, Node newN) {
		children.set(position, newN);
	}
	
	public Node [] depthGetParentNodeAtPosition (int position, int childN []) {
		return inDepthGetParentNodeAtPosition(0, position, childN);
	}
	
	private Node [] inDepthGetParentNodeAtPosition (int index, int position, int childN []) {
		Node r [] = new Node [2];
		int aux = 0;
		boolean found = false;
		Iterator<Node> it = children.iterator();
		int cn = -1;
		
		while (it.hasNext() && !found) {
			Node node = it.next();
			cn++;
			if (position == index + aux + 1) {
				r[0] = this;
				r[1] = node;
				childN[0] = cn;
				found = true;
			}			
			else if (position <= index + node.getChildrenAmount() + aux) {
				r = node.inDepthGetParentNodeAtPosition(index + aux + 1, position, childN);
				found = true;
			}
			else {
				aux += node.getChildrenAmount();
			}
		}	
				
		return r;		
	}
	
	public int getDepthAt(int cut) {
		return inGetDepthAt(0, cut, 1);
	}
	
	private int inGetDepthAt (int index, int position, int count) {
		int r = -1;
		int aux = 0;
		boolean found = false;
		Iterator<Node> it = children.iterator();
		
		while (it.hasNext() && !found) {
			Node node = it.next();
			
			if (position < index + node.getChildrenAmount() + aux) {
				r = node.inGetDepthAt(index + aux + 1, position, count  + 1);
				found = true;
			}
			else if (position == index + node.getChildrenAmount() + aux) {
				r = count + 1;
				found = true;
			}
			else {
				aux += node.getChildrenAmount();
			}
		}	
				
		return r;	
	}
	
	
	public abstract boolean evaluate (TerminalValues tv) throws Exception;
	
	public abstract int getNeededChildren ();
	
	@Override
	public abstract Object clone();
	
	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract String toString();
	
	
}
