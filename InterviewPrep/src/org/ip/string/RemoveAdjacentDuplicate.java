package org.ip.string;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/">LC: 1047</a>
 */
public class RemoveAdjacentDuplicate {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { "ca", "abbaca"};
		Object[][] tcs = new Object[][] { tc1};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public String apply(String t) {
			if (t == null || t.length() == 0) return "";
			StringBuilder res = new StringBuilder();
			for (int i = 0; i < t.length(); i++) {
				if (res.length() != 0 && t.charAt(i) == res.charAt(res.length() - 1)) {
					res.deleteCharAt(res.length() - 1);
				} else {
					res.append(t.charAt(i));
				}
			}
			return res.toString();
		}
	}
	interface Problem extends Function<String, String> {
		
	}
}
