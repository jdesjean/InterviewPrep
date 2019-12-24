package org.ip.dp;

import org.ip.array.Utils;

// EPI 2018: 16.11
public class PrettyPrint {
	public static void main(String[] s) {
		String text = "I have inserted a large number of new examples from the papers for the Mathematical Tripos during the last twenty years, which should be useful to Cambridge students.";
		System.out.println(new PrettyPrint().solve(text, 36));
	}
	public int solve(String s, int k) {
		String[] a = s.split(" ");
		int[] cache = new int[a.length];
		for (int i = cache.length - 1; i >= 0; i--) {
			int kk = k - a[i].length();
			int next = i < cache.length - 1 ? cache[i + 1] : 0;
			cache[i] = kk * kk + next;
			for (int j = i + 1; j < cache.length; j++) {
				kk -= (a[j].length() + 1);
				if (kk < 0) break;
				next = j < cache.length - 1 ? cache[j + 1] : 0;
				cache[i] = Math.min(cache[i], kk * kk + next);
			}
		}
		return cache[0];
	}
}
