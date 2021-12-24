package org.ip.matrix;

import java.util.Arrays;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/dungeon-game/">LC: 174</a>
 */
public class DungeonGame {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {7, new int[][] {
			{-2,-3,3},
			{-5,-10,1},
			{10,30,-5}
		}};
		/**
		 * {-2,-2},{-5,-5},{-2,-5}
		 * {-7,-7},{-15,-15},{-1,-5}
		 * {-3,-7},{27,-7},{-6,-6}
		 * 
		 * {3,1},{6,1},{6,4}
		 * {8,1},{16,1},{6,5}
		 * {8,11},{8,38},{7,1}
		 */
		Object[] tc2 = new Object[] {1, new int[][] {{0}}};
		Object[] tc3 = new Object[] {1, new int[][] {{100}}};
		Object[] tc4 = new Object[] {4, new int[][] {{0,-3}}};
		Object[] tc5 = new Object[] {3, new int[][] {
			{1,-3,3},
			{0,-2,0},
			{-3,-3,-3}}};
		/**
		 * {1,1},{-2,-2},{1,-2}
		 * {1,1},{-1,-1},{1,-2}
		 * {-2,-2},{-4,-4},{-2,-2}
		 * 
		 * {1,2},{3,1},{3,4}
		 * {1,2},{2,1},{2,1}
		 * {3,1},{6,1},{5,1}
		 * 
		 * dp[i][j] = max(min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j], 1)
		 * 3,4,1
		 * 6,6,4
		 * 10,7,4
		 */
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	/**
	 * Bottom up doesn't work.
	 * There 2 numbers current health & minimum health needed.
	 * Depending on future value current health or minimum health can be chosen as best 
	 */
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			if (value.length == 0) return 1;
			int[][] cache = new int[value[0].length][2];
			cache[0][0] = value[0][0];
			cache[0][1] = cache[0][0];
			for (int c = 1; c < value[0].length; c++) {
				cache[c][0] = cache[c - 1][0] + value[0][c];
				cache[c][1] = Math.min(cache[c - 1][1], cache[c][0]); 
			}
			for (int l = 1; l < value.length; l++) {
				cache[0][0] = cache[0][0] + value[l][0];
				cache[0][1] = Math.min(cache[0][1], cache[0][0]);
				for (int c = 1; c < value[l].length; c++) {
					int v1 = cache[c - 1][0] + value[l][c];
					int v2 = cache[c][0] + value[l][c];
					int min1 = Math.min(cache[c - 1][1], v1);
					int min2 = Math.min(cache[c][1], v2);
					if (min1 < min2) { //take min2
						cache[c][0] = v2;
						cache[c][1] = min2;
					} else {
						cache[c][0] = v1;
						cache[c][1] = min1;
					}
				}
			}
			return cache[cache.length - 1][1] < 0 ? Math.abs(cache[cache.length - 1][1]) + 1 : 1;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			if (value.length == 0) return 1;
			int[][] dp = new int[value.length + 1][value[0].length + 1];
			Arrays.fill(dp[value.length], Integer.MAX_VALUE);
			fill(dp, value[0].length, Integer.MAX_VALUE);
			dp[value.length][value[0].length - 1] = 1;
			dp[value.length - 1][value[0].length] = 1;
			for (int l = value.length - 1; l >= 0; l--) {
				for (int c = value[0].length - 1; c >= 0; c--) {
					dp[l][c] = Math.max(Math.min(dp[l + 1][c], dp[l][c + 1]) - value[l][c], 1);
				}
			}
			return dp[0][0];
		}
		void fill(int[][] value, int col, int val) {
			for (int i = 0; i < value.length; i++) {
				value[i][col] = val;
			}
		}
		
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
