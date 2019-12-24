package org.ip.primitives;

//EPI 2018: 4.6
public class Substracter {
	public static void main(String[] ss) {
		Substracter s = new Substracter();
		System.out.println(s.substract(1, 2));
		System.out.println(s.substract(10, 5));
		System.out.println(s.substract(10, -5));
	}
	Adder adder = new Adder();
	public int substract(int a, int b) {
		return adder.add(a, flip(b));
	}
	public int flip(int a) {
		return ~decrement(a);
	}
	public int decrement(int a) {
		int bits = 1;
		int b = a;
		while ((b & 1) == 0) { // Create mask of ones equal to the number of zeros + 1 at LSBs of a
			bits <<= 1;
			bits |= 1;
			b >>= 1;
		}
		return a ^ bits;
	}
}
