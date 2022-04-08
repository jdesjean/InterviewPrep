package org.ip.primitives;

import java.util.function.IntUnaryOperator;

import org.ip.Test;

public class NextGreaterElementIII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {21, 12};
		Object[] tc2 = new Object[] {-1, 21};
		Object[] tc3 = new Object[] {1243, 1234};
		Object[] tc4 = new Object[] {230412, 230241};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int operand) {
			char[] value = String.valueOf(operand).toCharArray();
			int l = value.length - 2;
			for (; l >= 0; l--) {
				if (value[l] < value[l + 1]) break;
			}
			if (l < 0) return -1;
			int r = value.length - 1;
			for (; r >= l; r--) {
				if (value[l] < value[r]) break;
			}
			swap(value, l, r);
			reverse(value, l + 1, value.length - 1);
			try {
				return Integer.parseInt(new String(value));
			} catch (NumberFormatException e) {
				return -1;
			}
		}
		void reverse(char[] value, int i, int j) {
			for (; i < j; i++, j--) {
				swap(value, i, j);
			}
		}
		void swap(char[] value, int i, int j) {
			char tmp = value[i];
			value[i] = value[j];
			value[j] = tmp;
		}
		
	}
	interface Problem extends IntUnaryOperator {
		
	}
}
