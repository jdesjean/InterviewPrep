package org.ip.tree;

public class ReducerOrderPostRecursiveBoolean<T extends Comparable<T>> implements ReducerBooleanVoid{
	private ReducerBoolean<T> reducer;
	private Node<T> root;
	public ReducerOrderPostRecursiveBoolean(Tree<T> tree, ReducerBoolean<T> reducer) {
		this.root = tree.root();
		this.reducer = reducer;
	}
	@Override
	public boolean execute() {
		if (root == null) return true;
		return inOrder(reducer,root,0);
	}
	private boolean inOrder(ReducerBoolean<T> visitor, Node<T> current, int depth) {
		for (Node<T> child : current.childs) {
			if (child == null) continue;
			if (!inOrder(visitor,child,depth+1)) return false;
		}
		return visitor.visit(current,depth); 
	}
}