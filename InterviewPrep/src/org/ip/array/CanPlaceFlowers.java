package org.ip.array;

import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/can-place-flowers/">LC: 605</a>
 */
public class CanPlaceFlowers {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { true, new int[] {1, 0, 0, 0, 1}, 1 };
		Object[] tc2 = new Object[] { false, new int[] {1, 0, 0, 0, 1}, 2 };
		Object[] tc3 = new Object[] { true, new int[] {0, 0, 0, 0, 1}, 2 };
		Object[] tc4 = new Object[] { true, new int[] {1, 0, 0, 0, 0}, 2 };
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public boolean test(int[] t, Integer u) {
			int k = u;
			for (int i = 0; i < t.length && k > 0; i++) {
				if (t[i] == 0 
						&& (i == 0 || i > 0 && t[i - 1] == 0)
						&& (i == t.length - 1 || i < t.length - 2 && t[i + 1] == 0)) {
					t[i] = 1;
					k--;
				}
			}
			return k <= 0;
		}
		
	}
	interface Problem extends BiPredicate<int[], Integer> {
		
	}
}
