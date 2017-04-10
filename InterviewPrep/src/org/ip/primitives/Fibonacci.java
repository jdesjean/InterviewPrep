package org.ip.primitives;

public class Fibonacci {
	public static void main(String[] s) {
		for (int i = 0; i <= 5; i++) {
			System.out.println(solve(i));
		}
	}
	public static int solve(int n) {
		if (n == 0) return n;
		if (n == 1 || n == 2) return 1;
		int p1 = 1;
		int p2 = 1;
		int sum = p2;
		for (int i = 3; i <= n; i++) {
			sum = p1+p2;
			p1 = p2;
			p2 = sum;
		}
		return p2;
	}
}
