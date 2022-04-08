package org.ip.string;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import org.ip.Test;
import org.ip.string.Trie.Node;

/**
 * <a href="https://leetcode.com/problems/word-break-ii/">LC: 140</a>
 */
public class WordBreakII2 {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {Arrays.asList(new String[] {"cats and dog", "cat sand dog"}), "catsanddog", Arrays.asList(new String[] {"cat", "cats", "and", "sand", "dog"})};
		Object[] tc2 = new Object[] {Arrays.asList(new String[] {"pine apple pen apple", "pineapple pen apple", "pine applepen apple"}), "pineapplepenapple", Arrays.asList(new String[] {"apple", "pen", "applepen", "pine", "pineapple"})};
		Object[] tc3 = new Object[] {Arrays.asList(new String[] {""}), "catsandog", Arrays.asList(new String[] {"cats", "dog", "sand", "and", "cat"})};
		Object[] tc4 = new Object[] {Arrays.asList(new String[] {""}), "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Arrays.asList(new String[] {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"})};
		Object[] tc5 = new Object[] {Arrays.asList(new String[] {"a a a", "a aa", "aa a", "aaa"}), "aaa", Arrays.asList(new String[] {"a","aa","aaa"})};
		Object[] tc6 = new Object[] {Arrays.asList(new String[] {}), "ab", Arrays.asList(new String[] {"a"})};
		Object[] tc7 = new Object[] {Arrays.asList(new String[] {"a a a a", "a a aa", "a aa a", "a aaa", "aa a a", "aa aa", "aaa a"}), "aaaa", Arrays.asList(new String[] {"a","aa","aaa"})};
		Object[] tc8 = new Object[] {Arrays.asList(new String[] {}), "aaaaaaa", Arrays.asList(new String[] {"aaaa","aa"})};
		Object[][] tcs = new Object[][] {tc8, tc6, tc1, tc2, tc3, tc5, tc7, tc4};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<String> apply(String t, List<String> u) {
			if (hasCharacterNotInDict(u, t)) {
				return new ArrayList<>();
			}
			Trie trie = new Trie(u);
			List<String> res = new ArrayList<>();
			_apply(res, trie, t, 0, new ArrayDeque<>());
			return res;
		}
		void _apply(List<String> res, Trie trie, String t, int index, Deque<String> words) {
			if (index == t.length()) {
				res.add(String.join(" " , words));
				return;
			}
			Node node = null;
			for (int i = index; i < t.length(); i++) {
				node = index == i ? trie.getNode(t.charAt(i)) : trie.getNode(t.charAt(i), node);
				if (node == null) break;
				if (node.end != null) {
					words.add(node.end);
					_apply(res, trie, t, i + 1, words);
					words.removeLast();
				}
			}
			
		}
		
		static boolean hasCharacterNotInDict(List<String> dict, String t) {
			Set<Character> characterSetWord = new HashSet<>();
			characterSet(characterSetWord, t);
			Set<Character> characterSetDict = characterSet(dict);
			characterSetWord.removeAll(characterSetDict);
			return !characterSetWord.isEmpty();
		}
		static void characterSet(Set<Character> characters, String word) {
			for (int i = 0; i < word.length(); i++) {
				characters.add(word.charAt(i));
			}
		}
		static Set<Character> characterSet(List<String> set) {
			Set<Character> characters = new HashSet<>();
			for (String word : set) {
				characterSet(characters, word);
			}
			return characters;
		}
		
	}
	interface Problem extends BiFunction<String, List<String>, List<String>> {
		
	}
}
