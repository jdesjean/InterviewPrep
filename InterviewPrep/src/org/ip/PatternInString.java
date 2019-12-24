package org.ip;

import java.util.HashMap;
import java.util.Map;

// Dropbox
public class PatternInString {
	public static void main(String[] s) {
		System.out.println(new PatternInString().solve("abba", "catdogdogcat"));
		System.out.println(new PatternInString().solve("abba", "catdogdogdogcat"));
		System.out.println(new PatternInString().solve("abba", "catdogcat"));
		System.out.println(new PatternInString().solve("abba", "catdogcatdog"));
	}
	public boolean solve(String pattern, String s) {
		Map<Character, Integer> map = new HashMap<Character,Integer>();
		Map<Character, Integer> count = new HashMap<Character,Integer>();
		for (int i = 0; i < pattern.length(); i++) {
			map.computeIfAbsent(pattern.charAt(i), k -> 1);
			count.compute(pattern.charAt(i), (k,v) -> v == null ? 1 : v + 1);
		}
		return solve(pattern, s, pattern.length(), map, count);
	}
	public boolean solve(String pattern, String s, int size, Map<Character,Integer> map, Map<Character,Integer> count) {
		if (size > s.length()) {
			return false;
		}
		//for current mapping check if there's a pattern
		for (int j = s.length() - size; j >= 0; j--) {
			if (isPattern(pattern, s, j, map)) {
				return true;
			}
		}
		//add 1 length to each character
		for (Character c : map.keySet()) {
			map.compute(c, (k,v) -> v + 1);
			boolean result = solve(pattern, s, size + count.get(c), map, count);
			if (result) {
				return result;
			}
			map.compute(c, (k,v) -> v - 1);
		}
		return false;
	}
	private boolean isPattern(String pattern, String s, int index, Map<Character,Integer> map) {
		Map<Character,String> patterns = new HashMap<Character,String>();
		for (int i = 0; i < pattern.length(); i++) {
			int length = map.get(pattern.charAt(i));
			char[] buffer = new char[length];
			for (int j = 0; j < length; j++) {
				buffer[j] = s.charAt(index + j);
			}
			if (!patterns.containsKey(pattern.charAt(i))) {
				patterns.put(pattern.charAt(i), new String(buffer));
			} else {
				if (patterns.get(pattern.charAt(i)).compareTo(new String(buffer)) != 0) {
					return false;
				}
			}
			index += length;
		}
		return true;
	}
}
