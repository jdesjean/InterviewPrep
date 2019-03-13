package org.ip.primitives;

public class Substraction {
	Addition addition = new Addition();
	public int negate(int a) {
		return addition.add(~a, 1);
	}
	public int substract(int a, int b) {
		return addition.add(a, negate(b));
	}
}
