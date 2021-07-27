package org.ip.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/minimum-window-substring/solution/">LC: 76</a>
 */
public class MinimumWindowSubstring {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"BANC", "ADOBECODEBANC", "ABC"};
		Object[] tc2 = new Object[] {"a", "a", "a"};
		Object[] tc3 = new Object[] {"", "ab", "A"};
		Object[] tc4 = new Object[] {"aa", "aa", "aa"};
		Object[] tc5 = new Object[] {"ba", "bba", "ba"};
		Object[] tc6 = new Object[] {"cwae", "cabwefgewcwaefgcf", "cae"};
		Object[][] tcs = new Object[][] {tc6, tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public String apply(String t, String u) {
			Set<Character> unique = unique(u);
			List<CharacterIndex> filtered = filter(t, unique);
			Map<Character, Integer> distribution = distribution(u);
			Map<Character, Integer> counts = new HashMap<>();
			int distinct = 0;
			boolean hasResult = false;
			StartEnd startEnd = new StartEnd(0, t.length());
			for (int l = 0, r = -1; r < filtered.size(); ) {
				if (distinct < unique.size()) {
					if (r == filtered.size() - 1) break;
					CharacterIndex pair = filtered.get(++r);
					int res = counts.compute(pair.c, (k, v) -> v == null ? 1 : v + 1);
					if (res == distribution.get(pair.c)) {
						if (++distinct >= unique.size()) {
							hasResult = true;
							if (startEnd.end - startEnd.start > filtered.get(r).index - filtered.get(l).index) {
								startEnd.start = filtered.get(l).index;
								startEnd.end = filtered.get(r).index;
							}
						}
					}
				} else {
					CharacterIndex pair = filtered.get(l++);
					int res = counts.compute(pair.c, (k, v) -> v == null ? 0 : v - 1);
					if (res == distribution.get(pair.c) - 1) {
						--distinct;
					}
					if (distinct >= unique.size()) {
						hasResult = true;
						if (startEnd.end - startEnd.start > filtered.get(r).index - filtered.get(l).index) {
							startEnd.start = filtered.get(l).index;
							startEnd.end = filtered.get(r).index;
						}
					}
				}
				
			}
			return hasResult ? t.substring(startEnd.start, startEnd.end + 1) : "";
		}
		List<CharacterIndex> filter(String t, Set<Character> set) {
			List<CharacterIndex> pairs = new ArrayList<>(t.length()); 
			for (int i = 0; i < t.length(); i++) {
				char c = t.charAt(i);
				if (!set.contains(c)) continue;
				pairs.add(new CharacterIndex(c, i));
			}
			return pairs;
		}
		Set<Character> unique(String u) {
			Set<Character> set = new HashSet<>();
			for (int i = 0; i < u.length(); i++) {
				set.add(u.charAt(i));
			}
			return set;
		}
		Map<Character, Integer> distribution(String u) {
			Map<Character, Integer> distribution = new HashMap<>();
			for (int i = 0; i < u.length(); i++) {
				distribution.compute(u.charAt(i), (k, v) -> v == null ? 1 : v + 1);
			}
			return distribution;
		}
		public static class CharacterIndex {
			final char c;
			final int index;
			public CharacterIndex(char c, int index) {
				this.c = c;
				this.index = index;
			}
		}
		public static class StartEnd {
			int start;
			int end;
			public StartEnd(int start, int end) {
				this.start = start;
				this.end = end;
			}
		}
		
	}
	public interface Problem extends BiFunction<String, String, String> {
		
	}
}
