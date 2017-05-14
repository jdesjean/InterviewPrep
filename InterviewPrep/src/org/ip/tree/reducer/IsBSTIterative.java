package org.ip.tree.reducer;

import java.util.Iterator;

import org.ip.tree.Node;
import org.ip.tree.Tree;

public class IsBSTIterative<T extends Comparable<T>> implements BooleanVoid {
	private Tree<T> tree;
	public IsBSTIterative(Tree<T> tree) {
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
