package org.ip.tree;

import org.ip.tree.Tree.BooleanReducer;
import org.ip.tree.Tree.BooleanVisitor;

public class RecursiveBooleanPostOrderReducer<T extends Comparable<T>> implements BooleanReducer{
	private BooleanVisitor<T> reducer;
	private Node<T> root;
	public RecursiveBooleanPostOrderReducer(Tree<T> tree, BooleanVisitor<T> reducer) {
		this.root = tree.root();
		this.reducer = reducer;
	}
	@Override
	public boolean execute() {
		if (root == null) return true;
		return inOrder(reducer,root,0);
	}
	private boolean inOrder(BooleanVisitor<T> visitor, Node<T> current, int depth) {
		for (Node<T> child : current.childs) {
			if (child == null) continue;
			if (!inOrder(visitor,child,depth+1)) return false;
		}
		return visitor.visit(current,depth); 
	}
}