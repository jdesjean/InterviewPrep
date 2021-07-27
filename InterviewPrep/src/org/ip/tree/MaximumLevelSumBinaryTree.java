package org.ip.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * OA 2019 <a href=
 * "https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/">LC:
 * 1161</a>
 * 
 *     1 
 *  7    0
 * 7 -8
 */
public class MaximumLevelSumBinaryTree {
	public static void main(String[] s) {
		System.out.println(new MaximumLevelSumBinaryTree().solve(new TreeNode(1, new TreeNode(7, new TreeNode(7), new TreeNode(-8)), new TreeNode(0))));
	}
	public int solve(TreeNode root) {
		if (root == null) return 0;
		Deque<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		queue.addLast(SENTINEL);
		int level = 1;
		int maxValue = Integer.MIN_VALUE;
		int maxLevel = 0;
		int sumLevel = 0;
		while (!queue.isEmpty()) {
			TreeNode current = queue.removeFirst();
			if (current == SENTINEL) {
				if (sumLevel > maxValue) {
					maxValue = sumLevel;
					maxLevel = level;
				}
				sumLevel = 0;
				level++;
				if (!queue.isEmpty()) {
					queue.add(SENTINEL);
				}
				continue;
			}
			sumLevel += current.val;
			if (current.left != null) {
				queue.addLast(current.left);
			}
			if (current.right != null) {
				queue.addLast(current.right);
			}
		}
		return maxLevel;
	}
	private final static TreeNode SENTINEL = new TreeNode();

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
}
