package org.ip.string;

import org.ip.Test;
import org.ip.Test.TriPredicate;

/**
 * <a href="https://leetcode.com/problems/interleaving-string/">LC: 97</a>
 */
public class InterleavingString {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, "aabcc", "dbbca", "aadbbcbcac"};
		Object[] tc2 = new Object[] {false, "aabcc", "dbbca", "aadbbbaccc"};
		Object[] tc3 = new Object[] {true, "", "", ""};
		Object[] tc4 = new Object[] {false, "db", "b", "cbb"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver(), new SolverDP() };
		Test.apply(solvers, tcs);
	}
	static class SolverDP implements Problem {

		@Override
		public boolean test(String a, String b, String c) {
			if (a.length() + b.length() != c.length()) return false;
			boolean[][] cache = new boolean[a.length() + 1][b.length() + 1];
			cache[0][0] = true;
			for (int i = 0; i < b.length(); i++) {
				cache[0][i + 1] = cache[0][i] && b.charAt(i) == c.charAt(i);
			}
			for (int i = 0; i < a.length(); i++) {
				cache[i + 1][0] = cache[i][0] && a.charAt(i) == c.charAt(i);
			}
			for (int row = 1; row <= a.length(); row++) {
				for (int col = 1; col <= b.length(); col++) {
					char cc = c.charAt(row + col - 1);
					cache[row][col] = (cache[row][col - 1] && b.charAt(col - 1) == cc) 
							|| (cache[row - 1][col] && a.charAt(row - 1) == cc);
				}
			}
			return cache[a.length()][b.length()];
		}
		
	}
	static class Solver implements Problem {

		@Override
		public boolean test(String a, String b, String c) {
			if (a.length() + b.length() != c.length()) return false;
			return test(a, 0, b, 0, c);
		}
		boolean test(String a, int ia, String b, int ib, String c) {
			if (ia + ib == c.length()) {
				return true;
			}
			return ((ia < a.length() && a.charAt(ia) == c.charAt(ia + ib) && test(a, ia + 1, b, ib, c))
					|| (ib < b.length() && b.charAt(ib) == c.charAt(ia + ib) && test(a, ia, b, ib + 1, c)));
		}
		
	}
	interface Problem extends TriPredicate<String, String, String> {
		
	}
}
