package org.ip.search;

import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/search-in-rotated-sorted-array/">LC: 33</a>
 */
public class SortedRotated {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 4, new int[] {4,5,6,7,0,1,2}, 0};
		Object[] tc2 = new Object[] { -1, new int[] {4,5,6,7,0,1,2}, 3};	
		Object[] tc3 = new Object[] { -1, new int[] {1}, 3};
		Object[] tc4 = new Object[] { 4, new int[] {4,5,6,7,8,1,2,3}, 8};
		Object[] tc5 = new Object[] { 2, new int[] {5,1,3}, 3};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public Integer apply(int[] t, Integer u) {
			int target = u;
			for (int left = 0, right = t.length - 1; left <= right; ) {
				int mid = left + (right - left) / 2;
				if (t[mid] == target) {
					return mid;
				} else if (t[left] <= t[mid]) {
					if (t[left] <= target && t[mid] > target) {
						right = mid - 1;
					} else {
						left = mid + 1;
					}
				} else {
					if (t[mid] < target && t[right] >= target) {
						left = mid + 1;
					} else {
						right = mid - 1;
					}
				}
			}
			return -1;
		}
		
	}
	interface Problem extends BiFunction<int[], Integer, Integer> {
		
	}
}
