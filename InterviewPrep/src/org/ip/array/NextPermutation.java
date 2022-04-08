package org.ip.array;

import java.util.function.Consumer;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/next-permutation/">LC: 31</a>
 */
public class NextPermutation {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,3,2}, new int[] {1,2,3}};
		Object[] tc2 = new Object[] {new int[] {1,2,3}, new int[] {3,2,1}};
		Object[] tc3 = new Object[] {new int[] {1,5,1}, new int[] {1,1,5}};
		Object[] tc4 = new Object[] {new int[] {1}, new int[] {1}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public void accept(int[] nums) {
			int l = nums.length - 2;
			// first number decreasing (right to left)
			for (; l >= 0; l--) {
				if (nums[l] < nums[l + 1]) break;
			}
			// l < 0 all decreasing (left to right)
			if (l >= 0) {
				int r = nums.length - 1;
				for (; r >= l; r--) {
					if (nums[l] < nums[r]) break;
				}
				swap(nums, l, r);
			}
			reverse(nums, l + 1, nums.length - 1);
		}
		void reverse(int[] nums, int l, int r) {
			for (; l < r; l++, r--) {
				swap(nums, l, r);
			}
		}
		void swap(int[] nums, int l, int r) {
			int tmp = nums[l];
			nums[l] = nums[r];
			nums[r] = tmp;
		}
		
	}
	interface Problem extends Consumer<int[]> {
		
	}
}
