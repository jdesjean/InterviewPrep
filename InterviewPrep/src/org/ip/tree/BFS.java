package org.ip.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// EPI 2018: 8.6
public class BFS {
	public static void main(String[] s) {
		Node root = node(314,node(6,node(271,node(28),node(0)),node(561,null,node(3,node(17)))),node(6,node(2,null,node(1,node(401,node(641)),node(257))),node(271,null,node(28))));
		List<List<Node>> result = new BFS().bfs(root);
		for (List<Node> current : result) {
			for (Node n : current) {
				System.out.print(n.value);
				System.out.print(",");
			}
			System.out.println();
		}
	}
	public List<List<Node>> bfs(Node root) {
		Deque<Node> queue = new LinkedList<Node>();
		List<List<Node>> list = new LinkedList<List<Node>>();
		queue.addLast(root);
		queue.addLast(Node.SENTINEL);
		List<Node> current = new LinkedList<Node>();
		while (!queue.isEmpty()) {
			Node n = queue.removeFirst();
			if (n == Node.SENTINEL) {
				list.add(current);
				current = new LinkedList<Node>();
				if (!queue.isEmpty()) {
					queue.addLast(Node.SENTINEL);
				}
				continue;
			}
			if (n.left != null) {
				queue.addLast(n.left);
			}
			if (n.right != null) {
				queue.addLast(n.right);
			}
			current.add(n);
		}
		return list;
	}
	public static Node node(int value) {
		return new Node(value, null, null);
	}
	public static Node node(int value, Node left) {
		return new Node(value, left, null);
	}
	public static Node node(int v, Node left, Node right) {
		return new Node(v, left, right);
	}
	public static class Node {
		public final static Node SENTINEL = new Node(0, null, null);
		public Node left;
		public Node right;
		public int value;
		public Node(int value, Node left, Node right) {this.value=value;this.left=left; this.right=right;}
		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
			
}
