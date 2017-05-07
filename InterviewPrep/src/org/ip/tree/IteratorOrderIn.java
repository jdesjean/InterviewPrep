package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

//EPI 10.7
//Time: n, Space: H
public class IteratorOrderIn<T extends Comparable<T>> implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	private int k;
	private boolean hasCount;
	public IteratorOrderIn(Tree<T> tree) {
		pushLeft(tree.root());
	}
	public IteratorOrderIn(Tree<T> tree, int k) {
		pushLeft(tree.root());
		this.hasCount = true;
		this.k = k;
	}
	public IteratorOrderIn(Deque<Node<T>> stack) {
		this.stack = stack;
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty() && (!hasCount || k > 0);
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
