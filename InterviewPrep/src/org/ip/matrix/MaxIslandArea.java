package org.ip.matrix;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/max-area-of-island/">LC: 695</a>
 */
public class MaxIslandArea {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 0, new int[][] {
			{0,0,0,0,0,0,0,0}
		}};
		Object[] tc2 = new Object[] { 5, new int[][] {
			{0,0,0,0},
			{0,1,1,0},
			{0,0,1,0},
			{0,1,0,0},
			{0,1,1,0},
			{0,1,1,0},
		}};
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private final static int[][] directions = new int[][] {{0,1},{0,-1},{1,0},{-1,0}};
		@Override
		public int applyAsInt(int[][] value) {
			int max = 0;
			int id = 2;
			for (int l = 0; l < value.length; l++) {
				for (int c = 0; c < value[l].length; c++) {
					if (value[l][c] != 1) continue;
					max = Math.max(max, visit(value, l, c, id++));
				}
			}
			return max;
		}
		int visit(int[][] value, int l, int c, int id) {
			value[l][c] = id;
			int size = 1;
			for (int i = 0; i < directions.length; i++) {
				int ll = directions[i][0] + l;
				int cc = directions[i][1] + c;
				if (
						ll < 0 
						|| cc < 0 
						|| ll >= value.length 
						|| cc >= value[ll].length
						|| value[ll][cc] != 1) continue;
				size += visit(value, ll, cc, id);
			}
			return size;
		}
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
