package org.ip.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/strobogrammatic-number/">LC: 246</a>
 */
public class StrobogrammaticNumber {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, "69"};
		Object[] tc2 = new Object[] {true, "88"};
		Object[] tc3 = new Object[] {false, "962"};
		Object[] tc4 = new Object[] {true, "1"};
		Object[] tc5 = new Object[] {true, "101"};
		Object[] tc6 = new Object[] {false, "010"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {
		//final static char[] even = new char[] {'0', '1', '6', '8', '9'};
		//final static char[] evenReversed = new char[] {'0', '1', '9', '8', '6'};
		//final static char[] odd = new char[] {'0', '1', '8'};
		Map<Character, Character> even = new HashMap<>() {{
			put('0', '0');
			put('1', '1');
			put('6', '9');
			put('8', '8');
			put('9', '6');
		}};
		Set<Character> odd = new HashSet<>(Arrays.asList('0', '1', '8'));
		
		@Override
		public Boolean apply(String t) {
			for (int l = 0, r = t.length() - 1; l <= r; l++, r--) {
				if (l != r) {
					if (l == 0 && t.charAt(l) == '0') return false;
					Character reversed = even.get(t.charAt(l));
					if (reversed == null) return false;
					if (t.charAt(r) != reversed.charValue()) return false;
				} else {
					if (!odd.contains(t.charAt(l))) {
						return false;
					}
				}
			}
			return true;
		}
		
	}
	public interface Problem extends Function<String, Boolean> {
		
	}
}
