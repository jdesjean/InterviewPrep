package org.ip.string;

import org.ip.array.Utils;

// EPI 2018: 6.12
public class RLE2 {
	public static void main(String[] s) {
		RLE2 rle = new RLE2();
		String start = "aaaabcccaa";
		Utils.println(start);
		char[] current = rle.encode(start);
		Utils.println(current, current.length);
		char[] output = rle.decode(current);
		Utils.println(output, output.length);
		System.out.println();
	}
	public char[] encode(String s) {
		StringBuilder sb = new StringBuilder();
		int length = 1;
		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == s.charAt(i - 1)) {
				length++;
			} else {
				sb.append(length);
				sb.append(s.charAt(i-1));
				length = 1;
			}
		}
		sb.append(length);
		sb.append(s.charAt(s.length()-1));
		return sb.toString().toCharArray();
	}
	public char[] decode(char[] a) {
		StringBuilder sb = new StringBuilder();
		int value = 0;
		for (int i = 0; i < a.length; i++) {
			if (Character.isDigit(a[i])) {
				value *= 10;
				value += Character.digit(a[i], 10);
			} else {
				for (int j = 0; j < value; j++) {
					sb.append(a[i]);
				}
				value = 0;
			}
		}
		return sb.toString().toCharArray();
	}
}
