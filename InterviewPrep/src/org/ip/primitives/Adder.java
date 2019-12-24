package org.ip.primitives;

// EPI 2018: 4.5
public class Adder {
	public static void main(String[] s) {
		Adder a = new Adder();
		System.out.println(a.add(1,1));
		System.out.println(a.add(5,5));
		System.out.println(a.add(5,6));
		System.out.println(a.add(10, 11));
	}
	public int add(int a, int b) {
		while (b != 0) {
			int xor = a ^ b; 
			b = (a & b) << 1;
			a = xor;
		}
		return a;
	}
}
