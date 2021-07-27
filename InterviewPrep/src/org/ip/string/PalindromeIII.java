package org.ip.string;

import java.util.Arrays;
import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/valid-palindrome-iii/solution/">LC: 1216</a>
 */
public class PalindromeIII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { true, "abcdeca", 2};
		Object[] tc2 = new Object[] { true, "abbababa", 1};
		Object[] tc3 = new Object[] { false, "abcdeca", 1};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new RecursiveSolver(), new DPSolver() };
		Test.apply(solvers, tcs);
	}
	static class DPSolver implements Problem {

		@Override
		public boolean test(String t, Integer u) {
			int k = u;
			int[] cache = new int[t.length()];
			int temp = 0;
			for (int l = t.length() - 2; l >= 0; l--) {
				int prev = 0;
				for (int r = l + 1; r < t.length(); r++) {
					temp = cache[r];
					if (t.charAt(l) == t.charAt(r)) {
						cache[r] = prev; //cache[l + 1][r - 1]
					} else {
						cache[r] = 1 + Math.min(cache[r], cache[r - 1]);
					}
					prev = temp;
				}
				
			}
			
			return cache[t.length() - 1] <= k;
		}
		
	}
	static class RecursiveSolver implements Problem {

		@Override
		public boolean test(String t, Integer u) {
			int k = u;
			int[][] cache = new int[t.length()][t.length()];
			for (int i = 0; i < cache.length; i++) {
				Arrays.fill(cache[i], -1);
			}
			int min = test(t, cache, 0, t.length() - 1);
			return min <= k;
		}
		int test(String t, int[][] cache, int l, int r) {
			if (l >= r) return 0;
			if (cache[l][r] != -1) return cache[l][r];
			if (t.charAt(l) == t.charAt(r)) {
				cache[l][r] = test(t, cache, l + 1, r - 1);
			} else {
				cache[l][r] = 1 + Math.min(test(t, cache, l + 1, r), test(t, cache, l, r - 1));
			}
			return cache[l][r];
		}
	}
	interface Problem extends BiPredicate<String, Integer> {
		
	}
}
