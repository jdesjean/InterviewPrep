package org.ip.bst;

import org.ip.tree.Finder;
import org.ip.tree.Node;
import org.ip.tree.Tree;
import org.ip.tree.Visitor;

//Time: H, Space: 1
public class FinderBST<T extends Comparable<T>> implements Finder<T>{
	private Tree<T> tree;
	private Visitor<T> visitor;

	public FinderBST(Tree<T> tree, Visitor<T> visitor) {
		this.tree = tree;
		this.visitor=visitor;
	}
	@Override
	public Node<T> find(T v) {
		Node<T> parent = null;
		Node<T> node = tree.root();
		while (node != null) {
			node.parent = parent;
			visitor.visit(node);
			if (node.value.compareTo(v) == 0) {
				return node;
			} else if (node.value.compareTo(v) < 0) {
				parent = node;
				node = node.getRight();
			} else {
				parent = node;
				node = node.getLeft();
			}
		}
		return null;
	}
}
