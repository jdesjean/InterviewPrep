package org.ip.string;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * <a href="https://leetcode.com/problems/add-binary/">LC: 67</a>
 */
public class AdderBinary {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"11", "1", "100"};
		Object[] tc2 = new Object[] {"1010", "1011", "10101"};
		Object[] tc3 = new Object[] {"1010", "0", "1010"};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3);
		BiFunction<String, String, String>[] solvers = new BiFunction[] {new LinearXor(), new Linear()};
		for (Object[] tc : tcs) {
			for (BiFunction<String, String, String> solver : solvers) {
				System.out.print(","  + String.valueOf(solver.apply((String) tc[0], (String) tc[1])));
			}
			System.out.print("," + String.valueOf(tc[2]));
			System.out.println();
		}
	}
	public static class Linear implements BiFunction<String, String, String> {

		@Override
		public String apply(String t, String u) {
			int carry = 0;
			StringBuilder res = new StringBuilder();
			for (int i = t.length() - 1, j = u.length() - 1; i >= 0 || j >= 0; i--, j--) {
				int d1 = i >= 0 ? t.charAt(i) - '0' : 0;
				int d2 = j >= 0 ? u.charAt(j) - '0' : 0;
				int sum = d1 + d2 + carry;
				int d3 = sum % 2;
				carry = sum / 2;
				res.append(d3);
			}
			if (carry > 0) {
				res.append(carry);
			}
			return res.reverse().toString();
		}
		
	}
	public static class LinearXor implements BiFunction<String, String, String> {

		@Override
		public String apply(String t, String u) {
			String carry = t;
			String res = u;
			while (carry.length() > 0) {
				StringBuilder carryNext = new StringBuilder(t.length() + 1);
				StringBuilder resNext = new StringBuilder(u.length());
				int carryMsb = -1;
				int resMsb = -1;
				carryNext.append("0");
				for (int i = res.length() - 1, j = carry.length() - 1; i >= 0 || j >=0; i--, j--) {
					int d1 = i >= 0 ? res.charAt(i) - '0' : 0;
					int d2 = j >= 0 ? carry.charAt(j) - '0' : 0;
					int r = d1 ^ d2;
					int c = d1 & d2;
					resNext.append(r);
					carryNext.append(c);
					if (r > 0) {
						resMsb = resNext.length();
					}
					if (c > 0) {
						carryMsb = carryNext.length();
					}
				}
				carry = carryMsb != -1 ? carryNext.reverse().substring(carryNext.length() - carryMsb) : "";
				res = resMsb != -1 ? resNext.reverse().substring(resNext.length() - resMsb) : "";
			}
			return res.length() > 0 ? res : "0";
		}
		
	}
}
