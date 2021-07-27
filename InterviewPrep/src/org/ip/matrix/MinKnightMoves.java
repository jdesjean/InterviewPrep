package org.ip.matrix;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-knight-moves/solution/">LC: 1197</a>
 */
public class MinKnightMoves {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {1, 2, 1};
		Object[] tc2 = new Object[] {4, 5, 5};
		Object[] tc3 = new Object[] {3, 0, 1};
		Object[] tc4 = new Object[] {135, 270, -21};
		Object[] tc5 = new Object[] {150, 300, 0};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int x, int y) {
			x = Math.abs(x);
			y = Math.abs(y);
			int[][] cache = new int[Math.max(x + 1, 3)][Math.max(y + 1, 3)];
			for (int i = 0; i < cache.length; i++) {
				Arrays.fill(cache[i], Integer.MAX_VALUE);
			}
			// x + y == 0
			cache[0][0] = 0;
			// x + y == 1
			cache[1][0] = 3;
			cache[0][1] = 3;
			// x + y == 2
			cache[1][1] = 2;
			cache[0][2] = 2;
			cache[2][0] = 2;
			dfs(cache, x, y);
			return cache[x][y];
		}
		int dfs(int[][] cache, int x, int y) {
			if (cache[x][y] != Integer.MAX_VALUE) return cache[x][y];
			cache[x][y] = Math.min(
					dfs(cache, Math.abs(x - 2), Math.abs(y - 1)),
					dfs(cache, Math.abs(x - 1), Math.abs(y - 2))) + 1;
			return cache[x][y];
		}
		
	}
	interface Problem extends IntBinaryOperator {
		
	}
}
