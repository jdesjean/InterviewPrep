package org.ip.sort;

import static org.ip.tree.TreeTest.bst3;

import org.ip.tree.Node;

//EPI 2018: 14.9
public class TotalOrder {
	public static void main(String[] s) {
		System.out.println(new TotalOrder().hasTotalOrder(bst3().root(), 19, 31, 23));
		System.out.println(new TotalOrder().hasTotalOrder(bst3().root(), 43, 53, 23));
	}
	public boolean hasTotalOrder(Node<Integer> root, int v1, int v2, int middle) {
		Node<Integer> current = root;
		int v3 = 0;
		while (current != null) {
			if (root.value < v1 && root.value < v2 && root.value < middle) {
				root = root.getRight();
			} else if (root.value > v1 && root.value > v2 && root.value > middle) {
				root = root.getLeft();
			} else if (root.value == v1) {
				v3 = v2;
				break;
			} else if (root.value == v2) {
				v3 = v1;
				break;
			} else {
				return false;
			}
		}
		while (current != null) {
			if (current.value == middle) {
				break;
			} else if (current.value < middle && current.value < v3) {
				current = current.getRight();
			} else if (current.value > middle && current.value > v3) {
				current = current.getLeft();
			} else {
				return false;
			}
		}
		while (current != null) {
			if (current.value == v3) return true;
			else if (current.value < v3) {
				current = current.getRight();
			} else {
				current = current.getLeft();
			}
		}
		return false;
	}
}
