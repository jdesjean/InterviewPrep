package org.ip;

import java.util.Deque;
import java.util.LinkedList;

//https://leetcode.com/discuss/interview-question/350363/Google-or-OA-2018-or-Max-Distance
public class BinaryStringMaxDistance {
	public static void main(String[] s) {
		//1011000 and 1011110 is 1011
		//len("000") + len("110") = 3 + 3 = 6
		System.out.println(new BinaryStringMaxDistance().solve(new String[] {"1011000", "1011110"}));
		System.out.println(new BinaryStringMaxDistance().solve(new String[] {"1011000", "1011110", "0"}));
		System.out.println(new BinaryStringMaxDistance().solve(new String[] {"1011000", "1011110", "10110000"}));
		System.out.println(new BinaryStringMaxDistance().solve(new String[] {"1", "0"}));
	}
	public int solve(String[] s) {
		Node root = createTree(s);
		return find(root);
	}
	private Node createTree(String[] s) {
		Node root = new Node(0);
		for (String string : s) {
			if (string == null || string.length() == 0) continue;
			root.maxLength = Math.max(root.maxLength, string.length());
			createTree(root, string, 0);
		}
		return root;
	}
	private void createTree(Node parent, String s, int index) {
		if (index >= s.length()) return;
		char c = s.charAt(index);
		if (c == '0') {
			if (parent.left == null) {
				parent.left = new Node(0);
			}
			parent.left.maxLength = Math.max(parent.left.maxLength, s.length() - index);			
			if (index == s.length() - 1) {
				parent.left.isEnd = true;
			} else {
				createTree(parent.left, s, index + 1);
			}
		} else if (c == '1') {
			if (parent.right == null) {
				parent.right = new Node(0);
			}
			parent.right.maxLength = Math.max(parent.right.maxLength, s.length() - index);
			if (index == s.length() - 1) {
				parent.right.isEnd = true;
			} else {
				createTree(parent.right, s, index + 1);
			}
			
			
		}
		
	}
	private int find(Node root) {
		if (root == null) return 0;
		Deque<Node> queue = new LinkedList<Node>();
		queue.add(root);
		int max = 0;
		while (!queue.isEmpty()) {
			Node node = queue.removeFirst();
			if (node.left != null && node.right != null) {
				max = Math.max(max, node.left.maxLength + node.right.maxLength);
			} else if (node.isEnd) {
				max = Math.max(max, node.left != null ? node.left.maxLength : node.right != null ? node.right.maxLength : 0);
			}
			if (node.left != null) {
				queue.addLast(node.left);
			}
			if (node.right != null) {
				queue.addLast(node.right);
			}
			
		}
		
		return max;
	}
	private static class Node {
		int maxLength;
		boolean isEnd;
		Node left;
		Node right;
		public Node(int maxLength) {
			this.maxLength = maxLength;
		}
	}
}
