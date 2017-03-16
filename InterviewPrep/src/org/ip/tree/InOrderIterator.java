package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class InOrderIterator<T extends Comparable<T>> implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	public InOrderIterator(Tree<T> tree) {
		pushLeft(tree.root());
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
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
		return removed;
	}
	
}
