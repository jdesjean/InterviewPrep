package org.ip.array;

import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/kth-largest-element-in-an-array/">LC: 215</a>
 */
public class KthLargest {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {5, new int[] {3,2,1,5,6,4}, 2};
		Object[] tc2 = new Object[] {4, new int[] {3,2,3,1,2,4,5,5,6}, 4};
		Object[] tc3 = new Object[] {2, new int[] {2,2,2,2,1}, 2};
		Object[] tc4 = new Object[] {1, new int[] {1,1,1,1,1,2}, 2};
		Object[] tc5 = new Object[] {1, new int[] {1,1}, 3};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u - 1;
			int l = 0;
			for (int r = t.length - 1; l < r; ) {
				int m = l + (r - l) / 2;
				m = partition(t, l, r, m);
				if (m < k) {
					l = m + 1;
				} else if (m > k) {
					r = m - 1;
				} else {
					return t[m];
				}
			}
			return t[l];
		}
		int partition(int[] a, int l, int r, int p) {
			int pv = a[p];
			for (int m = l; m <= r; ) {
				if (a[m] > pv) {
					Utils.swap(a, l++, m++);
				} else if (a[m] < pv) {
					Utils.swap(a, m, r--);
				} else {
					m++;
				}
			}
			return l;
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
