package org.ip.primitives;

// EPI 2018: 4.5
public class Multiplier {
	public static void main(String[] s) {
		Multiplier m = new Multiplier();
		System.out.println(m.multiply(8, 4));
		System.out.println(m.multiply(9, 9));
		System.out.println(m.multiply(12, 12));
		System.out.println(m.multiply(5, 6));
	}
	Adder adder = new Adder();
	public int multiply(int a, int b) {
		if (b > a) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		int result = 0;
		while (b != 0) {
			if ((b & 1) == 1) {
				result = adder.add(result, a);
			}
			a <<= 1;
			b >>= 1;
		}
		return result;
	}
}
