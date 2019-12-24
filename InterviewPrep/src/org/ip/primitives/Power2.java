package org.ip.primitives;

// EPI 2018: 4.7
public class Power2 {
	public static void main(String[] s) {
		Power2 power = new Power2();
		System.out.println(power.pow(2,4));
		System.out.println(power.pow(2,3));
		System.out.println(power.pow(3,3));
	}
	public double pow(double a, int b) {
		if (b < 0) {
			a = 1 / a;
			b = -b;
		}
		double result = 1;
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
