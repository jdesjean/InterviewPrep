package org.ip.tree.iterator;

import java.util.Iterator;

import org.ip.tree.Node;
import org.ip.tree.Tree;

//Time: O(n), Space: O(H);
public class OrderInLeaves<T> implements Iterator<Node<T>>{
	Node<T> current = null;
	private Iterator<Node<T>> iterator;
	public OrderInLeaves(Tree<T> tree){
		this.iterator = tree.inOrderIterator();
		current = getNextLeaf();
	}
	private Node<T> getNextLeaf() {
		Node<T> node = null;
		while (iterator.hasNext()) {
			node = iterator.next();
			if (node == null || node.isLeaf()) break;
		}
		return node;
	}

	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public Node<T> next() {
		Node<T> prev = current;
		current = getNextLeaf();
		return prev;
	}

}
