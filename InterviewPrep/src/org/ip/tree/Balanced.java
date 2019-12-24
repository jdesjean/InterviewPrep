package org.ip.tree;

// EPI 2018: 9.1
public class Balanced {
	public static void main(String[] s) {
		Node root = node('a', node('b', node('c', node('d', node('e'), node('f')), node('g')), node('h', node('i'), node('j'))), node('k', node('l', node('m'), node('n')), node('o')));
		System.out.println(new Balanced().isBalanced(root));
		root = node('a', node('b', node('c', node('d', node('e'), node('f')), node('g')), node('h', node('i'), node('j'))), node('k', node('l', node('m'), node('n'))));
		System.out.println(new Balanced().isBalanced(root));
	}
	public boolean isBalanced(Node root) {
		return _isBalanced(root).balanced;
	}
	private Pair _isBalanced(Node root) {
		int min = Integer.MAX_VALUE, max = 0;
		for (Node n : new Node[] {root.left, root.right}) {
			if (n != null) {
				Pair pair = _isBalanced(n);
				if (!pair.balanced) {
					return new Pair(0, pair.balanced);
				}
				min = Math.min(min, pair.height);
				max = Math.max(max, pair.height);
			} else {
				min = 0;
			}
		}
		int diff = max - min;
		return new Pair(max + 1, diff <= 1);
	}
	public static class Pair {
		int height;
		boolean balanced;
		public Pair(int height, boolean balanced) {this.height=height;this.balanced=balanced;}
	}
	public static Node node(char c, Node left) {
		return new Node(c, left, null);
	}
	public static Node node(char c) {
		return new Node(c, null, null);
	}
	public static Node node(char c, Node left, Node right) {
		return new Node(c, left, right);
	}
	public static class Node {
		char value;
		Node left;
		Node right;
		public Node(char c, Node left, Node right) {this.value=c;this.left=left;this.right=right;}
	}
}
