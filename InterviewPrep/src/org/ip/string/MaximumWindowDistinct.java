package org.ip.string;

public class MaximumWindowDistinct {
	public static void main(String[] s) {
		System.out.println(solve("eceba"));
		System.out.println(solve("aaaaeaaaceba"));
		System.out.println(solve("aaaaeaaaeeceba"));
		System.out.println(solve("ecebaaaaaa"));
	}
	public static String solve(String string) {
		int[] cache = new int[26];
		int count = 0;
		int start = -1;
		int length = 0;
		for (int i = 0, j = 0; i < string.length(); ) {
			if (count <= 2 && i < string.length()) {
				if (cache[string.charAt(i) - 'a']++ == 0) count++;
				if (i - j > length) {
					start = j;
					length = i - j;
				}
				i++;
			} else {
				if (--cache[string.charAt(j) - 'a'] == 0) count--;
				j++;
			}
		}
		return start != -1 ? string.substring(start, start+length) : "";
	}
}
