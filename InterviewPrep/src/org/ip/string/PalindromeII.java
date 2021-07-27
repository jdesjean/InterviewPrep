package org.ip.string;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/valid-palindrome-ii/">LC: 680</a>
 */
public class PalindromeII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"aba", true};
		Object[] tc2 = new Object[] {"abca", true};
		Object[] tc3 = new Object[] {"abcd", false};
		Object[] tc4 = new Object[] {"acbc", true};
		Object[] tc5 = new Object[] {"atbbga", false};
		List<Object[]> tcs = Arrays.asList(tc5, tc1, tc2, tc3, tc4);
		Function<String, Boolean>[] solvers = new Function[] {new Solver()};
		for (Object[] tc : tcs) {
			for (Function<String, Boolean> solver : solvers) {
				System.out.print(String.valueOf(solver.apply((String) tc[0])) + "," + String.valueOf(tc[1]));
			}
			System.out.println();
		}
	}
	public static class Solver implements Function<String, Boolean> {

		@Override
		public Boolean apply(String t) {
			int idx = isPalindrome(t, 0, t.length() - 1);
			if (idx == -1) return true;
			return isPalindrome(t, idx, t.length() - 1 - idx - 1) == -1
					|| isPalindrome(t, idx + 1, t.length() - 1 - idx) == -1;
		}
		
		int isPalindrome(String s, int i, int j) {
			for (int l = i, r = j; l < r; l++, r--) {
				if (s.charAt(l) != s.charAt(r)) return l - i;
			}
			return -1;
		}
		
	}
}
