package org.ip.honor;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import org.ip.tree.Node;
import org.ip.tree.Tree;
import org.ip.tree.TreeTest;

public class TraversalPostOrder implements Iterator<Node> {
	public static void main(String[] s) {
		Tree t = TreeTest.bst1();
		for (Iterator<Node> iterator = new TraversalPostOrder(t.root()); iterator.hasNext();) {
			System.out.println(iterator.next());
		}
	}
	Deque<Node> stack = new LinkedList<Node>();
	Node prev = null;
	public TraversalPostOrder(Node root) {
		stack.push(root);
		addLeft(root);
	}
	public void addLeft(Node node) {
		while (node != null) {
			if (node.getRight() != null) {
				stack.push(node.getRight());
			}
			if (node.getLeft() != null) {
				stack.push(node.getLeft());
			}
			node = node.getLeft();
		}
	}
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}
	@Override
	public Node next() {
		Node node = stack.peek();
		if (!node.isLeaf() && node.getLeft() != prev && node.getRight() != prev) {
			addLeft(node);
		}
		node = stack.pop(); // Can be same node as peeked or from the one just added
		prev = node;
		return node;
	}
}
