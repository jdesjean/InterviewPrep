package org.ip.tree.iterator;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.ip.tree.Node;
import org.ip.tree.Tree;

public class OrderReverse<T> implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	private int k;
	private boolean hasCount;
	public OrderReverse(Tree<T> tree) {
		pushRight(tree.root());
	}
	public OrderReverse(Tree<T> tree, int k) {
		pushRight(tree.root());
		this.hasCount = true;
		this.k = k;
	}
	public OrderReverse(Deque<Node<T>> stack) {
		this.stack=stack;
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
