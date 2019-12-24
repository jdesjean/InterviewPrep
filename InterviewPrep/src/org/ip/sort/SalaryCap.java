package org.ip.sort;

import java.util.Arrays;

// EPI 2018 13.12
public class SalaryCap {
	public static void main(String[] s) {
		int[] a = new int[] {90,30,100,40,20};
		System.out.println(new SalaryCap().solve(a, 210));
	}
	public int solve(int[] a, int target) {
		Arrays.sort(a);
		for (int i = 0; i < a.length; i++) {
			if (a[i] * (a.length - i) <= target) {
				target-=a[i];
			} else {
				return target / (a.length - i);
			}
		}
		return a[a.length - 1];
	}
}
