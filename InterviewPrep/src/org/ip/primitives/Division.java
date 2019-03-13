package org.ip.primitives;

// EPI 5.6
public class Division {
	public static void main(String[] s) {
		Division division = new Division();
		System.out.println(division.divide(8,4));
		System.out.println(division.divide(8,-2));
		System.out.println(division.divide(9,3));
		System.out.println(division.divide(8,3));
		System.out.println(division.divide(Integer.MAX_VALUE,4));
		System.out.println(division.divide(Integer.MAX_VALUE,Integer.MAX_VALUE));
	}
	Substraction substraction = new Substraction();
	Addition addition = new Addition();
	public int divide(int a, int b) {
		if (a < b) return 0;
		boolean isDivisionNegative = sign(a) != sign(b);
		a = abs(a);
		b = abs(b);
		int ap = 1 << 30;
		long bp = (long)b << 30;
		int c = 0;
		while (a >= b) {
			while (bp > a) {
				ap >>>= 1;
				bp >>>= 1;
			}
			c |= ap;
			a = substraction.substract(a, (int)bp);
		}
		return isDivisionNegative ? substraction.negate(c) : c;
	}
	public int abs(int a) {
		if (!sign(a)) {
			return substraction.negate(a);
		} else {
			return a;
		}
	}
	public boolean sign(int a) {
		return (a & (1 << 31)) == 0;
	}
}
