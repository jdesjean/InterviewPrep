package org.ip.tree;

//Time: O(n), Space: H
public class FinderComparable<T extends Comparable<T>> implements Finder<T>{
	private Tree<T> tree;
	public FinderComparable(Tree<T> tree){
		this.tree = tree;
	}
	@Override
	public Node<T> find(T v) {
		return find(null,tree.root(),v);
	}
	private static <T extends Comparable<T>> Node<T> find(Node<T> parent, Node<T> node, T t) {
		if (node == null) return null;
		node.parent = parent;
		if (node.value.compareTo(t) == 0) return node;
		Node<T> left = find(node,node.getLeft(),t);
		if (left != null) return left;
		Node<T> right = find(node,node.getRight(),t);
		return right;
	}
}
