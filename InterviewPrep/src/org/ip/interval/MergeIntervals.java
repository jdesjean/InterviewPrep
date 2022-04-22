package org.ip.interval;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/merge-intervals/">LC: 56</a>
 */
public class MergeIntervals {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{1,6},{8,10},{15,18}}, new int[][] {{1,3},{2,6},{8,10},{15,18}}};
		Object[] tc2 = new Object[] {new int[][] {{1,5}}, new int[][] {{1,4},{4,5}}};
		Object[] tc3 = new Object[] {new int[][] {{1,4}}, new int[][] {{1,4}}};
		Object[] tc4 = new Object[] {new int[][] {{0,4}}, new int[][] {{1,4},{0,4}}};
		Object[] tc5 = new Object[] {new int[][] {{1,4}}, new int[][] {{1,4},{2,3}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver(), new SortIterative() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static Comparator<int[]> INTERVAL_COMPARATOR_START = (a, b) -> Integer.compare(a[0], b[0]);
		
		@Override
		public int[][] apply(int[][] intervals) {
			if (intervals == null || intervals.length == 0) return null;
			Arrays.sort(intervals, INTERVAL_COMPARATOR_START);
			Deque<int[]> res = new ArrayDeque<>(intervals.length);
			res.add(intervals[0]);
			for (int i = 1; i < intervals.length; i++) {
				if (res.peekLast()[1] >= intervals[i][0]) {
					res.peekLast()[1] = Math.max(intervals[i][1], res.peekLast()[1]);
				} else {
					res.add(intervals[i]);
				}
			}
			return res.stream().toArray(int[][]::new);
		}
		
	}
	static class SortIterative implements Problem {

		@Override
		public int[][] apply(int[][] intervals) {
			if (intervals == null || intervals.length == 0) return intervals;
			int index = _solve(intervals);
			return Arrays.copyOf(intervals, index + 1);
		}
		
		private int _solve(int[][] intervals) {
			int count = 0;
			Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
			for (int i = 1; i < intervals.length; i++) {
				if (intervals[i][0] > intervals[count][1]) {
					count++;
					intervals[count] = intervals[i];
				} else {
					intervals[count][1] = Math.max(intervals[count][1], intervals[i][1]);
				}
			}
			return count;
		}
	}
	interface Problem extends Function<int[][], int[][]> {
		
	}
	
}
