package org.ip.matrix;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/diagonal-traverse/">LC: 498</a>
 */
public class DiagonalTraverse {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {1,2,4,7,5,3,6,8,9}, new int[][] {
			{1,2,3},
			{4,5,6},
			{7,8,9}
		}};
		Object[] tc2 = new Object[] { new int[] {1,2,4,5,3,6}, new int[][] {
			{1,2,3},
			{4,5,6}
		}};
		Object[] tc3 = new Object[] { new int[] {1,2,3,5,4,6}, new int[][] {
			{1,2},
			{3,4},
			{5,6}
		}};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver2(), new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {
		private int[][] directions = new int[][] {{-1,1}, {1,-1}};
		
		@Override
		public int[] apply(int[][] t) {
			if (t == null || t.length == 0) return new int[0];
			int direction = 0;
			int[] res = new int[t.length * t[0].length];
			for (int l = 0, c = 0, j = 0 ; j < res.length; j++) {
				res[j] = t[l][c];
				
				l += directions[direction][0];
				c += directions[direction][1];
				if (l < 0 || l >= t.length || c < 0 || c >= t[0].length) {
					l -= directions[direction][0];
					c -= directions[direction][1];
					if (direction == 0) {
						if (c < t[0].length - 1) c++;
						else l++;
					} else {
						if (l < t.length - 1) l++;
						else c++;
					}
					direction = (direction + 1) % 2;
				}
			}
			return res;
		}
		
	}
	static class Solver implements Problem {
		private int[][] directions = new int[][] {{-1,1}, {1,-1}};
		
		@Override
		public int[] apply(int[][] t) {
			if (t == null || t.length == 0) return new int[0];
			int direction = 0;
			int[] res = new int[t.length * t[0].length];
			int j = 0;
			for (int i = 0; j < res.length; i++) {
				int l, c;
				if (direction == 0) {
					l = i;
					c = 0;
				} else {
					l = 0;
					c = i;
				}
				for (; (l >= 0 && l < t.length) || (c >= 0 && c < t[0].length) ;) {
					if ((l >= 0 && l < t.length) && (c >= 0 && c < t[0].length)) {
						res[j++] = t[l][c];
					}
					l += directions[direction][0];
					c += directions[direction][1];
				}
				
				direction = (direction + 1) % 2;
			}
			return res;
		}
		
	}
	interface Problem extends Function<int[][], int[]> {
		
	}
}
