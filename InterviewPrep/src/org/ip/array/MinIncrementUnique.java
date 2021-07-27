package org.ip.array;

import java.util.Arrays;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-increment-to-make-array-unique/">LC: 945</a>
 */
public class MinIncrementUnique {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {1, new int[] {1,2,2}};
		Object[] tc2 = new Object[] {6, new int[] {3,2,1,2,1,7}};
		Object[] tc3 = new Object[] {12, new int[] {4,4,7,5,1,9,4,7,3,8}};
		Object[] tc4 = new Object[] {41, new int[] {14,4,5,14,13,14,10,17,2,12,2,14,7,13,14,13,4,16,4,10}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			int max = max(value);
			int[] cache = new int[Math.max(value.length, max) + 1];
			for (int i = 0; i < value.length; i++) {
				cache[value[i]]++;
			}
			int res = 0;
			for (int i = 0; i < cache.length - 1; i++) {
				if (cache[i] <= 1) continue;
				cache[i + 1] += cache[i] - 1;
				res += cache[i] - 1;
				cache[i] = 1;
			}
			res += Math.max(sum(cache[cache.length - 1] - 1), 0);
			return res;
		}
		int sum(int n) {
			return n * (n + 1) / 2;
		}
		int max(int[] value) {
			int max = Integer.MIN_VALUE;
			for (int i = 0; i < value.length; i++) {
				max = Math.max(max, value[i]);
			}
			return max;
		}
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			Arrays.sort(value);
			int res = 0;
			for (int i = 1; i < value.length; i++) {
				int diff = value[i] - value[i - 1];
				if (diff <= 0) {
					res += -diff + 1;
					value[i] = value[i - 1] + 1;
				}
				
			}
			return res;
		}
		
	}
	interface Problem extends ToIntFunction<int[]> {
		
	}
}
