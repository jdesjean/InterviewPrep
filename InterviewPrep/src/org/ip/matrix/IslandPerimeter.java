package org.ip.matrix;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/island-perimeter/">LC: 463</a>
 */
public class IslandPerimeter {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {16, new int[][] {{0,1,0,0},{1,1,1,0},{0,1,0,0},{1,1,0,0}}};
		Object[] tc2 = new Object[] {4, new int[][] {{1}}};
		Object[] tc3 = new Object[] {4, new int[][] {{1, 0}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			if (value == null || value.length == 0) return 0;
			int res = 0;
			for (int l = 0; l < value.length; l++) {
				for (int c = 0; c < value[l].length; c++) {
					if (value[l][c] == 1) {
						res += 4;
						if (c != 0 && value[l][c - 1] == 1) res-=2;
						if (l != 0 && value[l - 1][c] == 1) res-=2;
					}
				}
			}
			return res;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			if (value == null || value.length == 0) return 0;
			int res = 0;
			for (int l = 0; l < value.length; l++) {
				for (int c = 0; c < value[l].length; c++) {
					if ((c == 0 && value[l][c] == 1) || (c != 0 && value[l][c] != value[l][c - 1])) res++;
					if ((l == 0 && value[l][c] == 1) || (l != 0 && value[l][c] != value[l - 1][c])) res++;
					if (c == value[l].length - 1 && value[l][c] == 1) res++;
					if (l == value.length - 1 && value[l][c] == 1) res++;
				}
			}
			return res;
		}
		
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
