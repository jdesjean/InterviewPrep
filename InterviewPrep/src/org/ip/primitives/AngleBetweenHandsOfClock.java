package org.ip.primitives;

import org.ip.Test;
import org.ip.Test.BiIntToDoubleOperator;

/**
 * <a href="https://leetcode.com/problems/angle-between-hands-of-a-clock/">LC: 1344</a>
 */
public class AngleBetweenHandsOfClock {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {165, 12, 30};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static int MINUTE_ANGLE = 6; // 360 / 60;
		private final static int HOUR_ANGLE = 30;  // 360 / 12;
		
		@Override
		public double applyAsDouble(int a, int b) {
			double bb = MINUTE_ANGLE * b;
			double aa = HOUR_ANGLE * (a % 12 + b / 60.0);
			double diff = Math.abs(aa - bb);
			return Math.min(diff, 360 - diff);
		}
		
	}
	interface Problem extends BiIntToDoubleOperator {
		
	}
}
