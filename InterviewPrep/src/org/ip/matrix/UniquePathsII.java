package org.ip.matrix;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/unique-paths-ii/">LC: 63</a>
 */
public class UniquePathsII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[][] {{0,0,0},{0,1,0},{0,0,0}}};
		Object[] tc2 = new Object[] {1, new int[][] {{0,1},{0,0}}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			if (value == null || value.length == 0 || value[0][0] != 0) return 0;
			int[] cache = new int[value[0].length];
			cache[0] = 1;
			for (int l = 0; l < value.length; l++) {
				int prev = 0;
				for (int c = 0; c < value[l].length; c++) {
					//cache[c] = value[l][c] == 0 ? (prev + cache[c]) : 0;
					cache[c] = (prev + cache[c]) * (value[l][c] ^ 1);
					prev = cache[c];
				}
			}
			return cache[cache.length - 1];
		}
		
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
