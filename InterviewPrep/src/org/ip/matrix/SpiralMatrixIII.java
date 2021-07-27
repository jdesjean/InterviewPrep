package org.ip.matrix;

import org.ip.Test;
import org.ip.Test.QuadFunction;

/**
 * <a href="https://leetcode.com/problems/spiral-matrix-iii/">LC: 885</a>
 */
public class SpiralMatrixIII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{0,0},{0,1},{0,2},{0,3}}, 1, 4, 0, 0};
		Object[] tc2 = new Object[] {new int[][] {{1,4},{1,5},{2,5},{2,4},{2,3},{1,3},{0,3},{0,4},{0,5},{3,5},{3,4},{3,3},{3,2},{2,2},{1,2},{0,2},{4,5},{4,4},{4,3},{4,2},{4,1},{3,1},{2,1},{1,1},{0,1},{4,0},{3,0},{2,0},{1,0},{0,0}}, 5, 6, 1, 4};
		Object[] tc3 = new Object[] {new int[][] {{1,1},{1,2},{2,2},{2,1},{2,0},{1,0},{0,0},{0,1},{0,2}}, 3, 3, 1, 1};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private final static int[][] DIR = new int[][] {{0,1},{1,0},{0,-1},{-1,0}};
		@Override
		public int[][] apply(Integer a, Integer b, Integer c, Integer d) {
			int rows = a;
			int cols = b;
			int rStart = c;
			int cStart = d;
			int n = rows * cols;
			int[][] res = new int[n][2];
			for (int i = 0, dir = 0, step = 1; i < n; step++) {
				for (int k = 0; k < 2; k++) {
					for (int l = 0; l < step; l++) {
						if (rStart >= 0 && cStart >= 0 && rStart < rows && cStart < cols) {
							res[i++] = new int[] {rStart, cStart};
							if (i == n) return res;
						}
						rStart = rStart + DIR[dir][0];
						cStart = cStart + DIR[dir][1];
					}
					dir = (dir + 1) % 4;
				}
			}
			return res;
		}
		
	}
	interface Problem extends QuadFunction<Integer, Integer, Integer, Integer, int[][]> {
		
	}
}
