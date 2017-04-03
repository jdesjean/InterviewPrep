package org.ip.tree;

import java.util.Iterator;

import org.ip.tree.Tree.BooleanReducer;

public class ReducerIsBSTIterative<T extends Comparable<T>> implements BooleanReducer {
	private Tree<T> tree;
	public ReducerIsBSTIterative(Tree<T> tree) {
		this.tree=tree;
	}
	@Override
	public boolean execute() {
		Node<T> previous = null;
		for (Iterator<Node<T>> iterator = tree.inOrderIterator(); iterator.hasNext();) {
			Node<T> prev = previous;
			previous = iterator.next();
			if (prev != null && prev.compareTo(previous) >= 0) return false;
		}
		return true;
	}
	
}
