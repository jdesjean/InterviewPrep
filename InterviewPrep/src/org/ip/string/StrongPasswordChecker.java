package org.ip.string;

import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/strong-password-checker/">LC: 420</a>
 */
public class StrongPasswordChecker {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {5, "a"};
		Object[] tc2 = new Object[] {3, "aA1"};
		Object[] tc3 = new Object[] {0, "1337C0d3"};
		Object[] tc4 = new Object[] {1, "aaaA1a"};
		Object[] tc5 = new Object[] {2, "1234567890123456Baaaa"};
		Object[] tc6 = new Object[] {8, "bbaaaaaaaaaaaaaaacccccc"};
		Object[] tc7 = new Object[] {3, "1234567890123456Baaaaa"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6, tc7};
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static int MIN = 6;
		private final static int MAX = 20;
		@Override
		public int applyAsInt(String value) {
			if (value == null) return MIN;
			else if (value.length() + 3 <= MIN) return MIN - value.length();
			boolean hasLower = false;
			boolean hasUpper = false;
			boolean hasDigit = false;
			int consecutive = 0;
			int one = 0;
			int two = 0;
			for (int i = 0, equal = 1; i < value.length(); i++) {
				char c = value.charAt(i);
				hasLower |= Character.isLowerCase(c);
				hasUpper |= Character.isUpperCase(c);
				hasDigit |= Character.isDigit(c);
				if (i > 0 && value.charAt(i - 1) == c) {
					equal++;
				} else {
					equal = 1;
				}
				if (equal != 0 && equal % 3 == 0) consecutive++;
				if (i == value.length() - 1 || value.charAt(i + 1) != c) {
					if (equal != 0 && equal % 3 == 0) one++;
					else if (equal != 1 && equal % 3 == 1) two++;
				}
			}
			int errors = 0;
			errors += hasLower ? 0 : 1;
			errors += hasUpper ? 0 : 1;
			errors += hasDigit ? 0 : 1;
			if (value.length() < MIN) {
				return Math.max(MIN - value.length(), errors);
			} else if (value.length() <= MAX) {
				return Math.max(errors, consecutive);
			} else {
				int delete = value.length() - MAX;
				consecutive -= Math.min(delete, one);
				consecutive -= Math.min(Math.max(delete - one, 0), two * 2) / 2;
				consecutive -= Math.max(delete - one - 2 * two, 0) / 3;
				return delete + Math.max(consecutive, errors);
			}
		}
		
	}
	interface Problem extends ToIntFunction<String> {
		
	}
}
