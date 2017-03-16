package org.ip.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BFSIterator<T extends Comparable<T>> implements Iterator<Node<T>> {
	Queue<Node<T>> queue = new LinkedList<Node<T>>();
	public BFSIterator(Tree<T> tree) {
		if (tree.root() == null) return;
		queue.add(tree.root());
	}
	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	@Override
	public Node<T> next() {
		Node<T> current = queue.remove();
		for (Node<T> child : current.childs) {
			if (child == null) continue;
			queue.add(child);
		}
		return current;
	}
}
