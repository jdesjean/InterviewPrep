package org.ip.array;

import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/missing-element-in-sorted-array/">LC: 1060</a>
 */
public class KthMissingPositiveNumberFromLeftmost {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {5, new int[] {4,7,9,10}, 1};
		Object[] tc2 = new Object[] {8, new int[] {4,7,9,10}, 3};
		Object[] tc3 = new Object[] {6, new int[] {1,2,4}, 3};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
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
			//t[r] + k - (t[r] - a[0] - r)
			//k + t[0] + r
			//r + 1 = l
			//t[0] + k + l - 1
			return t[0] + l + k - 1;
		}
		int rank(int[] a, int i) {
			return a[i] - a[0] - i;
		}
	}
	public interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
