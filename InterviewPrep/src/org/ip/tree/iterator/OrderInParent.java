package org.ip.tree.iterator;

import java.util.Iterator;

import org.ip.tree.Node;
import org.ip.tree.Tree;

//EPI 10.11
//Time: O(n), Space: O(1)
public class OrderInParent<T extends Comparable<T>> implements Iterator<Node<T>> {
	Node<T> node;
	private Tree<T> tree;
	public OrderInParent(Tree<T> tree) {
		this.tree=tree;
		pushLeft(tree.root());
	}
	@Override
	public boolean hasNext() {
		return node != null;
	}
	public void pushLeft(Node<T> start) {
		for (Node<T> current = start; current != null;current = current.getLeft()) {
			node = current;
		}
	}
	@Override
	public Node<T> next() {
		Node<T> removed = node;
		node = tree.successor(removed);
		return removed;
	}
	
}
