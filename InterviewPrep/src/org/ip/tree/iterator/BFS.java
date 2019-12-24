package org.ip.tree.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.ip.tree.Node;
import org.ip.tree.Tree;

public class BFS<T> implements Iterator<Node<T>> {
	Queue<Node<T>> queue = new LinkedList<Node<T>>();
	public BFS(Tree<T> tree) {
		if (tree.root() == null) return;
		queue.add(tree.root());
	}
	public BFS(Node<T> node) {
		if (node == null) return;
		queue.add(node);
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
