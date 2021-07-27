package org.ip.array;

import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/">LC: 1011</a>
 */
public class CapacityToShipPackagesWithinDays {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {15, new int[] {1,2,3,4,5,6,7,8,9,10}, 5};
		Object[] tc2 = new Object[] {6, new int[] {3,2,2,4,1,4}, 3};
		Object[] tc3 = new Object[] {3, new int[] {1,2,3,1,1}, 3};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			if (t == null || t.length == 0) return 0;
			int d = u;
			int left = max(t);
			int right = sum(t);
			for (; left < right; ) {
				int mid = left + (right - left) / 2;
				if (canShip(t, d, mid)) {
					right = mid;
				} else {
					left = mid + 1;
				}
			}
			return right;
		}
		int sum(int[] t) {
			int sum = 0;
			for (int i = 0; i < t.length; i++) {
				sum += t[i];
			}
			return sum;
		}
		int max(int[] t) {
			int min = Integer.MIN_VALUE;
			for (int i = 0; i < t.length; i++) {
				min = Math.max(min, t[i]);
			}
			return min;
		}
		boolean canShip(int[] t, int d, int w) {
			int cum = 0;
			for (int i = 0; i < t.length; i++) {
				if (w - t[i] < cum) {
					cum = t[i];
					if (--d <= 0) return false;
				} else {
					cum += t[i];
				}
			}
			return true;
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
