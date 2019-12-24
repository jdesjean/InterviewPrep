package org.ip.bst;

import static org.ip.tree.TreeTest.bst1;
import static org.ip.tree.TreeTest.nonBST3;

import org.ip.tree.Node;

// EPI 2018: 14.1
public class BST {
	public static void main(String[] s) {
		System.out.println(new BST().isBST(bst1().root()));
		System.out.println(new BST().isBST(nonBST3().root()));
	}
	public boolean isBST(Node<Integer> root) {
		return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	public boolean isBST(Node<Integer> root, int left, int right) {
		if (root == null) return true;
		if (root.value < left || root.value > right) return false;
		return isBST(root.getLeft(), left, root.value) && isBST(root.getRight(), root.value, right);
	}
}
