package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

// EPI 2018: 9.7
public class IteratorOrderIn implements Iterator<BasicNode> {
	public static void main(String[] s) {
		BasicNode root = new BasicNode(45,
				new BasicNode(25, new BasicNode(15, new BasicNode(10), new BasicNode(20)),
						new BasicNode(30)),
				new BasicNode(65, new BasicNode(55, new BasicNode(50), new BasicNode(60)),
						new BasicNode(75, null, new BasicNode(80))));
		for (Iterator<BasicNode> it = new IteratorOrderIn(root); it.hasNext();) {
			BasicNode node = it.next();
			System.out.println(node);
		}
	}
	Deque<BasicNode> stack = new LinkedList<BasicNode>();
	public IteratorOrderIn(BasicNode root) {
		addLeft(root);
	}
	private void addLeft(BasicNode node) {
		if (node == null) return;
		stack.push(node);
		while (node.left != null) {
			stack.push(node.left);
			node = node.left;
		}
	}
	public boolean hasNext() {
		return !stack.isEmpty();
	}
	public BasicNode next() {
		BasicNode node = stack.pop();
		addLeft(node.right);
		return node;
	}
}
