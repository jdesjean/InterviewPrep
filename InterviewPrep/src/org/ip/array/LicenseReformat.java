package org.ip.array;

/**
 * OA 2019
 * <a href="https://leetcode.com/problems/license-key-formatting/">LC: 482</a>
 */
public class LicenseReformat {
	public static void main(String[] s) {
		System.out.println(new LicenseReformat().solve("5F3Z-2e-9-w", 4));
	}
	public String solve(String s, int k) {
		if (s == null || s.length() == 0) return s;
		int count = count(s);
		int mod = count % k;
		int split = count / k;
		StringBuilder sb = new StringBuilder(split * k + (mod != 0 ? split : Math.max(split - 1, 0)));
		for (int i = 0, dash = 0; i < s.length(); i++) {
			if (s.charAt(i) == '-') continue;
			if (sb.length() > 0 && (sb.length() - mod - dash) % k == 0) {
				sb.append('-');
				dash++;
			}
			sb.append(Character.toUpperCase(s.charAt(i)));
		}
		return sb.toString();
	}
	private int count(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '-') continue;
			count++;
		}
		return count;
	}
}
