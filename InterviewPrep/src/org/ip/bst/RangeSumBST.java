package org.ip.bst;

import java.util.function.ToIntBiFunction;

import org.ip.Test;
import org.ip.tree.TreeNode;

/**
 * <a href="https://leetcode.com/problems/range-sum-of-bst/">LC: 938</a>
 */
public class RangeSumBST {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {32, TreeNode.fromBfs(new Integer[] {10,5,15,3,7,null,18}), new Pair(7, 15)};
		Object[] tc2 = new Object[] {23, TreeNode.fromBfs(new Integer[] {10,5,15,3,7,13,18,1,null,6}), new Pair(6, 10)};
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public int applyAsInt(TreeNode t, Pair u) {
			if (t == null) return 0;
			if (t.val < u.low) {
				return applyAsInt(t.right, u);
			} else if (t.val > u.high) {
				return applyAsInt(t.left, u);
			} else {
				int sum = t.val;
				if (u.low < t.val) {
					sum += applyAsInt(t.left, u);
				}
				if (u.high > t.val) {
					sum += applyAsInt(t.right, u);
				}
				return sum;
			}
		}
		
	}
	interface Problem extends ToIntBiFunction<TreeNode, Pair> {
		
	}
	public static class Pair {
		int low;
		int high;
		public Pair(int low, int high) {
			this.low = low;
			this.high = high;
		}
	}
}
