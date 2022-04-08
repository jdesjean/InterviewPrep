package org.ip.array;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/">LC: 1011</a>
 */
public class CapacityToShipPackagesWithinDays {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {15, new int[] {1,2,3,4,5,6,7,8,9,10}, 5};
		Object[] tc2 = new Object[] {6, new int[] {3,2,2,4,1,4}, 3};
		Object[] tc3 = new Object[] {3, new int[] {1,2,3,1,1}, 3};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			IntSummaryStatistics stats = Arrays.stream(t).summaryStatistics();
			int h = (int) stats.getSum();
			for (int l = Math.max((int) stats.getSum() / k, stats.getMax()); l < h; ) {
				int m = l + (h - l) / 2;
				if (nDays(t, m) <= k) {
					h = m;
				} else {
					l = m + 1;
				}
			}
			return h;
		}
		int nDays(int[] a, int n) {
			int days = 1;
			for (int i = 0, sum = 0; i < a.length; i++) {
				sum += a[i];
				if (sum > n) {
					sum = a[i];
					days++;
				}
			}
			return days;
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
