package org.ip.string;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * <a href="https://leetcode.com/problems/add-strings/">LC: 415</a>
 */
public class AddStrings {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"99", "9", "108"};
		Object[] tc2 = new Object[] {"0", "0", "0"};
		List<Object[]> tcs = Arrays.asList(tc1, tc2);
		BiFunction<String, String, Integer>[] solvers = new BiFunction[] {new Solver()};
		for (Object[] tc : tcs) {
			for (BiFunction<String, String, Integer> solver : solvers) {
				System.out.print(String.valueOf(solver.apply((String) tc[0], (String) tc[1])) + "," + String.valueOf(tc[2]));
			}
			System.out.println();
		}
	}
	public static class Solver implements BiFunction<String, String, String> {

		@Override
		public String apply(String s1, String s2) {
			if (s1.length() < s2.length()) {
				String s3 = s1;
				s1 = s2;
				s2 = s3;
			}
			StringBuilder res = new StringBuilder();
			int carry = 0;
			for (int i1 = s1.length() - 1, i2 = s2.length() - 1; i1 >= 0 || i2 >= 0; i1--, i2--) {
				int d1 = i1 >= 0 ? s1.charAt(i1) - '0' : 0;
				int d2 = i2 >= 0 ? s2.charAt(i2) - '0' : 0;
				int sum = carry + d1 + d2;
				int d3 = sum % 10;
				
				res.append(d3);

				carry = sum / 10;
			}
			if (carry > 0) {
				res.append(carry);
			}
			return res.reverse().toString();
		}
		
	}
}
