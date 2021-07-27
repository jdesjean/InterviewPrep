package org.ip.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/word-pattern-ii/">LC: 291</a>
 */
public class WordPatternII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, "abab", "redblueredblue"};
		Object[] tc2 = new Object[] {true, "aaaa", "asdasdasdasd"};
		Object[] tc3 = new Object[] {true, "abab", "asdasdasdasd"};
		Object[] tc4 = new Object[] {false, "aabb", "xyzabcxzyabc"};
		Object[] tc5 = new Object[] {true, "abba", "dogcatcatdog"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public boolean test(String pattern, String s) {
			Map<Character, Integer> count = new HashMap<>();
			Map<Character, String> values = new HashMap<>();
			Set<String> visited = new HashSet<>();
			for (int i = 0; i < pattern.length(); i++) {
				count.compute(pattern.charAt(i), (k ,v) -> v == null ? 1 : v + 1);
			}
			return _test(pattern, s, count, values, visited, 0, 0);
		}
		boolean _test(String pattern, String s, Map<Character, Integer> count, Map<Character, String> values, Set<String> visited, int pi, int si) {
			if (si == s.length() && pi == pattern.length()) return true;
			if (si == s.length() || pi == pattern.length()) return false;
			char p = pattern.charAt(pi);
			String value = values.get(p);
			if (value != null) {
				return s.startsWith(value, si)
						&& _test(pattern, s, count, values, visited, pi + 1, si + value.length());
			} else {
				for (int i = si; i < s.length(); i++) {
					String substring = s.substring(si, i + 1);
					if (!visited.add(substring)) continue;
					values.put(p, substring);
					if (validLength(count, values, s.length())
							&& _test(pattern, s, count, values, visited, pi + 1, i + 1)) {
						return true;
					}
					values.remove(p);
					visited.remove(substring);
				}
			}
			return false;
		}
		boolean validLength(Map<Character, Integer> count, Map<Character, String> values, int length) {
			int sum = 0;
			for (Map.Entry<Character, Integer> entry : count.entrySet()) {
				String value = values.get(entry.getKey());
				if (value == null) return true;
				sum += entry.getValue() * value.length();
			}
			return sum == length;
		}
	}
	interface Problem extends BiPredicate<String, String> {
		
	}
}
