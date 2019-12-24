package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

//EPI 2018: 9.7
public class IteratorOrderPre implements Iterator<BasicNode>{
	public static void main(String[] s) {
		BasicNode root = new BasicNode(45,
				new BasicNode(25, new BasicNode(15, new BasicNode(10), new BasicNode(20)),
						new BasicNode(30)),
				new BasicNode(65, new BasicNode(55, new BasicNode(50), new BasicNode(60)),
						new BasicNode(75, null, new BasicNode(80))));
		for (Iterator<BasicNode> it = new IteratorOrderPre(root); it.hasNext();) {
			BasicNode node = it.next();
			System.out.println(node);
		}
	}
	Deque<BasicNode> stack = new LinkedList<BasicNode>();
	public IteratorOrderPre(BasicNode root) {
		stack.push(root);
	}

	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public BasicNode next() {
		BasicNode node = stack.pop();
		if (node.right != null) {
			stack.push(node.right);
		}
		if (node.left != null) {
			stack.push(node.left);
		}
		return node;
	}
}
