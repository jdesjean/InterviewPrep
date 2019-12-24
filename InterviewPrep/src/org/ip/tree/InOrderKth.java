package org.ip.tree;

// EPI 2018: 9.9
public class InOrderKth {
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
		InOrderKth finder = new InOrderKth();
		System.out.println(finder.find(root, 3).value);
		for (int i = 0; i < 5; i++) {
			System.out.println(finder.find(root, i).value);
		}
	}
	public Node find(Node root, int k) {
		if (root == null || k >= root.count) return null;
		int leftCount = root.left != null ? root.left.count : 0;
		if (k - leftCount == 0) return root; // short circuit
		
		Node left = find(root.left, k);
		if (left != null) return left;
		return find(root.right, k - leftCount - 1);
	}
	public static class Node {
		Node left;
		Node right;
		Node parent;
		int value;
		int count = 1;
		public Node(int value) {
			this(value, null, null);
		}
		public Node(int value, Node left, Node right) {
			this.value=value;
			this.left=left;
			this.right=right;
			if (left != null) {
				left.parent = this;
				count+=left.count;
			}
			if (right != null) {
				right.parent = this;
				count+=right.count;
			}
		}
	}
}
