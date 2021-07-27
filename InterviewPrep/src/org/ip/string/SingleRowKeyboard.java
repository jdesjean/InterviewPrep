package org.ip.string;

/**
 * OA 2019
 * <a href="https://leetcode.com/problems/single-row-keyboard/">LC: 1165</a>
 */
public class SingleRowKeyboard {
	public static void main(String[] s) {
		System.out.println(new SingleRowKeyboard().solve("abcdefghijklmnopqrstuvwxyz", "cba"));
		System.out.println(new SingleRowKeyboard().solve("pqrstuvwxyzabcdefghijklmno", "leetcode"));
	}
	public int solve(String keyboard, String word) {
		if (word == null || keyboard == null) return 0;
		int[] cache = new int[26];
		for (int i = 0; i < keyboard.length(); i++) {
			cache[keyboard.charAt(i) - 'a'] = i;
		}
		int sum = 0;
		for (int i = 0, prev = 0; i < word.length(); i++) {
			int index = cache[word.charAt(i) - 'a'];
			sum += Math.abs(index - prev);
			prev = index;
		}
		return sum;
	}
}
