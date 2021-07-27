package org.ip.string;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/basic-calculator-iii/">LC: 772</a>
 */
public class BasicCalculatorIII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 2, "1+1" };
		Object[] tc2 = new Object[] { 4, "6-4/2" };
		Object[] tc3 = new Object[] { 21, "2*(5+5*2)/3+(6/2+8)" };
		Object[] tc4 = new Object[] { -12, "(2+6*3+5-(3*14/7+2)*5)+3" };
		Object[] tc5 = new Object[] { 0, "0" };
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5 };
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}

	static class Solver implements Problem {

		@Override
		public int applyAsInt(String s) {
			return _apply(s, new AtomicInteger());
		}
		int _apply(String s, AtomicInteger i) {
			int res = 0, prev = 0, cur = 0;
			s = s.trim();
			char sign = '+';
			for (; i.get() < s.length() && sign != ')'; ) {
				char c = s.charAt(i.getAndIncrement());
				if (c == ' ') {
					continue;
				} else if (Character.isDigit(c)) {
					cur = cur * 10 + (c - '0');
					if (i.get() != s.length()) {
						continue;
					}
				} else if (c == '(') {
					cur = _apply(s, i);
					if (i.get() != s.length()) {
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
