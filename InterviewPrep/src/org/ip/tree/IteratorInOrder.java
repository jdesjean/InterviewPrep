package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class IteratorInOrder<T extends Comparable<T>> implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	private int k;
	public IteratorInOrder(Tree<T> tree) {
		pushLeft(tree.root());
	}
	public IteratorInOrder(Tree<T> tree, int k) {
		pushLeft(tree.root());
		this.k = k;
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty() && k > 0;
	}
	public void pushLeft(Node<T> start) {
		for (Node<T> current = start; current != null;current = current.getLeft()) {
			stack.push(current);
		}
	}
	@Override
	public Node<T> next() {
		Node<T> removed = stack.pop();
		if (removed.getRight() != null) {
			stack.push(removed.getRight());
			pushLeft(removed.getRight().getLeft());
		}
		k--;
		return removed;
	}
	
}
