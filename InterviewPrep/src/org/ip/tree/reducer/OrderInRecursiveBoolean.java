package org.ip.tree.reducer;

import org.ip.tree.Node;
import org.ip.tree.Tree;

public class OrderInRecursiveBoolean<T extends Comparable<T>> implements BooleanVoid{
	private Boolean<T> reducer;
	private Node<T> root;
	public OrderInRecursiveBoolean(Tree<T> tree, Boolean<T> reducer) {
		this.root = tree.root();
		this.reducer = reducer;
	}
	@Override
	public boolean execute() {
		if (root == null) return true;
		return inOrder(reducer,root,0);
	}
	private boolean inOrder(Boolean<T> visitor, Node<T> current, int depth) {
		int mid = current.childs.length/2;
		for (int i = 0; i < current.childs.length; i++) {
			Node<T> child = current.childs[i];
			if (child == null) continue;
			if (i < mid) {
				if (!inOrder(visitor,child,depth+1)) return false;
			}
			if (!visitor.visit(current,depth)) return false;
			if (i >= mid)  {
				if (!inOrder(visitor,child,depth+1)) return false;
			}
		}
		return true;
	}
}
