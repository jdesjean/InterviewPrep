package org.ip.primitives;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * <a href="https://leetcode.com/problems/powx-n/">LC: 50</a>
 */
public class Pow {
	public static void main(String[] s) {
		Object[] tc0 = new Object[] {27d, 3d, 3};
		Object[] tc1 = new Object[] {1024d, 2d, 10};
		Object[] tc2 = new Object[] {9.261, 2.1d, 3};
		Object[] tc3 = new Object[] {0.25, 2d, -2};
		Object[] tc4 = new Object[] {8d, 2d, 3};
		Object[] tc5 = new Object[] {1d, 2d, 0};
		Object[] tc6 = new Object[] {5d, 5d, 1};
		Object[] tc7 = new Object[] {0.25d, 2d, -2};
		Object[] tc8 = new Object[] {0d, 2d, Integer.MIN_VALUE};
		Object[] tc9 = new Object[] {0.00003d, 34.00515d, -3};
		List<Object[]> tcs = Arrays.asList(tc9, tc8, tc0, tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8);
		BiFunction<Double, Integer, Double>[] solvers = new BiFunction[] {new Solver()};
		for (Object[] tc : tcs) {
			System.out.print(tc[0]);
			for (BiFunction<Double, Integer, Double> solver : solvers) {
				System.out.print("," + solver.apply((Double)tc[1], (Integer) tc[2]));
			}
			System.out.println();
		}
	}
	public static class Solver implements BiFunction<Double, Integer, Double> {

		@Override
		public Double apply(Double t, Integer u) {
			long n = u;
			double res = 1;
			double power = t;
			if (n < 0) {
				n = -n;
				power = 1 / t;
			}
			while (n != 0) {
				if ((n & 1) == 1) {
					res *= power;
				}
				n >>= 1;
				power *= power;
			}
			return res;
		}
		
	}
}
