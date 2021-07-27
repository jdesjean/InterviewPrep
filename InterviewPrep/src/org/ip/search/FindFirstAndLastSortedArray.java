package org.ip.search;

import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/">LC: 34</a>
 */
public class FindFirstAndLastSortedArray {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {3, 4}, new int[] {5,7,7,8,8,10}, 8};
		Object[] tc2 = new Object[] {new int[] {-1, -1}, new int[] {5,7,7,8,8,10}, 6};
		Object[] tc3 = new Object[] {new int[] {-1, -1}, new int[0], 0};
		Object[] tc4 = new Object[] {new int[] {0, 1}, new int[] {2,2}, 2};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public int[] apply(int[] t, Integer u) {
			int l = 0, r = t.length - 1;
			int target = u;
			for (; l <= r; ) {
				int m = l + (r - l) / 2;
				int v = t[m];
				if (v == target) {
					return new int[] {min(t, l, m, target), max(t, m, r, target)}; 
				} else if (v < target) {
					l = m + 1;
				} else if (v > target) {
					r = m - 1;
				}
			}
			return new int[] {-1, -1};
		}
		
		int max(int[] t, int l, int r, int u) {
			int idx = -1;
			for (; l <= r; ) {
				int m = l + (r - l) / 2;
				int v = t[m];
				if (v == u) {
					idx = m;
					l = m + 1;
				} else if (v < u) {
					l = m + 1;
				} else if (v > u) {
					r = m - 1;
				}
			}
			return idx;
		}
		
		int min(int[] t, int l, int r, int u) {
			int idx = -1;
			for (; l <= r; ) {
				int m = l + (r - l) / 2;
				int v = t[m];
				if (v == u) {
					idx = m;
					r = m - 1;
				} else if (v < u) {
					l = m + 1;
				} else if (v > u) {
					r = m - 1;
				}
			}
			return idx;
		}
	}
	public interface Problem extends BiFunction<int[], Integer, int[]> {
		
	}
}
