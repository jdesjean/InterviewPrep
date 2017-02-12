package org.ip;

import java.util.ArrayList;
import java.util.List;

public class Subsequence {
	public static void main(String[] s) {
		/*
		 * LCS for input Sequences "ABCDGH" and "AEDFHR" is "ADH" of length 3.
		 * LCS for input Sequences "AGGTAB" and "GXTXAYB" is "GTAB" of length 4.
		 */
		System.out.println(solve(new String[] { "ABCDGH", "AEDFHR" }) + " ADH");
		System.out.println(solve(new String[] { "AGGTAB", "GXTXAYB" }) + " GTAB");
	}

	private static class Pair {
		public int length;
		public int location;

		public Pair(int length, int location) {
			this.length = length;
			this.location = location;
		}

		@Override
		public String toString() {
			return "Pair [length=" + length + ", location=" + location + "]";
		}
	}

	public static String getLongestSubsequenceFromCache(String strX, Pair[] aCache) {
		int max = 0;
		int maxIndex = -1;
		for (int i = 0; i < aCache.length; i++) {
			if (aCache[i].length < max) continue;
			max = aCache[i].length;
			maxIndex = i;
		}
		char[] aChar = new char[max];
		for (int i = maxIndex; i >= 0; i--) {
			if (aCache[i].length == max) {
				aChar[max - 1] = strX.charAt(i);
				max--;
			}
		}
		return new String(aChar);
	}

	public static String solve(String[] sequences) {
		List<Integer>[] locations = new ArrayList[26];
		for (int i = 0; i < 26; i++) {
			locations[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < sequences[1].length(); i++) {
			locations[Character.getNumericValue(sequences[1].charAt(i)) - 10].add(i);
		}
		Pair[] cache = new Pair[sequences[0].length()];
		for (int i = 0; i < sequences[0].length(); i++) {
			cache[i] = new Pair(0, -1);
			int locationIndex = Character.getNumericValue(sequences[0].charAt(i)) - 10;
			for (int j = 0; j <= i; j++) {
				for (Integer location : locations[locationIndex]) {
					if (location <= cache[j].location || cache[i].length > cache[j].length+1) continue;
					cache[i].length = cache[j].length+1;
					cache[i].location = location;
					break;
				}
			}
		}
		return getLongestSubsequenceFromCache(sequences[0],cache);
	}
}
