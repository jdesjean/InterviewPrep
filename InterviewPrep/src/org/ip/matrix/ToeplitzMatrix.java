package org.ip.matrix;

import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/toeplitz-matrix/">LC: 766</a>
 */
public class ToeplitzMatrix {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, new int[][] {{1,2,3,4},{5,1,2,3},{9,5,1,2}}};
		Object[] tc2 = new Object[] {false, new int[][] {{1,2},{2,2}}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public boolean test(int[][] t) {
			if (t.length == 0) return true;
			for (int r = 0; r < t.length; r++) {
				for (int rr = r, cc = 0; rr < t.length && cc < t[rr].length; rr++, cc++) {
					if (t[rr][cc] != t[r][0]) return false;
				}
			}
			for (int c = 1; c < t[0].length; c++) {
				for (int rr = 0, cc = c; rr < t.length && cc < t[rr].length; rr++, cc++) {
					if (t[rr][cc] != t[0][c]) return false;
				}
			}
			return true;
		}
		
	}
	interface Problem extends Predicate<int[][]> {
		
	}
}
