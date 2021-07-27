package org.ip.array;

import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/kth-missing-positive-number/">LC: 1539</a>
 */
public class KthMissingPositiveNumber {
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
			int k = u;
			int l = 0, r = t.length - 1;
			for (; l <= r; ) {
				int m = l + (r - l) / 2;
				int rank = rank(t, m);
				if (rank < k) {
					l = m + 1;
				} else if (rank >= k) {
					r = m - 1;
				}
			}
			//t[r] + k - rank(t, r)
			//t[r] + k - (t[r] - 1 - r)
			//k + 1 + r
			//r + 1 = l
			//k + l
			return l + k;
		}
		int rank(int[] a, int i) {
			return a[i] - 1 - i;
		}
	}
	public interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
