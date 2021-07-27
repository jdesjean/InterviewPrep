package org.ip.interval;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/my-calendar-iii/">LC: 732</a>
 */
public class CalendarIII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new Integer[] {1, 1, 2, 3, 3, 3}, new TestCase1()};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		TreeMap<Integer, Integer> map = new TreeMap<>();
		
		@Override
		public int[] apply(Function<Problem, int[]> t) {
			return t.apply(this);
		}

		@Override
		public int book(int start, int end) {
			map.compute(start, (k, v) -> v == null ? 1 : v + 1);
			map.compute(end, (k, v) -> v == null ? -1 : v - 1);
			int max = 0;
			int count = 0;
			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				count += entry.getValue();
				max = Math.max(max, count);
			}
			return max;
		}
		
	}
	interface Problem extends Function<Function<Problem, int[]>, int[]>{
		int book(int start, int end);
	}
	static class TestCase1 implements Function<Problem, int[]> {

		@Override
		public int[] apply(Problem t) {
			return new int[] {
					t.book(10, 20),
					t.book(50, 60),
					t.book(10, 40),
					t.book(5, 15),
					t.book(5, 10),
					t.book(25, 55)
			};
		}
	}
}
