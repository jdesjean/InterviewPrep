package org.ip.string;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/valid-anagram/">LC: 242</a>
 */
public class ValidAnagram {
	public static void main(String[] s) {
		var tc1 = new Object[] {true, "anagram", "nagaram"};
		var tc2 = new Object[] {false, "rat", "car"};
		var tcs = new Object[][] {tc1, tc2};
		var solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public boolean test(String t, String u) {
			if (t == null || u == null) return false;
			if (t.length() != u.length()) return false;
			var counts = counts(u);
			for (int r = 0; r < t.length(); r++) {
				int count = counts.compute(t.charAt(r), (k, v) -> v == null ? -1 : v - 1);
				if (count < 0) return false;
			}
			return true;
		}
		Map<Character, Integer> counts(String u) {
			return u.chars()
			.mapToObj((x) -> Character.valueOf((char) x))
			.collect(Collectors.toMap(Function.identity(), (x) -> Integer.valueOf(1), Integer::sum));
		}
		
	}
	public interface Problem extends BiPredicate<String, String> {
		
	}
}
