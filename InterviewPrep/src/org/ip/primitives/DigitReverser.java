package org.ip.primitives;

// EPI 5.8
public class DigitReverser {
	public static void main(String[] s) {
		DigitReverser reverser = new DigitReverser();
		System.out.println(reverser.reverse(-413));
		System.out.println(reverser.reverse(42));
	}
	public int reverse(int value) {
		int reverse = 0;
		boolean sign = value >= 0;
		value = Math.abs(value);
		while (value > 0) {
			int digit = value % 10;
			value /= 10;
			reverse = 10 * reverse + digit;
		}
		return sign ? reverse : -reverse;
	}
}
