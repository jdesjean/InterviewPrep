package org.ip.tree.reducer;

import org.ip.tree.Node;
import org.ip.tree.Tree;

public class OrderPostRecursive<T, S>{
	private OrderPost<S,T> reducer;
	private Node<T> root;
	private Object<S, S> reducerChild;
	public OrderPostRecursive(Tree<T> tree, OrderPost<S,T> reducer, Object<S,S> reducerChild) {
		this.root = tree.root();
		this.reducer = reducer;
		this.reducerChild = reducerChild;
	}
	public S execute(S init) {
		if (root == null) return null;
		return inOrder(root,0,init);
	}
	private S inOrder(Node<T> current, int depth, S s) {
		S sss = s;
		for (int i = 0; i < current.childs.length; i++) {
			Node<T> child = current.childs[i];
			if (child == null) continue;
			S ss = inOrder(child,depth+1, s);
			sss = reducerChild.visit(ss, sss);
		}
		return reducer.visit(current,depth,sss); 
	}
}