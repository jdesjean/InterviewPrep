package org.ip.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/palindrome-permutation/">LC: 266</a>
 */
public class PalindromePermutation {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {false, "code"};
		Object[] tc2 = new Object[] {true, "aab"};
		Object[] tc3 = new Object[] {true, "carerac"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		private final static byte[] ALL_MINUS_ONES = new byte[26];
		static {
			Arrays.fill(ALL_MINUS_ONES, (byte)-1);
		}
		@Override
		public Boolean apply(String t) {
			return odd(t) <= 1;
		}
		
		int odd(String t) {
			byte[] odd = Arrays.copyOf(ALL_MINUS_ONES, 26);
			int count = 0;
			for (int i = 0 ;i < t.length(); i++) {
				count += odd[t.charAt(i) - 'a'] *= -1;
			}
			return count;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public Boolean apply(String t) {
			Set<Character> odd = odd(t);
			return t.length() % 2 == 0 && odd.isEmpty() || t.length() % 2 == 1 && odd.size() == 1;
		}
		
		Set<Character> odd(String t) {
			Set<Character> odd = new HashSet<>(); 
			for (int i = 0; i < t.length(); i++) {
				Character c = t.charAt(i); 
				if (!odd.add(c)) {
					odd.remove(c);
				}
			}
			return odd;
		}
		
	}
	interface Problem extends Function<String, Boolean> {
		
	}
}
