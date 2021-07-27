package org.ip.string;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/palindromic-substrings/">LC: 647</a>
 */
public class PalindromicSubstring {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, "abc"};
		Object[] tc2 = new Object[] {6, "aaa"};
		Object[] tc3 = new Object[] {6, "fdsklf"};
		Object[] tc4 = new Object[] {64, "bbccaacacdbdbcbcbbbcbadcbdddbabaddbcadb"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new DPSolver(), new RecursiveSolver(), new CubeSolver()};
		Test.apply(solvers, tcs);
	}
	static class DPSolver implements Problem {

		@Override
		public Integer apply(String t) {
			boolean[][] cache = new boolean[t.length()][t.length()];
			int count = 0;
			for (int i = 0; i < t.length(); i++) {
				cache[i][i] = true;
				count++;
			}
			for (int i = 1; i < t.length(); i++) {
				cache[i - 1][i] = t.charAt(i) == t.charAt(i - 1);
				if (cache[i - 1][i]) count++;
			}
			for (int len = 3; len <= t.length(); len++) {
				for (int i = 0, j = len - 1; j < t.length(); i++, j++) {
					if (!cache[i + 1][j - 1]) continue;
					cache[i][j] = t.charAt(i) == t.charAt(j);
					if (cache[i][j]) count++;
				}
			}
			return count;
		}
	}
	static class RecursiveSolver implements Problem {

		@Override
		public Integer apply(String t) {
			int count = 0;
			Boolean[][] cache = new Boolean[t.length()][t.length()];
			for (int i = 0; i < t.length(); i++) {
				for (int j = i; j < t.length(); j++) {
					if (isPalindrome(t, i, j, cache)) count++;
				}
			}
			return count;
		}

		boolean isPalindrome(String t, int l, int r, Boolean[][] cache) {
			if (cache[l][r] != null) {
				return cache[l][r] == true;
			}
			if (l >= r) {
				cache[l][r] = true;
				return true;
			}
			if (t.charAt(l) != t.charAt(r)) {
				cache[l][r] = false;
				return false;
			}
			return isPalindrome(t, l + 1, r - 1, cache);
		}
		
	}
	static class CubeSolver implements Problem {

		@Override
		public Integer apply(String t) {
			int count = 0;
			for (int l = 0; l < t.length(); l++) {
				for (int r = l; r < t.length(); r++) {
					if (isPalindrome(t, l, r)) count++; 
				}
			}
			return count;
		}
		boolean isPalindrome(String t, int l, int r) {
			for (; l < r; l++, r--) {
				if (t.charAt(l) != t.charAt(r)) return false;
			}
			return true;
		}
	}
	
	interface Problem extends Function<String, Integer> {
		
	}
}
