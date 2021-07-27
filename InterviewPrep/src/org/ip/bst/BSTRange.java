package org.ip.bst;

import static org.ip.tree.TreeTest.bst3;

import java.util.function.Consumer;

import org.ip.tree.Node;
// EPI 2018: 14.10
public class BSTRange {
	public static void main(String[] s) {
		new BSTRange().solve(bst3().root(), 16, 31, (x) -> System.out.println(x.value));
	}
	public void solve(Node<Integer> root, int left, int right, Consumer<Node<Integer>> consumer) {
		if (root == null || left > right) return;
		if (root.value >= left && root.value <= right) {
			solve(root.getLeft(), left, root.value - 1, consumer);
			consumer.accept(root);
			solve(root.getRight(), root.value + 1, right, consumer);
		} else if (root.value < left) {
			solve(root.getRight(), left, right, consumer);
		} else if (root.value > right) {
			solve(root.getLeft(), left, right, consumer);
		}
	}
}
