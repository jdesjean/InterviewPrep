package org.ip.primitives;

// EPI: 5.5
public class Multiplication {
	public static void main(String[] s) {
		Multiplication mult = new Multiplication();
		System.out.println(mult.multiply(4, 3));
		System.out.println(mult.multiply(4, 5));
		System.out.println(mult.multiply(5, 6));
		System.out.println(mult.multiply(7, 9));
		System.out.println(mult.multiply(3,-4));
		System.out.println(mult.multiply(-3,-4));
	}
	public int multiply(int a, int b) {
		//assign, bit, equal
		if (b < a) return multiply(b, a);
		int value = 0;
		Addition addition = new Addition();
		while (a != 0) {
			if ((a & 1) != 0) {
				value = addition.add(value, b);
			}
			b <<= 1;
			a >>>= 1;
		}
		return value;
	}
}
