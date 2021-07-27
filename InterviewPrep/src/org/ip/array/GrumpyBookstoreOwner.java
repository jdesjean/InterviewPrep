package org.ip.array;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/grumpy-bookstore-owner/">LC: 1052</a>
 */
public class GrumpyBookstoreOwner {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {16, new int[] {1,0,1,2,1,1,7,5}, new int[] {0,1,0,1,0,1,0,1}, 3};
		Object[] tc2 = new Object[] {18, new int[] {10,1,7}, new int[] {0,0,0}, 2};
		Object[] tc3 = new Object[] {3, new int[] {3}, new int[] {0}, 1};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public Integer apply(int[] a, int[] b, Integer c) {
			int k = c;
			
			int secret = 0;
			int sum = 0;
			for (int i = 0; i < k; i++) {
				secret += a[i] * b[i];
				sum += a[i] * (b[i] ^ 1);
			}
			int max = secret;
			for (int i = k; i < a.length; i++) {
				secret -= a[i - k] * b[i - k];
				secret += a[i] * b[i];
				max = Math.max(max, secret);
				sum += a[i] * (b[i] ^ 1);
			}
			return sum + max;
		}
		
	}
	interface Problem extends TriFunction<int[], int[], Integer, Integer> {
		
	}
}
