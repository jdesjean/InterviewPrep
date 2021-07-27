package org.ip.tree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/largest-bst-subtree/">LC: 333</a>
 */
public class LargestBSTSubtree {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new Integer[] {10,5,15,1,8,null,7}};
		Object[] tc2 = new Object[] {4, new Integer[] {10,5,null,1,8,null,null}};
		Object[] tc3 = new Object[] {2, new Integer[] {4,2,7,2,3,5,null,2,null,null,null,null,null,1}};
		Object[] tc4 = new Object[] {1, new Integer[] {1,2}};
		Object[] tc5 = new Object[] {2, new Integer[] {2,3,null,1}};
		Object[] tc6 = new Object[] {2, new Integer[] {3,2,4,null,null,1}};
		Object[] tc7 = new Object[] {2, new Integer[] {4,2,7,2,3,5,null,2,null,null,null,null,null,1}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6, tc7};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[])tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public Integer apply(TreeNode node) {
			AtomicInteger max = new AtomicInteger();
			_apply(node, max);
			return max.get();
		}
		Pair _apply(TreeNode node, AtomicInteger gMax) {
			if (node == null) return new Pair(Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
			Pair left = _apply(node.left, gMax);
			Pair right = _apply(node.right, gMax);
			int min = Math.min(Math.min(left.min, right.min), node.val);
			int max = Math.max(Math.max(left.max, right.max), node.val);
			if (node.val > left.max && node.val < right.min) {
				int sum = 1 + left.count + right.count;
				if (sum > gMax.get()) {
					gMax.set(sum);
				}
				return new Pair(min, max, sum);
			} else {
				return new Pair(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
			}
		}
		public static class Pair {
			int min;
			int max;
			int count;
			public Pair(int min, int max, int count) {
				this.min = min;
				this.max = max;
				this.count = count;
			}
		}
	}
	public interface Problem extends Function<TreeNode, Integer> {
		
	}
}
