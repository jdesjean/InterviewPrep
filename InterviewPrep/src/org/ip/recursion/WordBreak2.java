package org.ip.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// EPI: 17.7
public class WordBreak2 {
	public static void main(String[] s) {
		Set<String> set = new HashSet<String>();
		set.add("apple");
		set.add("pie");
		set.add("bed");
		set.add("bath");
		set.add("and");
		set.add("beyond");
		set.add("hand");
		System.out.println(new RecursiveSolve().solve("applepie",set));
		System.out.println(new DPSolve().solve("applepie",set));
		System.out.println(new DPSolve().solve("bedbathandbeyond",set));
	}
	public interface Solve {
		public List<String> solve(String s, Set<String> set);
	}
	public static class RecursiveSolve implements Solve {
		@Override
		public List<String> solve(String s, Set<String> set) {
			char[] buffer = s.toCharArray();
			return solve(s, buffer, 0, s.length()-1, set);
		}
		private List<String> solve(String s, char[] buffer, int l, int r, Set<String> set) {
			if (l > r) return null;
			for (int i = l; i <= r; i++) {
				String current = new String(buffer, l, i - l + 1);
				if (set.contains(current)) {
					if (i + 1 > r) {
						List<String> result = new ArrayList<String>();
						result.add(current);
						return result;
					} else {
						List<String> next = solve(s, buffer, i + 1, r, set);
						if (next != null) {
							next.add(0, current);
							return next;
						}
					}
				}
			}
			return null;
		}
		
	}
	public static class DPSolve implements Solve {

		@Override
		public List<String> solve(String s, Set<String> set) {
			String[] cache = new String[s.length()];
			char[] buffer = s.toCharArray();
			for (int i = s.length() - 1; i >= 0; i--) {
				String current = new String(buffer, i, s.length() - i);
				if (set.contains(current)) {
					cache[i] = current;
				} else {
					for (int j = i + 1; j < s.length(); j++) {
						if (cache[j] != null) {
							current = new String(buffer, i, j - i);
							if (set.contains(current)) {
								cache[i] = current;
								break;
							}
						}
					}
				}
			}
			if (cache[0] == null) return new ArrayList<String>();
			List<String> result = new ArrayList<String>();
			for (int i = 0; i < cache.length;) {
				if (cache[i] != null) {
					result.add(cache[i]);
					i += cache[i].length();
				}
			}
			return result;
		}
		
	}
}
