package org.ip.string;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/basic-calculator-ii/">LC: 227</a>
 */
public class BasicCalculatorII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 7, "3+2*2" };
		Object[] tc2 = new Object[] { 1, " 3/2 " };
		Object[][] tcs = new Object[][] { tc1, tc2 };
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}

	static class Solver implements Problem {

		@Override
		public int applyAsInt(String s) {
			int res = 0, prev = 0, cur = 0;
			s = s.trim();
			char sign = '+';
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == ' ') {
					continue;
				}
				if (Character.isDigit(c)) {
					cur = cur * 10 + (c - '0');
					if (i != s.length() - 1) {
						continue;
					}
				}
				switch (sign) {
				case '+':
					res += prev;
					prev = cur;
					break;
				case '-':
					res += prev;
					prev = -cur;
					break;
				case '*':
					prev *= cur;
					break;
				case '/':
					prev /= cur;
					break;
				}
				sign = c;
				cur = 0;
			}
			res += prev;
			return res;
		}
	}

	interface Problem extends ToIntFunction<String> {

	}
}
