package org.ip.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/binary-tree-paths/">LC: 257</a>
 */
public class BinaryTreePath {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				BinaryTreePath::tc1);
		Solver[] solvers = new Solver[] {new Recursive()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(
				new TreeNode(1,
						new TreeNode(2,
								new TreeNode(5),
								null),
						new TreeNode(3))));
	}
	public static class Recursive implements Solver {

		@Override
		public List<String> solve(TreeNode node) {
			List<String> res = new ArrayList<String>();
			solve(res, new StringBuilder(), node);
			return res;
		}
		
		void solve(List<String> res, StringBuilder current, TreeNode node) {
			if (node == null) {
				return;
			}
			if (current.length() != 0) {
				current.append("->");
			}
			current.append(node.val);
			if (node.left == null && node.right == null) {
				res.add(current.toString());
			}
			solve(res, current, node.left);
			solve(res, current, node.right);
			int lastIndexOf = current.lastIndexOf("->");
			if (lastIndexOf >= 0) {
				current.delete(lastIndexOf, current.length());
			}
		}
		
	}
	public interface Solver {
		public List<String> solve(TreeNode node);
	}
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode() {}
		TreeNode(int val) { this.val = val; }
		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
}
