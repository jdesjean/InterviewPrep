package org.ip.array;

import java.util.Arrays;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/3sum/">LC: 15</a>
 */
public class ThreeSumClosest {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[] {-1,2,1,-4}, 1};
		Object[] tc2 = new Object[] {0, new int[] {0,0,0}, 1};
		Object[] tc3 = new Object[] {82, new int[] {1,2,4,8,16,32,64,128}, 82};
		Object[] tc4 = new Object[] {0, new int[] {-55,-24,-18,-11,-7,-3,4,5,6,9,11,23,33}, 0};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			Arrays.sort(t);
			int target = u;
			int res = Integer.MAX_VALUE;
			for (int i = 0; i < t.length - 2 && res != 0; i++) {
				int diff = solve(t, i, target - t[i]);
				if (Math.abs(diff) < Math.abs(res)) {
					res = diff;
				}
			}
			return target - res;
		}
		int solve(int[] t, int i, int target) {
			int res = Integer.MAX_VALUE;
			for (int k = i + 1, j = t.length - 1; k < j && res != 0;) {
				int diff = target - (t[j] + t[k]);
				if (diff > 0) k++;
				else if (diff < 0) j--;
				if (Math.abs(diff) < Math.abs(res)) {
					res = diff;
				}
			}
			return res;
		}
	}
	
	// Doesn't quite work
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int k = u;
			Arrays.sort(t);
			int res = Integer.MAX_VALUE;
			for (int i = 0, j = t.length - 1; j - i > 1; ) {
				int twoSum = t[i] + t[j];
				int target = k - twoSum;
				int idx = Arrays.binarySearch(t, i + 1, j - 1, target);
				if (idx >= 0) {
					return k;
				} else {
					idx = -idx-1;
					int v = t[idx];
					int diff = target - t[idx];
					//Check previous index as well
					if (idx > i + 1) {
						int diff2 = target - t[idx - 1];
						if (Math.abs(diff2) < Math.abs(diff)) {
							v = t[idx - 1];
							diff = diff2;
						}
					}
					if (Math.abs(diff) < Math.abs(res)) {
						res = diff;
					}
					if (v == target) break;
					else if (v < target) i++;
					else j--;
				}
			}
			return res == Integer.MAX_VALUE ? Integer.MAX_VALUE : k - res;
		}
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
