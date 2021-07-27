package org.ip.matrix;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/bomb-enemy/">LC: 361</a>
 */
public class BombEnemy {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new char[][] {
			{'0','E','0','0'},
			{'E','0','W','E'},
			{'0','E','0','0'}}};
		Object[] tc2 = new Object[] {1, new char[][] {
			{'W','W','W'},
			{'0','0','0'},
			{'E','E','E'}}};
		Object[] tc3 = new Object[] {1, new char[][] {
			{'W'},
			{'E'},
			{'W'},
			{'0'},
			{'E'}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(char[][] value) {
			if (value == null || value.length == 0) return 0;
			int[] colHit = new int[value[0].length];
			int res = 0;
			for (int r = 0; r < value.length; r++) {
				int rowHit = 0;
				for (int c = 0; c < value[r].length; c++) {
					if (c == 0 || value[r][c - 1] == 'W') {
						rowHit = 0;
						for (int k = c; k < value[r].length; k++) {
							if (value[r][k] == 'W') break;
							else if (value[r][k] == 'E') rowHit++;
						}
					}
					if (r == 0 || value[r - 1][c] == 'W') {
						colHit[c] = 0;
						for (int k = r; k < value.length; k++) {
							if (value[k][c] == 'W') break;
							else if (value[k][c] == 'E') colHit[c]++;
						}
					}
					if (value[r][c] == '0') {
						res = Math.max(res, rowHit + colHit[c]);
					}
				}
			}
			return res;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(char[][] value) {
			if (value == null || value.length == 0) return 0;
			int[][] buf = new int[value.length][value[0].length];
			int res = 0, count = 0;
			for (int r = 0; r < value.length; r++) {
				count = 0;
				for (int c = 0; c < value[r].length; c++) {
					if (value[r][c] == 'W') count = 0;
					else if (value[r][c] == 'E') count++;
					else buf[r][c] += count; 
				}
				count = 0;
				for (int c = value[r].length - 1; c >= 0; c--) {
					if (value[r][c] == 'W') count = 0;
					else if (value[r][c] == 'E') count++;
					else buf[r][c] += count; 
				}
			}
			for (int c = 0; c < value[0].length; c++) {
				count = 0;
				for (int r = 0; r < value.length; r++) {
					if (value[r][c] == 'W') count = 0;
					else if (value[r][c] == 'E') count++;
					else buf[r][c] += count; 
				}
				count = 0;
				for (int r = value.length - 1; r >= 0; r--) {
					if (value[r][c] == 'W') count = 0;
					else if (value[r][c] == 'E') count++;
					else buf[r][c] += count; 
					res = Math.max(buf[r][c], res);
				}
			}
			return res;
		}
		
	}
	interface Problem extends ToIntFunction<char[][]> {
		
	}
}
