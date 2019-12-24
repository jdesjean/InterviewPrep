package org.ip.tree;

// EPI 2018: 9.5
public class SumBinary {
	public static void main(String[] s) {
		BasicNode root = 
			node(1, 
				node(0, 
						node(0, 
								node(0), 
								node(1)), 
						node(1, 
								node(1, 
										node(0), null))), 
				node(1, 
						node(0, 
								node(0, 
										node(1, 
												node(1)), 
										node(0))), 
						node(0, 
								node(0))));
		System.out.println(new SumBinary().sum(root)); //126
	}
	public int sum(BasicNode root) {
		return _sum(root, 0);
	}
	public int _sum(BasicNode root, int sum) {
		if (root == null) return 0;
		int current = sum * 2 + root.value;
		if (root.left == null && root.right == null) {
			return current;
		} else {
			return _sum(root.left, current) + _sum(root.right, current);
		}
	}
	public static BasicNode node(int value, BasicNode right) {
		return new BasicNode(value, right);
	}
	public static BasicNode node(int value) {
		return new BasicNode(value);
	}
	public static BasicNode node(int value, BasicNode left, BasicNode right) {
		return new BasicNode(value, left, right);
	}
}
