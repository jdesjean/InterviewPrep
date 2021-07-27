package org.ip.matrix;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/rotating-the-box/">LC: 1861</a>
 */
public class RotateBox {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new char[][] {
			{'.'},
			{'#'},
			{'#'}
			}, new char[][] {{'#','.','#'}}};
		Object[] tc2 = new Object[] {new char[][] {
			{'#','.'},
			{'#','#'},
			{'*','*'},
			{'.','.'}}
		, new char[][] {
			{'#','.','*','.'},
			{'#','#','*','.'}
		}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public char[][] apply(char[][] t) {
			if (t == null) return null;
			if (t.length == 0) return new char[0][0];
			int m = t.length, n = t[0].length;
			char[][] res = new char[n][m];
			for (int r = 0; r < m; r++) {
				int resc = m - r - 1;
				for (int c = n - 1, resr = n - 1; c >= 0; c--) {
					res[c][resc] = '.';
					if (t[r][c] != '.') {
						resr = t[r][c] == '*' ? c : resr;
						res[resr--][resc] = t[r][c];
					}
				}
			}
			return res;
		}
		
	}
	interface Problem extends Function<char[][], char[][]> {
		
	}
}
