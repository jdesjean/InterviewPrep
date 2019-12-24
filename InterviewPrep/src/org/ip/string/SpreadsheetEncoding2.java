package org.ip.string;

// EPI 2018: 6.3
public class SpreadsheetEncoding2 {
	public static void main(String[] s) {
		SpreadsheetEncoding2 encoding = new SpreadsheetEncoding2();
		System.out.println(encoding.encode("AA"));
		System.out.println(encoding.encode("D"));
		System.out.println(encoding.encode("ZZ"));
	}
	public int encode(String s) {
		int value = 0;
		for (int i = 0; i < s.length(); i++) {
			value *= 26;
			value += ((s.charAt(i) - 'A') + 1);
		}
		return value;
	}
}
