package org.ip.array;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/find-pivot-index/solution/">LC: 724</a>
 */
public class PivotIndex {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new int[] {1,7,3,6,5,6}};
		Object[] tc2 = new Object[] {-1, new int[] {1,2,3}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			int sum = 0;
			for (int i = 0; i < value.length; i++) sum += value[i];
			int prefix = 0;
			int res = -1;
			for (int i = value.length - 1; i >= 0; i--) {
				if (sum - value[i] - prefix == prefix) res = i;
				prefix += value[i];
			}
			return res;
		}
		
	}
	interface Problem extends ToIntFunction<int[]> {
		
	}
}
