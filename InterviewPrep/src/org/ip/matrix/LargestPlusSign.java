package org.ip.matrix;

import java.util.Arrays;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/largest-plus-sign/">LC: 764</a>
 */
public class LargestPlusSign {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, 5, new int[][] {{4,2}}};
		Object[] tc2 = new Object[] {0, 1, new int[][] {{0,0}}};
		Object[] tc3 = new Object[] {1, 2, new int[][] {{0,0}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(Integer t, int[][] u) {
			int k = t;
			int[][] buf = new int[k][k];
			for (int r = 0; r < k; r++) {
				Arrays.fill(buf[r], 1);
			}
			for (int i = 0; i < u.length; i++) {
				buf[u[i][0]][u[i][1]] = 0;
			}
			int res = 0, count = 0;
			for (int r = 0; r < k; r++) {
				count = 0;
				for (int c = 0; c < k; c++) {
					count = buf[r][c] == 0 ? 0 : count + 1;
					buf[r][c] = count;
				}
				count = 0;
				for (int c = k - 1; c >= 0; c--) {
					count = buf[r][c] == 0 ? 0 : count + 1;
					buf[r][c] = Math.min(buf[r][c], count);
				}
			}
			for (int c = 0; c < k; c++) {
				count = 0;
				for (int r = 0; r < k; r++) {
					count = buf[r][c] == 0 ? 0 : count + 1;
					buf[r][c] = Math.min(buf[r][c], count);
				}
				count = 0;
				for (int r = k - 1; r >= 0; r--) {
					count = buf[r][c] == 0 ? 0 : count + 1;
					buf[r][c] = Math.min(buf[r][c], count);
					res = Math.max(buf[r][c], res);
				}
			}
			return res;
		}
		
	}
	interface Problem extends ToIntBiFunction<Integer, int[][]> {
		
	}
}
