package org.ip.bst;

import static org.ip.tree.TreeTest.bst3;

import org.ip.tree.Node;

// EPI 2018: 14.2
public class GreaterThanRecursive {
	public static void main(String[] s) {
		System.out.println(new GreaterThanRecursive().next(bst3().root(), 23));
		System.out.println(new GreaterThanRecursive().next(bst3().root(), 31));
		System.out.println(new GreaterThanRecursive().next(bst3().root(), 53));
	}
	public int next(Node<Integer> root, int value) {
		Node<Integer> next = _next(null, root, value);
		return next != null ? next.value : -1;
	}
	public Node<Integer> _next(Node<Integer> parent, Node<Integer> root, int value) {
		if (root == null) return null;
		if (root.value == value) {
			return successor(parent, root);
		}
		if (value > root.value) {
			return _next(parent, root.getRight(), value);
		} else {
			return _next(root, root.getLeft(), value);
		}
	}
	public Node<Integer> successor(Node<Integer> parent, Node<Integer> root) {
		if (root.getRight() != null) {
			return getRight(root);
		} else {
			return parent;
		}
	}
	public static Node<Integer> getRight(Node<Integer> root) {
		Node<Integer> current = root.getRight();
		while (current != null && current.getLeft() != null) {
			current = current.getLeft();
		}
		return current;
	}
}
