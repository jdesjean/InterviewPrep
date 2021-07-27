package org.ip.matrix;

import java.util.function.Consumer;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/rotate-image/">LC: 48</a>
 */
public class RotateImage {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {
			{7,4,1},
			{8,5,2},
			{9,6,3}
		}, new int[][] {
			{1,2,3},
			{4,5,6},
			{7,8,9}
		}};
		Object[] tc2 = new Object[] {new int[][] {
			{13,9,5,1},
			{14,10,6,2},
			{15,11,7,3},
			{16,12,8,4}
		}, new int[][] {
			{1,2,3,4},
			{5,6,7,8},
			{9,10,11,12},
			{13,14,15,16},
		}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public void accept(int[][] t) {
			int n = t.length;
			for (int j = 0; j < n / 2; j++) {
				int ii = n - j - 1;
				for (int i = j; i < ii; i++) {
					int jj = n - i - 1;
					int tmp = t[j][i];
					t[j][i] = t[jj][j];
					t[jj][j] = t[ii][jj];
					t[ii][jj] = t[i][ii];
					t[i][ii] = tmp;
				}
			}
		}
		
	}
	static interface Problem extends Consumer<int[][]> {
		
	}
}
