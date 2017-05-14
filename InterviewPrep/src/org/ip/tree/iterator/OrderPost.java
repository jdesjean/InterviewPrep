package org.ip.tree.iterator;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.ip.tree.Node;
import org.ip.tree.Tree;

public class OrderPost<T> implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	Node<T> prev;
	private boolean bIterateNullChilds = false;
	public OrderPost(Tree<T> tree){
		pushLeft(tree.root());
	}
	public OrderPost(Tree<T> tree, boolean bIterateNullChilds){
		this.bIterateNullChilds = bIterateNullChilds;
		pushLeft(tree.root());
	}
	public void pushLeft(Node<T> start) {
		for (Node<T> current = start; current != null;current = current.getLeft()) {
			stack.push(current);
		}
		if (bIterateNullChilds) stack.push(null);
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public Node<T> next() {
		Node<T> peek = stack.peek();
		if (peek == null) return stack.pop();
		Node<T> right = peek.getRight();
		if (bIterateNullChilds && right == null && prev != peek) { prev = peek; return null;}
		else if (right == null || prev == right) return prev = stack.pop();
		stack.push(right);
		while(right.getLeft() == null && right.getRight() != null) {
			right = right.getRight();
			stack.push(right);
			if (bIterateNullChilds) stack.push(null);
		}
		pushLeft(right.getLeft());
		return prev = stack.pop();
	}
	
}
