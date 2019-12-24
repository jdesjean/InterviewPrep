package org.ip.bst;

import org.ip.tree.Node;
import static org.ip.tree.TreeTest.bst3;

// EPI 2018: 14.4
// leetcode 235
public class LCA {
	public static void main(String[] s) {
		System.out.println(new LCA().solve(bst3().root(), 3, 17));
		System.out.println(new LCA().solve(bst3().root(), 37, 41));
	}
	public Node<Integer> solve(Node<Integer> root, int n1, int n2) {
		while (root != null) {
			if (n1 > root.value && n2 > root.value) {
				root = root.getRight();
			} else if (n1 < root.value && n2 < root.value) {
				root = root.getLeft();
			} else {
				return root;
			}
		}
		return root;
	}
}
