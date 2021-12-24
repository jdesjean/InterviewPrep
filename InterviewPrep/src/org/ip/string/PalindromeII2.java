package org.ip.string;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/valid-palindrome-ii/">LC: 680</a>
 */
public class PalindromeII2 {
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
		public Boolean apply(String s) {
			int j;
			return (j = isPalindrome(s, 0, s.length() - 1)) == -1 // string is not palindrome, check if left or right is the wrong element
					|| isPalindrome(s, j, s.length() - j - 1 - 1) == -1 // remove element from the right
					|| isPalindrome(s, j + 1, s.length() - j - 1) == -1; // remove element from the left
		}
		
		int isPalindrome(String s, int i, int j) {
			for (; i < j; i++, j--) {
				if (s.charAt(i) != s.charAt(j)) return i;
			}
			return -1;
		}
		
	}
}
