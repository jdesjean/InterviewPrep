package org.ip.honor;

import org.ip.array.Utils;

// EPI 2018: 24.2
public class Missing {
	public static void main(String[] s) {
		int[] a = new int[] {3,5,4,-1,5,1,-1};
		System.out.println(new Missing().solve(a));
	}
	public int solve(int[] a) {
		for (int i = 0; i < a.length; ) {
			if (a[i] == i + 1) {
				i++;
			} else if (a[i] < 1 || a[i] > a.length) { // Doesn't fit skip
				i++;
			} else {
				int j = a[i] - 1; // 1 should be at 0
				if (a[i] == a[j]) { // dup skip
					i++;
					continue;
				}
				Utils.swap(a, i, j);
			}
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i] != i + 1) return i + 1;
		}
		return -1;
	}
}
