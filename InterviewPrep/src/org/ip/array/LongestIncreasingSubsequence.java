package org.ip.array;

import java.util.Arrays;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/longest-increasing-subsequence/">LC: 300</a>
 */
public class LongestIncreasingSubsequence {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 4, new int[] {10,9,2,5,3,7,101,18} };
		Object[] tc2 = new Object[] { 4, new int[] {0,1,0,3,2,3} };
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new BinarySearchSolver(), new DPSolver() };
		Test.apply(solvers, tcs);
	}
	// nlogn
	static class BinarySearchSolver implements Problem {

		@Override
		public int applyAsInt(int[] value) {
			int max = 0;
			for (int i = 0; i < value.length; i++) {
				int index = Arrays.binarySearch(value, 0, max, value[i]);
				if (index < 0) {
					index = -(index + 1);
				}
				value[index] = value[i];
				if (index == max) {
					max++;
				}
			}
			return max;
		}
		
	}
	//n^2
	static class DPSolver implements Problem {

		@Override
		public int applyAsInt(int[] nums) {
			int[] cache = new int[nums.length];
	        int max = 0;
	        for (int i = 0; i < cache.length; i++) {
	            cache[i] = 1;
	            for (int j = 0; j < i; j++) {
	                if (nums[i] <= nums[j])  continue;
	                cache[i] = Math.max(cache[i], cache[j] + 1);
	            }
	            max = Math.max(max, cache[i]);
	        }
	        return max;
		}
		
	}
	interface Problem extends ToIntFunction<int[]> {
		
	}
}
