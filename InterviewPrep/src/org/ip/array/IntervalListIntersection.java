package org.ip.array;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/interval-list-intersections/">LC: 986</a>
 */
public class IntervalListIntersection {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{1,2},{5,5},{8,10},{15,23},{24,24},{25,25}}, new int[][] {{0,2},{5,10},{13,23},{24,25}}, new int[][] {{1,5},{8,12},{15,24},{25,26}}};
		Object[] tc2 = new Object[] {new int[][] {}, new int[][] {{1,3}, {5,9}}, new int[][] {}};
		Object[] tc3 = new Object[] {new int[][] {}, new int[][] {}, new int[][] {{4,8}, {10,12}}};
		Object[] tc4 = new Object[] {new int[][] {{3,7}}, new int[][] {{1,7}}, new int[][] {{3,10}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public int[][] apply(int[][] t, int[][] u) {
			List<int[]> res = new ArrayList<>();
			for (int i = 0, j = 0; i < t.length && j < u.length; ) {
				int[] overlap = overlap(t[i], u[j]);
				if (overlap != null) { // overlap
					res.add(overlap);
				}
				if (t[i][1] == u[j][1]) {
					i++;
					j++;
				} else if (t[i][1] < u[j][1]) {
					i++;
				} else {
					j++;
				}
			}
			return res.toArray(new int[res.size()][2]);
		}
		int[] overlap(int[] t, int[] u) {
			int start = Math.max(t[0], u[0]);
			int end = Math.min(t[1], u[1]);
			if (start > end) return null;
			return new int[] {start, end};
		}
	}
	public interface Problem extends BiFunction<int[][], int[][], int[][]> {
		
	}
}
