package org.ip.tree;

public interface ReducerOrderPost<S, T extends Comparable<T>> {
	public S visit(Node<T> node, int depth, S previous);
}