package org.ip.recursion;

import java.util.function.Consumer;

import org.ip.tree.Node;

// EPI 2018: 15.8
public class BinaryTree {
	public static void main(String[] s) {
		new BinaryTree().generate((x) -> org.ip.tree.Tree.print(x), 3);
	}
	public void generate(Consumer<Node<Integer>> consumer, int k) {
		if (k == 0) return;
		Node<Integer> root = new Node<Integer>(k);
		generate(consumer, k - 1, root, root);
	}
	public void generate(Consumer<Node<Integer>> consumer, int k, Node<Integer> root, Node<Integer> current) {
		if (k == 0) {
			consumer.accept(root);
			return;
		}
		if (k >= 2) {
			current.childs[0] = new Node<Integer>(k);
			current.childs[1] = new Node<Integer>(k - 1);
			generate(consumer, k - 2, root, current.childs[0]);
			if (k > 2) {
				current.childs[0] = new Node<Integer>(k);
				current.childs[1] = new Node<Integer>(k - 1);
				generate(consumer, k - 2, root, current.childs[1]);
			}
		}
		current.childs[0] = new Node<Integer>(k);
		current.childs[1] = null;
		generate(consumer, k - 1, root, current.childs[0]);
		current.childs[0] = null;
		current.childs[1] = new Node<Integer>(k);
		generate(consumer, k - 1, root, current.childs[1]);
	}
}
