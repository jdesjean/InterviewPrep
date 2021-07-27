package org.ip.array;

import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/">LC: 1218</a>
 */
public class LongestArithmeticSubsequenceGivenDifference {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4, new int[] {1,2,3,4}, 1};
		Object[] tc2 = new Object[] {1, new int[] {1,3,5,7}, 1};
		Object[] tc3 = new Object[] {4, new int[] {1,5,7,8,5,3,4,2,1}, -2};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			Map<Integer, Integer> map = new HashMap<>();
			int max = 0;
			for (int i = 0; i < t.length; i++) {
				int count = map.getOrDefault(t[i] - k, 0) + 1;
				map.put(t[i], count);
				max = Math.max(max, count);
			}
			return max;
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
