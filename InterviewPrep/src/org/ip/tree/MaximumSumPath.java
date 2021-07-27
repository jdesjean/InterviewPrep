package org.ip.tree;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/binary-tree-maximum-path-sum/">LC: 124</a>
 */
public class MaximumSumPath {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {6, new Integer[] {1,2,3}};
		Object[] tc2 = new Object[] {42, new Integer[] {-10,9,20,null,null,15,7}};
		Object[] tc3 = new Object[] {1, new Integer[] {1}};
		Object[] tc4 = new Object[] {Integer.MIN_VALUE, new Integer[] {}};
		Object[] tc5 = new Object[] {-1, new Integer[] {-1}};
		Object[] tc6 = new Object[] {3, new Integer[] {-1,2,2}};
		Object[] tc7 = new Object[] {-1, new Integer[] {-1,-2,-3}};
		Object[] tc8 = new Object[] {48, new Integer[] {5,4,8,11,null,13,4,7,2,null,null,null,1}};
		List<Object[]> tcs = Arrays.asList(tc8, tc1, tc2, tc3, tc4, tc5, tc6, tc7);
		Function<TreeNode, Integer>[] solvers = new Function[] {new Solver()};
		for (Object[] tc : tcs) { 
			System.out.print(String.valueOf(tc[0]));
			for (Function<TreeNode, Integer> solver : solvers) {
				System.out.print("," + solver.apply(TreeNode.fromBfs((Integer[]) tc[1])));
			}
			System.out.println();
		}
	}
	public static class Solver implements Function<TreeNode, Integer> {

		@Override
		public Integer apply(TreeNode t) {
			AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);
			_solve(t, max);
			return max.get();
		}
		public int _solve(TreeNode t, AtomicInteger max) {
			if (t == null) return 0;
			int left = _solve(t.left, max);
			int right = _solve(t.right, max);
			int sum = t.val + left + right;
			if (sum > max.get()) {
				max.set(sum);
			}
			return Math.max(t.val +  Math.max(left, right), 0);
		}
	}
}
