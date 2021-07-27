package org.ip.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/guess-the-word/">LC: 843</a>
 */
public class GuessTheWord {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { "acckzz", new String[] {"acckzz","ccbazz","eiowzz","abcczz"}, "acckzz" };
		Object[][] tcs = new Object[][] { tc1};
		for (Object[] tc : tcs) {
			tc[2] = new Master((String[])tc[1], (String)tc[2]);
		}
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public String apply(String[] t, Master u) {
			Set<Integer> possible = new HashSet<>();
			for (int i = 0; i < t.length; i++) {
				possible.add(i);
			}
			for (int i = 0; i < 9; i++) {
				int idx = i == 0 ? firstGuess(t) : possible.iterator().next();
				int count = u.guess(t[idx]);
				if (count == 6) return t[idx];
				possible = filter(t, idx, count, possible);
			}
			return null;
		}
		int firstGuess(String[] t) {
			int[][] histo = histo(t);
			int max = 0;
			int maxScore = 0;
			for (int i = 0; i < t.length; i++) {
				int score = 0;
				for (int j = 0; j < t[i].length(); j++) {
					int index = t[i].charAt(j) - 'a';
					score += histo[j][index];
				}
				if (score > maxScore) {
					maxScore = score;
					max = i;
				}
			}
			return max;
		}
		int[][] histo(String[] t) {
			int[][] histo = new int[6][26];
			for (int i = 0; i < t.length; i++) {
				for (int j = 0; j < t[i].length(); j++) {
					int idx = t[i].charAt(j) - 'a';
					histo[j][idx]++; 
				}
			}
			return histo;
		}
		Set<Integer> filter(String[] t, int j, int k, Set<Integer> possible) {
			Set<Integer> res = new HashSet<>();
			for (Integer i : possible) {
				if (i == j) continue;
				int count = count(t[i], t[j]);
				if (count == k) {
					res.add(i);
				}
			}
			return res;
		}
		
	}
	static int count(String word1, String word2) {
		int count = 0;
		for (int i = 0; i < word1.length(); i++) {
			if (word1.charAt(i) == word2.charAt(i)) count++;
		}
		return count;
	}
	interface Problem extends BiFunction<String[], Master, String> {
		
	}
	
	static class Master {
		private String res;
		private Set<String> list;

		public Master(String[] list, String res) {
			this.list = new HashSet<>(Arrays.asList(list));
			this.res = res;
		}
		public int guess(String word) {
			if (!list.contains(word)) return -1;
			return count(word, res);
		}
	}
}
