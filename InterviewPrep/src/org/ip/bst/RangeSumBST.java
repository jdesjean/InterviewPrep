package org.ip.bst;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import org.ip.tree.TreeNode;

/**
 * <a href="https://leetcode.com/problems/range-sum-of-bst/">LC: 938</a>
 */
public class RangeSumBST {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {32, TreeNode.fromBfs(new Integer[] {10,5,15,3,7,null,18}), new Pair(7, 15)};
		Object[] tc2 = new Object[] {23, TreeNode.fromBfs(new Integer[] {10,5,15,3,7,13,18,1,null,6}), new Pair(6, 10)};
		List<Object[]> tcs = Arrays.asList(tc1, tc2);
		BiFunction<TreeNode, Pair, Integer>[] solvers = new BiFunction[] {new Solver()};
		for (Object[] tc : tcs) { 
			System.out.print(String.valueOf(tc[0]));
			for (BiFunction<TreeNode, Pair, Integer> solver : solvers) {
				System.out.print("," + solver.apply((TreeNode) tc[1], (Pair)tc[2]));
			}
			System.out.println();
		}
	}
	public static class Solver implements BiFunction<TreeNode, Pair, Integer> {

		@Override
		public Integer apply(TreeNode t, Pair u) {
			AtomicInteger sum = new AtomicInteger(0);
			_solve(t, u, sum);
			return sum.get();
		}
		
		void _solve(TreeNode t, Pair p, AtomicInteger sum) {
			if (t == null) return;
			if (t.val >= p.low && t.val <= p.high) {
				sum.addAndGet(t.val);
			}
			if (p.low < t.val) {
				_solve(t.left, p, sum);
			}
			if (p.high > t.val) {
				_solve(t.right, p, sum);
			}
		}
		
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
