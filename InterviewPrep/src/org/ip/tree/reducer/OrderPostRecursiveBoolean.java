package org.ip.tree.reducer;

import org.ip.tree.Node;
import org.ip.tree.Tree;

public class OrderPostRecursiveBoolean<T extends Comparable<T>> implements BooleanVoid{
	private Boolean<T> reducer;
	private Node<T> root;
	public OrderPostRecursiveBoolean(Tree<T> tree, Boolean<T> reducer) {
		this.root = tree.root();
		this.reducer = reducer;
	}
	@Override
	public boolean execute() {
		if (root == null) return true;
		return inOrder(reducer,root,0);
	}
	private boolean inOrder(Boolean<T> visitor, Node<T> current, int depth) {
		for (Node<T> child : current.childs) {
			if (child == null) continue;
			if (!inOrder(visitor,child,depth+1)) return false;
		}
		return visitor.visit(current,depth); 
	}
}