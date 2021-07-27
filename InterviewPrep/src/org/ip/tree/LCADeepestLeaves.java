package org.ip.tree;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/">LC: 865</a>
 */
public class LCADeepestLeaves {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {2,7,4}, new Integer[] {3,5,1,6,2,0,8,null,null,7,4}};
		Object[] tc2 = new Object[] {new int[] {1}, new Integer[] {1}};
		Object[] tc3 = new Object[] {new int[] {2}, new Integer[] {0,1,3,null,2}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public TreeNode apply(TreeNode t) {
			Result result = _apply(t);
			if (result == null) return null;
			return result.candidate;
		}
		Result _apply(TreeNode t) {
			if (t == null) return new Result(null, 0);
			Result left = _apply(t.left);
			Result right = _apply(t.right);
			if (left.depth == right.depth) { // this node is candidate
				return new Result(t, left.depth + 1);
			} else if (left.depth > right.depth) {
				left.depth++;
				return left;
			} else {
				right.depth++;
				return right;
			}
		}
		static class Result {
			TreeNode candidate;
			int depth;
			public Result(TreeNode candidate, int depth) {
				super();
				this.candidate = candidate;
				this.depth = depth;
			}
			
		}
	}
	interface Problem extends Function<TreeNode, TreeNode> {
		
	}
}
