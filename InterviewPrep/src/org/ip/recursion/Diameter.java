package org.ip.recursion;

// EPI: 16.11
public class Diameter {
	public static void main(String[] s) {
		Node node = node(0, node(7, node(4, node(6)), node(3)), node(14), node(3, node(2), node(1, node(6), node(4, node(4), node(2, node(1), node(2), node(3))))));
		System.out.println(diameter(node) + ": 31"); 
	}
	public static int diameter(Node tree) {
		Pair pair = RecursiveDiameter(tree);
		return Math.max(pair.max, pair.d);
	}
	public static Pair RecursiveDiameter(Node tree) {
		if (tree == null) return new Pair(0,0);
		int max = 0;
		int d1 = 0;
		int d2 = 0;
		for (Node node : tree.nodes) {
			Pair pair = RecursiveDiameter(node);
			max = Math.max(max, pair.max);
			if (pair.d > d1) {
				d2 = d1;
				d1 = pair.d;
			} else if (pair.d > d2) {
				d2 = pair.d;
			}
		}
		return new Pair(Math.max(d1 + d2, max), tree.value + d1);
	}
	public static class Pair {
		int max;
		int d;
		public Pair(int max, int d) {this.max=max; this.d=d;}
	}
	public static Node node(int value) {
		return new Node(new Node[0], value);
	}
	public static Node node(int value, Node left) {
		return new Node(new Node[] {left}, value);
	}
	public static Node node(int value, Node left, Node right) {
		return new Node(new Node[] {left, right}, value);
	}
	public static Node node(int value, Node left, Node center, Node right) {
		return new Node(new Node[] {left, center, right}, value);
	}
	public static class Node {
		Node[] nodes;
		int value;
		public Node(Node[] nodes, int value) {this.nodes=nodes;this.value=value;}
	}
}
