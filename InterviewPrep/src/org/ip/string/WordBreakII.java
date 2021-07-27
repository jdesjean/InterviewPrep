package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/word-break-ii/">LC: 140</a>
 */
public class WordBreakII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {Arrays.asList(new String[] {"cats and dog", "cat sand dog"}), "catsanddog", Arrays.asList(new String[] {"cat", "cats", "and", "sand", "dog"})};
		Object[] tc2 = new Object[] {Arrays.asList(new String[] {"pine apple pen apple", "pineapple pen apple", "pine applepen apple"}), "pineapplepenapple", Arrays.asList(new String[] {"apple", "pen", "applepen", "pine", "pineapple"})};
		Object[] tc3 = new Object[] {Arrays.asList(new String[] {""}), "catsandog", Arrays.asList(new String[] {"cats", "dog", "sand", "and", "cat"})};
		Object[] tc4 = new Object[] {Arrays.asList(new String[] {""}), "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Arrays.asList(new String[] {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"})};
		Object[] tc5 = new Object[] {Arrays.asList(new String[] {"a a a", "a aa", "aa a", "aaa"}), "aaa", Arrays.asList(new String[] {"a","aa","aaa"})};
		Object[] tc6 = new Object[] {Arrays.asList(new String[] {}), "ab", Arrays.asList(new String[] {"a"})};
		Object[][] tcs = new Object[][] {tc6, tc1, tc2, tc3, tc5};
		Problem[] solvers = new Problem[] {new SolverRecursive(), new SolverIterative(), new SolverIterative2(), new SolverIterative3()};
		Test.apply(solvers, tcs);
		//Test.apply(new Problem[] {new SolverIterative()}, new Object[][] {tc4});
	}
	public static class SolverIterative3 implements Problem {

		@Override
		public List<String> apply(String t, List<String> u) {
			if (t == null) return new ArrayList<>();
			Set<String> dict = u
					.stream()
					.filter(s -> s != null && s.length() > 0)
					.collect(Collectors.toSet());
			if (hasCharacterNotInDict(dict, t)) {
				return new ArrayList<>();
			}
			int min = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MAX_VALUE, Integer::min);
			int max = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MIN_VALUE, Integer::max);
			List[] cache = new List[t.length() + 1];
			cache[t.length()] = new ArrayList<>();
			cache[t.length()].add(t.length());
			for (int i = t.length() - 1; i >= 0; i--) {
				cache[i] = new ArrayList<>();
				for (int j = i + 1; j <= t.length(); j++) {
					int len = j - i;
					if (len < min) continue;
					if (len > max) break;
					if (cache[j].size() == 0) continue;
					String substring = t.substring(i, j);
					if (!dict.contains(substring)) continue;
					cache[i].add(j);
				}
			}
			List<String> result = new ArrayList<String>();
			accumulate(string -> result.add(string), cache, 0, new StringBuilder(), t);
			return result;
		}
		void accumulate(Consumer<String> consumer, List<Integer>[] cache, int index, StringBuilder sb, String t) {
			if (index == t.length()) {
				consumer.accept(sb.toString());
				return;
			}
			int prevLength = sb.length();
			for (Integer next : cache[index]) {
				append(sb, t.substring(index, next));
				accumulate(consumer, cache, next, sb, t);
				sb.setLength(prevLength);
			}
		}
	}
	public static class SolverIterative2 implements Problem {

		@Override
		public List<String> apply(String t, List<String> u) {
			if (t == null) return new ArrayList<>();
			Set<String> dict = u
					.stream()
					.filter(s -> s != null && s.length() > 0)
					.collect(Collectors.toSet());
			if (hasCharacterNotInDict(dict, t)) {
				return new ArrayList<>();
			}
			int min = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MAX_VALUE, Integer::min);
			int max = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MIN_VALUE, Integer::max);
			List[] cache = new List[t.length() + 1];
			cache[t.length()] = new ArrayList<>();
			cache[t.length()].add(Arrays.asList(t.length()));
			for (int i = t.length() - 1; i >= 0; i--) {
				cache[i] = new ArrayList<>();
				for (int j = i + 1; j <= t.length(); j++) {
					int len = j - i;
					if (len < min) continue;
					if (len > max) break;
					String substring = t.substring(i, j);
					if (!dict.contains(substring)) continue;
					for (List<Integer> word : (List<List<Integer>>) cache[j]) {
						List<Integer> integer = new ArrayList<>();
						integer.add(i);
						integer.addAll(word);
						cache[i].add(integer);
					}
				}
			}
			List<String> res = new ArrayList<>();
			for (List<Integer> sentense : (List<List<Integer>>) cache[0]) {
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < sentense.size(); i++) {
					if (sb.length() > 0) {
						sb.append(" ");
					}
					sb.append(t.substring(sentense.get(i - 1), sentense.get(i)));
				}
				res.add(sb.toString());
			}
			return res;
		}
		
	}
	public static class SolverIterative implements Problem {

		@Override
		public List<String> apply(String t, List<String> u) {
			if (t == null) return new ArrayList<>();
			Set<String> dict = u
					.stream()
					.filter(s -> s != null && s.length() > 0)
					.collect(Collectors.toSet());
			if (hasCharacterNotInDict(dict, t)) {
				return new ArrayList<>();
			}
			int min = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MAX_VALUE, Integer::min);
			int max = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MIN_VALUE, Integer::max);
			List[] cache = new List[t.length() + 1];
			cache[t.length()] = new ArrayList<>();
			cache[t.length()].add("");
			for (int i = t.length() - 1; i >= 0; i--) {
				cache[i] = new ArrayList<>();
				for (int j = i + 1; j <= t.length(); j++) {
					int len = j - i;
					if (len < min) continue;
					if (len > max) break;
					String substring = t.substring(i, j);
					if (!dict.contains(substring)) continue;
					for (String word : (List<String>) cache[j]) {
						if (word.length() > 0) {
							cache[i].add(substring + " " + word);
						} else {
							cache[i].add(substring);
						}
					}
				}
			}
			return cache[0];
		}
		
	}
	public static class SolverRecursive implements Problem {

		@Override
		public List<String> apply(String t, List<String> u) {
			if (t == null) return new ArrayList<>();
			Set<String> dict = u
					.stream()
					.filter(s -> s != null && s.length() > 0)
					.collect(Collectors.toSet());
			if (hasCharacterNotInDict(dict, t)) {
				return new ArrayList<>();
			}
			 
			int min = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MAX_VALUE, Integer::min);
			int max = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MIN_VALUE, Integer::max);
			StringBuilder sb = new StringBuilder();
			List<String> res = new ArrayList<>();
			_apply(sentense -> res.add(sentense), t, 0, 1, dict, sb, min , max);
			return res;
		}
		
		void _apply(Consumer<String> consumer, String t, int prevIndex, int index, Set<String> dict, StringBuilder sb, int min, int max) {
			if (index == t.length()) {
				if (append(sb, t, dict, prevIndex, index)) {
					consumer.accept(sb.toString());
				}
				return;
			} else if (index > t.length()) { // overshot
				return;
			}
			int prevLength = sb.length();
			if (append(sb, t, dict, prevIndex, index)) {
				_apply(consumer, t, index, index + min, dict, sb, min, max);
				sb.setLength(prevLength);
			}
			if (index - prevIndex < max) {
				_apply(consumer, t, prevIndex, index + 1, dict, sb, min, max);
			}
		}
	}
	static Set<Character> characterSet(Set<String> set) {
		Set<Character> characters = new HashSet<>();
		for (String word : set) {
			characterSet(characters, word);
		}
		return characters;
	}
	static void characterSet(Set<Character> characters, String word) {
		for (int i = 0; i < word.length(); i++) {
			characters.add(word.charAt(i));
		}
	}
	static boolean hasCharacterNotInDict(Set<String> dict, String t) {
		Set<Character> characterSetWord = new HashSet<>();
		characterSet(characterSetWord, t);
		Set<Character> characterSetDict = characterSet(dict);
		characterSetWord.removeAll(characterSetDict);
		return !characterSetWord.isEmpty();
	}
	static boolean append(StringBuilder sb, String t, Set<String> dict, int prevIndex, int index) {
		if (index - prevIndex > 0) {
			String substring = t.substring(prevIndex, index);
			if (dict.contains(substring)) {
				append(sb, substring);
				return true;
			}
		}
		return false;
	}
	static void append(StringBuilder sb, String s) {
		if (sb.length() > 0) {
			sb.append(" ");
		}
		sb.append(s);
	}
	public interface Problem extends BiFunction<String, List<String>, List<String>> {
		
	}
}
