package org.ip.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class IteratorBFSIterator<T extends Comparable<T>> implements Iterator<Iterator<Node<T>>> {
	Queue<Node<T>> queue = new LinkedList<Node<T>>();
	public static final Node SENTINEL = new Node(null);
	public IteratorBFSIterator(Tree<T> tree) {
		if (tree.root() == null) return;
		queue.add(SENTINEL);
		queue.add(tree.root());
	}
	@Override
	public boolean hasNext() {
		return !queue.isEmpty() && (queue.size() > 1 || queue.peek() != SENTINEL);
	}

	@Override
	public Iterator<Node<T>> next() {
		if (queue.peek() != SENTINEL) throw new NoSuchElementException(); 
		queue.remove();
		if (!queue.isEmpty()) queue.add(SENTINEL);
		return new LevelIterator();
	}
	private class LevelIterator implements Iterator<Node<T>> {

		@Override
		public boolean hasNext() {
			return !queue.isEmpty() && queue.peek() != SENTINEL;
		}

		@Override
		public Node<T> next() {
			Node<T> current = queue.remove();
			if (current == SENTINEL) throw new NoSuchElementException();
			for (Node<T> child : current.childs) {
				if (child == null) continue;
				queue.add(child);
			}
			return current;
		}
		
	}
}
