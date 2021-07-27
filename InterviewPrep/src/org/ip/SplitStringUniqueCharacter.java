package org.ip;

/**
 * <a href="https://leetcode.com/discuss/interview-question/553399/">OA 2019</a>
 * <a href="https://leetcode.com/problems/number-of-good-ways-to-split-a-string/">LC: 1525</a>
 */
public class SplitStringUniqueCharacter {
	public static void main(String[] s) {
		tc1();
		tc2();
		tc3();
	}
	public static void tc1() {
		System.out.println(new SplitStringUniqueCharacter().solve("aaaa"));
	}
	public static void tc2() {
		System.out.println(new SplitStringUniqueCharacter().solve("bac"));
	}
	public static void tc3() {
		System.out.println(new SplitStringUniqueCharacter().solve("ababa"));
	}
	public int solve(String s) {
		int[] forward = new int[26];
		int f = 0;
		for (int i = 0; i < s.length(); i++) {
			if (forward[s.charAt(i) - 'a']++ == 0) {
				f++;
			}
		}
		int[] backward = new int[26];
		int b = 0;
		int count = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			if (backward[s.charAt(i) - 'a']++ == 0) b++;
			if (--forward[s.charAt(i) - 'a'] == 0) f--;
			if (f == b) count++;
		}
		return count;
	}
	
}
