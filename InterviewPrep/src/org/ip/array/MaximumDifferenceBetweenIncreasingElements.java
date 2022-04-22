package org.ip.array;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximum-difference-between-increasing-elements/">LC: 2016</a>
 */
public class MaximumDifferenceBetweenIncreasingElements {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4, new int[] {7,1,5,4}};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] nums) {
			if (nums == null || nums.length == 0) return -1;
	        int res = -1;
	        int min = 0;
	        for (int i = 1; i < nums.length; i++) {
	            int diff = nums[i] - nums[min];
	            if (diff > res) {
	                res = diff;
	            }
	            if (nums[i] < nums[min]) {
	                min = i;
	            }
	        }
	        return res != 0 ? res : -1;
		}
		
	}
	interface Problem extends ToIntFunction<int[]> {
		
	}
}
