package org.ip.tree;

import org.ip.tree.Tree.BooleanReducer;

public class RecursiveIsBSTReducer<T extends Comparable<T>> implements BooleanReducer {
	private Node<T> root;
	public RecursiveIsBSTReducer(Tree<T> tree) {
		this.root = tree.root();
	}
	@Override
	public boolean execute() {
		return isBST(root,null,null);
	}
	private boolean isBST(Node<T> node, Node<T> left, Node<T> right) {
		if (node == null) return true;
		if ((left == null && right == null)
				|| (left == null && node.compareTo(right) < 0)
				|| (right == null && node.compareTo(left) > 0)
				|| (node.compareTo(left) > 0 && node.compareTo(right) < 0)) {
			return isBST(node.getLeft(), left, node) && isBST(node.getRight(), node, right);
		} else  {
			return false;
		}
	}
}