package org.ip.stream;

import java.util.Random;

import org.ip.Test;
import org.ip.Test.TriFunction;

public class ReservoirSampling {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {}, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }, 5, 3};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		Random random = new Random(0);
		@Override
		public int[] apply(int[] a, Integer b, Integer c) {
			int k = b;
			int l = Math.min(a.length, k + 1);
			int[] res = new int[l];
			System.arraycopy(a, 0, res, 0, l);
			for (int i = k; i < a.length; i++) {
				int next = random.nextInt(a.length);
				if (next < k) {
					res[next] = a[i];
				}
			}
			return res;
		}
		
	}
	interface Problem extends TriFunction<int[], Integer, Integer, int[]> {
		
	}
}
