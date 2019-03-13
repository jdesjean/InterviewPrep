package org.ip.primitives;

public class Addition {
	public int add(int a, int b) {
		if (b < a) return add(b, a);
		while (a != 0) {
			int carry = (a & b) << 1;
			b = a ^ b;
			a = carry;
		}
		return b;
	}
}
