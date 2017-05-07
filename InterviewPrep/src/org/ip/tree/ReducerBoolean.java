package org.ip.tree;
public interface ReducerBoolean<T extends Comparable<T>> {
		public boolean visit(Node<T> node, int depth);
	}