package org.ip.recursion;

//EPI: 17.11
public class WordWrap2 {
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
			return minimize(words, width, 0);
		}
		public int minimize(String[] words, int width, int l) {
			if (l >= words.length) return 0;
			int min = Integer.MAX_VALUE;
			for (int j = l, w = 0; w <= width && j < words.length; j++, w++) {
				w += words[j].length();
				int _min = (int)Math.pow(width - w, 2) + minimize(words, width, j + 1);
				min = Math.min(min, _min);
			}
			return min;
		}
	}
	public static class DPWordWrapMinimizer implements WordWrapMinimizer {
		@Override
		public int minimize(String[] words, int width) {
			int[] cache = new int[words.length];
			cache[words.length - 1] = (int)Math.pow(width - words[words.length-1].length(), 2);
			for (int i = words.length - 2; i >= 0; i--) {
				cache[i] = Integer.MAX_VALUE;
				int w = 0;
				for (int j = i; j < words.length; j++, w++) {
					w += words[j].length();
					int c = j < words.length - 1 ? cache[j + 1] : 0; 
					cache[i] = Math.min((int)Math.pow(width - w, 2) + c, cache[i]);
				}
			}
			return cache[0];
		}
	}
}
