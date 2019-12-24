package org.ip.dp;

import java.util.Arrays;

// EPI 2018: 16.12
// nlog(n)
public class NonDecreasingSubsequence2 {
	public static void main(String[] s) {
		System.out.println(new NonDecreasingSubsequence2().solve(new int[] {0,8,3,12,2,10,6,14,9}));
	}
	public int solve(int[] a) {
		int[] cache = new int[a.length + 1];
		int max = 0;
		Arrays.fill(cache, -1);
		for (int i = 0; i < a.length; i++) {
			int found = 0;
			for (int left = 1, right = max; left <= right; ) {
				int mid = left + (right - left) / 2;
				if (a[cache[mid]] <= a[i]) {
					left = mid + 1;
					found = mid;
				} else {
					right = mid - 1;
				}
			}
			if (cache[found + 1] == -1 || a[i] <= a[cache[found + 1]]) {
				cache[found + 1] = i;
				max = Math.max(max, found + 1);
			}
		}
		return max;
		
	}
	
}
