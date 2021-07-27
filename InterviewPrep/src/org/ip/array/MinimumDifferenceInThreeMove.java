package org.ip.array;

import java.util.Arrays;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-difference-between-largest-and-smallest-value-in-three-moves/">LC: 1509</a>
 */
public class MinimumDifferenceInThreeMove {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 0, new int[] {5,3,2,4} };
		Object[] tc2 = new Object[] { 1, new int[] {1,5,0,10,14} };
		Object[] tc3 = new Object[] { 2, new int[] {6,6,0,1,1,4,6} };
		Object[] tc4 = new Object[] { 1, new int[] {1,5,6,14,15} };
		Object[] tc5 = new Object[] { 31, new int[] {20,66,68,57,45,18,42,34,37,58} };
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			if (value == null || value.length < 5) return 0;
			int k = 3;
			quickSelect(value, value.length - k - 1, value.length - 1);
			quickSelect(value, k, value.length - k - 2);
			Arrays.sort(value, 0, k + 1);
			Arrays.sort(value, value.length - k - 1, value.length);
			int min = Integer.MAX_VALUE;
			for (int i = 0; i <= k; i++) {
				min = Math.min(min, value[value.length - i - 1] - value[k - i]);
			}
			return min;
		}
		void quickSelect(int[] a, int k, int r) {
			for (int l = 0; l < r; ) {
				int m = l + (r - l) / 2;
				m = partition(a, l, r, a[m]);
				if (m == k) {
					return;
				} else if (m < k) {
					l = m + 1;
				} else {
					r = m;
				}
			}
		}
		int partition(int[] a, int l, int r, int pivot) {
			for (int m = l; m <= r; ) {
				if (a[m] == pivot) {
					m++;
				} else if (a[m] < pivot) {
					swap(a, l++, m++);
				} else {
					swap(a, m, r--);
				}
			}
			return l;
		}
		void swap(int[] a, int l, int r) {
			int tmp = a[l];
			a[l] = a[r];
			a[r] = tmp;
		}
	}
	interface Problem extends ToIntFunction<int[]> {
		
	}
}
