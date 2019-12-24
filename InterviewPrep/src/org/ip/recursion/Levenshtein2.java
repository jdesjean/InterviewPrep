package org.ip.recursion;

import org.ip.array.Utils;
import org.ip.recursion.Levenshtein.Calculator;
import org.ip.recursion.Levenshtein.DPCalculator;
import org.ip.recursion.Levenshtein.RecursiveCalculator;

// EPI: 17.2
public class Levenshtein2 {
	public static void main(String[] s) {
		Calculator[] calculators = new Calculator[]{new RecursiveCalculator(), new DPCalculator()};
		String[][] strings = new String[][]{{"kitten","sitting"},  {"aaa","bbb"}, {"aaa",""}, {"abc","aba"}};
		for (Calculator calculator : calculators) {
			for (int i = 0; i < strings.length; i++) {
				System.out.println(calculator.distance(strings[i][0],strings[i][1]));
			}
			System.out.println("*");
		}
	}
	public interface Calculator {
		public int distance(String word1, String word2);
	}
	public static class RecursiveCalculator implements Calculator {

		@Override
		public int distance(String word1, String word2) {
			return distance(word1, word2, 0, 0);
		}
		public int distance(String word1, String word2, int i1, int i2) {
			if (i1 >= word1.length()) {
				return word2.length() - i2;
			} else if (i2 >= word2.length()) {
				return word1.length() - i1;
			}
			if (word1.charAt(i1) == word2.charAt(i2)) {
				return distance(word1, word2, i1 + 1, i2 + 1);
			}
			int min = Integer.MAX_VALUE;
			int replace = distance(word1, word2, i1 + 1, i2 + 1);
			int add = distance(word1, word2, i1, i2 + 1);
			int remove = distance(word1, word2, i1 + 1, i2);
			return Math.min(Math.min(replace, add), remove) + 1;
		}
		
	}
	public static class DPCalculator implements Calculator {

		@Override
		public int distance(String word1, String word2) {
			if (word1.length() < word2.length()) {
				String tmp = word1;
				word1 = word2;
				word2 = tmp;
			}
			int[] cache = new int[word2.length()+1];
			for (int i = 1; i <= word1.length(); i++) {
				int preij = cache[0];
				cache[0] = i;
				for (int j = 1; j <= word2.length(); j++) {
					int prei = cache[j];
					int prej = cache[j-1];
					boolean equal = word1.charAt(i-1) == word2.charAt(j-1); 
					cache[j] = equal ? preij : 1 + Utils.min(prei, preij, prej);
					preij = prei; 
				}
			}
			return cache[word2.length()];
		}
		
	}
}
