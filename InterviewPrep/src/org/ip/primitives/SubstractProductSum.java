package org.ip.primitives;

import java.util.function.IntUnaryOperator;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/subtract-the-product-and-sum-of-digits-of-an-integer/">LC: 1281</a>
 */
public class SubstractProductSum {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {15, 234};
		Object[] tc2 = new Object[] {21, 4421};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int operand) {
			int sum = 0;
			int product = 1;
			while (operand > 0) {
				int digit = operand % 10;
				sum += digit;
				product *= digit;
				operand /= 10;
			}
			return product - sum;
		}
	}
	interface Problem extends IntUnaryOperator {
		
	}
}
