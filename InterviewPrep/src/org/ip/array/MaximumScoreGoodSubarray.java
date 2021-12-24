package org.ip.array;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximum-score-of-a-good-subarray/">LC: 1793</a>
 */
public class MaximumScoreGoodSubarray {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {15, new int[] {1,4,3,7,4,5}, 3};
		Object[] tc2 = new Object[] {20, new int[] {5,5,4,5,4,1,1,1}, 0}; //5,5 - 4,5 - 4 - 1,1,1
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver3 implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			int res = t[k];
			int min = t[k];
			int i = k, j = k;
			for (; i > 0 && j < t.length - 1; ) {
				if (t[i - 1] >= t[j + 1]) {
					i--;
				} else {
					j++;
				}
				min = Math.min(min, Math.min(t[i], t[j]));
	            res = Math.max(res, min * (j - i + 1));
			}
			while (i > 0) {
				min = Math.min(min, Math.min(t[--i], t[j]));
	            res = Math.max(res, min * (j - i + 1));
			}
			while (j < t.length - 1) {
				min = Math.min(min, Math.min(t[i], t[++j]));
	            res = Math.max(res, min * (j - i + 1));
			}
			return res;
		}
		
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			int max = 0;
			Deque<Integer> mins = new LinkedList<>();
			for (int i = 0; i <= k; i++) {
				while (!mins.isEmpty() && t[i] < t[mins.peek()]) {
					mins.remove();
				}
				mins.push(i);
			}
			for (int i = 0; i <= k; i++) {
				int min = !mins.isEmpty() ? t[mins.peekLast()] : Integer.MAX_VALUE;
				for (int j = k; j < t.length; j++) {
					min = Math.min(min, t[j]);
					max = Math.max(max,  min * (j - i + 1));
				}
				if (!mins.isEmpty() && mins.peekLast() == i) {
					mins.removeLast();
				}
			}
			return max;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			int max = 0;
			for (int i = 0; i <= k; i++) {
				int min = Integer.MAX_VALUE;
				for (int j = k; j < t.length; j++) {
					for (int l = i; l <= j; l++) {
						min = Math.min(min, t[l]);
						max = Math.max(max,  min * (l - i + 1));
					}
				}
			}
			return max;
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
