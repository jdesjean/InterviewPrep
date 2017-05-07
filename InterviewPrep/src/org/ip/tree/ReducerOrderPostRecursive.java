package org.ip.tree;

public class ReducerOrderPostRecursive<T extends Comparable<T>, S>{
	private ReducerOrderPost<S,T> reducer;
	private Node<T> root;
	private Reducer<S, S> reducerChild;
	public ReducerOrderPostRecursive(Tree<T> tree, ReducerOrderPost<S,T> reducer, Reducer<S,S> reducerChild) {
		this.root = tree.root();
		this.reducer = reducer;
		this.reducerChild = reducerChild;
	}
	public S execute(S init) {
		if (root == null) return null;
		return inOrder(reducer,root,0,init);
	}
	private S inOrder(ReducerOrderPost<S, T> visitor, Node<T> current, int depth, S s) {
		S sss = s;
		for (int i = 0; i < current.childs.length; i++) {
			Node<T> child = current.childs[i];
			if (child == null) continue;
			S ss = inOrder(visitor,child,depth+1, s);
			sss = reducerChild.visit(ss, sss);
		}
		return visitor.visit(current,depth,sss); 
	}
}