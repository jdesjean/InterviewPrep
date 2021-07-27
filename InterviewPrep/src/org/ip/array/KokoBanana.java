package org.ip.array;

import java.util.Arrays;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/koko-eating-bananas/">LC: 875</a>
 */
public class KokoBanana {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 4, new int[] {3,6,7,11}, 8};
		Object[] tc2 = new Object[] { 30, new int[] {30,11,23,4,20}, 5};
		Object[] tc3 = new Object[] { 23, new int[] {30,11,23,4,20}, 6};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int h = u;
			int max = Arrays.stream(t).max().getAsInt();
			if (h == t.length) {
				return max;
			}
			int lo = 1, hi = max;
			for (; lo < hi; ) {
				int mid = lo + (hi - lo) / 2;
				int computedH = computeH(t, mid);
				if (computedH > h) {
					lo = mid + 1;
				} else {
					hi = mid;
				}
			}
			return lo;
		}
		int computeH(int[] t, int k) {
			int sum = 0;
			double kk = k;
			for (int i = 0; i < t.length; i++) {
				sum += Math.ceil(t[i] / kk);
			}
			return sum;
		}
	}
	interface Problem extends ToIntBiFunction<int[], Integer>{
		
	}
}
