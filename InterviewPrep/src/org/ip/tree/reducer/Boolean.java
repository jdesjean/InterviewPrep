package org.ip.tree.reducer;

import org.ip.tree.Node;

public interface Boolean<T> {
	public boolean visit(Node<T> node, int depth);
}