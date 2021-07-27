package org.ip.array;

import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/monotonic-array/">LC: 896</a>
 */
public class Monotone {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { true, new int[] { 1,2,2,3 }};
		Object[] tc2 = new Object[] { true, new int[] { 6,5,4,4 }};
		Object[] tc3 = new Object[] { false, new int[] { 1,3,2 } };
		Object[] tc4 = new Object[] { true, new int[] { 1,2,4,5 } };
		Object[] tc5 = new Object[] { true, new int[] { 1,1,1 } };
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		enum Direction {
			INCREASING, DECREASING;
		}
		
		@Override
		public boolean test(int[] t) {
			if (t == null || t.length <= 1) return true;
			Direction direction = null;
			for (int i = 1; i < t.length; i++) {
				int compare = Integer.compare(t[i - 1], t[i]);
				if (compare == 0) continue;
				if (direction == null) {
					direction = compare > 0 ? Direction.DECREASING : Direction.INCREASING;
				} else {
					Direction nextDirection = compare > 0 ? Direction.DECREASING : Direction.INCREASING;
					if (direction != nextDirection) return false;
				}
			}
			return true;
		}
		
	}
	interface Problem extends Predicate<int[]> {
		
	}
}
