package org.ip.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/spiral-matrix/submissions/">LC: 54</a>
 */
public class SpiralMatrix {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,2,3,4,5,6,7,8,9,10,11,12}, new int[][] {
			{1,2,3,4},
			{10,11,12,5},
			{9,8,7,6}}};
		Object[] tc2 = new Object[] {new int[] {1,2,3,4,5,6,7,8,9,10,11,12}, new int[][] {
			{1,2,3},
			{10,11,4},
			{9,12,5},
			{8,7,6}}};
		Object[] tc3 = new Object[] {new int[] {1,2,3,4,5,6,7,8,9}, new int[][] {
			{1,2,3},
			{8,9,4},
			{7,6,5}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		@Override
		public List<Integer> apply(int[][] t) {
			if (t == null || t.length == 0) return null;
			int n = t.length, m = t[0].length;
			List<Integer> res = new ArrayList<>(n * m);
			int i = 0;
			for (; n > 1 && m > 1; i++, n -= 2, m -= 2) {
				int rend = i + n - 1;
				int cend = i + m - 1;
				for (int c = i; c < cend; c++) {
					res.add(t[i][c]);
				}
				for (int r = i; r < rend; r++) {
					res.add(t[r][cend]);
				}
				for (int c = cend; c > i; c--) {
					res.add(t[rend][c]);
				}
				for (int r = rend; r > i; r--) {
					res.add(t[r][i]);
				}
			}
			if (n == 1 && m == 1) {
				res.add(t[i][i]);
			} else if (n == 1 && m > 1) {
				for (int c = i; c <= i + m - 1; c++) {
					res.add(t[i][c]);
				}
			} else if (n > 1 && m == 1) {
				for (int r = i; r <= i + n - 1; r++) {
					res.add(t[r][i]);
				}
			}
			return res;
		}
		
	}
	interface Problem extends Function<int[][], List<Integer>> {
		
	}
}
