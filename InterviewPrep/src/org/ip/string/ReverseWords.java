package org.ip.string;

import org.ip.array.Utils;

// EPI 2018: 6.6
public class ReverseWords {
	public static void main(String[] s) {
		char[] a = "Alice likes Bob".toCharArray();
		new ReverseWords().reverseWords(a);
		Utils.println(a);
	}
	public void reverseWords(char[] a) {
		reverse(a, 0, a.length - 1);
		reverse2(a);
	}
	public void reverse(char[] a, int l, int r) {
		for (;l < r; l++, r--) {
			Utils.swap(a, l, r);
		}
	}
	public void reverse2(char[] a) {
		for (int l = 0, r = 0; l < a.length; r++) {
			if (r >= a.length || a[r] == ' ') {
				reverse(a, l, r - 1);
				l = r + 1;
			}
		}
	}
}
