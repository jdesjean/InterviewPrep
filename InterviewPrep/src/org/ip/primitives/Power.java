package org.ip.primitives;

// EPI 5.7
public class Power {
	public static void main(String[] s) {
		Power power = new Power();
		System.out.println(power.pow(2,4));
		System.out.println(power.pow(2,3));
		System.out.println(power.pow(3,3));
	}
	public double pow(double a, int b) {
		double result = 1;
		if (b < 0) {
			a = 1 / a;
			b = -b;
		}
		while (b > 0) {
			if ((b & 1) != 0) {
				result *= a; 
			}
			a *= a;
			b >>>= 1;
		}
		return result;
	}
}
