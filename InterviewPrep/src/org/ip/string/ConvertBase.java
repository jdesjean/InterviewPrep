package org.ip.string;

// EPI: 7.2
public class ConvertBase {
	public static void main(String[] s) {
		ConvertBase convert = new ConvertBase();
		System.out.println(convert.convert("100", 10, 2));
		System.out.println(convert.convert("1111", 2, 10));
		System.out.println(convert.convert("15", 10, 16));
		System.out.println(convert.convert("F", 16, 10));
	}
	public String convert(String s, int b1, int b2) {
		int b10 = 0;
		for (int i = 0; i < s.length(); i++) {
			b10 *= b1;
			b10 += Character.digit(s.charAt(i), b1);
		}
		StringBuilder sb = new StringBuilder();
		while (b10 > 0) {
			sb.append(Character.forDigit(b10 % b2, b2));
			b10 /= b2;
		}
		return sb.reverse().toString();
	}
}
