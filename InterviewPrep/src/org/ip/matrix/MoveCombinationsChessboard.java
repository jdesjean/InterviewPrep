package org.ip.matrix;

import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/number-of-valid-move-combinations-on-chessboard/">LC: 2056</a>
 */
public class MoveCombinationsChessboard {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {15, new String[] {"rook"}, new int[][] {{1,1}}};
		Object[] tc2 = new Object[] {22, new String[] {"queen"}, new int[][] {{1,1}}};
		Object[] tc3 = new Object[] {12, new String[] {"bishop"}, new int[][] {{4,3}}};
		Object[] tc4 = new Object[] {281, new String[] {"queen","bishop"}, new int[][] {{5,7},{3,4}}};
		Object[] tc5 = new Object[] {96, new String[] {"bishop","rook"}, new int[][] {{8,5},{7,7}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static int[][] DIRS = new int[][] {{0,1},{1,0},{0,-1},{-1,0},{1,1},{1,-1},{-1,-1},{-1,1}};
		
		@Override
		public int applyAsInt(String[] t, int[][] u) {
			return _applyAsInt(t, u, 0, new int[4][8][8]);
		}
		int _applyAsInt(String[] t, int[][] u, int p, int[][][] cache) {
			if (p >= t.length) {
				return 1;
			}
			int res = 0;
			
			for (int i = 0; i < DIRS.length; i++) {
				if ((i < 4 && "bishop".equals(t[p])) || (i >= 4 && "rook".equals(t[p]))) continue;
				boolean blocked = false;
				for (int step = res == 0 ? 1 : 2; !blocked; step++) {
					int r = u[p][0] - 1 + DIRS[i][0] * (step - 1);
					int c = u[p][1] - 1 + DIRS[i][1] * (step - 1);
					if (r < 0 || c < 0 || r > 7 || c > 7) break;
					boolean canStop = true;
					for (int pp = 0; pp < p; pp++) {
						canStop &= cache[pp][r][c] >= 0 && cache[pp][r][c] < step;
						blocked |= (cache[pp][r][c] < 0 && -cache[pp][r][c] <= step) || cache[pp][r][c] == step;
					}
					if (canStop) {
						cache[p][r][c] = -step;
						System.out.println(p + "," + r + "," + c);
						res += _applyAsInt(t, u, p + 1, cache);
					}
					cache[p][r][c] = step;
				}
				cache[p] = new int[8][8];
			}
			return res;
		}
		
	}
	interface Problem extends ToIntBiFunction<String[], int[][]> {
		
	}
}
