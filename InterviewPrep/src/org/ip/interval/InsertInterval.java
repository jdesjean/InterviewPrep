package org.ip.interval;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/insert-interval/">LC: 57</a>
 */
public class InsertInterval {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{1,5},{6,9}}, new int[][] {{1,3},{6,9}}, new int[] {2,5}};
		Object[] tc2 = new Object[] {new int[][] {{1,2},{3,10},{12,16}}, new int[][] {{1,2},{3,5},{6,7},{8,10},{12,16}}, new int[] {4,8}};
		Object[] tc3 = new Object[] {new int[][] {{5,7}}, new int[][] {}, new int[] {5,7}};
		Object[] tc4 = new Object[] {new int[][] {{1,5}}, new int[][] {{1,5}}, new int[] {2,3}};
		Object[] tc5 = new Object[] {new int[][] {{1,7}}, new int[][] {{1,5}}, new int[] {2,7}};
		Object[] tc6 = new Object[] {new int[][] {{1,5},{6,8}}, new int[][] {{1,5}}, new int[] {6,8}};
		Object[] tc7 = new Object[] {new int[][] {{0,0},{1,5}}, new int[][] {{1,5}}, new int[] {0,0}};
		Object[] tc8 = new Object[] {new int[][] {{3,5},{6,6},{12,15}}, new int[][] {{3,5},{12,15}}, new int[] {6,6}};
		Object[] tc9 = new Object[] {new int[][] {{1,7}}, new int[][] {{1,5}}, new int[] {5,7}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8, tc9};
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int[][] apply(int[][] t, int[] u) {
			if (t.length == 0) {
				return new int[][] {u};
			}
			List<int[]> res = new ArrayList<>(t.length);
			boolean addedU = false;
			for (int i = 0; i < t.length; i++) {
				int overlap = overlap(t[i], u);
				if (overlap == 0) {
					u[0] = Math.min(t[i][0], u[0]);
					u[1] = Math.max(t[i][1], u[1]);
				} else if (overlap > 0) {
					res.add(t[i]);
				} else {
					if (!addedU) {
						addedU = true;
						res.add(u);
					}
					res.add(t[i]);
				}
			}
			if (!addedU) {
				res.add(u);
			}
			return res.toArray(new int[res.size()][]);
		}
		int overlap(int[] t, int[] u) {
			if (t[1] < u[0]) {
				return 1;
			} else if (u[1] < t[0]) {
				return -1;
			} else {
				return 0;
			}
		}
		
	}
	interface Problem extends BiFunction<int[][], int[], int[][]> {
		
	}
}
