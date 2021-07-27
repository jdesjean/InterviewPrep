package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/24-game/">LC: 679</a>
 */
public class Game24 {
	public static void main(String[] s) {
		Object[] tc0 = new Object[] {true, new int[] {4,6}};
		Object[] tc1 = new Object[] {true, new int[] {4,1,8,7}};
		Object[] tc2 = new Object[] {false, new int[] {1,2,1,2}};
		Object[] tc3 = new Object[] {true, new int[] {1,9,1,2}};
		Object[][] tcs = new Object[][] {tc0, tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private final static double EPS = 0.001;
		
		@Override
		public boolean test(int[] t) {
			double[] res = new double[t.length];
			for (int i = 0; i < t.length; i++) {
				res[i] = t[i];
			}
			return _test(res, res.length - 1);
		}
		boolean _test(double[] t, int l) {
			if (l == 0) {
				return Math.abs(t[0] - 24) < EPS;
			}
			for (int j = 0; j < l; j++) {
				double pj = t[j];
				final int fj = j; 
				for (int k = j + 1; k <= l; k++) {
					double pk = t[k];
					t[k] = t[l];
					boolean res = ops((v) -> {
						t[fj] = v;
						return _test(t, l - 1);
					}, pj, pk);
					t[j] = pj;
					t[k] = pk;
 					if (res) return res;
				}
			}
			return false;
		}
		boolean ops(Predicate<Double> consumer, double a, double b) {
			return consumer.test(a + b)
					|| consumer.test(a - b)
					|| consumer.test(b - a)
					|| consumer.test(a * b)
					|| (b > EPS && consumer.test(a / b))
					|| (a > EPS && consumer.test(b / a));  
		}
		
	}
	interface Problem extends Predicate<int[]> {
		
	}
}
