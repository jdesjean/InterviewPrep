package org.ip.dp;

// EPI 2018: 16.12
public class NonDecreasingSubsequence {
	public static void main(String[] s) {
		System.out.println(new NonDecreasingSubsequence().solve(new int[] {0,8,3,12,2,10,6,14,9}));
	}
	public int solve(int[] a) {
		int[] cache = new int[a.length];
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			cache[i] = 1;
			for (int j = 0; j < i; j++) {
				if (a[i] < a[j]) continue;
				cache[i] = Math.max(cache[i], cache[j] + 1);
			}
			max = Math.max(max, cache[i]);
		}
		return max;
	}
	
}
