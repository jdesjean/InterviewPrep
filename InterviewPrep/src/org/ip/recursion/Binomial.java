package org.ip.recursion;

// EPI: 17.4
public class Binomial {
	public static void main(String[] s) {
		System.out.println(new DPSolve().solve(5, 3) + "=10");
		System.out.println(new DP2Solve().solve(5, 3) + "=10");
		System.out.println(new RecursiveSolve().solve(5, 3) + "=10");
	}
	public static class DPSolve implements Solve {
		public int solve(int n, int k) {
			int[][] cache = new int[k + 1][n + 1];
			for (int i = 0; i < cache[0].length; i++) {
				cache[0][i] = 1;
			}
			for (int kk = 1; kk <= k; kk++) {
				for (int nn = 1; nn <= n; nn++) {
					cache[kk][nn] = cache[kk - 1][nn - 1] + cache[kk][nn - 1];
				}
			}
			return cache[k][n];
		}
	}
	public static class DP2Solve implements Solve {
		public int solve(int n, int k) {
			int[] cache = new int[n + 1];
			for (int i = 0; i < cache.length; i++) {
				cache[i] = 1;
			}
			for (int kk = 1; kk <= k; kk++) {
				int prev = 0;
				for (int nn = 1; nn <= n; nn++) {
					int tmp = cache[nn];
					cache[nn] = prev + cache[nn - 1];
					prev = tmp;
				}
				cache[0] = 0;
			}
			return cache[n];
		}
	}
	public static class RecursiveSolve implements Solve {
		public int solve(int n, int k) {
			if (k == 0) {
				return 1;
			} else if (n == 0) {
				return 0;
			}
			return solve(n - 1, k - 1) + solve(n - 1, k);
		}
	}
	public interface Solve {
		public int solve(int n, int k);
	}
	
}
