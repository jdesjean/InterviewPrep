package org.ip.dp;

// EPI 2018: 16.4
public class BinomialCoefficient {
	public static void main(String[] s) {
		System.out.println(new BinomialCoefficient().solve(5,2));
		System.out.println(new BinomialCoefficient().solve(5,3));
	}
	// (n k) = (n-1 k) + (n-1 k-1)
	// (2 2) = 1
	// (n 1) = n
	public int solve(int n, int k) {
		if (k == 0) return 1;
		if (k == 1) return n;
		else if (n == k && n == 2) return 1;
		int[][] cache = new int[k][n + 1];
		for (int i = 0; i <= n; i++) {
			cache[0][i] = i;
		}
		for (int l = 1; l < k; l++) {
			for (int c = 1; c <= n; c++) {
				cache[l][c] = cache[l-1][c-1] + cache[l][c-1];
			}
		}
		return cache[k-1][n];
	}
}
