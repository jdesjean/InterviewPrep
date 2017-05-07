package org.ip.tree;

public interface LCA<T extends Comparable<T>> {
	public Node<T> lca(T v1, T v2);
}