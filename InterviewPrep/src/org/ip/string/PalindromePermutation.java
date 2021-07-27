package org.ip.string;

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
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

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
	public interface Problem extends Function<String, Boolean> {
		
	}
}
