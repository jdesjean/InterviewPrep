package org.ip.cartesian;

import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/rectangle-overlap/">LC: 836</a>
 */
public class RectangleOverlap {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, new int[] {0,0,2,2}, new int[] {1,1,3,3}};
		Object[] tc2 = new Object[] {false, new int[] {0,0,1,1}, new int[] {1,0,2,1}};
		Object[] tc3 = new Object[] {false, new int[] {0,0,1,1}, new int[] {2,2,3,3}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public boolean test(int[] t, int[] u) {
			boolean noOverlap = t[2] <= u[0] || t[3] <= u[1] || u[2] <= t[0] || u[3] <= t[1];
			return !noOverlap;
		}
		
	}
	interface Problem extends BiPredicate<int[], int[]> {
		
	}
}
