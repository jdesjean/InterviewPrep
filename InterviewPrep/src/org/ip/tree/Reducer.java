package org.ip.tree;

public interface Reducer<S, T> {
	public S visit(T current, S init);
}