package org.ip.array;

public class LCS {
	public static void main(String[] s) {
		testLongestCommon();
	}
	
	public static void testLongestCommon() {
		/*
		 * LCS for input Sequences "ABCDGH" and "AEDFHR" is "ADH" of length 3.
		 * LCS for input Sequences "AGGTAB" and "GXTXAYB" is "GTAB" of length 4.
		 */
		System.out.println(lcs_recursive(new String[] { "ABCDGH", "AEDFHR" }) + " ADH");
		System.out.println(lcs_recursive(new String[] { "AGGTAB", "GXTXAYB" }) + " GTAB");
		System.out.println(lcs(new String[] { "ABCDGH", "AEDFHR" }) + " ADH");
		System.out.println(lcs(new String[] { "AGGTAB", "GXTXAYB" }) + " GTAB");
	}
	private static class Pair {
		int value;
		int index;
		public Pair(int value, int index) {this.value = value; this.index=index;}
	}
	public static String lcs(String[] s) {
		Pair[] cache = new Pair[s[0].length()];
		int index = s[1].lastIndexOf(s[0].charAt(s[0].length() - 1));
		for (int i = 0; i < cache.length; i++) {
			cache[i] = new Pair(0, -1);
		}
		if (index >= 0) {
			cache[s[0].length() - 1] = new Pair(1, index);
		} else {
			cache[s[0].length() - 1] = new Pair(0, -1);
		}
		for (int i = s[0].length() - 2; i >= 0; i--) {
			char c = s[0].charAt(i);
			index = s[1].length();
			do {
				index = s[1].lastIndexOf(c, index - 1);
				int max = cache[i].value;
				int maxindex = cache[i].index;
				if (index >= 0) {
					for (int k = i + 1; k < cache.length; k++) {
						if (cache[k].value > max && index < cache[k].index) {
							max = cache[k].value;
							maxindex = cache[k].index;
						}
					}
					cache[i] = new Pair(max + 1, index);
				} else {
					cache[i] = new Pair(max, maxindex);
				}
			} while (index >= 0);
		}
		return lcs(s[0], cache);
	}
	private static String lcs(String s, Pair[] cache) {
		if (cache.length == 0) return "";
		int max = 0;
		for (int i = 1; i < cache.length; i++) {
			if (cache[i].value > cache[max].value) {
				max = i;
			}
		}
		char[] buffer = new char[cache[max].value];
		for (int i = max, j = 0; j < buffer.length; j++) {
			buffer[j] = s.charAt(i);
			for (int k = max + 1; k < cache.length; k++) {
				if (cache[k].value == cache[i].value - 1 && cache[i].index < cache[k].index) {
					i = k;
					break;
				}
			}
		}
		return new String(buffer);
	}
	public static String lcs_recursive(String[] s) {
		int max = 0;
		String value = null;
		char[] buffer = new char[s[0].length()];
		for (int i = 0; i < s[0].length(); i++) {
			int index = s[1].indexOf(s[0].charAt(i));
			if (index >= 0) {
				int size = lcs(buffer, s[0], s[1], i, index, 0);
				if (size > max) {
					max = size;
					value = new String(buffer, 0, size);
				}
			}
		}
		return value;
	}
	public static int lcs(char[] buffer, String a, String b, int i, int j, int w) {
		if (i >= a.length()) return 0;
		else if (j >= b.length()) return 0;
		int index = b.indexOf(a.charAt(i), j);
		if (index >= 0) {
			buffer[w] = a.charAt(i);
			return lcs(buffer, a, b, i + 1, index + 1, w + 1) + 1;
		} else {
			return lcs(buffer, a, b, i + 1, j, w);
		}
	}
}
