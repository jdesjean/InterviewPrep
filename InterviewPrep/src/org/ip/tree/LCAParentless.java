package org.ip.tree;

//EPI 10.3
//Time: n to find nodes v1 & v2
//Time: H to find LCA
//Space: H
public class LCAParentless<T extends Comparable<T>> implements LCA<T> {
	public final LcaWrapper EMPTY = new LcaWrapper(null);
	private Tree<T> tree;

	public class LcaWrapper {
		public Node<T> node;
		int count = 0;

		public LcaWrapper(Node<T> node) {
			this.node = node;
		}
	}

	public LCAParentless(Tree<T> tree) {
		this.tree = tree;
	}

	@Override
	public Node<T> lca(T v1, T v2) {
		LcaWrapper wrapper = lca(tree.root, v1, v2);
		return wrapper.count >= 2 ? wrapper.node : null;
	}

	public LcaWrapper lca(Node<T> current, T v1, T v2) {
		if (current == null)
			return new LcaWrapper(null);
		// 4 cases
		// 1) 1 left & 1 right: Return current
		// 2) 1 current & (1 left | right) : Return current;
		// 3) 2 left | 2 right : Return left or right
		// 4) Can't find both : Return left or right
		LcaWrapper wrapper = lca(current.getLeft(), v1, v2);
		if (wrapper.count >= 2)
			return wrapper;
		if (current.value == v1 || current.value == v2)
			wrapper.count++;
		if (wrapper.count < 2) {
			LcaWrapper wrapper2 = lca(current.getRight(), v1, v2);
			if (wrapper2.count >= 2)
				return wrapper2;
			wrapper.count += wrapper2.count;
			if (wrapper2.count > 0)
				wrapper.node = wrapper2.node;
		}
		if (wrapper.count >= 2)
			wrapper.node = current;
		return wrapper;
	}
}