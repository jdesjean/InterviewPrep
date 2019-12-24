package org.ip.tree;

import java.util.Deque;
import java.util.LinkedList;

// EPI 2018: 9.16
public class Sibling {
	public static void main(String[] s) {
		Node root = n('A', 
				n('B', 
						n('C', n('D'), n('E')), 
						n('F', n('G'), n('H'))), 
				n('I', 
						n('J', n('K'), n('L')), 
						n('M', n('N'), n('O'))));
		new Sibling().solve(root);
		for (IteratorOrderIn it = new IteratorOrderIn(root); it.hasNext(); ) {
			Node node = (Node)it.next();
			System.out.println((char)(node.value + 'A') + ":" + (node.next != null ? (char)(node.next.value + 'A') : "null"));
		}
	}
	Node SENTINEL = new Node(0);
	public void solve(Node root) {
		Deque<Node> queue = new LinkedList<Node>();
		queue.addLast(root);
		queue.addLast(SENTINEL);
		Node prev = null;
		while (!queue.isEmpty()) {
			Node current = queue.removeFirst();
			if (current == SENTINEL) {
				prev = null;
				if (!queue.isEmpty()) {
					queue.addLast(SENTINEL);
				}
				continue;
			} else if (prev != null) {
				prev.next = current;
			}
			if (current.left != null) {
				queue.addLast((Node)current.left);
			}
			if (current.right != null) {
				queue.addLast((Node)current.right);
			}
			prev = current;
		}
	}
	private static Node n(int value) {
		return new Node (value - 'A');
	}
	private static Node n(int value, Node l, Node r) {
		return new Node (value - 'A', (BasicNode)l, (BasicNode)r);
	}
	public static class Node extends BasicNode{
		public BasicNode next;
		public Node(int value) {
			super(value);
		}
		public Node(int value, BasicNode l, BasicNode r) {
			super(value, l, r);
		}
	}
}
