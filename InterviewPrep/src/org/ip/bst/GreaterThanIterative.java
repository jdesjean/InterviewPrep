package org.ip.bst;

import static org.ip.tree.TreeTest.bst3;

import org.ip.tree.Node;

// EPI 2018: 14.2
public class GreaterThanIterative {
	public static void main(String[] s) {
		System.out.println(new GreaterThanIterative().next(bst3().root(), 23));
		System.out.println(new GreaterThanIterative().next(bst3().root(), 31));
		System.out.println(new GreaterThanIterative().next(bst3().root(), 53));
	}
	public int next(Node<Integer> root, int value) {
		Node<Integer> current = root;
		Node<Integer> next = null;
		while (current != null) {
			if (current.value > value) {
				next = current;
				current = current.getLeft();
			} else {
				current = current.getRight();
			}
		}
		return next != null ? next.value : -1;
	}
}
