package org.ip;

import java.util.Deque;
import java.util.LinkedList;

// Uber - Print bfs with spaces
public class TreePrint {
	public static void main(String[] s) {
		Node root = n(0,n(1,n(3),n(4)),n(5,null,n(6)));
		TreePrint print = new TreePrint();
		print.inorder(root);
		print.dfs(root);
	}
	public void inorder(Node root) {
		inorder(root, new IntegerHolder());
	}
	private void inorder(Node root, IntegerHolder holder) {
		if (root == null) return;
		inorder(root.left, holder);
		root.pos = holder.value++;
		inorder(root.right, holder);
	}
	public void dfs(Node root) {
		Deque<Node> queue = new LinkedList<Node>();
		queue.add(root);
		queue.add(SENTINEL);
		Node prev = null;
		while (!queue.isEmpty()) {
			Node current = queue.remove();
			if (current != SENTINEL) {
				int d = prev == null ? current.pos : current.pos - prev.pos - 1;
				for (int i = 0; i < d; i++) {
					System.out.print(" ");
				}
				System.out.print(current.value);
				prev = current;
			} else if (current == SENTINEL && !queue.isEmpty()) {
				System.out.println();
				queue.add(SENTINEL);
				prev = null;
			}
			if (current.left != null) {
				queue.add(current.left);
			}
			if (current.right != null) {
				queue.add(current.right);
			}
		}
	}
	public static final Node SENTINEL = new Node(0,null,null);
	public static Node n(int value) {
		return new Node(value, null, null);
	}
	public static Node n(int value, Node left, Node right) {
		return new Node(value, left, right);
	}
	public static class Node {
		Node left;
		Node right;
		int value;
		int pos;
		public Node(int value, Node left, Node right) {
			this.value=value;
			this.left=left;
			this.right=right;
		}
	}
	public static class IntegerHolder {
		int value = 0;
	}
}
