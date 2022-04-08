package org.ip.array;

import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/">LC: 325</a>
 */
public class MaximumSubArraySum {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4, new int[] {1,-1,5,-2,3}, 3};
		Object[] tc2 = new Object[] {2, new int[] {-2,-1,2,1}, 1};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			Map<Integer, Integer> sums = new HashMap<>();
			sums.put(0, -1);
			int res = 0;
			for (int i = 0, sum = 0; i < t.length; i++) {
				sum += t[i];
				Integer idx = sums.get(sum - k);
				if (idx != null) {
					res = Math.max(res, i - idx);
				}
				sums.putIfAbsent(sum, i);
			}
			return res;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			Map<Integer, Integer> sums = new HashMap<>();
			for (int i = 0, sum = 0; i < t.length; i++) {
				sum += t[i];
				sums.put(sum, i);
			}
			int res = 0;
			for (int i = 0, sum = 0; i < t.length; i++) {
				Integer idx = sums.get(sum + k);
				if (idx != null) {
					res = Math.max(res, idx - i + 1);
				}
				sum += t[i];
			}
			return res;
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
