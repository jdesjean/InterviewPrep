package org.ip.array;

import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/kth-missing-positive-number/">LC: 1539</a>
 */
public class KthMissingPositiveNumber2 {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {9, new int[] {2,3,4,7,11}, 5};
		Object[] tc2 = new Object[] {6, new int[] {1,2,3,4}, 2};
		Object[] tc3 = new Object[] {1, new int[] {2,3,4,7,11}, 1};
		Object[] tc4 = new Object[] {12, new int[] {2,3,4,7,11}, 7};
		Object[] tc5 = new Object[] {1, new int[] {3,4,5,8,12}, 1};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			if (t.length == 0) return -1;
			int k = u;
			int l = 0;
			for (int r = t.length - 1; l <= r; ) {
				int m = l + (r - l) / 2;
				if (t[m] < k + 1 + m) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			return l + k;
		}
		
	}
	public interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
