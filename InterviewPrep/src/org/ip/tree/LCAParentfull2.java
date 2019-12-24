package org.ip.tree;

// EPI 2018: 9.4
public class LCAParentfull2 {
	public static void main(String[] s) {
		Node root = new Node(45,
				new Node(25, new Node(15, new Node(10), new Node(20)),
						new Node(30)),
				new Node(65, new Node(55, new Node(50), new Node(60)),
						new Node(75, null, new Node(80))));
		LCAParentfull2 lca = new LCAParentfull2();
		System.out.println(lca.solve(root, 10, 20));
		System.out.println(lca.solve(root, 50, 80));
		System.out.println(lca.solve(root, 20, 60));
		System.out.println(lca.solve(root, 20, 90));
	}
	public Node solve(Node root, int v1, int v2) {
		NodeDepth left = _solve(root, v1, 0);
		NodeDepth right = _solve(root, v2, 0);
		if (left == null || right == null) {
			return null;
		}
		NodeDepth deepest = left;
		NodeDepth smallest = right;
		if (left.depth > right.depth) {
			deepest = left;
			smallest = right;
		} else if (right.depth > left.depth) {
			deepest = right;
			smallest = left;
		}
		if (deepest != null) {
			while (deepest.depth > smallest.depth) {
				deepest.node = deepest.node.parent;
				deepest.depth--;
			}
		}
		while (deepest.node != smallest.node) {
			deepest.node = deepest.node.parent;
			smallest.node = smallest.node.parent;
		}
		return deepest.node;
	}
	public NodeDepth _solve(Node root, int value, int depth) {
		if (root == null) return null;
		if (root.value  == value) {
			return new NodeDepth(root, depth);
		}
		for (Node n : new Node[] {root.left, root.right}) {
			NodeDepth nd = _solve(n, value, depth + 1);
			if (nd != null) return nd;
		}
		return null;
	}
	public static class NodeDepth {
		int depth;
		Node node;
		public NodeDepth(Node node, int depth) {this.node=node;this.depth=depth;}
	}
	public static class Node {
		public Node parent;
		public Node left;
		public Node right;
		public int value;
		public Node(int value) {this.value=value;}
		public Node(int value, Node right) {
			this.value=value;
			this.right=right;
			if (right != null) right.parent=this;
		}
		public Node(int value, Node left, Node right) {
			this.value=value;
			this.left=left;
			this.right=right;
			if (left != null) left.parent=this;
			if (right != null) right.parent=this;
		}
		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
}
