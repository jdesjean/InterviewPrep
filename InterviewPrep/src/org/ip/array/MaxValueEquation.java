package org.ip.array;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/max-value-of-equation/">LC: 1499</a>
 */
public class MaxValueEquation {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 4, new int[][] {{1,3},{2,0},{5,10},{6,-10}}, 1 };
		Object[] tc2 = new Object[] { 3, new int[][] {{0,0},{3,0},{9,2}}, 3};
		Object[] tc3 = new Object[] { -6, new int[][] {{-19,9},{-15,-19},{-5,-8}}, 10};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[][] t, Integer u) {
			if (t.length == 0) return 0;
			int k = u;
			Deque<int[]> deque = new LinkedList<>();
			int max = Integer.MIN_VALUE;
			for (int i = 0 ; i < t.length; i++) {
				while (!deque.isEmpty() && Math.abs(deque.peek()[0] - t[i][0]) > k) {
					deque.pop();
				}
				if (!deque.isEmpty()) {
					max = Math.max(max, eq(deque.peek(), t[i]));
				}
				while (!deque.isEmpty() && value(deque.peekLast()) < value(t[i])) {
					deque.removeLast();
				}
				deque.add(t[i]);
			}
			return max;
		}
		int eq(int[] a, int[] b) {
			return Math.abs(a[0] - b[0]) + a[1] + b[1];
		}
		int value(int[] a) {
			return a[1] - a[0];
		}
	}
	interface Problem extends ToIntBiFunction<int[][], Integer> {
		
	}
}
