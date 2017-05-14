package org.ip.tree.iterator;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.ip.tree.Node;
import org.ip.tree.Tree;

//EPI 10.7
//Time: n, Space: H
public class OrderIn<T> implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	private int k;
	private boolean hasCount = false;
	private boolean bIterateNullChilds;
	public OrderIn(Tree<T> tree) {
		pushLeft(tree.root());
	}
	public OrderIn(Tree<T> tree, int k) {
		pushLeft(tree.root());
		this.hasCount = true;
		this.k = k;
	}
	public OrderIn(Tree<T> tree, boolean bIterateNullChilds) {
		this.bIterateNullChilds = bIterateNullChilds;
		pushLeft(tree.root());
	}
	public OrderIn(Deque<Node<T>> stack) {
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
		if (bIterateNullChilds) stack.push(null);
	}
	@Override
	public Node<T> next() {
		Node<T> removed = stack.pop();
		if (removed != null) {
			Node<T> right = removed.getRight();
			if (bIterateNullChilds || right != null) {
				stack.push(right);
				if (right != null) pushLeft(right.getLeft());
			}
		}
		k--;
		return removed;
	}
	
}
