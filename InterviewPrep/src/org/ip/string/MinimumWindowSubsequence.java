package org.ip.string;

import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-window-subsequence/">LC: 727</a>
 */
public class MinimumWindowSubsequence {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { "bcde", "abcdebdde", "bde"};
		Object[] tc2 = new Object[] { "bde", "abcdebdbde", "bde"};
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver(), new DPSolver() };
		Test.apply(solvers, tcs);
	}
	static class DPSolver implements Problem {

		@Override
		public String apply(String t, String u) {
			int[][] cache = new int[u.length()][t.length()];
			for (int iu = 0; iu < u.length(); iu++) {
				for (int it = 0; it < t.length(); it++) {
					int cu = iu > 0 ? cache[iu - 1][it] : 1;
					int ct = it == 0 ? Integer.MAX_VALUE : cache[iu][it - 1];
					if (t.charAt(it) == u.charAt(iu)) {
						cache[iu][it] = Math.max(cu, 1);
					} else {
						cache[iu][it] = Math.max(cu, ct == Integer.MAX_VALUE ? Integer.MAX_VALUE : ct + 1);
					}
				}
			}
			int min = 0;
			int iu = u.length() - 1;
			for (int i = 1; i < t.length(); i++) {
				if (cache[iu][i] < cache[iu][min]) {
					min = i;
				}
			}
			if (cache[iu][min] == Integer.MAX_VALUE) return "";
			return t.substring(min + 1 - cache[iu][min], min + 1);
		}
		
	}
	static class Solver implements Problem {

		@Override
		public String apply(String t, String u) {
			int count = Integer.MAX_VALUE;
			int pos = -1;
			for (int it = 0, iu = 0; it < t.length(); it++) {
 				if (t.charAt(it) == u.charAt(iu)) {
 					iu++;
 				}
 				if (iu == u.length()) { // We have complete sequence, find smallest
 					int end = it;
 					while (--iu >= 0) {
 						while (t.charAt(it--) != u.charAt(iu)) {}
 					}
 					it++;
 					iu++;
 					int c = end - it + 1;
 					if (c < count) {
 						count = c;
 	 					pos = it;
 					}
 					if (c == u.length()) break; // smallest achieved no need to look further
 				}
			}
			return count != Integer.MAX_VALUE ? t.substring(pos, pos + count) : "";
		}
		
	}
	interface Problem extends BiFunction<String, String, String> {
		
	}
}
