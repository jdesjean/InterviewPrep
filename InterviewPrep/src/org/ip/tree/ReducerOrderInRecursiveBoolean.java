package org.ip.tree;

public class ReducerOrderInRecursiveBoolean<T extends Comparable<T>> implements ReducerBooleanVoid{
	private ReducerBoolean<T> reducer;
	private Node<T> root;
	public ReducerOrderInRecursiveBoolean(Tree<T> tree, ReducerBoolean<T> reducer) {
		this.root = tree.root();
		this.reducer = reducer;
	}
	@Override
	public boolean execute() {
		if (root == null) return true;
		return inOrder(reducer,root,0);
	}
	private boolean inOrder(ReducerBoolean<T> visitor, Node<T> current, int depth) {
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
