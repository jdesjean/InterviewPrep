package org.ip.tree;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/discuss/interview-question/584214/">OA 2020</a>
 * <a href="https://leetcode.com/problems/count-univalue-subtrees/">LC: 250</a>
 */
public class UnivalueSubtree {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				UnivalueSubtree::tc1,
				UnivalueSubtree::tc2,
				UnivalueSubtree::tc3);
		Solver[] solvers = new Solver[] {new Recursive()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static class Recursive implements Solver {

		@Override
		public int solve(TreeNode root) {
			return solve(root, root != null ? root.val : 0).count;
		}
		public Result solve(TreeNode root, int parent) {
			if (root == null) return new Result(0, true);
			if (root.left == null && root.right == null) return new Result(1, root.val == parent);
			Result left = solve(root.left, root.val);
			Result right = solve(root.right, root.val);
			int count = left.count + right.count + (left.equal && right.equal ? 1 : 0);
			return new Result(count, root.val == parent && left.equal && right.equal);
		}
		
	}
	public static class Result {
		int count;
		boolean equal;
		public Result(int count, boolean equal) {
			this.count=count;this.equal=equal;
		}
	}
	public static void tc1(Solver solver) {
		int res = solver.solve(
				new TreeNode(5,
						new TreeNode(1,
								new TreeNode(5),
								new TreeNode(5)),
						new TreeNode(5,
								new TreeNode(5),
								null)));
		System.out.println(res);
	}
	public static void tc2(Solver solver) {
		int res = solver.solve(null);
		System.out.println(res);
	}
	public static void tc3(Solver solver) {
		int res = solver.solve(
				new TreeNode(5,
						new TreeNode(5,
								new TreeNode(5),
								new TreeNode(5)),
						new TreeNode(5,
								null,
								new TreeNode(5))));
		System.out.println(res);
	}
	public interface Solver {
		public int solve(TreeNode root);
	}
}
