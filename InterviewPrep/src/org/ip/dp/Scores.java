package org.ip.dp;

// EPI 2018: 16.1
public class Scores {
	public static void main(String[] s) {
		System.out.println(new Scores().count(12, new int[] {2,3,7}));
	}
	// sum(count(score - options[i]) + count(score - options[i + 1]) + ...)  
	public int count(int score, int[] options) {
		int length = options[options.length - 1] + 1;
		int[] cache = new int[length];
		cache[0] = 1;
		for (int i = options[0]; i <= score; i++) {
			int index = i % length;
			for (int j = 0; j < options.length; j++) {
				int prev = (i - options[j]) % length;
				if (prev < 0) continue;
				if (cache[prev] > 0) cache[index]++;
			}
		}
		return cache[score % length];
	}
}
