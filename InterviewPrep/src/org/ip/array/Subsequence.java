package org.ip.array;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Subsequence {
	public static void main(String[] s) {
		testLongestIncreasing();
	}
	
	public static void testLongestCommon() {
		/*
		 * LCS for input Sequences "ABCDGH" and "AEDFHR" is "ADH" of length 3.
		 * LCS for input Sequences "AGGTAB" and "GXTXAYB" is "GTAB" of length 4.
		 */
		System.out.println(lcs(new String[] { "ABCDGH", "AEDFHR" }) + " ADH");
		System.out.println(lcs(new String[] { "AGGTAB", "GXTXAYB" }) + " GTAB");
	}
	
	public static void testLongestNonDecreasing() {
		System.out.println(longestNonDecreasing(new int[]{0,8,4,12,2,10,6,14,1,9}));
	}
	public static void testLongestIncreasing() {      //1,1,2,3, 1,3,3,4,2,4
		System.out.println(longestIncreasing2(new int[]{0,8,4,12,2,10,6,14,1,9}));
	}
	
	public static int longestNonDecreasing(int[] array) {
		int[] cache = new int[array.length];
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < i; j++) {
				if (array[j] > array[i]) continue;
				cache[i] = Math.max(cache[j]+1, cache[i]);
			}
			cache[i] = Math.max(cache[i], 1);
		}
		return cache[array.length-1];
	}
	public interface Sizer<T> {
		public int size(T t);
	}
	public static <T> int longestIncreasing(T[] array, Comparator<T> comparator, Sizer<T> sizer) {
		int[] cache = new int[array.length];
		int max = 0;
		for (int i = 0; i < cache.length; i++) {
			cache[i] = sizer.size(array[i]);
			for (int j = 0; j < i; j++) {
				if (comparator.compare(array[j], array[i]) >= 0) continue;
				cache[i] = Math.max(cache[i], cache[j] + sizer.size(array[i]));
			}
			max = Math.max(max, cache[i]);
		}
		return max;
	}
	
	public static int longestIncreasing2(int[] array) {
		//int[] p = new int[array.length];
		int[] m = new int[array.length + 1]; // [1-N]
		int length = 0;
		for (int i = 0; i < array.length; i++) {
			int l = 1;
			int r = length;
			while (l <= r) {
				int mid = l + (r - l) / 2;
				if (array[m[mid]] <= array[i]) {
					l = mid + 1;
				} else {
					r = mid - 1;
				}
			}
			int newL = l;
			//p[i] = m[newL - 1];
			m[newL] = i;
			if (newL > length) {
				length = newL;
			}
		}
		return length;
	}

	private static class PairLCS {
		public int length;
		public int location;

		public PairLCS(int length, int location) {
			this.length = length;
			this.location = location;
		}

		@Override
		public String toString() {
			return "Pair [length=" + length + ", location=" + location + "]";
		}
	}

	public static String getLongestSubsequenceFromCache(String strX, PairLCS[] aCache) {
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

	public static String lcs(String[] sequences) {
		List<Integer>[] locations = new ArrayList[26];
		for (int i = 0; i < 26; i++) {
			locations[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < sequences[1].length(); i++) {
			locations[Character.getNumericValue(sequences[1].charAt(i)) - 10].add(i);
		}
		PairLCS[] cache = new PairLCS[sequences[0].length()];
		for (int i = 0; i < sequences[0].length(); i++) {
			cache[i] = new PairLCS(0, -1);
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
