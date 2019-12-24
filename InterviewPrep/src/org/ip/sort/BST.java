package org.ip.sort;

import org.ip.tree.Node;

//EPI 2018: 14.8
public class BST {
	public static void main(String[] s) {
		int[] array = new int[] {1,2,3,4,5,6,7};
		Node<Integer> root = new BST().fromSorted(array);
	}
	public Node<Integer> fromSorted(int[] a) {
		return _fromSorted(a, 0, a.length - 1);
	}
	private Node<Integer> _fromSorted(int[] a, int left, int right) {
		if (left > right) return null;
		int mid = left + (right - left) / 2;
		Node<Integer> node = new Node<Integer>(a[mid]);
		node.childs[0] = _fromSorted(a, left, mid - 1);
		node.childs[1] = _fromSorted(a, mid + 1, right);
		return node;
	}
}
