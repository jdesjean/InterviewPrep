package org.ip.array;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/">LC: 1438</a>
 */
public class LongestContinuousSubarrayAsoluteDiffLessThanK {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[] {8,2,4,7}, 4};
		Object[] tc2 = new Object[] {4, new int[] {10,1,2,4,7,2}, 5};
		Object[] tc3 = new Object[] {3, new int[] {4,2,2,2,4,4,2,2}, 0};
		Object[] tc4 = new Object[] {6, new int[] {2,2,2,4,4,2,5,5,5,5,5,2}, 2};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			int max = 0;
			Deque<Integer> maxDeque = new ArrayDeque<>();
			Deque<Integer> minDeque = new ArrayDeque<>();
			for (int i = 0, j = 0; j < t.length; j++) {
				while (!maxDeque.isEmpty() && maxDeque.peekLast() < t[j]) {
					maxDeque.removeLast();
				}
				while (!minDeque.isEmpty() && minDeque.peekLast() > t[j]) {
					minDeque.removeLast();
				}
				maxDeque.add(t[j]);
				minDeque.add(t[j]);
				while (!maxDeque.isEmpty() && !minDeque.isEmpty() && maxDeque.peek() - minDeque.peek() > k) {
					if (maxDeque.peek() == t[i]) {
						maxDeque.remove();
					}
					if (minDeque.peek() == t[i]) {
						minDeque.remove();
					}
					i++;
				}
				max = Math.max(max, j - i + 1);
			}
			return max;
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
