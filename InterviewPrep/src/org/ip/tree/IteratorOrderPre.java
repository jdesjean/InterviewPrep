package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

//EPI 10.8
//Time: n, Space: H
public class IteratorOrderPre<T extends Comparable<T>>  implements Iterator<Node<T>> {
	Deque<Node<T>> stack = new LinkedList<Node<T>>();
	public IteratorOrderPre(Tree<T> tree){
		if (tree.root() != null) stack.push(tree.root());
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public Node<T> next() {
		Node<T> current = stack.pop();
		if (current.getRight() != null) stack.push(current.getRight());
		if (current.getLeft() != null) stack.push(current.getLeft());
		return current;
	}
	
}
