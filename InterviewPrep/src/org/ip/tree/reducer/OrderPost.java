package org.ip.tree.reducer;

import org.ip.tree.Node;

public interface OrderPost<S, T> {
	public S visit(Node<T> node, int depth, S previous);
}