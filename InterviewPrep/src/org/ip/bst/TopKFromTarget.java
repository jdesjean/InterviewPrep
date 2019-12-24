package org.ip.bst;

import static org.ip.tree.TreeTest.bst3;

import java.util.Deque;
import java.util.LinkedList;

import org.ip.tree.Node;

// LinkedIn: Top K from target in BST
public class TopKFromTarget {
	public static void main(String[] s) {
		//               6,4,0,6
		//2,3,5,7,11,13,17,19,23,29,31,37,31,43,47,53
		Node<Integer> root = bst3().root();
		Deque<Integer> queue = new TopKFromTarget().topK(root, 3, 23);
		for (Integer value : queue) {
			System.out.print(value + ",");
		}
	}
	public Deque<Integer> topK(Node<Integer> root, int k, int target) {
		Deque<Integer> list = new LinkedList<Integer>();
		topK(list, root, k, target);
		return list;
	}
	public void topK(Deque<Integer> list, Node<Integer> root, int k, int target) {
		if (root == null) return;
		topK(list, root.getLeft(), k, target);
		if (list.size() < k) {
			list.add(root.value);
		} else {
			int d1 = Math.abs(root.value - target);
			int d2 = Math.abs(list.peekLast() - target);
			if (d1 < d2) {
				list.addFirst(root.value);
				list.removeLast();
			} else {
				return;
			}
		}
		topK(list, root.getRight(), k, target);
	}
}
