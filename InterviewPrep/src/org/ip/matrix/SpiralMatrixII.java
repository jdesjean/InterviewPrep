package org.ip.matrix;

import java.util.function.IntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/spiral-matrix-ii/">LC: 59</a>
 */
public class SpiralMatrixII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {
			{1,2,3},
			{8,9,4},
			{7,6,5}}, 3};
		Object[] tc2 = new Object[] {new int[][] {
			{1}}, 1};
		Object[] tc3 = new Object[] {new int[][] {
			{1,2},
			{4,3}}, 2};
		Object[] tc4 = new Object[] {new int[][] {
			{1,2,3,4},
			{12,13,14,5},
			{11,16,15,6},
			{10,9,8,7}}, 4};
		Object[] tc5 = new Object[] {new int[][] {
			{1,2,3,4,5},
			{16,17,18,19,6},
			{15,24,25,20,7},
			{14,23,22,21,8},
			{13,12,11,10,9}}, 5};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {
		private final static int[][] DIR = new int[][] {{0,1},{1,0},{0,-1},{-1,0}};
		@Override
		public int[][] apply(int n) {
			int[][] res = new int[n][n];
			for (int i = 1, r = 0, c = 0, d = 0; i <= n * n; i++) {
				res[r][c] = i;
				int nr = Math.floorMod(r + DIR[d][0], n);
				int nc = Math.floorMod(c + DIR[d][1], n);
				if (res[nr][nc] != 0) {
					d = (d + 1) % 4;
					r += DIR[d][0];
					c += DIR[d][1];
				} else {
					r = nr;
					c = nc;
				}
			}
			return res;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int[][] apply(int n) {
			int[][] res = new int[n][n];
			int counter = 1;
			int i = 0;
			for (; n > 1; i++, n -= 2) {
				int end = i + n - 1;
				for (int c = i; c < end; c++) {
					res[i][c] = counter++;
				}
				for (int r = i; r < end; r++) {
					res[r][end] = counter++;
				}
				for (int c = end; c > i; c--) {
					res[end][c] = counter++;
				}
				for (int r = end; r > i; r--) {
					res[r][i] = counter++;
				}
			}
			if (n == 1) {
				res[i][i] = counter;
			}
			return res;
		}
		
	}
	interface Problem extends IntFunction<int[][]> {
		
	}
}
