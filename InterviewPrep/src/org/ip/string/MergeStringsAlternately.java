package org.ip.string;

import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/merge-strings-alternately/">LC: 1768</a>
 */
public class MergeStringsAlternately {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"apbqcr", "abc", "pqr"};
		Object[] tc2 = new Object[] {"apbqrs", "ab", "pqrs"};
		Object[] tc3 = new Object[] {"apbqcd", "abcd", "pq"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public String apply(String t, String u) {
			char[] res = new char[t.length() + u.length()];
			for (int i = 0; i < t.length() && i < u.length(); i++) {
				res[i * 2] = t.charAt(i);
				res[i * 2 + 1] = u.charAt(i);
			}
			if (t.length() != u.length()) {
				String rem = t.length() > u.length() ? t : u;
				int min = Math.min(t.length(), u.length());
				for (int i = Math.min(t.length(), u.length()); i < rem.length(); i++) {
					res[min + i] = rem.charAt(i);
				}
			}
			return new String(res);
		}
		
	}
	interface Problem extends BiFunction<String, String, String> {
		
	}
}
