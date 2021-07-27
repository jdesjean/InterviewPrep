package org.ip.array;

import java.util.Arrays;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-cost-for-tickets/">LC: 983</a>
 */
public class MinCostTicket {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {11, new int[] {1,4,6,7,8,20}, new int[] {2,7,15}};
		Object[] tc2 = new Object[] {17, new int[] {1,2,3,4,5,6,7,8,9,10,30,31}, new int[] {2,7,15}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new DPSolver(), new DPSolver2() };
		Test.apply(solvers, tcs);
	}
	static class DPSolver2 implements Problem {

		@Override
		public int applyAsInt(int[] t, int[] u) {
			int[] days = new int[] {1,7,30};
			int[] res = new int[t[t.length - 1] + 1];
			Arrays.fill(res, Integer.MAX_VALUE);
			for (int i = 0, j = 0; i < res.length; i++) {
				if (i < t[j]) {
					res[i] = i > 1 ? res[i - 1] : 0;
				} else {
					for (int k = 0; k < u.length; k++) {
						int cost = i >= days[k] ? res[i - days[k]] : 0;
						res[i] = Math.min(res[i], cost + u[k]);
					}
					j++;
				}
			}
			return res[res.length - 1];
		}
	}
	/**
	 * min(dp(t[i] - 1) + cost[0], dp(t[i] - 7) + cost[1], dp(t[i] - 30) + cost[2])
	 */
	static class DPSolver implements Problem {

		@Override
		public int applyAsInt(int[] t, int[] u) {
			int[] days = new int[] {1,7,30};
			int[] res = new int[t.length];
			Arrays.fill(res, Integer.MAX_VALUE);
			for (int i = 0; i < t.length; i++) {
				for (int k = 0; k < u.length; k++) {
					int cost = 0;
					for (int j = i - 1; j >= 0; j--) {
						cost = res[j];
						if (t[j] <= t[i] - days[k]) break;
						if (j == 0) cost = 0;
					}
					res[i] = Math.min(res[i], cost + u[k]);
				}
			}
			return res[t.length - 1];
		}
	}
	interface Problem extends ToIntBiFunction<int[], int[]> {
		
	}
}
