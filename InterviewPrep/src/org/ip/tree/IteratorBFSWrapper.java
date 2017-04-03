package org.ip.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class IteratorBFSWrapper<T extends Comparable<T>> implements Iterator<NodeWrapper<T>> {
	Queue queue = new LinkedList();
	NodeWrapper<T> wrapper = new NodeWrapper<T>();
	private final static Object EMPTY = new Object();
	int depth = 0;
	public IteratorBFSWrapper(Tree<T> tree) {
		if (tree.root() == null) return;
		queue.add(tree.root());
		queue.add(EMPTY);
	}
	@Override
	public boolean hasNext() {
		boolean add = false;
		while (!queue.isEmpty() && queue.peek() == EMPTY) {
			depth++;
			queue.remove();
			add = true;
		}
		if (!queue.isEmpty() && add) queue.add(EMPTY);
		return !queue.isEmpty();
	}

	@Override
	public NodeWrapper<T> next() {
		Object current = queue.remove();
		boolean add = false;
		while (current == EMPTY) {
			depth++;
			current = queue.remove();
			add = true;
		}
		if (add && !queue.isEmpty()) queue.add(EMPTY);
		for (Node<T> child : ((Node<T>)current).childs) {
			if (child == null) continue;
			queue.add(child);
		}
		wrapper.node = (Node<T>)current;
		wrapper.depth = depth;
		return wrapper;
	}
}
