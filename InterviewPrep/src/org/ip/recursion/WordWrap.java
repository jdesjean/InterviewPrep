package org.ip.recursion;

//EPI: 17.11
public class WordWrap {
	public static void main(String[] s) {
		WordWrapMinimizer[] minimizers = new WordWrapMinimizer[]{new RecursiveWordWrapMinimizer(), new DPWordWrapMinimizer()};
		String[] text = "Geeks for Geeks presents word wrap problem".split(" ");
		for (WordWrapMinimizer minimizer : minimizers) {
			System.out.println(minimizer.minimize(text, 15));
		}
	}
	public interface WordWrapMinimizer {
		public int minimize(String[] words, int width);
	}
	public static class RecursiveWordWrapMinimizer implements WordWrapMinimizer {

		@Override
		public int minimize(String[] words, int width) {
			return min(words, width, 0);
		}
		private static int min(String[] words, int width, int left) {
			if (left >= words.length) return 0;
			int min = Integer.MAX_VALUE;
			for (int i = left, length = width-words[i].length(); length >= 0 && i < words.length; i++, length-= i < words.length ? (words[i].length()+1) : 0) {
				int cost = (int)Math.pow(length, 2);
				min = Math.min(min, cost+min(words,width,i+1));
			}
			return min;
		}
	}
	public static class DPWordWrapMinimizer implements WordWrapMinimizer {

		@Override
		public int minimize(String[] words, int width) {
			int[] cache = new int[words.length];
			for (int j = words.length-1; j >= 0; j--) {
				cache[j] = Integer.MAX_VALUE;
				for (int i = j, length = width-words[i].length(); length >= 0 && i < words.length; i++, length -= i < words.length ? (words[i].length()+1) : 0) {
					int cost = (int)Math.pow(length, 2);
					cache[j] = Math.min(cache[j], i < words.length - 1 ? cost+cache[i+1] : cost);
				}
				
			}
			return cache[0];
		}
		
	}
}
