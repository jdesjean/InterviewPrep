package org.ip.string;

// EPI: 7.3
public class SpreadsheetEncoding {
	public static void main(String[] s) {
		SpreadsheetEncoding encoding = new SpreadsheetEncoding();
		System.out.println(encoding.encode("AA"));
		System.out.println(encoding.encode("D"));
		System.out.println(encoding.encode("ZZ"));
	}
	public int encode(String s) {
		int result = 0;
		for (int i = 0; i < s.length(); i++) {
			result *= 26;
			result += s.charAt(i) - 'A' + 1;
		}
		return result;
	}
}
