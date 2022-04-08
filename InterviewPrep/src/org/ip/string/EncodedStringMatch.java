package org.ip.string;

import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/check-if-an-original-string-exists-given-two-encoded-strings/">LC: 2060</a>
 */
public class EncodedStringMatch {
	public static void main(String[] s) {
		var tc1 = new Object[] {true, "internationalization", "i18n"};
		var tc2 = new Object[] {true, "l123e", "44"};
		var tc3 = new Object[] {false, "a5b", "c5b"};
		var tc4 = new Object[] {true, "13", "22"};
		var tc5 = new Object[] {true, "112s", "g841"};
		var tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		var solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public boolean test(String t, String u) {
			boolean[][][] dp = new boolean[t.length() + 1][u.length() + 1][2000];
			return _test(t, 0, u, 0, 0, dp);
		}
		
		/*
		 * 3 states
		 * i < s, k != 0, isDigit(s.charAt(i)); 
		 */
		boolean _test(String s1, int i, String s2, int j, int k, boolean[][][] dp) {
			if (i == s1.length() && j == s2.length()) {
				return k == 0;
			}
			if (dp[i][j][1000 + k]) {
				return false;
			}
			dp[i][j][1000 + k] = true;
			if (i < s1.length() && Character.isDigit(s1.charAt(i))) {
				return processDigit(s1, i, s2, j, k, true, dp);
			} else if (j < s2.length() && Character.isDigit(s2.charAt(j))) {
				return processDigit(s1, i, s2, j, k, false, dp);
			}
			if (k > 0) {
				return i < s1.length() && _test(s1, i + 1, s2, j, k - 1, dp);
			}
			else if (k < 0) {
				return j < s2.length() && _test(s1, i, s2, j + 1, k + 1, dp);
			}
			return i < s1.length() && j < s2.length() && s1.charAt(i) == s2.charAt(j) && _test(s1, i + 1, s2, j + 1, k, dp);
		}
		boolean processDigit(String s1, int i, String s2, int j, int k, boolean useI, boolean[][][] dp) {
			String s = useI ? s1 : s2;
			int sign = useI ? -1 : 1; 
			for (int l = useI ? i : j, v = 0; l < s.length() && Character.isDigit(s.charAt(l)); l++) {
				v = v * 10 + Character.digit(s.charAt(l), 10);
				if (_test(s1, useI ? l + 1 : i, s2, useI ? j : l + 1, k + v * sign, dp)) {
					return true;
				}
			}
			return false;
		}
	}
	static class Solver implements Problem {

		@Override
		public boolean test(String t, String u) {
			return _test(t, 0, u, 0, 0);
		}
		
		/*
		 * 3 states
		 * i < s, k != 0, isDigit(s.charAt(i)); 
		 */
		boolean _test(String s1, int i, String s2, int j, int k) {
			if (i == s1.length() && j == s2.length()) {
				return k == 0;
			}
			if (i < s1.length() && Character.isDigit(s1.charAt(i))) {
				return processDigit(s1, i, s2, j, k, true);
			} else if (j < s2.length() && Character.isDigit(s2.charAt(j))) {
				return processDigit(s1, i, s2, j, k, false);
			}
			if (k > 0) {
				return i < s1.length() && _test(s1, i + 1, s2, j, k - 1);
			}
			else if (k < 0) {
				return j < s2.length() && _test(s1, i, s2, j + 1, k + 1);
			}
			return i < s1.length() && j < s2.length() && s1.charAt(i) == s2.charAt(j) && _test(s1, i + 1, s2, j + 1, k);
		}
		boolean processDigit(String s1, int i, String s2, int j, int k, boolean useI) {
			String s = useI ? s1 : s2;
			int sign = useI ? -1 : 1; 
			for (int l = useI ? i : j, v = 0; l < s.length() && Character.isDigit(s.charAt(l)); l++) {
				v = v * 10 + Character.digit(s.charAt(l), 10);
				if (_test(s1, useI ? l + 1: i, s2, useI ? j : l + 1, k + v * sign)) {
					return true;
				}
			}
			return false;
		}
	}
	interface Problem extends BiPredicate<String, String> {
		
	}
}
