package org.ip.string;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/string-to-integer-atoi/">LC: 8</a>
 */
public class AtoI {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 42, "42"};
		Object[] tc2 = new Object[] { -42, "   -42"};
		Object[] tc3 = new Object[] { 4193, "4193 with words"};
		Object[] tc4 = new Object[] { 0, "words and 987"};
		Object[] tc5 = new Object[] { -2147483648, "-91283472332"};
		Object[] tc6 = new Object[] { 2147483647, "91283472332"};
		Object[] tc7 = new Object[] { 10, "000010"};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5, tc6, tc7};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(String value) {
			int res = 0;
			boolean sign = true;
			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i); 
				if (c == ' ') {
					if (i != 0 && value.charAt(i - 1) != ' ') break;
					else continue;
				} else if (c == '+') {
					if (i != 0 && value.charAt(i - 1) != ' ') break;
					continue;
				} else if (c == '-') {
					if (i != 0 && value.charAt(i - 1) != ' ') break;
					sign = false;
					continue;
				} else if (Character.isDigit(c)) {
					int v = Character.digit(c, 10);
					try {
						res = Math.addExact(Math.multiplyExact(res, 10), sign ? v : -v);
					} catch (ArithmeticException e) {
						res = sign ? Integer.MAX_VALUE : Integer.MIN_VALUE;
						break;
					}
				} else {
					break;
				}
			}
			return res;
		}
		
	}
	interface Problem extends ToIntFunction<String> {
		
	}
}
