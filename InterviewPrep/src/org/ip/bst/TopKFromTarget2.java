package org.ip.bst;

import static org.ip.tree.TreeTest.bst3;

import org.ip.tree.Node;

// LinkedIn: Top K from target in BST
public class TopKFromTarget2 {
	public static void main(String[] s) {
		//               6,4,0,6
		//2,3,5,7,11,13,17,19,23,29,31,37,31,43,47,53
		Node<Integer> root = bst3().root();
		Node<Integer> queue = new TopKFromTarget2().topK(root, 3, 23);
		for (Node<Integer> current = queue; current != null; current = current.getLeft()) {
			System.out.print(current.value + ",");
		}
	}
	public Node<Integer> topK(Node<Integer> root, int k, int target) {
		NodeWrapper wrapper = new NodeWrapper();
		topK(wrapper,root, k, target);
		return wrapper.tail;
	}
	public void topK(NodeWrapper wrapper, Node<Integer> root, int k, int target) {
		if (root == null) return;
		Node<Integer> left = root.getLeft();
		Node<Integer> right = root.getRight();
		topK(wrapper, left, k, target);
		if (wrapper.size < k) {
			wrapper.add(root);
		} else {
			int d1 = Math.abs(root.value - target);
			int d2 = Math.abs(wrapper.tail.value - target);
			if (d1 < d2) {
				wrapper.add(root);
				wrapper.removeLast();
			} else {
				return;
			}
		}
		topK(wrapper, right, k, target);
	}
	public static class NodeWrapper {
		Node<Integer> tail;
		Node<Integer> head;
		int size = 0;
		public void add(Node<Integer> next) {
			next.childs[0] = tail;
			if (tail != null) {
				tail.childs[1] = next;
			} else {
				head = next;
			}
			tail = next;
			size++;
		}
		public void removeLast() {
			Node<Integer> next = head.childs[1];
			next.childs[0] = null;
			head.childs[1] = null;
			head.childs[0] = null;
			head = next;
			size--;
		}
	}
}
