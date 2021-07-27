package org.ip.array;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/split-array-into-consecutive-subsequences/">LC: 659</a>
 */
public class SplitArrayIntoConsecutiveSubsequences {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, new int[] {1,2,3,3,4,5}};
		Object[] tc2 = new Object[] {true, new int[] {1,2,3,3,4,4,5,5}};
		Object[] tc3 = new Object[] {false, new int[] {1,2,3,4,4,5}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static BiFunction<Integer, Integer, Integer> ADD = (k, v) -> v == null ? 1 : v + 1;
		private final static BiFunction<Integer, Integer, Integer> SUB = (k, v) -> v - 1;
		
		@Override
		public boolean test(int[] t) {
			Map<Integer, Integer> freqs = new HashMap<>();
			Map<Integer, Integer> tail = new HashMap<>();
			for (int i = 0; i < t.length; i++) {
				freqs.compute(t[i], ADD);
			}
			for (int i = 0; i < t.length; i++) {
				int freq = freqs.get(t[i]);
				if (freq == 0) continue;
				freqs.compute(t[i], SUB);
				if (tail.getOrDefault(t[i] - 1, 0) > 0) {
					tail.compute(t[i] - 1, SUB);
					tail.compute(t[i], ADD);
				} else if (freqs.getOrDefault(t[i] + 1, 0) > 0 && freqs.getOrDefault(t[i] + 2, 0) > 0) {
					freqs.compute(t[i] + 1, SUB);
					freqs.compute(t[i] + 2, SUB);
					tail.compute(t[i] + 2, ADD);
				} else {
					return false;
				}
			}
			return true;
		}
		
	}
	interface Problem extends Predicate<int[]> {
		
	}
}
