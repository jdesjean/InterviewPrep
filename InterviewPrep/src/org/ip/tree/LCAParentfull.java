package org.ip.tree;

//EPI 10.4
//Time: n to find nodes v1 & v2
//Time: H to find LCA
//Space: H
public class LCAParentfull<T> implements LCASolver<T> {
	private Tree<T> tree;

	public LCAParentfull(Tree<T> tree) {
		this.tree = tree;
	}

	@Override
	public Node<T> lca(T v1, T v2) {
		Node<T> n1 = lca(null, tree.root, v1);
		Node<T> n2 = lca(null, tree.root, v2);
		int h1 = height(n1);
		int h2 = height(n2);
		if (h2 < h1) { // swap
			int tmp = h1;
			h1 = h2;
			h2 = h1;
			Node<T> nT = n1;
			n1 = n2;
			n2 = nT;
		}
		while (h1 < h2) {
			h2--;
			n2 = n2.parent;
		}
		while (n1 != null && n2 != null && n1 != n2) {
			n1 = n1.parent;
			n2 = n2.parent;
		}
		return n1;
	}

	public int height(Node<T> n) {
		if (n == null)
			return 0;
		int count = 1;
		while (n.parent != null) {
			n = n.parent;
			count++;
		}
		return count;
	}

	public Node<T> lca(Node<T> parent, Node<T> current, T v1) {
		if (current == null)
			return null;
		current.parent = parent;
		if (current.value == v1)
			return current;
		Node<T> left = lca(current, current.getLeft(), v1);
		if (left != null)
			return left;
		Node<T> right = lca(current, current.getRight(), v1);
		return right;
	}
}