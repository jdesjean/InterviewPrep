package org.ip.honor;

// EPI 2018: 24.1
public class GCD {
	public static void main(String[] s) {
		GCD gcd = new GCD();
		System.out.println(gcd.solve(36, 24));
		System.out.println(gcd.solve(40, 25));
		System.out.println(gcd.solve(6, 5));
	}
	public int solve(int a, int b) {
		if (a > b) return solve(b, a);
		if (a == 0) return b;
		if ((a & 1) == 0 && (b & 1) == 0) return solve(a >> 1, b >> 1) << 1;
		if ((a & 1) == 0) return solve(a >> 1, b);
		if ((b & 1) == 0) return solve(a, b >> 1);
		return solve(a, b - a);
	}
}
