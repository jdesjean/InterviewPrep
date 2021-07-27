package org.ip.array;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-number-of-increments-on-subarrays-to-form-a-target-array/">LC: 1526</a>
 */
public class MinimumNumberIncrementsToTarget {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 3, new int[] {1,2,3,2,1}};
		Object[] tc2 = new Object[] { 4, new int[] {3,1,1,2}};
		Object[] tc3 = new Object[] { 7, new int[] {3,1,5,4,2}};
		Object[] tc4 = new Object[] { 1, new int[] {1,1,1,1}};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	/**
	 * [1,2,3,2,1] -> [1,2,2,2,1] -> [1,1,1,1,1]  -> 
	 *                       1     +      1       +   1 -> 3
	 * [3,1,1,2] -> [3,1,1,1] -> [1,1,1,1] -> 
	 *                 1      +   2        +  1 -> 4
	 * [3,1,5,4,2] -> [3,1,4,4,2] -> [3,1,2,2,2] -> [3,1,1,1,1] -> [1,1,1,1,1] ->
	 *                      1      +     2        +     1        +    2        +  1 -> 7
	 * When seeing element greater than previous element increase by diff.
	 */
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			int res = value[0];
			for (int i = 1; i < value.length; i++) {
				res += Math.max(0, value[i] - value[i - 1]);
			}
			return res;
		}
		
	}
	interface Problem extends ToIntFunction<int[]> {
		
	}
}
