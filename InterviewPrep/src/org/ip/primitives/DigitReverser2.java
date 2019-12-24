package org.ip.primitives;

//EPI 2018: 4.8
public class DigitReverser2 {
	public static void main(String[] s) {
		DigitReverser2 reverser = new DigitReverser2();
		System.out.println(reverser.reverse(-413));
		System.out.println(reverser.reverse(42));
	}
	public int reverse(int value) {
		int sign = value < 0 ? -1 : 1;
		value = Math.abs(value);
		int result = 0;
		while (value != 0) {
			result *= 10;
			result += value % 10;
			value /= 10;
		}
		return sign * result;
	}
}
