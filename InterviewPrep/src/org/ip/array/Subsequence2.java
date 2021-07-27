package org.ip.array;

import java.util.Arrays;

/**
 * <a href="https://leetcode.com/discuss/interview-question/350233/Google-or-Summer-Intern-OA-2019-or-Decreasing-Subsequences">OA 2019</a>
 */
public class Subsequence2 {
	public static void main(String[] s) {
		tc1();
		tc2();
		tc3();
	}
	private static void tc1() {
		//Input: [5, 2, 4, 3, 1, 6]
		//Output: 3
		System.out.println(new Subsequence2().solve(new int[] {5,2,4,3,1,6}));
	}
	private static void tc2() {
		//Input: [1, 1, 1]
		//Output: 3
		System.out.println(new Subsequence2().solve(new int[] {1, 1, 1}));
	}
	private static void tc3() {
		//Input: [2, 9, 12, 13, 4, 7, 6, 5, 10]
		//Output: 4
		System.out.println(new Subsequence2().solve(new int[] {2, 9, 12, 13, 4, 7, 6, 5, 10}));
	}
	public int solve(int[] a) {
		int[] buffer = new int[a.length];
		Arrays.fill(buffer, Integer.MAX_VALUE);
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			int idx = Arrays.binarySearch(buffer, a[i]);
			if (idx < 0) {
				idx = (-1)*(idx+1);
			} else {
				for (int j = idx + 1; j < a.length; j++) {
					if (buffer[j] == a[i]) continue;
					idx = j;
				}
			}
			buffer[idx] = a[i];
			max = Math.max(max, idx + 1);
		}
		return max;
	}
}
