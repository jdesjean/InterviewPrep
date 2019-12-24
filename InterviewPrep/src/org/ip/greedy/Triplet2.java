package org.ip.greedy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.ip.array.Utils;

// EPI 2018: 17.4
// Not sure this works all the time, but O(n)
public class Triplet2 {
	public static void main(String[] s) {
		int[] result = new Triplet2().solve(new int[] {11,2,5,7,3}, 21);
		Utils.println(result, result.length);
		result = new Triplet2().solve(new int[] {12,1,5,10,2}, 17);
		Utils.println(result, result.length);
	}
	public int[] solve(int[] a, int target) {
		Arrays.sort(a);
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < a.length; i++) {
			set.add(a[i]);
		}
		for (int l = 0, r = a.length - 1; l < r; ) {
			int s = a[l] + a[r];
			int diff = target - s;
			if (set.contains(diff)) {
				return new int[] {diff, a[l], a[r]};
			} else if (diff > 0) {
				l++;
			} else {
				r--;
			}
		}
		return new int[0];
	}
}
