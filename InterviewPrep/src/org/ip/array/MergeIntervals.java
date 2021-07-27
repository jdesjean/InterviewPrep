package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/merge-intervals/">LC: 56</a>
 */
public class MergeIntervals {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				MergeIntervals::tc1,
				MergeIntervals::tc2,
				MergeIntervals::tc3,
				MergeIntervals::tc4);
		Solver[] solvers = new Solver[] {new SortIterative()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		Utils.println(solver.solve(new int[][] {{1,3},{2,6},{8,10},{15,18}}));
	}
	public static void tc2(Solver solver) {
		Utils.println(solver.solve(new int[][] {{1,4},{4,5}}));
	}
	public static void tc3(Solver solver) {
		Utils.println(solver.solve(new int[][] {{1,4}}));
	}
	public static void tc4(Solver solver) {
		Utils.println(solver.solve(new int[][] {}));
	}
	private static class SortIterative implements Solver {

		@Override
		public int[][] solve(int[][] intervals) {
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
	private interface Solver {
		public int[][] solve(int[][] intervals);
	}
}
