package org.ip.string;

import java.util.BitSet;
import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/construct-k-palindrome-strings/">LC: 1400</a>
 */
public class ConstructKPalindrome {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, "annabelle", 2};
		Object[] tc2 = new Object[] {false, "leetcode", 3};
		Object[] tc3 = new Object[] {true, "true", 4};
		Object[] tc4 = new Object[] {true, "yzyzyzyzyzyzyzy", 2};
		Object[] tc5 = new Object[] {false, "cr", 7};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public boolean test(String t, Integer u) {
			int k = u;
			if (t.length() < k) return false;
			int odd = freqs(t);
			if (odd > k) return false;
			return true;
		}
		int freqs(String t) {
			BitSet odds = new BitSet();
			int odd = 0;
			for (int i = 0; i < t.length(); i++) {
				int index = t.charAt(i) - 'a';
				odds.flip(index);
			}
			for (int i = 0; i < 26; i++) {
				if (odds.get(i)) odd++;
			}
			return odd;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public boolean test(String t, Integer u) {
			int k = u;
			if (t.length() < k) return false;
			int odd = freqs(t);
			if (odd > k) return false;
			return true;
		}
		int freqs(String t) {
			int[] freqs = new int[26];
			int odd = 0;
			for (int i = 0; i < t.length(); i++) {
				if (++freqs[t.charAt(i) - 'a'] % 2 == 1) odd++;
				else odd--;
			}
			return odd;
		}
		
	}
	interface Problem extends BiPredicate<String, Integer> {
		
	}
}
