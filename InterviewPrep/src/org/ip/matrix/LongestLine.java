package org.ip.matrix;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/longest-line-of-consecutive-one-in-matrix/solution/">LC: 562</a>
 */
public class LongestLine {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 3, new int[][] {
			{0,1,1,0},
			{0,1,1,0},
			{0,0,0,1}
		}};
		Object[][] tcs = new Object[][] { tc1};
		Problem[] solvers = new Problem[] { new Solver(), new DPSolver()};
		Test.apply(solvers, tcs);
	}
	static class DPSolver implements Problem {
		private final static int[][] directions = new int[][] {{0,1},{1,0},{1,1},{1,-1}};
		@Override
		public int applyAsInt(int[][] value) {
			if (value.length == 0) return 0;
			int[][][] dp = new int[value.length][value[0].length][directions.length];
			int max = 0;
			for (int l = 0; l < value.length; l++) {
				for (int c = 0; c < value[l].length; c++) {
					for (int i = 0; i < directions.length; i++) {
						if (value[l][c] == 0) continue;
						int ll = l - directions[i][0];
						int cc = c - directions[i][1];
						int prev = ll < 0 || cc < 0 || ll >= value.length || cc >= value[0].length ? 0 : dp[ll][cc][i];
						dp[l][c][i] = prev + 1;
						max = Math.max(max, dp[l][c][i]);
					}
				}
			}
			return max;
		}
		
	}
	static class Solver implements Problem {
		//horizontal, vertical, diagonal, antidiagonal
		private final static int[][] directions = new int[][] {{0,1},{1,0},{1,1},{1,-1}};
		@Override
		public int applyAsInt(int[][] value) {
			int max = 0;
			for (int l = 0; l < value.length; l++) {
				for (int c = 0; c < value[l].length; c++) {
					for (int i = 0; i < directions.length; i++) {
						if (!isStart(value, l, c, i)) continue;
						max = Math.max(max, count(value, l, c, i));
					}
				}
			}
			return max;
		}
		int count(int[][] value, int l, int c, int i) {
			int count = 0;
			for (;!isOutOfBounds(value, l, c) && value[l][c] == 1; 
					l = l + directions[i][0], c = c + directions[i][1], count++) {}
			return count;
		}
		boolean isOutOfBounds(int[][] value, int l, int c) {
			return l < 0 || c < 0 || l >= value.length || c >= value[l].length;
		}
		boolean isStart(int[][] value, int l, int c, int i) {
			int ll = l - directions[i][0];
			int cc = c - directions[i][1];
			if (isOutOfBounds(value, ll, cc)) return true;
			return value[ll][cc] != 1;
		}
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
