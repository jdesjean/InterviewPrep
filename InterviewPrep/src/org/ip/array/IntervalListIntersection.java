package org.ip.array;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/interval-list-intersections/">LC: 986</a>
 */
public class IntervalListIntersection {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {new int[][] {{1,2},{5,5},{8,10},{15,23},{24,24},{25,25}}, new int[][] {{0,2},{5,10},{13,23},{24,25}}, new int[][] {{1,5},{8,12},{15,24},{25,26}}};
		Object[] tc2 = new Object[] {new int[][] {}, new int[][] {{1,3}, {5,9}}, new int[][] {}};
		Object[] tc3 = new Object[] {new int[][] {}, new int[][] {}, new int[][] {{4,8}, {10,12}}};
		Object[] tc4 = new Object[] {new int[][] {{3,7}}, new int[][] {{1,7}}, new int[][] {{3,10}}};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4);
		Class<? extends Problem>[] solvers = new Class[] {Solver.class};
		for (Object[] tc : tcs) {
			Utils.print((int[][])tc[0]);
			System.out.println();
			for (Class<? extends Problem> solver : solvers) {
				Problem function = solver.getConstructor(int[][].class).newInstance(tc[1]);
				Utils.print(function.apply((int[][]) tc[2]));
				System.out.println();
			}
			System.out.println();
		}
	}
	public static class Solver implements Problem {
		private int[][] intervals;

		public Solver(int[][] intervals) {
			this.intervals = intervals;
		}

		@Override
		public int[][] apply(int[][] t) {
			List<int[]> res = new ArrayList<>();
			int[] interval = new int[2];
			for (int i = 0, j = 0; i < intervals.length && j < t.length; ) {
				if (overlap(interval, intervals[i], t[j])) {
					res.add(Arrays.copyOf(interval, 2));
				}
				if (intervals[i][1] == t[j][1]) {
					i++;
					j++;
				} else if (intervals[i][1] < t[j][1]) {
					i++;
				} else {
					j++;
				}
			}
			return res.toArray(new int[res.size()][2]);
		}
		
		boolean overlap(int[] res, int[] a, int[] b) {
			if (a[1] < b[0] || b[1] < a[0]) {
				return false;
			}
			res[0] = Math.max(a[0], b[0]);
			res[1] = Math.min(a[1], b[1]);
			return true;
		}
	}
	public interface Problem extends Function<int[][], int[][]> {
		
	}
}
