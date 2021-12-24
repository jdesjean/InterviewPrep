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
		Object[] tc3 = new Object[] { false, "substitution", "s55n" };
		Object[] tc4 = new Object[] { false, "substitution", "s010n" };
		Object[] tc5 = new Object[] { false, "substitution", "s0ubstitution" };
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5 };
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}

	static class Solver implements Problem {

		@Override
		public boolean test(String t, String u) {
			int iu = 0, it = 0;
			for (; it < t.length() && iu < u.length(); ) {
				if (Character.isDigit(u.charAt(iu))) {
					int n = 0;
					for (; iu < u.length() && Character.isDigit(u.charAt(iu)); iu++) {
						n = n * 10 + (u.charAt(iu) - '0');
						if (n == 0) return false;
					}
					it += n;
				} else if (t.charAt(it++) != u.charAt(iu++)) return false;
			}
			return iu == u.length() && it == t.length();
		}

	}

	interface Problem extends BiPredicate<String, String> {

	}
}
