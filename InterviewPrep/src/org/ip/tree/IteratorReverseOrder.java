package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class IteratorReverseOrder<T extends Comparable<T>> implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	private int k;
	private boolean hasCount;
	public IteratorReverseOrder(Tree<T> tree) {
		pushRight(tree.root());
	}
	public IteratorReverseOrder(Tree<T> tree, int k) {
		pushRight(tree.root());
		this.hasCount = true;
		this.k = k;
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty() && (!hasCount || k > 0);
	}
	public void pushRight(Node<T> start) {
		for (Node<T> current = start; current != null;current = current.getRight()) {
			stack.push(current);
		}
	}
	@Override
	public Node<T> next() {
		Node<T> removed = stack.pop();
		if (removed.getLeft() != null) {
			stack.push(removed.getLeft());
			pushRight(removed.getLeft().getRight());
		}
		k--;
		return removed;
	}
	
}
