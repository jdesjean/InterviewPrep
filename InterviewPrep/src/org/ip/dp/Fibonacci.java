package org.ip.dp;

// EPI  2018: 16.0
public class Fibonacci {
	public static void main(String[] s) {
		System.out.println(new Fibonacci().solve(5));
	}
	public int solve(int n) {
		if (n <= 1) return n;
		int[] cache = new int[] {0, 1};
		for (int i = 2; i <= n; i++) {
			cache[i % 2] = cache[(i - 1) % 2] + cache[(i - 2) % 2];
		}
		return cache[n % 2];
	}
}
