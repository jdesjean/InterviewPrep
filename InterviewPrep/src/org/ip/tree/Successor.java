package org.ip.tree;

// EPI 2018: 9.10
public class Successor {
	public static void main(String[] s) {
		Node root = new Node(45,
				new Node(25, 
						new Node(15, 
								new Node(10), 
								new Node(20)),
						new Node(30)),
				new Node(65, 
						new Node(55, 
								new Node(50), 
								new Node(60)),
						new Node(75, 
								null, 
								new Node(80))));
		Successor finder = new Successor();
		System.out.println(finder.find(root.left.left.left));
		System.out.println(finder.find(root.left.left.right));
		System.out.println(finder.find(root.left.left).value);
		System.out.println(finder.find(root.right.right.right));
	}
	public Node find(Node node) {
 		if (node == null) return null;
		if (node.right != null) {
			return getRight(node);
		} else {
			Node prev = node;
			while (node.parent != null) {
				prev = node;
				node = node.parent;
				if (node.left == prev) {
					return node;
				}
			}
		}
		return null;
	}
	public Node getRight(Node node) {
		node = node.right;
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}
	public static class Node {
		Node left;
		Node right;
		Node parent;
		int value;
		public Node(int value) {
			this(value, null, null);
		}
		public Node(int value, Node left, Node right) {
			this.value=value;
			this.left=left;
			this.right=right;
			if (left != null) {
				left.parent = this;
			}
			if (right != null) {
				right.parent = this;
			}
		}
		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}
}
