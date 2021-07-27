package org.ip.matrix;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/count-square-submatrices-with-all-ones/">LC: 1277</a>
 */
public class CountSquareSubmatrices {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 15, new int[][] {
				{0,1,1,1},
				{1,1,1,1},
				{0,1,1,1}
				}};
		Object[] tc2 = new Object[] { 7, new int[][] {
			{1,0,1},
			{1,1,0},
			{1,1,0}
			}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver(), new DPSolver()};
		Test.apply(solvers, tcs);
	}
	// Can be modified to use input instead of additional storage
	static class DPSolver implements Problem {

		@Override
		public int applyAsInt(int[][] A) {
			if (A == null || A.length == 0) return 0;
			int count = 0;
			for (int r = 0; r < A.length; r++) {
				for (int c = 0; c < A[r].length; c++) {
					if (A[r][c] != 1) continue;
					if (c != 0 && r != 0) {
						int pr = r - 1;
						int pc = c - 1;
						int min = Math.min(A[pr][pc], Math.min(A[pr][c], A[r][pc]));
						A[r][c] = min + 1;
					}
					count += A[r][c];
				}
			}
			return count;
		}
	}
	//For each 1, visit diagonally. If both vertical & horizontal filled increase size of square else exit.
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			int count = 0;
			for (int r = 0; r < value.length; r++) {
				for (int c = 0; c < value[r].length; c++) {
					if (value[r][c] != 1) continue;
					count += _apply(value, r, c);
				}
			}
			return count;
		}
		int _apply(int[][] value, int r, int c) {
			int l = 0;
			for (; r + l < value.length && c + l < value[r].length; l++) {
				int cc = c + l;
				int rr = r + l;
				if (value[rr][cc] != 1) return l;
				if (!check(value, r, rr - 1, cc, cc) // check column
						|| !check(value, rr, rr, c, cc - 1)) { // check row
					return l;
				}
			}
			return l;
		}
		boolean check(int[][] value, int r1, int r2, int c1, int c2) {
			for (int r = r1; r <= r2; r++) {
				for (int c = c1; c <= c2; c++) {
					if (value[r][c] != 1) return false;
				}
			}
			return true;
		}
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
