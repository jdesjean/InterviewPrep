package org.ip.tree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/diameter-of-binary-tree/">LC: 543</a>
 * EPI: 16.11
 */
public class Diameter {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new Integer[] {1,2,3,4,5}};
		Object[] tc2 = new Object[] {6, new Integer[] {1,2,3,4,5,null,null,6,null,7,null,8,null,9,null}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public Integer apply(TreeNode t) {
			AtomicInteger max = new AtomicInteger();
			_solve(t, max);
			return max.get();
		}
		
		int _solve(TreeNode node, AtomicInteger max) {
			if (node == null) {
				return 0;
			}
			int left = _solve(node.left, max);
			int right = _solve(node.right, max);
			int sum = left + right;
			if (sum > max.get()) {
				max.set(sum);
			}
			return Math.max(left, right) + 1;
		}
		
	}
	public interface Problem extends Function<TreeNode, Integer> {
		
	}
}
