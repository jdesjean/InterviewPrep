package org.ip.tree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

// EPI 2018: 9.2
public class Symmetric {
	public static void main(String[] s) {
		Node root = node(314, node(6, null, node(2, null, node(3))), node(6, node(2, node(3))));
		System.out.println(new Symmetric().isSymmetric(root));
		root = node(314, node(6, null, node(561, null, node(3))), node(6, node(2, node(3))));
		System.out.println(new Symmetric().isSymmetric(root));
		root = node(314, node(6, null, node(2, null, node(3))), node(6, node(2)));
		System.out.println(new Symmetric().isSymmetric(root));
	}
	public boolean isSymmetric(Node node) {
		Iterator<Node> iLeft = new DfsIterator(node.left);
		Iterator<Node> iRight = new DfsIteratorReversed(node.right);
		for (; iLeft.hasNext() && iRight.hasNext(); ) {
			Node nLeft = iLeft.next();
			Node nRight = iRight.next();
			if (nLeft != null && nRight == null) {
				return false;
			} else if (nLeft == null && nRight != null) {
				return false;
			} else if (nLeft != null && nRight != null && nLeft.value != nRight.value) {
				return false;
			}
		}
		return true;
	}
	public Iterator<Node> dfs(Node node) {
		return new DfsIterator(node);
	}
	public static class DfsIterator implements Iterator<Node> {
		Deque<Node> queue = new LinkedList<Node>();
		public DfsIterator(Node node) {
			queue.addLast(node);
			while (node.left != null) {
				queue.addLast(node.left);
				node = node.left;
			}
			queue.addLast(null);
		}
		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}
		@Override
		public Node next() {
			Node node = queue.removeFirst();
			if (node != null) {
				if (node.right != null) {
					queue.addLast(node.right);
					Node tmp = node.left;
					while (tmp != null) {
						queue.addLast(tmp);
						tmp = tmp.left;
					}
				} else {
					queue.addLast(node.right);
				}
			}
			return node;
		}
	}
	public static class DfsIteratorReversed implements Iterator<Node> {
		Deque<Node> queue = new LinkedList<Node>();
		public DfsIteratorReversed(Node node) {
			queue.addLast(node);
			while (node.right != null) {
				queue.addLast(node.right);
				node = node.right;
			}
			queue.addLast(null);
		}
		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}
		@Override
		public Node next() {
			Node node = queue.removeFirst();
			if (node != null) {
				if (node.left != null) {
					queue.addLast(node.left);
					Node tmp = node.right;
					while (tmp != null) {
						queue.addLast(tmp);
						tmp = tmp.right;
					}
				} else {
					queue.addLast(node.left);
				}
			}
			return node;
		}
	}
	public static Node node(int c, Node left) {
		return new Node(c, left, null);
	}
	public static Node node(int c) {
		return new Node(c, null, null);
	}
	public static Node node(int c, Node left, Node right) {
		return new Node(c, left, right);
	}
	public static class Node {
		int value;
		Node left;
		Node right;
		public Node(int c, Node left, Node right) {this.value=c;this.left=left;this.right=right;}
	}
}
