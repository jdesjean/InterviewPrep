package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/word-break/">LC: 139</a>
 */
public class WordBreak {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, "catsanddog", Arrays.asList(new String[] {"cat", "cats", "and", "sand", "dog"})};
		Object[] tc2 = new Object[] {true, "pineapplepenapple", Arrays.asList(new String[] {"apple", "pen", "applepen", "pine", "pineapple"})};
		Object[] tc3 = new Object[] {false, "catsandog", Arrays.asList(new String[] {"cats", "dog", "sand", "and", "cat"})};
		Object[] tc4 = new Object[] {false, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Arrays.asList(new String[] {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"})};
		Object[] tc5 = new Object[] {true, "aaa", Arrays.asList(new String[] {"a","aa","aaa"})};
		Object[] tc6 = new Object[] {false, "ab", Arrays.asList(new String[] {"a"})};
		Object[][] tcs = new Object[][] {tc3, tc1, tc2, tc3, tc5, tc6};
		Problem[] solvers = new Problem[] {new SolverIterative()};
		Test.apply(solvers, tcs);
		//Test.apply(new Problem[] {new SolverIterative()}, new Object[][] {tc4});
	}
	public static class SolverIterative implements Problem {

		@Override
		public Boolean apply(String t, List<String> u) {
			if (t == null) return true;
			Set<String> dict = u
					.stream()
					.filter(s -> s != null && s.length() > 0)
					.collect(Collectors.toSet());
			if (hasCharacterNotInDict(dict, t)) {
				return false;
			}
			int min = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MAX_VALUE, Integer::min);
			int max = dict.stream()
					.mapToInt(s -> s.length())
					.reduce(Integer.MIN_VALUE, Integer::max);
			BitSet cache = new BitSet();
			cache.set(t.length());
			for (int i = t.length() - 1; i >= 0; i--) {
				for (int j = i + 1; j <= t.length(); j++) {
					int len = j - i;
					if (len < min) continue;
					if (len > max) break;
					if (!cache.get(j)) continue;
					String substring = t.substring(i, j);
					if (!dict.contains(substring)) continue;
					cache.set(i);
					break;
				}
			}
			return cache.get(0);
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
	public interface Problem extends BiFunction<String, List<String>, Boolean> {
		
	}
}
