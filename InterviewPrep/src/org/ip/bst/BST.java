package org.ip.bst;

import java.util.function.BiFunction;

import org.ip.Test;
import org.ip.tree.TreeNode;

public class BST {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 4, new Integer[] {3,2,4,1}, 4};
		Object[] tc2 = new Object[] { 4, new Integer[] {3,2,4,1}, 1};
		Object[] tc3 = new Object[] { 4, new Integer[] {3,2,4,1}, 2};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	
	static class Solver implements Problem {

		@Override
		public TreeNode apply(TreeNode t, Integer u) {
			return delete(t, u);
		}
		public TreeNode search(TreeNode root, int value) {
			if (root == null) {
				return null;
			}
			if (value < root.val) {
				return search(root.left, value);
			} else if (value > root.val) {
				return search(root.right, value);
			} else {
				return root;
			}
		}
		public TreeNode insert(TreeNode root, int value) {
			if (root == null) {
				return new TreeNode(value);
			}
			if (value < root.val) {
				root.left = insert(root.left, value);
			} else if (value > root.val) {
				root.right = insert(root.right, value);
			} // else do nothing
			return root; 
		}
		public TreeNode delete(TreeNode root, int value) {
			if (root == null) {
				return null;
			}
			if (value < root.val) {
				root.left = delete(root.left, value);
			} else if (value > root.val) {
				root.right = delete(root.right, value);
			} else {
				TreeNode next = next(root);
				if (next == null) {
					TreeNode prev = prev(root);
					if (prev == null) {
						return null;
					} else {
						root.val = prev.val;
						root.left = delete(root.left, prev.val);
					}
				} else {
					root.val = next.val;
					root.right = delete(root.right, next.val);
				}
			}
			return root;
		}
		
		TreeNode next(TreeNode root) {
			if (root == null || root.right == null) {
				return null;
			}
			root = root.right;
			while (root.left != null) {
				root = root.left;
			}
			return root;
		}
		TreeNode prev(TreeNode root) {
			if (root == null || root.left == null) {
				return null;
			}
			root = root.left;
			while (root.right != null) {
				root = root.right;
			}
			return root;
		}
		
	}
	interface Problem extends BiFunction<TreeNode, Integer, TreeNode> {
		
	}
}
