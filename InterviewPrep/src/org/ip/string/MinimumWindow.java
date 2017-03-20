package org.ip.string;

public class MinimumWindow {
	public static void main(String[] s) {
		System.out.println(solve("AYZABOBECODXBANC","ABC"));
	}
	private static int[] getBuckets(String characters) {
		int[] buckets = new int[26];
		for (int i = 0; i < characters.length(); i++) {
			buckets[characters.charAt(i) - 'A']++;
		}
		return buckets;
	}
	
	public static String solve(String string, String characters) {
		int[] text = new int[26];
		int[] buckets = getBuckets(characters);
		int length = Integer.MAX_VALUE;
		int start = -1;
		int count = characters.length();
		for (int i = 0, k = 0; (k < string.length() && count == 0) || i < string.length();) {
			if (count > 0 && i < string.length()) {
				if (text[string.charAt(i) - 'A']++ < buckets[string.charAt(i) - 'A']) {
					count--;
				}
				i++;
			} else {
				if (count <= 0) {
					if (i - k < length) {
						length = i - k;
						start = k;
					}
				}
				if (--text[string.charAt(k) - 'A'] < buckets[string.charAt(k) - 'A']) {
					count++;
				}
				k++;
			}
		}
		return start >= 0 ? string.substring(start, start+length) : "";
	}
}
