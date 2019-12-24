package org.ip.greedy;

public class Doublet {
	public static int[] solve(int[] array, int target, int l, int r) {
		while (l < r) {
			int sum = array[l] + array[r];
			if (sum == target) {
				return new int[] {l,r};
			}
			else if (sum < target) {
				l++;
			} else {
				r--;
			}
		}
		return null;
	}
}
