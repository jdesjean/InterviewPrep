package org.ip.dp;

// EPI 2018: 16.2
public class Distance {
	public static void main(String[] s) {
		System.out.println(new Distance().levenshtein("saturday", "sunday"));
		System.out.println(new Distance().levenshtein("carthorse", "orchestra"));
	}
	// insertion, deletion, substitution
	public int levenshtein(String s1, String s2) {
		//insert s2-2
		//deletion s1-2
		//substitution s1-2,s2-2
		int[][] cache = new int[s1.length() + 1][s2.length() + 1];
		for (int i = 0; i < cache.length; i++) {
			cache[i][0] = i;
		}
		for (int i = 0; i < cache[0].length; i++) {
			cache[0][i] = i;
		}
		for (int i = 1; i <= s1.length(); i++) {
			for (int j = 1; j <= s2.length(); j++) {
				if (s1.charAt(i - 1) == s2.charAt(j - 1)) cache[i][j] = cache[i - 1][j - 1];
				else {
					int insert = cache[i-1][j];
					int delete = cache[i][j-1];
					int sub = cache[i-1][j-1];
					cache[i][j] = 1 + min(insert, delete, sub);
				}
			}
		}
		return cache[s1.length()][s2.length()];
	}
	public int min(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}
}
