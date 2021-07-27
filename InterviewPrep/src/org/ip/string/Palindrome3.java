package org.ip.string;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.ip.string.AlienDictionaryAlphabet.Solver;

/**
 * <a href="https://leetcode.com/problems/valid-palindrome/">LC: 125</a>
 */
public class Palindrome3 {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, "A man, a plan, a canal: Panama"};
		Object[] tc2 = new Object[] {false, "race a car"};
		Object[] tc3 = new Object[] {true, ""};
		Object[] tc4 = new Object[] {true, " :,"};
		Object[] tc5 = new Object[] {true, "a"};
		Object[] tc6 = new Object[] {true, " :,a"};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4, tc5, tc6);
		Function<String, Boolean>[] solvers = new Function[] {new Solver()};
		for (Object[] tc : tcs) { 
			System.out.print(String.valueOf(tc[0]));
			for (Function<String, Boolean> solver : solvers) {
				System.out.print("," + solver.apply((String) tc[1]));
			}
			System.out.println();
		}
	}
	public static class Solver implements Function<String, Boolean> {

		@Override
		public Boolean apply(String t) {
			for (int l = 0, r = t.length() - 1; l < r; ) {
				if (!Character.isLetterOrDigit(t.charAt(l))) {
					l++;
				} else if (!Character.isLetterOrDigit(t.charAt(r))) {
					r--;
				} else {
					if (Character.toLowerCase(t.charAt(l)) != Character.toLowerCase(t.charAt(r))) return false;
					l++;
					r--;
				}
			}
			return true;
		}
		
	}
}
