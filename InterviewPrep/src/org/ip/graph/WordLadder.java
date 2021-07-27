package org.ip.graph;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/word-ladder/">LC: 127</a>
 */
public class WordLadder {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 5, "hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog")};
		Object[] tc2 = new Object[] { 0, "hit", "cog", Arrays.asList("hot","dot","dog","lot","log")};
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static String SENTINEL = "";
		@Override
		public Integer apply(String s1, String s2, List<String> words) {
			Set<String> dictionary = new HashSet<>();
			dictionary.addAll(words);
			Deque<String> queue = new LinkedList<>();
			Set<String> visited = new HashSet<>();
			queue.add(s1);
			visited.add(s1);
			queue.add(SENTINEL);
			int depth = 1;
			while (!queue.isEmpty()) {
				String current = queue.remove();
				if (current == SENTINEL) {
					depth++;
					if (!queue.isEmpty()) {
						queue.add(SENTINEL);
					}
					continue;
				}
				if (current.compareTo(s2) == 0) {
					return depth;
				}
				//neighbors
				char[] buffer = current.toCharArray();
				for (int i = 0; i < current.length(); i++) {
					for (int j = 0; j < 26; j++) {
						buffer[i] = (char)(j + 'a');
						String neighbor = String.copyValueOf(buffer);
						if (visited.contains(neighbor) || !dictionary.contains(neighbor)) continue;
						queue.add(neighbor);
						visited.add(neighbor);
					}
					buffer[i] = current.charAt(i);
				}
			}
			return 0;
		}
		
	}
	interface Problem extends TriFunction<String, String, List<String>, Integer> {
		
	}
}
