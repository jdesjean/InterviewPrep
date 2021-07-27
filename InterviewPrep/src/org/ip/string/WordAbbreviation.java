package org.ip.string;

import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/valid-word-abbreviation/">LC: 408</a>
 */
public class WordAbbreviation {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { true, "internationalization", "i12iz4n" };
		Object[] tc2 = new Object[] { false, "apple", "a2e" };
		Object[][] tcs = new Object[][] { tc1, tc2 };
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}

	static class Solver implements Problem {

		@Override
		public boolean test(String t, String u) {
			int val = 0;
			int i = 0, j = 0;
			while (i < t.length() && j < u.length()) {
				if (t.charAt(i) == u.charAt(j)) {
					i++;
					j++;
				} else {
					while (j < u.length() && Character.isDigit(u.charAt(j))) {
						val *= 10;
						val += (u.charAt(j) - '0');
						if (val == 0)
							return false; // starts with a 0
						j++;
					}
					if (val == 0)
						return false;
					i += val;
					val = 0;
				}
			}
			return i == t.length() && j == u.length();
		}

	}

	interface Problem extends BiPredicate<String, String> {

	}
}
