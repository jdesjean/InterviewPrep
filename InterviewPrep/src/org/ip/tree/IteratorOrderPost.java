package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class IteratorOrderPost<T extends Comparable<T>> implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	Node<T> prev;
	public IteratorOrderPost(Tree<T> tree){
		pushLeft(tree.root());
	}
	public void pushLeft(Node<T> start) {
		for (Node<T> current = start; current != null;current = current.getLeft()) {
			stack.push(current);
		}
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public Node<T> next() {
		Node<T> peek = stack.peek();
		Node<T> right = peek.getRight();
		if (right == null) return prev = stack.pop();
		if (prev == right) return prev = stack.pop();
		stack.push(right);
		while(right.getLeft() == null && right.getRight() != null) {
			right = right.getRight();
			stack.push(right);
		}
		pushLeft(right.getLeft());
		return prev = stack.pop();
	}
	
}
