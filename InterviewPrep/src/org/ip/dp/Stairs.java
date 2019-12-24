package org.ip.dp;

// EPI 2018: 16.10
public class Stairs {
	public static void main(String[] s) {
		System.out.println(new Stairs().solve(4,2));
	}
	public int solve(int n, int k) {
		int[] cache = new int[k];
		cache[0] = 1;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j < k; j++) {
				if (i - j < 0) break;
				cache[i % k] += cache[(i - j) % k];
			}
		}
		return cache[n % k];
	}
}
