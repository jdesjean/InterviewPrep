package org.ip.tree;

public interface LCASolver<T> {
	public Node<T> lca(T v1, T v2);
}