package org.ip;

import org.ip.array.ArrayUtils;

public class Levenshtein {
	public static void main(String[] s) {
		Calculator[] calculators = new Calculator[]{new RecursiveCalculator(), new DPCalculator()};
		String[][] strings = new String[][]{{"kitten","sitting"}};
		for (Calculator calculator : calculators) {
			for (int i = 0; i < strings.length; i++) {
				System.out.println(calculator.distance(strings[i][0],strings[i][1]));
			}
		}
		//System.out.println(distance("kitten","sitting"));
		//System.out.println(distance("aaa","bbb"));
		//System.out.println(distance("aaa",""));
	}
	public interface Calculator {
		public int distance(String word1, String word2);
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
					cache[j] = equal ? preij : 1 + ArrayUtils.min(prei, preij, prej);
					preij = prei;
				}
			}
			return cache[word2.length()];
		}
		
	}
	public static class RecursiveCalculator implements Calculator {
		@Override
		public int distance(String word1, String word2) {
			return distance(word1,word1.length()-1,word2,word2.length()-1);
		}
		private static int distance(String word1, int n1, String word2, int n2) {
			if (n1 < 0 || n2 < 0) return Math.max(n1, n2);
			
			if (word1.charAt(n1) == word2.charAt(n2)) return distance(word1,n1-1,word2,n2-2);
			
			int nRemove = distance(word1,n1-1,word2,n2);
			int nAdd = distance(word1,n1,word2,n2-1);
			int nEdit = distance(word1,n1-1,word2,n2-1);
			return ArrayUtils.min(nRemove, nAdd, nEdit) + 1;
		}
	}
	
	
}
