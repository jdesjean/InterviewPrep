package org.ip.string;

import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/add-strings/">LC: 415</a>
 */
public class AddStrings {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"108", "99", "9"};
		Object[] tc2 = new Object[] {"0", "0", "0"};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public String apply(String s1, String s2) {
			if (s1  == null || s1.length() == 0) return s2;
			else if (s2 == null || s2.length() == 0) return s1;
			int carry = 0;
			StringBuilder sb = new StringBuilder();
			if (s2.length() > s1.length()) {
				String tmp = s1;
				s1 = s2;
				s2 = tmp;
			}
			for (int i1 = s1.length() - 1, i2 = s2.length() - 1; i1 >= 0 && i2 >= 0; i1--, i2--) {
				int v1 = s1.charAt(i1) - '0';
				int v2 = s2.charAt(i2) - '0';
				int v3 = carry + v1 + v2;
				sb.append(v3 % 10);
				carry = v3 / 10;
			}
			for (int i1 = s1.length() - s2.length() - 1; i1 >= 0; i1--) {
				int v1 = s1.charAt(i1) - '0';
				int v3 = carry + v1;
				sb.append(v3 % 10);
				carry = v3 / 10;
			}
			if (carry > 0) {
				sb.append(carry);
			}
			return sb.reverse().toString();
		}
		
	}
	interface Problem extends BiFunction<String, String, String> {
		
	}
}
