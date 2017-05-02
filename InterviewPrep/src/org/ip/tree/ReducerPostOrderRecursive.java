package org.ip.tree;

import java.lang.reflect.Array;

import org.ip.tree.Tree.Reducer;
import org.ip.tree.Tree.ObjectVisitor;

public class ReducerPostOrderRecursive<T extends Comparable<T>, S> implements Reducer<S>{
	private ObjectVisitor<S,T> reducer;
	private Node<T> root;
	private S[] buffer;
	private S previous = null;
	public ReducerPostOrderRecursive(Tree<T> tree, ObjectVisitor<S,T> reducer) {
		this.root = tree.root();
		this.reducer = reducer;
	}
	@Override
	public S execute(S init) {
		if (root == null) return null;
		previous = init;
		return inOrder(reducer,root,0);
	}
	private S inOrder(ObjectVisitor<S, T> visitor, Node<T> current, int depth) {
		if (buffer == null) buffer = (S[])Array.newInstance(root.value.getClass(), root.childs.length);
		S[] tmp = current.childs.length == buffer.length ? buffer : (S[])Array.newInstance(root.value.getClass(), current.childs.length);
		for (int i = 0; i < current.childs.length; i++) {
			Node<T> child = current.childs[i];
			if (child == null) continue;
			tmp[i] = inOrder(visitor,child,depth+1); 
		}
		return previous = visitor.visit(current,depth,tmp,previous); 
	}
	@Override
	public S get() {
		return previous;
	}
}