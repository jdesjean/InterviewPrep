package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/find-all-anagrams-in-a-string/">LC: 438</a>
 */
public class FindAllAnagrams {
	public static void main(String[] s) {
		var tc1 = new Object[] {Arrays.asList(0,6), "cbaebabacd", "abc"};
		var tc2 = new Object[] {Arrays.asList(0,1,2), "abab", "ab"};
		var tcs = new Object[][] {tc1, tc2};
		var solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public List<Integer> apply(String t, String u) {
			var res = new ArrayList<Integer>();
			var counts = counts(u);
			var match = counts.size();
			for (int r = 0, l = 0; r < t.length(); r++) {
				int count = counts.compute(t.charAt(r), (k, v) -> v == null ? -1 : v - 1);
				if (count == 0) {
					match--;
				} else if (count == -1) {
					match++;
				}
				if (r - l + 1> u.length()) {
					count = counts.compute(t.charAt(l++), (k, v) -> v == null ? 1 : v + 1);
					if (count == 0) {
						match--;
					} else if (count == 1) {
						match++;
					}
				}
				if (match == 0) {
					res.add(l);
				}
			}
			return res;
		}
		Map<Character, Integer> counts(String u) {
			return u.chars()
			.mapToObj((x) -> Character.valueOf((char) x))
			.collect(Collectors.toMap(Function.identity(), (x) -> Integer.valueOf(1), Integer::sum));
		}
		
	}
	public interface Problem extends BiFunction<String, String, List<Integer>> {
		
	}
}
