package org.ip.array;

import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/">LC: 1423</a>
 */
public class MaxPointsFromCards {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 12, new int[] {1,2,3,4,5,6,1}, 3};
		Object[] tc2 = new Object[] { 4, new int[] {2,2,2}, 2};
		Object[] tc3 = new Object[] { 55, new int[] {9,7,7,9,7,7,9}, 7};
		Object[] tc4 = new Object[] { 1, new int[] {1,1000,1}, 1};
		Object[] tc5 = new Object[] { 202, new int[] {1,79,80,1,1,1,200,1}, 3};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver(), new SolverConstantMem() };
		Test.apply(solvers, tcs);
	}
	static class SolverConstantMem implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			int max = 0;
			int sum = 0;
			for (int i = 0; i < k; i++) {
				sum += t[i];
				max = Math.max(max, sum);
			}
			for (int i = t.length - 1, j = k - 1; i >= t.length - k; i--, j--) {
				sum += t[i] - t[j];
				max = Math.max(max, sum);
			}
			return max;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			int[] cache = new int[k];
			int max = 0;
			for (int i = 0; i < k; i++) {
				if (i == 0) cache[i] = t[i];
				else cache[i] = cache[i - 1] + t[i];
				max = Math.max(max, cache[i]);
			}
			int sum = 0;
			for (int i = t.length - 1, j = k - 2; i >= t.length - k; i--, j--) {
				sum += t[i];
				int cacheValue = j >= 0 ? cache[j] : 0;
				max = Math.max(max, cacheValue + sum);
			}
			return max;
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
