package org.ip.tree;

import java.util.function.ToDoubleFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximum-average-subtree/">LC: 1120</a>
 */
public class MaximumAverageSubtree {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {6d, new Integer[] {5,6,1}};
		Object[][] tcs = new Object[][] {tc1};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[])tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public double applyAsDouble(TreeNode value) {
			DoubleHolder holder = new DoubleHolder();
			postOrder(value, holder);
			return holder.value;
		}
		double average(Pair pair) {
			if (pair.count == 0) return 0;
			return pair.sum / (double)pair.count;
		}
		Pair postOrder(TreeNode node, DoubleHolder holder) {
			if (node == null) return new Pair(0,0);
			Pair left = postOrder(node.left, holder);
			Pair right = postOrder(node.right, holder);
			Pair current = new Pair(left.sum + right.sum + node.val, left.count + right.count + 1);
			double val = average(current);
			if (val > holder.value) {
				holder.value = val;
			}
			return current;
		}
		static class DoubleHolder {
			double value;
		}
		static class Pair {
			int sum;
			int count;
			public Pair(int sum, int count) {
				this.sum = sum;
				this.count = count;
			}
		}
	}
	interface Problem extends ToDoubleFunction<TreeNode> {
		
	}
}
