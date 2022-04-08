package org.ip.array;

import java.util.Arrays;
import java.util.LongSummaryStatistics;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/cutting-ribbons/">LC: 1891</a>
 */
public class CuttingRibbons {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {5, new int[] {9,7,5}, 3};
		Object[] tc2 = new Object[] {4, new int[] {7,5,9}, 4};
		Object[] tc3 = new Object[] {0, new int[] {5,7,9}, 22};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			LongSummaryStatistics stats = Arrays.stream(t).asLongStream().summaryStatistics();
			if (stats.getSum() < k) return 0;
			int l = 1;
			for (int r = Math.min((int)(stats.getSum() / k), (int) stats.getMax()); l < r; ) {
				int m = l + (r - l + 1) / 2;
				if (cut(t, m) >= k) {
					l = m;
				} else {
					r = m - 1;
				}
			}
			return l;
		}
		int cut(int[] t, int m) {
			int count = 0;
			for (int i = 0; i < t.length; i++) {
				count += t[i] / m;
			}
			return count;
		}
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
	