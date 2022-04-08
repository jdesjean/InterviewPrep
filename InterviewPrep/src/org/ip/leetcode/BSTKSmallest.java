package org.ip.leetcode;

import static org.ip.tree.Node.node;

import org.ip.tree.Node;

//leetcode 230
public class BSTKSmallest {
	public static void main(String[] s) {
		Node root = node(3,
				node(1, 
						null,
						node(2)),
				node(4));
		BSTKSmallest BSTKSmallest = new BSTKSmallest();
		System.out.println(BSTKSmallest.kthSmallest(root, 1));
	}
	private int k;
	public int kthSmallest(Node<Integer> root, int k) {
		this.k = k;
		return kthSmallest(root);
    }
	private int kthSmallest(Node<Integer> root) {
		if (root == null) return Integer.MAX_VALUE;
		int left = kthSmallest(root.left);
		if (left != Integer.MAX_VALUE) return left;
		if (--this.k == 0) {
			return root.value;
		}
		int right = kthSmallest(root.right);
		return right;
	}
}
