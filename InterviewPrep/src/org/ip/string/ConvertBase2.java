package org.ip.string;

// EPI 2018: 6.2
public class ConvertBase2 {
	public static void main(String[] s) {
		ConvertBase2 convert = new ConvertBase2();
		System.out.println(convert.convert("100", 10, 2));
		System.out.println(convert.convert("1111", 2, 10));
		System.out.println(convert.convert("15", 10, 16));
		System.out.println(convert.convert("F", 16, 10));
	}
	public String convert(String s, int b1, int b2) {
		int value = toBase10(s,b1);
		return toBase(value, b2);
	}
	public int toBase10(String s, int b1) {
		int value = 0;
		for (int i = 0; i < s.length(); i++) {
			value *= b1;
			value += digitForCharacter(s.charAt(i));
		}
		return value;
	}
	public String toBase(int value, int b2) {
		StringBuilder sb = new StringBuilder();
		while (value > 0) {
			int n = value % b2;
			sb.append(characterForDigit(n));
			value /= b2;
		}
		return sb.reverse().toString();
	}
	public int digitForCharacter(char c) {
		if (c >= 'A') {
			return (10 + (c - 'A'));
		} else {
			return (c - '0');
		}
	}
	public char characterForDigit(int n) {
		if (n < 10) {
			return (char)(n + '0');
		} else {
			return (char)('F' - (15 - n));
		}
	}
}
